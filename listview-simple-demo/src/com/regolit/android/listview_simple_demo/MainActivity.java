package com.regolit.android.listview_simple_demo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.regolit.android.listview_simple_demo.R;

public class MainActivity extends Activity {
	protected ArrayAdapter<String> mAdapter;
	protected ListView mListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// инициaлизируем интерфейс на основе указанного макета
		setContentView(R.layout.activity_main);
		
		// создаём адаптер
		mAdapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1);
		// находим виджет ListView по его идентификатору из макета		
		mListView = (ListView)findViewById(R.id.listview);
		// и назначаем ему только что созданный адаптер
		mListView.setAdapter(mAdapter);
		
		// находим виджет кнопки
		Button button = (Button)findViewById(R.id.button);
		// и назначаем ему обработчик нажатия
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// просто добавляем строчку в списке со словом test
				// сколько раз нажмём, столько строчек и добавится
				mAdapter.add("test");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		// save
		saveListViewLines();
	}
	
	protected void saveListViewLines() {
		FileOutputStream fos;
		try {
			// открываем файл в приватном пространстве приложения
			fos = openFileOutput("listview-lines.txt", Context.MODE_PRIVATE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		int cnt = mAdapter.getCount();
		String item;
		
		for (int i=0; i<cnt; ++i) {
			try {
				item = mAdapter.getItem(i) + "\n";
				fos.write( item.getBytes("UTF-8") );
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
