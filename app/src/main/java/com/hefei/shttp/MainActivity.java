package com.hefei.shttp;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.hefei.retrofit.HttpUtils;
import com.hefei.retrofit.callback.ACallback;
import com.hefei.retrofit.callback.UCallback;
import com.hefei.shttp.adapter.CategoryListAdapter;
import com.hefei.shttp.databinding.ActivityMainBinding;
import com.hefei.shttp.entity.CategoryBean;
import com.hefei.shttp.entity.base.Data;
import com.hefei.shttp.support.base.BaseBindingActivity;
import com.orhanobut.logger.Logger;

import java.util.List;

import retrofit2.Call;

public class MainActivity extends BaseBindingActivity<ActivityMainBinding> {

    private CategoryListAdapter categoryListAdapter;

    @Override
    protected void initViews() {
        categoryListAdapter = new CategoryListAdapter(null);
        binding.rvCategoryList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCategoryList.setAdapter(categoryListAdapter);
    }

    @Override
    protected void updateViews() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_get) {
            get();
        } else if(item.getItemId() == R.id.menu_upload) {
            upload();
        }
        return super.onOptionsItemSelected(item);
    }

    private void get() {
        HttpUtils.GET("categories/Article")
                .request(new ACallback<Data<List<CategoryBean>>>() {
                    @Override
                    public void onSuccess(Data<List<CategoryBean>> data) {
                        if (data.getStatus() == 100) {
                            Logger.e(new Gson().toJson(data.getData()));

                            categoryListAdapter.setNewInstance(data.getData());
                        }
                    }

                    @Override
                    public void onFail(String errMsg) {
                        Logger.e(errMsg);
                    }
                });
    }

    private void upload() {
        HttpUtils.UPLOAD("", new UCallback() {
            @Override
            public void onProgress(long currentLength, long totalLength, float percent) {

            }

            @Override
            public void onFail(int errCode, String errMsg) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        }).request(new ACallback<Object>() {
            @Override
            public void onSuccess(Object data) {

            }

            @Override
            public void onFail(String errMsg) {

            }
        });
    }
}