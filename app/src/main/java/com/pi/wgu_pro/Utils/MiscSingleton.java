package com.pi.wgu_pro.Utils;

import android.text.format.DateFormat;

import java.util.Date;

public class MiscSingleton {

    public static String formatDateStr(Date date){
        String res = DateFormat.format("MM/dd/yyyy", date).toString();
        return res;
    }

}
