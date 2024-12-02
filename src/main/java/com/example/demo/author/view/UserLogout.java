package com.example.demo.author.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;

@RequestScoped
@Named
public class UserLogout {

    private HttpServletRequest request;

    @Inject
    public UserLogout(HttpServletRequest request) {
        this.request = request;
    }

    @SneakyThrows
    public String logoutAction() {
        request.logout();
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
