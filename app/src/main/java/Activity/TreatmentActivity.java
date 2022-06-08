package Activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
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
import com.example.utils.R;
import Objects.Treatment;
import Objects.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class TreatmentActivity extends AppCompatActivity{
    private Button uploadTreatment;
    private EditText treatName;
    private EditText ingredients;
    private EditText preparation;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_treatment);
        uploadTreatment = findViewById(R.id.uploadTreatment);
        treatName = findViewById(R.id.treatment_name);
        ingredients = findViewById(R.id.ingredients);
        preparation = findViewById(R.id.preparation);
        auth = FirebaseAuth.getInstance();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification","My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manger = getSystemService(NotificationManager.class);
            manger.createNotificationChannel(channel);
        }
        uploadTreatment.setOnClickListener(new View.OnClickListener() {
            String nickname = "";

            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();

                String txt_treatName = treatName.getText().toString();
                String txt_ingredients = ingredients.getText().toString();
                String txt_preparation = preparation.getText().toString();

                if (txt_treatName.isEmpty() || txt_ingredients.isEmpty() || txt_preparation.isEmpty()) {
                    Toast.makeText(TreatmentActivity.this, "Please fill all!", Toast.LENGTH_SHORT).show();
                }else {

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

                            Treatment treatment = new Treatment(userID, userPhone[0],nickname,txt_treatName,txt_ingredients,txt_preparation);
                            updateUI(user,treatment);
                            Toast.makeText(TreatmentActivity.this, "Treatment Uploaded! \nConfirmation message will be sent.", Toast.LENGTH_SHORT).show();
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(TreatmentActivity.this,"My Notification");
                            builder.setContentTitle("New treatment uploaded!");
                            builder.setContentText("Hello! \n Just let you know that new treatment was upload:)");
                            builder.setSmallIcon(R.drawable.ic_plus);
                            builder.setAutoCancel(true);

                            NotificationManagerCompat mangerCompat = NotificationManagerCompat.from(TreatmentActivity.this);
                            mangerCompat.notify(1,builder.build());

                            startActivity(new Intent(TreatmentActivity.this, MainActivity.class));

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }}
        });




    }

    void updateUI(FirebaseUser user, Treatment treatment) {
        System.err.println(user.getEmail());
        System.err.println(user.getDisplayName()); // don't display asynchronic maybe
        FirebaseUsers.addTreatmentToDB(treatment);
    }

}



