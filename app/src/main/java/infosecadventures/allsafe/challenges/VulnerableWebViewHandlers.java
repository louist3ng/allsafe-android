package infosecadventures.allsafe.challenges;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import infosecadventures.allsafe.R;

/**
 * Fragment demonstrating reachability analysis with vulnerable WebView sinks.
 * 
 * This fragment contains two click handlers with identical vulnerable code:
 * - Handler A: REACHABLE - wired to a button in the UI
 * - Handler B: UNREACHABLE - defined but never registered or invoked
 * 
 * Both handlers call WebView.addJavascriptInterface() with a vulnerable implementation
 * that exposes sensitive data through a bridge interface.
 */
public class VulnerableWebViewHandlers extends Fragment {

    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vulnerable_webview_handlers, container, false);
        
        webView = view.findViewById(R.id.vulnerable_webview);
        Button triggerButton = view.findViewById(R.id.trigger_vulnerable_handler);
        
        // Wire Handler A to the button - this is REACHABLE
        triggerButton.setOnClickListener(getReachableHandler());
        
        return view;
    }

    /**
     * Handler A: REACHABLE - This handler is wired to a real button
     * 
     * @return View.OnClickListener containing reachable vulnerable sink
     */
    private View.OnClickListener getReachableHandler() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EXPECT_REACHABLE_VULN: This vulnerable sink IS reachable at runtime
                webView.addJavascriptInterface(new VulnerableBridge(), "bridge");
                webView.loadUrl("javascript:bridge.getSecret()");
            }
        };
    }

    /**
     * Handler B: UNREACHABLE - This handler is defined but never registered
     * 
     * This View.OnClickListener is never wired to any button or view.
     * It is not invoked through setOnClickListener, indirect callbacks, or any other means.
     * This handler exists only to test reachability analysis detection.
     */
    private View.OnClickListener unreachableHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // EXPECT_UNREACHABLE_VULN: This vulnerable sink is UNREACHABLE at runtime
            webView.addJavascriptInterface(new VulnerableBridge(), "bridge");
            webView.loadUrl("javascript:bridge.getSecret()");
        }
    };

    /**
     * Vulnerable JavaScript bridge that exposes sensitive data to JavaScript context.
     * This demonstrates a classic Android WebView vulnerability.
     */
    private static class VulnerableBridge {
        @android.webkit.JavascriptInterface
        public String getSecret() {
            // This method is exposed to JavaScript and can be called from web content
            // It returns sensitive data that should never be exposed
            return "SENSITIVE_TOKEN_12345_SECRET_KEY_67890";
        }

        @android.webkit.JavascriptInterface
        public String getUserCredentials() {
            // Another sensitive method exposed through the JavaScript bridge
            return "admin:password123";
        }

        @android.webkit.JavascriptInterface
        public String getDatabasePassword() {
            // Database credentials exposed via JavaScript interface
            return "db_admin:supersecurepassword";
        }
    }
}
