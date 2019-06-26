package com.tsa.exam.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;

public class NetworkCheck {
	/*
	 * @return boolean return true if the application can access the internet
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean checkInternet(View view, final Context context)
	{
		String msg1="";

		if (NetworkCheck.isNetworkAvailable(context))
			msg1="Connected...";
		else
			msg1="Network Not Available";

		Snackbar snackbar = Snackbar
				.make(view, msg1, Snackbar.LENGTH_LONG)
				.setAction("Retry", new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						String msg="";
						if (NetworkCheck.isNetworkAvailable(context))
							msg="Connected...";
						else
							msg="Network Not Available";

						Snackbar snackbar1 = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
						snackbar1.show();
					}
				});

		snackbar.show();

		return NetworkCheck.isNetworkAvailable(context);
	}
}
