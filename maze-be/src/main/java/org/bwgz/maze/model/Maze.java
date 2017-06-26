package org.bwgz.maze.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * A maze is described by a two dimensional matrix with a size defined
 * by it's width and height;
 * 
 * Within the matrix are cells that represent "open road". That
 * cell has a location (x, y) in the matrix and a set of paths from that 
 * location to another adjacent location.
 * 
 * The "paths" field maps all "open road" cells to adjacent "open road"
 * cells. This creates the equivalent of a graph. Each location is a 
 * vertex and the adjacent paths are edges.  
 *  
 */

public class Maze {
	private Integer width;
	private Integer height;
	private Map<Location, Set<Location>> paths;
	private Location start;
	private Location finish;

	public Maze(int width, int height, Map<Location, Set<Location>> paths) {
		this.width = width;
		this.height = height;
		this.paths = paths;
	}
	
	public Maze() {
		this(0, 0, new HashMap<Location, Set<Location>>());
	}
	
	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Map<Location, Set<Location>> getPaths() {
		return paths;
	}

	public void setPaths(Map<Location, Set<Location>> paths) {
		this.paths = paths;
	}

	public Location getStart() {
		return start;
	}

	public void setStart(Location start) {
		this.start = start;
	}

	public Location getFinish() {
		return finish;
	}

	public void setFinish(Location finish) {
		this.finish = finish;
	}

	@Override
	public String toString() {
		return "Maze [width=" + width + ", height=" + height + ", paths=" + paths + ", start=" + start + ", finish="
				+ finish + "]";
	}
}
