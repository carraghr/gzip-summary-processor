package com.gzip.processing.datastorage;

public class Display {
	
	private int height;
	private int width;
	
	public Display(String width,String height){
		this.width = Integer.parseInt(width);
		this.height = Integer.parseInt(height);
	}
	
	public boolean equal(Display d){
		return this.height==d.height && this.width==d.width;
	}
}

