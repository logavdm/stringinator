package com.comcast.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StringinatorInput {

	@NotBlank(message = "String cannot be Null or Empty")
	private String input;
}
