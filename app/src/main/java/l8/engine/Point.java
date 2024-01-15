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

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    // Quand on considère ces coordonnées comme un vecteur, on aimerait pouvoir le
    // multiplier par un facteur donné
    // Retourne un nouveau point et ne modifie pas le point actuel
    public Point getMultiplied(int multiplier) {
        return new Point(x * multiplier, y * multiplier);
    }

    // Retourne un nouveau point dont les coordonnées ont été additionnées avec
    // celles d'un vecteur donné
    public Point getAdded(Point vector) {
        return new Point(x + vector.x, y + vector.y);
    }

    public boolean equals(Object b) {
        return b != null
                && b.getClass() == getClass()
                && (x == ((Point) b).x && y == ((Point) b).y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
