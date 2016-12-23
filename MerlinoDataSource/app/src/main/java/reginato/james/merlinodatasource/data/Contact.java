package reginato.james.merlinodatasource.data;

import java.util.Random;

/**
 * Created by Reginato James on 02/12/2016.
 */
public class Contact {
    public long mId;
    public String mName;
    public String mSurname;
    public Contact(String aName, String aSurname){
        mName = aName;
        mSurname = aSurname;
    }

    public Contact(){

    }

    public Contact(long aId, String aName, String aSurname){
        mId = aId;
        mName = aName;
        mSurname = aSurname;
    }

    public static Contact createContacts(){
        Random vRand = new Random();
        String name = "nome " + vRand.nextInt();
        String surname = "surname " + vRand.nextInt();
        Contact c = new Contact(name, surname);
        return c;
    }
}
