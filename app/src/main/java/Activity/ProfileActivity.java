package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView MyProf;
    private ImageView granny;
    private TextView fname;
    private TextView lname;
    private TextView nickname;
    private TextView email;
    private TextView phone;
    private TextView rating;
    private FirebaseAuth mAuth;
    private ImageButton logoff;
    private ImageButton back;
    private ImageButton myTreats;
    private TextView myTreatsTxt;
    private TextView isPremiumText;
    private Button becomePremium;
    //final String[] isPremium = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        activateText();
        init();

        //check if premium user
        DatabaseReference DR = FirebaseBaseModel.getRef().child("Users").child(FirebaseAuth.getInstance().getUid());
        final String[] isPremium = new String[1];
        DR.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    if (s.getKey().equals("premium")) {
                        isPremium[0] = s.getValue().toString();
                        isPremiumText.setText("Premium: " + isPremium[0]);
                        if(isPremium[0] == "true") {
                            becomePremium.setVisibility(View.GONE);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        becomePremium = findViewById(R.id.upgradeToPremium);
        becomePremium.setOnClickListener(this);

       // if(isPremium[0] == "true") {
         //   becomePremium.setVisibility(View.GONE);
     //   }

        myTreats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPremium[0] == "true") {
                    uploadDialog();
                    //startActivity(new Intent(MainActivity.this, PremiumTreatmentActivity.class));

                } else {
                    startActivity(new Intent(ProfileActivity.this, AllTreatmentsActivity.class));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
    if (logoff.equals(v))
    {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(ProfileActivity.this, "Logged Out!",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ProfileActivity.this, StartActivity.class));
    }
    if(back.equals(v)){
        ProfileActivity.super.onBackPressed();
    }
    if(myTreats.equals(v)){
        Intent i = new Intent(ProfileActivity.this,AllTreatmentsActivity.class);
        i.putExtra("personal",true);
        startActivity(i);
    }
        becomePremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfileActivity.this, PaymentActivity.class));
            }
        });
    }
    private void activateText()
    {
        fname = findViewById(R.id.firstName);
        lname = findViewById(R.id.lastName);
        nickname = findViewById(R.id.nickName);
        email = findViewById(R.id.email_prof);
        phone = findViewById(R.id.phone_num);
        rating = findViewById(R.id.rate);
        MyProf = findViewById(R.id.myProfile);
        granny = findViewById(R.id.grannyImg);
        logoff = findViewById(R.id.logoffB);
        logoff.setOnClickListener(this);
        back = findViewById(R.id.backButton);
        back.setOnClickListener(this);
        myTreats = findViewById(R.id.treats);
        myTreats.setOnClickListener(this);
        myTreatsTxt = findViewById(R.id.myTreats);
        isPremiumText = findViewById(R.id.premium);
        //isPremiumText.setOnClickListener(this);


    }
    private void init(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String user_email = user.getEmail();
        String id = user.getUid();
        DatabaseReference DR = FirebaseUsers.getUserByID(id);
        DR.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.getValue(User.class);
                fname.setText("First name: " + u.getFirstName());
                lname.setText("Last name: " + u.getLastName());
                nickname.setText("Nickname: " + u.getNickname());
                email.setText("Email: " + u.getEmail());
                phone.setText("Phone number: " + u.getPhoneNumber());
                rating.setText("Rates: " + u.getRate());
               // isPremiumText.setText("Premium: " + u.getIsPremium());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void uploadDialog()
    {
        Dialog d = new Dialog(ProfileActivity.this);
        d.setContentView(R.layout.activity_choose_treats_to_show);
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
                //startActivity(new Intent(ProfileActivity.this, AllPremiumTreatmentsActivity.class));
                Intent i = new Intent(ProfileActivity.this, AllPremiumTreatmentsActivity.class);
                i.putExtra("personal",true);
                startActivity(i);
            }
        });

        regButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                d.dismiss();
                //startActivity(new Intent(ProfileActivity.this, AllTreatmentsActivity.class));
                Intent i = new Intent(ProfileActivity.this,AllTreatmentsActivity.class);
                i.putExtra("personal",true);
                startActivity(i);
                //moveToPremiumPayment();
            }
        });

        d.show();
    }


}