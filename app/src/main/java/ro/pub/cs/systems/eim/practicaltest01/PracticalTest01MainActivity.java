package ro.pub.cs.systems.eim.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class PracticalTest01MainActivity extends AppCompatActivity {
    public static final AtomicInteger b1Clicks = new AtomicInteger(0), b2Clicks = new AtomicInteger(0);
    public static Boolean serviceStarted = false;
    private StartedServiceBroadcastReceiver startedServiceBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        startedServiceBroadcastReceiver = new StartedServiceBroadcastReceiver((TextView) findViewById(R.id.textView5));
        Button b1 = findViewById(R.id.button);
        Button b2 = findViewById(R.id.button2);
        Button b3 = findViewById(R.id.button3);
        final Intent i = new Intent(this, PracticalTest01SecondaryActivity.class);

        final TextView t1 = findViewById(R.id.textView);
        final TextView t2 = findViewById(R.id.textView2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1Clicks.incrementAndGet();
                t1.setText(b1Clicks.get() + "");
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b2Clicks.incrementAndGet();
                t2.setText(b2Clicks.get()  + "");
                if (!serviceStarted && ((PracticalTest01MainActivity.b1Clicks.get() + PracticalTest01MainActivity.b2Clicks.get()) > 15)) {
                    serviceStarted = true;
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("ro.pub.cs.systems.eim.practicaltest01", "ro.pub.cs.systems.eim.practicaltest01.PracticalTest01Service"));
                    startService(intent);
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("open", Integer.parseInt(t1.getText().toString()) + Integer.parseInt(t2.getText().toString()));
                startActivity(i);
                if (!serviceStarted && ((PracticalTest01MainActivity.b1Clicks.get() + PracticalTest01MainActivity.b2Clicks.get()) > 15)) {
                    serviceStarted = true;
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("ro.pub.cs.systems.eim.practicaltest01", "ro.pub.cs.systems.eim.practicaltest01.PracticalTest01Service"));
                    startService(intent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("ro.pub.cs.systems.eim.practicaltest01", "ro.pub.cs.systems.eim.practicaltest01.PracticalTest01Service"));
        stopService(intent);

        super.onDestroy();
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String message = intent.getStringExtra("data");
        if (message != null) {
            ((TextView) findViewById(R.id.textView5)).setText(((TextView) findViewById(R.id.textView5)).getText().toString() + "\n" + message);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        TextView t1 = findViewById(R.id.textView);
        TextView t2 = findViewById(R.id.textView2);
        savedInstanceState.putString("t1", t1.getText().toString());
        savedInstanceState.putString("t2", t2.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TextView t1 = findViewById(R.id.textView);
        TextView t2 = findViewById(R.id.textView2);
        t1.setText(savedInstanceState.getString("t1"));
        t2.setText(savedInstanceState.getString("t2"));
    }

}

class StartedServiceBroadcastReceiver extends BroadcastReceiver {

    private TextView messageTextView;

    public StartedServiceBroadcastReceiver() {
        this.messageTextView = null;
    }

    public StartedServiceBroadcastReceiver(TextView messageTextView) {
        this.messageTextView = messageTextView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        String data = null;
        data = intent.getStringExtra("data");
        if (messageTextView != null) {
            messageTextView.setText(messageTextView.getText().toString() + "\n" + data);
        }
    }
}
