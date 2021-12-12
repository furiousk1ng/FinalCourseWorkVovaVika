package com.vovavika.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Leaderboards {

    private static Leaderboards lBoard;
    private String filePath; // путь к файлу
    private String highScores;  // лучший результат


    private ArrayList<Integer> topScores; // список лучшего резульата
    private ArrayList<Integer> topTiles;  // список наибольшего значения плитки
    private ArrayList<Long> topTimes;	  // список наилучшего времени

    private Leaderboards(){
        try {
            filePath = new File("").getAbsolutePath(); // абсолютный путь к файлу
            System.out.println(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        highScores = "Scores";

        topScores = new ArrayList<Integer>();
        topTiles = new ArrayList<Integer>();
        topTimes = new ArrayList<Long>();
    }

    public static Leaderboards getInstance(){
        if(lBoard == null){
            lBoard = new Leaderboards();
        }
        return lBoard;
    }
    // добавление результата
    public void addScore(int score){
        for(int i = 0; i < topTiles.size(); i++){
            if(score >= topScores.get(i)){
                //добавялем результат
                topScores.add(i, score);
                // удаляем результат
                topScores.remove(topScores.size() - 1);
                return;
            }
        }
    }
    // добавление результата плиток
    public void addTile(int tileValue){
        for(int i = 0; i < topTiles.size(); i++){
            if(tileValue >= topTiles.get(i)){
                //добавялем плитку с большим значением
                topTiles.add(i, tileValue);
                //удаляем плитку
                topTiles.remove(topTiles.size() - 1);
                return;
            }
        }
    }

    public void addTime(long millis){
        for(int i = 0; i < topTimes.size(); i++){
            if(millis <= topTimes.get(i)){
                topTimes.add(i, millis);
                topTimes.remove(topTimes.size() - 1);
                return;
            }
        }
    }
    // загрузка файла с результатами
    public void loadScores() {
        try {
            File f = new File(filePath, highScores);
            if (!f.isFile()) {
                createSaveData();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

            topScores.clear();
            topTiles.clear();
            topTimes.clear();

            String[] scores = reader.readLine().split("-");
            String[] tiles = reader.readLine().split("-");
            String[] times = reader.readLine().split("-");

            //добавляем
            for (int i = 0; i < scores.length; i++) {
                topScores.add(Integer.parseInt(scores[i]));
            }
            for (int i = 0; i < tiles.length; i++) {
                topTiles.add(Integer.parseInt(tiles[i]));
            }
            for (int i = 0; i < times.length; i++) {
                topTimes.add(Long.parseLong(times[i]));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // сохранение результатов
    public void saveScores() {
        FileWriter output = null;

        try {
            File f = new File(filePath, highScores);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);
            // записываем 5 лучших результатов
            writer.write(topScores.get(0) + "-" + topScores.get(1) + "-" + topScores.get(2) + "-" + topScores.get(3) + "-" + topScores.get(4));
            writer.newLine();
            // записываем 5 самых больших значений плиток
            writer.write(topTiles.get(0) + "-" + topTiles.get(1) + "-" + topTiles.get(2) + "-" + topTiles.get(3) + "-" + topTiles.get(4));
            writer.newLine();
            // лучшее время
            writer.write(topTimes.get(0) + "-" + topTimes.get(1) + "-" + topTimes.get(2) + "-" + topTimes.get(3) + "-" + topTimes.get(4));
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    // создание файла сохранения
    private void createSaveData() {
        try {
            File file = new File(filePath, highScores);

            FileWriter output = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(output);
            // запись в файл
            writer.write("0-0-0-0-0");
            writer.newLine();
            writer.write("0-0-0-0-0");
            writer.newLine();
            writer.write(Integer.MAX_VALUE + "-" + Integer.MAX_VALUE + "-" + Integer.MAX_VALUE + "-" + Integer.MAX_VALUE + "-" + Integer.MAX_VALUE);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getHighScore(){
        return topScores.get(0);
    }

    public long getFastestTime(){
        return topTimes.get(0);
    }

    public ArrayList<Integer> getTopScores() {
        return topScores;
    }

    public ArrayList<Integer> getTopTiles() {
        return topTiles;
    }

    public ArrayList<Long> getTopTimes() {
        return topTimes;
    }
}
