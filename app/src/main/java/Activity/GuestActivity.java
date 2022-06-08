package Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utils.R;


public class GuestActivity extends AppCompatActivity {
   // private ImageButton signupButton;
    private ImageButton treaments;
    private ImageButton mark;
    private ImageButton tips;
    private ImageButton sign;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        getSupportActionBar().hide();
        //signupButton = findViewById(R.id.signupB);
        treaments = findViewById(R.id.TreatB);
        sign = findViewById(R.id.signupB);
        mark = findViewById(R.id.exMark);
        tips = findViewById(R.id.idea);
        welcomeText = findViewById(R.id.textView);
        treaments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GuestActivity.this, "all treatments!",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(GuestActivity.this, AllTreatmentsActivity.class));
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuestActivity.this, RegisterActivity.class));
            }
        });
        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuestActivity.this, TipsActivity.class));
            }
        });

        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuestActivity.this, AttentionActivity.class));
            }
        });
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        welcomeText.setText("Welcome, Guest!");
    }
}