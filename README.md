#FormatEx   
String.format look like more extendable and useful   

##--- Usage ---   
`FormatEx foo=new FormatEx("[...]{[<index>[.<path>][:<function>[=<parameter][+<function>...]}[...]");`    
...   
`foo.ex(fooObject);`    
...   

`<index>`=index of object...      
`<path>`=path in object tree      
`<function>`=function to call     

##--- Build-in function ---     
`q[=<sign>[sign]]`=> quote value with `sign` (" by default)     
`l`=> lower case value  
`U`=> upper case value  
`tag=<tag>[ data]`=>enclose value with "`<tag>`" like HTML style    
`trim` => trim String
    
##--- Custom function declaration ---   
`FormatEx.exFormat.add("<name>",new ExFormat(){    
    @Override String proceed(String v,String p){
        return (String)...
    }
}`      
    
or  
    
`class foo extends ExFormat {      
    @Override String proceed(String v, String p){   
        return (String)...  
    }   
}`
    
`v`=>value   
`p`=>parameter  

##--- Advanced usage ---
use direct `FormatEx.ex(<pattern>,<object...>);` (but no recommanded)   
    
`String ExFormat.precomp(String v){return (String)...}` is called for each function in pattern  
each function in pattern is newInstanced    

`<pattern>` is pseudo-compiled one time 

##--- Limitation ---    
object path tree can be a function but without parameter and must return a "`.toString()`" object   

##--- Example ---
`
FormatEx foo=new FormatEx("str= {1:U+q} and have {1.length} char");    
... 
print(foo.ex(fooobj0,foostr1)); 
`   
    
1st pattern:    
`1`=get value of foostr1    
`:`=function escape sequence    
`U`=call 'U' build-in function  
`+`=chaining function escape sequence      
`q`=call 'q' buil-in function   
    
2nd pattern:        
`1`=foostr1    
`.`=object tree escape sequence 
`length`=call `foostr1.length()` function
    
##--- Licence ---
(c)SJFN@ by Foxz at free.fr     CC:ByNc     
!!! no mil or gov usage at all !!! (except edu)     