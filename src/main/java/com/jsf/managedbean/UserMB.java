package com.jsf.managedbean;


import com.jsf.dao.UserDAO;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "UserMB")
@SessionScoped
public class UserMB {

    private String email;
    private String password;
    private String userName;


    public UserMB() {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String signIn(UserMB userMB) {
        boolean succesUser = UserDAO.signIn(email, password);
        if (succesUser) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            session.setAttribute("email", email);
            return "main";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Email and Passowrd",
                            "Please enter correct Email and Password"));
            return "login";
        }
    }

    public String registration() {
        return "registration";
    }

    public String createUser(UserMB userMB) {
        boolean succesNewUser = UserDAO.createNewUser(userMB);
        if (succesNewUser) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Successfully completed",
                            "Successfully completed"));
            return "login";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Not successful",
                            "Not successful"));
            return "registration";
        }
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();
        return "login";
    }
}
