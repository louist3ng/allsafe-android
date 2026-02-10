package infosecadventures.allsafe.challenges;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * BroadcastReceiver B - UNREACHABLE
 * 
 * This receiver is declared in AndroidManifest.xml with an intent-filter
 * for the action "infosecadventures.allsafe.action.UNREACHABLE_TEST".
 * 
 * This action is NEVER sent anywhere in the application, making this
 * receiver UNREACHABLE at runtime.
 * 
 * Despite being declared in the manifest, the vulnerable code in onReceive
 * is never executed because no broadcast with the matching action is sent.
 * 
 * The code is identical to the reachable receiver, but this one never executes.
 */
public class UnreachableBroadcastReceiver extends BroadcastReceiver {

    private static final String ACTION_UNREACHABLE_TEST = "infosecadventures.allsafe.action.UNREACHABLE_TEST";
    private static final String EXTRA_TOKEN = "token";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && ACTION_UNREACHABLE_TEST.equals(intent.getAction())) {
            String token = intent.getStringExtra(EXTRA_TOKEN);
            
            // EXPECT_UNREACHABLE_VULN: This sensitive data logging is UNREACHABLE at runtime
            // No broadcast with ACTION_UNREACHABLE_TEST is ever sent from anywhere in the app,
            // so this vulnerable sink is never executed.
            Log.d("ALLSAFE_BROADCAST", "Received token from unreachable intent: " + token);
            Log.i("ALLSAFE_UNREACHABLE", "Processing unreachable broadcast with sensitive data: " + token);
            
            // Additional vulnerable operations (never executed)
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
