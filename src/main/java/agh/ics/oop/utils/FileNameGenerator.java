package agh.ics.oop.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileNameGenerator {
    public static String generateFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String fileName = dateFormat.format(date) + ".txt";
        return fileName;
    }

}