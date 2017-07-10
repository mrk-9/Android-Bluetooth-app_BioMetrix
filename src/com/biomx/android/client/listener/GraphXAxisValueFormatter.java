package com.biomx.android.client.listener;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple formatter to convert bar indexes into sensor names.
 */
public class GraphXAxisValueFormatter extends Format {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8155298511324831308L;
	public long startCount;

	public GraphXAxisValueFormatter() {
		this.changeStartCount();
	}

	@Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
    	Number num = (Number) obj;
    	int value = num.intValue() / 20;
    	long dateL = 0;
		dateL =  this.startCount + (value * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss", Locale.US);
        Date date = new Date(dateL);
		toAppendTo.append(sdf.format(date));
        return toAppendTo;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return null;  // We don't use this so just return null for now.
    }
    
    public void changeStartCount() {
    	this.startCount = System.currentTimeMillis();
	}
}