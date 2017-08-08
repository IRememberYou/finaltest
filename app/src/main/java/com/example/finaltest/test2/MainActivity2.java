package com.example.finaltest.test2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.finaltest.R;
import com.example.finaltest.app.BaseActivity;
import com.example.finaltest.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by pinan on 2017/7/7.
 */

public class MainActivity2 extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.RequestLoadMoreListener {
    @Bind(R.id.rv)
    RecyclerView rv;
    private MainAdapter adapter;
    private List<MainModel> datas = new ArrayList<>();
    private int anim;//记录动画（共5种）
    private View mHeadView;
    private View mFootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        rv.setLayoutManager(new GridLayoutManager(this, 2, GridLayout.VERTICAL, false));
        rv.setAdapter(adapter = new MainAdapter(datas));
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.isFirstOnly(false);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        adapter.setOnLoadMoreListener(this, rv);
        adapter.setEmptyView(getEmpty());
        getData();
    }

    public void getData() {
        List<MainModel> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add(new MainModel("title " + i, "desc " + i, i));
        }
        datas.clear();
        datas.addAll(data);
        adapter.notifyDataSetChanged();
    }

    public List<MainModel> getData(String str) {
        List<MainModel> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add(new MainModel(str + ":title " + i, str + ":desc " + i, i));
        }
        return data;
    }

    @OnClick(R.id.btn_clear)
    void clear() {
        datas.clear();
        adapter.notifyDataSetChanged();
        mCurrentCounter=0;

    }

    @OnClick(R.id.btn_add)
    void add() {
        datas.add(0, new MainModel("title 新增的数据", "title 新增的数据", datas.size() + 1));
        adapter.notifyDataSetChanged();
        mCurrentCounter++;
    }

    @OnClick(R.id.btn_charger)
    void charger() {
        if (0 < anim && anim <= 5) {
            adapter.openLoadAnimation(anim);
            anim++;
        } else {
            anim = 1;
            adapter.openLoadAnimation(anim);
            anim++;
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_add_head)
    void addHead() {
//        mHeadView = getHeadView();
//        adapter.addHeaderView(mHeadView);
            adapter.setEnableLoadMore(true);
    }

    @OnClick(R.id.btn_add_foot)
    void addFoot() {
        mFootView = getFootView();
        adapter.addFooterView(mFootView);
    }

    @OnClick(R.id.btn_remove_head)
    void removeHead() {
        adapter.removeAllHeaderView();
    }

    @OnClick(R.id.btn_remove_foot)
    void removeFoot() {
        if (mFootView != null) {
            adapter.removeFooterView(mFootView);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ToastUtil.show("条目" + i + "被点击了");
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ToastUtil.show("局部" + i + "被点击了");
    }

    public View getHeadView() {
        return LayoutInflater.from(this).inflate(R.layout.item_head, null);
    }

    public View getFootView() {
        return LayoutInflater.from(this).inflate(R.layout.item_foot, null);

    }

    public View getEmpty() {
        return LayoutInflater.from(this).inflate(R.layout.item_empty, null);
    }

    private boolean isErr;
    private int mCurrentCounter = datas.size();

    @Override
    public void onLoadMoreRequested() {

        rv.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mCurrentCounter >= 100) {
                    //Data are all loaded.
                    adapter.loadMoreEnd();
                } else {
                    if (!isErr) {
                        //Successfully get more data
                        adapter.addData(getData("夹在更多数据"));
                        mCurrentCounter = adapter.getData().size();
                        adapter.loadMoreComplete();
                    } else {
                        //Get more data failed
                        isErr = true;
                        ToastUtil.show("add more err");
                        adapter.loadMoreFail();
                    }
                }
            }
        }, 3000);

    }
}
