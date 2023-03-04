package com.example.periodoc;

import android.util.Log;

public class CustomerModel {
    private int id;
    private String myDate;
    //private String nextDay;

    public CustomerModel(int id, String myDate) {
        this.id = id;
        this.myDate = myDate;

    }

    //private String nextDay = countNextDate(myDate);

    @Override
    public String toString() {
        return "Current Period : " + myDate + " \nNext Period      : " + countNextDate(myDate);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMyDate() {
        return myDate;
    }

    public void setMyDate(String myDate) {
        this.myDate = myDate;
    }

    public String countNextDate(String myDate) {

        Log.v(CustomerModel.class.toString(), myDate);

        try {
            int day = Integer.parseInt(myDate.replaceAll("[\\D]", ""));

            int d1, d2 = 0, m1, m2 = 0, y1, y2 = 0;
            d1 = day / 1000000;
            m1 = (day - (d1 * 1000000)) / 10000;
            y1 = day % 10000;
            if (m1 == 2) {
                y2 = y1;
                if (((y1 % 4 == 0) && (y1 % 100 != 0)) || (y1 % 400 == 0)) {
                    if (d1 == 1) {
                        d2 = 29;
                        m2 = m1;
                    } else {
                        d2 = d1 - 1;
                        m2 = m1 + 1;
                    }
                } else {
                    d2 = d1;
                    m2 = m1 + 1;
                }

            }
            if (m1 == 1 || m1 == 3 || m1 == 5 || m1 == 7 || m1 == 8 || m1 == 10) {
                y2 = y1;
                if (d1 == 1 || d1 == 2 || d1 == 3) {
                    m2 = m1;
                    d2 = d1 + 28;
                } else {
                    m2 = m1 + 1;
                    d2 = d1 - 3;
                }
            }
            if (m1 == 4 || m1 == 6 || m1 == 9 || m1 == 11) {
                y2 = y1;
                if (d1 == 1 || d1 == 2) {
                    m2 = m1;
                    d2 = d1 + 28;
                } else {
                    m2 = m1 + 1;
                    d2 = d1 - 2;
                }
            }
            if (m1 == 12) {
                y2 = y1 + 1;
                if (d1 == 1 || d1 == 2 || d1 == 3) {
                    m2 = m1;
                    d2 = d1 + 28;
                } else {
                    m2 = 1;
                    d2 = d1 - 3;
                }
            }

            String next;
            if (d2 < 10) {
                if ((m2) < 10) {
                    next = "0" + d2 + "/" + "0" + m2 + "/" + y2;
                } else {
                    next = "0" + d2 + "/" + m2 + "/" + y2;
                }
            } else {
                if (m2 < 10) {
                    next = d2 + "/" + "0" + m2 + "/" + y2;
                } else {
                    next = d2 + "/" + m2 + "/" + y2;
                }
            }
            //next = d2 + "/" + m2 + "/" +y2;
            return next;
        } catch (Exception ex) {
            return "00/00/0000";
        }
    }
}
