package com.pagss.lms.constants;

public class LmsClassInfoData {

	public static final int 
		//Completion Status
		CANCELLED=										0,
		COMPLETE=										1,
		ONGOING=										2;
	
	public static final int 
		//View Restriction Type
		ACCESSIBLE_BY_TRAINING_DURATION=				1,
		ACCESSIBLE_INDEFINITELY=						2,
		ACCESSIBLE_BY_SPECIFIED_DATE=					3;
	
	public static final int
		//Is SelfRegister
		ISSELFREGISTER_TRUE=							1,
		ISSELFREGISTER_FALSE=							0;
	
	public static final int 
		//Schedule type
		BLOCK=											1,
		SET=											2,
		SERIES=											3;
	
	public static final int
		//With Certificate
		WITHCERTIFICATE_TRUE=							1,
		WITHCERTIFICATE_FALSE=							0;
	
	public static final int
		//Certificate Type
		CERTIFICATETYPE_SYSTEMTEMPLATE=					1,
		CERTIFICATETYPE_CUSTOMTEMPLATE=					2;
	
	public static final int
		//Certificate Downloadable
		CERTIFICATE_DOWNLOADABLE_TRUE=					1,
		CERTIFICATE_DOWNLOADABLE_FALSE=					0;
	
	public static final int
		//Self Register Type
		SELFREGISTERTYPE_OPENINDEFINITELY=				1,
		SELFREGISTERTYPE_SPECIFIEDPERIOD=				0;
	
	public static final int
		//Access Restriction
		ACCESSRESTRICTION_PUBLIC=						1,
		ACCESSRESTRICTION_PRIVATE=						2;
}
