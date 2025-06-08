package com.company.ecommerce.dto.user;

import com.company.ecommerce.model.User;

public class UserListDTO {
    private Integer id;
    private String fullName;
    private String email;
    private String role;

    public UserListDTO(String fullName, String email, String role) {
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    public UserListDTO(User user) {
        this.fullName = user.getFirstName() + " " + user.getLastName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.id = user.getId();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
