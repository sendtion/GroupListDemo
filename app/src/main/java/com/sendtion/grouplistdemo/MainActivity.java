package com.sendtion.grouplistdemo;

import android.os.SystemClock;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.sendtion.grouplistdemo.base.BaseActivity;

import androidx.recyclerview.widget.LinearLayoutManager;

public class MainActivity extends BaseActivity {

    GroupRecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //setStatusBarDarkMode();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new GroupItemDecoration<String,Article>());
        mRecyclerView.setAdapter(new ArticleAdapter(this));
        mRecyclerView.notifyDataSetChanged();

        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallScale);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.LineScale);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //refresh data here
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRecyclerView.refreshComplete();
                            }
                        });
                    }
                }).start();
            }

            @Override
            public void onLoadMore() {
                // load more data here
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRecyclerView.loadMoreComplete();
                            }
                        });
                    }
                }).start();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mRecyclerView != null){
            mRecyclerView.destroy(); // this will totally release XR's memory
            mRecyclerView = null;
        }
    }
}
