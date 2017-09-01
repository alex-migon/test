package com.testtask.binomteh.data;


import android.graphics.Bitmap;

import org.osmdroid.util.GeoPoint;

public class User {

    private String mId;
    private String mName;
    private Bitmap mPhoto;
    private String mStatusGPS;
    private GeoPoint mGeoPoint;
    private String mDate;
    private String mTime;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public Bitmap getPhoto() {
        return mPhoto;
    }

    public void setPhoto(Bitmap mPhoto) {
        this.mPhoto = mPhoto;
    }

    public String getStatusGPS() {
        return mStatusGPS;
    }

    public void setStatusGPS(String mStatusGPS) {
        this.mStatusGPS = mStatusGPS;
    }

    public GeoPoint getGeoPoint() {
        return mGeoPoint;
    }

    public void setGeoPoint(GeoPoint mGeoPoint) {
        this.mGeoPoint = mGeoPoint;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }
}
