package ro.pub.cs.systems.eim.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class PracticalTest01MainActivity extends AppCompatActivity {
    public static final AtomicInteger i1 = new AtomicInteger(0), i2 = new AtomicInteger(0);
    final Context c = this;
    public static boolean service = true;

    private TextView messageTextView;
    private StartedServiceBroadcastReceiver startedServiceBroadcastReceiver;
    private IntentFilter startedServiceIntentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        final TextView t1 = findViewById(R.id.textView2);
        final TextView t2 = findViewById(R.id.textView3);
        Button b1 = findViewById(R.id.button);
        Button b2 = findViewById(R.id.button2);
        Button b3 = findViewById(R.id.button5);
        final Intent intent = new Intent(this, PracticalTest01SecondaryActivity.class);
        messageTextView = findViewById(R.id.textView);
        // TODO: exercise 8a - create an instance of the StartedServiceBroadcastReceiver broadcast receiver
        startedServiceBroadcastReceiver = new StartedServiceBroadcastReceiver(messageTextView);

        // TODO: exercise 8b - create an instance of an IntentFilter
        // with all available actions contained within the broadcast intents sent by the service
        startedServiceIntentFilter = new IntentFilter();
        startedServiceIntentFilter.addAction("1");
        startedServiceIntentFilter.addAction("2");
        startedServiceIntentFilter.addAction("3");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.setText(i1.incrementAndGet() + "");
                if (service && i1.get() + i2.get() > 15) {
                    service = false;
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("ro.pub.cs.systems.eim.practicaltest01", "ro.pub.cs.systems.eim.practicaltest01.PracticalTest01Service"));
                    startService(intent);
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t2.setText(i2.incrementAndGet() + "");
                if (service && i1.get() + i2.get() > 15) {
                    service = false;
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("ro.pub.cs.systems.eim.practicaltest01", "ro.pub.cs.systems.eim.practicaltest01.PracticalTest01Service"));
                    startService(intent);
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("sum", (i1.get() + i2.get()));
                startActivity(intent);
                //finish();
            }
        });

    }

    protected void onDestroy() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("ro.pub.cs.systems.eim.practicaltest01", "ro.pub.cs.systems.eim.practicaltest01.PracticalTest01Service"));
        stopService(intent);

        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // apelarea metodei din activitatea parinte este recomandata, dar nu obligatorie
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("t1", i1.get());
        savedInstanceState.putInt("t2", i2.get());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // apelarea metodei din activitatea parinte este recomandata, dar nu obligatorie
        super.onRestoreInstanceState(savedInstanceState);
        TextView t1 = findViewById(R.id.textView2);
        TextView t2 = findViewById(R.id.textView3);
        t1.setText(savedInstanceState.getInt("t1") + "");
        t2.setText(savedInstanceState.getInt("t2") + "");
    }


    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(startedServiceBroadcastReceiver, startedServiceIntentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(startedServiceBroadcastReceiver);

        super.onPause();
    }
}

class StartedServiceBroadcastReceiver extends BroadcastReceiver {

    private TextView messageTextView;

    // TODO: exercise 10 - default constructor2
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

