package com.comcast.serviceImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comcast.model.StatsResult;
import com.comcast.model.StringinatorInput;
import com.comcast.model.StringinatorResult;
import com.comcast.service.StringManipulationOperations;
import com.comcast.service.StringinatorService;

@Service
public class StringinatorServiceImpl implements StringinatorService {

	private Map<String, Integer> seenStrings = new ConcurrentHashMap<>();
	
	@Autowired
	private StringManipulationOperations StringOperations;

	@Override
	public synchronized StringinatorResult stringinate(StringinatorInput input) {
		seenStrings.compute(input.getInput(), (k, v) -> (v == null) ? Integer.valueOf(1) : v + 1);
		StringinatorResult result = new StringinatorResult(input.getInput(),Integer.valueOf(input.getInput().length()),StringOperations);
		return result;
	}

	@Override
	public StatsResult stats() {
		return new StatsResult(seenStrings);
	}
}
