package reginato.james.listviewexcercise;

import java.util.Random;

/**
 * Created by Reginato James on 11/11/2016.
 */
public class CustomItem {
    public int mID;
    public String mName;
    public int mProgress;
    public int mValutation;

    public CustomItem(int id){
        mID = id;
        mName = "Oggetto " + id;
        Random r = new Random();
        mProgress = r.nextInt(100);
        mValutation = r.nextInt(5);
    }
}
