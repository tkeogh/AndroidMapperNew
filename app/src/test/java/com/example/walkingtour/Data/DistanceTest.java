package com.example.walkingtour.Data;

import com.example.walkingtour.R;

import junit.framework.TestCase;
import junit.framework.TestResult;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by ww3ref on 01/05/15.
 */
public class DistanceTest extends TestCase {

    Distance distance;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        distance = new Distance();
    }

    @Test
   public void testDistanceNotNull(){
       assertNotNull(distance);
   }

    public void testReturnOfDistanceFunction(){
        double dist = distance.distanceTo(1,1,2,2);
        assertTrue(dist > 0);
    }

    @Test
    public void testValueIsCloseToCorrect(){
        //cant predict the exact return as issues with algorith, should be close though
        double ret = distance.distanceTo(52.413121, -4.086836,52.413372, -4.069066);
        assertTrue(ret < 1.5 && ret > 1);
    }


}