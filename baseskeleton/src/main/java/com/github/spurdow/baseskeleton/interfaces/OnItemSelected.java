package com.github.spurdow.baseskeleton.interfaces;

/**
 * Created by davidmontecillo on 26/12/2016.
 */

public interface OnItemSelected<E , K> {

    void onSelectItem(E adapter, K item);
}
