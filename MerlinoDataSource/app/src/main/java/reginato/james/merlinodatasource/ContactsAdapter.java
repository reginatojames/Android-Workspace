package reginato.james.merlinodatasource;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import reginato.james.merlinodatasource.data.Contact;

/**
 * Created by Reginato James on 02/12/2016.
 */
public class ContactsAdapter extends BaseAdapter {

    ArrayList<Contact> mData;
    Context mContext;

    public ContactsAdapter(Context aContext,ArrayList<Contact> aData){
        mContext = aContext;
        mData = aData;
    }

    class ViewHolder{
        TextView mName;
        TextView mSurname;
        TextView mId;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Contact getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).mId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vCell;

        if (convertView == null) {      //onde evitare la creazione della view ogni volta allo scroll
            LayoutInflater vInflater = LayoutInflater.from(mContext);
            vCell = vInflater.inflate(R.layout.layout_cella, null);        //step 1 = creazione view

            TextView vName = (TextView) vCell.findViewById(R.id.txtNome);     //step 2 = tiro fuori riferimenti alla view
            TextView vSurname = (TextView) vCell.findViewById(R.id.txtSurname);
            TextView vId = (TextView) vCell.findViewById(R.id.txtId);

            ViewHolder vHolder = new ViewHolder();
            vHolder.mName = vName;
            vHolder.mSurname = vSurname;
            vHolder.mId = vId;

            vCell.setTag(vHolder);
        }else{
            vCell = convertView;
        }

        Contact vContact = getItem(position);         //step 3 = recupero elemento della lista

        ViewHolder viewHolder = (ViewHolder) vCell.getTag();
        viewHolder.mName.setText(vContact.mName);
        viewHolder.mSurname.setText(vContact.mSurname);      //step 4 = personalizzazione
        viewHolder.mId.setText(vContact.mId + "");

        return vCell;
    }
}
