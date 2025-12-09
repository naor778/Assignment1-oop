package geometry;

public class Point {
    private double x;
    private double y;
    // constructor
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    // distance -- return the distance of this point to the other point
    public double distance(Point other) {
    double ax=this.x-other.x;
    double ay=this.y-other.y;
    return Math.sqrt((ax*ax)+(ay*ay));
    }
    // equals -- return true is the points are equal, false otherwise
    public boolean equals(Point other) {
        if((this.x==other.x)&&(this.y== other.y)){return true;}
        else {return false;}}

    // Return the x and y values of this point
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
}