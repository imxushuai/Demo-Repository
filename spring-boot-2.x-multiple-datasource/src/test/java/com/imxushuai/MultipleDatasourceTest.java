package com.imxushuai;

import com.imxushuai.entity.primary.PrimaryTable;
import com.imxushuai.entity.secondary.SecondaryTable;
import com.imxushuai.repository.primary.PrimaryRepository;
import com.imxushuai.repository.secondary.SecondaryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultipleDatasourceTest {

    @Autowired
    private PrimaryRepository primaryRepository;

    @Autowired
    private SecondaryRepository secondaryRepository;

    @Test
    public void testAdd() {
        PrimaryTable primary = new PrimaryTable();
        primary.setId(1L);
        primary.setUsername("PrimaryTable 1");
        primary.setPassword("PrimaryTable 1");
        primary.setP("This primary 1");

        primaryRepository.save(primary);

        SecondaryTable secondaryTable = new SecondaryTable();
        secondaryTable.setId(1L);
        secondaryTable.setUsername("SecondaryTable 1");
        secondaryTable.setPassword("SecondaryTable 1");
        secondaryTable.setS("This secondary 1");

        secondaryRepository.save(secondaryTable);

        System.out.println(primaryRepository.findById(1L));
        System.out.println(secondaryRepository.findById(1L));
    }

}
