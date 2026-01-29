package infosecadventures.allsafe.challenges;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import infosecadventures.allsafe.R;

public class StickyBroadcastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_broadcast);

        TextView explanationText = findViewById(R.id.sticky_broadcast_explanation);
        if (explanationText != null) {
            explanationText.setText("This activity demonstrates the vulnerability of sticky broadcasts.\n\n" +
                    "Sticky broadcasts remain in the system after being sent, allowing any app " +
                    "to receive them at any time. This is a security vulnerability because:\n" +
                    "- Sensitive data can be leaked through sticky broadcasts\n" +
                    "- Any app can register a receiver after the broadcast is sent and still receive it\n" +
                    "- Malicious apps can access information they shouldn't have access to");
        }

        Button sendBroadcastButton = findViewById(R.id.send_sticky_broadcast_button);
        if (sendBroadcastButton != null) {
            sendBroadcastButton.setOnClickListener(v -> {
                // Vulnerability: Using sendStickyBroadcast which is vulnerable
                Intent intent = new Intent("infosecadventures.allsafe.action.STICKY_MESSAGE");
                intent.putExtra("message", "Sensitive data exposed via sticky broadcast");
                intent.putExtra("timestamp", System.currentTimeMillis());
                
                // This is the vulnerability - sendStickyBroadcast keeps the broadcast in the system
                sendStickyBroadcast(intent);
                
                TextView statusText = findViewById(R.id.broadcast_status);
                if (statusText != null) {
                    statusText.setText("Sticky broadcast sent!\nThis broadcast will remain in the system and can be received by any app at any time.");
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up sticky broadcasts
        try {
            Intent intent = new Intent("infosecadventures.allsafe.action.STICKY_MESSAGE");
            removeStickyBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
