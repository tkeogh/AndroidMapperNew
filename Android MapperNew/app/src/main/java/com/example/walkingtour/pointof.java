package com.example.walkingtour;

import android.graphics.Bitmap;
/**
 * 
 * essentially a marker but made so it can be manipulated much easier. All methods just getters and setters
 * @author ww3ref
 *
 */
public class pointof {

	private String name;
	private String description;
	
	public pointof(){
		
		
	}
	
	
	//Seperate constructor for help page.
	public pointof(String name, double dist){
		
		this.name = name;
		this.dist = dist;
	}
	
	public pointof(double lat,double lng){
		this.lat=lat;
		this.lng=lng;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String test) {
		this.description = test;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public Bitmap getImg() {
		return img;
	}
	public void setImg(Bitmap img) {
		this.img = img;
	}
	public double getDist() {
		return dist;
	}

	public void setDist(double dist) {
		this.dist = dist;
	}
	private double lat;
	private double lng;
	private Bitmap img;
	private double dist;




}
