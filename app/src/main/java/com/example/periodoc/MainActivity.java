package com.example.periodoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView myDate;
    Button save;
    //ListView lv_dateList;
    ArrayAdapter userArrayAdapter;
    DataBaseHelper dateBaseHelper;
    Button settings;
    Button listWindow;
    TextView returnDate;
    TextView returnNextDate;
    TextView ovulation2;
    //SimpleDateFormat dateFormat;
    //Calendar calendar;
    int d1,m1,y1;
    //MenuItem item_done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        myDate = (TextView) findViewById(R.id.myDate);
        save = (Button) findViewById(R.id.save);
        //lv_dateList = (ListView) findViewById(R.id.lv_dateList);


        settings = (Button) findViewById(R.id.settings);
        listWindow= (Button) findViewById(R.id.listWindow);
        returnDate =(TextView) findViewById(R.id.returnDate);
        returnNextDate =(TextView) findViewById(R.id.returnNextDate);
        ovulation2 =(TextView) findViewById(R.id.ovulation2);



        listWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity2();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity3();
            }
        });

        //final String myDate ;
        //System.out.println(myDate);
        //calendar = Calendar.getInstance();
        //dateFormat= new SimpleDateFormat("DD/MM/YY");
        //date2= dateFormat.format(calendar.getTime());
        //myDate.setText(date2);
        //System.out.println("Printing" + myDate);

        dateBaseHelper = new DataBaseHelper(MainActivity.this);

        //UserListVIew(dateBaseHelper);

        String lastDate = dateBaseHelper.getLastDate();// CHANGED
        returnDate.setText("Current Period: " + lastDate); // CHANGED
        String nextDate = "00/00/0000";
        nextDate = countNext(lastDate);

        returnNextDate.setText("Next Period: " + nextDate);
        String ovulation="00/00/0000";
        ovulation = countOvulation(lastDate);
        ovulation2.setText("Ovulation: " + ovulation);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date;
                if(dayOfMonth <10) {
                    if((month+1) <10) {
                        date = "0" + dayOfMonth + "/" + "0" + (month+1) + "/" + year;
                    }
                    else {
                        date = "0" + dayOfMonth + "/" + (month+1) + "/" + year;
                    }
                }
                else {
                    if((month+1) <10) {
                        date = dayOfMonth + "/" + "0" + (month+1) + "/" + year;
                    }
                    else {
                        date = dayOfMonth + "/" + (month+1) + "/" + year;
                    }
                }


                myDate.setText(date);
                d1 = dayOfMonth; m1= month; y1= year;


            }
        });
        //myDate.setText(date2);


        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if (myDate.getText() == "Mark Your Date") {
                    Toast.makeText(MainActivity.this, "Select a date", Toast.LENGTH_SHORT).show();
                } else if (myDate.getText()!= "Mark Your Date"){
                    CustomerModel customerModel;
                    try{
                        customerModel = new CustomerModel(-1, myDate.getText().toString());
                        Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Toast.makeText(MainActivity.this,"Error saving date", Toast.LENGTH_SHORT).show();
                        customerModel = new CustomerModel(-1, "error");
                    }

                    DataBaseHelper dateBaseHelper = new DataBaseHelper(MainActivity.this);
                    boolean success = dateBaseHelper.addOne(customerModel);
                    //Toast.makeText(MainActivity.this, "Success" + success, Toast.LENGTH_SHORT).show();


                    List<CustomerModel>everyDate = dateBaseHelper.getEveryDate();
                    //Toast.makeText(MainActivity.this, everyDate.toString(), Toast.LENGTH_SHORT).show();

                    String lastDate = dateBaseHelper.getLastDate(); // CHANGED
                    //Toast.makeText(MainActivity.this, lastDate, Toast.LENGTH_SHORT).show();// CHANGED
                    returnDate.setText("Current Period: " + lastDate); // CHANGED
                    String nextDate = "00/00/0000";
                    nextDate = countNext(lastDate);

                    returnNextDate.setText("Next Period: " + nextDate);
                    String ovulation="00/00/0000";
                    ovulation = countOvulation(lastDate);
                    ovulation2.setText("Ovulation: " + ovulation);


                    //MainActivity2 userList = new MainActivity2();
                    //UserListVIew(dateBaseHelper);
                }



            }
        });



    }

    public String countNext(String lastDate){
        try {
            int day = Integer.parseInt(lastDate.replaceAll("[\\D]",""));
            int d1, d2=0, m1, m2=0, y1, y2=0;
            d1 = day/1000000;
            m1= (day-(d1*1000000))/10000;
            y1=day%10000;


            if(m1 == 2) {
                y2 = y1;
                if(((y1 % 4 == 0) && (y1 % 100!= 0)) || (y1%400 == 0)) {
                    if(d1 == 1) {d2=29; m2 = m1;}
                    else {d2=d1-1; m2=m1+1;}
                }
                else {
                    d2 = d1;
                    m2 = m1+1;
                }


            }
            if(m1==1 || m1==3 || m1==5 || m1==7 || m1==8 || m1==10 ) {
                y2=y1;
                if(d1==1 || d1==2 || d1==3) {m2=m1; d2 = d1+28;}
                else {m2 = m1+1; d2 = d1-3;}
            }
            if(m1==4 || m1==6 || m1==9 || m1==11 ) {
                y2=y1;
                if(d1==1 || d1==2 ) {m2=m1; d2 = d1+28;}
                else {m2 = m1+1; d2 = d1-2;}
            }
            if(m1 == 12) {
                y2 = y1+1;
                if(d1==1 || d1==2 || d1==3) {m2=m1; d2 = d1+28;}
                else {m2 = 1; d2 = d1-3;}
            }


            String nextDate;
            if(d2<10) {
                if((m2) <10) {
                    nextDate = "0" + d2 + "/" + "0" + m2 + "/" + y2;
                }
                else {
                    nextDate = "0" + d2 + "/" + m2 + "/" + y2;
                }
            }
            else {
                if(m2<10) {
                    nextDate = d2 + "/" + "0" + m2 + "/" + y2;
                }
                else {
                    nextDate = d2 + "/" + m2 + "/" + y2;
                }
            }
            //next = d2 + "/" + m2 + "/" +y2;
            return nextDate;
        }
        catch (Exception ex){
            return  "00/00/0000";
        }
    }

    public String countOvulation(String lastDate){
        try {
            int day = Integer.parseInt(lastDate.replaceAll("[\\D]",""));
            int d1, d2=0, m1, m2=0, y1, y2=0;
            d1 = day/1000000;
            m1= (day-(d1*1000000))/10000;
            y1=day%10000;


            if(m1 == 2) {
                y2 = y1;
                if(((y1 % 4 == 0) && (y1 % 100!= 0)) || (y1%400 == 0)) {
                    if(d1 <= 15) {d2=d1+14; m2 = m1;}
                    else {d2=d1-15; m2=m1+1;}
                }
                else {
                    if(d1 <= 14){d2 = d1+14; m2 = m1;}
                    else {d2=d1-14; m2=m1+1;}

                }


            }
            if(m1==1 || m1==3 || m1==5 || m1==7 || m1==8 || m1==10 ) {
                y2=y1;
                if(d1 <= 16) {m2=m1; d2 = d1+14;}
                else {m2 = m1+1; d2 = d1-16;}
            }
            if(m1==4 || m1==6 || m1==9 || m1==11 ) {
                y2=y1;
                if(d1<= 17 ) {m2=m1; d2 = d1+14;}
                else {m2 = m1+1; d2 = d1-17;}
            }
            if(m1 == 12) {
                y2 = y1+1;
                if(d1 <= 16) {m2=m1; d2 = d1+14;}
                else {m2 = m1+1; d2 = d1-16;}
            }


            String nextDate;
            if(d2<10) {
                if((m2) <10) {
                    nextDate = "0" + d2 + "/" + "0" + m2 + "/" + y2;
                }
                else {
                    nextDate = "0" + d2 + "/" + m2 + "/" + y2;
                }
            }
            else {
                if(m2<10) {
                    nextDate = d2 + "/" + "0" + m2 + "/" + y2;
                }
                else {
                    nextDate = d2 + "/" + m2 + "/" + y2;
                }
            }
            //next = d2 + "/" + m2 + "/" +y2;
            return nextDate;
        }
        catch (Exception ex){
            return  "00/00/0000";
        }
    }



    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure you want to exit?")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed(); finish();
                    }
                }).setNegativeButton("Cancel", null).setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void openMainActivity2(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
        //finish();
    }
    public void openMainActivity3(){
        Intent intent = new Intent(MainActivity.this, MainActivity3.class);

        startActivity(intent);
        //finish();
    }


}