package com.regolit.android.listview_simple_demo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
		
		// восстановим содержимое списка при запуске
		restoreListViewLines();
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
			e.printStackTrace();
			return;
		}
		int cnt = mAdapter.getCount();
		String item;
		
		// считываем все элементы из адаптера и сохраняем их в файл
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
	
	protected void restoreListViewLines() {
		FileInputStream fis;
		try {
			// открываем файл из приватного хранилища
			fis = openFileInput("listview-lines.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		// создаём «читатель» данных из файла, чтобы прочитать
		// их построчно.
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		String line;
		
		try {
			while (true) {
				line = br.readLine();
				if (line == null) {
					break;
				}
				// и каждую успешно считанную строчку добавляем
				// в список через адаптер
				mAdapter.add(line);
			}
		} catch (IOException e) {
		}
	}
	

}
