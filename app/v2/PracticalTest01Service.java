package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PracticalTest01Service extends Service {
    public PracticalTest01Service() {
    }

    private int startMode;

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
        Log.d("myService", "onStartCommand() method was invoked");
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
        Log.d("myService", "Thread.run() was invoked, PID: " + android.os.Process.myPid() + " TID: " + android.os.Process.myTid());
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
                intent.putExtra("data", (PracticalTest01MainActivity.i1.get() + PracticalTest01MainActivity.i2.get()) + "");
                Log.d("myService", (PracticalTest01MainActivity.i1.get() + PracticalTest01MainActivity.i2.get()) + "");

                break;
            case 2:
                intent.setAction("2");
                intent.putExtra("data", ((PracticalTest01MainActivity.i1.get() + PracticalTest01MainActivity.i2.get()) / 2) + "");
                Log.d("myService", ((PracticalTest01MainActivity.i1.get() + PracticalTest01MainActivity.i2.get()) / 2) + "");

                break;
            case 3:
                intent.setAction("3");
                intent.putExtra("data", Math.sqrt(PracticalTest01MainActivity.i1.get() * PracticalTest01MainActivity.i2.get()) + "");
                Log.d("myService", Math.sqrt(PracticalTest01MainActivity.i1.get() * PracticalTest01MainActivity.i2.get()) + "");

                break;
        }
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException interruptedException) {
            Log.e("myService", interruptedException.getMessage());
            interruptedException.printStackTrace();
        }
    }

}
