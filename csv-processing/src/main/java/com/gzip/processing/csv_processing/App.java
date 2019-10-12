package com.gzip.processing.csv_processing;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.text.StringEscapeUtils;

import com.gzip.processing.fileprocessing.DataProcessing;
import com.gzip.processing.fileprocessing.GzipCSVRetriver;

public class App {
	public static void main(String [] args) {
		if(args.length != 1) {
			System.out.println("Error!!! Please enter a URL for file processing");
			return;
		}
		
		InputStreamReader reader;
		try {
			reader = new InputStreamReader(GzipCSVRetriver.gZipInputStreamFromURL(args[0]));
			//reader = new InputStreamReader(GzipCSVRetriver.GZipInputStreamFromURL(StringEscapeUtils.escapeJava(args[0])));
			
			BufferedReader fileStreamBuffer = new BufferedReader(reader);
			DataProcessing.printDefaultCsvDataSummary(fileStreamBuffer);
			GzipCSVRetriver.closeFileConnection();
			fileStreamBuffer.close();		
		} catch (NullPointerException | IOException e) {
			System.out.println("Error!! Url does not direct to gz(gzip) file location");
		}catch(Exception e) {
			
		}
	}
}