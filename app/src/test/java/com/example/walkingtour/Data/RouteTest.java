package com.example.walkingtour.Data;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by ww3ref on 02/05/15.
 */
public class RouteTest extends TestCase {

    Route route;

    public void setUp() throws Exception {
        super.setUp();
        route = new Route("here","end");

    }

    public void testIfCreated(){
        assertNotNull(route);
    }

    public void testAdditionOfASinglePoint(){
        locations locat = new locations(1,1);
        route.add(locat);
        ArrayList<locations> b = route.getPoints();
        assertTrue(b.size() > 0);
    }

    public void testManyPointsAdded(){
        locations locat = new locations(1,1);
        ArrayList<locations> these = locat.populate();
        route.setPoints(these);
        assertTrue(route.getPoints().size() > 5);
    }
}