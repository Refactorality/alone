package com.palehorsestudios.alone;

import junit.framework.TestCase;
import org.junit.Test;

public class FoodTest extends TestCase {

    @Test
    public void testGetCalory() {
        assertEquals(0.84, GameAssets.gameFoods.get("FISH").getCaloriesPerGram());
        assertEquals(0.57, GameAssets.gameFoods.get("BERRIES").getCaloriesPerGram());
        assertEquals(1.02, GameAssets.gameFoods.get("MOOSE").getCaloriesPerGram());
    }

}