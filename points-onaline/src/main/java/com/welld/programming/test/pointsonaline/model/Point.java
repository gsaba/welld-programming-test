package com.welld.programming.test.pointsonaline.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Point implements Comparable<Point> {
	
	private Double offset;
	
	public Point() {
		super();
	}
	
	public Point(Double offset) {
		super();
		this.offset = offset;
	}

	public Double getOffset() {
		return offset;
	}

	public void setOffset(Double offset) {
		this.offset = offset;
	}

	@Override
	@JsonIgnore
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (offset == null) {
			if (other.offset != null)
				return false;
		} else if (!offset.equals(other.offset))
			return false;
		return true;
	}

	@Override
	@JsonIgnore
	public String toString() {
		return "[offset=" + offset + "]";
	}
	
	@Override
	@JsonIgnore
	public int compareTo(Point other) {
		
		if (this == other)
			return 0;
		if (other == null)
			return 1;
		
		if (offset == null) {
			if (other.offset != null)
				return -1;
			else 
				return 0;
		} else {
			if (other.offset != null)
				return offset.compareTo(other.offset);
			else 
				return 1;
		}
	}
}
