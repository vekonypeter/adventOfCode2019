package orbits;

import orbits.domain.SpaceObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> mapEntries = Files.readAllLines(Paths.get(Main.class.getResource("/day_6_input.txt").toURI()), StandardCharsets.UTF_8);

        Map<String, SpaceObject> map = new HashMap<>();
        List<SpaceObject> orbitLessObjects = new ArrayList<>();
        for (String mapEntry : mapEntries) {
            String objectId = mapEntry.substring(4);
            String orbitedObjectId = mapEntry.substring(0, 3);

            SpaceObject orbitedObject = map.get(orbitedObjectId);
            if (orbitedObject == null) {
                orbitedObject = new SpaceObject(orbitedObjectId, null);
                orbitLessObjects.add(orbitedObject);
                addNewObjectToMap(orbitedObject, map, orbitLessObjects);
            }

            SpaceObject object = new SpaceObject(objectId, orbitedObject);
            addNewObjectToMap(object, map, orbitLessObjects);
        }
    }

    private static void addNewObjectToMap(SpaceObject newObject, Map<String, SpaceObject> map, List<SpaceObject> orbitLessObjects) {
        map.put(newObject.getId(), newObject);


    }
}
