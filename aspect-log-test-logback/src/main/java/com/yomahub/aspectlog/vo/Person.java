package com.yomahub.aspectlog.vo;

public class Person {

    private Long id;

    private String name;

    private int age;

    private Company company;

    public Person(Long id, String name, int age, Company company) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
