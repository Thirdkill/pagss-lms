 package com.pagss.lms.dao.interfaces;

import com.pagss.lms.domains.ClassDefault;
import com.pagss.lms.domains.CourseInfo;

public interface ClassDefaultDao {

	public void createClassDefault(CourseInfo courseInfo);
	
	public void updatePhotoUrl(ClassDefault classDefault);
	
	public void updateClassDefault(CourseInfo courseInfo);

	public ClassDefault fetchClassDefault(int courseId);

	public int checkClassDefault(int courseId);
}
