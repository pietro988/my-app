package com.example.application.views.masterdetail;

import com.example.application.data.service.LoginService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;


@Route(value = "login")
@PageTitle("Login")
@RouteAlias("")

public class LoginView extends VerticalLayout {

    public LoginView(LoginService loginService) {

        setId("login-view");

        TextField username = new TextField("Username");
        var password = new PasswordField("Password");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(new H1("Benvenuto"), username, password, new Button("Login", event -> {

            try {

                loginService.authenticate(username.getValue(), password.getValue());

                UI.getCurrent().navigate("misurazioni");

            } catch (LoginService.AuthException e) {

                Notification.show("Wrong credentials.");

            }

        })

        );

    }
}