package com.github.spurdow.baseskeleton.base.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.spurdow.baseskeleton.base.utils.gc.GcTrigger;
import com.huemedscience.dsr.utils.GcTriggerUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by davidluvellejoseph on 9/22/16.
 */
public abstract class BaseDialogFragment extends DialogFragment {

    public abstract int resourceId();

    public abstract void setUp(View customView);

    Unbinder mUnbinder;


//    @Inject
//    protected GcTriggerUtils gcTrigger;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View customView = inflater.inflate(resourceId() , container , false);
        mUnbinder = ButterKnife.bind(this , customView);
        setUp(customView);
        return customView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        GcTrigger.DEFAULT.runGc();

    }
}
