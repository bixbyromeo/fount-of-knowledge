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

public class RegisterActivity extends AppCompatActivity {


    TextView btn;

   private EditText inputUsername,inputPassword,inputEmail,inputconfirmpassword;
   Button btnRegister;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn = findViewById(R.id.loginRedirectText);
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputpassword);
        inputEmail = findViewById(R.id.inputEmail);
        inputconfirmpassword = findViewById(R.id.inputconfirmpassword);

        btnRegister=findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cheakCrededentials();

            }
        });


        btn.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });


    }

    private void cheakCrededentials() {
        String username=inputUsername.getText().toString();
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        String confirmPassword=inputconfirmpassword.getText().toString();

        if(username.isEmpty() || username.length()<0){
            showError(inputUsername, "Please enter your Username!");
        } else if (email.isEmpty() || !email.contains("@")) {
            showError(inputEmail, "Invalid Email!");
        } else if (password.isEmpty() || password.length()<7) {
            showError(inputPassword, "Please set a password of more than 7 characters!");
        }else if(confirmPassword.isEmpty() || !confirmPassword.equals(password)){
            showError(inputconfirmpassword, "Please Confirm Password!");
        }else  {
            Toast.makeText(this,"Already Registered!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

}