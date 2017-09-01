package com.testtask.binomteh.data;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.testtask.binomteh.R;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class Data {

    List<User> listData;

    public Data(Context context) {
        listData = new ArrayList<>();

        User alex = new User();
        alex.setId("0");
        alex.setName("Alex");
        alex.setPhoto(BitmapFactory.decodeResource(context.getResources(), R.drawable.alex));
        alex.setStatusGPS("GPS");
        alex.setGeoPoint(new GeoPoint(56.885509, 60.586680));
        alex.setDate("24.05.17");
        alex.setTime("14:00");
        listData.add(alex);

        User dasha = new User();
        dasha.setId("1");
        dasha.setName("Dasha");
        dasha.setPhoto(BitmapFactory.decodeResource(context.getResources(), R.drawable.dasha));
        dasha.setStatusGPS("GPS");
        dasha.setGeoPoint(new GeoPoint(56.885509, 61.586680));
        dasha.setDate("24.05.17");
        dasha.setTime("15:00");
        listData.add(dasha);

        User olga = new User();
        olga.setId("2");
        olga.setName("Olga");
        olga.setPhoto(BitmapFactory.decodeResource(context.getResources(), R.drawable.olga));
        olga.setStatusGPS("GPS");
        olga.setGeoPoint(new GeoPoint(56.330000, 60.300000));
        olga.setDate("24.05.17");
        olga.setTime("9:00");
        listData.add(olga);
    }

    public List<User> getData() {
        return listData;
    }
}
