package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import Database.FirebaseUsers;
import Objects.PremiumTreatment;
import com.example.utils.R;
import Objects.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class BuyTreatmentActivity extends AppCompatActivity {
    private Button buyTreatment;
    private EditText firstName;
    private EditText lastName;
    private EditText city;
    private EditText address;
    private EditText phone;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_treatment);
        buyTreatment = findViewById(R.id.button_continue);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        city = findViewById(R.id.city);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);

        auth = FirebaseAuth.getInstance();


        buyTreatment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String txt_fName = firstName.getText().toString();
                String txt_lname = lastName.getText().toString();
                String txt_city = city.getText().toString();
                String txt_address = address.getText().toString();
                String txt_phone = phone.getText().toString();

                if (txt_fName.isEmpty() || txt_lname.isEmpty() || txt_city.isEmpty()
                        || txt_address.isEmpty()|| txt_phone.isEmpty() ) {
                    Toast.makeText(BuyTreatmentActivity.this, "Please fill all!", Toast.LENGTH_SHORT).show();
                }
                if (!txt_phone.matches("[0-9]+") || txt_phone.length() != 10) {
                    Toast.makeText(BuyTreatmentActivity.this, "Phone Number can contain only numbers and must be 10 digits!", Toast.LENGTH_SHORT).show();

                } else {

                    String userID = auth.getCurrentUser().getUid();

                    DatabaseReference DR = FirebaseUsers.getUserByID(userID);
                    DR.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            startActivity(new Intent(BuyTreatmentActivity.this, TreatmentPaymentActivity.class));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });

                }
            }
        });

    }

}



