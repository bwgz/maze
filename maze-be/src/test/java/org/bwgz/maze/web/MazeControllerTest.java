/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bwgz.maze.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bwgz.maze.model.Location;
import org.bwgz.maze.web.MazeController.MazeRequest;
import org.bwgz.maze.web.MazeController.MazeResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class MazeControllerTest {
	final private static Logger logger = LoggerFactory.getLogger(MazeControllerTest.class);
	
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    private List<String> getMaze(String name) throws IOException {
		List<String> lines = new ArrayList<>();
		
		ClassLoader classLoader = MazeControllerTest.class.getClassLoader();
		File file = new File(classLoader.getResource(name).getFile());
		Scanner scanner = new Scanner(file);
		
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			lines.add(line);
		}

		scanner.close();
		
		return lines;
    }

    @Test
    public void maze() throws Exception {
    	String file = "maze1.txt";
    	
        URI uri = new URI("http://localhost:" + this.port + "/api/v1/maze");
        MazeRequest request = new MazeRequest();
        request.setMaze(getMaze(file));
        HttpEntity<MazeRequest> entity = new HttpEntity<>(request);
        MazeResponse response = restTemplate.postForObject(uri, entity, MazeResponse.class);
        assertEquals("width is 10", response.getWidth(), Integer.valueOf(10));
        assertEquals("height is 8", response.getHeight(), Integer.valueOf(8));
        assertEquals("start is (1, 1)", response.getStart(), new Location(1, 1));
        assertEquals("finish is  (8, 4)", response.getFinish(), new Location(8, 4));
        assertTrue("route has 15 locations", response.getRoute().size() == 15);
   }

}
