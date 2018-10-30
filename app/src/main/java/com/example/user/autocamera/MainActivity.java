package com.example.user.autocamera;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.CAMERA
    };
    String[] cameralist;
    ListView lv;
    ListAdapter adapter ;
    String choice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }
        lv=(ListView)findViewById(R.id.listcamera);
        setupCamera();


    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    private void setupCamera() {
        //获取摄像头的管理者CameraManager
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            cameralist=manager.getCameraIdList();
            adapter= new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1 ,cameralist);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener( onClickListView );
            choice=cameralist[0];
            //遍历所有摄像头
            for (String cameraId: cameralist) {
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                Log.d("camera :",cameraId);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    public void service_clk(View view){
        Toast.makeText(this,"service start ",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this,AutoTakePictureService.class);
        startService(intent);
    }
    public void setcamera_clk(View view){
        Toast.makeText(this,"set camera ",Toast.LENGTH_SHORT).show();

    }
    public void takephoto_clk(View view){
        Toast.makeText(this,"take photo ",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setAction("SERVICE");
        intent.putExtra("Msg", "Hi");
        sendBroadcast(intent);
    }
    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Toast 快顯功能 第三個參數 Toast.LENGTH_SHORT 2秒  LENGTH_LONG 5秒
            String str;
            Toast.makeText(MainActivity.this,"點選第 "+(position +1) +" 個 \n內容："+cameralist[position], Toast.LENGTH_SHORT).show();
            choice=cameralist[position];
        }
    };


}
