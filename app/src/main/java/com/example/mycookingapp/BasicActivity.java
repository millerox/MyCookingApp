package com.example.mycookingapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class BasicActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optional,menu);
        firebaseAuth = FirebaseAuth.getInstance();

        MenuItem menu_login_logout = menu.findItem(R.id.menuItem_login_logout);
        MenuItem menu_resetPsw = menu.findItem(R.id.menuItem_resetPsw);
        MenuItem menu_addRecipe = menu.findItem(R.id.menuItem_add);
        if(firebaseAuth.getCurrentUser() == null) { // NOT Logged in
            menu_login_logout.setTitle("Login");
            menu_resetPsw.setEnabled(false); // disable reset password
            menu_addRecipe.setEnabled(false); // disable add recipe
        } else {
            menu_login_logout.setTitle("Logout");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.menuItem_add:
                redirectToActivity(this, AddRecipeActivity.class);
                break;
            case R.id.menuItem_login_logout:
                if(firebaseAuth.getCurrentUser()!= null){ //If logged in
                    firebaseAuth.signOut();
                }
                redirectToActivity(this,LoginActivity.class);
                break;
            case R.id.menuItem_resetPsw:
                redirectToActivity(this,ResetPasswordActivity.class);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void redirectToActivity(Activity firstActivity, Class finalActivity){
        Intent intent = new Intent(firstActivity,finalActivity);
        firstActivity.startActivity(intent);
    }
}
