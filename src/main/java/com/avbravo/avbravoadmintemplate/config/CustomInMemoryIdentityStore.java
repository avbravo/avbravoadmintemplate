/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.avbravoadmintemplate.config;


import com.avbravo.jmoordbutils.JmoordbResourcesFiles;

import java.util.Arrays;
import java.util.HashSet;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;


/**
 *
 * @author avbravo
 */
@ApplicationScoped
public class CustomInMemoryIdentityStore implements IdentityStore {

    private String rolValue = "LOGIN";
    @Inject
    JmoordbResourcesFiles rf;
//    @Inject
//    UserServices userServices;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        UsernamePasswordCredential login = (UsernamePasswordCredential) credential;
        //Esta adaptado para guardar el GRUPO DEL USUARIO la validacion se hizo en LoginController
        String username = login.getCaller();
        String password = login.getPasswordAsString();

        //Validacion admin-faces
        if (login.getCaller().equals("admin@faces.com") && login.getPasswordAsString().equals("admin")) {
            return new CredentialValidationResult("admin", new HashSet<>(Arrays.asList("ADMIN")));
        } else if (login.getCaller().equals("user@faces.com") && login.getPasswordAsString().equals("user")) {
            return new CredentialValidationResult("user", new HashSet<>(Arrays.asList("USER")));
        } else {
            return CredentialValidationResult.INVALID_RESULT;
        }
        //Validacion jmoordb
        
//        if (!isValidUser(username, password)) {
//            return CredentialValidationResult.NOT_VALIDATED_RESULT;
//        }
//        return new CredentialValidationResult(rolValue, new HashSet<>(Arrays.asList("LOGIN")));

    }

    // <editor-fold defaultstate="collapsed" desc="Boolean isValidUser(String username, String password) ">
//    private Boolean isValidUser(String username, String password) {
//        try {
//
//            if (!isValidData(username, password)) {
//                return false;
//            }
//            User user = new User();
//            user.setUsername(username);
//
////            Role role = (Role) JmoordbContext.get("jmoordb_rol");
////Asigna el rol del user
////            this.rolValue = role.getIdrole();
//
//            //-----------------
//            user.setUsername(username);
//
//            Optional<User> optional = userServices.findByUsername(username);
//            if (!optional.isPresent()) {
//                JsfUtil.warningMessage(rf.getAppMessage("login.usernamenotvalid"));
//                return false;
//            } else {
//              
//                User u2 = optional.get();
//                  
//                user = u2;
//                 
//                //guarda el user logeado
//                JmoordbContext.put("jmoordb_user", user);
//
//                if (!JsfUtil.desencriptar(user.getPassword()).equals(password)) {
//                    JsfUtil.successMessage(rf.getAppMessage("login.passwordnotvalid"));
//                    return false;
//                }
//                if (user.getActive().equals("no")) {
//                    JsfUtil.successMessage(rf.getAppMessage("login.userinactivo"));
//                    return false;
//                }
//
//            
////No se valida el rol del usuario
//                //Valida los roles del user si coincide con el seleccionado
////                Boolean foundrol = false;
////                for (Rol r : user.getProfile()) {
////                    if (rol.getIdrol().equals(r.getIdrol())) {
////                        foundrol = true;
////                    }
////                }
////                if (!foundrol) {
////                    JsfUtil.successMessage(rf.getAppMessage("login.notienerolenelsistema") + " " + rol.getIdrol());
////                    return false;
////                }
//                return true;
//            }
//        } catch (Exception e) {
//              JsfUtil.successMessage("isValidUser() "+e.getLocalizedMessage());
//        }
//        return false;
//    }
//
//    // </editor-fold>
//    // <editor-fold defaultstate="collapsed" desc="Boolean isValidData(String username, String password)">
//    private Boolean isValidData(String username, String password) {
//        try {
//            if (username.isEmpty() || username.equals("") || username == null) {
//                JsfUtil.successMessage(rf.getAppMessage("warning.usernameisempty"));
//                return false;
//            }
//            if (password.isEmpty() || password.equals("") || password == null) {
//                JsfUtil.successMessage(rf.getAppMessage("warning.passwordisempty"));
//                return false;
//            }
//            return true;
//        } catch (Exception e) {
//              JsfUtil.successMessage("isValidData() "+e.getLocalizedMessage());
//        }
//
//        return false;
//    }
    // </editor-fold>
}
