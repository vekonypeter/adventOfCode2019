package day_12;

import com.google.common.collect.Sets;
import day_12.domain.Moon;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    Set<Moon> moons =
        Set.of(
            // DAY 12. PART 1. EXAMPLE
            //  new Moon("Io", -1, 0, 2),
            //  new Moon("Europa", 2, -10, -7),
            //  new Moon("Ganymede", 4, -8, 8),
            //  new Moon("Callisto", 3, 5, -1)
            // DAY 12. INPUT
            new Moon("Io", -8, -9, -7),
            new Moon("Europa", -5, 2, -1),
            new Moon("Ganymede", 11, 8, -14),
            new Moon("Callisto", 1, -4, -11));

    List<List<Moon>> moonPairs =
        Sets.combinations(moons, 2).stream().map(ArrayList::new).collect(Collectors.toList());

    final int STEPS = 1000;
    for (int i = 0; i < STEPS; i++) {
      moonPairs.forEach(moonPair -> moonPair.get(0).calculateVelocity(moonPair.get(1)));
      moons.forEach(Moon::applyVelocity);
    }

    int totalEnergy = moons.stream().map(Moon::getTotalEnergy).reduce(0, Integer::sum);

    System.out.println("TOTAL ENERGY IN THE SYSTEM: " + totalEnergy);
  }
}
