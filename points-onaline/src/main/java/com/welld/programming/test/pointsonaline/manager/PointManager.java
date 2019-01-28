package com.welld.programming.test.pointsonaline.manager;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.welld.programming.test.pointsonaline.model.Point;
import com.welld.programming.test.pointsonaline.model.PointsOrder;

@Service
public class PointManager {

	private LinkedList<Point> points = new LinkedList<Point>();

	public synchronized void addPoint(Point point) throws IOException {

		if ( point != null ? point.getOffset() == null : true ) {
			throw new IOException("Point is not valid.");
		}
		
		// It can be optimised using an algorithm to split the list likewise quick sort
		
		int i = 0;
		for ( ; i < points.size(); i++ ) {
			Point currentPoint = points.get(i);
			int compareResult = currentPoint.compareTo(point); 
			if ( compareResult >= 0 ) {
				if ( compareResult > 0 ) {
					points.add(i, point);
				}
				break;
			}
		}
		if ( points.size() == i ) {
			points.add(point);
		}
	}

	public synchronized List<Point> getAll(PointsOrder pointsOrder) {

		if ( PointsOrder.DESC.equals(pointsOrder) ) {
			LinkedList<Point> reversedPoints = (LinkedList<Point>) points.clone();		
			Collections.reverse(reversedPoints);
			return reversedPoints;
		}
		return points;
	}

	public synchronized void deleteAll() {

		points = new LinkedList<Point>();
	}

	public synchronized List<Point> getNeighbours(Double pointOffset, Integer k) throws IOException{

		k = k != null ? k : 1;
		if ( k < 0 ) throw new IOException("Passed a k negative number"); 
		
		LinkedList<Point> cart = new LinkedList<Point>();
		
		if ( k > 0 && points.size() > 0 ) {
			
			if ( k >= points.size() ) {
				
				 cart.addAll(points);
				 
			} else {
				
				// Basically use a cart containing k elements, then move the cart on the line starting from the left to the right. 
				// If the next point of the line is nearer to the passed point than the first element in the cart,
				// then remove the first element from the cart and add the next point of the line into the cart.
				
				for (Point currentPoint : points) {
					
					if ( cart.size() < k ) {
						cart.add(currentPoint);
					} else {
						
						double currentDelta = Math.abs(currentPoint.getOffset() - pointOffset);
						double firstNeighboursDelta = Math.abs(cart.get(0).getOffset() - pointOffset);
						
						if ( currentDelta < firstNeighboursDelta ) {
							cart.removeFirst();
							cart.add(currentPoint);
						} else {
							break;
						}
					}
				}
			}
		}

		return cart;
	}
}
