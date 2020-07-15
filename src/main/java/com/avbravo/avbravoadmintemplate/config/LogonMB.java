package com.avbravo.avbravoadmintemplate.config;

import static com.avbravo.avbravoadmintemplate.util.Utils.addDetailMessage;
import com.avbravo.jmoordb.configuration.JmoordbConnection;
import com.avbravo.jmoordb.configuration.JmoordbContext;
import com.avbravo.jmoordb.mongodb.history.entity.Configuracion;
import com.avbravo.jmoordb.mongodb.history.repository.AccessInfoRepository;
import com.avbravo.jmoordb.services.AccessInfoServices;
import com.avbravo.jmoordbutils.JmoordbResourcesFiles;
import com.github.adminfaces.template.config.AdminConfig;
import com.github.adminfaces.template.session.AdminSession;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Specializes;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;

import static com.github.adminfaces.template.util.Assert.has;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

@Named
@SessionScoped
@Specializes
public class LogonMB extends AdminSession implements Serializable {

    @Inject
    private AdminConfig adminConfig;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ExternalContext externalContext;

    private String password;

    private String email;

    private boolean remember;
    
    //Remueva para el usuario logeado
    //private User user = new User();
    
    //Remueva para el rol o profile
    //private Role role =  new Role();

    /**
     * 
     */
     Configuracion configuracion = new Configuracion();
    //Acceso
    @Inject
    AccessInfoServices accessInfoServices;
    @Inject
    AccessInfoRepository accessInfoRepository;
    @Inject
    JmoordbResourcesFiles rf;
    
     Boolean loggedIn = false;

    
    
     @PostConstruct
    public void init() {
        loggedIn = false;
      
//        configuracion = new Configuracion();

        //Configuracion de la base de datos
//        JmoordbConnection jmc = new JmoordbConnection.Builder()
//                .withSecurity(false)
//                .withDatabase("elsa")
//                .withHost("")
//                .withPort(0)
//                .withUsername("")
//                .withPassword("")
//                .build();
    }
    
    public void autoLogin() throws IOException {
        String emailCookie = Faces.getRequestCookie("admin-email");
        String passCookie = Faces.getRequestCookie("admin-pass");
        if (has(emailCookie) && has(passCookie)) {
            this.email = emailCookie;
            this.password = passCookie;
            login();
        }
    }

    public void login() throws IOException {
     // Aqui coloque el rol o profile que selecciona el usuario para logearse
         //   JmoordbContext.put("jmoordb_rol", role);
        switch (continueAuthentication()) {
            case SEND_CONTINUE:
                facesContext.responseComplete();
                break;
            case SEND_FAILURE:
                Messages.addError(null, "Login failed");
                externalContext.getFlash().setKeepMessages(true);
                break;
            case SUCCESS:
                externalContext.getFlash().setKeepMessages(true);
                addDetailMessage("Logged in successfully as <b>" + email + "</b>");
                
                //Remueva para obtener el usuario logeado
               // user= (User) JmoordbContext.get("jmoordb_user");
                
                if (remember) {
                    storeCookieCredentials(email, password);
                }
                Faces.redirect(adminConfig.getIndexPage());
                break;
            case NOT_DONE:
                Messages.addError(null, "Login failed");
        }
    }

    private void storeCookieCredentials(final String email, final String password) {
        Faces.addResponseCookie("admin-email", email, 1800);//store for 30min
        Faces.addResponseCookie("admin-pass", password, 1800);//store for 30min
    }

    private AuthenticationStatus continueAuthentication() {
        return securityContext.authenticate((HttpServletRequest) externalContext.getRequest(),
                (HttpServletResponse) externalContext.getResponse(),
                AuthenticationParameters.withParams().rememberMe(remember)
                        .credential(new UsernamePasswordCredential(email, password)));
    }

    @Override
    public boolean isLoggedIn() {
        return securityContext.getCallerPrincipal() != null;
    }

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

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public String getCurrentUser() {
        return securityContext.getCallerPrincipal() != null ? securityContext.getCallerPrincipal().getName() : "";
    }

}