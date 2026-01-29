package infosecadventures.allsafe.challenges;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import infosecadventures.allsafe.R;

public class FilterTouchesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_touches);

        // Vulnerability: Setting this to false allows touches to be processed
        // even when the window is obscured by other windows or dialogs.
        // This can allow attackers to intercept or simulate touches.
        getWindow().setFilterTouchesWhenObscured(false);

        TextView explanationText = findViewById(R.id.filter_touches_explanation);
        if (explanationText != null) {
            explanationText.setText("This activity demonstrates the vulnerability of setFilterTouchesWhenObscured(false).\n\n" +
                    "When this flag is set to false, touches are processed even if the window is obscured " +
                    "by other windows or dialogs. This can allow attackers to:\n" +
                    "- Intercept user touches intended for other applications\n" +
                    "- Simulate touches without the user's knowledge\n" +
                    "- Perform unauthorized actions on behalf of the user");
        }
    }
}
