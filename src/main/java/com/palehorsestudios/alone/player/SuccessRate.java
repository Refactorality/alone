package com.palehorsestudios.alone.player;

public enum SuccessRate {
    LOW(1),
    MEDIAN(2),
    HIGH(3);

    private final int rate;

    public double getRate() {
        return this.rate;
    }

    private SuccessRate(int rate) {
        this.rate = rate;
    }
}
