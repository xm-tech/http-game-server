package com.ppgames.demo.config.item;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/15.
 */
public class CheckInItem {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getMorning() {
        return morning;
    }

    public void setMorning(ArrayList<Integer> morning) {
        this.morning = morning;
    }

    public ArrayList<Integer> getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(ArrayList<Integer> afternoon) {
        this.afternoon = afternoon;
    }

    public ArrayList<Integer> getEvening() {
        return evening;
    }

    public void setEvening(ArrayList<Integer> evening) {
        this.evening = evening;
    }

    private int id;
    private ArrayList<Integer> morning;
    private ArrayList<Integer> afternoon;
    private ArrayList<Integer> evening;
}
