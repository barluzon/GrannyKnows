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

public class PremiumTreatmentActivity extends AppCompatActivity {
    private Button uploadTreatment;
    private EditText treatName;
    private EditText ingredients;
    private EditText preparation;
    private EditText size;
    private EditText price;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_premium_treatment);
        uploadTreatment = findViewById(R.id.uploadTreatment);
        treatName = findViewById(R.id.treatment_name);
        ingredients = findViewById(R.id.ingredients);
        preparation = findViewById(R.id.preparation);
        size = findViewById(R.id.size);
        price = findViewById(R.id.price);
        auth = FirebaseAuth.getInstance();
        uploadTreatment.setOnClickListener(new View.OnClickListener() {
            String nickname = "";

            @Override
            public void onClick(View v) {

                String txt_treatName = treatName.getText().toString();
                String txt_ingredients = ingredients.getText().toString();
                String txt_preparation = preparation.getText().toString();
                String txt_size = size.getText().toString();
                String txt_price = price.getText().toString();

                if (txt_treatName.isEmpty() || txt_ingredients.isEmpty() || txt_preparation.isEmpty()
                        || txt_size.isEmpty() || txt_price.isEmpty()) {
                    Toast.makeText(PremiumTreatmentActivity.this, "Please fill all!", Toast.LENGTH_SHORT).show();
                }
                if (!txt_price.matches("[0-9]+") && txt_price != "0") {
                    Toast.makeText(PremiumTreatmentActivity.this, "Price can contain only numbers and must be greater than 0!", Toast.LENGTH_SHORT).show();

                } else {

                    FirebaseUser user = auth.getCurrentUser();
                    nickname = user.getDisplayName();
                    String userID = auth.getCurrentUser().getUid();

                    final String[] userPhone = {""};
                    DatabaseReference DR = FirebaseUsers.getUserByID(userID);
                    DR.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            User u = snapshot.getValue(User.class);
                            userPhone[0] = u.getPhoneNumber();

                            PremiumTreatment pTreatment = new PremiumTreatment(userID, userPhone[0], nickname, txt_treatName, txt_ingredients, txt_preparation, txt_size, txt_price);
                            updateUI(user, pTreatment);
                            Toast.makeText(PremiumTreatmentActivity.this, "Premium Treatment Uploaded! \n", Toast.LENGTH_SHORT).show();

                            NotificationCompat.Builder builder = new NotificationCompat.Builder(PremiumTreatmentActivity.this, "My Notification");
                            builder.setContentTitle("New treatment uploaded!");
                            builder.setContentText("Hello! \n Just let you know that new treatment was upload:)");
                            builder.setSmallIcon(R.drawable.ic_plus);
                            builder.setAutoCancel(true);

                            NotificationManagerCompat mangerCompat = NotificationManagerCompat.from(PremiumTreatmentActivity.this);
                            mangerCompat.notify(1, builder.build());
                            startActivity(new Intent(PremiumTreatmentActivity.this, MainActivity.class));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });

                }
            }
        });

    }

    void updateUI(FirebaseUser user, PremiumTreatment ptreatment) {
        System.err.println(user.getEmail());
        System.err.println(user.getDisplayName()); // don't display asynchronic maybe
        FirebaseUsers.addPremiumTreatmentToDB(ptreatment);
    }

}



