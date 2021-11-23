package com.tmax.eTest.TestStudio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tmax.eTest.Common.model.problem.Part;

public interface PartRepository extends JpaRepository<Part, Integer> {
	
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Part p set p.orderNum = p.orderNum + 1 where p.orderNum >= :order")
	int bulkOrderPlus(@Param("order") Integer order);
	
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Part p set p.orderNum = p.orderNum + 1 where p.orderNum >= :order and p.orderNum < :beforeOrder")
	int bulkOrderPlus(@Param("order") Integer order, @Param("beforeOrder") Integer beforeOrder);
	
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Part p set p.orderNum = p.orderNum - 1 where p.orderNum > :order")
	int bulkOrderMinus(@Param("order") Integer order);
	
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Part p set p.orderNum = p.orderNum - 1 where p.orderNum <= :order and p.orderNum > :beforeOrder")
	int bulkOrderMinus(@Param("order") Integer order, @Param("beforeOrder") Integer beforeOrder);
	
	List<Part> findAllByOrderByOrderNum();
}