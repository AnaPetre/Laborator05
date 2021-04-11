package ro.pub.cs.systems.eim.lab05.startedserviceactivity.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ro.pub.cs.systems.eim.lab05.startedserviceactivity.R;
import ro.pub.cs.systems.eim.lab05.startedserviceactivity.general.Constants;

public class StartedServiceActivity extends AppCompatActivity {

    private TextView messageTextView;
    private StartedServiceBroadcastReceiver startedServiceBroadcastReceiver;
    private IntentFilter startedServiceIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started_service);

        messageTextView = (TextView)findViewById(R.id.message_text_view);

        // TODO: exercise 6 - start the service
        // pornim serviciul din StartedService.java printr-un apel al metodei startService()

        /* folosim o intentie care refera explicit serviciul care urmeaza a fi pornit
        si metoda setComponent care indica atat pachetul corespunzator aplicatiei Android(care contine serviciul)
        cat si clasa corespunzatoare acestuia*/
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("ro.pub.cs.systems.eim.lab05.startedservice", "ro.pub.cs.systems.eim.lab05.startedservice.service.StartedService"));

        // si aici pornim serviciul
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        }
        else {
            startService(intent);
        }

        // TODO: exercise 8a - create an instance of the StartedServiceBroadcastReceiver broadcast receiver
        // creare a instantei de ascultator implementata in StartedServiceBroadcastReceiver
        startedServiceBroadcastReceiver = new StartedServiceBroadcastReceiver(messageTextView);

        // TODO: exercise 8b - create an instance of an IntentFilter
        // with all available actions contained within the broadcast intents sent by the service

        /* cream o instanta a unui obiect de tipul IntentFilter, la care adaugam toate actiunile
        corespunzatoare intentiilor cu difuzare propagate de serviciu*/
        startedServiceIntentFilter = new IntentFilter();
        // intentie cu difuzare pentru string-ul "EIM"
        startedServiceIntentFilter.addAction(Constants.ACTION_STRING);
        // intentie cu difuzare pentru integer-ul "2017"
        startedServiceIntentFilter.addAction(Constants.ACTION_INTEGER);
        // intentie cu difuzare pentru array-ul "[EIM,2017]"
        startedServiceIntentFilter.addAction(Constants.ACTION_ARRAY_LIST);
    }


    /*mesajele vor fi difuzate doar daca activitatea este vizibila pe suprafata de afisare*/
    /* metodele registerReceiver si unregister Receiver sunt apelate pe metodele de callback ale
      activitatii corespunzatoare starii in care aceasta este vizibila pe suprafata de afisare:
      adica in onResume() si in onPause()*/

    @Override
    protected void onResume() {
        super.onResume();

        // TODO: exercise 8c - register the broadcast receiver with the corresponding intent filter
        // activarea ascultatorului se face in metoda onResume()
        registerReceiver(startedServiceBroadcastReceiver, startedServiceIntentFilter);
    }

    @Override
    protected void onPause() {
        // TODO: exercise 8c - unregister the broadcast receiver
        // dezactivarea ascultatorului se face in metoda onPause()
        unregisterReceiver(startedServiceBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // TODO: exercise 8d - stop the service
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("ro.pub.cs.systems.eim.lab05.startedservice", "ro.pub.cs.systems.eim.lab05.startedservice.service.StartedService"));
        stopService(intent);

        super.onDestroy();
    }

    // TODO: exercise 10 - implement the onNewIntent callback method
    // get the message from the extra field of the intent
    // and display it in the messageTextView
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String message = intent.getStringExtra(Constants.MESSAGE);
        if (message != null) {
            messageTextView.setText(messageTextView.getText().toString() + "\n" + message);
        }
    }

}
