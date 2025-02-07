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
@Table(name="trainingtopic")
public class TrainingTopic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int topicId;
	@Getter @Setter private String topicDesc;
	@Getter @Setter private int status;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
}
