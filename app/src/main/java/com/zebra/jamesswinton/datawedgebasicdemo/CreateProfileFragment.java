package com.zebra.jamesswinton.datawedgebasicdemo;

import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zebra.jamesswinton.datawedgebasicdemo.databinding.FragmentCreateProfileBinding;

public class CreateProfileFragment extends Fragment{

  // Default Profile Name
  private static final String DefaultProfileName = "DataWedgeBasicDemo";

  // UI
  private FragmentCreateProfileBinding mDataBinding;

  // DW Utils
  private DWUtilities mDWUtilities;

  public CreateProfileFragment(DWUtilities dwUtilities) {
    this.mDWUtilities = dwUtilities;
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_profile, container,
        false);
    return mDataBinding.getRoot();
  }

  @Override
  public void onStart() {
    super.onStart();
    mDataBinding.associateAppButton.setOnClickListener(v -> {
      String profileName = mDataBinding.profileName.getText() == null || TextUtils.isEmpty(mDataBinding.profileName.getText())
          ? DefaultProfileName
          : mDataBinding.profileName.getText().toString();
      mDWUtilities.createProfile(profileName);
    });
  }
}