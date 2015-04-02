package com.example.walkingtour;

import java.util.ArrayList;

public class Route {
	
	private String from;
	private String to;



    private String difficulty;
	private ArrayList<pointof> points;
	
	public ArrayList<pointof> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<pointof> points) {
		this.points = points;
	}

	public Route(){
		
		
	}
	
	public Route(String from,String to){
		this.setFrom(from);
		this.setTo(to);
		points = new ArrayList<pointof>();
	}
	
	private void add(pointof p){
		
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
