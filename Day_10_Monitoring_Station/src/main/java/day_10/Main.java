package day_10;

import day_10.domain.Asteroid;
import day_10.domain.Coordinate;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {

  private static final int SCAN_RADIUS = 30;
  private static final double SCAN_PRECISION = 0.2;
  private static final double SCAN_RADIUS_STEP = 0.25;

  public static void main(String[] args) throws IOException, URISyntaxException {
    runScan("/day_10_1_example_0.txt");
    runScan("/day_10_1_example_1.txt");
    runScan("/day_10_1_example_2.txt");
    runScan("/day_10_1_example_3.txt");
    runScan("/day_10_1_example_4.txt");
    runScan("/day_10_input.txt");
  }

  private static void runScan(String inputFileName) throws URISyntaxException, IOException {
    List<String> input =
        Files.readAllLines(
            Paths.get(Main.class.getResource(inputFileName).toURI()), StandardCharsets.UTF_8);

    List<Asteroid> asteroids = readMap(input);
    long maxAsteroidsOnSight = -1;
    for (var asteroid : asteroids) {
      long numberOfAsteroidsOnSight = getNumberOfAsteroidsOnSight(asteroid, asteroids);
      if (numberOfAsteroidsOnSight > maxAsteroidsOnSight) {
        maxAsteroidsOnSight = numberOfAsteroidsOnSight;
      }
    }

    System.out.println("MAX ASTEROIDS ON SIGHT: " + maxAsteroidsOnSight);
  }

  private static List<Asteroid> readMap(List<String> input) {
    List<Asteroid> asteroids = new ArrayList<>();
    for (int y = 0; y < input.size(); y++) {
      for (int x = 0; x < input.get(y).length(); x++) {
        if (input.get(y).charAt(x) == '#') {
          asteroids.add(new Asteroid(new Coordinate(x, y)));
        }
      }
    }
    return asteroids;
  }

  private static long getNumberOfAsteroidsOnSight(Asteroid asteroid, List<Asteroid> allAsteroids) {
    Map<Asteroid, Double> angleMap = new LinkedHashMap<>();
    for (Asteroid other : allAsteroids) {
      if (other != asteroid) {
        angleMap.put(
            other, asteroid.getCoordinate().getAngleWithOtherCoordinate(other.getCoordinate()));
      }
    }

    return angleMap.values().stream().distinct().count();
  }
}
