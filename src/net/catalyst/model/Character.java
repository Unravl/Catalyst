package net.catalyst.model;

import java.io.Serializable;


public class Character implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private Point location;
	
	public Character() {

	}
	

	public void setUsername(String username) {
		this.username = username.toLowerCase();
	}

	public String getName() {
		return username;
	}



	public void setLocation(Point location) {
		this.location = location;
	}

	public Point getLocation() {
		return location;
	}
	


}
