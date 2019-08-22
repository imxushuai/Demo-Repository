package com.imxushuai.services;

import com.imxushuai.entities.mysql.JobCron;

public interface JobCronService {

    Iterable<JobCron> listAllJobCron();

    JobCron getJobCronById(Long id);

    JobCron saveJobCron(JobCron jobCron);

    void deleteJobCron(Long id);

    void disable();

    JobCron findByCurrent(String current);
}
