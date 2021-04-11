package ro.pub.cs.systems.eim.lab05.startedservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import ro.pub.cs.systems.eim.lab05.startedservice.general.Constants;

public class StartedService extends Service {

    // fiecare serviciu trebuie sa aiba functiile de onCreate(), onBind() si onUnbind()
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Constants.TAG, "onCreate() method was invoked");
    }

    // metodele onBind() si onUnbind() vor primi ca parametrii o intentie
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(Constants.TAG, "onBind() method was invoked");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(Constants.TAG, "onUnbind() method was invoked");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(Constants.TAG, "onRebind() method was invoked");
    }

    @Override
    public void onDestroy() {
        Log.d(Constants.TAG, "onDestroy() method was invoked");
        super.onDestroy();
    }

    // porneste serviciul de tip started
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Constants.TAG, "onStartCommand() method was invoked");
        // TODO: exercise 5 - implement and start the ProcessingThread

        // pornim un thread care contine 3 intentii cu difuzare la nivelul sistemului Android
        // unde sunt cele 3 intentii: in metoda ProcessingThread
        ProcessingThread thread1 = new ProcessingThread(this);
        thread1.start();

        Log.d(Constants.TAG, "Thread.run() was invoked, PID: " + android.os.Process.myPid() + " TID: " + android.os.Process.myTid());
        return START_REDELIVER_INTENT;

        /* START_REDELIVER_INTENT = mecanism utilizat cand vrem sa ne asigurem ca procesarile asociate serviciului
        sunt terminate; Daca serviciul a fost distrus de sistemul de operare Android, este (re)pornit apeland metoda
        onStartCommand(), folosind ca parametru intentia originala, a carei procesare nu a fost terminata corespunzator.
         */

    }

}
