package jFormatEx.BuilIn;

import jFormatEx.ExFormat;

public class Ex_Tag extends ExFormat{
    private String pre;
    private String post;
    @Override public String proceed(String v, String p) {
	return pre.concat(v).concat(v).concat(post);
    }
    @Override public String precomp(String p){
	String a="";
	int y=p.indexOf(" ");
	if (y>0) {
	    a=p.substring(y);
	    p=p.substring(0,y);	    
	}
	pre=String.format("<%s%s>", p,a);
	post=String.format("</%s>",p);
	return null;
    }
    
}
