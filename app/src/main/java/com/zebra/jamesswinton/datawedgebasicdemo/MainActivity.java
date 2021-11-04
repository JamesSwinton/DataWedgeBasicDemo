package com.zebra.jamesswinton.datawedgebasicdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import com.zebra.jamesswinton.datawedgebasicdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

  // Helper
  private DWUtilities mDwUtilities;

  // UI
  private ActivityMainBinding mDataBinding;

  /**
   * Sending Data To DW
   */

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Init DWUtils
    mDwUtilities = new DWUtilities(this);

    // Create Layout Binding
    mDataBinding = DataBindingUtil
        .setContentView(this, R.layout.activity_main);

    // Create Fragment
    getSupportFragmentManager()
        .beginTransaction()
        .add(mDataBinding.fragmentContainer.getId(),
            new CreateProfileFragment(mDwUtilities),
            "")
        .commit();
  }

  /**
   * Receiving Data From DW
   */

  @Override
  protected void onResume() {
    super.onResume();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("com.symbol.datawedge.api.RESULT_ACTION");
    intentFilter.addAction("com.symbol.datawedge.api.NOTIFICATION_ACTION");
    intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
    registerReceiver(DWReceiver, intentFilter);
  }

  @Override
  protected void onPause() {
    super.onPause();
    unregisterReceiver(DWReceiver);
  }

  private final BroadcastReceiver DWReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      String intentAction = intent.getAction();
      if ("com.symbol.datawedge.api.RESULT_ACTION".equals(intentAction)) {
        if ((intent.hasExtra("RESULT"))) {
          Log.i(this.getClass().getName(), "Command Result Received");
          boolean success = mDwUtilities.handleDataWedgeResult(intent);
          if (success) {
            switch (intent.getStringExtra("COMMAND_IDENTIFIER")) {
              case "CreateProfile":
                changeFragmentState(FragmentState.AssociateApp);
                break;
              case "AssociateApp":
                changeFragmentState(FragmentState.ConfigureIntentOutput);
                break;
              case "ConfigureIntentOutput":
                changeFragmentState(FragmentState.PerformScan);
                break;
            }
          }
        }
      }
    }
  };

  private void changeFragmentState(FragmentState fragmentState) {
    switch(fragmentState){
      case CreateProfile:
        getSupportFragmentManager()
            .beginTransaction()
            .replace(mDataBinding.fragmentContainer.getId(),
                new CreateProfileFragment(mDwUtilities),
                "CreateProfileFragment")
            .commit();
        break;
      case AssociateApp:
        getSupportFragmentManager()
            .beginTransaction()
            .replace(mDataBinding.fragmentContainer.getId(),
                new AssociateAppFragment(mDwUtilities),
                "AssociateAppFragment")
            .commit();
        break;
      case ConfigureIntentOutput:
        getSupportFragmentManager()
            .beginTransaction()
            .replace(mDataBinding.fragmentContainer.getId(),
                new ConfigureIntentOutputFragment(mDwUtilities),
                "ConfigureIntentFragment")
            .commit();
        break;
      case PerformScan:
        getSupportFragmentManager()
            .beginTransaction()
            .replace(mDataBinding.fragmentContainer.getId(),
                new PerformScanFragment(mDwUtilities),
                "ScanFragment")
            .commit();
        break;
    }
  }
}