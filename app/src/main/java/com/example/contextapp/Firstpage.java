package com.example.contextapp;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Firstpage extends Activity {
Button button1,button2,button3;
EditText edt;
ListView lst;
String[] name,number,total;
int m;
FileOutputStream fs;
File f;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstpage);
		button1= (Button)findViewById(R.id.button1);
		button2= (Button)findViewById(R.id.button2);
		button3= (Button)findViewById(R.id.button3);
		edt=(EditText)findViewById(R.id.edt);
		lst=(ListView)findViewById(R.id.lst);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContentResolver cr=getContentResolver();
				Cursor c=cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
				name=new String[c.getCount()];
				number=new String[c.getCount()];
				total=new String[c.getCount()];
				m=c.getCount();
				c.moveToFirst();
				for(int i=0;i<c.getCount();i++){
					name[i]=c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
					number[i]=c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
					total[i]=name[i]+number[i];
					c.moveToNext();
				}
				 ArrayAdapter<String> a=new ArrayAdapter<String>(Firstpage.this, android.R.layout.simple_list_item_1,total);
				  lst.setAdapter(a);
			}
		});
button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File path=Environment.getExternalStorageDirectory();
				 f=new File(path, "Contacts.txt");
					try {
						FileOutputStream fs= new FileOutputStream(f,true);
					for(int i=0;i<m;i++)
					{
					fs.write((total[i]+"\n").getBytes());
					}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
				
			}
		});
	
button3.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String Email = edt.getText().toString();
		Uri u=Uri.fromFile(f);
		Intent i=new Intent(Intent.ACTION_SEND);
		i.setType("vnd.android.cursor.dir/email");
		i.putExtra(Intent.EXTRA_EMAIL, new String[]{Email});
		i.putExtra(Intent.EXTRA_STREAM,u);
		i.putExtra(Intent.EXTRA_SUBJECT, "contacts");
		startActivity(i.createChooser(i, "sending data"));
		
	}
});
lst.setOnItemClickListener(new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		//Toast.makeText(MainActivity.this, ""+parent.getItemAtPosition(position), Toast.LENGTH_LONG);
		
	}
});

	}
	

	
}
