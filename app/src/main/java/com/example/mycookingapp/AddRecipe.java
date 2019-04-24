package com.example.mycookingapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class AddRecipe extends AppCompatActivity {

    public static final int REQUEST_CAPTURE = 1;
    public static final int REQUEST_TAKE_PHOTO = 1;


    StorageReference storageRef;
    FirebaseAuth auth;
    FirebaseUser currentUser;

    ImageView photo;
    EditText photoName;
    Button btn_click;
    String strName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        //Initializing variables:
        photo = findViewById(R.id.photo);
        btn_click = findViewById(R.id.btn_takePhoto);
        photoName = findViewById(R.id.txtName);

        storageRef = FirebaseStorage.getInstance().getReference();

        hasCamera();
        checkFilesPermissions();
    }

    // Take Thumbnail image photo:
    public void takePhoto(View view)
    {
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(photoIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(photoIntent,REQUEST_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CAPTURE && resultCode == RESULT_OK)
        {
            //Get photo name/description
            strName = photoName.getText().toString();
            //Take photo
            Bundle extras = data.getExtras();
            Bitmap myPhoto = (Bitmap) extras.get("data");
            //Set imageview to photo
            photo.setImageBitmap(myPhoto);
            //Save image to the phone gallery
            String URL = MediaStore.Images.Media.insertImage(getContentResolver(),myPhoto,strName,strName);

            //insertImage method was depricated

           // Uri URI = Uri.fromFile(new File(""+URL));

            //Save image to Firebase:
            //Get reference
//            Uri file = Uri.fromFile(new File(""+URL));
//            StorageReference photoRef = storageRef.child("images/recipes/"+strName+".jpg");
//
//            photoRef.putFile(file)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            // Get a URL to the uploaded content
//                            // Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                            Toast.makeText(AddRecipe.this,"FAILED",Toast.LENGTH_LONG).show();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            // Handle unsuccessful uploads
//                            Toast.makeText(AddRecipe.this,"FAILED",Toast.LENGTH_LONG).show();
//                        }
//                    });
        }
    }


    public boolean hasCamera()
    {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public void checkFilesPermissions(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = AddRecipe.this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += AddRecipe.this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {
                this.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        }else{
            Log.d("debug", "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }
}

