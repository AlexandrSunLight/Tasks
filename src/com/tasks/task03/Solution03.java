package com.tasks.task03;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Solution03 {

    ArrayList<Double> list = new ArrayList();
    ArrayList<Integer> indexList = new ArrayList<>();
    int hour;
    public static void main(String[] args){

        System.out.println("Введите путь к папке с файлами отчетов:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Solution03 solution03 = new Solution03();

        try {
            File directory = new File(reader.readLine());
            solution03.processFilesFromFolder(directory);
            System.out.println("Введите час открытия магазина (08, 12 и т.п.):");
            solution03.hour = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Double maxPeople = Collections.max(solution03.list);
        for (int i = 0; i < solution03.list.size(); i++){
            if (solution03.list.get(i).equals(maxPeople)){
                solution03.indexList.add(i);
            }
        }

        System.out.println(solution03.getAnsverString(solution03.indexList));


    }

    public void processFilesFromFolder(File folder)
    {
        File[] folderEntries = folder.listFiles();
        for (File entry : folderEntries)
        {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(entry));
                for (int i = 0; i < 16; i++){
                    String s = reader.readLine();
                    if (s.contains(",")) s = s.replace(',', '.');
                    Double averagePeople = Double.valueOf(s);
                    if (list.size() < i + 1){
                        list.add(averagePeople);
                    }
                    else {
                        averagePeople = averagePeople + list.get(i);
                        list.set(i, averagePeople);
                    }

                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getAnsverString(ArrayList list){
        String s = "Наибольшее количество посетителей за день было в промежуток ";
        if (list.size() > 2){
            s+= getHourString((int)list.get(0));
            for (int i = 1; i < list.size() - 1; i++){
                s += "," + getHourString((int)list.get(i));
            }
            s += " и " + getHourString((int)list.get(list.size() - 1));
        }
        else{
            if (list.size() == 2){
                s += getHourString((int)list.get(0)) + " и " + getHourString((int)list.get(1));
            }
            else {
                s += getHourString((int)list.get(0));
            }
        }
        return s;
    }
    private String getHourString(int indexOfPeriod){
        if (indexOfPeriod % 2 == 0){
            return String.format("c %d:00 по %d:30",(hour + indexOfPeriod / 2), (hour + indexOfPeriod / 2));
        }
        else {
            return String.format("c %d:30 по %d:00",(hour + indexOfPeriod / 2),(hour + (indexOfPeriod + 1) / 2));
        }
    }

}
