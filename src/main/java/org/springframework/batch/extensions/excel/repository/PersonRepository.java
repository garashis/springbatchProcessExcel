package org.springframework.batch.extensions.excel.repository;

import org.springframework.batch.extensions.excel.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}

