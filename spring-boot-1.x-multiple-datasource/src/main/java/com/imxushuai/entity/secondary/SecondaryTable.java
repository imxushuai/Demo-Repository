package com.imxushuai.entity.secondary;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class SecondaryTable {

    @Id
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String s;

}
