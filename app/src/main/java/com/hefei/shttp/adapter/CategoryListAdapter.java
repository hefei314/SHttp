package com.hefei.shttp.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hefei.shttp.R;
import com.hefei.shttp.entity.CategoryBean;

import java.util.List;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/08/16
 *     desc  :
 * </pre>
 */
public class CategoryListAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {

    public CategoryListAdapter(@Nullable List<CategoryBean> data) {
        super(R.layout.item_category_list, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, CategoryBean categoryBean) {
        baseViewHolder.setText(R.id.tv_title, categoryBean.getTitle())
                .setText(R.id.tv_desc, categoryBean.getDesc());

        Glide.with(getContext())
                .load(categoryBean.getCoverImageUrl())
                .into((ImageView) baseViewHolder.getView(R.id.iv_cover));
    }
}
