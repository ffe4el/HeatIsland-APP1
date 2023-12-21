package com.example.projec7;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.Marker;

import java.util.List;

public class SharedUmbrellaViewModel extends ViewModel {
    private int[] umbrellaCounts;
    private List<Marker> countMarkerList;
    public String CurrentLocationMarkerName;

    public int[] getUmbrellaCounts() {
        return umbrellaCounts;
    }

    public void setUmbrellaCounts(int[] umbrellaCounts) {
        this.umbrellaCounts = umbrellaCounts;
    }

    public List<Marker> getCountMarkerList(){
        return countMarkerList;
    }
    public void setCountMarkerList(List<Marker> countMarkerList) {
        this.countMarkerList = countMarkerList;
    }

    public String getCurrentLocationMarkerName() {return CurrentLocationMarkerName;}

    public void setCurrentLocationMarkerName(String CurrentLocationMarkerName){
        this.CurrentLocationMarkerName=CurrentLocationMarkerName;
    }

}