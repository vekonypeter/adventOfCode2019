package day_10;

import day_10.domain.Asteroid;
import day_10.domain.Coordinate;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
      long numberOfAsteroidsOnSight = scanForAsteroidsOnSight(asteroid, asteroids);
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

  private static long scanForAsteroidsOnSight(Asteroid asteroid, List<Asteroid> allAsteroids) {
    Stream<Asteroid> asteroidsOnSight = Stream.empty();
    for (double i = 0; i < 360; i += SCAN_RADIUS_STEP) {
      var line = getLineCoordinates(asteroid.getCoordinate(), i, SCAN_RADIUS);
      asteroidsOnSight =
          Stream.concat(asteroidsOnSight, getFirstAsteroidOnLine(allAsteroids, line).stream());
    }
    return asteroidsOnSight.distinct().count();
  }

  /*
   * sinAngle = y / length -> y = sinAngle * length
   *
   * cosAngle = x / length -> x = cosAngle * length
   */
  private static List<Coordinate> getLineCoordinates(Coordinate start, double angle, int length) {
    List<Coordinate> coordinates = new ArrayList<>();
    double sinAngle = Math.sin(Math.toRadians(angle));
    double cosAngle = Math.cos(Math.toRadians(angle));

    for (double l = SCAN_PRECISION; l <= length; l += SCAN_PRECISION) {
      int x = start.getX() + (int) Math.floor(cosAngle * l);
      int y = start.getY() + (int) Math.floor(sinAngle * l);
      var coordinate = new Coordinate(x, y);
      if (!coordinates.contains(coordinate) && !coordinate.equals(start)) {
        coordinates.add(coordinate);
      }
    }
    return coordinates;
  }

  private static Optional<Asteroid> getFirstAsteroidOnLine(
      List<Asteroid> asteroids, List<Coordinate> lineCoordinates) {
    return asteroids.stream()
        .filter(asteroid -> lineCoordinates.contains(asteroid.getCoordinate()))
        .findFirst();
  }
}
