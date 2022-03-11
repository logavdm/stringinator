package com.comcast.model;

import java.util.Comparator;
import java.util.Map;

import lombok.Getter;

@Getter
public class StatsResult {

	private final Map<String, Integer> inputs;

	private final String most_popular;

	private final String longest_input_received;

	public StatsResult(Map<String, Integer> inputs) {
		this.inputs = inputs;

		longest_input_received = inputs.keySet().stream().max(Comparator.comparingInt(String::length)).orElse("");

		most_popular = inputs.entrySet().stream().max(Comparator.comparingInt(entry -> entry.getValue()))
				.map(Map.Entry::getKey).orElse("");

	}

}
