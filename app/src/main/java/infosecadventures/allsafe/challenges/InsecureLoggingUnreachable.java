package infosecadventures.allsafe.challenges;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import infosecadventures.allsafe.R;

/**
 * Activity for testing reachability analysis.
 * Contains an unreachable branch with the MD5 cryptographic method.
 */
public class InsecureLoggingUnreachable extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insecure_logging, container, false);
        setHasOptionsMenu(true);
        TextInputEditText secret = view.findViewById(R.id.secret);
        secret.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE && !Objects.requireNonNull(secret.getText()).toString().equals("")) {
                Log.d("ALLSAFE", "User entered secret: " + secret.getText().toString());
            }
            return false;
        });
        // Call unreachable code branch for reachability analysis testing
        unreachableCodeBranch();
        return view;
    }

    /**
     * Unreachable branch containing MD5 cryptographic function for reachability analysis testing.
     * This code is intentionally unreachable to test static analysis tools.
     */
    private void unreachableCodeBranch() {
        // This condition is always false, so the code below is unreachable
        if (false) {
            // Unreachable: MD5 hashing function
            String input = "test";
            String hash = md5(input);
            Log.d("ALLSAFE_UNREACHABLE", "MD5 Hash: " + hash);
            
            // Additional unreachable calls to the MD5 method
            md5("password123");
            md5("secret_data");
            md5("sensitive_information");
        }
    }

    /**
     * MD5 cryptographic hashing method (only reachable through unreachable code path).
     * This method should be flagged as unreachable by static analysis tools.
     */
    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
