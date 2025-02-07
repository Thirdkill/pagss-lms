package com.pagss.lms.manager.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pagss.lms.dao.interfaces.ClassDefaultDao;
import com.pagss.lms.domains.ClassDefault;
import com.pagss.lms.manager.interfaces.ClassDefaultManager;

@Component
public class LmsClassDefaultManager implements ClassDefaultManager {
	
	@Autowired
	private ClassDefaultDao classDefaultDao;
	
	@Override
	public void updatePhotoUrl(ClassDefault classDefault) {
		this.classDefaultDao.updatePhotoUrl(classDefault);
	}

	@Override
	public ClassDefault fetchClassDefault(int courseId) {
		int courseCount = classDefaultDao.checkClassDefault(courseId);
		if(courseCount != 0) {
			return this.classDefaultDao.fetchClassDefault(courseId);
		} else {
			return null;
		}
	}
}
