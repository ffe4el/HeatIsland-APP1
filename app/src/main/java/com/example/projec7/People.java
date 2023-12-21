// MapHelper.java
package com.example.projec7;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class People {

    public static double getPeopleFromCSV(Context context, String districtName) {
        double sum = 0.0;
        int totalCount = 0;
        try {
            InputStream inputStream = context.getAssets().open("people_train.csv");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            // CSV 파일 구조가 "자치구, 온도"와 같다고 가정합니다.
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].trim().equals(districtName)) {
                    double people = Double.parseDouble(parts[1].trim());
                    sum += people;
                    totalCount++;
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

