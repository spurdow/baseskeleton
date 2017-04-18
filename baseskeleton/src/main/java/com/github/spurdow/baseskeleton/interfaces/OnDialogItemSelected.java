package com.github.spurdow.baseskeleton.interfaces;

/**
 * Created by davidmontecillo on 10/01/2017.
 */

public interface OnDialogItemSelected<K , E , D> {
    void onItemSelected(K adapter, E item, D dialog);
}
