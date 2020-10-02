package com.example.cameraapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    Button mCaptureBtn;
    ImageView mImageView;
    public static final int PERMISSION_CODE = 1000;
    URI image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.image_view);
        mCaptureBtn = findViewById(R.id.capture_image_btn);
        //button clicking method

        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if >= marshmallow you need to request runtime permission
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    String [] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    //show popup to request permissions
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else{
                        //permission already has been given
                        activateCamera();

                    }
                }

                else{// operating system is less than marshmallow
                    activateCamera();
                }
            }
        });

    }
    private void activateCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From CApplication");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


    }

    // to handle the permission
    @Override // only called when the user allows or denys from permission popup
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch(requestCode){
        case PERMISSION_CODE:{
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){ //permission was granted
            activateCamera();
            }
            else{ //permission denied
                Toast.makeText(this, "Permission has been Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    }
}
