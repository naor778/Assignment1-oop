public class Velocity {
    private double dx;
    private double dy;

    // constructor
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // Take a point with position (x,y) and return a new point
    // with position (x+dx, y+dy)
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // angle במעלות, 0 זה למעלה, 90 זה ימינה וכו'
        double radians = Math.toRadians(angle);

        // בגלל שמערכת הצירים של המסך היא "עם y הפוך":
        // למעלה זה y קטן יותר, למטה זה y גדול יותר
        double dx = speed * Math.sin(radians);
        double dy = -speed * Math.cos(radians);

        return new Velocity(dx, dy);
    }
}