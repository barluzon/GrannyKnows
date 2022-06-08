package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import Database.FirebaseBaseModel;
import Database.FirebaseUsers;
import com.example.utils.R;
import Objects.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Button logout;
    private ImageButton upload;
    private ImageButton allTreatments;
    private ImageButton allPremTreatments;
    private ImageButton tips;
    private ImageButton prof;
    private ImageButton exM;
    private EditText first_name;
    private TextView welcomeText;

    private FirebaseAuth auth;

    //private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        logout = findViewById(R.id.logout);
        upload = findViewById(R.id.uploadButton);
        allTreatments = findViewById(R.id.allTreatmentsButton);
        allPremTreatments = findViewById(R.id.allPremTreatmentsButton);
        prof = findViewById(R.id.profile);
        welcomeText = findViewById(R.id.WelcomeText);
        first_name = findViewById(R.id.user_firstName);
        tips = findViewById(R.id.tipsButton);
        exM = findViewById(R.id.exmarkButton);

        //check if premium user
        DatabaseReference DR = FirebaseBaseModel.getRef().child("Users").child(FirebaseAuth.getInstance().getUid());
        final String[] isPremium = new String[1];
        DR.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    if (s.getKey().equals("premium")) {
                        isPremium[0] = s.getValue().toString();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPremium[0] == "true") {
                    uploadDialog();
                    //startActivity(new Intent(MainActivity.this, PremiumTreatmentActivity.class));

                } else {
                    startActivity(new Intent(MainActivity.this, TreatmentActivity.class));
                }
            }
        });


        allTreatments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "All treatments!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, AllTreatmentsActivity.class));
            }
        });

        allPremTreatments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "All Premium treatments!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, AllPremiumTreatmentsActivity.class));
            }
        });
        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TipsActivity.class));
            }
        });
        exM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AttentionActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loggedInWelcome();
    }

    private void loggedInWelcome() {
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String id = user.getUid();
        DatabaseReference DR = FirebaseUsers.getUserByID(id);
        DR.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                String name = u.getFirstName();
                welcomeText.setText("Hello, " + name + "!");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (user != null) {
            String userName = user.getUid();
            String real = FirebaseBaseModel.getRef().child("firstName").child(userName).getKey();
            //welcomeText.setText("Hello," + real + "!");
        }
    }


    private void uploadDialog()
    {
        Dialog d = new Dialog(MainActivity.this);
        d.setContentView(R.layout.activity_choose_upload);
        d.setTitle("premium");

        Button premButton = d.findViewById(R.id.activity_dialog_premiumButton);
        Button regButton = d.findViewById(R.id.activity_dialog_regularButton);

        premButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                d.dismiss();
                //RegisterActivity.super.onBackPressed(); // Go previous page
                startActivity(new Intent(MainActivity.this, PremiumTreatmentActivity.class));
            }
        });

        regButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                d.dismiss();
                startActivity(new Intent(MainActivity.this, TreatmentActivity.class));

                //moveToPremiumPayment();
            }
        });
        d.show();
    }

}