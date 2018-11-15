package com.br.gamifit.model;

import java.io.Serializable;

public class Progress implements Serializable{
    private final int OFFENSIVE_DAY_EXPERIENCE = 200;
    private int offensiveDays;
    private int experiencePoints;
    private int level;
    public Progress(){}

    public Progress(int offensiveDays,int experiencePoints,int level) {
        this.setOffensiveDays(offensiveDays);
        this.setExperiencePoints(experiencePoints);
        this.setLevel(level);
    }

    public int getOffensiveDays() {
        return offensiveDays;
    }

    public void setOffensiveDays(int offensiveDays) {
        this.offensiveDays = offensiveDays;
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void increaseExperiencePointsOnOffensiveDay(){
        this.setLevel(this.getLevel()+OFFENSIVE_DAY_EXPERIENCE);
    }

    public int getLevel() {
        return level;
    }
}
