package com.example.yinzhong.mytorch;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends Activity {

    Button control = null;
    boolean flashOn = false;
    Camera mCamera = null;

    //android.hardware.camera2.CameraDevice
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        control = (Button) findViewById(R.id.control);
        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flashOn) {
                    openCameraTorch();
                    control.setText("关灯");
                    control.setBackgroundResource(R.drawable.close);
                } else {
                    closeCameraTorch();
                    control.setText("开灯");
                    control.setBackgroundResource(R.drawable.button);
                }
            }
        });
    }

    private void openCameraTorch() {
        Camera.Parameters params;
        mCamera = Camera.open();
        Camera.getNumberOfCameras();
        System.out.println("Camera Num:" + Camera.getNumberOfCameras());
        params = mCamera.getParameters();
        if (params == null) {
            System.err.println("Camera Param err null!");
            return;
        }
        List<String> flashmodes = params.getSupportedFlashModes();
        if (!Camera.Parameters.FLASH_MODE_TORCH.equals(params.getFlashMode())) {
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(params);
        }
        flashOn = true;
    }

    private void closeCameraTorch() {
        System.out.println("camera release");
        Camera.Parameters params= mCamera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        mCamera.setParameters(params);
        mCamera.release();
        flashOn = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(flashOn)
        {
            closeCameraTorch();
        }
        System.exit(0);
    }
}
