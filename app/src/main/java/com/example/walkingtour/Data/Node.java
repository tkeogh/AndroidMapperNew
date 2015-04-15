package com.example.walkingtour.Data;

/**
 * Created by ww3ref on 27/03/15.
 */
public class Node {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    String from;

    public Node(String from,String name){
        this.from = from;
        this.name = name;
    }
}
