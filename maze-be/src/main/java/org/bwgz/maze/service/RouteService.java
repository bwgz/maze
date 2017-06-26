package org.bwgz.maze.service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.bwgz.maze.model.Location;
import org.bwgz.maze.model.Maze;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RouteService {
	final private static Logger logger = LoggerFactory.getLogger(RouteService.class);

	/*
	 * The "vertices" field represents a graph. A breadth-first search on a graph
	 * will find the shortest path between two vertex (start and finish).
	 * 
	 * In breadth-first search:
	 *     - you need to keep track of which nodes have been visited
	 *     - you need to make sure you visit all nodes adjacent to a node before visiting any others (this can be done with a queue)
	 *     - the first time you visit a node, you can be sure you have found the shortest path to it, so that node does not need to be visited again
	 *     
	 *     By distance between two nodes u,v we mean the number of edges on the shortest path between u and v . Now:
	 *       - Start at the start vertex s . It is at distance 0 from itself, and there are no other nodes at distance 0
	 *       - Consider all the nodes adjacent to s . These all are at distance at most 1 from s (maybe less than 1, if s has an edge to itself; but then we would have found a shorter path already) and there are no other nodes at distance 1
	 *       - Consider all the nodes adjacent to the nodes adjacent to s . These are all at distance at most 2 from s (maybe less than 2; but then we would have found a shorter path already) and there are no other nodes at distance 2
	 *         ... and so on. In this breadth-first search, as soon as we visit a node in the graph, we know the shortest path from s to it; and so by the time we have visited all the nodes in the graph, we know the shortest path from s to each of them
	 */
	private Stack<Location> bfs(Map<Location, Set<Location>> vertices, Location start, Location finish) {
		Stack<Location> stack = new Stack<>();

		if (start != null) {
			stack.add(start);
			
			if (finish != null) {
				Queue<Location> queue = new ArrayDeque<>();
				Set<Location> visited = new HashSet<>();
		
				visited.add(start);
				queue.add(start);
		
				while (!queue.isEmpty()) {
					Location current = queue.poll();
		
					for (Location location : vertices.get(current)) {
						
						if (!visited.contains(location)) {
							stack.add(location);
							visited.add(location);
							queue.add(location);
							if (location.equals(finish)) {
								return stack;
							}
						}
					}
				}
			}
		}

		return stack;
	}
	
	private List<Location> getShortestPath(Map<Location, Set<Location>> locations, Location start, Location finish, Stack<Location> search) {
		List<Location> paths = new ArrayList<>();
		
		if (start != null && finish != null) {
			Location current = finish;
			paths.add(finish);
			
			while (!search.isEmpty()) {
				Location location = search.pop();
				if (locations.get(current).contains(location)) {
					paths.add(location);
					if (location.equals(start)) {
						break;
					}
					current = location;
				}
			}
		}
		
		return paths;
	}
		
	private List<Location> getShortestPath(Map<Location, Set<Location>> vertices, Location start, Location finish) {
		return getShortestPath(vertices, start, finish, bfs(vertices, start, finish));
	}

	public List<Location> getShortestPath(Maze maze) {
		// The path comes back in reverse. So call for it in reverse.
		return getShortestPath(maze.getPaths(), maze.getFinish(), maze.getStart());
	}
}
