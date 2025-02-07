package com.pagss.lms.manager.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pagss.lms.dao.interfaces.UserDao;
import com.pagss.lms.dao.jdbc.JdbcUserGroupDao;
import com.pagss.lms.domains.EmployeeInfo;
import com.pagss.lms.domains.User;
import com.pagss.lms.domains.UserGroupMember;
import com.pagss.lms.manager.interfaces.UserManager;
import com.pagss.lms.spring.data.repositories.EmployeeInfoRepository;
import com.pagss.lms.spring.data.repositories.UserRepository;
import com.pagss.lms.utilities.InputValidators;

@Component
public class LmsUserManager implements UserManager {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired 
	private UserRepository userRepository;
	@Autowired
	private EmployeeInfoRepository employeeInfoRepository;
	@Autowired
	private LmsEmployeeInfoManager empManager;
	
	@Autowired
	private JdbcUserGroupDao userGroupDao;
	@Autowired
	private InputValidators inputValidators;
	
	@Override
	public int countUser(User user) {
		return this.userDao.countUser(user);
	}
	
	@Override
	public User fetchUser(User user) {
		return this.userDao.fetchUser(user);
	}
	
	@Override
	public List<User> fetchUsers(int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.userDao.fetchUsers(pageSize, calculatedPageNo);
	}

	@Override
	public int countTotalUsers() {
		return this.userDao.countTotalUsers();
	}
	@Override
	public List<User> searchUsers(User user, int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		
		StringBuffer whereClause = new StringBuffer();
		int userTypeId = user.getUserTypeId();
		int jobRoleId = user.getJobroleId();
		int userStatus = user.getStatus();
		
		if(jobRoleId != 0) {
			whereClause.append("AND ei.jobRoleId = :jobRoleId ");
		}
		
		if(userTypeId != 0) {
			whereClause.append("AND ut.userTypeId = :userTypeId ");
		}
		
		if(userStatus != 2) {
			whereClause.append("AND us.status = :status ");
		}
		
		user.setWhereClause(whereClause.toString());
		
		return this.userDao.searchUsers(user, pageSize, calculatedPageNo);
	}
	@Override
	public int countTotalSearchedUsers(User user) {
		return this.userDao.countTotalSearchedUsers(user);
	}
	@Override
	public User fetchUserInfo(User user) {
		return this.userDao.fetchUserInfo(user);
	}
	@Override
	public User generateUserCode() {
		User newUser = new User();
		newUser.setEmployeeCode(generateEmployeeCode(this.userDao.fetchLatestUserId()));
		newUser.setEmployeeId(this.userDao.fetchLatestEmployeeId());
		newUser.setUserId(this.userDao.fetchLatestUserId());
		return newUser;
	}
	
	private String generateEmployeeCode(int employeeId) {
		String empNo = Integer.toString(100000 + employeeId).substring(1);
		return "EN" + empNo;
	}
	
