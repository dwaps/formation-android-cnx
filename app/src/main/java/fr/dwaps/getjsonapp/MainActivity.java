package fr.dwaps.getjsonapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private ConnectToData connectToData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectToData = new ConnectToData(
                this,
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));

        tv = (TextView) findViewById(R.id.tv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connectToData = null;
        tv = null;
    }
}
