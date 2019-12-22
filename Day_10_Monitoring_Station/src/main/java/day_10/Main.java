package day_10;

import day_10.domain.Asteroid;
import day_10.domain.Coordinate;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    Asteroid station = null;
    for (var asteroid : asteroids) {
      long numberOfAsteroidsOnSight = getNumberOfAsteroidsOnSight(asteroid, asteroids);
      if (numberOfAsteroidsOnSight > maxAsteroidsOnSight) {
        maxAsteroidsOnSight = numberOfAsteroidsOnSight;
        station = asteroid;
      }
    }
    System.out.println("MAX ASTEROIDS ON SIGHT AT THE STATION: " + maxAsteroidsOnSight);
    System.out.println(
        "STATION SHOULD BE AT "
            + station.getCoordinate().getX()
            + ","
            + station.getCoordinate().getY());

    vaporizeAsteroids(station, asteroids, 200);
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

  // TODO: fix the method because it's result is usually less by 2 than the real result should be
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

  private static void vaporizeAsteroids(Asteroid station, List<Asteroid> allAsteroids, int limit) {
    var angleMap = getSortedAngleMap(station, allAsteroids);
    var asteroidsToVaporize = new ArrayList<>(angleMap.values());

    var angles = new ArrayList<>(angleMap.keySet());
    int j =
        angles.indexOf(angles.stream().filter(angle -> angle >= 270).min(Double::compareTo).get())
            - 1;
    for (int i = 0; i < limit; i++) {
      Coordinate coordinate = asteroidsToVaporize.get(j).get(0).getCoordinate();
      asteroidsToVaporize.get(j).remove(0);
      if (asteroidsToVaporize.get(j).isEmpty()) {
        asteroidsToVaporize.remove(j);
      } else {
        j++;
      }
      if (j >= asteroidsToVaporize.size()) {
        j = 0;
      }
      System.out.println(
          "#"
              + (i + 1)
              + " ASTEROID AT "
              + coordinate.getX()
              + ","
              + coordinate.getY()
              + " VAPORIZED, ("
              + (coordinate.getX() * 100 + coordinate.getY())
              + ")");
    }
  }

  private static Map<Double, List<Asteroid>> getSortedAngleMap(
      Asteroid station, List<Asteroid> allAsteroids) {
    Map<Double, List<Asteroid>> angleMap = new LinkedHashMap<>();
    for (Asteroid other : allAsteroids) {
      if (other != station) {
        double angle = station.getCoordinate().getAngleWithOtherCoordinate(other.getCoordinate());
        if (!angleMap.containsKey(angle)) {
          angleMap.put(angle, new ArrayList<>());
        }
        angleMap.get(angle).add(other);
      }
    }

    // TODO: sort list of Asteroids by distance
    angleMap =
        angleMap.entrySet().stream()
            .sorted((entry1, entry2) -> Double.compare(entry2.getKey(), entry1.getKey()))
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (oldValue, newValue) -> oldValue,
                    LinkedHashMap::new));
    return angleMap;
  }
}
