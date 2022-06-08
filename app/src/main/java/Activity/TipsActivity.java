package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import Database.FirebaseUsers;
import Adapters.MyTipsAdapter;
import com.example.utils.R;
import com.google.firebase.auth.FirebaseAuth;

public class TipsActivity extends AppCompatActivity {
    String s1[], s2[];
    int images[] = {R.drawable.avocado,R.drawable.pineapple,R.drawable.lemon, R.drawable.ginger};
    RecyclerView recylerView;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        getSupportActionBar().hide();
        s1 = getResources().getStringArray(R.array.HealthyInfo);
        s2 = getResources().getStringArray(R.array.descriptions);
        recylerView = findViewById(R.id.recyclerView);
        auth = FirebaseAuth.getInstance();

        MyTipsAdapter myTipsAdapter = new MyTipsAdapter(this,s1,s2,images);
        recylerView.setAdapter(myTipsAdapter);
        recylerView.setLayoutManager(new LinearLayoutManager(this));
        updateDB(s1,s2);

    }
    void updateDB ( String name[],String description[]){
        FirebaseUsers.addTipToDB(name,description);

    }
}