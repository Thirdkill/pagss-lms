package com.pagss.lms.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.FormulaError;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.domains.JobRole;
import com.pagss.lms.domains.User;
import com.pagss.lms.domains.UserGroup;
import com.pagss.lms.domains.UserType;
import com.pagss.lms.manager.interfaces.JobRoleManager;
import com.pagss.lms.manager.interfaces.UserGroupManager;
import com.pagss.lms.manager.interfaces.UserManager;
import com.pagss.lms.spring.data.repositories.JobRoleRepository;
import com.pagss.lms.spring.data.repositories.UserGroupRepository;
import com.pagss.lms.spring.data.repositories.UserTypeRepository;

@Component
public class ExcelGenerator {
	private Logger logger;
	@Autowired
	private UserTypeRepository userTypeRepository;
	@Autowired
	private UserGroupManager userGroupManager;
	@Autowired
	private JobRoleRepository jobRoleRepository;
	@Autowired
	private UserGroupRepository userGroupRepository;
	@Autowired
	private UserManager userManager;
	@Autowired
	private JobRoleManager jobRoleManager;
	

	private static final int CELL_EMPLOYEE_NUMBER = 0
			, CELL_LAST_NAME = 1
			, CELL_FIRST_NAME = 2
			, CELL_MIDDLE_NAME = 3
			, CELL_EMAIL = 4
			, CELL_CONTACT_NUMBER = 5
			, CELL_USER_TYPE = 6
			, CELL_JOB_ROLE = 7
			, CELL_USER_GROUP = 8
			, CELL_HIRE_DATE = 9;
	public File generateMassUserTemplate(int userTypeId
			, int jobRoleId, int userGroupId) throws IOException {
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("massusertemplate");
		byte[] blue = new byte[]{(byte) 50, (byte) 164, (byte) 168};

		XSSFCellStyle style = wb.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
        style.setFillForegroundColor(new XSSFColor(blue, new DefaultIndexedColorMap()));
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        Optional<UserType> userType;
        Optional<JobRole> jobRole;
        Optional<UserGroup> userGroup;
        
        String userTypeValue = ""
        	, jobRoleValue = ""
        	,userGroupValue = "";
        if (userTypeId > 0) {
        	userType = userTypeRepository.findById(userTypeId);
        	userTypeValue = userType.get().getUserTypeDesc();
        } else {
        	userTypeValue = "No Default User Type";
        }
        if (jobRoleId > 0) {
        	jobRole = jobRoleRepository.findById(jobRoleId);
        	jobRoleValue = jobRole.get().getJobName() 
    				+ "(" + jobRole.get().getJobCode() + ")";
        } else {
        	jobRoleValue = "No Default Job Role";
        }
        if (userGroupId > 0) {
        	userGroup = userGroupRepository.findById(userGroupId);
        	userGroupValue = userGroup.get().getUserGroupName()
    				+ "(" + userGroup.get().getUserGroupCode() + ")";
        } else {
        	userGroupValue = "No Default User Group";
        }
        
        
        String[] headerRecord  = new String[] {"Employee Number", "Last Name*", "First Name*"
				, "Middle Initial" , "Email"," Contact Number"
				, "User Type*",  "Job Role*",  "User Group", "Date Hire(ex: 2019/08/25)*"};
		String[] values = {"", "", ""
				, "", "", "" 
				, userTypeValue, jobRoleValue, userGroupValue, ""};
		
		List<UserType> userTypeList = (List<UserType>) userTypeRepository.findAll();
		List<UserGroup> userGroupsList = userGroupManager.fetchUserGroupList();
		List<JobRole> jobRolesList = (List<JobRole>) jobRoleRepository.findAll();
		
		ArrayList<String> userGroupsToString = new ArrayList<>();
		ArrayList<String> userTypesToString = new ArrayList<>();
		ArrayList<String> jobRolesToString = new ArrayList<>();
		
		userGroupsToString.add("No Default User Group");
		userTypesToString.add("No Default User Type");
		jobRolesToString.add("No Default Job Role");
		for (UserGroup userGroup1: userGroupsList) {
			userGroupsToString.add(userGroup1.getUserGroupName() + "("
					+ userGroup1.getUserGroupCode() + ")");
		}

		for (UserType userType1: userTypeList) {
			userTypesToString.add(userType1.getUserTypeDesc());
		}
		
		for (JobRole jobRole1: jobRolesList) {
			jobRolesToString.add(jobRole1.getJobName() + "("
					+ jobRole1.getJobCode() + ")"
					);
		}
		String[] userGroups = new String[userGroupsToString.size()];
		userGroups = userGroupsToString.toArray(userGroups);
		
		String[] userTypes = new String[userTypesToString.size()];
		userTypes = userTypesToString.toArray(userTypes);
		
		String[] jobRoles = new String[jobRolesToString.size()];
		jobRoles = jobRolesToString.toArray(jobRoles);
		
		List<String> listHeader = Arrays.asList(headerRecord);
		List<String> listValues = Arrays.asList(values);
        insertDropDown(userTypes, CELL_USER_TYPE, CELL_USER_TYPE, sheet);
        insertDropDown(jobRoles, CELL_JOB_ROLE, CELL_JOB_ROLE, sheet);
        insertDropDown(userGroups, CELL_USER_GROUP, CELL_USER_GROUP, sheet);
		for (int i = 0; i < 1000; i++) {
			
			XSSFRow row = sheet.createRow(i);
			if (i == 0) {
				for (int x = 0; x < listHeader.size(); x++) {

                    XSSFCell cell = row.createCell(x);
                    cell.setCellStyle(style);
                    setCellValue(cell, listHeader.get(x));
                }
                style = null;
            } else {
                for (int y = 0; y < listValues.size(); y++) {
                    XSSFCell cell = row.createCell(y);
                    setCellValue(cell, listValues.get(y));
                }
            }
		}
		int width = ((int)(20 * 1.14388)) * 256;
        
        for (int i = 0; i < listHeader.size(); i++) {
            sheet.setColumnWidth(i, width);
        }
        
		File file = File.createTempFile("massuploadtemplate", ".tmp");
		
		try (FileOutputStream out = new FileOutputStream(file)) {
			wb.write(out);
			wb.close();
		}
		return file;
	}
	
	
	public List<User> uploadUserTemplate(File file) throws IOException {
		FileInputStream inputFile = null;
		inputFile = new FileInputStream(file);
		List<User> uploadedUserInfo = new ArrayList<>();
		
		try (Workbook wb = WorkbookFactory.create(file)) {
		Sheet sheet = wb.getSheetAt(0);
		
		sheet.forEach(row -> {
			
            if (row.getRowNum() == 0) {
	                return;
            }
            User user = new User();
            String getSelectedDropDownValue = "";
            user.setEmployeeCode(getStringValue(row,  CELL_EMPLOYEE_NUMBER));
            user.setLastName(getStringValue(row, CELL_LAST_NAME));
            user.setFirstName(getStringValue(row, CELL_FIRST_NAME));
            user.setMiddleInitial(getStringValue(row, CELL_MIDDLE_NAME));
            user.setStatus(1);
            user.setEmail(getStringValue(row, CELL_EMAIL));
            user.setMobileNo(getStringValue(row, CELL_CONTACT_NUMBER));
            getSelectedDropDownValue = getStringValue(row, CELL_USER_TYPE);

            user.setUserTypeDesc(getSelectedDropDownValue);
            
            if (!StringUtils.equals(getSelectedDropDownValue.toLowerCase(), "no default user type")) {
            	user.setUserTypeId(this.userManager.getUserTypeId(getSelectedDropDownValue));
            } else {
            	user.setUserTypeId(0);
            }
            getSelectedDropDownValue = getStringValue(row, CELL_JOB_ROLE);
            user.setJobName(getSelectedDropDownValue);
            if (!StringUtils.equals(getSelectedDropDownValue, "No Default Job Role")) {
            	user.setJobroleId(this.jobRoleManager
            			.getJobRoleId(getCodeOnFetchUpload(getSelectedDropDownValue)));
            } else {
            	user.setJobroleId(0);
            }
            getSelectedDropDownValue = getStringValue(row, CELL_USER_GROUP);
            user.setUserGroupName(getSelectedDropDownValue);
            if (!StringUtils.equals(getSelectedDropDownValue, "No Default User Group")) {
            	user.setUserGroupId(this.userGroupManager
            			.getUserGroupId(getCodeOnFetchUpload(getSelectedDropDownValue)));
            } else {
            	user.setUserGroupId(0);
            }
            user.setUsername(user.getFirstName() + "." + user.getLastName());
            user.setFullName(user.getFirstName() + " " + user.getLastName());
            user.setPassword("password");
            Date date = getDateValue(row, CELL_HIRE_DATE);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (date != null) {
            	user.setDateHired(format.format(date));
            }
            
            user.setIsPasswordReset(1);
            if(user.getFirstName().trim().isEmpty() && user.getLastName().trim().isEmpty()
            		&& user.getEmail().trim().isEmpty()) {
            	
            } else {
            	uploadedUserInfo.add(user);
            }
		});
		inputFile.close();
		return uploadedUserInfo;
		}
	}
	
