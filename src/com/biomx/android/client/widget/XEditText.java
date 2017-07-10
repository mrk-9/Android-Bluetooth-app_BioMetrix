/**
 * 
 */
package com.biomx.android.client.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * @author Raghu
 *
 */
public class XEditText extends EditText {

	public XEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public XEditText(Context context, AttributeSet attrs) {
		super(context, attrs);

		if (isInEditMode()) {
			return;
		}

		Typeface tf = Typeface.createFromAsset(context.getAssets(),
				"fonts/SEGOEUIL.TTF");
		setTypeface(tf);

	}

	public XEditText(Context context) {
		super(context);
	}

}
