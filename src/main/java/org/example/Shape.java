package org.example;

public abstract class Shape implements Comparable<Shape>{

    abstract double getArea();
    abstract double getPerimeter();

    public static Shape newRectangle(double width, double height) {
        return new Rectangle(width, height);
    }

    public static Shape newCircle(double radius) {
        return new Circle(radius);
    }

    @Override
    public int compareTo(Shape other) {
        return Double.compare(this.getArea(), other.getArea());
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
