package Database;


import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import Objects.PremiumTreatment;
import Objects.Treatment;
import Objects.User;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.firestore.auth.User;


public class FirebaseUsers {

    public static void addUserToDB(String User_ID, String firstName, String lastName,String nickname, String phoneNumber, boolean isPremium, String email) {

        User user = new User(User_ID, firstName, lastName,nickname, phoneNumber, isPremium, 0, 0, email);
        FirebaseBaseModel.getRef().child("Users").child(User_ID).setValue(user);

    }

    public static void changeUserByID(User user) {

        FirebaseBaseModel.getRef().child("Users").child(user.getUser_ID()).setValue(user);
    }

    public static DatabaseReference getAllusers() {

        return FirebaseBaseModel.getRef().child("Users");
    }

    public static DatabaseReference getUserByID(String id) {

        return FirebaseBaseModel.getRef().child("Users").child(id);
    }
    public static DatabaseReference getUserByNickname(String nickname) {

        return FirebaseBaseModel.getRef().child("Users").child(nickname);
    }

    public static DatabaseReference getTreatments() {

        return FirebaseBaseModel.getRef().child("Treatments");
    }

    public static DatabaseReference getPremiumTreatments() {

        return FirebaseBaseModel.getRef().child("Premium Treatments");
    }


    public static void addTreatmentToDB(Treatment treatment) {
        FirebaseBaseModel.getRef().child("Users").child(treatment.getTreatUser_ID()).child("Treatments").child(treatment.getTreatName()).setValue(treatment);
        FirebaseBaseModel.getRef().child("Treatments").child(treatment.getTreatName()).setValue(treatment);

    }

    public static void addPremiumTreatmentToDB(PremiumTreatment pTreatment) {
        FirebaseBaseModel.getRef().child("Users").child(pTreatment.getTreatUser_ID()).child("Premium Treatments").child(pTreatment.getTreatName()).setValue(pTreatment);
        FirebaseBaseModel.getRef().child("Premium Treatments").child(pTreatment.getTreatName()).setValue(pTreatment);

    }

    public static void addTipToDB(String name[], String description[]) {
        for (int i = 0; i < name.length; i++) {
            FirebaseBaseModel.getRef().child("Tips").child(name[i]).setValue(description[i]);
        }
    }

    public static void setPremiumToUser(String id, Context context){
        FirebaseBaseModel.getRef().child("Users").child(id).child("premium").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Thank you! \n Payment successful!", Toast.LENGTH_SHORT).show();

            }
        });
    }

}