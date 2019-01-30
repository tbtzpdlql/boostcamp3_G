package com.boostcamp.dreampicker.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<B extends ViewDataBinding> extends Fragment {
    protected final String TAG = getClass().getSimpleName();

    protected B binding;
    protected Context context;

    protected abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        context = binding.getRoot().getContext();

        return binding.getRoot();
    }

    public void makeToast(String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}