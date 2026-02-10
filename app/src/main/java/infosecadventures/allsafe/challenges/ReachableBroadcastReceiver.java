package infosecadventures.allsafe.challenges;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * BroadcastReceiver A - REACHABLE
 * 
 * This receiver is declared in AndroidManifest.xml with an intent-filter
 * for the action "infosecadventures.allsafe.action.REACHABLE_TEST".
 * 
 * MainActivity.onCreate sends a broadcast with this exact action,
 * making this receiver REACHABLE at runtime.
 * 
 * The vulnerable action logs sensitive token data without validation.
 */
public class ReachableBroadcastReceiver extends BroadcastReceiver {

    private static final String ACTION_REACHABLE_TEST = "infosecadventures.allsafe.action.REACHABLE_TEST";
    private static final String EXTRA_TOKEN = "token";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && ACTION_REACHABLE_TEST.equals(intent.getAction())) {
            String token = intent.getStringExtra(EXTRA_TOKEN);
            
            // EXPECT_REACHABLE_VULN: This sensitive data logging IS reachable at runtime
            // The broadcast is sent from MainActivity.onCreate, making this vulnerable sink reachable
            Log.d("ALLSAFE_BROADCAST", "Received token from reachable intent: " + token);
            Log.i("ALLSAFE_REACHABLE", "Processing reachable broadcast with sensitive data: " + token);
            
            // Additional vulnerable operations
            if (token != null && !token.isEmpty()) {
                // Validate? No - accepting whatever comes in
                Log.e("ALLSAFE_SECURITY", "Token value (unvalidated): " + token);
                
                // Store in shared preferences without encryption
                android.content.SharedPreferences prefs = context.getSharedPreferences(
                        "broadcast_tokens", Context.MODE_PRIVATE);
                prefs.edit().putString("last_token", token).apply();
                
                Log.d("ALLSAFE_STORAGE", "Token stored in shared preferences");
            }
        }
    }
}
