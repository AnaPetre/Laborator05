package ro.pub.cs.systems.eim.lab05.startedservice.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ro.pub.cs.systems.eim.lab05.startedservice.general.Constants;

public class ProcessingThread extends Thread {

    private Context context;

    // constructor al metodei ProcessingThread()
    public ProcessingThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        Log.d(Constants.TAG, "Thread.run() was invoked, PID: " + android.os.Process.myPid() + " TID: " + android.os.Process.myTid());
        while(true) {
            // afisare string "EIM"
            sendMessage(Constants.MESSAGE_STRING);
            //sleep();
            // afisare integer "2017"
            sendMessage(Constants.MESSAGE_INTEGER);
           // sleep();
            // afisare array "[EIM, 2017]"
            sendMessage(Constants.MESSAGE_ARRAY_LIST);
            sleep();
        }
    }

    // metoda pentru afisarea datelor in interfata
    private void sendMessage(int messageType) {
        Intent intent = new Intent();
        switch (messageType) {
            // daca mesajul este de tip string
            // in metoda putExtra folosim 2 argumente: Constants.DATA si Constants.STRING_DATA
            case Constants.MESSAGE_STRING:
                intent.setAction(Constants.ACTION_STRING);
                intent.putExtra(Constants.DATA, Constants.STRING_DATA);
                break;
            // daca mesajul este de tip integer
            // in metoda putExtra folosim 2 argumente: Constants.DATA si Constants.INTEGER_DATA
            case Constants.MESSAGE_INTEGER:
                intent.setAction(Constants.ACTION_INTEGER);
                intent.putExtra(Constants.DATA, Constants.INTEGER_DATA);
                break;
            // daca mesajul este de tip array (ex: "[EIM,2017]")
            // in metoda putExtra folosim 2 argumente: Constants.DATA si Constants.ARRAY_LIST_DATA
            case Constants.MESSAGE_ARRAY_LIST:
                intent.setAction(Constants.ACTION_ARRAY_LIST);
                intent.putExtra(Constants.DATA, Constants.ARRAY_LIST_DATA);
                break;
        }
        context.sendBroadcast(intent);
    }

    // implementarea metodei sleep() pentru a temporiza cele 3 mesaje
    private void sleep() {
        try {
            Thread.sleep(Constants.SLEEP_TIME);
        } catch (InterruptedException interruptedException) {
            Log.e(Constants.TAG, interruptedException.getMessage());
            interruptedException.printStackTrace();
        }
    }

}
