package org.example;

import java.util.*;

public class App {
    public static void main(String[] args) {
        List<Shape> shapes = new ArrayList<>();

        shapes.add(Shape.newRectangle(1,2));
        shapes.add(Shape.newRectangle(2,3));
        shapes.add(Shape.newRectangle(0.5,1.5));
        shapes.add(Shape.newRectangle(10,2));
        shapes.add(Shape.newCircle(1));
        shapes.add(Shape.newCircle(1.4));
        shapes.add(Shape.newCircle(12));

        Collections.sort(shapes);
        System.out.println(" ");
        System.out.println("Lista med sorterade rektanglar och cirklar: ");
        for (Shape shape : shapes) {
            System.out.println("\t" + shape.getClass().getSimpleName() + ", area: " + shape.getArea());
        }

        Set<Shape> shapeSet = new HashSet<>();

        shapeSet.add(Shape.newRectangle(4.2, 5));
        shapeSet.add(Shape.newCircle(3));
        shapeSet.add(Shape.newRectangle(2, 6));
        shapeSet.add(Shape.newRectangle(2, 6));
        shapeSet.add(Shape.newCircle(4));
        shapeSet.add(Shape.newRectangle(1, 7));
        shapeSet.add(Shape.newRectangle(4, 5));
        shapeSet.add(Shape.newCircle(3));
        shapeSet.add(Shape.newCircle(3));

        System.out.println("\nSet med rektanglar och cirklar: ");
        for (Shape shape : shapeSet) {
            System.out.println("\t" + shape.getClass().getSimpleName() + ", area: " + shape.getArea() + ", perimeter: " + shape.getPerimeter());
        }

    }
}