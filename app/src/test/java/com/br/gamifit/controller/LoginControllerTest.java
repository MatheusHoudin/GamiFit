package com.br.gamifit.controller;

import com.br.gamifit.activity.LoginActivity;
import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.UserFirebaseDAO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginControllerTest {
    @Mock
    private UserFirebaseDAO dao;
    @Mock
    FirebaseAuth auth;
    @Mock
    FirebaseUser user;
    @Mock
    LoginActivity view;

    @Test
    public void checkUserAlreadyLoggedInTest(){
        MockitoAnnotations.initMocks(this);


        when(auth.getCurrentUser()).thenReturn(user);
        when(FirebaseFactory.getUserFirebaseDAO()).thenReturn(dao);

        LoginController controller = LoginController.getLoginController(null);

        controller.checkUserAlreadyLoggedIn();

        verify(view,atLeast(1)).openDashboardActivity();
    }
}
