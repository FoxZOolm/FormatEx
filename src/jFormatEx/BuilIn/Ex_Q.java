
package jFormatEx.BuilIn;

import jFormatEx.ExFormat;

public class Ex_Q extends ExFormat{
    private String pre;
    private String post;
    @Override public String proceed(String v, String p) {	
	return pre.concat(v).concat(post);
    }
    @Override public String precomp(String p){
	if (p.equals("")) p="\"";
	p+=p;
	pre=p.substring(0,1);
	post=p.substring(1,2);
	return null;
    }
}
