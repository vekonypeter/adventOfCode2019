package day_12;

import com.google.common.collect.Sets;
import day_12.domain.Moon;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  /**
   * INPUT:
   *
   * <ul>
   *   <li>x=-8, y=-9, z=-7
   *   <li>x=-5, y=2, z=-1
   *   <li>x=11, y=8, z=-14
   *   <li>x=1, y=-4, z=-11
   * </ul>
   */
  public static void main(String[] args) {
    List<Moon> moons =
        List.of(
            //
            // DAY 12. PART 1. EXAMPLE
            //
            new Moon("Io", -1, 0, 2),
            new Moon("Europa", 2, -10, -7),
            new Moon("Ganymede", 4, -8, 8),
            new Moon("Callisto", 3, 5, -1)
            //
            // DAY 12. PART 2. EXAMPLE
            //
            //  new Moon("Io", -8, -10, 0),
            //  new Moon("Europa", 5, 5, 10),
            //  new Moon("Ganymede", 2, -7, 3),
            //  new Moon("Callisto", 9, -8, -3)
            //
            // DAY 12. INPUT
            //
            //  new Moon("Io", -8, -9, -7),
            //  new Moon("Europa", -5, 2, -1),
            //  new Moon("Ganymede", 11, 8, -14),
            //  new Moon("Callisto", 1, -4, -11)
            );

    List<List<Moon>> moonPairs =
        Sets.combinations(new HashSet<>(moons), 2).stream()
            .map(ArrayList::new)
            .collect(Collectors.toList());

    List<String> systemHashes = new ArrayList<>();
    String systemHash;
    int steps = 0;
    while (true) {
      moonPairs.forEach(moonPair -> moonPair.get(0).calculateVelocity(moonPair.get(1)));
      moons.forEach(Moon::applyVelocity);
      systemHash = getSystemHash(moons);
      if (systemHashes.contains(systemHash)) {
        break;
      } else {
        systemHashes.add(systemHash);
      }
      if (steps % 100000 == 0) {
        System.out.println(steps);
      }
      steps++;
    }

    System.out.println("NUMBER OF STEPS FOR REPETITION: " + steps);

    // PART 1.
    // int totalEnergy = moons.stream().map(Moon::getTotalEnergy).reduce(0, Integer::sum);
    // System.out.println("TOTAL ENERGY IN THE SYSTEM: " + totalEnergy);
  }

  private static String getSystemHash(List<Moon> moons) {
    return moons.stream()
        .reduce("", (acc, moon) -> acc.concat(String.valueOf(moon.hashCode())), String::concat);
  }
}
