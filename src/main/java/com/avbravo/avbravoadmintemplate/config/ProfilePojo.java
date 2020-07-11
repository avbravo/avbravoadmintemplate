/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.avbravoadmintemplate.config;


import com.avbravo.sigeclclient.entity.Applicative;
import com.avbravo.sigeclclient.entity.Role;
import java.util.List;

/**
 *
 * @author avbravo
 */
public class ProfilePojo {
    private Applicative applicative;
    private List<Role> role;

    public ProfilePojo() {
    }

    public ProfilePojo(Applicative applicative, List<Role> role) {
        this.applicative = applicative;
        this.role = role;
    }

    
    
    
    
    public Applicative getApplicative() {
        return applicative;
    }

    public void setApplicative(Applicative applicative) {
        this.applicative = applicative;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }
    
    
    
    
}
