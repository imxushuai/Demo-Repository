package com.imxushuai.services;

import com.imxushuai.entities.mysql.JobCron;
import com.imxushuai.entities.mysql.JobCronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Product service implement.
 */
@Service
public class JobCronServiceImpl implements JobCronService {

    @Autowired
    private JobCronRepository jobCronRepository;

    @Override
    public Iterable<JobCron> listAllJobCron() {
        return jobCronRepository.findAll();
    }

    @Override
    public JobCron getJobCronById(Long id) {
        Optional<JobCron> cronOptional = jobCronRepository.findById(id);
        return cronOptional.orElse(null);
    }

    @Override
    public JobCron saveJobCron(JobCron jobCron) {
        return jobCronRepository.save(jobCron);
    }

    @Override
    public void deleteJobCron(Long id) {
        jobCronRepository.deleteById(id);
    }

    @Override
    public void disable() {
        jobCronRepository.updateCurrent();
    }

    @Override
    public JobCron findByCurrent(String current) {
        return jobCronRepository.findByCurrent(current);
    }


}
