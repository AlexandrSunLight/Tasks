package com.tasks.task01;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Solution01 {

    String filePath;
    ArrayList<Integer> integerList;

    public static void main(String[] args){
        Solution01 solution = new Solution01();
        System.out.println(String.format("90 percentile <%g>", solution.getPercentile(90, solution.integerList)));
        System.out.println(String.format("median <%g>", solution.getPercentile(50, solution.integerList)));
        System.out.println(String.format("average  <%g>", solution.getAverage(solution.integerList)));
        System.out.println(String.format("max <%d>",Collections.max(solution.integerList)));
        System.out.println(String.format("min <%d>",Collections.min(solution.integerList)));
    }

    public Solution01() {
        pathReading();
        getListOfIntegers(filePath);
        Collections.sort(integerList);

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

    private void getListOfIntegers(String fileName){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            ArrayList<Integer> list = new ArrayList<>();
            while (reader.ready()) {
                list.add(Integer.valueOf(reader.readLine()));
            }
            reader.close();
            integerList = list;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private double getPercentile(int persentile, ArrayList<Integer> list){
        Double number = (double) (persentile * (list.size() + 1)) / 100;
        int n = number.intValue();
        double p = ((double) (persentile * (list.size() + 1)) % 100) / 100;
        if (number % 1 == 0){
            return list.get(n - 1);
        }
        else {
            return (list.get(n - 1) + (p * (list.get(n) - list.get(n - 1))));
        }
    }

    private double getAverage(ArrayList<Integer> list){
        int sum = 0;
        double average;
        for (Integer i: list){
            sum += i;
        }
        average = (double) sum / list.size();
        return average;
    }

}
