package com.example.walkingtour.Data;

import junit.framework.TestCase;

import org.junit.Before;

import java.util.ArrayList;

/**
 * Created by ww3ref on 02/05/15.
 */
public class locationsTest extends TestCase {

    locations locat;

    @Before
    public void setUp() throws Exception {
        super.setUp();


    }

    public void testCreationForHelpActivity(){
        locat = new locations("Building",7);
        double b = locat.getDist();
        assertNotNull(locat);
        assertTrue(b == 7);
    }

    public void testCreationForRouteObject(){
        locat = new locations(5,5);
        assertTrue(locat.getLat() == 5);
        assertTrue(locat.getLon() == 5);
    }

    public void testCreationForBuildingDisplay(){
        locat = new locations("building","this is a building","lecture",1,1);
        assertTrue(locat.getName().equals("building"));
        assertTrue(locat.getDes().equals("this is a building"));
        assertTrue(locat.getType().equals("lecture"));

    }

    public void testHardCodedValuesForDevelopmentAreCreated(){
        locat = new locations(1,1);
        ArrayList<locations> these = locat.populate();
        assertTrue(these.size() > 0);
        assertNotNull(these);
    }

    public void testValuesWithinAreCreatedCorrectly(){
        locat = new locations(1,1);
        ArrayList<locations> these = locat.populate();
        assertTrue(these.get(0).getName().equals("Edward Llwyd"));
        assertTrue(these.get(17).getName().equals("Train Station"));
    }


}