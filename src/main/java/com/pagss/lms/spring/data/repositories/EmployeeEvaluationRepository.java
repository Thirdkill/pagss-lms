package com.pagss.lms.spring.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pagss.lms.domains.EmployeeEvaluation;

@Repository
public interface EmployeeEvaluationRepository  extends CrudRepository<EmployeeEvaluation, Integer> {}
