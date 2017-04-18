package com.github.spurdow.baseskeleton.base.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.clans.fab.FloatingActionButton;
import com.huemedscience.dsr.R;

import butterknife.BindView;

/**
 * Created by davidluvellejoseph on 9/4/16.
 */
public abstract class BaseRecyclerFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.empty_layout)
    protected LinearLayout mEmptyLayout;

    @BindView(R.id.image_empty)
    protected ImageView mEmptyImage;

    protected RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.fab)
    FloatingActionButton mFab;



    /**
     * Show/Hide Empty View
     * Default is VISIBLE
     * RecyclerView items > 0 then this should be shown
     * else this will not be shown
     * VISIBLE OR INVISIBLE ONLY
     * @param conditional
     */
    public void showEmptyLayout(boolean conditional){
        if(mEmptyLayout != null) {
            mEmptyLayout.setVisibility(conditional ? View.VISIBLE : View.INVISIBLE);
        }
    }



}
