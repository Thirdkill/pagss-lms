package com.pagss.lms.manager.interfaces;

import com.pagss.lms.domains.ClassDefault;

public interface ClassDefaultManager {

	public void updatePhotoUrl(ClassDefault classDefault);

	public ClassDefault fetchClassDefault(int courseId);
	
}
