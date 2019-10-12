package com.gzip.processing.datastorage;

import static org.junit.Assert.*;

import org.junit.Test;


public class DisplayTest {

	@Test
	public void constructorTest() {
		Display display = new Display("640","960");
	}
	
	
	@Test(expected = NumberFormatException.class)
	public void constructorExceptionTest() {
		Display display = new Display("hello","world!");
	}
	
	@Test
	public void equalsTest() {
		Display display = new Display("640","960");
		Display compareAble = new Display("640","960");
		
		boolean result = display.equal(compareAble);
		
		assertTrue(result);
	}
	
	@Test
	public void equalsFailTest() {
		Display display = new Display("640","960");
		Display compareAble = new Display("960","640");
		
		boolean result = display.equal(compareAble);
		
		assertFalse(result);
	}

}
