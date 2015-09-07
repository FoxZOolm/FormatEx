package test;

import jFormatEx.ExFormat;
import jFormatEx.FormatEx;

public class JFormatEx {
    static class test{
	public String fname="foxz";
	public String lname="oolm";
	public String foo(){
	    return ("from foo");
	}
    }
    public static void main(String[] args) {
	FormatEx.exFormat.put("u", new ExFormat() {
	    @Override
	    public String proceed(String v, String p) {
		return v.toUpperCase();
	    }
	});
	FormatEx.exFormat.put("l",new ExFormat(){
	    @Override
	    public String proceed(String v, String p) {
		return v.toLowerCase();
	    }
	});
        FormatEx f=new FormatEx("test:>{#0.toto:l}<");
	test t=new test();
        System.out.print(f.ex(t));
    }
}
