package fr.dwaps.getjsonapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Michael on 06/07/2017.
 */

public class ConnectToData {
    private static final String TAG = "DWAPS.ConnectToData";

    private ConnectivityManager cm = null;
    private NetworkInfo ni = null;

    private boolean cnxEstablished = false, wifi = false;

    private Context ctx = null;

    ConnectToData(Context ctx, ConnectivityManager cm)
    {
        this.ctx = ctx;
        this.cm = cm;

        this.checkForNetworkState();

        this.requestForStream();
    }

    private boolean checkForNetworkState()
    {
        if(this.cm != null)
        {
            ni = this.cm.getActiveNetworkInfo();
            cnxEstablished = ni.isConnected();

            if(ni != null && cnxEstablished)
                this.wifi = (ni.getType() == ConnectivityManager.TYPE_WIFI)? true : false;
        }

        Log.i(TAG, "checkForNetworkState: cnxEstablished => " + cnxEstablished);

        Toast.makeText(this.ctx, "Connexion nickel !", Toast.LENGTH_SHORT).show();

        return cnxEstablished;
    }

    private void requestForStream()
    {
        if(this.cnxEstablished)
        {
        }
        else Toast.makeText(this.ctx, "Problème de connexion...", Toast.LENGTH_SHORT).show();
    }
}
