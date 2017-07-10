/**
 * 
 */
package com.biomx.android.client.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * @author Raghu
 *
 */
public class XButton extends Button {

	public XButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public XButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// Typeface.createFromAsset doesn't work in the layout editor.
		// Skipping...
		if (isInEditMode()) {
			return;
		}

		Typeface tf = Typeface.createFromAsset(context.getAssets(),
				"fonts/SEGOEUIL.TTF");
		setTypeface(tf);
	}

	public XButton(Context context) {
		super(context);
	}

}
