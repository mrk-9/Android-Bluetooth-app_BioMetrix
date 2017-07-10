/**
 * 
 */
package com.biomx.android.client.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Raghu
 * 
 */
public class XTextView extends TextView {

	public XTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public XTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		if (isInEditMode()) {
			return;
		}

		Typeface tf = Typeface.createFromAsset(context.getAssets(),
				"fonts/SEGOEUIL.TTF");
		setTypeface(tf);
	}

	public XTextView(Context context) {
		super(context);
	}

}
