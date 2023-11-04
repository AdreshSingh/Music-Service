package com.example.musicservice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    Button startButton,stopButton;
    TextView serviceStatus;

    // static integer type storing our permission flag
    public static final int PERMIT =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            // making a cll for the permission method
            getPermit();
        }


        // accessing xml components to control functionality
        startButton = findViewById(R.id.startService);
        stopButton = findViewById(R.id.serviceStop);
        serviceStatus = findViewById(R.id.serviceStatus);

        //setting textview text to check status
        serviceStatus.setText(getString(R.string.service_status_s," Ready To Go!"));



        //attached a onclick event to the start button
        startButton.setOnClickListener(view -> {


            startService(new Intent(MainActivity.this, myService.class));
            serviceStatus.setText(getString(R.string.service_status_s," started"));
            Toast.makeText(this, "Service Active", Toast.LENGTH_SHORT).show();
        });

        // attached a onclick button to the stop button
        stopButton.setOnClickListener(view -> {
            stopService(new Intent(MainActivity.this,myService.class));
            serviceStatus.setText(getString(R.string.service_status_s," stopped"));
            Toast.makeText(this, "Service Inactive", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMIT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getPermit(){
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                alert.setTitle("Storage Permission");
                alert.setMessage("Read external permission");

                alert.setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMIT));

                alert.setNegativeButton("Deny", (dialog, which) -> Toast.makeText(MainActivity.this, "permission declined", Toast.LENGTH_SHORT).show());

                // finally creating alert dialog , asking permission
                AlertDialog showAlert = alert.create();
                showAlert.show();
            }
            else {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMIT);
            }
        }

    }
}