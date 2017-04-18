package com.github.spurdow.baseskeleton.base.callbacks;

/**
 * Created by davidluvellejoseph on 9/4/16.
 */
public interface OnToolbarTextChange {
    void onTitleChange(String titleChange);

    void onTitleChange(int resId);

    void onSubTitleChange(String subTitleChange);

    void onSubTitleChange(int resId);
}
