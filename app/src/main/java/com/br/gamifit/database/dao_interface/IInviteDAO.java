package com.br.gamifit.database.dao_interface;

import com.br.gamifit.model.GymInvite;

public interface IInviteDAO {

    boolean createInvite(GymInvite invite);
    void getUserInvites(String code);
    void acceptInvite();
}
