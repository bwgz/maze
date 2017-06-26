package org.bwgz.maze.util;

import java.util.ArrayList;
import java.util.List;

import org.bwgz.maze.model.Location;
import org.bwgz.maze.model.Maze;
import org.bwgz.maze.web.MazeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MazeDump {
	final private static Logger logger = LoggerFactory.getLogger(MazeController.class);
	
	private final static Character FENCE = '#';
	private final static Character PATH = '.';
	private final static Character START = 'A';
	private final static Character FINISH = 'B';
	private final static Character ROUTE = '@';

	public static void printMaze(Maze maze, List<Location> route) {
		for (int y = 0; y < maze.getHeight(); y++) {
			StringBuffer buffer = new StringBuffer();
			
			for (int x = 0; x < maze.getWidth(); x++) {
				Location location = new Location(x, y);
				Character c;
				
				if (route.contains(location)) {
					c = ROUTE;
				}
				else if (!maze.getPaths().containsKey(location)) {
					c = FENCE;
				}
				else if (location.equals(maze.getStart())) {
					c = START;
				}
				else if (location.equals(maze.getFinish())) {
					c = FINISH;
				}
				else {
					c = PATH;
				}
				
				buffer.append(c);
			}
			
			logger.debug(buffer.toString());
		}
	}
	
	public static void printMaze(Maze maze) {
		printMaze(maze, new ArrayList<Location>());
	}


}
