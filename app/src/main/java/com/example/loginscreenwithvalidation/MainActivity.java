package com.example.loginscreenwithvalidation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final String EMAIL_KEY = "com.example.loginscreenwithvalidation.EMAIL";
    EditText email, password;
    Button login;
    TextView attempt, replaceButton;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    int attempt_count = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        attempt = findViewById(R.id.textViewAttempt);
        replaceButton = findViewById(R.id.textViewReplaceButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String EMAIL = email.getText().toString().trim();
                String PASS = password.getText().toString().trim();

                if (EMAIL.isEmpty()) {
                    email.setError("Email required");
                    email.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()) {
                    email.setError("Enter valid email address");
                    email.requestFocus();
                    return;
                }

                if (PASS.isEmpty()) {
                    password.setError("Password required");
                    password.requestFocus();
                    return;
                }

                if (PASS.length() < 6) {
                    password.setError("Minimum lenght of password is 6");
                    password.requestFocus();
                    return;
                }

                if (!PASSWORD_PATTERN.matcher(PASS).matches()) {
                    password.setError("Password too weak, Use special character");
                    password.requestFocus();
                    return;
                }

                if (EMAIL.equals("admin@gmail.com") && PASS.equals("a@123#")) {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra(EMAIL_KEY,EMAIL);
                    startActivity(intent);

                    //startActivity(new Intent(MainActivity.this, HomeActivity.class));


                } else {
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                    attempt_count--;
                    attempt.setVisibility(View.VISIBLE);
                    attempt.setText("Attempt left " + attempt_count);

                    if (attempt_count == 0) {
                        login.setVisibility(View.INVISIBLE);
                        replaceButton.setVisibility(View.VISIBLE);
                        attempt.setVisibility(View.INVISIBLE);
                    }

                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
