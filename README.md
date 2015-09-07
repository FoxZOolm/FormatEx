# FormatEx

look like String.format but with plugin extension<br>
--- usage ---<br>
FormatEx foo=new FormatEx("<...>{<index>|<path>:<function>[=<par>][+<function>[=<par>]}<...>");<br>
...<br>
String result=foo.ex(<object>[,<object>...))<br>
...<br>
<br>
--- add custom function plugin ---<br>
FormatEx.exformat.add("<name>",new ExFormat(){<br>
	    @Override public String proceed(String v, String p) {<br>
		    // v=value, p=par<br>
	    }<br>
	});<br>
	<br>
	or<br>
	<br>
	public class foo extends ExFomat{<br>
	    @Override public String proceed(String v, String p) {<br>
		    // v=value, p=par<br>
	    }<br>
	}<br>
	<br>
	...<br>
	<br>
	FormatEx.exformat.add("name",foo);<br>
	<br>
	--- exemple ---<br>
	see text
