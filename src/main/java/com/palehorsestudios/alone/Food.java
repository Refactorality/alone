package com.palehorsestudios.alone;

public enum Food {
    FISH(84, 100),
    SQUIRREL(120, 100),
    RABBIT(136, 100),
    MOOSE(102, 100),
    SNAKE(93, 100),
    BUG(500, 100),
    MUSHROOM(28, 100),
    BERRIES(57, 100);

    private final double calory;
    private final double gram;

    public double getCalory() {
        return this.calory;
    }

    public double getGram() {
        return this.gram;
    }

    private Food(double calory, double gram) {
        this.calory = calory;
        this.gram = gram;
    }

}
