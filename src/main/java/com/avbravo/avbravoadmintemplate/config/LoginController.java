/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.avbravoadmintemplate.config;


import com.avbravo.jmoordb.configuration.JmoordbConfiguration;
import com.avbravo.jmoordb.configuration.JmoordbConnection;
import com.avbravo.jmoordb.configuration.JmoordbContext;
import com.avbravo.jmoordb.mongodb.history.repository.AccessInfoRepository;
import com.avbravo.jmoordb.mongodb.history.services.AutoincrementableServices;
import com.avbravo.jmoordb.mongodb.history.repository.ConfiguracionRepository;
import com.avbravo.jmoordb.mongodb.history.services.ConfiguracionServices;
import com.avbravo.jmoordb.mongodb.history.services.ErrorInfoServices;
import com.avbravo.jmoordb.mongodb.history.repository.RevisionHistoryRepository;
import com.avbravo.jmoordb.mongodb.history.entity.Configuracion;
import com.avbravo.jmoordb.profiles.repository.JmoordbNotificationsRepository;
import com.avbravo.jmoordb.services.AccessInfoServices;
import com.avbravo.jmoordb.services.RevisionHistoryServices;
//import com.avbravo.jmoordbsecurity.SecurityInterface;
import com.avbravo.jmoordbutils.JsfUtil;

import com.avbravo.jmoordbutils.JmoordbResourcesFiles;
import com.avbravo.sigeclclient.entity.Applicative;
import com.avbravo.sigeclclient.entity.Profile;
import com.avbravo.sigeclclient.entity.Role;
import com.avbravo.sigeclclient.entity.User;
import com.avbravo.sigeclclient.services.RoleServices;
import com.avbravo.sigeclclient.services.UserServices;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Named
@SessionScoped
//public class LoginController implements Serializable, SecurityInterface {
public class LoginController implements Serializable{
// <editor-fold defaultstate="collapsed" desc="fields">

    List<ProfilePojo> profilePojosList = new ArrayList<>();
    @Inject
    private SecurityContext securityContext;
    @Inject
    private ExternalContext externalContext;
    @Inject
    private FacesContext facesContext;

