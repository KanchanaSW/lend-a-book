package com.system.payload.request;

import javax.validation.constraints.*;
import java.util.Set;

public class SignupRequest {
    @NotBlank
    @Size(max = 70)
    @Email
    private String email;

    @NotBlank
    @Size(min=5,max = 120)
    private String password;

    @NotBlank
    @Size(max = 220)
    private String fullname;

    @NotBlank
    @Size(max = 120)
    private String DOB;

    @NotBlank
    @Size(max = 120)
    private String signupDate;

    @NotBlank
    @Size(max = 120)
    private String image;
    
    private Set<String> role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(String signupDate) {
        this.signupDate = signupDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
