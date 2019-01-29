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
	
	/*
	 * Using the default singleton scope of Spring, Spring ensure there is only one bean PointManager.
	 * Consequently there is only one LinkedList of points in the running application.
	 * Using "synchronized" methods it is ensured only one call is executed at the same time over the list of points.
	 */ 

	private LinkedList<Point> points = new LinkedList<Point>();

	public synchronized void addPoint(Point point) throws IOException {

		if ( point != null ? point.getOffset() == null : true ) {
			throw new IOException("Point is not valid.");
		}
		
		if ( points.isEmpty() ) {
			 points.add(point);
		} else {
			addPointInSubList(point, 0, points.size()-1);
		}
	}
	
	private void addPointInSubList(Point point, int firstIndex, int lastIndex) {
		
		// Like Quick Sort split the list and work on the matching half of the list.
		
		Double firstOffset = points.get(firstIndex).getOffset();
		Double lastOffset = points.get(lastIndex).getOffset();
		Double pointOffset = point.getOffset();
		
		if ( pointOffset.equals(firstOffset) || pointOffset.equals(lastOffset) ) {
			// first point or last point (or both) have the same offset of the passed point, this point already exist.
			return;
		} else if ( firstIndex == lastIndex ) {
			// only one point in the sub list (and the point is not equal to the passed one), 
			// add the passed point to the left or to the right
			points.add( firstOffset > pointOffset ? firstIndex : firstIndex + 1, point);
			return;
		} else {
			// split the list, and call recursively the method on the appropriate sub-list
			int middleIndex = (lastIndex + firstIndex) / 2;
			Double middleOffset = points.get(middleIndex).getOffset();
			
			if ( pointOffset <= middleOffset ) {
				addPointInSubList(point, firstIndex, middleIndex);
			} else {
				addPointInSubList(point, middleIndex+1, lastIndex);
			}
		}
		
		return;
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

		points.clear();
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
