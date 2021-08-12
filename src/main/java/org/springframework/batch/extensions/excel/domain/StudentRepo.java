package org.springframework.batch.extensions.excel.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface StudentRepo extends CrudRepository<StudentEntity, Long> {
}
