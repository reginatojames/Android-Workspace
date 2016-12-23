package reginato.james.listviewmerlino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private ArrayList<Item> mData;
    private Context mContext;

    public CustomAdapter(Context context, ArrayList<Item> aData){
        mData = aData;
        mContext = context;
    }

    class ViewHolder{
        TextView mName;
        TextView mLiter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vCell;

        if (convertView == null) {      //onde evitare la creazione della view ogni volta allo scroll
            LayoutInflater vInflater = LayoutInflater.from(mContext);
            vCell = vInflater.inflate(R.layout.item_lay, null);        //step 1 = creazione view

            TextView vName = (TextView) vCell.findViewById(R.id.textView1);     //step 2 = tiro fuori riferimenti alla view
            TextView vLiter = (TextView) vCell.findViewById(R.id.textView2);

            ViewHolder vHolder = new ViewHolder();
            vHolder.mName = vName;
            vHolder.mLiter = vLiter;

            vCell.setTag(vHolder);
        }else{
            vCell = convertView;
        }

        Item vItem = getItem(position);         //step 3 = recupero elemento della lista

        ViewHolder viewHolder = (ViewHolder) vCell.getTag();
        viewHolder.mName.setText(vItem.mName);
        viewHolder.mLiter.setText("" + vItem.mLiter);      //step 4 = personalizzazione

        return vCell;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Item getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).mID;
    }
}
