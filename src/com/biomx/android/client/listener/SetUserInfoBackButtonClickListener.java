/**
 * 
 */
package com.biomx.android.client.listener;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author Raghu
 *
 */
public class SetUserInfoBackButtonClickListener implements OnClickListener {

	private Button prevMenu;

	public SetUserInfoBackButtonClickListener(Button prevMenu) {
		this.prevMenu = prevMenu;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		prevMenu.callOnClick();
	}

}
