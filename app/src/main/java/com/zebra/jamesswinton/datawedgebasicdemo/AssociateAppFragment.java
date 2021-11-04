package com.zebra.jamesswinton.datawedgebasicdemo;

import android.os.Bundle;
import android.text.TextUtils;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zebra.jamesswinton.datawedgebasicdemo.databinding.FragmentAssociateAppBinding;
import com.zebra.jamesswinton.datawedgebasicdemo.databinding.FragmentCreateProfileBinding;

public class AssociateAppFragment extends Fragment {

  // UI
  private FragmentAssociateAppBinding mDataBinding;

  // DW Utils
  private DWUtilities mDWUtilities;

  public AssociateAppFragment(DWUtilities dwUtilities) {
    this.mDWUtilities = dwUtilities;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_associate_app, container,
        false);
    return mDataBinding.getRoot();
  }

  @Override
  public void onStart() {
    super.onStart();
    mDataBinding.associateAppButton.setOnClickListener(v -> mDWUtilities.associateApp());
  }
}