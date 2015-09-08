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
	
	
	FormatEx.exFormat.put("cf", new ExFormat(){
	    @Override
	    public String proceed(String v, String p) {
		return String.format("cf:[%s]%s",p,v);
	    };
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
	System.out.println(FormatEx.ex("test8:{#0.foo:cf=test}",t));	
	System.err.println(FormatEx.ex("test9:{#0.foo:tag=big id='0'}",t));
    }
}
