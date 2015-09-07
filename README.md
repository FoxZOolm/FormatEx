# FormatEx

look like String.format but with plugin extension

--- usage ---

FormatEx foo=new FormatEx("<...>{<index>|<path>:<function>[=<par>][+<function>[=<par>]}<...>");

...

String result=foo.ex(<object>[,<object>...))

...


--- add custom function plugin ---

FormatEx.exformat.add("<name>",new ExFormat(){

	    @Override
	    
	    public String proceed(String v, String p) {
	    
		    // v=value, p=par
		    
	    }
	    
	});
	
	
	or
	
	
	public class foo extends ExFomat{
	
	    @Override
	
	    public String proceed(String v, String p) {
	
		    // v=value, p=par
	
	    }
	
	}
	
	...
	
	FormatEx.exformat.add("name",foo);
	
	
	--- exemple ---
	
	see text
