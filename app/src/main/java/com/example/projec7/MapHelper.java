// MapHelper.java
package com.example.projec7;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapHelper {

    public static double getTemperaturesFromCSV(Context context, String districtName, String selectedItem) {
        List<Double> temperatures = new ArrayList<>();
        double sum = 0.0;
        int totalCount = 0;
        try {
            InputStream inputStream = context.getAssets().open("nature.csv");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            // CSV 파일 구조가 "자치구, 온도"와 같다고 가정합니다.
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                if(selectedItem.equals("온도")) {
                    if (parts[0].trim().equals(districtName)) {
                        double temperature = Double.parseDouble(parts[1].trim());
                        sum += temperature;
                        totalCount++;
                    }
                }
                else if(selectedItem.equals("습도")) {
                    if (parts[0].trim().equals(districtName)) {
                        double temperature = Double.parseDouble(parts[2].trim());
                        sum += temperature;
                        totalCount++;
                    }
                }
                else if(selectedItem.equals("열섬")) {
                    if (parts[0].trim().equals(districtName)) {
                        double temperature = Double.parseDouble(parts[2].trim()) + Double.parseDouble(parts[1].trim());
                        sum += temperature;
                        totalCount++;
                    }
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        double overallAverage = (totalCount > 0) ? sum / totalCount : 0.0;
        return overallAverage;
    }
}

