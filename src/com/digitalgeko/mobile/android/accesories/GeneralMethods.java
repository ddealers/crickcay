package com.digitalgeko.mobile.android.accesories;


import gt.com.santillana.trazos.android.R;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

public final class GeneralMethods {

	public static boolean isKey(JSONArray nombres, String key) {
		boolean response = false;
		for(int i = 0; i < nombres.length(); i++) {
			try {
				if(nombres.getString(i).equals(key)) {
					response = true;
				}
			} catch (JSONException e) {
				Log.e("GeneralMethods - isKey", e.getMessage());
			}
		}
		return response;
	}
	
	public static void crearDialogoOk(String message, Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message);
		builder.setNeutralButton(context.getString(R.string.base_btnOK), new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}			
		});		
		builder.create().show();
	}
	
	public static void crearDialogFinish(String message, Context context, final Activity activity){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message);
		builder.setNeutralButton(context.getString(R.string.base_btnOK), new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				activity.finish();
			}			
		});		
		builder.create().show();
	}
	
	public static void exitOfSystem(String message, final Context context, final Activity activity){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message);
		builder.setNeutralButton(context.getString(R.string.base_btnOK), new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				salirDelSistema(context, activity);
			}			
		});		
		builder.create().show();
	}
	
	public static void showDialogYesNo(String message, final Context context, 
			final Activity activity, final Runnable runnable){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message);
		builder.setPositiveButton(context.getString(R.string.base_btnYes), new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				runnable.run();
			}			
		});
		builder.setNegativeButton(context.getString(R.string.base_btnNo), new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}			
		});
		builder.setCancelable(true);
		builder.create().show();
	}
	
	public static void salirDelSistema(Context context, Activity activity){
		// TODO
//		activity.startActivity(new Intent().setComponent(new ComponentName(context, ViewValidateUser.class)));
//		MasterService.resetPile();
//		MasterService.setToken(null);
//		WSValidateLealtadUser.cardNumber = null;
//		WSLoginVirtual.response = null;
	}
	
}
