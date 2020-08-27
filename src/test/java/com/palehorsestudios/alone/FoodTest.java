package com.palehorsestudios.alone;

import junit.framework.TestCase;
import org.junit.Test;

public class FoodTest extends TestCase {

    @Test
    public void testGetCalory() {
        assertEquals(0.84, Food.FISH.getCaloriesPerGram());
        assertEquals(0.57, Food.BERRIES.getCaloriesPerGram());
        assertEquals(1.02, Food.MOOSE.getCaloriesPerGram());
    }

}