package com.example.crop.crop;

//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//}


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView imageview;
    private ImageView camera_imageview;
    public static final int REQUEST_CODE_UPDATE_PIC = 0x1;
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageview = (ImageView) findViewById(R.id.library_image);
        camera_imageview = (ImageView) findViewById(R.id.camera_image);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStoragePermission();
            }
        });

        camera_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCameraPermission();
            }
        });


    }

    private void actionProfilePic(String action) {
        Intent intent = new Intent(this, CropScreen.class);
        intent.putExtra("ACTION", action);
        startActivityForResult(intent, REQUEST_CODE_UPDATE_PIC);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.finish();
        return true;
    }

    private void requestCameraPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            String action = "action-camera";
                            actionProfilePic(action);
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {

                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
//                            String value= Constants.PicModes.CAMERA;
//                            showSettingsDialog(value);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            String action = "action-gallery";
                            actionProfilePic(action);
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {

                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
//                            String value = Constants.PicModes.GALLERY;
//                            showSettingsDialog(value);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

//    private void showSettingsDialog(String value) {
//        String permission_message = getResources().getString(R.string.permission_message);
//        String permission_setting_message = getResources().getString(R.string.permission_setting_message);
//        String cancatString = permission_message + value + permission_setting_message;
//        AlertDialog.Builder builder = new AlertDialog.Builder(ImagePickerActivity.this);
//        int TEXT_SIZE = 17;
//        builder.setTitle(R.string.permission);
//        builder.setMessage(cancatString);
//        builder.setPositiveButton(R.string.open_settings, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//                openSettings();
//            }
//        });
//        builder.setNegativeButton(R.string.okay, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//        Button negative_button = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
//        negative_button.setAllCaps(false);
//        negative_button.setTextSize(TEXT_SIZE);
//        Button positive_button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
//        positive_button.setAllCaps(false);
//        positive_button.setTextSize(TEXT_SIZE);
//    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


}
