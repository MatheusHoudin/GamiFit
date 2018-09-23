package com.br.gamifit.model;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GymUnitTest {

    @Test
    public void createInviteTest(){
        Gym gym = new Gym("Fitness Everyhere","f9bfp43");
        User user = mock(User.class);
        when(user.getName()).thenReturn("Matheus Gomes");
        when(user.getEmail()).thenReturn("msau@gmail.com");
        when(user.getPassword()).thenReturn("nnfo5tnb");

        GymInvite expectedInvite = mock(GymInvite.class);
        when(expectedInvite.getGym()).thenReturn(gym);
        when(expectedInvite.getUser()).thenReturn(user);

        assertEquals(expectedInvite,gym.createInviteToJoin(user));
    }


}
