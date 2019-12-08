package orbits;

import orbits.domain.SpaceObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        System.out.print("PART 1.: TOTAL ORBITS: " + totalOrbits);


    }
}
