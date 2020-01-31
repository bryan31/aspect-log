package com.thebeastshop.aspectlog.vo;

public class Company {

    private Long id;

    private String companyName;

    private Department department;

    public Company(Long id, String companyName, Department department) {
        this.id = id;
        this.companyName = companyName;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
