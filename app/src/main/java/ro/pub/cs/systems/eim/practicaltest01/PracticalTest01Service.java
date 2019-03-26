package ro.pub.cs.systems.eim.practicaltest01;

import android.app.IntentService;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.os.IBinder;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PracticalTest01Service extends Service {

    private int startMode;
    public static int t1, t2;
    @Override
    public void onCreate() {
        super.onCreate();

        // ...
    }

    @Override
    public int onStartCommand(Intent intent,
                              int flags,
                              int startId) {
        // ...
        t1 = intent.getIntExtra("t1", 0);
        t2 = intent.getIntExtra("t2", 0);
        ProcessingThread processingThread = new ProcessingThread(this);
        processingThread.start();
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // ...
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // ...
    }
}


class ProcessingThread extends Thread {

    private Context context;

    public ProcessingThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        while(true) {
            sendMessage(1);
            sleep();
            sendMessage(2);
            sleep();
            sendMessage(3);
            sleep();
        }
    }

    private void sendMessage(int messageType) {
        Intent intent = new Intent();
        switch (messageType) {
            case 1:
                intent.setAction("1");
                intent.putExtra("data", ((Integer)(PracticalTest01MainActivity.b1Clicks.get() + PracticalTest01MainActivity.b2Clicks.get())).toString());
                Log.e("servicePractical", ((Integer)(PracticalTest01MainActivity.b1Clicks.get() + PracticalTest01MainActivity.b2Clicks.get())).toString());
                break;
            case 2:
                intent.setAction("2");
                intent.putExtra("data", ((Integer)((PracticalTest01MainActivity.b1Clicks.get() + PracticalTest01MainActivity.b2Clicks.get()) / 2)).toString());
                Log.e("servicePractical", ((Integer)((PracticalTest01MainActivity.b1Clicks.get() + PracticalTest01MainActivity.b2Clicks.get()) / 2)).toString());

                break;
            case 3:
                intent.setAction("3");
                intent.putExtra("data", ((Double)(Math.sqrt(PracticalTest01MainActivity.b1Clicks.get() * PracticalTest01MainActivity.b2Clicks.get()))).toString());
                Log.e("servicePractical", ((Double)(Math.sqrt(PracticalTest01MainActivity.b1Clicks.get() * PracticalTest01MainActivity.b2Clicks.get()))).toString());

                break;
        }
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException interruptedException) {
//            Log.e(Constants.TAG, interruptedException.getMessage());
            interruptedException.printStackTrace();
        }
    }

}