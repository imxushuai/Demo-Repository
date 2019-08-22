package com.imxushuai.entities.mysql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "job_cron")
public class JobCron {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String cron;

    @Column
    private String current;

    @Column
    private String description;
}
