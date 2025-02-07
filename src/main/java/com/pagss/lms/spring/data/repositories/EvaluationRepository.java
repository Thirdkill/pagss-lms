package com.pagss.lms.spring.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pagss.lms.domains.Evaluation;

@Repository
public interface EvaluationRepository extends CrudRepository<Evaluation, Integer> {}
