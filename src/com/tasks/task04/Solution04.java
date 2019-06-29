package com.tasks.task04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class Solution04 {

    String filePath;
    ArrayList<TimePoint> timePoints;
    ArrayList<TimeInterval> timeIntervals;

    public Solution04() {
        pathReading();
        getListOfTimePoints(filePath);
        Collections.sort(timePoints);
        timeIntervals = new ArrayList<>();

    }

    public static void main(String[] args){
        Solution04 solution = new Solution04();
        int start = 0, end = 0, count = 0, max = 0;
        for (TimePoint point: solution.timePoints){
            if (point.arrival){
                count++;
                if (count >= max){
                    start = point.seconds;
                    end = -1;
                    max = count;
                }
            }
            else {
                count--;
                if (end == -1){
                    end = point.seconds;
                    solution.timeIntervalAdding(max,start,end);
                }
            }
        }

        for (TimeInterval timeInterval: solution.timeIntervals){
            if (timeInterval.maxCustomers == max){
                String startTime = LocalTime.ofSecondOfDay(timeInterval.start).toString();
                String endTime = LocalTime.ofSecondOfDay(timeInterval.end).toString();
                System.out.println(String.format("Больше всего посетителей (%d) было в период с %s по %s", max, startTime, endTime));
            }
        }
    }

    private void pathReading(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите путь к файлу (формата .txt или .rtf):");
        try {
            filePath = reader.readLine();
            reader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getListOfTimePoints(String path){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            ArrayList<TimePoint> list = new ArrayList<>();
            LocalTime localTime;
            String s;
            while (reader.ready()) {
                s = reader.readLine();
                localTime = LocalTime.parse(s.split(" ")[0]);
                list.add(new TimePoint(localTime.toSecondOfDay(),true));
                localTime = LocalTime.parse(s.split(" ")[1]);
                list.add(new TimePoint(localTime.toSecondOfDay(),false));
            }
            reader.close();
            timePoints = list;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void timeIntervalAdding(int max, int start, int end){
        timeIntervals.add(new TimeInterval(max,start,end));
    }

    private class TimePoint implements Comparable {
        int seconds;
        boolean arrival;
        TimePoint (int seconds, boolean arrival){
            this.seconds = seconds;
            this.arrival = arrival;
        }

        @Override
        public int compareTo(Object o) {
            TimePoint secondPoint = (TimePoint) o;
            return this.seconds - secondPoint.seconds;
        }
    }

    private class TimeInterval{
        int maxCustomers, start, end;

        public TimeInterval(int maxCustomers, int start, int end) {
            this.maxCustomers = maxCustomers;
            this.start = start;
            this.end = end;
        }
    }
}
