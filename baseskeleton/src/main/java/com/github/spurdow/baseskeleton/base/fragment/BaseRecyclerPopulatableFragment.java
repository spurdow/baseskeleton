package com.github.spurdow.baseskeleton.base.fragment;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.huemedscience.dsr.R;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by davidluvellejoseph on 12/18/16.
 */

public abstract class BaseRecyclerPopulatableFragment<E> extends BaseRecyclerFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = BaseRecyclerPopulatableFragment.class.getSimpleName();
    protected MaterialDialog populateProgress;


    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;


    @Override
    protected void setUp() {
        if(!showFab()){
            mFab.setVisibility(View.GONE);
        }else{
            mFab.setImageDrawable(ContextCompat.getDrawable(getContext() , fabIcon()));
        }

//        mRefreshLayout.setOnRefreshListener(this);
//        mDatabaseReference = mFirebaseDatabase.getReference("/"+getTableName());
//        mLayoutManager = getLayoutManager();
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        populateProgress = new MaterialDialog.Builder(getActivity())
//                .title(R.string.populating_records)
//                .content(R.string.please_wait_wildcard , "populating " + getTableName() +"records")
//                .positiveText(R.string.close)
//                .progress(true, 0)
//                .cancelable(false)
//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//
//                    }
//                }).build();
//
//        populate();
    }


    protected abstract RecyclerView.LayoutManager getLayoutManager();

    protected abstract String getTableName();

    @Override
    public void onRefresh() {
        onClearUpAdapter();
        populate();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    protected void populate(){

        new PopulateAsync().execute();

    }

    protected int fabIcon(){
        return R.drawable.fab_add;
    }

    protected void onPrePopulate(){
        mRefreshLayout.setRefreshing(true);
    }

    protected abstract void onClearUpAdapter();

    protected abstract void onDonePopulate(List<E> records);

    protected abstract void onErrorPopulate();


    protected abstract List<E> onGetDataAsync();

    protected abstract boolean showFab();


    class PopulateAsync extends AsyncTask<Void , Void , List<E>> {


        @Override
        protected List<E> doInBackground(Void... voids) {

            return onGetDataAsync();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            onPrePopulate();

        }

        @Override
        protected void onPostExecute(List<E> es) {
            super.onPostExecute(es);

            if(mRefreshLayout != null){
                mRefreshLayout.setRefreshing(false);
            }
            if(es != null && !es.isEmpty()) {

                showEmptyLayout(false);
                onDonePopulate(es);
            }else{
                onErrorPopulate();

            }
        }
    }
}
