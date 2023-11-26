package quiz.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class main_email extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_email);
        Button email = findViewById(R.id.saveBtn);
        email.setOnClickListener(v -> {
            Intent intent = new Intent(main_email.this, main_con.class);
            startActivity(intent);
        });
    }
}