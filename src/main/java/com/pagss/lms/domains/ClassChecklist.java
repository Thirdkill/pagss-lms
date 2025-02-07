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
@Table(name="classchecklist")
public class ClassChecklist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int classChecklistId;
	@Getter @Setter private int classId;
	@Getter @Setter private String contentDesc;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
}
