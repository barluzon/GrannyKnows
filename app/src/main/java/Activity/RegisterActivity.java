package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Database.FirebaseUsers;
import com.example.utils.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText phone;
    private EditText user_firstName;
    private EditText user_lastName;
    private EditText user_nickname;

    private Button register;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        phone = findViewById(R.id.PhoneNumber);
        user_firstName = findViewById(R.id.user_firstName);
        user_lastName = findViewById(R.id.user_lastName);
        user_nickname = findViewById(R.id.user_nickname);

        auth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_phone = phone.getText().toString();
                String fname = user_firstName.getText().toString();
                String lname = user_lastName.getText().toString();
                String nickname = user_nickname.getText().toString();

                if(ValidateUserInformation(txt_email,txt_password,txt_phone,fname,lname,nickname)) {
                    registerUser(txt_email, txt_password,txt_phone,fname,lname,nickname);
                }
            }
        });
    }

    private void registerUser(String email,String password, String phone, String fname, String lname,String nickname){

        this.auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    //update display name
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(nickname).build();
                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                private static final String TAG ="";

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User profile updated.");
                                    }
                                }
                            });


                    updateUI(user,phone,fname,lname,nickname);
                    Toast.makeText(RegisterActivity.this,"Registering user successful!",Toast.LENGTH_SHORT).show();
                    premiumDialog(nickname);
                    //startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    //finish();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Registration failed!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    boolean ValidateUserInformation(String email, String password, String phone, String fname, String lname,String nickname)
    {
        // if there is an empty field
        if (email.isEmpty() || password.isEmpty() || phone.isEmpty() || fname.isEmpty() || lname.isEmpty())
        {
            Toast.makeText(RegisterActivity.this, "Please fill all!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // email validation
        if (!ValidateUserEmail(email))
        {
            Toast.makeText(RegisterActivity.this, "Invalid email!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // check if password is at least 6
        if (password.length() < 6)
        {
            Toast.makeText(RegisterActivity.this, "Password have to be at least 6 chars!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // check if first name contains only letters
        if (!fname.matches("[a-zA-Z]+"))
        {
            user_firstName.setError("First name should have only letters!");
            return false;
        }
        // check if last name contains only letters
        if (!lname.matches("[a-zA-Z]+"))
        {
            user_lastName.setError("Last name should have only letters!");
            return false;
        }
        // check if phone number contains only numbers
        if (!phone.matches("[0-9]+") || phone.length() != 10)
        {
            this.phone.setError("Phone Number can contain only numbers and must be 10 digits!");
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
    void updateUI(FirebaseUser user, String phoneNumber, String fname, String lname,String nickname)
    {

        String User_ID = user.getUid(); // the unique userID Token (string)
        System.err.println(user.getEmail());
        System.err.println(user.getDisplayName()); // don't display asynchronic maybe


        FirebaseUsers.addUserToDB(User_ID, fname, lname,nickname, phoneNumber, false, user.getEmail());
    }
    private void premiumDialog(String nickname)
    {
        Dialog d = new Dialog(RegisterActivity.this);
        d.setContentView(R.layout.activity_dialog_premium);
        d.setTitle("premium");

        Button yesButton = d.findViewById(R.id.activity_dialog_yesButton);
        Button noButton = d.findViewById(R.id.activity_dialog_noButton);
        TextView nameTextView = d.findViewById(R.id.activity_dialog_NameTextView);
        nameTextView.setText(nameTextView.getText() + " " + nickname);

        noButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                d.dismiss();
                //RegisterActivity.super.onBackPressed(); // Go previous page
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                d.dismiss();
                startActivity(new Intent(RegisterActivity.this, PaymentActivity.class));
            }
        });
        d.show();
    }
}