package mfs.ashish.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
      ImageView imageView;
       boolean state = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image);

        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
             runFlashLight();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
              Toast.makeText(getBaseContext(), "Please give permission.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();
    }

    private void runFlashLight(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!state){
                    try {
                        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                        String cameraId = cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(cameraId,true);
                        state = true;
                        imageView.setImageResource(R.drawable.torch_on);
                    }catch (CameraAccessException e){
                        Toast.makeText(getBaseContext(),"Camira is not available",Toast.LENGTH_SHORT).show();
                    }

                }else {


                    try {
                        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                        String cameraId = cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(cameraId,false);
                        state = false;
                        imageView.setImageResource(R.drawable.torch_off);
                    }catch (CameraAccessException e){
                        Toast.makeText(getBaseContext(),"Camira is not available",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

}