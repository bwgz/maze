package org.bwgz.maze.web;

import java.util.ArrayList;
import java.util.List;

import org.bwgz.maze.model.Location;
import org.bwgz.maze.model.Maze;
import org.bwgz.maze.service.MazeService;
import org.bwgz.maze.service.RouteService;
import org.bwgz.maze.util.MazeDump;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MazeController {
	final private static Logger logger = LoggerFactory.getLogger(MazeController.class);

	@Autowired
	private RouteService routeService;
	
	@Autowired
	private MazeService mazeService;
	
	protected static class MazeRequest {
		private List<String> maze;

		public List<String> getMaze() {
			return maze;
		}

		public void setMaze(List<String> maze) {
			this.maze = maze;
		}

		@Override
		public String toString() {
			return "MazeRequest [maze=" + maze + "]";
		}
	}

	protected static class MazeResponse {
		private Integer width;
		private Integer height;
		private Location start;
		private Location finish;
		private List<Location> paths;
		private List<Location> route;

		public MazeResponse() {}
		
		public MazeResponse(Maze maze, List<Location> route) {
			this.width = maze.getWidth();
			this.height = maze.getHeight();
			this.start = maze.getStart();
			this.finish = maze.getFinish();
			this.paths = new ArrayList<>(maze.getPaths().keySet());
			this.route = route;
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

		public List<Location> getPaths() {
			return paths;
		}

		public void setPaths(List<Location> paths) {
			this.paths = paths;
		}

		public List<Location> getRoute() {
			return route;
		}

		public void setRoute(List<Location> route) {
			this.route = route;
		}

		@Override
		public String toString() {
			return "MazeResponse [width=" + width + ", height=" + height + ", start=" + start + ", finish=" + finish
					+ ", paths=" + paths + ", route=" + route + "]";
		}
	}

	@PostMapping("/maze")
	public MazeResponse maze(@RequestBody MazeRequest request) {
		logger.debug("maze - request: {}", request);
		
		Maze maze = mazeService.build(request.getMaze());
		MazeDump.printMaze(maze);

		List<Location> route = routeService.getShortestPath(maze);
		MazeDump.printMaze(maze, route);

		return new MazeResponse(maze, route);
	}

}
