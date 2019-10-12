package com.gzip.processing.datastorage;

import java.time.LocalDateTime;

import com.gzip.processing.datastorage.Display;

public class Record {
	private String id;
	private LocalDateTime dateJoined;
	private double spend;
	private long millisecondsPlayed;
	
	private Display deviceDisplay;
	
	
	public Record(String id, String dateJoined, String spend, String millisecondsPlayed, String deviceHeight, String deviceWidth){
		this.id = id;
		this.dateJoined = LocalDateTime.parse(dateJoined);
		this.spend = Double.parseDouble(spend);
		this.millisecondsPlayed = Long.parseLong(millisecondsPlayed);
		this.deviceDisplay = new Display(deviceWidth, deviceHeight);
	}
	
	public boolean joinedBefore(LocalDateTime datetime) {
		return this.dateJoined.compareTo(datetime) > 0;
	}
	
	public String toString() {
		return id;
	}
}
