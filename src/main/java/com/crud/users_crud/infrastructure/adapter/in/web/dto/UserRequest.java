package com.crud.users_crud.infrastructure.adapter.in.web.dto;

public class UserRequest {

    private String name;
    private String email;
    private Integer age;

    public UserRequest() {}

    public String getName() { return name; }
    public String getEmail() { return email; }
    public Integer getAge() { return age; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setAge(Integer age) { this.age = age; }
}
