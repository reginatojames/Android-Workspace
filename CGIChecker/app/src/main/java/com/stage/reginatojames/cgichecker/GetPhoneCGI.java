package com.stage.reginatojames.cgichecker;

import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

public class GetPhoneCGI implements Parcelable{

    public String cgi, mcc, mnc, lac, ci;

    public GetPhoneCGI(){

    }

    public static GetPhoneCGI returnCGIinfos(Activity act){

        GetPhoneCGI item = new GetPhoneCGI();

        TelephonyManager tm = (TelephonyManager) act.getSystemService(Context.TELEPHONY_SERVICE);
        String mccAndMnc = tm.getNetworkOperator(); // MCC & MNC

        if(mccAndMnc.length() > 0) {
            item.mcc = mccAndMnc.substring(0, 3);
            item.mnc = mccAndMnc.substring(3, 5);
        }else{
            item.cgi = "";
            return item;
        }

        GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();
        int cellIdInt = location.getCid();
            if (cellIdInt > 15) {
                cellIdInt = cellIdInt & 0xffff;
            }
        String vcellId = String.valueOf(cellIdInt);
        String vlac = String.valueOf(location.getLac());

        String dataCGI = mccAndMnc + vlac + vcellId;
        item.cgi = dataCGI;
        item.lac = vlac;
        item.ci = vcellId;

        return item;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cgi);
        dest.writeString(mcc);
        dest.writeString(mnc);
        dest.writeString(lac);
        dest.writeString(ci);
    }

    public static Parcelable.Creator<GetPhoneCGI> CREATOR = new ClassLoaderCreator<GetPhoneCGI>() {
        @Override
        public GetPhoneCGI createFromParcel(Parcel source, ClassLoader loader) {
            return new GetPhoneCGI(source);
        }

        @Override
        public GetPhoneCGI createFromParcel(Parcel source) {
            return new GetPhoneCGI(source);
        }

        @Override
        public GetPhoneCGI[] newArray(int size) {
            return new GetPhoneCGI[size];
        }
    };

    private GetPhoneCGI(Parcel in) {
        cgi = in.readString();
        mcc = in.readString();
        mnc = in.readString();
        lac = in.readString();
        ci = in.readString();
    }
}
