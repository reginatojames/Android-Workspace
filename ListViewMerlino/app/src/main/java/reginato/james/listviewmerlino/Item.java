package reginato.james.listviewmerlino;


import java.util.Random;

public class Item {
    public int mID;
    public String mName;
    public int mLiter;

    public Item(int id){
        mID = id;
        mName = "Birra "+ id;
        Random r = new Random();
        mLiter = r.nextInt(10);
    }
}
