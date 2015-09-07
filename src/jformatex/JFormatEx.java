package jformatex;

import jFormatEx.ExFormat;
import jFormatEx.FormatEx;

public class JFormatEx {
    static class test{
	public String fname="testfname";
	public String lname="testlname";
	public String toto(){
	    return ("lioi");
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
