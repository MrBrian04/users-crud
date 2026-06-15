package com.crud.users_crud.infrastructure.adapter.in.web.dto;

public class UserResponse {
    
    private Long id;
    private String name;
    private String email;
    private Integer age;

    public UserResponse() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Integer getAge() { return age; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setAge(Integer age) { this.age = age; }
}
