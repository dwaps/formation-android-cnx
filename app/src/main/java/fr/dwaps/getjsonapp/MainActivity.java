package fr.dwaps.getjsonapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private ConnectToData connectToData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);

        connectToData = new ConnectToData(
                this,
                tv,
                "https://dwaps.fr/tests/bdd-to-json",
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(
                this,
                connectToData.wifiIsEnabled() ?
                    "L'appareil est connecté en wifi"
                    :
                    "La connexion wifi est désactivée !",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connectToData = null;
        tv = null;
    }
}
