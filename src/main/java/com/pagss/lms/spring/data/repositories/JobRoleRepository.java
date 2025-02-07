package com.pagss.lms.spring.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pagss.lms.domains.JobRole;

@Repository
public interface JobRoleRepository extends CrudRepository<JobRole, Integer> {}

