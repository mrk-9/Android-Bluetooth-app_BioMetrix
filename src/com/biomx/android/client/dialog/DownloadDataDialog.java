package com.biomx.android.client.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

import com.biomx.android.client.R;
import com.biomx.android.client.adapter.DownloadDataListAdapter;
import com.biomx.android.client.listener.DownloadDataCloseButtonClickListener;
import com.biomx.android.client.listener.DownloadDataDeleteButtonClickListener;
import com.biomx.android.client.listener.DownloadDataDownloadButtonOnClickListener;
import com.biomx.android.client.listener.DownloadDataSelectAllCheckBoxOnCheckedChangeListener;

/**
 * @author Raghu
 * 
 */
public class DownloadDataDialog extends Dialog {

	public DownloadDataDialog(Context context) {
		super(context);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.layout_download_data);
		ListView sensitivityListView = (ListView) this
				.findViewById(R.id.list_view_biomx_data);

		List<String> selectedFiles = new ArrayList<String>();
		BaseAdapter sensitivitySettingsListAdapter = new DownloadDataListAdapter(
				selectedFiles);
		sensitivityListView.setAdapter(sensitivitySettingsListAdapter);
		sensitivitySettingsListAdapter.notifyDataSetChanged();

		CheckBox checkBox = (CheckBox) this
				.findViewById(R.id.text_view_list_file_chooser_all);
		OnCheckedChangeListener downloadDataCheckBoxOnCheckedChangeListener = new DownloadDataSelectAllCheckBoxOnCheckedChangeListener(
				sensitivityListView);
		checkBox.setOnCheckedChangeListener(downloadDataCheckBoxOnCheckedChangeListener);

		Button doneButton = (Button) this.findViewById(R.id.button_download);
		android.view.View.OnClickListener doneButtonOnClickListener = new DownloadDataDownloadButtonOnClickListener(
				this, selectedFiles);
		doneButton.setOnClickListener(doneButtonOnClickListener);

		Button deleteButton = (Button) this.findViewById(R.id.button_delete);
		android.view.View.OnClickListener downloadDataDeleteFileButtonClickListener = new DownloadDataDeleteButtonClickListener(
				selectedFiles, sensitivitySettingsListAdapter);
		deleteButton
				.setOnClickListener(downloadDataDeleteFileButtonClickListener);

		Button closeButton = (Button) this.findViewById(R.id.button_close);
		android.view.View.OnClickListener downloadDataCloseButtonClickListener = new DownloadDataCloseButtonClickListener(
				this);
		closeButton.setOnClickListener(downloadDataCloseButtonClickListener);
	}
}
