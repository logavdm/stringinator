package com.comcast.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class StringManipulationOperations {
	
	private static final Logger log=LoggerFactory.getLogger(StringManipulationOperations.class);
	
	@Cacheable("LongestPalindrome")
	public String findLongestPalindrome(String s) {
		log.info("Cached Value not available , Generating Longest Palindrome value for String :: {}",s);
		if (s.isEmpty()) {
			return s;
		}
		if (s.length() == 1) {
			return s;
		}
		String longest = s.substring(0, 1);
		for (int i = 0; i < s.length(); i = i + 1) {
			String tmp = checkForEquality(s, i, i);
			if (tmp.length() > longest.length()) {
				longest = tmp;
			}
			tmp = checkForEquality(s, i, i + 1);
			if (tmp.length() > longest.length()) {
				longest = tmp;
			}
		}
		return longest;
	}

	public String checkForEquality(String s, int begin, int end) {
		while (begin >= 0 && end <= s.length() - 1 && s.charAt(begin) == s.charAt(end)) {
			begin--;
			end++;
		}
		return s.substring(begin + 1, end);
	}
}
