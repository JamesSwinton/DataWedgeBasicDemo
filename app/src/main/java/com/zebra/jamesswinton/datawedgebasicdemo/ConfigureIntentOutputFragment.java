package com.zebra.jamesswinton.datawedgebasicdemo;

import android.os.Bundle;
import android.text.TextUtils;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zebra.jamesswinton.datawedgebasicdemo.databinding.FragmentConfigureIntentOutputBinding;
import com.zebra.jamesswinton.datawedgebasicdemo.databinding.FragmentCreateProfileBinding;

public class ConfigureIntentOutputFragment extends Fragment {

  // UI
  private FragmentConfigureIntentOutputBinding mDataBinding;

  // DW Utils
  private DWUtilities mDWUtilities;

  public ConfigureIntentOutputFragment(DWUtilities dwUtilities) {
    this.mDWUtilities = dwUtilities;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_configure_intent_output, container,
        false);
    return mDataBinding.getRoot();
  }

  @Override
  public void onStart() {
    super.onStart();
    mDataBinding.configureIntentOutputButton.setOnClickListener(v -> mDWUtilities
        .configureIntentOutput());
  }
}