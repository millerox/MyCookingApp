package com.example.mycookingapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;

public class AddRecipeActivity extends AppCompatActivity {

    public static final int REQUEST_CAPTURE = 1;
    public static final int REQUEST_TAKE_PHOTO = 1;


    StorageReference storageRef;
    FirebaseAuth auth;
    FirebaseUser currentUser;

    ImageView img_photo;
    EditText et_name;
    EditText et_ingredients;
    EditText et_steps;
    Button btn_addRecipe;
    String strName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        //Initializing variables:
        img_photo = findViewById(R.id.img_addRecipe_photo);
        et_name = findViewById(R.id.et_addRecipe_name);
        et_ingredients = findViewById(R.id.et_addRecipe_ingredients);
        et_steps = findViewById(R.id.et_addRecipe_steps);
        btn_addRecipe = findViewById(R.id.btn_addRecipe_add);

        auth = FirebaseAuth.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        hasCamera();
        checkFilesPermissions();
    }

    // Take Thumbnail image img_photo:
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
            //Get img_photo name/description
            strName = et_name.getText().toString();
            //Take img_photo
            Bundle extras = data.getExtras();
            Bitmap myPhoto = (Bitmap) extras.get("data");
            //Set imageview to img_photo
            img_photo.setImageBitmap(myPhoto);
            //Save image to the phone gallery
            String URL = MediaStore.Images.Media.insertImage(getContentResolver(), myPhoto, strName, strName);
            Uri URI = Uri.parse(URL) ;

            //Save image to Firebase: DO THIS ONLY after button click
              // TODO:Check for authenication (only authenicated users can add photos)

             //  Get reference to the storage (path, where to save)
            StorageReference photoRef = storageRef.child("images/recipes/"+strName+".jpg");
            InputStream stream;
            try {
                stream = this.getContentResolver().openInputStream(URI);
                UploadTask uploadTask = photoRef.putStream(stream);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URI to the uploaded content
                        Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                        // TODO SAVE URI TO DATABASE
                        Log.d("AddRecepie", "Success TO UPLOAD IMAGE");

                    }
                }).addOnFailureListener(AddRecipeActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("AddRecepie", "Failure TO UPLOAD IMAGE");
                    }
                });
            }
            catch (Exception e) {
                e.fillInStackTrace();
            }
        }
    }


    public boolean hasCamera()
    {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public void checkFilesPermissions(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = AddRecipeActivity.this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += AddRecipeActivity.this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {
                this.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        }else{
            Log.d("debug", "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }
}

