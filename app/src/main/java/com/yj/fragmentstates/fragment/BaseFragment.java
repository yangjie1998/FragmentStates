package com.yj.fragmentstates.fragment;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yj.fragmentstates.R;

/**
 * Created by Administrator on 2017/4/2.
 */

public class BaseFragment extends Fragment {
    public LayoutInflater inflater;
    public ViewGroup container;
    private View view ,errorView;
    private ImageView error_iv;
    private TextView error_tv;
    private OnReLoadDataListener onReLoadDataListener;
    private RotateAnimation animator;
    public Activity mActivity;
    public void setContentView(int layout){
        view = inflater.inflate(layout,container,false);
    }
    public View getContentView(){
        return this.view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater  = inflater;
        this.container = container;
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    private void initBaseView(){
        errorView = findViewById(R.id.errorView);
        if(errorView != null){
            errorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onReLoadDataListener!=null){
                        onReLoadDataListener.request();
                    }
                }
            });
        }
        error_iv = (ImageView)findViewById(R.id.iv_error);
        error_tv = (TextView)findViewById(R.id.tv_error);
    }
    //显示加载页
    public void showLoadDataPage(String txt, int resId){
        initBaseView();
        if(errorView == null){
            return;
        }
        if(error_tv == null){
            return;
        }
        if(error_iv == null){
            return;
        }
        errorView.setVisibility(View.VISIBLE);
        error_tv.setText(txt);
        error_iv.setImageResource(resId);
        if(animator == null){
            animator = new RotateAnimation(0f,365f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            animator.setDuration(1000);
            animator.setRepeatCount(Integer.MAX_VALUE);
            animator.startNow();
        }
        error_iv.setAnimation(animator);
    }
    //显示异常页
    public void showErrorPage(String txt, int resId){
        initBaseView();
        if(errorView == null){
            return;
        }
        if(error_tv == null){
            return;
        }
        if(error_iv == null){
            return;
        }
        errorView.setVisibility(View.VISIBLE);
        error_tv.setText(txt);
        error_iv.setImageResource(resId);
        error_iv.setAnimation(null);
    }
    //显示内容页，隐藏异常状态页
    public void showContentView(){
        initBaseView();
        if(errorView == null){
            return;
        }
        if(error_tv == null){
            return;
        }
        if(error_iv == null){
            return;
        }
        errorView.setVisibility(View.GONE);
    }
    public View findViewById(int id){
        return view.findViewById(id);
    }
    public void setOnReLoadDataListener(OnReLoadDataListener onReLoadDataListener){
        this.onReLoadDataListener = onReLoadDataListener;
    }
    //加载数据接口
    public interface  OnReLoadDataListener{
        void request();
    }
}
