##XMLParser
<hr>
###What Is This?
This is a parser for XML that I wrote so that I could use it on another project that I am doing (It's basically like a 
Statistexts web application. Although, now that I think about it, I will probably have to rewrite it in javascript.
<br>
Anyway, this is a pretty basic XML Parser. You use it by creating an XMLParser object and passing an XML file into the 
constructor. You can then print the XML (pretty useless since you already have the basic xml), traverse the XML 
hierarchy, or search for specific elements by tag name. Since that is basically all I want to use it for, that's all I 
am going to implement for now but I might come back to it later.

###Current Limitations
* Unable to parse XML files that aren't fully-formed
    * This isn't a super big problem since most legit XML files should be correct. However, I know that this isn't 
    always the case so I would like to fix this at some point.
* Weird Errors that I didn't account for are just ignored
    * I did this so that if you have a long XML and only one line won't parse, you still are able to get some sort of
    result. Basically, any line that throws errors are skipped. This is obviously not the best solution, but it's finals
    and I really need to get back to studying.
* Comments are ignored, not sure what happens to multi-lined comments.
* Can only search by tag
