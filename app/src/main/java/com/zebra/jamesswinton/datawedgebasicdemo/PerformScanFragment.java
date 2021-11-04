package com.zebra.jamesswinton.datawedgebasicdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zebra.jamesswinton.datawedgebasicdemo.databinding.FragmentAssociateAppBinding;
import com.zebra.jamesswinton.datawedgebasicdemo.databinding.FragmentPerformScanBinding;

public class PerformScanFragment extends Fragment {

  // UI
  private FragmentPerformScanBinding mDataBinding;

  // DW Utils
  private DWUtilities mDWUtilities;

  public PerformScanFragment(DWUtilities dwUtilities) {
    this.mDWUtilities = dwUtilities;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_perform_scan, container,
      false);
    return mDataBinding.getRoot();
}

  @Override
  public void onStart() {
    super.onStart();
    mDataBinding.associateAppButton.setOnClickListener(v -> mDWUtilities.startScan());
    IntentFilter intentFilter = new IntentFilter("com.zebra.dwbasicdemo.SCAN");
    intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
    getActivity().registerReceiver(DWReceiver, intentFilter);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    getActivity().unregisterReceiver(DWReceiver);
  }

  private final BroadcastReceiver DWReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      String intentAction = intent.getAction();
      if ("com.zebra.dwbasicdemo.SCAN".equals(intentAction)) {
        Scan scan = mDWUtilities.handleDataWedgeScan(intent);
        mDataBinding.scanData.setText(String.format("Data: %1$s \nSymbology: %2$s \nSource: %3$s",
            scan.getData(), scan.getSymbology(), scan.getSource()));
      }
    }
  };
}