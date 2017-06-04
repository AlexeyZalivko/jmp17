package com.jmp17.lambda;

import com.jmp17.lambda.data.NewPoint;
import com.jmp17.lambda.data.Point;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;


public class App {

    public static void main(String[] args) {
        final App app = new App();
        final List<Point> points = app.generatePoints();

        System.out.println("point 1:");
        points.stream().forEach(System.out::println);

        final List<NewPoint> newPoints = points.stream()
                .map(x -> new NewPoint(x.getX(), x.getY()))
                .collect(toList());
        System.out.println("point 2:");
        newPoints.stream().forEach(System.out::println);

        final List<NewPoint> filteredPoints = newPoints.stream()
                .filter(x -> x.getA() > 3 && x.getB() > 3)
                .collect(toList());
        System.out.println("point 3:");
        filteredPoints.stream().forEach(System.out::println);

        final List<NewPoint> distinctPoints = filteredPoints.stream()
                .distinct()
                .collect(toList());
        System.out.println("point 4:");
        distinctPoints.stream().forEach(System.out::println);

        System.out.println("sum: " + distinctPoints.stream().mapToInt(x -> x.getA() + x.getB()).sum());
        System.out.println("multiply: " + distinctPoints.stream().mapToInt(x -> x.getA() * x.getB()).sum());
    }

    private List<Point> generatePoints() {
        return IntStream.iterate(1, x -> x + 2)
                .limit(10)
                .mapToObj(x -> new Point(x, 2 * x))
                .collect(toList());
    }
}
