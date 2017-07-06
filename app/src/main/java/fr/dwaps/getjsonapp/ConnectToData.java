package fr.dwaps.getjsonapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Michael on 06/07/2017.
 */

public class ConnectToData {
    private static final String TAG = "DWAPS.ConnectToData";

    private ConnectivityManager cm = null;
    private NetworkInfo ni = null;
    private URL url = null;

    private boolean cnxEstablished = false, wifi = false;
    private String stream = "";
    private TextView tv = null;

    private Context ctx = null;

    ConnectToData(Context ctx, TextView tv, String url, ConnectivityManager cm)
    {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.ctx = ctx;
        this.cm = cm;
        this.tv = tv;

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
            final URL url = this.url;
            final Handler h = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    tv.setText((String) msg.obj);
                }
            };

            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            int codeChar;
                            StringBuilder sb = new StringBuilder();

                            HttpURLConnection huc;
                            InputStream is = null;

                            try
                            {
                                huc = (HttpURLConnection) url.openConnection();
                                is = new BufferedInputStream(huc.getInputStream());

                                while((codeChar=is.read()) != -1)
                                    sb.append((char) codeChar);

                                Message m = new Message();
                                m.obj = sb.toString();
                                h.sendMessage(m);
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                                e.getMessage();
                                Log.i(TAG, "run: Les données n'ont pas pu être lues...");
                            }
                            finally
                            {
                                if(is != null)
                                    try {
                                        is.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        e.getMessage();
                                    }
                            }
                        }
                    }
            ).start();
        }
        else Toast.makeText(this.ctx, "Problème de connexion...", Toast.LENGTH_SHORT).show();
    }
}
