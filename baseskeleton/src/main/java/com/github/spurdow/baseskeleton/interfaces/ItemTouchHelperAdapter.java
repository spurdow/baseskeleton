package com.github.spurdow.baseskeleton.interfaces;

/**
 * Created by davidmontecillo on 25/12/2016.
 */

public interface ItemTouchHelperAdapter<E> {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemSelect(int position);

    boolean isLongPressEnabled();

    boolean excludeSwipeOnClick();

    E getItem(int position);
}