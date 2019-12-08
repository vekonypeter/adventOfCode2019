package orbits.domain;

public class SpaceObject {

    private String id;
    private SpaceObject objectToOrbit;

    public SpaceObject(String id, SpaceObject objectToOrbit) {
        this.id = id;
        this.objectToOrbit = objectToOrbit;
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

    public SpaceObject getObjectToOrbit() {
        return objectToOrbit;
    }

    public void setObjectToOrbit(SpaceObject objectToOrbit) {
        this.objectToOrbit = objectToOrbit;
    }
}
