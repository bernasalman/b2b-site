package com.example.application.views;

import com.example.application.models.SystemUser;
import com.example.application.services.SystemUserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route
public class LoginView extends VerticalLayout {
    private final SystemUserService systemUserService;

    public LoginView(SystemUserService systemUserService){
        this.systemUserService = systemUserService;
        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(loginEvent -> {

            SystemUser result = systemUserService.login(loginEvent.getUsername(),loginEvent.getPassword());

            if (result.getId()==1)
            {
                VaadinSession.getCurrent().getSession().setAttribute("LoggedInSystemUserId",result.getId());
                UI.getCurrent().getPage().setLocation("/");
            }else
            {
                loginForm.setError(true);
            }
        });

        LoginForm loginForm1 = new LoginForm();
        loginForm1.addLoginListener(loginEvent -> {

            SystemUser result = systemUserService.login(loginEvent.getUsername(),loginEvent.getPassword());

            if (result.getId()!=null)
            {
                VaadinSession.getCurrent().getSession().setAttribute("LoggedInSystemUserId",result.getId());
                UI.getCurrent().getPage().setLocation("/siparis");
            }else
            {
                loginForm1.setError(true);
            }
        });

        SplitLayout layout = new SplitLayout(loginForm,loginForm1);

        layout.addToPrimary(new H1("Yönetici Girişi"),loginForm);
        layout.addToSecondary(new H1("Kullanıcı Girişi"),loginForm1);

        layout.setPrimaryStyle("maxWidth", "300px");
        layout.setPrimaryStyle("maxWidth", "300px");
        layout.setPrimaryStyle("background", "red");


        layout.setSecondaryStyle("maxWidth", "300px");
        layout.setSecondaryStyle("maxWidth", "300px");
        layout.setSecondaryStyle("background", "salmon");


        add(layout);
        setAlignItems(Alignment.CENTER);
}

}
