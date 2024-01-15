package l8.engine;

/**
 * Point class, contains the point.
 *
 * @autor : Cosmo de Oliveira Maria Vitoria
 * @autor : Koestli Camille
 * @autor : Roland Samuel
 * @date : 17.01.2024
 */
public class Point {
    int x;
    int y;

    /**
     * Constructor of Point
     * @param x the x
     * @param y the y
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retrieves the x coordinate
     * @return the x coordinate
     */
    public int x() {
        return this.x;
    }

    /**
     * Retrieves the y coordinate
     * @return the y coordinate
     */
    public int y() {
        return this.y;
    }

    /**
     * Multiplies the point's coordinates by a given multiplier. This operation does not modify the current point but returns a new point.
     * @param multiplier the factor by which to multiply the point's coordinates.
     * @return new point with the multiplied coordinates.
     */
    public Point getMultiplied(int multiplier) {
        return new Point(x * multiplier, y * multiplier);
    }

    /**
     * Adds a given point's coordinates to this point's coordinates. This operation does not modify the current point but returns a new point.
     * @param vector the point to add to this point's coordinates.
     * @return new point with the added coordinates.
     */
    public Point getAdded(Point vector) {
        return new Point(x + vector.x, y + vector.y);
    }

    /**
     * Checks whether this point is equal to another object.
     * @param b the object to compare to.
     * @return true if the object is a point and has the same coordinates as this point, false otherwise.
     */
    public boolean equals(Object b) {
        return b != null
                && b.getClass() == getClass()
                && (x == ((Point) b).x && y == ((Point) b).y);
    }

    /**
     * Returns the string of the point.
     * @return the string of the point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
