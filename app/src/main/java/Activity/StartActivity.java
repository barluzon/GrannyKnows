package Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.utils.R;

public class StartActivity extends AppCompatActivity {
    private Button registerSignup;
  //  private ImageButton TreatmentsButton;
    private TextView welcomeText;
    private ImageButton guestButton;

    //private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();
        guestButton = findViewById(R.id.GuestButton);
        registerSignup = findViewById(R.id.registerSignup);
       // TreatmentsButton = findViewById(R.id.herbButton);
      //  login = findViewById(R.id.login);
        welcomeText = findViewById(R.id.WelcomeText);
        registerSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                //finish();
            }
        });
        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, GuestActivity.class));
            }
        });
/*
        TreatmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StartActivity.this, "In order to upload treatments a registration is required :)",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(StartActivity.this, AllTreatmentsActivity.class));
            }
        });

 */
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        welcomeText.setText("Welcome, visitor!");
    }
}
