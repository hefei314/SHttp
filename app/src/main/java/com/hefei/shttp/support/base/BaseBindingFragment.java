package com.hefei.shttp.support.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * <pre>
 *     author: hefei
 *     time  : 2021/02/25
 *     desc  :
 * </pre>
 */
public abstract class BaseBindingFragment<T extends ViewBinding> extends Fragment {

    protected Context mContext;

    protected T binding;

    protected boolean isFirstLoad = true;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            initArguments();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Type superclass = getClass().getGenericSuperclass();
        Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
        try {
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            binding = (T) method.invoke(null, getLayoutInflater(), container, false);
            initViews();
        } catch (Exception e) {
            throw new NullPointerException("ViewBinding is null");
        }
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isAlwaysUpdateViews()) {
            updateViews();
        } else {
            if (isFirstLoad) {
                updateViews();

                isFirstLoad = false;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirstLoad = true;
    }

    protected boolean isAlwaysUpdateViews() {
        return true;
    }

    protected void initArguments() {

    }

    protected abstract void initViews();

    protected abstract void updateViews();
}
