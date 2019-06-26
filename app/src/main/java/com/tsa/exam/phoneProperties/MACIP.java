package com.tsa.exam.phoneProperties;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

/**
 * Created by Akhil Tripathi on 9/30/2016.
 */
public class MACIP {

    public String macGetter(Context activityContext)
    {
        WifiManager wifiManager = (WifiManager) activityContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();
        return macAddress;
    }

    public String ipGetter(Context activityContext)
    {
        WifiManager wifiManager = (WifiManager) activityContext.getSystemService(Context.WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        return ipAddress;
    }
}
