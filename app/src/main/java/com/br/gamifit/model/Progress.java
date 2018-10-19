package com.br.gamifit.model;

import java.io.Serializable;

public class Progress implements Serializable{
    private int offensiveDays;
    private CheckInOut checkIn;

    public Progress(){}

    public Progress(int offensiveDays) {
        this.setOffensiveDays(offensiveDays);
    }

    public int getOffensiveDays() {
        return offensiveDays;
    }

    public void setOffensiveDays(int offensiveDays) {
        this.offensiveDays = offensiveDays;
    }

    public CheckInOut getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(CheckInOut checkIn) {
        this.checkIn = checkIn;
    }
}
