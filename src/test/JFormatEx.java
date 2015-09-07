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
	
	
	FormatEx.exFormat.put("q", new ExFormat(){
	    @Override
	    public String proceed(String v, String p) {
		if (p.equals("")) p="\"";
		p+=p;
		return p.charAt(0)+v+p.charAt(1);
	    }
	});
	test t=new test();
	System.out.println(FormatEx.ex("test1:{#0.foo:l}",t));
 	System.out.println(new FormatEx("test2:{#0.foo:l+q}").ex(t));
	System.out.println(new FormatEx("test3:{#0.foo:l+q=*}").ex(t));
	System.out.println(new FormatEx("test4:{#0.foo:l+q=<>}").ex(t));
	System.out.println(new FormatEx("test5:{#0.foo:l}").ex(t));
 	System.out.println(new FormatEx("test6:{#0.foo:q+q=<>}").ex(t));
	System.out.println(new FormatEx("test7:{#0.foo:q=*+q}").ex(t));
	System.out.println(new FormatEx("test8:{#0.foo:q=*+q=<>}").ex(t));
	
    }
}
