package com.zebra.jamesswinton.datawedgebasicdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class DWUtilities {

  // Context
  private Context mContext;
  private String mProfileName = "DWBasicDemo";

  public DWUtilities(Context context) {
    this.mContext = context;
  }

  /**
   * Creates a profile in DataWedge with the specified name.
   *
   * Profile will be created with all default values & no app association will be made
   *
   * @param profileName - Name of profile in DataWedge
   */

  public void createProfile(String profileName) {
    // Save ProfileName for later...
    this.mProfileName = profileName;

    // Create DataWedge Intent
    Intent createProfileIntent = new Intent();
    createProfileIntent.setAction("com.symbol.datawedge.api.ACTION");

    // Add Profile Name to Intent
    createProfileIntent.putExtra("com.symbol.datawedge.api.CREATE_PROFILE", profileName);

    // Optionally: Add extras to specify we want to receive the result. See below for options.
    // The CommandIdentifier value is used to distinguish between different results in DataWedge
    createProfileIntent.putExtra("SEND_RESULT", "LAST_RESULT"); // NONE, LAST_RESULT, COMPLETE_RESULT
    createProfileIntent.putExtra("COMMAND_IDENTIFIER","CreateProfile");

    // Send Intent to DataWedge
    mContext.sendBroadcast(createProfileIntent);
  }

  /**
   * This method builds the intent required to associate apps with a datawedge profile, based on the
   * package and activity name. The activity name can be replaced with a "*" if you want to include
   * all activities in a certain package.
   */

  public void associateApp() {
    // Create DataWedge Intent
    Intent associateAppIntent = new Intent();
    associateAppIntent.setAction("com.symbol.datawedge.api.ACTION");

    // Create Main Bundle - This contains the top level information such as which profile to target,
    // what update mode to use and the apps / activities that should be associated with this profile.
    Bundle mainBundle = new Bundle();
    mainBundle.putString("PROFILE_NAME", mProfileName);
    mainBundle.putString("PROFILE_ENABLED", "true");
    mainBundle.putString("CONFIG_MODE", "UPDATE"); // CREATE_IF_NOT_EXIST, OVERWRITE, UPDATE

    // Create App config Bundle
    Bundle appConfig = new Bundle();
    appConfig.putString("PACKAGE_NAME", this.getClass().getPackage().getName());
    appConfig.putStringArray("ACTIVITY_LIST", new String[]{ "*" });

    // Add app config to MainBundle
    mainBundle.putParcelableArray("APP_LIST", new Bundle[]{ appConfig });

    // Add MainBundle to Intent
    associateAppIntent.putExtra("com.symbol.datawedge.api.SET_CONFIG", mainBundle);

    // Optionally: Add extras to specify we want to receive the result. See below for options.
    // The CommandIdentifier value is used to distinguish between different results in DataWedge
    associateAppIntent.putExtra("SEND_RESULT", "LAST_RESULT"); // NONE, LAST_RESULT, COMPLETE_RESULT
    associateAppIntent.putExtra("COMMAND_IDENTIFIER","AssociateApp");

    // Send Intent to DataWedge
    mContext.sendBroadcast(associateAppIntent);
  }

  /**
   * This method builds the intent for configuring the "IntentOutput" plugin in DataWedge
   *
   * The IntentOutput plugin lets us configure how datawedge sends us scan data via intents.
   * We need to provide an Action string to identify the intent, as well as an delivery mode that
   * tells DataWedge how we want the intent to be send (broadcast, startActivity etc...)
   */

  public void configureIntentOutput() {
    // Create DataWedge Intent
    Intent associateAppIntent = new Intent();
    associateAppIntent.setAction("com.symbol.datawedge.api.ACTION");

    // Create Main Bundle - This contains the top level information such as which profile to target,
    // what update mode to use and the apps / activities that should be associated with this profile.
    Bundle mainBundle = new Bundle();
    mainBundle.putString("PROFILE_NAME", mProfileName);
    mainBundle.putString("PROFILE_ENABLED", "true");
    mainBundle.putString("CONFIG_MODE", "UPDATE"); // CREATE_IF_NOT_EXIST, OVERWRITE, UPDATE

    // Build the Intent Config bundle (Tells datawedge what plugin we're configuring)
    Bundle intentConfig = new Bundle();
    intentConfig.putString("PLUGIN_NAME", "INTENT");
    intentConfig.putString("RESET_CONFIG", "true");

    // Create the Intent Properties bundle - this is where we define the intent settings
    Bundle intentProps = new Bundle();
    intentProps.putString("intent_output_enabled", "true");
    intentProps.putString("intent_action", "com.zebra.dwbasicdemo.SCAN");
    intentProps.putString("intent_delivery", "2"); // 2 = Broadcast

    // Add Properties Bundle to Config Bundle
    intentConfig.putBundle("PARAM_LIST", intentProps);

    // Add Intent Config Bundle to MainBundle
    mainBundle.putBundle("PLUGIN_CONFIG", intentConfig);

    // Add MainBundle to Intent
    associateAppIntent.putExtra("com.symbol.datawedge.api.SET_CONFIG", mainBundle);

    // Optionally: Add extras to specify we want to receive the result. See below for options.
    // The CommandIdentifier value is used to distinguish between different results in DataWedge
    associateAppIntent.putExtra("SEND_RESULT", "LAST_RESULT"); // NONE, LAST_RESULT, COMPLETE_RESULT
    associateAppIntent.putExtra("COMMAND_IDENTIFIER","ConfigureIntentOutput");

    // Send Intent to DataWedge
    mContext.sendBroadcast(associateAppIntent);
  }

  /**
   * This method builds the scan intent & sends it to datawedge to trigger the scanner
   */

  public void startScan() {
    // Build & send Scan Intent
    Intent startScanIntent = new Intent();
    startScanIntent.setAction("com.symbol.datawedge.api.ACTION");
    startScanIntent.putExtra("com.symbol.datawedge.api.SOFT_SCAN_TRIGGER", "START_SCANNING");
    mContext.sendBroadcast(startScanIntent);
  }

  /**
   * Parse the result intent from DataWedge. This intent is broadcast after datawedge has processed
   * an intent from an application, provided the intent contains the "SEND_RESULT"
   * & "COMMAND_IDENTIFIER" extras.
   *
   * @param resultIntent - Intent received from DataWedge
   */

  public boolean handleDataWedgeResult(Intent resultIntent) {
    // Parse Values from Intent
    String result = resultIntent.getStringExtra("RESULT");
    String commandIdentifier = resultIntent.getStringExtra("COMMAND_IDENTIFIER");
    return result.equals("SUCCESS");
  }

  /**
   * Handle a Datawedge Scan intent. This intent is broadcast by DataWedge whenever a scan is
   * performed, provided the intent output plugin has been configured.
   *
   * @param scanIntent - Intent broadcast by datawedge on a scan event
   */

  public Scan handleDataWedgeScan(Intent scanIntent) {
    String data = scanIntent.getStringExtra("com.symbol.datawedge.data_string");
    String symbology = scanIntent.getStringExtra("com.symbol.datawedge.label_type");
    String source = scanIntent.getStringExtra("com.symbol.datawedge.source");
    return new Scan(data, symbology, source);
  }

}
