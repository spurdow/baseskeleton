package com.github.spurdow.baseskeleton.interfaces;

/**
 * Created by davidmontecillo on 14/01/2017.
 */

public interface OnItemRemoveListener<K , E> {

    E remove(K adapter, int position);

    void remove(K adapter, E object);

}
