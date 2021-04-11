package ro.pub.cs.systems.eim.lab05.startedserviceactivity.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import ro.pub.cs.systems.eim.lab05.startedserviceactivity.general.Constants;

// ascultator pentru intentii cu difuzare in clasa StartedServiceBroadcastReceiver
// ascultatorul extinde clasa BroadcastReceiver si implementeaza metoda onReceive()*
public class StartedServiceBroadcastReceiver extends BroadcastReceiver {

    private TextView messageTextView;

    // TODO: exercise 10 - default constructor
    public StartedServiceBroadcastReceiver() {
        this.messageTextView = null;
    }

    public StartedServiceBroadcastReceiver(TextView messageTextView) {
        this.messageTextView = messageTextView;
    }

    /* implementarea metodei onReceive()* care are ca argumente contextul in care a fost
     invocata si intentia prin intermediul careia a fost transmis mesajul */
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: exercise 7 - get the action and the extra information from the intent
        // and set the text on the messageTextView

        String action = intent.getAction();
        String data = null;

        if (Constants.ACTION_STRING.equals(action)) {
            data = intent.getStringExtra(Constants.DATA);
        }
        if (Constants.ACTION_INTEGER.equals(action)) {
            data = String.valueOf(intent.getIntExtra(Constants.DATA, -1));
        }
        if (Constants.ACTION_ARRAY_LIST.equals(action)) {
            data = intent.getStringArrayListExtra(Constants.DATA).toString();
        }

        // afisarea efectiva a datelor: string-ul "EIM", integer-ul "2017" si array-ul "[EIM,2017]"
        if (messageTextView != null) {
            messageTextView.setText(messageTextView.getText().toString() + "\n" + data);
        }
        // modificare ex. 9, lab 5 (vezi AndroidManifest.xml)
        else {
            Intent startedServiceActivityIntent = new Intent(context, StartedServiceActivity.class);
            startedServiceActivityIntent.putExtra(Constants.MESSAGE, data);
            startedServiceActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(startedServiceActivityIntent);
        }

        // TODO: exercise 10 - restart the activity through an intent
        // if the messageTextView is not available

    }



}
