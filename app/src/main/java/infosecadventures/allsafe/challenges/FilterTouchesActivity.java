package infosecadventures.allsafe.challenges;

import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import infosecadventures.allsafe.R;

public class FilterTouchesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_touches);

        // Apply to the root view of the activity
        View rootView = findViewById(android.R.id.content);
        rootView.setFilterTouchesWhenObscured(false);

        TextView explanationText = findViewById(R.id.filter_touches_explanation);
        if (explanationText != null) {
            explanationText.setText(
                    "This activity demonstrates an insecure configuration of filterTouchesWhenObscured.\n\n" +
                            "When set to false on a View, touches are accepted even if the window is obscured, " +
                            "allowing tapjacking-style attacks."
            );
        }
    }
}
