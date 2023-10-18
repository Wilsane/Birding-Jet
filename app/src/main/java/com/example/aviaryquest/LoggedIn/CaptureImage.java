package com.example.aviaryquest.LoggedIn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aviaryquest.Data.Models.ImageVariables;
import com.example.aviaryquest.Data.Msg;
import com.example.aviaryquest.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class CaptureImage extends AppCompatActivity {

    String currentPhotoPath;
    public static final int CAMERA_REQUEST_CODE = 101;
    public static final int OPEN_CAM_REQUEST = 102;
    public static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_PICK_PHOTO = 1;
    private StorageTask uploadTask;
    String birdNameFromTrip;

    Msg msg;
    CardView btn_camera;
    TextView picName;
    ImageView selectedImage;
    Button btn_gallery,btn_upload;
    ProgressBar progressBar;
    PreviewView previewView;
    ProcessCameraProvider cameraProvider;
    private ListenableFuture<ProcessCameraProvider>cameraProviderFuture;
    private ImageCapture imageCapture;
    private Uri mImageUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        msg = new Msg();
        drawable = getResources().getDrawable(R.drawable.baseline_error_24);

        btn_camera = findViewById(R.id.btn_capCard);
        selectedImage=findViewById(R.id.displayImage);
        btn_gallery=findViewById(R.id.btn_gallery);
        btn_upload=findViewById(R.id.btn_savePic);
        picName=findViewById(R.id.picName);
        progressBar=findViewById(R.id.camProgressBar);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();


        //Retrieve the name of the bird from "Trips_RV_Adapter"
        // Retrieve the data from the Intent
        Intent fromTripsRv = getIntent();
        if (fromTripsRv != null && fromTripsRv.hasExtra("birdName")) {
            birdNameFromTrip = fromTripsRv.getStringExtra("birdName"); // Replace "key" with the same key used in the adapter
            // Use the receivedData as needed in your activity
            picName.setText(birdNameFromTrip);
        }

        //The location of the uploaded images in firestore
        storageReference= FirebaseStorage.getInstance().getReference("uploads");
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");

        //Upload into database
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checking if there's any picture in the process of uploading
                if(uploadTask!=null && uploadTask.isInProgress())
                    Toast.makeText(CaptureImage.this, "Upload in progress", Toast.LENGTH_LONG).show();
                else
                    upload();
            }
        });

        //Load picture from gallery
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        //Take a picture
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
            }
        });

    }

    //Redirect to phone's files app for images
    private void openFileChooser() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,REQUEST_PICK_PHOTO);
    }

    //Request for permission to use the users' camera
    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
        else{
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                openCamera();
            }
            else{
                Toast.makeText(this, "Camera is required", Toast.LENGTH_SHORT).show();
            }
        }

    }
    //Redirect the user to the camera
    private void openCamera() {
        Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, OPEN_CAM_REQUEST);
    }

    //Obtain the image from the camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==OPEN_CAM_REQUEST){
            Bitmap image= (Bitmap) data.getExtras().get("data");
            selectedImage.setImageBitmap(image);
        }
        if(requestCode==REQUEST_PICK_PHOTO && data !=null
        && data.getData()!=null){
            mImageUri=data.getData();
            Toast.makeText(this, "This is uri:\t"+mImageUri, Toast.LENGTH_LONG).show();
            Picasso.get().load(mImageUri).into(selectedImage);
        }
    }


    private File createImageFile()throws IOException{
        //Create image file name
        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName="JPEG_"+timeStamp+"_";
        File storageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image=File.createTempFile(
                imageFileName,/*prefix*/
                ".png",/*Suffix*/
                storageDir /*Directory*/
        );
        //Save file path
        currentPhotoPath=image.getAbsolutePath();
        return image;
    }

    //Retrieve the extension of the uploaded file
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    //Upload the database
    private void upload() {
        if(picName.getText().toString().trim().isEmpty() ||
                picName.getText().toString()==null||picName.getText().toString()==""){
            picName.setError("*Required");
            return;
        }
        else if(mImageUri.toString().isEmpty() || mImageUri.toString()==null)
        {
            Toast.makeText(this, "No picture to upload", Toast.LENGTH_SHORT).show();
        }
        else{
            StorageReference fileRef=storageReference.child(System.currentTimeMillis()+
                    "."+getFileExtension(mImageUri));

            //To check if the "SAVE" is currently running, to prevent the user from pressing "Save" multiple times, saving one picture
            uploadTask=fileRef.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    }, 500);

                    // Get the download URL of the uploaded file
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadUri = uri.toString();
                            ImageVariables upload = new ImageVariables(birdNameFromTrip, downloadUri, currentUser.getUid());

                            // Creating a unique Id
                            String picId = databaseReference.push().getKey();
                            // Saving into the database
                            databaseReference.child(picId).setValue(upload);

                            Drawable drawable = getResources().getDrawable(R.drawable.baseline_check_circle_24);
                            msg.display(CaptureImage.this, "picture successfully uploaded", birdNameFromTrip, drawable, "success");
                            selectedImage.setImageDrawable(null);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            msg.display(CaptureImage.this, e.getMessage(), "Picture not uploaded", drawable, "err");
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    msg.display(CaptureImage.this, e.getMessage(), "Picture not uploaded", drawable, "err");
                    Toast.makeText(CaptureImage.this, "Picture not uploaded:\t" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressBar.setProgress((int) progress);
                }
            });

        }
    }
}