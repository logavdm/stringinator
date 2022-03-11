package com.comcast.service;

import com.comcast.model.StatsResult;
import com.comcast.model.StringinatorInput;
import com.comcast.model.StringinatorResult;

public interface StringinatorService {

	StringinatorResult stringinate(StringinatorInput input);

	StatsResult stats();

}
