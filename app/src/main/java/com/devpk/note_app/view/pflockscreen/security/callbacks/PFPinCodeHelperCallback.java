package com.devpk.note_app.view.pflockscreen.security.callbacks;

import com.devpk.note_app.view.pflockscreen.security.PFResult;

public interface PFPinCodeHelperCallback<T> {
    void onResult(PFResult<T> result);
}
