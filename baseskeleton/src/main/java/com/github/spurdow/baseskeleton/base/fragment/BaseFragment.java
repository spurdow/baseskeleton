package com.github.spurdow.baseskeleton.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.spurdow.baseskeleton.base.utils.gc.GcTrigger;
import com.huemedscience.dsr.DSRApplication;
import com.huemedscience.dsr.R;
import com.huemedscience.dsr.callbacks.OnToolbarTextChange;
import com.huemedscience.dsr.utils.GcTriggerUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Android on 3/2/2015.
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();
    protected OnToolbarTextChange mOnToolbarTextChange = null;

    protected DSRApplication app;

    protected View resourceView = null;

    protected Unbinder mUnbinder;


    public String getTabTitle(){
        return null;
    }

    public abstract void onAcquireBundle(Bundle args);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(savedInstanceState != null) {
            Log.d(TAG , "SavedInstanceState");
            onAcquireBundle(savedInstanceState);
        }else if(args != null){
            onAcquireBundle(args);
        }

        if(mOnToolbarTextChange != null) {
            if (getTitle() != null) {
                mOnToolbarTextChange.onTitleChange(getTitle());
            }
            if (getSubTitle() != null) {
                mOnToolbarTextChange.onSubTitleChange(getSubTitle());
            }
        }
        setRetainInstance(retainInstance());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        resourceView = inflater.inflate(resourceId() , container , false);
        app = DSRApplication.get();
        app.inject(this);

        mUnbinder = ButterKnife.bind(this, resourceView);

        provideOnCreateView(inflater , container , savedInstanceState);

        setHasOptionsMenu(hasOptions());

        setUp();


        return resourceView;
    }


    protected abstract int resourceId();

    protected abstract void provideOnCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    protected abstract boolean hasOptions();

    protected abstract String getTitle();

    protected abstract String getSubTitle();

    protected abstract void setUp();

    protected abstract HashMap<String, Object> getStates();

    protected abstract void onOrientationLandscape();

    protected abstract void onOrientationPortrait();

    protected boolean retainInstance(){
        return false;
    }

    public void onEvent(String dummy){

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

    /**
     * removed adding title and subtitle during onAttach,
     * because onAttach is called first before onCreate thus not getting the bundle
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            try {
                mOnToolbarTextChange = (OnToolbarTextChange) context;

            }catch(ClassCastException ex){}
        }
    }

    protected void startActivity(Intent intent, View theView){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), theView, getString(R.string.transition));
            startActivity(intent , optionsCompat.toBundle());
        }else{
            startActivity(intent );
        }
    }

    protected void startActivityForResult(Intent intent , View theView , int requestCode){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), theView, getString(R.string.transition));
            startActivityForResult(intent , requestCode, optionsCompat.toBundle() );
        }else{
            startActivityForResult(intent  , requestCode);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG  , "OnSavedInstanceState");
        HashMap<String, Object> stateList = getStates();
        if(stateList != null) {
            for (Map.Entry<String, Object> entry : stateList.entrySet()) {
                if (entry.getValue() instanceof Integer) {
                    outState.putInt(entry.getKey(), (Integer) entry.getValue());
                } else if (entry.getValue() instanceof Double) {
                    outState.putDouble(entry.getKey(), (Double) entry.getValue());
                } else if (entry.getValue() instanceof String) {
                    outState.putString(entry.getKey(), (String) entry.getValue());
                } else if (entry.getValue() instanceof Long){
                    outState.putLong(entry.getKey() , (Long) entry.getValue());
                }
            }
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d(TAG , "Orientation Landscape");
            onOrientationLandscape();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Log.d(TAG , "Orientation Portrait");
            onOrientationPortrait();
        }
    }


}
