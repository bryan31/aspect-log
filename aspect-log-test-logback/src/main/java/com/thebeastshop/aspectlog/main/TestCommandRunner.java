package com.thebeastshop.aspectlog.main;

import com.thebeastshop.aspectlog.vo.Company;
import com.thebeastshop.aspectlog.vo.Department;
import com.thebeastshop.aspectlog.vo.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestCommandRunner implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo demo;

    @Override
    public void run(String... args) throws Exception {
        demo.demo1("NO1234","jenny");
        demo.demo2("NO1234","jenny");
        demo.demo3(initTestPerson());
    }

    private Person initTestPerson(){
        Department dpt = new Department(80013,"技术部");
        Company company = new Company(12001L,"上海ABC技术有限公司",dpt);
        Person person = new Person(31L,"张三",25,company);
        return person;
    }

}
