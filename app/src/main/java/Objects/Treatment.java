package Objects;
import java.io.Serializable;
import java.util.Vector;


public class Treatment implements Serializable {
    private String User_ID;
    private String nickname;

    private String treatName;
    private String ingredients;
    private String preparation;
    private String phone;
    protected boolean isConfirmed;
    static protected int numOfRates = 0;
    protected static Vector<Integer> treatRate= new Vector<>();

    public Treatment(String userID,String phoneN,String nickname, String name, String ingredients, String preparation){
        this.User_ID = userID;
        this.nickname = nickname;
        this.treatName = name;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.isConfirmed = false;
        this.phone = phoneN;
    }

    public String getPhoneNumber(){return phone;}

    public void SetPhoneNumber(String phoneNumber){phone = phoneNumber;}

    public String getTreatUser_ID()
    {
        return User_ID;
    }

    public void setTreatUser_ID(String user_ID)
    {
        User_ID = user_ID;
    }

    public String getNickname() {return nickname;}

    public void setNickname(String nick) { nickname = nick; }

    public String getTreatName()
    {
        return treatName;
    }

    public void setTreatName(String treatName)
    {
        this.treatName = treatName;
    }

    public String getIngredients()
    {
        return ingredients;
    }

    public void setIngredients(String ingredients)
    {
        this.ingredients = ingredients;
    }

    public void setPreparation(String preparation) { this.preparation = preparation; }

    public String getPreparation() { return preparation; }

    public boolean getIsConfirmed()
    {
        return isConfirmed;
    }

    public void setIsConfirmed(boolean isConfirmed) { this.isConfirmed = isConfirmed; }

    public double getRate() {
        int sumRate = 0;
        int counter = 0;
        for (int i : treatRate) {
            counter++;
            sumRate += i;
        }
        numOfRates = counter;
        return sumRate / treatRate.capacity();
    }

    public void addRate(int rate) { treatRate.add(rate); }

    public int getNumOfRates() { return numOfRates; }


    @Override
    public String toString() {
        return "Treatment{" + "User_ID ='" + User_ID + '\'' + ", TreatName ='" + treatName + '\'' + ", ingredients ='" + ingredients + '\'' + ", preparation ='" + preparation + '\'' + ", isConfirmed =" + isConfirmed + ", numOfRanks =" + treatRate.capacity() + '}';

    }
}


