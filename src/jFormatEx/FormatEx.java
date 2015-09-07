package jFormatEx;

import static jFormatEx.FormatEx.exFormat;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Objects.isNull;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FormatEx {

    public static String co="(c)SFJN FormatEx [140907] by foxz free.fr CC:ByNc";
    
    private class obj {
	protected int hid;
    };
    
    private String str;
    private int pos = 0;
    private int beg = 0;
    private int end = 0;

    private class ostring extends obj {
	public String str;

	public ostring(String s) {
	    str = s;
	    hid=1;
	}
    }

    private class ovar extends obj {
	private String var;

	public ovar(String v) {
	    var = v;
	    hid=2;
	}
    }

    private class ofun extends obj {
	public String par;
	public ExFormat func;

	public ofun(String n, String p) {
	    func = exFormat.get(n);
	    func.precomp(p);
	    par = p;
	    hid=3;
	}
    }

    private class oidx extends obj{
	public int idx=0;
	public oidx(int i){
	    idx=i;
	    hid=4;
	}
    }

    private List<obj> p = new ArrayList();

    private void pp() {
	if (beg>pos)return;
	String fn = str.substring(beg, pos);
	if (str.charAt(pos)=='=')pos++;
	beg=pos;
	for (; pos < end; pos++) {
	    switch (str.charAt(pos)) {
		case '}':		    
		    p.add(new ofun(fn,str.substring(beg, pos)));
		    beg=pos+1;
		    return;		    
		case '+':
		    p.add(new ofun(fn, str.substring(beg, pos)));
		    pf();
		    return;
		case '\\':
		    pos++;
		    break;
	    }
	}
    }
    
    private void pf() {
	pos++;
	beg = pos;
	for (; pos < end; pos++) {
	    switch (str.charAt(pos)) {
		case '=':
		case '+':
		    pp();
		    return;
		case '}':		    
		    pp();
		    beg=pos+1;
		    return;
	    }
	}
    }

    private void aov() {
	p.add(new ovar(str.substring(beg, pos)));
	beg = pos;
    }

    private void pv() {
	pos++;
	beg = pos;
	for (; pos < end; pos++) {
	    switch (str.charAt(pos)) {
		case '.':
		    aov();
		    beg++;
		    break;
		case ':':
		    aov();
		    pf();
		    return;
		case '}':
		    pf();
		    beg = pos;
		    return;
	    }
	}
    }

    private void dos() {
	String t = str.substring(beg, pos);
	p.add(new ostring(t));
	beg = pos;
    }
    
    private void aidx(){
	String t=str.substring(beg,pos);
	int i=Integer.parseInt(t);
	p.add(new oidx(i));
	beg=pos;
    }
    
    private void pidx(){
	for (;pos<end;pos++){
	    switch(str.charAt(pos)){
		case '#':
		    pos++;
		    beg=pos;
		    break;
		case '.':
		    aidx();
		    pv();
		    pos++;
		    beg=pos;
		    return;	
		case ':':
		    aidx();
		    pf();
		    return;
		case '}':
		    aidx();
		    pv();		    
		    pos++;
		    beg=pos+1;
		    return;
	    }
	}
    }

    public static Map<String, ExFormat> exFormat = new HashMap<>();
    
    public String ex(Object... pp)  {
	StringBuilder r = new StringBuilder();
	StringBuilder debug = new StringBuilder();
	int idx=0;
	Object oo=null;
	String cur=null;
	for (obj o : p) {
	    switch(o.hid) {
		case 1:
		    if(!isNull(cur))r.append(cur);
		    cur=null;
		    r.append(((ostring) o).str);
		    break;
		case 2:
		    String i=((ovar) o).var;
		    try {
			Field f=oo.getClass().getDeclaredField(i);
			f.setAccessible(true);		
			oo=f.get(oo);
			break;
		    } catch (NoSuchFieldException ex){
		    } catch (SecurityException|IllegalArgumentException|IllegalAccessException ex) {
			Logger.getLogger(FormatEx.class.getName()).log(Level.SEVERE, null, ex);
		    }
		    try{
			Method m=oo.getClass().getDeclaredMethod(i,null);
			m.setAccessible(true);
			oo=(Object) m.invoke(oo);
		    } catch (SecurityException|IllegalArgumentException|NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
			Logger.getLogger(FormatEx.class.getName()).log(Level.SEVERE, null, ex);
		    }
		    break;
		case 3:
		    ofun f = (ofun) o;
		    if (isNull(cur)) cur=oo.toString();
		    f.func.org=oo.toString();
		    cur=f.func.proceed(cur,f.par);
		    break;
		case 4:
		    idx=((oidx) o).idx;
		    oo=pp[idx];
	    }
	}
	if(!isNull(cur))r.append(cur);
	return r.toString();
    }

    public FormatEx(String s) {
	str = s;
	end = str.length();
	for (pos = 0; pos < end; pos++) {
	    switch (str.charAt(pos)) {
		case '\\':
		    pos++;
		    break;
		case '{':
		    dos();
		    pos++;beg=pos;
		    pidx();
		    break;		
	    }
	}
	dos();
    }
}
