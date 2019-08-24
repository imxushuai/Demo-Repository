package com.imxushuai.repository.secondary;

import com.imxushuai.entity.secondary.SecondaryTable;
import org.springframework.data.repository.CrudRepository;

public interface SecondaryRepository extends CrudRepository<SecondaryTable, Long> {
}
