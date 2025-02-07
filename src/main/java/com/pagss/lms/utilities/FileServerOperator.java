package com.pagss.lms.utilities;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.pagss.lms.commands.FileUploadCommand;
import com.pagss.lms.constants.LmsCourseMaterialData;
import com.pagss.lms.constants.LmsFileType;

@Component
public class FileServerOperator {

	@Value("${amazon-s3.accesskey}")
	private String accessKey;
	@Value("${amazon-s3.secretkey}")
	private String secretKey;
	@Value("${amazon-s3.bucketname}")
	private String bucketName;
	@Value("${amazon-s3.region}")
	private String region;
	
	/**
	 * Description : Uploads specified File in amazon S3 and returns file url path
	 * @Param FileUploadCommand : Contains filename, content type and fileDirectory
	 * @return 
	 * **/
	public String uploadFile (MultipartFile file,FileUploadCommand fileUploadCommand) throws Exception {
		BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey,secretKey);
		String uploadPath = new StringBuffer()
				.append(bucketName)
				.append(fileUploadCommand.getFileDir()).toString();
		String fileFullUrlPath = new StringBuffer()
				.append(uploadPath)
				.append("/")
				.append(fileUploadCommand.getFileName()).toString();
		
		System.out.println("["+DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now())+"]FILE UPLOADING");
		InputStream inputStream = file.getInputStream();
		ObjectMetadata metaData = new ObjectMetadata();
		metaData.setContentLength(file.getSize());
		metaData.setContentType(fileUploadCommand.getContentType());

		AmazonS3 s3Client = AmazonS3Client.builder()
			    .withRegion(Regions.US_WEST_2)
			    .withCredentials(new AWSStaticCredentialsProvider(credentials))
			    .build();
		s3Client.putObject(new PutObjectRequest(uploadPath,fileUploadCommand.getFileName(),inputStream,metaData));
		System.out.println("["+DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now())
			+"]Successfully uploaded.");
		return fileFullUrlPath; 
	}
	
	/**
	 * Description: Validates if File format is valid or not
	 * @param file
	 * @return Boolean
	 */
	public boolean validateFileFormat(MultipartFile file) {
		System.out.println("FILE TYPE " + file.getContentType());
		String fileContentType = file.getContentType();
		if (LmsFileType.fileTypes.contains(fileContentType)) {return true;}
		return false;
	}
	
	/**
	 * Description: Returns true, if file upload is image type, and false if not.
	 * @param imageFile
	 * @return Boolean
	 */
	public boolean validateImageFormat(MultipartFile file) {
		String fileContentType = file.getContentType();
		if (LmsFileType.imageTypes.contains(fileContentType)) {return true;}
		return false;
	}
	
	/**
	 * Description: Validates if File format is valid or not depending on the specified 
	 * content type
	 * @param file
	 * @return Boolean
	 */
	public boolean validateCustomFileFormat(int contentType,MultipartFile file) {
		System.out.println("FILE TYPE " + file.getContentType());
		String fileContentType = file.getContentType();
		Boolean result = true;
		if(contentType==LmsCourseMaterialData.CONTENTTYPE_DOCUMENT) {
			if (!LmsFileType.documentTypes.contains(fileContentType)) {result=false;}
		}
		if(contentType==LmsCourseMaterialData.CONTENTTYPE_VIDEO) {
			if (!LmsFileType.videoTypes.contains(fileContentType)) {result=false;}
		}
		if(contentType==LmsCourseMaterialData.CONTENTTYPE_AUDIO) {
			if (!LmsFileType.audioTypes.contains(fileContentType)) {result=false;}
		}
		if(contentType==LmsCourseMaterialData.CONTENTTYPE_IMAGE) {
			if (!LmsFileType.imageTypes.contains(fileContentType)) {result=false;}
		}
		System.out.println(result);
		return result;
	}
	
	public Boolean validateCustomeFileSize(int contentType,MultipartFile file) {
		System.out.println("FILE SIZE " + file.getSize());
		Boolean result = true;
		if(contentType==LmsCourseMaterialData.CONTENTTYPE_VIDEO) {
			if (file.getSize()>LmsFileType.VIDEO_SIZE_LIMIT) {result=false;}
		}
		if(contentType==LmsCourseMaterialData.CONTENTTYPE_AUDIO) {
			if (file.getSize()>LmsFileType.AUDIO_SIZE_LIMIT) {result=false;}
		}
		if(contentType==LmsCourseMaterialData.CONTENTTYPE_IMAGE) {
			if (file.getSize()>LmsFileType.IMAGE_SIZE_LIMIT) {result=false;}
		}
		return result;
	}
	
	/**
	 * Description: Dissect the directory url and deletes
	 * the target keyname with the specified directory.
	 * @param directoryUrl
	 * @throws Exception
	 */
	public void deleteFile (String directoryUrl) throws Exception {
		BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey,secretKey);		
		String[] directoryUrlArray = directoryUrl.split("/");					
		StringBuffer newDirectoryBuffer = new StringBuffer();			
		for (int i=0; i < directoryUrlArray.length - 1; i++) {													
			newDirectoryBuffer.append(directoryUrlArray[i]);			
			if (i < directoryUrlArray.length - 2) {
				newDirectoryBuffer.append("/");
			}							
		}		
		AmazonS3 s3Client = AmazonS3Client.builder()
			    .withCredentials(new AWSStaticCredentialsProvider(credentials))
			    .withRegion(Regions.US_WEST_2)
			    .build();
		s3Client.deleteObject(new DeleteObjectRequest(newDirectoryBuffer.toString(), directoryUrlArray[directoryUrlArray.length-1]));
	}
	
	/**
	 * Description: Generates Presigned url with 1 hour expiration for private files
	 * @param directoryUrl
	 * @return
	 */
	public String generatePresignedUrl(String directoryUrl) {
		BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);		
		AmazonS3 s3Client = AmazonS3Client.builder()
			    .withRegion(region)
			    .withCredentials(new AWSStaticCredentialsProvider(credentials))
			    .build();
		
		Date expiration = new java.util.Date();
        long msec = expiration.getTime();
        msec += 1000 * 60 * 60; 
        expiration.setTime(msec);
        
        if(directoryUrl != null && !directoryUrl.trim().isEmpty()) {
        	String[] directoryUrlArray = directoryUrl.split("/");					
    		StringBuffer newDirectoryBuffer = new StringBuffer();			
    		for (int i=0; i < directoryUrlArray.length - 1; i++) {													
    			newDirectoryBuffer.append(directoryUrlArray[i]);			
    			if (i < directoryUrlArray.length - 2) {
    				newDirectoryBuffer.append("/");
    			}							
    		}		
    		
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(newDirectoryBuffer.toString(), 
            				directoryUrlArray[directoryUrlArray.length-1]);
            generatePresignedUrlRequest.setMethod(HttpMethod.GET); 
            generatePresignedUrlRequest.setExpiration(expiration);
            URL fileUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            return fileUrl.toString();
        }
        return directoryUrl;
	}
}
