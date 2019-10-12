package com.gzip.processing.fileprocessing;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import org.junit.Test;

public class GzipCSVRetriverTest {

	private final String NORMAL_FILE = "src/test/resources/Normal.csv.gz";
	private final String TEXT_FILE = "src/test/resources/Text.txt";
	
	private final String URL_NORMAL_LOCATION = "https://s3.amazonaws.com/swrve-public/full_stack_programming_test/test_data.csv.gz";
	private final String URL_GOOGLE_LOCATION = "https://www.google.com/";
	
	@Test
	public void urlFileSize() {
		long size = 0;
		try {
			size = GzipCSVRetriver.gZipFileSize(URL_NORMAL_LOCATION);
		}catch (Exception e) {
			fail("File unable to be downloaded!");
		}
		assertEquals(size,3510l);
		
	}
	
	@Test
	public void localFileLocation(){
		GZIPInputStream inputStream = GzipCSVRetriver.GZipInputStreamFromLocal(NORMAL_FILE);
		assertNotNull(inputStream);
	}
	
	@Test
	public void localFileDownload(){
		String fileName = URL_NORMAL_LOCATION.substring(URL_NORMAL_LOCATION.lastIndexOf("/"));
		try {
			GzipCSVRetriver.gZipDownloadFromURL(URL_NORMAL_LOCATION,fileName);
		}catch (Exception e) {
			fail("File unable to be downloaded!");
		}
		
		File file = new File(fileName);
		file.deleteOnExit();
		
		boolean fileExists = file.exists();
		assertTrue(fileExists);
	}
	
	@Test
	public void localFileLocationFail() {
		GZIPInputStream inputStream = GzipCSVRetriver.GZipInputStreamFromLocal(TEXT_FILE);
		assertNull(inputStream);
	}
	
	@Test
	public void urlFileLocation(){
		GZIPInputStream inputStream;
		try {
			inputStream = GzipCSVRetriver.gZipInputStreamFromURL(URL_NORMAL_LOCATION);
			assertNotNull(inputStream);
		} catch (NullPointerException | IOException e){
			fail("Expected exception");
		}
	}
	
	@Test
	public void urlFileLocationFail() {
		GZIPInputStream inputStream;
		try {
			inputStream = GzipCSVRetriver.gZipInputStreamFromURL(URL_GOOGLE_LOCATION);
			fail("Expected exception");
		}catch (NullPointerException | IOException e){}
	}
	
	@Test
	public void closeFileConnection() {
		GZIPInputStream inputStream = GzipCSVRetriver.GZipInputStreamFromLocal(NORMAL_FILE);
		GzipCSVRetriver.closeFileConnection();
	}
}
