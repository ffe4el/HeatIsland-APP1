package com.example.projec7;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;

public class MapUpdater_hotisland {

    public static void updateImageView(Context context, int drawableId, ImageView imageView, String selectedITem) {
        VectorChildFinder vector = new VectorChildFinder(context, drawableId, imageView);

        VectorDrawableCompat.VFullPath[] districts = new VectorDrawableCompat.VFullPath[25];

        String[] districtNames = {
                "도봉구", "동대문구", "동작구", "은평구", "강북구", "강동구", "강서구", "금천구", "구로구",
                "관악구", "광진구", "강남구", "종로구", "중구", "중랑구", "마포구", "노원구", "서초구",
                "서대문구", "성북구", "성동구", "송파구", "양천구", "영등포구", "용산구"
        };

        String[] regionNames = {
                "Dobong-gu","Dongdaemun-gu","Dongjak-gu","Eunpyeong-gu","Gangbuk-gu","Gangdong-gu", "Gangseo-gu","Geumcheon-gu","Guro-gu",
                "Gwanak-gu", "Gwangjin-gu", "Gangnam-gu", "Jongno-gu", "Jung-gu", "Jungnang-gu", "Mapo-gu", "Nowon-gu", "Seocho-gu",
                "Seodaemun-gu", "Seongbuk-gu", "Seongdong-gu", "Songpa-gu", "Yangcheon-gu", "Yeongdeungpo-gu", "Yongsan-gu"
        };

        for (int i = 0; i < districts.length; i++) {
            districts[i] = vector.findPathByName(districtNames[i]);
        }

        int[] tmp = new int[regionNames.length];

        for (int i = 0; i < regionNames.length; i++) {
            double value = MapHelper.getTemperaturesFromCSV(context, regionNames[i], selectedITem);
            double people = People.getPeopleFromCSV(context, regionNames[i]);
            int sum = (int) value + (int) people;
            if(sum > 255){
                tmp[i] = 255;
            }
            else
                tmp[i] = sum;
        }

        for (int i = 0; i < tmp.length; i++) {
            int color = Color.argb(tmp[i], 255, 20, 147);
            districts[i].setFillColor(color);
        }

        imageView.invalidate();
    }
}