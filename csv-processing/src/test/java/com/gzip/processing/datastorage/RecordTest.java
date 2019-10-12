package com.gzip.processing.datastorage;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.junit.Test;

public class RecordTest {

	@Test()
	public void constructorTest() {
		Record record = new Record("da27c392de83933dbde844d580e9fa60","2015-11-25T01:29:14.0","820","57698","1136","640");
		assertEquals(record.toString(),"da27c392de83933dbde844d580e9fa60");
	}
	
	
	@Test
	public void constructorExceptionTest() {
		try {
			Record record = new Record("da27c392de83933dbde844d580e9fa60","2015-11-25T01:29:14+00:00","820","57698","1136","640");
			fail("Expected exception");
		}catch(DateTimeParseException e) {
			
		}
	}
	
	@Test
	public void joinedBeforeTest() {
		Record record = new Record("da27c392de83933dbde844d580e9fa60","2015-11-25T01:29:14.0","820","57698","1136","640");
		
		boolean result = record.joinedBefore(LocalDateTime.parse("2015-11-25T00:29:14.0"));
	
		assertTrue(result);
	}
	@Test
	public void joinedBeforeFailTest() {
		Record record = new Record("da27c392de83933dbde844d580e9fa60","2015-10-25T01:29:14.0","820","57698","1136","640");
		
		boolean result = record.joinedBefore(LocalDateTime.parse("2015-11-25T02:29:14.0"));
	
		assertFalse(result);
	}
}
