package com.example.periodoc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity2 extends AppCompatActivity {

    ListView lv_dateList;
    DataBaseHelper dateBaseHelper;
    ArrayAdapter userArrayAdapter;
    Button settings;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        lv_dateList = (ListView) findViewById(R.id.lv_dateList);
        settings = (Button) findViewById(R.id.settings);
        home= (Button) findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity3();
            }
        });


        dateBaseHelper = new DataBaseHelper(MainActivity2.this);
        UserListVIew(dateBaseHelper)
        ;

        lv_dateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);

                builder.setTitle("Delete Date");
                builder.setMessage("Do you want to delete this date?");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        CustomerModel clickedUser = (CustomerModel) parent.getItemAtPosition(position);
                        dateBaseHelper.deleteOne(clickedUser);
                        UserListVIew(dateBaseHelper);
                        Toast.makeText(MainActivity2.this, "Deleted \n" + clickedUser.toString(), Toast.LENGTH_SHORT).show();

                    }
                }).setNegativeButton("Cancel", null);

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }
    private void UserListVIew(DataBaseHelper dateBaseHelper2) {
        userArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity2.this, android.R.layout.simple_list_item_1, dateBaseHelper2.getEveryDate());
        lv_dateList.setAdapter(userArrayAdapter);
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //finish();
    }
    public void openMainActivity3() {
        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);

        startActivity(intent);
        //finish();
    }
    /*@Override
    public void onBackPressed() {
        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity2.this);
        builder.setMessage("Are you sure you want to exit?")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity2.super.onBackPressed();
                    }
                }).setNegativeButton("Cancel", null).setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }*/


}