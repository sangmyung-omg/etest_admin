package com.tmax.eTest.TestStudio.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmax.eTest.Common.repository.uk.UkDescriptionVersionRepo;
import com.tmax.eTest.KdbStudio.util.UkVersionManager;
import com.tmax.eTest.TestStudio.dto.UKListDTO;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UKController {

	private final UkDescriptionVersionRepo ukDescriptionVersionRepo;
	private final UkVersionManager ukVersionManager;

	@GetMapping("test-studio/uk")
	public ResponseEntity<List<UKListDTO>> UKList() {
		return new ResponseEntity<>(ukDescriptionVersionRepo.findByVersionIdOrderByUkId(ukVersionManager.getCurrentUkVersionId()).stream().map(UKListDTO::new).collect(Collectors.toList()), HttpStatus.OK);
	}

}