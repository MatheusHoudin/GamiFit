package com.br.gamifit.model;

import java.io.Serializable;

public class Progress implements Serializable{
    private int offensiveDays;

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
}
