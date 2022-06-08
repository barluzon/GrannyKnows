package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.utils.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.graphics.Color;
import android.os.Build;
import android.view.WindowManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText email;
    private EditText password;
    private Button login;
    private FirebaseAuth auth;
    private Button registerB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setStatusBarTransparent(LoginActivity.this);

        login = findViewById(R.id.button_signin);
        registerB = findViewById(R.id.button_signup);
        email = findViewById(R.id.et_Email);
        password = findViewById(R.id.et_password);
        auth = FirebaseAuth.getInstance();
        registerB.setOnClickListener(this);
        login.setOnClickListener(this);

    }

    private void setStatusBarTransparent(AppCompatActivity activity) {
        //Make Status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //Make status bar icons color dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            activity.getWindow().setStatusBarColor(Color.WHITE);
        }
    }

  @Override
  public void onClick(View ClickedButton){
     // if(backButton.equals(ClickedButton)){
      //    LoginActivity.super.onBackPressed();
     // }
       if(registerB.equals(ClickedButton)){

           startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

      }
      else if (login.equals(ClickedButton)){

          String txt_email = email.getText().toString();
          String txt_password = password.getText().toString();
          if (!validation(txt_email, txt_password)){
              return;
          }
          //no one is logged in already
          if (auth.getCurrentUser() == null){

              loginUser(txt_email,txt_password);
          }
          // already logged in
          else{
              FirebaseAuth.getInstance().signOut();
              registerB.setEnabled(true);
              registerB.setVisibility(View.VISIBLE);
              email.setEnabled(true);
              password.setEnabled(true);
              login.setText("Login");
          }
      }

  }
    private void loginUser(String email, String password){
        this.auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    //finish();
                    FirebaseUser user = auth.getCurrentUser();
                    updateUI(user);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Failed",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    private boolean validation(String email, String password)
    {
        if (email.isEmpty())
        {

            this.email.setError("Please enter an Email");
            //Toast.makeText(LoginActivity.this, "Please enter an email!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty())
        {
            this.password.setError("Please enter a password");
            return false;
        }
        if (!ValidateUserEmail(email))
        {
            this.email.setError("Incorrect Email!");
            return false;
        }
        if (password.length() < 6)
        {
            this.password.setError("Wrong password!");
            return false;
        }
        return true;
    }
    public static boolean ValidateUserEmail(String email)
    {
        String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        // user_emailEditText.setError("Email is invalid");
        return matcher.matches();
    }

    private void updateUI(FirebaseUser user)
    {
        if (user != null)
        {
            registerB.setEnabled(false);
            registerB.setVisibility(View.GONE);
            email.setEnabled(false);
            password.setEnabled(false);
            login.setText("Log off");
            //LoginActivity.super.onBackPressed(); // get back
        }
        else
        {
            email.setError("Email or password is incorrect");
            password.setError("Email or password is incorrect");
            password.setText("");
            email.setText("");
        }
    }

}