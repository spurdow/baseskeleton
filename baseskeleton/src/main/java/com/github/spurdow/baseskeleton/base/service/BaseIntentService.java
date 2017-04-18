package com.github.spurdow.baseskeleton.base.service;

import android.app.IntentService;

/**
 * Created by davidluvellejoseph on 9/18/16.
 */
public abstract class BaseIntentService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BaseIntentService(String name) {
        super(name);
    }


}
