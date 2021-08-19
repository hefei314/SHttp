package com.hefei.shttp.support.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

import com.hefei.shttp.R;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/02/24
 *     desc  :
 * </pre>
 */
public abstract class BaseBindingActivity<T extends ViewBinding> extends AppCompatActivity {

    protected T binding;

    @Override
    protected void onStart() {
        super.onStart();
        initArguments();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Type superclass = getClass().getGenericSuperclass();
        Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
        try {
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class);
            binding = (T) method.invoke(null, getLayoutInflater());
            setContentView(binding.getRoot());
            initViews();
            updateViews();
        } catch (Exception e) {
            throw new NullPointerException("ViewBinding is null");
        }
    }

    protected void initActionbar(boolean showHomeAsUp) {
        initActionbar(R.string.app_name, showHomeAsUp);
    }

    protected void initActionbar(@StringRes int resTitle, boolean showHomeAsUp) {
        initActionbar(getString(resTitle), showHomeAsUp);
    }

    protected void initActionbar(CharSequence title, boolean showHomeAsUp) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title == null || title.length() == 0 ? getString(R.string.app_name) : title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
        }
    }

    protected void initToolbar(Toolbar toolbar, boolean showHomeAsUp) {
        CharSequence title = getString(R.string.app_name);
        if (toolbar != null && toolbar.getTitle() != null && !"".contentEquals(toolbar.getTitle())) {
            title = toolbar.getTitle();
        }
        initToolbar(toolbar, title, showHomeAsUp);
    }

    protected void initToolbar(Toolbar toolbar, @StringRes int resTitle, boolean showHomeAsUp) {
        initToolbar(toolbar, getString(resTitle), showHomeAsUp);
    }

    protected void initToolbar(Toolbar toolbar, CharSequence title, boolean showHomeAsUp) {
        toolbar.setTitle(title);
        if (toolbar.getNavigationIcon() == null) {
            toolbar.setNavigationIcon(R.drawable.icon_back);
        }
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
        }
    }

    protected void setToolbarTitle(@StringRes int resTitle) {
        setToolbarTitle(getString(resTitle));
    }

    protected void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void initArguments() {

    }

    protected abstract void initViews();

    protected abstract void updateViews();
}
