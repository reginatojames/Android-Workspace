package reginato.james.listviewexcercise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Reginato James on 11/11/2016.
 */
public class CustomAdapter extends BaseAdapter{

    private ArrayList<CustomItem> mData;
    private Context mContext;

    public CustomAdapter(Context context, ArrayList<CustomItem> aData){
        mData = aData;
        mContext = context;
    }

    class ViewHolder{
        TextView mName;
        ProgressBar mProgress;
        RatingBar mValutation;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CustomItem getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).mID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vCell;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            vCell = inflater.inflate(R.layout.item_layout, null);

            TextView vName = (TextView) vCell.findViewById(R.id.nameTxt);
            ProgressBar vProgress = (ProgressBar) vCell.findViewById(R.id.progressBar);
            RatingBar vValutation = (RatingBar) vCell.findViewById(R.id.ratingBar);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.mName = vName;
            viewHolder.mProgress = vProgress;
            viewHolder.mValutation = vValutation;

            vCell.setTag(viewHolder);
        }else{
            vCell = convertView;
        }

        CustomItem vItem = getItem(position);

        ViewHolder viewHolder = (ViewHolder) vCell.getTag();
        viewHolder.mName.setText(vItem.mName);
        viewHolder.mProgress.setProgress(vItem.mProgress);
        viewHolder.mValutation.setProgress(vItem.mValutation);

        return vCell;
    }
}
