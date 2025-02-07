package com.pagss.lms.domains;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="trainerfinance")
public class TrainerFinance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int trainerFinanceId;
	@Getter @Setter private int classId;
	@Getter @Setter private int employeeId;
	@Getter @Setter private BigDecimal trainerRate;
	@Getter @Setter private int noOfDays;
	@Getter @Setter private BigDecimal totalCost;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
}