	@Override
	public int createUserCredentials(User user) {
		String genUsername = user.getFirstName().replaceAll("\\s","")+"."+user.getLastName();
		int countGenUsername = this.userDao.countUsername(genUsername);
		if(countGenUsername >= 1) {
			genUsername = genUsername+""+countGenUsername;
		}
		
		int leftLimit = 97;
	    int rightLimit = 122;
	    int targetStringLength = 10;
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(targetStringLength);
	    for (int i = 0; i < targetStringLength; i++) {
	        int randomLimitedInt = leftLimit + (int) 
	          (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	    }
	    String generatedPass = buffer.toString();

		user.setUsername(genUsername.toLowerCase());
		user.setPassword(generatedPass);
		
		return this.userDao.addUserInfo(user);
	}
	@Override
	public int generatePassword(User user, int userId) {
		int leftLimit = 97;
	    int rightLimit = 122;
	    int targetStringLength = 10;
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(targetStringLength);
	    for (int i = 0; i < targetStringLength; i++) {
	        int randomLimitedInt = leftLimit + (int) 
	          (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	    }
	    String generatedPass = buffer.toString();
	    user.setPassword(generatedPass);
	    return this.userDao.updateUserPassword(user);
	}
	@Override
	public int updateUserInfo(User user, int userId) {
		StringBuffer setClause = new StringBuffer();
		int userTypeId = user.getUserTypeId();
		int userStatus = user.getStatus();

		if(userTypeId != 0 && userStatus != 0) {
			setClause.append("us.userTypeId = :userTypeId, us.status = :status ");
		}
		user.setUserId(userId);
		user.setWhereClause(setClause.toString());

		return this.userDao.updateUserInfo(user);
	}

	@Override
	public int checkPasswordReset(User user) {
		User checkUser = this.userDao.fetchUser(user);
		int isPassReset = checkUser.getIsPasswordReset();
		return isPassReset;
	}

	@Override
	public void saveNewPassword(User user) {
		user.setUserId(user.getUserId());
		this.userDao.saveNewPassword(user);
	}

	@Override
	public List<User> fetchUserlist() {
		return this.userDao.fetchUserlist();
	}

	@Override
	public List<User> searchUserList(User user, int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		
		StringBuffer whereClause = new StringBuffer();
		int jobRoleId = user.getJobroleId();
		
		if(jobRoleId != 0) {
			whereClause.append("AND ei.jobRoleId = :jobRoleId ");
		}
		user.setWhereClause(whereClause.toString());		
		return this.userDao.fetchSearchedUserListOnUserGroup(user, pageSize, calculatedPageNo);
	}

	@Override
	public int countTotalSearchedUserList(User user) {
		return this.userDao.countTotalSearchedUserListOnUserGroup(user);
	}

	@Override
	public List<User> fetchUserList(int pageSize, int pageNo) {
		int calculatedPageNo = pageSize * (pageNo - 1);
		return this.userDao.fetchUserListOnUserGroup(pageSize, calculatedPageNo);
	}

	@Override
	public int countTotalUserList() {
		return this.userDao.countTotalUserListOnUserGroup();
	}

	@Override
	public int countTotalRecords() {
		return this.userDao.countTotalRecords();
	}

	@Override
	public int countTotalActiveRecords() {
		return this.userDao.countTotalActiveRecords();
	}

	@Override
	public int countTotalInactiveRecords() {
		return this.userDao.countTotalInactiveRecords();
	}
	
	@Override
	public int getUserTypeId(String userTypeDesc) {
		return this.userDao.getUserTypeId(userTypeDesc);
	}
	
	@Override
	public int countUsername(String username) {
		return this.userDao.countUsername(username);
	}
	@Override
	public User validateUploadedUser(User user) {
		return user;
	}
	@Override
	//single upload
	public User createUploadedUser(User user) {
		user = validateEmployeeInfo(user);
		if (user.getErrorMessage().isEmpty()) {
			List<User> saveList = new ArrayList<>();
			saveList.add(user);
			addEmployee(saveList);
			return saveList.get(0);
		} else {
			return user;
		}
		
	}	
	
	@Override
	public void createUploadedUsers(List<User> errorList, List<User> saveList
			, List<User> uploadedUser, String modifiedBy) {
		int i = 0;
		for(User user : uploadedUser) {
			user.setModifiedBy(modifiedBy);
			validateEmployeeInfo(user);
			if (user.getErrorMessage().isEmpty()) {
				saveList.add(user);
			} else {

				i++;
				//temporary id
				user.setUserId(i);
				errorList.add(user);
			}
		}
		if (saveList.size() > 0) {
			addEmployee(saveList);
		}
	}
	
	private Integer addEmployee(List<User> userList) {
		User fetchGeneratedCode = new User();
		String genUsername;
		String username = "";

		for (int index = 0; index < userList.size(); index++) {
			fetchGeneratedCode = generateUserCode();
			User user = userList.get(index);
			genUsername = user.getFirstName().replaceAll("\\s","")+"."+user.getLastName();
			username = genUsername;
			
			for (int i = 1; i < 1000; i++) {
				if (this.userDao.countUsername(username) == 0) {
					break;
				} else {
					username = genUsername + (i+1);
				}
			}
			user.setUsername(username.toLowerCase());
			user.setUserId(0);
			user.setUserId(userRepository.save(user).getUserId());
			EmployeeInfo employeeInfo = new EmployeeInfo();
			employeeInfo.setFirstName(user.getFirstName());
			employeeInfo.setLastName(user.getLastName());
			employeeInfo.setFullName(user.getFullName());
			employeeInfo.setDateHired(user.getDateHired());
			employeeInfo.setEmployeeCode(fetchGeneratedCode.getEmployeeCode());
			employeeInfo.setUserId(user.getUserId());
			employeeInfo.setJobRoleId(user.getJobroleId());
			employeeInfo.setMiddleInitial(user.getMiddleInitial());
			employeeInfo.setMobileNo(user.getMobileNo());
			employeeInfo.setEmail(user.getEmail());
			employeeInfo.setModifiedBy(user.getModifiedBy());
			this.employeeInfoRepository.save(employeeInfo);
			if (user.getUserGroupId() > 0) {
				List<UserGroupMember> userGroupMemberList = new ArrayList<>();
				UserGroupMember userGroupMember = new UserGroupMember();
				userGroupMember.setUserGroupId(user.getUserGroupId());
				userGroupMember.setUserId(user.getUserId());
				userGroupMemberList.add(userGroupMember);
				this.userGroupDao.addMember(userGroupMemberList);
			}
			// fetch the latest data on mysql
			user = this.userDao.fetchUserInfo(user);
			userList.set(index, user);
			fetchGeneratedCode = user;
		}
		return fetchGeneratedCode.getUserId();
	}
	
	private User validateEmployeeInfo(User user) {
		StringBuilder errorMessage = new StringBuilder();
		//auto set if username have duplicate
		if (user.getLastName().trim().isEmpty()) {
			errorMessage.append(" Last Name is Empty");
		} else {
			if (this.inputValidators.isNumeric(user.getLastName())) {
				errorMessage.append(" Last Name cannot have numeric values");
			}
			if (user.getLastName().trim().length() > 25) {
				errorMessage.append(" Last Name cannot be more than 25 characters");
			}
		}
		
		if (user.getFirstName().trim().isEmpty()) {
			errorMessage.append(" First Name is Empty");
		} else {
			if (this.inputValidators.isNumeric(user.getFirstName())) {
				errorMessage.append(" First Name cannot have numeric values");
			}
			if (user.getFirstName().trim().length() > 50) {
				errorMessage.append(" First Name  cannot be more than 50 characters ");
			}
		}
		
		if(user.getMiddleInitial().trim().length() > 5) {
			errorMessage.append(" Middle Name  cannot be more than 5 characters ");
		}
		
		if (user.getUserTypeId() == 0) {
			errorMessage.append(" User Type is Empty");
		}
		
		if (user.getJobroleId() == 0) {
			errorMessage.append(" Job Role is Empty");
		}
		
		if (!user.getEmail().isEmpty()) {
			if (!inputValidators.validateEmail(user.getEmail())) {
				errorMessage.append(" Email is Wrong Format");
			}
		}
		
		if (user.getDateHired() == null) {
			errorMessage.append(" Date Hired is not valid");
		}
		
		if (user.getMobileNo().length() >= 15) {
			errorMessage.append(" Number cannot be higher than 15");
		}
		
		if (!StringUtils.isEmpty(user.getEmployeeCode())) {
			EmployeeInfo empInfo = new EmployeeInfo();
			empInfo.setEmployeeCode(user.getEmployeeCode());
			int count = empManager.countEmployeeCode(empInfo);
			if (count > 0) {
				errorMessage.append(" Employee Number: Employee Number already exists. Please try another one.");
			}
			user.setErrorMessage(errorMessage.toString());
		} 
		user.setErrorMessage(errorMessage.toString());
		return user;
	}
}
