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
@Table(name="materialfinance")
public class MaterialFinance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private int materialFinanceId;
	@Getter @Setter private int classId;
	@Getter @Setter private String name;
	@Getter @Setter private BigDecimal price;
	@Getter @Setter private int quantity;
	@Getter @Setter private BigDecimal totalCost;
	@Transient @Getter @Setter private String createdAt;
	@Transient @Getter @Setter private String lastModifiedAt;
}
