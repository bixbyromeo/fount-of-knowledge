package quiz.project;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView resultText, scoreText;
    private Button backToMainMenuButton;
    private ImageView trophyImage;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Initialize views
        resultText = findViewById(R.id.resultText);
        scoreText = findViewById(R.id.scoreText);
        backToMainMenuButton = findViewById(R.id.backToMainMenuButton);
        trophyImage = findViewById(R.id.trophyImage);

        // Get the score from the intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);

        // Display the result
        resultText.setText("คะแนนที่ทำได้:");
        scoreText.setText( score + "/15" + " คะแนน");

        // Set up click listener for the back to main menu button
        backToMainMenuButton.setOnClickListener(v -> {
            // Navigate back to the main menu
            Intent mainMenuIntent = new Intent(ResultActivity.this, MenuActivity.class);
            startActivity(mainMenuIntent);
            finish();
        });
    }
}
