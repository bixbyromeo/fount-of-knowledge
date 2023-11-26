package quiz.project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class main_con extends AppCompatActivity {
    TextView email2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_con);
        email2 = findViewById(R.id.emaill);
        ImageView goback = findViewById(R.id.Goback);
        goback.setOnClickListener(v -> {
            Intent intent = new Intent(main_con.this, MenuActivity.class);
            startActivity(intent);
        });
        email2.setOnClickListener(v -> {
            startActivity(new Intent(main_con.this,main_email.class));
        });




    }
}