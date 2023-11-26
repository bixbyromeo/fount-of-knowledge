package quiz.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    Button btnDailylogin,btnAdswatch;

    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        myDialog = new Dialog(this);
        btnDailylogin = findViewById(R.id.btnDailylogin);
        btnAdswatch = findViewById(R.id.btnAdswatch);
        ImageView backback = findViewById(R.id.imageView3);
        Button Logout = findViewById(R.id.button5);
        btnDailylogin.setOnClickListener(v -> {
            myDialog.setContentView(R.layout.popup);
            myDialog.show();

            TextView TextHello = findViewById(R.id.textViewAnswer);
            TextHello.setText("01");
        });

        btnAdswatch.setOnClickListener(v -> {
            myDialog.setContentView(R.layout.popupads);
            myDialog.show();

            TextView TextHello = findViewById(R.id.textViewAnswer);
            TextHello.setText("02");
        });
        backback.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MenuActivity.class);
            startActivity(intent);
        });
        Logout.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

}
