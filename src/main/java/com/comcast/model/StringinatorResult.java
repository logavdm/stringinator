package com.comcast.model;

import com.comcast.service.StringManipulationOperations;

import lombok.Getter;

@Getter
public class StringinatorResult {

	private final String input;
	private final Integer length;
	private final String longest_palindrome;	
	private final boolean palindrome;


	public StringinatorResult(String input, Integer length,StringManipulationOperations stringOperation) {
		this.input = input;
		this.length = length;
		this.palindrome=input.equals(new StringBuilder(input).reverse().toString())?true:false;	
		this.longest_palindrome=stringOperation.findLongestPalindrome(input);
	}
}
