package com.example.periodoc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {

    Button home;
    Button listWindow;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    EditText username;
    Button save_name;
    Button cancel;
    TextView set_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        home = (Button) findViewById(R.id.home);
        listWindow= (Button) findViewById(R.id.listWindow);
        set_name = (TextView) findViewById(R.id.set_name);
        set_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewNameDialog();

            }
        });


        listWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity2();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });


    }
    public void openMainActivity2(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
        //finish();

    }
    public void openMainActivity() {
        Intent intent = new Intent(MainActivity3.this, MainActivity.class);

        startActivity(intent);
        //finish();
    }


    public void createNewNameDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View namePopupView = getLayoutInflater().inflate(R.layout.popup,null);
        save_name = (Button) namePopupView.findViewById(R.id.save_name);
        username = (EditText) namePopupView.findViewById(R.id.username);
        cancel = (Button) namePopupView.findViewById(R.id.cancel);

        dialogBuilder.setView(namePopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        save_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserInfo userInfo;
                if (username.getText().toString() != null)//check whether the entered text is not null
                {
                    userInfo = new UserInfo(username.getText().toString());
                    Toast.makeText(getApplicationContext(), userInfo.toString(), Toast.LENGTH_LONG).show();
                    //display the text that you entered in edit text
                    set_name.setText(username.getText().toString());
                }
                dialog.dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /*@Override
    public void onBackPressed() {
        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity3.this);
        builder.setMessage("Are you sure you want to exit?")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity3.super.onBackPressed();
                    }
                }).setNegativeButton("Cancel", null).setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }*/
}