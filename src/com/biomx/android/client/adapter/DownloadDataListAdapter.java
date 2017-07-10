/**
 * 
 */
package com.biomx.android.client.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.biomx.android.client.R;
import com.biomx.android.client.listener.DownloadDataCheckBoxOnCheckedChangeListener;
import com.biomx.android.client.util.DataHolder;

/**
 * @author Raghu
 * 
 */
public class DownloadDataListAdapter extends BaseAdapter {

	private List<String> selectedFiles;
	private File dataDirectory;

	public DownloadDataListAdapter(List<String> selectedFiles) {
		this.selectedFiles = selectedFiles;
		File root = android.os.Environment.getExternalStorageDirectory();
		this.dataDirectory = new File(root.getAbsolutePath()
				+ DataHolder.homeFolderPath);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return dataDirectory.list().length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return this.dataDirectory.list()[position];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int id) {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View downloadDataItemView,
			ViewGroup parent) {
		Context context = parent.getContext();
		if (downloadDataItemView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			downloadDataItemView = inflater.inflate(
					R.layout.layout_download_data_item, null);
		}
		String fileName = (String) this.getItem(position);

		CheckBox checkBox = (CheckBox) downloadDataItemView
				.findViewById(R.id.text_view_list_file_chooser);
		OnCheckedChangeListener downloadDataCheckBoxOnCheckedChangeListener = new DownloadDataCheckBoxOnCheckedChangeListener(
				fileName, this.selectedFiles);
		checkBox.setOnCheckedChangeListener(downloadDataCheckBoxOnCheckedChangeListener);

		TextView nameTextView = (TextView) downloadDataItemView
				.findViewById(R.id.text_view_list_file_name);
		nameTextView.setText(fileName);

		return downloadDataItemView;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public void notifyDataSetInvalidated() {
		super.notifyDataSetInvalidated();
	}

}
