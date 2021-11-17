package com.tmax.eTest.TestStudio.service;

import java.util.List;
import java.util.stream.Collectors;

import com.tmax.eTest.Common.model.problem.Part;
import com.tmax.eTest.TestStudio.dto.PartListDTO;
import com.tmax.eTest.TestStudio.repository.PartRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PartService {

	private final PartRepository partRepo;
	
	public void create(String name, Integer order, Integer count) {
		order = checkOrder(order);
		partRepo.bulkOrderPlus(order);
		partRepo.save(new Part(name, order, count));
	}

	public List<PartListDTO> read() {
		return partRepo.findAllByOrderByOrderNum().stream().map(PartListDTO::new).collect(Collectors.toList());
	}

	public void update(Integer id, String name, Integer order, Integer count) {
		order = checkOrder(order);
		Integer beforeOrder = partRepo.getById(id).getOrderNum();
		if(beforeOrder > order) {
			partRepo.bulkOrderPlus(order, beforeOrder);
		} else if(beforeOrder < order) {
			partRepo.bulkOrderMinus(order, beforeOrder);
		}
		partRepo.getById(id).updatePart(name, order, count);
	}

	public void delete(Integer id) {
		Integer order = partRepo.getById(id).getOrderNum();
		partRepo.bulkOrderMinus(order);
		partRepo.deleteById(id);
	}
	
	private Integer checkOrder(Integer order) {
		int maxNum = partRepo.findAll().size();
		if(maxNum < order) return maxNum + 1;
		else if(order < 1) return 1;
		else return order;
	}

}