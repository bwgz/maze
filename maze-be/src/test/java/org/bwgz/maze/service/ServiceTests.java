package org.bwgz.maze.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bwgz.maze.model.Location;
import org.bwgz.maze.model.Maze;
import org.bwgz.maze.util.MazeDump;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTests {
	final private static Logger logger = LoggerFactory.getLogger(ServiceTests.class);
	
	@Autowired
	private MazeService mazeService;
	
    @Autowired
    private RouteService service;
    
    private Maze getMaze(String name) throws IOException {
		ClassLoader classLoader = ServiceTests.class.getClassLoader();
		File file = new File(classLoader.getResource(name).getFile());
		
		Maze maze = mazeService.build(file);
		MazeDump.printMaze(maze);
		
		return maze;
    }
    
    @Test
	public void nullMazeTest() throws IOException {
    	String file = "maze-null.txt";
    	
    	Maze maze = getMaze(file);
    	logger.debug("{} - maze: {}", file, maze);
        assertEquals("width is 0", maze.getWidth(), Integer.valueOf(0));
        assertEquals("height is 0", maze.getHeight(), Integer.valueOf(0));
        assertTrue("paths is empty", maze.getPaths().size() == 0);

		List<Location> route = service.getShortestPath(maze);
    	logger.debug("{} - route: {}", file, route);
        assertTrue("route is empty", route.size() == 0);
		
		MazeDump.printMaze(maze, route);
	}

    @Test
	public void emptylMazeTest1x1() throws IOException {
    	String file = "maze-empty-1x1.txt";
    	
    	Maze maze = getMaze(file);
    	logger.debug("{} - maze: {}", file, maze);
        assertEquals("width is 1", maze.getWidth(), Integer.valueOf(1));
        assertEquals("height is 1", maze.getHeight(), Integer.valueOf(1));
        assertTrue("paths is empty", maze.getPaths().size() == 0);

		List<Location> route = service.getShortestPath(maze);
    	logger.debug("{} - route: {}", file, route);
        assertTrue("route is empty", route.size() == 0);
		
		MazeDump.printMaze(maze, route);
	}

    @Test
	public void emptylMazeTest3x3() throws IOException {
    	String file = "maze-empty-3x3.txt";
    	
    	Maze maze = getMaze(file);
    	logger.debug("{} - maze: {}", file, maze);
        assertEquals("width is 3", maze.getWidth(), Integer.valueOf(3));
        assertEquals("height is 3", maze.getHeight(), Integer.valueOf(3));
        assertTrue("paths is empty", maze.getPaths().size() == 0);

		List<Location> route = service.getShortestPath(maze);
    	logger.debug("{} - route: {}", file, route);
        assertTrue("route is empty", route.size() == 0);
		
		MazeDump.printMaze(maze, route);
	}

    @Test
	public void emptylMazeTest3x3with1_Path() throws IOException {
    	String file = "maze-3x3-1-path.txt";
    	
    	Maze maze = getMaze(file);
    	logger.debug("{} - maze: {}", file, maze);
        assertEquals("width is 3", maze.getWidth(), Integer.valueOf(3));
        assertEquals("height is 3", maze.getHeight(), Integer.valueOf(3));
        assertTrue("paths is 1", maze.getPaths().size() == 1);

		List<Location> route = service.getShortestPath(maze);
    	logger.debug("{} - route: {}", file, route);
        assertTrue("route is empty", route.size() == 0);
		
		MazeDump.printMaze(maze, route);
	}

    @Test
	public void noStartMazeTest() throws IOException {
    	String file = "maze-no-start.txt";
    	
    	Maze maze = getMaze(file);
    	logger.debug("{} - maze: {}", file, maze);
        assertEquals("width is 5", maze.getWidth(), Integer.valueOf(5));
        assertEquals("height is 5", maze.getHeight(), Integer.valueOf(5));
        assertEquals("start is missing", maze.getStart(), null);
        assertEquals("finish is (3, 2)", maze.getFinish(), new Location(3,2));
        assertTrue("paths is 8", maze.getPaths().size() == 8);

		List<Location> route = service.getShortestPath(maze);
    	logger.debug("{} - route: {}", file, route);
        assertTrue("route is empty", route.size() == 0);
		
		MazeDump.printMaze(maze, route);
	}

    @Test
	public void noFinishMazeTest() throws IOException {
    	String file = "maze-no-finish.txt";
    	
    	Maze maze = getMaze(file);
    	logger.debug("{} - maze: {}", file, maze);
        assertEquals("width is 5", maze.getWidth(), Integer.valueOf(5));
        assertEquals("height is 5", maze.getHeight(), Integer.valueOf(5));
        assertEquals("start is (1, 1)", maze.getStart(), new Location(1, 1));
        assertEquals("finish is missing", maze.getFinish(), null);
        assertTrue("paths is 8", maze.getPaths().size() == 8);

		List<Location> route = service.getShortestPath(maze);
    	logger.debug("{} - route: {}", file, route);
        assertTrue("route is empty", route.size() == 0);
		
		MazeDump.printMaze(maze, route);
	}
    
    @Test
	public void Maze1Test() throws IOException {
    	String file = "maze1.txt";
    	
    	Maze maze = getMaze(file);
    	logger.debug("{} - maze: {}", file, maze);
        assertEquals("width is 10", maze.getWidth(), Integer.valueOf(10));
        assertEquals("height is 8", maze.getHeight(), Integer.valueOf(8));
        assertEquals("start is (1, 1)", maze.getStart(), new Location(1, 1));
        assertEquals("finish is  (8, 4)", maze.getFinish(), new Location(8, 4));

		List<Location> route = service.getShortestPath(maze);
    	logger.debug("{} - route: {}", file, route);
        assertTrue("route has 15 locations", route.size() == 15);
		
		MazeDump.printMaze(maze, route);
	}
}
