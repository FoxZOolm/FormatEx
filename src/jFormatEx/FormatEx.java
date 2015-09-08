package jFormatEx;

import jFormatEx.BuilIn.Ex_L;
import jFormatEx.BuilIn.Ex_Q;
import jFormatEx.BuilIn.Ex_Tag;
import jFormatEx.BuilIn.Ex_U;
import static jFormatEx.FormatEx.exFormat;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Objects.isNull;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FormatEx {
    
    public static String ex(String p,Object... o){
	return new FormatEx(p).ex(o);
    };

    public static String co="(c)SFJN FormatEx [140908] by foxz free.fr CC:ByNc";

    private static Map<String, ExFormat> addstd() {
	Map<String,ExFormat>r=new HashMap<>();
	r.put("U",new Ex_U());
	r.put("l",new Ex_L());
	r.put("q",new Ex_Q());
	r.put("tag",new Ex_Tag());
	return r;
    }

    private static void Ex_Tag() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
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
	    Constructor o = null;
	    try {
		o = exFormat.get(n).getClass().getDeclaredConstructor();
	    } catch (SecurityException | NoSuchMethodException ex) {
		Logger.getLogger(FormatEx.class.getName()).log(Level.SEVERE, null, ex);
	    }	    
	    try {
		o.setAccessible(true);
		func=(ExFormat)o.newInstance();
	    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
		Logger.getLogger(FormatEx.class.getName()).log(Level.SEVERE, null, ex);
	    }	    
	    par=func.precomp(p);	    
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
		    beg=pos;
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
		    return;
	    }
	}
    }
    
    private class oarr extends obj{
	public Object idx=null;
	public oarr(String v){
	    hid=5;
	    Integer i=Integer.parseInt(v);
	    idx=i;	    
	    if (isNull(i)) idx=v;
	}
    }

    private void aov() {
	String t=str.substring(beg, pos);
	Integer i=Integer.parseInt(t);
	if (isNull(i)) {
	    p.add(new ovar(t));
	} else {
	    p.add(new oarr(t));
	}
	beg = pos;
    }

    private void pv() {
	pos++;
	beg = pos;
	for (; pos < end; pos++) {
	    switch (str.charAt(pos)) {
		case '#':
		    aov();
		    parr();
		    return;
		case '.':
		    aov();
		    beg++;
		    break;
		case ':':
		    aov();
		    pf();
		    return;
		case '}':
		    aov();
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
    
    private void aarr(){	
	p.add(new oarr(str.substring(beg,pos)));
	beg=pos;
    }
    
    
    private void parr(){
	pos++;
	beg = pos;
	for (; pos < end; pos++) {
	    switch (str.charAt(pos)) {
		case '#':
		    aarr();
		    break;
		case '.':
		    pv();
		    return;
		case ':':
		    aarr();
		    pf();
		    return;
		case '}':
		    aarr();
		    return;
	    }
	}
    }
    
    private void pidx(){
	pos++;
	beg=pos;
	for (;pos<end;pos++){
	    switch(str.charAt(pos)){
		case '#':
		    aidx();
		    parr();
		    return;
		case '.':
		    aidx();
		    pv();
		    return;	
		case ':':
		    aidx();
		    pf();
		    return;
		case '}':
		    aidx();
		    pv();		    
		    return;
	    }
	}
    }

    public static Map<String, ExFormat> exFormat = addstd();
	    
    public String ex(Object... pp)  {
	StringBuilder r = new StringBuilder();	
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
		    //f.func.org=oo.toString();
		    cur=f.func.proceed(cur,f.par);
		    break;
		case 4:
		    idx=((oidx) o).idx;
		    oo=pp[idx];		    
		    cur=oo.toString();
		    break;
		case 5:
		    oarr t=((oarr) o);
		    Object[] c=(Object[])oo;
		    oo=c[(Integer)t.idx];
		    cur=oo.toString();
		    break;
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
		    pidx();
		    beg=pos+1;
		    break;		
	    }
	}
	if (beg<pos)dos();
    }
}
