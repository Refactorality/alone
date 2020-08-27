package com.palehorsestudios.alone;

import junit.framework.TestCase;
import org.junit.Test;

public class FoodTest extends TestCase {

    @Test
    public void testGetCalory() {
        assertEquals(84.0, Food.FISH.getCalory());
        assertEquals(57.0, Food.BERRIES.getCalory());
        assertEquals(102.0, Food.MOOSE.getCalory());
    }
    @Test
    public void testGetGram() {
        assertEquals(100.0, Food.FISH.getGram());
        assertEquals(100.0, Food.BERRIES.getGram());
    }
}