	public void insertCellValue(Object value ,XSSFRow row, int column) {
		XSSFCell cell;
		cell = row.createCell(column);
		setCellValue(cell, value);
	
	}
	private void insertDropDown(String[] list, int rangeX, int rangeY, XSSFSheet sheet) {
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
                .createExplicitListConstraint(list);
        CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, rangeX, rangeY);
        XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(
                dvConstraint, addressList);
        validation.setShowErrorBox(true);
        sheet.addValidationData(validation);

    }
	
	public Cell setCellValue(Cell cell, Object value) {
		if (value == null) {
            
        } else if (value instanceof CharSequence) {
            CharSequence text = (CharSequence) value;
            cell.setCellValue(text.toString());
            
        } else if (value instanceof Number) {
            Number number = (Number) value;
            cell.setCellValue(number.doubleValue());
            
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
            
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
            
        } else if (value instanceof Calendar) {
            cell.setCellValue((Calendar) value);
            
        } else if (value instanceof RichTextString) {
            cell.setCellValue((RichTextString) value);
        }
		
		return cell;
	}
	
	public String getStringValue(Row row, int columnIndex) {
		DataFormatter formatter = new DataFormatter();
		if (row == null) {
			return "";
		}
		Cell cell = row.getCell(columnIndex);
		if (cell == null) {
			return "";
		}
		if (cell.getCellType() == CellType.NUMERIC) {
			return NumberToTextConverter.toText(cell.getNumericCellValue());
		}
		formatter.formatCellValue(cell);
		Object value = getValue(cell, cell.getCellType());
		return Objects.toString(value, null).trim();
	}
	
	public String getNumericValueToString(Row row, int rowColumn) {
		if (row == null) {
			return null;
		}
		Cell cell = row.getCell(rowColumn);
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }
        
        if (cell.getCellType() == CellType.NUMERIC) {
    		return NumberToTextConverter.toText(cell.getNumericCellValue());
        }
		return null;
	}
	
	public Date getDateValue(Row row, int rowColumn) {
		if (row == null) {
			return null;
		}
		Cell cell = row.getCell(rowColumn);
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }
        
        boolean dateType = cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell);
        if (dateType) {
            return cell.getDateCellValue();
        } else {
            return null;
        }
    }
	
	public Object getValue(Cell cell, CellType cellType) {
		DataFormatter formatter = new DataFormatter();
		formatter.formatCellValue(cell);
        switch (cellType) {
            case STRING:
                return cell.getStringCellValue();
                
            case BOOLEAN:
                return cell.getBooleanCellValue();
                
            case NUMERIC:
                return cell.getNumericCellValue();
                
            case BLANK:
                return "";
                
            case FORMULA:
                CellType cellTypeReference = cell.getCachedFormulaResultType();
                return getValue(cell, cellTypeReference);
                
            case ERROR:
                int errorValue = cell.getErrorCellValue();
                String errorCode = FormulaError.forInt(errorValue).toString();
                String message = "The cell is invalid: " + errorCode;
                logger.warning(message);
                return null;
                
            default:
                logger.warning("The cell has no valid type.");
                return null;
        }
    }
	
	private String getCodeOnFetchUpload(String value) {
		if (value.contains("(")) {
			int getLength = value.trim().length();
			int getLastIndexOfCharacter = value.lastIndexOf("(");
			return value.substring(getLastIndexOfCharacter + 1, getLength - 1);
		}
		return value;
	}
}
