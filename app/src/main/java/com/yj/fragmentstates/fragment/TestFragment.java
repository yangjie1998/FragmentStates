package com.yj.fragmentstates.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yj.fragmentstates.R;

/**
 * Created by Administrator on 2017/4/2.
 */

public class TestFragment extends BaseFragment implements BaseFragment.OnReLoadDataListener {
    private RecyclerView mTestRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private String[] mTestData = new String[10];
    private Handler handler = new Handler();
    private boolean isLoad = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_test);
        initView();
        initData();
        return getContentView();
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(mActivity);
        mTestRecyclerView = (RecyclerView) findViewById(R.id.rv_test);
        mTestRecyclerView.setLayoutManager(mLayoutManager);
        mTestRecyclerView.setAdapter(new TestAdapter());
        setOnReLoadDataListener(this);
    }

    private void initData() {
        int length = mTestData.length;
        for (int i = 0; i < length; i++) {
            mTestData[i] = "TEST" + i;
        }
        request();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showErrorPage("请检查网络...",R.drawable.ic_error);
                isLoad = true;
            }
        },3000);
    }

    @Override
    public void request() {
        showLoadDataPage("正在加载...", R.drawable.ic_loading);
        if(isLoad){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTestRecyclerView.setAdapter(new TestAdapter());
                    showContentView();
                }
            },2000);
        }
    }

    class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_txt, null);
            return new TestHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TestHolder testHolder = (TestHolder) holder;
            if(testHolder.item_tv == null){
                Log.i("TAG","testHolder.item_tv==null");
            }
            testHolder.item_tv.setText(mTestData[position]);

        }

        @Override
        public int getItemCount() {
            return mTestData.length;
        }

        class TestHolder extends RecyclerView.ViewHolder {
            private TextView item_tv;
            public TestHolder(View itemView) {
                super(itemView);
                item_tv = (TextView)itemView.findViewById(R.id.tv_test);
            }
        }
    }
}
