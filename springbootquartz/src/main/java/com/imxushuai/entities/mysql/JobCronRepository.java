package com.imxushuai.entities.mysql;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface JobCronRepository extends CrudRepository<JobCron, Long> {

    @Transactional
    @Modifying
    @Query("update JobCron jc set jc.current = '0'")
    void updateCurrent();

    JobCron findByCurrent(String current);
}
