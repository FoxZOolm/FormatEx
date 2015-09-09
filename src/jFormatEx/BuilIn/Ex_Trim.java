package jFormatEx.BuilIn;

import jFormatEx.ExFormat;

public class Ex_Trim extends ExFormat {

    @Override
    public String proceed(String v, String p) {
	return v.trim();
    }
    
}
