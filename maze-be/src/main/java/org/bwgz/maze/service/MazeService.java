package org.bwgz.maze.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.bwgz.maze.model.Location;
import org.bwgz.maze.model.Maze;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MazeService {
	final private static Logger logger = LoggerFactory.getLogger(MazeService.class);
	
	private static enum Direction {
		UP, DOWN, LEFT, RIGHT
	};
	
	private final static Character OPEN = '.';
	private final static Character START = 'A';
	private final static Character FINISH = 'B';

	/*
	 * Create an empty maze;
	 */
	public Maze build() {
		return new Maze();
	}

	private Location parseCharacter(Character c, Integer x, Integer y) {
		return (c.equals(OPEN) || c.equals(START) || c.equals(FINISH)) ? new Location(x, y) : null;
	}

	/*
	 * Create an maze from a list of strings that describe the maze.
	 * 
	 * First - create a map that contains all locations (vertices) in the maze.
	 * Second - for each location (vertex) calculate all the adjacent locations (edges).
	 * 
	 */

	public Maze build(List<String> lines) {
		Integer height = 0;
		Integer width = 0;
		Map<Location, Set<Location>> paths = new HashMap<>();
		Location start = null;
		Location finish = null;
				
		if (lines != null) {
			for (int y = 0; y < lines.size(); y++) {
				String line = lines.get(y).trim();
				
				if (line.length() != 0) {
					for (int x = 0; x < line.length(); x++) {
						Character c = line.charAt(x);
						Location location = parseCharacter(c, x, y);
						if (location != null) {
							paths.put(location, new HashSet<Location>());
							
							if (START.equals(c)) {
								start = location;
							}
							else if (FINISH.equals(c)) {
								finish = location;
							}
						}
					}
					
					width = Math.max(width, line.length());
					height++;
				}
			}
		}
		
		// yuck - the lambda need these variables to be final.
		final Integer _width = width;
		final Integer _height = height;
		
		paths.entrySet().stream()
			.forEach(entry -> {
				Location location = entry.getKey();
				int x = location.getX();
				int y = location.getY();
				
				Arrays.stream(Direction.values())
					.forEach(direction -> {
						Location adjacent = null;
						
						switch (direction) {
						case UP:
							if (y != 0) {
								adjacent = new Location(x, y - 1);
							}
							break;
						case DOWN:
							if (y < (_height - 1)) {
								adjacent = new Location(x, y + 1);
							}
							break;
						case LEFT:
							if (x != 0) {
								adjacent = new Location(x - 1, y);
							}
							break;
						case RIGHT:
							if (x < (_width - 1)) {
								adjacent = new Location(x + 1, y);
							}
							break;
						}
						
						if (adjacent != null && paths.containsKey(adjacent)) {
							entry.getValue().add(adjacent);
						}
					});
			});

		Maze maze = build();
		maze.setWidth(width);
		maze.setHeight(height);
		maze.setPaths(paths);
		maze.setStart(start);
		maze.setFinish(finish);
		
		return maze;
	}

	public Maze build(File file) throws IOException {
		List<String> lines = new ArrayList<>();
		Scanner scanner = new Scanner(file);
		
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			lines.add(line);
		}

		scanner.close();
		
		return build(lines);
	}
}
