package com.comcast;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.comcast.service.StringManipulationOperations;


@SpringBootTest
public class StringManipulationTest {
	
	@Autowired
	private StringManipulationOperations stringOperation;
	
	
	@Test
	public void testRun() {		
		assertEquals("d", stringOperation.findLongestPalindrome("demo"));
	}

}
