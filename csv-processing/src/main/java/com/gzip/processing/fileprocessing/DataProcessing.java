package com.gzip.processing.fileprocessing;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

import com.gzip.processing.datastorage.Display;
import com.gzip.processing.datastorage.Record;

public class DataProcessing {
	/* Constants for columns in records */
	private final static String USER_ID = "user_id";
	private final static String DATE_JOINED = "date_joined";
	private final static String SPEND = "spend";
	private final static String MILLISECONDS_PLAYED = "milliseconds_played";
	private final static String DEVICE_HEIGHT = "device_height";
	private final static String DEVICE_WIDTH = "device_width";
	
	/*Map index for look up*/
	private static HashMap<String, Integer> getHeaderIdIndexs(String[] headerIds){
		HashMap<String,Integer> idIndexs = new HashMap<String, Integer>();
		for(int i=0;i<headerIds.length;i++) {
			idIndexs.put(headerIds[i],i);
		}
		return idIndexs;
	}
	
	/*Ensure constants are maped with an index for look up*/
	private static boolean requiredIdsAvailable(HashMap<String, Integer> idIndexs){
		boolean isAvailable = true;
		isAvailable = isAvailable && idIndexs.containsKey(USER_ID);
		isAvailable = isAvailable && idIndexs.containsKey(DATE_JOINED);
		isAvailable = isAvailable && idIndexs.containsKey(SPEND);
		isAvailable = isAvailable && idIndexs.containsKey(MILLISECONDS_PLAYED);
		isAvailable = isAvailable && idIndexs.containsKey(DEVICE_HEIGHT);
		isAvailable = isAvailable && idIndexs.containsKey(DEVICE_WIDTH);
		return isAvailable;
	}
	
	/*reformat time from CSV for data structure*/
	private static String convertTimeFormat(String dateTime) {
		return dateTime.replace("+00:00",".0");
	}
	
	/*generate user object from record*/
	private static Record genarateUserRecord(String[] userRecord, HashMap<String,Integer> titleIndexs){
		return new Record(userRecord[titleIndexs.get(USER_ID)],convertTimeFormat(userRecord[titleIndexs.get(DATE_JOINED)]),
				userRecord[titleIndexs.get(SPEND)],userRecord[titleIndexs.get(MILLISECONDS_PLAYED)],
				userRecord[titleIndexs.get(DEVICE_HEIGHT)],userRecord[titleIndexs.get(DEVICE_WIDTH)]);
	}	
	
	/*Summary of data requested for assessment*/
	public static void printDefaultCsvDataSummary(BufferedReader fileInputStream) {
		
		String row;
		Display requiredDisplay = new Display("640","960");
		
		try {
			HashMap<String,Integer> idIndexs;
			if((row = fileInputStream.readLine()) != null) {
				
				//row = StringEscapeUtils.unescapeHtml4(row);
				idIndexs = getHeaderIdIndexs(row.split(","));
				
				if(!requiredIdsAvailable(idIndexs)) {
					System.out.println("File does not contain nesscarry data");
					return;
				}
				
				long matchingDisplayCount = 0;
				long recordCount = 0;
				double totalSpender = 0.0; 
				Record oldestRegisterdUser = null;
				
				//assign first record as oldest user
				if((row = fileInputStream.readLine()) == null) {
					System.out.println("File Does not contain any records");
					return;
				}else {
					//row = StringEscapeUtils.unescapeHtml4(row);
					
					String [] record = row.split(",");
					oldestRegisterdUser = genarateUserRecord(record, idIndexs);
					
					recordCount++;
				}
				
				//process all remaining user records in file
				while ((row = fileInputStream.readLine()) != null) {
					
					//row = StringEscapeUtils.unescapeHtml4(row);
					String [] record = row.split(",");
					
					if(oldestRegisterdUser.joinedBefore(LocalDateTime.parse(convertTimeFormat(record[idIndexs.get(DATE_JOINED)])))) {
						oldestRegisterdUser = genarateUserRecord(record, idIndexs);
					}
					
					if(requiredDisplay.equal(new Display(record[idIndexs.get(DEVICE_WIDTH)], record[idIndexs.get(DEVICE_HEIGHT)]))){
						matchingDisplayCount++;
					}
					
					totalSpender+=Double.parseDouble(record[idIndexs.get(SPEND)]);
					recordCount++;
				}
				
				//print summary of records
				System.out.println(recordCount);
				System.out.println(matchingDisplayCount);
				System.out.println(totalSpender);
				System.out.println(oldestRegisterdUser);
			}
		}catch (IOException|NullPointerException e) {
			System.out.println("Error in processing file, could not maintain link!");
		}catch(NumberFormatException e) {
			System.out.println("Error in processing file! Record does not contain data in all fields");
		}catch(Exception e) {
			System.out.println("Error in processing file!");
		}
	}
}
