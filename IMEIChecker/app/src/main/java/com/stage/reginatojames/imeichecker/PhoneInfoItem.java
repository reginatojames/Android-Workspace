package com.stage.reginatojames.imeichecker;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.TelephonyManager;

public class PhoneInfoItem implements Parcelable {

    public PhoneInfoItem(){

    }

    public String imei, tac, manufacturer, model, product, operatorName, operatorCode;

    public static PhoneInfoItem getPhoneInfos(Activity act)
    {
        PhoneInfoItem item = new PhoneInfoItem();
        TelephonyManager tm = (TelephonyManager)act.getSystemService(Context.TELEPHONY_SERVICE);
        item.imei = tm.getDeviceId();
        item.tac = "";
        if (item.imei != null && item.imei.length()>8)
            item.tac = item.imei.substring(0,8);
        item.manufacturer = Build.MANUFACTURER;
        item.model = Build.MODEL;
        item.product = Build.PRODUCT;
        item.operatorName = tm.getNetworkOperatorName();
        item.operatorCode = tm.getNetworkOperator();
        return item;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imei);
        dest.writeString(tac);
        dest.writeString(manufacturer);
        dest.writeString(model);
        dest.writeString(product);
        dest.writeString(operatorName);
        dest.writeString(operatorCode);
    }

    public static Parcelable.Creator<PhoneInfoItem> CREATOR = new ClassLoaderCreator<PhoneInfoItem>() {
        @Override
        public PhoneInfoItem createFromParcel(Parcel source, ClassLoader loader) {
            return new PhoneInfoItem(source);
        }

        @Override
        public PhoneInfoItem createFromParcel(Parcel source) {
            return new PhoneInfoItem(source);
        }

        @Override
        public PhoneInfoItem[] newArray(int size) {
            return new PhoneInfoItem[size];
        }
    };

    private PhoneInfoItem(Parcel in) {
        imei = in.readString();
        tac = in.readString();
        manufacturer = in.readString();
        model = in.readString();
        product = in.readString();
        operatorName = in.readString();
        operatorCode = in.readString();
    }
}
