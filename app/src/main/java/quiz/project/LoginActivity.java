package quiz.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView btn;
    EditText inputEmail, inputPassword;
    Button btnLogin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn = findViewById(R.id.signupRedirectText);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> cheakCrededentials());


        btn.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

    }
    private void cheakCrededentials() {
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();

        if (email.isEmpty() || !email.contains("@")) {
            showError(inputEmail, "Invalid Email!");
        } else if (password.isEmpty() || password.length()<7) {
            showError(inputPassword, "Please set a password of more than 7 characters!");
        }else  {
            Toast.makeText(this,"Login Successful!!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(intent);
        }

    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}