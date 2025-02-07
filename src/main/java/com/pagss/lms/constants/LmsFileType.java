package com.pagss.lms.constants;

import java.util.Arrays;
import java.util.List;

public class LmsFileType {

	public static final List<String> fileTypes = Arrays.asList(
		//Image File Types
		"image/png", "image/jpeg", "image/gif",
		//Video File Types
		"video/avi","video/mpeg","video/mpeg","video/mp4","video/x-flv","application/ogg","video/x-matroska",
		"application/octet-stream",
		//Audio File Types
		"audio/wav","audio/mpeg3","audio/mpeg","audio/mp3");
	
	public static final List<String> imageTypes = Arrays.asList(
		"image/png", "image/jpeg", "image/gif","image/bmp","image/tiff");
	
	public static final List<String> documentTypes = Arrays.asList(
			//Power point formats
			"application/mspowerpoint","application/vnd.ms-powerpoint","application/powerpoint",
			"application/x-mspowerpoint","application/mspowerpoint", "application/vnd.oasis.opendocument.presentation",
			"application/vnd.oasis.opendocument.text","application/vnd.openxmlformats-officedocument.presentationml.presentation",
			//Spread sheets
			"application/vnd.oasis.opendocument.spreadsheet",
			"application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
			//Pdf formats
			"application/pdf",
			//MS WORD DOCUMENT
			"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document");
	
	public static final List<String> videoTypes = Arrays.asList(
			"video/avi","video/mpeg","video/mpeg","application/octet-stream",
			"video/mp4","video/ogg","video/x-ms-wmv","application/ogg","video/x-matroska",
			"video/quicktime","	video/x-flv");
	
	public static final List<String> audioTypes = Arrays.asList(
			"audio/wav","audio/mpeg3","audio/mpeg","audio/mp3","audio/ogg");
	
	public static final int
		//content type file size Limit
		VIDEO_SIZE_LIMIT=									524288000,
		AUDIO_SIZE_LIMIT=									10485760,
		IMAGE_SIZE_LIMIT=									10485760,
		DOCUMENT_SIZE_LIMIT=								15728640;
}