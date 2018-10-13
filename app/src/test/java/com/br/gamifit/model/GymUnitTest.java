package com.br.gamifit.model;

import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.InviteFirebaseDAO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.AtLeast;
import org.mockito.junit.MockitoJUnit;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest({FirebaseFactory.class,FirebaseDatabase.class})
public class GymUnitTest {

    @Test
    public void createInviteTest(){
        Gym gym = new Gym("Fitness Everyhere","f9bfp43");
        User user = mock(User.class);
        when(user.getName()).thenReturn("Matheus Gomes");
        when(user.getEmail()).thenReturn("msau@gmal.com");
        when(user.getPassword()).thenReturn("nnfo5tnb");

        GymInvite expectedInvite = mock(GymInvite.class);
        when(expectedInvite.getGym()).thenReturn(gym);
        when(expectedInvite.getUser()).thenReturn(user);

        GymInvite createdInvite = gym.createInviteToJoin(user);

        assertEquals(expectedInvite.getGym(),createdInvite.getGym());
        assertEquals(expectedInvite.getUser(),createdInvite.getUser());
    }

    @Test
    public void sendInviteToJoinTest(){
        Gym gym = new Gym("Fitness Everyhere","f9bfp43");

        User user = mock(User.class);
        when(user.getName()).thenReturn("Matheus Gomes");
        when(user.getEmail()).thenReturn("matheusdin04@gmail.com");
        when(user.getPassword()).thenReturn("msabd9334");

        GymInvite gymInvite = gym.createInviteToJoin(user);

        InviteFirebaseDAO inviteFirebaseDAO = mock(InviteFirebaseDAO.class);
        FirebaseDatabase firebaseDatabase = mock(FirebaseDatabase.class);
        DatabaseReference databaseReference = mock(DatabaseReference.class);

        PowerMockito.mockStatic(FirebaseFactory.class);

        when(FirebaseFactory.getInviteFirebaseDAO()).thenReturn(inviteFirebaseDAO);
        when(firebaseDatabase.getReference()).thenReturn(databaseReference);
        when(databaseReference.child("invite")).thenReturn(databaseReference);
        when(databaseReference.push()).thenReturn(databaseReference);
        when(databaseReference.getKey()).thenReturn("-adnV9YSYFo3");

        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(firebaseDatabase);

        when(inviteFirebaseDAO.createInvite(gymInvite)).thenReturn(true);

        boolean result = gym.sendInviteToJoin(user);

        verify(inviteFirebaseDAO,atLeast(1)).createInvite(gymInvite);
        assertEquals(true,result);

    }

}
