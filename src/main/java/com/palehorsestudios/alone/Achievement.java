package com.palehorsestudios.alone;

import java.util.Objects;

public class Achievement {
    private String name;
    private String visibleName;
    private int minimum = 2;
    private boolean achieved;


    public Achievement() {
        setAchieved(false);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVisibleName() {
        return visibleName;
    }

    public void setVisibleName(String visibleName) {
        this.visibleName = visibleName;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "visibleName='" + getVisibleName() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Achievement)) return false;
        Achievement that = (Achievement) o;
        return getMinimum() == that.getMinimum() &&
                isAchieved() == that.isAchieved() &&
                getName().equals(that.getName()) &&
                getVisibleName().equals(that.getVisibleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getVisibleName(), getMinimum(), isAchieved());
    }
}
