package Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseBaseModel {
    protected static DatabaseReference ref;

    private FirebaseBaseModel(){}

    public static synchronized DatabaseReference getRef()
    {
        if (ref == null)
        {
            ref = FirebaseDatabase.getInstance("https://grannyknow-2e77b-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        }
        return ref;
    }
}
