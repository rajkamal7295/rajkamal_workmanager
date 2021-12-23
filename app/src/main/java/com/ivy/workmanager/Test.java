package com.ivy.workmanager;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class Test extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public Test(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
