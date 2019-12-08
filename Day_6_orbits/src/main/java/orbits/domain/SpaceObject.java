package orbits.domain;

public class SpaceObject {

    private String id;
    private String objectToOrbitId;
    private SpaceObject objectToOrbit;

    public SpaceObject(String id, String objectToOrbitId) {
        this.id = id;
        this.objectToOrbitId = objectToOrbitId;
    }

    public int getNumberOfOrbits() {
        if (id.equals("COM")) {
            return 0;
        } else {
            return objectToOrbit.getNumberOfOrbits() + 1;
        }
    }

    public String getId() {
        return id;
    }

    public String getObjectToOrbitId() {
        return objectToOrbitId;
    }

    public SpaceObject getObjectToOrbit() {
        return objectToOrbit;
    }

    public void setObjectToOrbit(SpaceObject objectToOrbit) {
        this.objectToOrbit = objectToOrbit;
    }
}
