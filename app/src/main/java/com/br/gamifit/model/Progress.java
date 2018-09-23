package com.br.gamifit.model;

public class Progress {
    private int offensiveDays;

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
