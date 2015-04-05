package com.example.walkingtour;

import java.util.ArrayList;

public class locations {
	
	private String name;
	private String des;
	private String type;
	private double lat;
	private double lon;



    private double dist;
	
	/** Constructor for displaying them in the view buildings activity*/
	
	public locations(String string, String string2,String type, double i, double j) {
		
		this.name = string;
		this.des = string2;
		this.type = type;
		this.lat = i;
		this.lon = j;
	
	}
    /** Constructor for the Help activity*/
    public locations(String string, double dist){
        this.name = string;
        this.dist = dist;
    }

    /** Constructor for route finding just lat lon*/
    public locations(double lat, double lon){
        this.lat =lat;
        this.lon =lon;
    }



	public locations() {
		// TODO Auto-generated constructor stub
	}



	public ArrayList<locations> populate() {
		
		ArrayList<locations> info = new ArrayList<locations>();
		
		locations a = new locations("Edward Lloyd","Lecture Theatres","Lecture",52.4156455,-4.0664103);
		info.add(a);
		locations b = new locations("IBERS","Lecture Theatres","Lecture",52.4163857,-4.0668202);
		info.add(b);
		locations c = new locations("School Of Business and Management","Lecture Theatres","Lecture",52.4171251,-4.0672997);
		info.add(c);
		locations d = new locations("Pantacelyn","Welsh Speaking Accomodation","Accomodation",52.4164723,-4.0693913);
		info.add(d);
		locations e = new locations("Institute of Maths and Physics","Lecture Theatres","Lecture",52.4159151,-4.0655077);
		info.add(e);
		locations f = new locations("Llandinam","Lecture Theatres","Lecture",52.4164982,-4.0663893);
		info.add(f);
		locations g = new locations("Students Union","Home of SU","Facilities",52.4151517,-4.0630464);
		info.add(g);
		locations h = new locations("Arts Centre","Contains Great Hall","Facilities",52.4156345,-4.0628907);
		info.add(h);
		locations i = new locations("Hugh Owen Library","Books","Facilties",52.4158165,-4.0636652);
		info.add(i);
		locations j = new locations("Hugh Owen Building","Lecture Theatre","Lecture",52.4166614,-4.0641835);
		info.add(j);
		locations k = new locations("Department of Theatre","Also Film/Drama","Lecture",52.416998,-4.0626962);
		info.add(k);
		locations l = new locations("Sports Centre","Gym etc","Facilties",52.4143772,-4.0662023);
		info.add(l);
		locations m = new locations("National Library","More Books","Facilities",52.4143308,-4.067746);
		info.add(m);
		locations n = new locations("Cwrt Mawr","Blocks","Accomodation",52.4169516,-4.061601);
		info.add(n);
		locations o = new locations("Rosser Hall","Blocks","Accomodation",52.417708,-4.0609177);
		info.add(o);
		locations p = new locations("Llanbadarn","Lecture place","Lecture",52.4103625,-4.0521911);
		info.add(p);
		locations q = new locations("PJM","Blocks","Accomodation",52.4202346,-4.062425);
		info.add(q);
		locations r = new locations("Train Station","Trains","Facilities",52.4140782,-4.0818546);
		info.add(r);
		
		
		
		return info;
		
		
		
		
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getDes() {
		return des;
	}



	public void setDes(String des) {
		this.des = des;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public double getLat() {
		return lat;
	}



	public void setLat(double lat) {
		this.lat = lat;
	}



	public double getLon() {
		return lon;
	}



	public void setLon(double lon) {
		this.lon = lon;
	}

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }
	
	
	

}
