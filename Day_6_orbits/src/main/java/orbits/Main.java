package orbits;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import orbits.domain.SpaceObject;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> mapEntries = Files.readAllLines(Paths.get(Main.class.getResource("/day_6_input.txt").toURI()), StandardCharsets.UTF_8);

        // TODO: enhance this pretty nasty solution
        Map<String, SpaceObject> map = new HashMap<>();
        map.put("COM", new SpaceObject("COM", null));
        for (String mapEntry : mapEntries) {
            String objectId = mapEntry.substring(4);
            String orbitedObjectId = mapEntry.substring(0, 3);

            SpaceObject object = new SpaceObject(objectId, orbitedObjectId);
            map.put(objectId, object);
        }

        map.values().forEach(spaceObject -> spaceObject.setObjectToOrbit(map.get(spaceObject.getObjectToOrbitId())));
        Integer totalOrbits = map.values().stream().reduce(0, (identity, object) -> identity + object.getNumberOfOrbits(), Integer::sum);
        System.out.println("PART 1.: TOTAL ORBITS: " + totalOrbits);

        SpaceObject youOrbit = map.get("YOU").getObjectToOrbit();
        SpaceObject sanOrbit = map.get("SAN").getObjectToOrbit();
        Integer commonOrbitedObjectNumber = getCommonOrbitedObject(youOrbit, sanOrbit).getNumberOfOrbits();
        Integer transfersNeeded = (youOrbit.getNumberOfOrbits() - commonOrbitedObjectNumber) + (sanOrbit.getNumberOfOrbits() - commonOrbitedObjectNumber);

        System.out.println("PART 2.: TRANSFERS NEEDED: " + transfersNeeded);
    }

    private static SpaceObject getCommonOrbitedObject(SpaceObject so1, SpaceObject so2) {
        List<String> ids = new ArrayList<>();
        while (true) {
            if (so1.getObjectToOrbit() != null) {
                String id1 = so1.getId();
                if (ids.contains(id1)) {
                    return so1;
                }
                else {
                    ids.add(id1);
                    so1 = so1.getObjectToOrbit();
                }
            }
            if (so2.getObjectToOrbit() != null) {
                String id2 = so2.getId();
                if (ids.contains(id2)) {
                    return so2;
                }
                else {
                    ids.add(id2);
                    so2 = so2.getObjectToOrbit();
                }
            }
        }
    }
}
