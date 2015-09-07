
package jFormatEx.BuilIn;

import jFormatEx.ExFormat;

public class Ex_Q extends ExFormat{
    @Override public String proceed(String v, String p) {
	if (p.equals("")) p="\"";
	p+=p;
	return p.charAt(0)+v+p.charAt(1);
    }
}
