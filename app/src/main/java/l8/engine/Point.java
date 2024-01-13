package l8.engine;

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

    public boolean equals(Point b) {
        if (b == null)
            return false;
        return x == b.x && y == b.y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
