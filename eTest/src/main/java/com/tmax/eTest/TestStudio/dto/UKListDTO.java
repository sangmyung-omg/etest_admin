package com.tmax.eTest.TestStudio.dto;

import com.tmax.eTest.Common.model.uk.UkDescriptionVersion;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UKListDTO {
	private Long id;
	private String name;
	
	public UKListDTO(UkDescriptionVersion u) {
		this.id = u.getUkId();
		this.name = u.getUkName();
	}
}