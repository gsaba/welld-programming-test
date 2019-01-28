package com.welld.programming.test.pointsonaline.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.welld.programming.test.pointsonaline.manager.PointManager;
import com.welld.programming.test.pointsonaline.model.Point;
import com.welld.programming.test.pointsonaline.model.PointsOrder;


@RestController
public class PointService {
	
	@Autowired
	private PointManager pointManager;
	
	@PostMapping("/point")
	public void addPoint(@RequestBody Point point) throws IOException {
		pointManager.addPoint(point);
	}
	
	@GetMapping("/points")
	public List<Point> getAll(@RequestParam(name= "order", required = false) PointsOrder order) {
		return pointManager.getAll(order);
	}
	
	@DeleteMapping("/points")
	public void deleteAll() {
		pointManager.deleteAll();
	}
	
	@GetMapping("/neighbours/{point}")
	public List<Point> getNeighbours(@PathVariable("point") Double point, @RequestParam(name= "k", required = false) Integer k) throws IOException {
		return pointManager.getNeighbours(point, k);
	}
}
