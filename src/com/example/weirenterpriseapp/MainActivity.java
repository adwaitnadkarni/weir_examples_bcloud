package com.example.weirenterpriseapp;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.weir.WeirManager;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Intents;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

		static Context context;
		static final String fileName="testfile.txt";
		//The log tag
		//static final String TAG="Weir_Test_1";
		//TAG to use for performance tests
		public static final String TAG = "WEIR_BCLOUD";
		//The DIFC tag
		static String tagName = "bcloud_tag";
		//For the bound service
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			createTag();
		}
		
		void createTag(){
			WeirManager weir = (WeirManager) getSystemService("weir");
			String tagDomains[] ={"smtp.mail.yahoo.com","imap.mail.yahoo.com"};
			weir.createTag(tagName, true, false, null, null, tagDomains);
		}
		

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
			if (id == R.id.action_settings) {
				return true;
			}
			return super.onOptionsItemSelected(item);
		}

		@Override
		protected void onStart() {
			super.onStart();
			context=this;
			SharedPreferences sp = this.getPreferences(MODE_PRIVATE);
			boolean tag_created = sp.getBoolean("tag_created", false);
			if(!tag_created){
				//WeirManager weir = (WeirManager) getSystemService("weir");
				//weir.createTag(tagName, false, false, null, null);
				Editor spedit = sp.edit();
				spedit.putBoolean("tag_created", true);
				spedit.commit();
			}
		}
		
		public static void addTag(View view){
			WeirManager weir = (WeirManager) context.getSystemService("weir");
			Log.i(TAG, "Adding tag:"+tagName);
			weir.addTagToLabel(context.getPackageName(), tagName);
		}
		
		public void insertContact(View view){
			// Creates a new Intent to insert a contact
			Intent intent = new Intent(Intents.Insert.ACTION);
			// Sets the MIME type to match the Contacts Provider
			intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
			
			//Data
			intent.putExtra(Intents.Insert.EMAIL, "secretemail@gmail.com");
			startActivity(intent);
		}
			
		public void shareData(View view) {	
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
			sendIntent.setType("text/plain");
			startActivity(sendIntent);
		}
		public void editData(View view) {	
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_EDIT);
			sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
			sendIntent.setType("text/plain");
			startActivity(sendIntent);
		}	
		
		/* File */
		public void writeFile(View view) {	
			try {
				Log.i(TAG, "WriteStart.");
				FileOutputStream fos = this.openFileOutput(fileName, MODE_PRIVATE);
				fos.write(1);
				fos.close();
				Log.i(TAG, "WriteEnd.");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}    
		
		public void readFile(View view) {	
			FileInputStream fis;
			try {
				Log.i(TAG, "ReadStart.");
				fis = this.openFileInput(fileName);
				fis.read();
				fis.close();
				Log.i(TAG, "ReadEnd.");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		} 
		
		public void readExternal(View view) {
			try {
				Log.i(TAG, "ReadExternalStart.");
				FileInputStream fis = new FileInputStream(this.getExternalFilesDir(null)+"/"+fileName);
				fis.read();
				fis.close();
				Log.i(TAG, "ReadExternalEnd.");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void writeExternal(View view) {
			try {
				Log.i(TAG, "WriteExternalStart.");
				FileOutputStream fis = new FileOutputStream(this.getExternalFilesDir(null)+"/"+fileName);
				fis.write(1);
				fis.close();
				Log.i(TAG, "WriteExternalEnd.");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	
/*
 * 
 */

