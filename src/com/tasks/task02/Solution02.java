package com.tasks.task02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Solution02 {
    String filePath;
    ArrayList<Point> points;
    Point testPoint;

    public Solution02() {
        pathReading();
        getListOfPoints(filePath);
    }

    public static void main(String[] args){
        Solution02 solution = new Solution02();
        Polygon polygon = new Polygon(solution.points);

        if (polygon.isVertex(solution.testPoint)){
            System.out.println("точка - вершина четырехугольника");
        }
        else if (polygon.OnSegment(solution.testPoint)){
            System.out.println("точка лежит на сторонах четырехугольника");
        }
        else if (polygon.inPolygon(solution.testPoint)){
            System.out.println("точка внутри четырехугольника");
        }
        else {
            System.out.println("точка снаружи четырехугольника");
        }
    }

    private void pathReading(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите путь к файлу (формата .txt или .rtf):");
        try {
            filePath = reader.readLine();
            System.out.println("Введите координаты точки через пробел:");
            String s = reader.readLine();
            String[] strings = s.split(" ");
            testPoint = new Point(Double.parseDouble(strings[0]),Double.parseDouble(strings[1]));
            reader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {

        }
    }

    private void getListOfPoints(String fileName){
        ArrayList<Point> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while (reader.ready()) {
                String s = reader.readLine();
                String[] strings = s.split(" ");
                list.add(new Point(Double.parseDouble(strings[0]),Double.parseDouble(strings[1])));
            }
            reader.close();
            points = list;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class Point{
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Segment{
        Point alfa, beta;
        double k, b, minX, maxX;


        public Segment(Point alfa, Point beta) {
            this.alfa = alfa;
            this.beta = beta;
            this.k = (alfa.y - beta.y) / (alfa.x - beta.x);
            this.b = alfa.y - k * alfa.x;
            if (alfa.x > beta.x) {
                maxX = alfa.x;
                minX = beta.x;
            }
            else {
                maxX = beta.x;
                minX = alfa.x;
            }
        }

        private boolean isPoinBelongsToSegment(Point point){
            if ( point.y == k * point.x + b){
                if (point.x < maxX && point.x > minX){
                    return true;
                }
                else {
                  return false;
                }
            }
            else {
                return false;
            }
        }
    }

    private static class Polygon{
        ArrayList<Point> points;
        ArrayList<Segment> segments;

        public Polygon(ArrayList<Point> points) {
            this.points = points;
            ArrayList<Segment> segments = new ArrayList<>();
            for (int i = 1; i < points.size(); i++){
                segments.add(new Segment(points.get(i - 1),points.get(i)));
            }
            segments.add(new Segment(points.get(0),points.get(points.size() - 1)));
            this.segments = segments;
        }

        private boolean isVertex(Point testPoint){
            boolean haveVertex = false;
            for (Point point : points){
                if (testPoint.x == point.x && testPoint.y == point.y){
                    haveVertex = true;
                }
            }
            return haveVertex;
        }

        private boolean OnSegment(Point testpoint){
            boolean hasSegment = false;
            for (Segment segment : segments){
                if (segment.isPoinBelongsToSegment(testpoint)) hasSegment = true;
            }
            return hasSegment;
        }

        private boolean inPolygon(Point testpoint){
            int angle = 0;
            for (int i = 1; i < points.size(); i++){
                double vectorACoordinateX = points.get(i - 1).x - testpoint.x;
                double vectorACoordinateY = points.get(i - 1).y - testpoint.y;
                double vectorBCoordinateX = points.get(i).x - testpoint.x;
                double vectorBCoordinateY = points.get(i).y - testpoint.y;

                double vectorALength = Math.sqrt(vectorACoordinateX * vectorACoordinateX + vectorACoordinateY * vectorACoordinateY);
                double vectorBLength = Math.sqrt(vectorBCoordinateX * vectorBCoordinateX + vectorBCoordinateY * vectorBCoordinateY);
                angle += Math.acos((vectorACoordinateX * vectorBCoordinateX + vectorACoordinateY * vectorBCoordinateY) / (vectorALength * vectorBLength));
            }
            double vectorACoordinateX = points.get(points.size() - 1).x - testpoint.x;
            double vectorACoordinateY = points.get(points.size() - 1).y - testpoint.y;
            double vectorBCoordinateX = points.get(0).x - testpoint.x;
            double vectorBCoordinateY = points.get(0).y - testpoint.y;

            double vectorALength = Math.sqrt(vectorACoordinateX * vectorACoordinateX + vectorACoordinateY * vectorACoordinateY);
            double vectorBLength = Math.sqrt(vectorBCoordinateX * vectorBCoordinateX + vectorBCoordinateY * vectorBCoordinateY);
            angle += Math.acos((vectorACoordinateX * vectorBCoordinateX + vectorACoordinateY * vectorBCoordinateY) / (vectorALength * vectorBLength));

            if (angle != 0){
                return true;
            }
            else {
                return false;
            }
        }
    }
}
