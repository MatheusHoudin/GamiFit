package com.br.gamifit.model;

import java.io.Serializable;

public class FriendshipInvite implements Serializable {
    private User user;
    private User userToInvite;
    private boolean active;

}
