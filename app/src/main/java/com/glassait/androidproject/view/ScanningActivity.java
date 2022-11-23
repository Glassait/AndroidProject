package com.glassait.androidproject.view;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.glassait.androidproject.R;
import com.glassait.androidproject.common.utils.LocalData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;

public class ScanningActivity extends AppCompatActivity {
    // to handle the permissions
    private static final int CAMERA_REQUEST_CODE = 101;
    // private static final int STORAGE_REQUEST_CODE = 100;

    // array of required permissions
    private String[] cameraPermissions;
    // private String[] storagePermissions;

    // Uri from the barcode
    private Uri imageUri = null;

    // UI Views
    // private MaterialButton cameraBtn;
    private ImageView      imageIv;
    private MaterialButton scanBtn;
    private TextView       resultTv;

    private BarcodeScannerOptions barcodeScannerOptions;
    private BarcodeScanner        barcodeScanner;

    //
    private static final String TAG = "MAIN_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);

        // init UI Views
        // cameraBtn = findViewById(R.id.cameraBtn);
        imageIv = findViewById(R.id.imageIv);
        scanBtn = findViewById(R.id.scanBtn);
        resultTv = findViewById(R.id.resultTv);

        cameraPermissions =
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        barcodeScannerOptions =
                new BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                                                   .build();
        barcodeScanner = BarcodeScanning.getClient(barcodeScannerOptions);
        /**
         // handle cameraBtn click
         cameraBtn.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        checkCameraPermissions();

        }
        });**/

        MaterialButton compareBtn = findViewById(R.id.compare_btn);

        // handle scanBtn click
        scanBtn.setOnClickListener(v -> {
            checkCameraPermissions();
            int cameraPermission = ContextCompat.checkSelfPermission(
                    ScanningActivity.this,
                    Manifest.permission.CAMERA
            );

            if (cameraPermission == PackageManager.PERMISSION_GRANTED && imageUri == null) {
                pickImageCamera();
            } else if (cameraPermission == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(
                             ScanningActivity.this,
                             "Permission denied",
                             Toast.LENGTH_SHORT
                     )
                     .show();
            } else if (imageUri == null) {
                Toast.makeText(
                             ScanningActivity.this,
                             "Take image first...",
                             Toast.LENGTH_SHORT
                     )
                     .show();
            } else {
                detectResultFromImage();
                compareBtn.setVisibility(View.VISIBLE);
            }
        });

        compareBtn.setOnClickListener(v -> {
            Intent intent = new Intent(
                    getApplicationContext(),
                    CompareActivity.class
            );
            startActivity(intent);
        });
    }

    private void detectResultFromImage() {
        try {
            InputImage inputImage = InputImage.fromFilePath(
                    this,
                    imageUri
            );
            Task<List<Barcode>> barcodeResult = barcodeScanner.process(inputImage)
                                                              .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                                                                  @Override
                                                                  public void onSuccess(
                                                                          List<Barcode> barcodes) {
                                                                      extractBarCodeQRCodeInfo(barcodes);
                                                                  }
                                                              })
                                                              .addOnFailureListener(new OnFailureListener() {
                                                                  @Override
                                                                  public void onFailure(
                                                                          @NonNull Exception e) {
                                                                      Toast.makeText(
                                                                                   ScanningActivity.this,
                                                                                   "Failed scanning due to "
                                                                                           + e.getMessage(),
                                                                                   Toast.LENGTH_SHORT
                                                                           )
                                                                           .show();
                                                                  }
                                                              });
        } catch (Exception e) {
            Toast.makeText(
                         this,
                         "Failed due to " + e.getMessage(),
                         Toast.LENGTH_SHORT
                 )
                 .show();
        }
    }

    private void extractBarCodeQRCodeInfo(List<Barcode> barcodes) {
        for (Barcode barcode : barcodes) {
            Rect    bounds  = barcode.getBoundingBox();
            Point[] corners = barcode.getCornerPoints();

            String rawValue = barcode.getRawValue();
            Log.d(
                    TAG,
                    "extractBarCodeQRCodeInfo: rawValue: " + rawValue
            );

            resultTv.setText("Scanned: " + rawValue);
            LocalData.getInstance()
                     .setScanningCode(rawValue);
        }
    }

    private void pickImageCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(
                MediaStore.Images.Media.TITLE,
                "Sample Title"
        );
        contentValues.put(
                MediaStore.Images.Media.DESCRIPTION,
                "Sample Image Description"
        );

        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
        );

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(
                MediaStore.EXTRA_OUTPUT,
                imageUri
        );
        cameraActivityResultLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> cameraActivityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            // receive image from camera
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Intent data = result.getData();
                                Log.d(
                                        TAG,
                                        "onActivityResult: imageUri: " + imageUri
                                );
                                imageIv.setImageURI(imageUri);
                            } else {
                                // cancelled
                                Toast.makeText(
                                             ScanningActivity.this,
                                             "Cancelled...",
                                             Toast.LENGTH_SHORT
                                     )
                                     .show();
                            }

                        }
                    }
            );

    private void checkCameraPermissions() {
        int cameraPermission = ContextCompat.checkSelfPermission(
                ScanningActivity.this,
                Manifest.permission.CAMERA
        );
        int storagePermission = ContextCompat.checkSelfPermission(
                ScanningActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        );

        if (cameraPermission != PackageManager.PERMISSION_GRANTED
                && storagePermission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(
                ScanningActivity.this,
                cameraPermissions,
                CAMERA_REQUEST_CODE
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        );

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                // Permission is granted. Continue the action
                Toast.makeText(
                             ScanningActivity.this,
                             "Permission Granted",
                             Toast.LENGTH_SHORT
                     )
                     .show();
            } else {
                // Permission is denied. Continue the action is not possible.
                Toast.makeText(
                             ScanningActivity.this,
                             "Permissions required.",
                             Toast.LENGTH_SHORT
                     )
                     .show();
            }
        }

    }

}