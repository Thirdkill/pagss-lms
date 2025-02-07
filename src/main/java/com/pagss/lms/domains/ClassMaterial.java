package com.pagss.lms.domains;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="classmaterial")
public class ClassMaterial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int classMaterialId;
	@Getter @Setter private int classId;
	@Getter @Setter private int contentType;
	@Getter @Setter private String contentUrl;
	@Getter @Setter private String fileName;
	@Getter @Setter private String fileLabel;
	@Getter @Setter private int viewStatus;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
}
