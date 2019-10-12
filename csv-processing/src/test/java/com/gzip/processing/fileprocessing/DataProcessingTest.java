package com.gzip.processing.fileprocessing;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.zip.GZIPInputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DataProcessingTest {
	
	
	//File locations for testing
	private final String NORMAL_FILE = "src/test/resources/Normal.csv.gz";
	private final String ID_MISSING_FILE = "src/test/resources/IdMissing.csv.gz";
	private final String RECORD_MISSING_FILE = "src/test/resources/RecordMissing.csv.gz";
	private final String EMPTY_FILE = "src/test/resources/Empty.csv.gz";
	
	private final String URL_NORMAL_LOCATION = "REDACTED";
	
	//Output streams
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	
	
	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
	}
	
	@After
	public void restoreStreams() {
		System.setOut(originalOut);
	}
	
	@Test
	public void testDownloadedFile() {
		
		String fileName = URL_NORMAL_LOCATION.substring(URL_NORMAL_LOCATION.lastIndexOf("/"));
		try {
			GzipCSVRetriver.gZipDownloadFromURL(URL_NORMAL_LOCATION,fileName);
			File file = new File(fileName);
			file.deleteOnExit();

			InputStreamReader inputStream = new InputStreamReader(GzipCSVRetriver.GZipInputStreamFromLocal(file.getAbsolutePath()));			
			BufferedReader in3 = new BufferedReader(inputStream);

			DataProcessing.printDefaultCsvDataSummary(in3);
			GzipCSVRetriver.closeFileConnection();
			
			assertEquals("100\r\n28\r\n50801.0\r\na888a1c57cf6af2ffee687bfdd7dc4c5\r\n", outContent.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Unable to download file");
		}
	}
	
	@Test
	public void normalFile() {
		InputStreamReader inputStream = new InputStreamReader(GzipCSVRetriver.GZipInputStreamFromLocal(NORMAL_FILE));
		BufferedReader in2 = new BufferedReader(inputStream);
		DataProcessing.printDefaultCsvDataSummary(in2);
		GzipCSVRetriver.closeFileConnection();
		
		assertEquals("100\r\n28\r\n50801.0\r\na888a1c57cf6af2ffee687bfdd7dc4c5\r\n", outContent.toString());
	}
	
	@Test
	public void idMissingFile() {

		InputStreamReader inputStream = new InputStreamReader(GzipCSVRetriver.GZipInputStreamFromLocal(ID_MISSING_FILE));
		BufferedReader in2 = new BufferedReader(inputStream);
		DataProcessing.printDefaultCsvDataSummary(in2);
		GzipCSVRetriver.closeFileConnection();
		
		assertEquals("File does not contain nesscarry data\r\n", outContent.toString());
	}
	
	@Test
	public void recordMissingFile() {

		InputStreamReader inputStream = new InputStreamReader(GzipCSVRetriver.GZipInputStreamFromLocal(RECORD_MISSING_FILE));
		BufferedReader in2 = new BufferedReader(inputStream);
		DataProcessing.printDefaultCsvDataSummary(in2);
		GzipCSVRetriver.closeFileConnection();
		
		assertEquals("Error in processing file! Record does not contain data in all fields\r\n", outContent.toString());
	}
	
	@Test
	public void emptyFile() {

		InputStreamReader inputStream = new InputStreamReader(GzipCSVRetriver.GZipInputStreamFromLocal(EMPTY_FILE));
		BufferedReader in2 = new BufferedReader(inputStream);
		DataProcessing.printDefaultCsvDataSummary(in2);
		GzipCSVRetriver.closeFileConnection();
		
		assertEquals("File does not contain nesscarry data\r\n", outContent.toString());
	}
	
	@Test
	public void nullBufferedReader() {

		BufferedReader in2 = null;
		DataProcessing.printDefaultCsvDataSummary(in2);
		GzipCSVRetriver.closeFileConnection();
		
		assertEquals("Error in processing file, could not maintain link!\r\n", outContent.toString());
	}
}
