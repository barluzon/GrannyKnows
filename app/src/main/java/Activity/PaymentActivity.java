package Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import Database.FirebaseUsers;
import com.example.utils.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText cardNumber;
    private EditText cardCVC;
    private Button payment;
    private ImageView backB;
    private FirebaseAuth auth;
    private Spinner monthsSpin;
    private Spinner yearsSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        backB = findViewById(R.id.back_premium_payment);
        cardNumber = findViewById(R.id.avtivity_premium_payment_cardNumber_PlainText);
        cardCVC = findViewById(R.id.avtivity_premium_payment_cardNumberCVC);
        payment = findViewById(R.id.activity_premium_payment_acceptButton);
        monthsSpin = findViewById(R.id.monthSpinner);
        yearsSpin = findViewById(R.id.yearSpinner);
        activeButtons();
    }

    private void activeButtons(){
        setUpMonthsSpinner();
        setUpYearsSpinner();
        backB.setOnClickListener(this);
        payment.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(backB.equals(v)){
            PaymentActivity.super.onBackPressed();
        }
        else if(payment.equals(v)){
            if(validateFields()){
                auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                FirebaseUsers.setPremiumToUser(user.getUid(),PaymentActivity.this);
                startActivity(new Intent(PaymentActivity.this, MainActivity.class));

            }
            else{
                Toast.makeText(PaymentActivity.this,"Payment failed!", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void setUpMonthsSpinner()
    {
        Resources res = getResources();
        String[] MonthsValues = res.getStringArray(R.array.months_array);
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MonthsValues);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthsSpin.setAdapter(adp1);
    }

    private void setUpYearsSpinner()
    {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        List<Integer> yearsValues = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            yearsValues.add(year + i);
        }
        ArrayAdapter<Integer> adp1 = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, yearsValues);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearsSpin.setAdapter(adp1);
    }

    private boolean validateFields()
    {
        // Validate card number
        if (cardNumber.getText().toString().length() != 16)
        {
            cardNumber.setError("Need to put 16 characters.");
            return false;
        }
        else if (!cardNumber.getText().toString().matches("[0-9]+"))
        {
            cardCVC.setError("Please put only numbers!");
            return false;
        }
        // Validate CVC
        if (cardCVC.getText().toString().length() != 3)
        {
            cardCVC.setError("Please enter 3 numbers.");
            return false;
        }
        else if (!cardCVC.getText().toString().matches("[0-9]+"))
        {
            cardCVC.setError("Please enter only numbers!");
            return false;
        }
        return true;
    }

}