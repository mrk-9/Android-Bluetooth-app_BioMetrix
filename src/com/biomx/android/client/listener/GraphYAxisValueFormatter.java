package com.biomx.android.client.listener;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * A simple formatter to convert bar indexes into sensor names.
 */
public class GraphYAxisValueFormatter extends Format {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8155298511324831308L;

	public GraphYAxisValueFormatter() {
	}

	@Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
    	Number num = (Number) obj;
		double ceil = Math.ceil(num.doubleValue());
		if (Math.abs(ceil) == 0) {
			ceil = 0;
		}
		toAppendTo.append(ceil);
        return toAppendTo;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return null;  // We don't use this so just return null for now.
    }
    
}