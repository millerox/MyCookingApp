package com.example.mycookingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BasicActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optional,menu);
        firebaseAuth = FirebaseAuth.getInstance();

        MenuItem menu_login_logout = menu.findItem(R.id.menu_login_logout);
        MenuItem menu_resetPsw = menu.findItem(R.id.menu_resetPsw);
        if(firebaseAuth.getCurrentUser() == null) { // NOT Logged in
            menu_login_logout.setTitle("Login");
            menu_resetPsw.setEnabled(false); // disable reset password
        } else {
            menu_login_logout.setTitle("Logout");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.menu_allRecipes:
                intent = new Intent(BasicActivity.this, SingleRecipeActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_myRecipes:
                intent = new Intent(BasicActivity.this, MyRecipes.class);
                startActivity(intent);
                break;
            case R.id.menu_search:
                intent = new Intent(BasicActivity.this, SingleRecipeActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_add:
                intent = new Intent(BasicActivity.this, AddRecipe.class);
                startActivity(intent);
                break;
            case R.id.menu_login_logout:
                if(firebaseAuth.getCurrentUser()!= null){
                    //If logged in
                    firebaseAuth.signOut();
                } //Go to Login Page
                intent = new Intent(BasicActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_resetPsw:
                intent = new Intent(BasicActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
