package Objects;


import java.io.Serializable;
import java.util.Vector;

public class User implements Serializable
{
    private String User_ID;
    private String firstName;
    private String lastName;
    private String nickname;
    private String phoneNumber;
    private boolean isPremium;
    private String email;
    protected Vector<Treatment> treatments;
    private float rate;
    public User() {}

    public User(String User_ID, String firstName, String lastName,String nickname, String phoneNumber, boolean isPremium, float rating, int ratingsAmount, String email)
    {
        this.User_ID = User_ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.isPremium = isPremium;
        this.email = email;
        this.rate = rating;
    }

    public String getUser_ID()
    {
        return User_ID;
    }

    public void setUser_ID(String user_ID)
    {
        User_ID = user_ID;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nick)
    {
        nickname = nick;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public boolean getIsPremium()
    {
        return isPremium;
    }

    public void setPremium(boolean premium)
    {
        isPremium = premium;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void addTreatment(Treatment treatment){
        treatments.add(treatment);
    }

    public Vector<Treatment> getTreatments(){ return treatments;}

    public float getRate(){return rate;}
    @Override
    public String toString()
    {
        return "User{" + "User_ID='" + User_ID + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", phoneNumber='" + phoneNumber + '\'' + ", isPremium=" + isPremium + ", email='" + email + '\'' + '}';
    }
}