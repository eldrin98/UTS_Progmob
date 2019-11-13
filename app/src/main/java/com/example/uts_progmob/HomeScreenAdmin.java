package com.example.uts_progmob;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeScreenAdmin extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logutogo, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutBtn: /*Intent logoutIntent = new Intent(MenuAdmin.this, LoginScreen.class);
            startActivity(logoutIntent);*/
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreenAdmin.this);
                builder.setMessage("Apakah anda ingin Logout?").setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Toast.makeText(HomeScreenAdmin.this, "Tidak jadi LogOut", Toast.LENGTH_SHORT).show();
                    }
                })
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Toast.makeText(HomeScreenAdmin.this, "Berhasil LogOut", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(HomeScreenAdmin.this,LogInActivity.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_admin);
    }
}
