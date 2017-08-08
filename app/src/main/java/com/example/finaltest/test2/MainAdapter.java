package com.example.finaltest.test2;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.finaltest.R;

import java.util.List;

/**
 * Created by pinan on 2017/7/7.
 */

public class MainAdapter extends BaseQuickAdapter<MainModel, BaseViewHolder> {
    public MainAdapter(List<MainModel> data) {
        super(R.layout.item_main, data);
    }
    
    @Override
    protected void convert(BaseViewHolder holder, MainModel model) {
        holder.setText(R.id.tv_title, model.title)
            .setText(R.id.tv_desc, model.desc)
            .setText(R.id.tv_number, model.icon + "")
            .addOnClickListener(R.id.fl);
    }
}