    //Atributos para la interface IController
    @Inject
    RevisionHistoryRepository revisionHistoryRepository;
    @Inject
    RevisionHistoryServices revisionHistoryServices;
    @Inject
    AutoincrementableServices autoincrementableServices;
    @Inject
    ConfiguracionRepository configuracionRepository;

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());
    private HashMap<String, String> parameters = new HashMap<>();

    private String passwordold;
    private String passwordnew;
    private String passwordnewrepeat;

    Configuracion configuracion = new Configuracion();
    //Acceso
    @Inject
    AccessInfoServices accessInfoServices;
    @Inject
    AccessInfoRepository accessInfoRepository;
    @Inject
    JmoordbResourcesFiles rf;

    Boolean loggedIn = false;
    private String username;
    private String password;
    private String foto;
    private String id;
    private String key;
    String usernameSelected;
    Boolean recoverSession = false;
    Boolean userwasLoged = false;
    Boolean tokenwassend = false;
    String usernameRecover = "";
    String myemail = "@gmail.com";
    String mytoken = "";
    //Repository
    //Notificaciones
    @Inject
    JmoordbNotificationsRepository jmoordbNotificationsRepository;

    User user = new User();
    Role role = new Role();

    //Services
    @Inject
    RoleServices roleServices;
    @Inject
    ErrorInfoServices errorServices;
    @Inject
    UserServices userServices;
    @Inject
    ConfiguracionServices configuracionServices;

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="getter/setter">

    public List<ProfilePojo> getProfilePojosList() {
        return profilePojosList;
    }

    public void setProfilePojosList(List<ProfilePojo> profilePojosList) {
        this.profilePojosList = profilePojosList;
    }
    
    
    
    
    public Configuracion getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPasswordold() {
        return passwordold;
    }

    public void setPasswordold(String passwordold) {
        this.passwordold = passwordold;
    }

    public String getPasswordnew() {
        return passwordnew;
    }

    public void setPasswordnew(String passwordnew) {
        this.passwordnew = passwordnew;
    }

    public String getPasswordnewrepeat() {
        return passwordnewrepeat;
    }

    public void setPasswordnewrepeat(String passwordnewrepeat) {
        this.passwordnewrepeat = passwordnewrepeat;
    }

    public String getMyemail() {
        return myemail;
    }

    public void setMyemail(String myemail) {
        this.myemail = myemail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Boolean getTokenwassend() {
        return tokenwassend;
    }

    public void setTokenwassend(Boolean tokenwassend) {
        this.tokenwassend = tokenwassend;
    }

    public String getMytoken() {
        return mytoken;
    }

    public void setMytoken(String mytoken) {
        this.mytoken = mytoken;
    }

    public String getUsernameSelected() {
        return usernameSelected;
    }

    public void setUsernameSelected(String usernameSelected) {
        this.usernameSelected = usernameSelected;
    }

    public Boolean getUserwasLoged() {
        return userwasLoged;
    }

    public void setUserwasLoged(Boolean userwasLoged) {
        this.userwasLoged = userwasLoged;
    }
    // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="init">
    @PostConstruct
    public void init() {
        loggedIn = false;
        recoverSession = false;
        userwasLoged = false;
        tokenwassend = false;
        configuracion = new Configuracion();

        //Configuracion de la base de datos
        JmoordbConnection jmc = new JmoordbConnection.Builder()
                .withSecurity(false)
                .withDatabase("elsa")
                .withHost("")
                .withPort(0)
                .withUsername("")
                .withPassword("")
                .build();
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="destroy">
    @PreDestroy
    public void destroy() {
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="constructor">
    public LoginController() {
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="irLogin">
    public String irLogin() {
//        return "/faces/login";
        return "/login";
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="doLogin">
    public String doLogin() {
        try {

        
            
            tokenwassend = false;
            userwasLoged = false;
            loggedIn = true;
            user = new User();
            if (username == null || password == null) {
                JsfUtil.warningMessage(rf.getAppMessage("login.usernamenotvalid"));
                return null;
            }

            //Aqui valida la sesion de otro user
//            usernameRecover = usernameRecoveryOfSession();
//            recoverSession = !usernameRecover.equals("");
//            if (recoverSession) {
//                invalidateCurrentSession();
//                //  RequestContext.getCurrentInstance().execute("PF('sessionDialog').show();");
//                JsfUtil.warningMessage(rf.getAppMessage("session.procederacerrar"));
//                return "";
//            }
//            if (recoverSession && usernameRecover.equals(username)) {
//            } else {
//                if (isUserLogged(username)) {
//                    userwasLoged = true;
//                    JsfUtil.warningMessage(rf.getAppMessage("login.alreadylogged"));
//                    if (destroyByUsername(username)) {
//
//                    }
//                    return "";
//                }
//
//            }
//            if (!isValidSession(username)) {
//                return "";
//            }
            /**
             * Cargando la configuracion
             */
            configuracion = configuracionServices.generarConfiguracionInicial(username);

            //----------------------------------------------
//Agregar al context
            JmoordbConfiguration jmc = new JmoordbConfiguration.Builder()
                    .withSpanish(true)
                    .withRepositoryRevisionHistory(revisionHistoryRepository)
                    .withRevisionHistoryServices(revisionHistoryServices)
                    .withRevisionSave(true)
                    .withUsername(username)
                    .build();

            JmoordbContext.put("jmoordb_user", user);
            //Es un String LOGIN  que se usa para todos los formularios
            JmoordbContext.put("jmoordb_rol", "LOGIN");

//---Injectarlo en el Session
            switch (continueAuthentication()) {
                case SEND_CONTINUE:
                    facesContext.responseComplete();
                    break;
                case SEND_FAILURE:
                    facesContext.addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", null));
                    break;
                case SUCCESS:
                    foto = "img/me.jpg";
                    loggedIn = true;
                    user = (User) JmoordbContext.get("jmoordb_user");

              //      saveUserInSession(username, 2100);
                    accessInfoRepository.save(accessInfoServices.generateAccessInfo(username, "login", rf.getAppMessage("login.welcome")));
                    loggedIn = true;
                    JsfUtil.successMessage(rf.getAppMessage("login.welcome") + " " + user.getName());
                    //Genera los profiles
profilePojosList = generateProfile();
                    //Notificaciones que tiene
//                    Document doc = new Document("username", username).append("viewed", "no");
//                    Integer count = jmoordbNotificationsRepository.count(doc);
             //       JmoordbContext.put("notification_count", count);
                    // Para todos los usuarios el rol sera LOGIN
                    return "/faces/pages/index.xhtml?faces-redirect=true";
                // validadorRoles.validarRoles(rol.getIdrol());
//                    switch (role.getIdrole()) {
//                        case "DOCENTE":
//                            return "/faces/pages/solicituddocente/new.xhtml?faces-redirect=true";
//                        case "ADMINISTRATIVO":
//                            return "/faces/pages/solicitudadministrativo/new.xhtml?faces-redirect=true";
//                        case "COORDINADOR":
//                            return "/faces/pages/coordinador/list.xhtml?faces-redirect=true";
//                        case "ADMINISTRADOR":
//                        case "SUBDIRECTORADMINISTRATIVO":
//                        case "SECRETARIA":
//             
//                        case "CONDUCTOR":
//                            return "/faces/pages/index.xhtml?faces-redirect=true";
//                        default:
//                           JsfUtil.warningDialog(rf.getAppMessage("warning.view"), rf.getMessage("warning.rolnovalidadoenelmenu"));
//                    }

                case NOT_DONE:
            }

            //-----------------------------
            //              return "/dashboard.xhtml?faces-redirect=true";
        } catch (Exception e) {
            errorServices.errorMessage(JsfUtil.nameOfClass(), JsfUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return "";
    }

    // </editor-fold>
    private AuthenticationStatus continueAuthentication() {
        return securityContext.authenticate(
                (HttpServletRequest) externalContext.getRequest(),
                (HttpServletResponse) externalContext.getResponse(),
                AuthenticationParameters.withParams()
                        .credential(new UsernamePasswordCredential(username, password))
        );
    }

//    // <editor-fold defaultstate="collapsed" desc="sendToken()"> 
   public String sendOt() {
//        try {
//
////            if(!myemail.equals("emailuser")){
////                //no es el email del user
////            }
//            ManagerEmail managerEmail = new ManagerEmail();
//            String token = tokenOfUsername(username);
//            if (!token.equals("")) {
//
//                String texto = rf.getAppMessage("token.forinitsession") + " " + token + rf.getAppMessage("token.forinvalidate ");
//                if (managerEmail.send(myemail, rf.getAppMessage("token.tokenofsecurity"), texto, "adminemail@gmail.com", "adminpasswordemail")) {
//                    JsfUtil.successMessage(rf.getAppMessage("token.wassendtoemail"));
//                    tokenwassend = true;
//                } else {
//                    JsfUtil.warningMessage(rf.getAppMessage("token.errortosendemail"));
//                }
//            } else {
//                JsfUtil.warningMessage(rf.getAppMessage("token.asiganedtouser"));
//            }
//
//        } catch (Exception e) {
//            errorServices.errorMessage(JsfUtil.nameOfClass(), JsfUtil.nameOfMethod(), e.getLocalizedMessage(), e);
//        }
        return "";
   }
// </editor-fold>


// <editor-fold defaultstate="collapsed" desc="doLogout">

    public String doLogout() {
        return logout("/elsa/faces/login.xhtml?faces-redirect=true");
    }
        // <editor-fold defaultstate="collapsed" desc="logout">
/**
 * name 
 * @param path
 * @return 
 */
  
    public String logout(String path) {
        Boolean loggedIn = false;
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            if (session != null) {
                session.invalidate();
            }
            String url = (path);
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            ec.redirect(url);
            return path;
        } catch (Exception e) {
        errorServices.errorMessage(JsfUtil.nameOfClass(), JsfUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return path;
    } 
// <editor-fold defaultstate="collapsed" desc="changePassword">
    public String changePassword() {
        try {
            if (passwordold.isEmpty() || passwordold.equals("") || passwordold == null) {
                //password anterior no debe estar vacio
                JsfUtil.warningMessage(rf.getMessage("warning.passwordvacio"));
                return "";
            }
            if (passwordnew.isEmpty() || passwordnew.equals("") || passwordold == null) {
                //password nuevo no debe estar vacio
                JsfUtil.warningMessage(rf.getMessage("warning.passwordnuevovacio"));
                return "";
            }
            if (passwordnewrepeat.isEmpty() || passwordnewrepeat.equals("") || passwordnewrepeat == null) {
                //el password repetido no coincide
                JsfUtil.warningMessage(rf.getMessage("warning.passwordnuevorepetidovacio"));
                return "";
            }
            if (!passwordnew.equals(passwordnewrepeat)) {
                //password nuevo no coincide
                JsfUtil.warningMessage(rf.getMessage("warning.passwordnocoinciden"));
                return "";
            }

            if (!passwordold.equals(JsfUtil.desencriptar(user.getPassword()))) {
                //password anterior no valido
                JsfUtil.warningMessage(rf.getMessage("warning.passwordanteriornoescorrecto"));
                return "";
            }
            if (passwordold.equals(passwordnew)) {
                //esta colocando el password anterior como nuevo
                JsfUtil.warningMessage(rf.getMessage("warning.passwordanteriorigualalnuevo"));
                return "";
            }
            user.setPassword(JsfUtil.encriptar(passwordnew));
            userServices.update(user);
            JsfUtil.successMessage(rf.getAppMessage("info.update"));
        } catch (Exception e) {
            errorServices.errorMessage(JsfUtil.nameOfClass(), JsfUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return null;
    }
    // </editor-fold>
    
    
    
    
     // <editor-fold defaultstate="collapsed" desc="metodo()">
    public List<ProfilePojo> generateProfile() {
      
        List<ProfilePojo> list = new ArrayList<>();
        try {
              User user = (User) JmoordbContext.get("jmoordb_user");
            List<Applicative> applicativeList = new ArrayList<>();

            for (Profile p : user.getProfile()) {
                if (applicativeList.isEmpty()) {
                    applicativeList.add(p.getApplicative());
                } else {
                    Boolean found = false;
                    for (Applicative a : applicativeList) {
                        if (a.getIdapplicative().equals(p.getApplicative().getIdapplicative())) {
                            found = true;
                            break;
                        }

                    }
                    if (!found) {
                        applicativeList.add(p.getApplicative());
                    }
                }
            }

            for (Applicative a : applicativeList) {
                List<Role> roleList = new ArrayList<>();
                ProfilePojo profilePojo = new ProfilePojo();
                profilePojo.setApplicative(a);
                for (Profile p : user.getProfile()) {
                    System.out.println("Searching... "+p.getApplicative().getIdapplicative());
                    if (a.getIdapplicative().equals(p.getApplicative().getIdapplicative())) {
                        roleList.add(p.getRole());
                     
                    }
                }
                profilePojo.setRole(roleList);
                list.add(profilePojo);
            }
            
        } catch (Exception e) {
            JsfUtil.errorMessage("generateProfile " + e.getLocalizedMessage());
        }
      
        return list;
    }

}
