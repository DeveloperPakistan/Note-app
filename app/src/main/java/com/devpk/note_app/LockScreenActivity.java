package com.devpk.note_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.devpk.note_app.SharedPref.Setting;
import com.devpk.note_app.SharedPref.SharedPref;
import com.devpk.note_app.activity.MainActivity;
import com.devpk.note_app.methods.Methods;
import com.devpk.note_app.view.pflockscreen.PFFLockScreenConfiguration;
import com.devpk.note_app.view.pflockscreen.fragments.PFLockScreenFragment;
import com.devpk.note_app.view.pflockscreen.security.PFResult;
import com.devpk.note_app.view.pflockscreen.viewmodels.PFPinCodeViewModel;

public class LockScreenActivity extends AppCompatActivity {

    private SharedPref sharedPref;
    private Methods methods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

        sharedPref = new SharedPref(this);
        methods = new Methods(this);

        showLockScreenFragment();
    }

    private void showLockScreenFragment() {
        new PFPinCodeViewModel().isPinCodeEncryptionKeyExist().observe(this, new Observer<PFResult<Boolean>>() {
            @Override
            public void onChanged(PFResult<Boolean> booleanPFResult) {

                if (booleanPFResult == null) {
                    return;
                }
                if (booleanPFResult.getError() != null) {
                    methods.showSnackBar("Can not get pin code info", "error");
                    return;
                }

                showLockScreenFragment(booleanPFResult.getResult());

            }
        });
    }


    private final PFLockScreenFragment.OnPFLockScreenCodeCreateListener mCodeCreateListener = new PFLockScreenFragment.OnPFLockScreenCodeCreateListener() {
        @Override
        public void onCodeCreated(String encodedCode) {
            methods.showSnackBar("Code created","success");
            sharedPref.saveToPref(encodedCode, true);
            Setting.in_code = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onBackPressed();
                }
            },500);
        }

        @Override
        public void onNewCodeValidationFailed() {
            methods.showSnackBar("Code validation error","error");
        }
    };

    private final PFLockScreenFragment.OnPFLockScreenLoginListener mLoginListener = new PFLockScreenFragment.OnPFLockScreenLoginListener() {

        @Override
        public void onCodeInputSuccessful() {
            methods.showSnackBar("Code successfull","success");
            showMainFragment();
        }

        @Override
        public void onFingerprintSuccessful() {
            methods.showSnackBar("Fingerprint successfull","success");
            showMainFragment();
        }

        @Override
        public void onPinLoginFailed() {
            methods.showSnackBar("Pin failed","error");
        }

        @Override
        public void onFingerprintLoginFailed() {
            methods.showSnackBar("Fingerprint failed","error");
        }
    };

    private void showMainFragment() {
        Intent main = new Intent(LockScreenActivity.this, MainActivity.class);
        startActivity(main);
        finish();
    }

    private void showLockScreenFragment(boolean isPinExist) {
        final PFFLockScreenConfiguration.Builder builder = new PFFLockScreenConfiguration.Builder(this)
                .setTitle(isPinExist ? "Unlock with your pin code or fingerprint" : "Create Code")
                .setCodeLength(4)
                .setLeftButton("Can't remeber")
                .setNewCodeValidation(true)
                .setNewCodeValidationTitle("Please input code again")
                .setUseFingerprint(true);

        final PFLockScreenFragment fragment = new PFLockScreenFragment();

        fragment.setOnLeftButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methods.showSnackBar("Left button pressed","success");
            }
        });

        builder.setMode(isPinExist
                ? PFFLockScreenConfiguration.MODE_AUTH
                : PFFLockScreenConfiguration.MODE_CREATE);
        if (isPinExist) {
            fragment.setEncodedPinCode(sharedPref.getCode());
            fragment.setLoginListener(mLoginListener);
        }

        fragment.setConfiguration(builder.build());
        fragment.setCodeCreateListener(mCodeCreateListener);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_view, fragment).commit();



    }
}