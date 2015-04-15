package com.example.walkingtour.Data;

import com.example.walkingtour.Data.locations;

import java.util.ArrayList;

public class Route {
	
	private String from;
	private String to;



    private String difficulty;
	private ArrayList<locations> points;
	
	public ArrayList<locations> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<locations> points) {
		this.points = points;
	}

	public Route(){
		
		
	}
	
	public Route(String from,String to){
		this.setFrom(from);
		this.setTo(to);
		points = new ArrayList<locations>();
	}
	
	private void add(locations p){
		
		points.add(p);
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

}
