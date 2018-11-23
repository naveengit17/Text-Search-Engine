import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.*;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
import java.util.HashMap;
import java.util.Map;

import java.io.*;
// class that going to return stopword list object in sorted order
class stopWord{
	public static String[] StringStopWord={
		"a","able","about","across","after","all","almost","also","am","among","an","and","any","are","as","at","be",
        "because","been","but","by","can","cannot","could","dear","did","do","does","either","else","ever","every",
		"for","from","get","got","had","has","have","he","her","hers","him","his","how","however","i","if","in","into","is",
		"it","its","just","least","let","like","likely","may","me","might","most","must","my","neither","no","nor",
	    "not","of","off","often","on","only","or","other","our","own","rather","said","say","says","she","should","since",
	    "so","some","than","that","the","their","them","then","there","these","they","this","tis","to","too","twas",
	    "us","wants","was","we","were","what","when","where","which","while","who","whom","why","will","with","would",
	    "yet","you","your","gt","lt","nbsp","amp","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y",
	   "z","accessed","alternate","american","archive","area","article","author","b","birth","book","born","br",
	    "c","caption","category","cite","city","class","com","common","country","d","date","de","death","defaultsort",
	    "description","display","dure","e","edit","end","establish","extern","f","file","first","follow",
	    "found","general","history","html","http","id","image","include","infobox","information","jpg",
	    "july","known","last","left","link","list","live","locate","long","m","made","march","metadata","more",
	    "n","name","nation","new","non","note","october","official","on","org","origin","out","over","p","page",
	    "part","people","persondata","php","place","publish","px","r","record","redirect","ref","refer",
	    "reflist","region","result","right","s","see","short","small","source","start","state","stub",
	    "style","such","t","templat","the","time","title","top","two","type","unit","up","url","us","user","w","web",
	    "website","wikipedia","work","world","www","year","age","april","associate","august","background",
	    "base","be","before","between","co","country","create","daily","december","en","family","february",
	    "free","group","html","index","january","june","l","language","license","main","make","mani","member",
	    "november","number","positive","public","second","september","size","st","summary","three","u",
	    "under","well","yes","-"," "

	};
	public static ArrayList<String> stopWordList=new ArrayList<String>();
    static int count=0;
	ArrayList<String> arraystoreStopWord(){
		for(int i=0;i<StringStopWord.length;i++){
			stopWordList.add(StringStopWord[i]);
			
		}
		Collections.sort(stopWordList);
		return stopWordList;
	}
}

class wikiPage {

	private String id;
	private String title;
	private String text;
	private String extenalLink;
	private String category;

	void setId(String id){
		this.id=id;
	}

	String getId(){
		return this.id;
	}

	void setTitle(String title){
		this.title=title;
	}

	String getTitle(){
		return this.title;
	}
	void setText(String text){
		this.text=text;
	}

	String getText(){
		return this.text;
	}
	void setExternalLink(String extenalLink){
		this.extenalLink=extenalLink;
	}

	String getExternalLink(){
		return this.extenalLink;
	}

	void setCategory(String category){
		this.category=category;
	}

	String getCategory(){
		return this.category;
	}

	    public void toString1() {
             // StringBuffer sb = new StringBuffer();
              System.out.println("Doc Details - ");
              System.out.println("Id:" + (getId()));
              System.out.println("title:" + getTitle());
              System.out.println("text:" + getText());
              System.out.println("External Link:" + getExternalLink());
              System.out.println("Category:" + getCategory());
       }
}

class SAXhandler extends DefaultHandler{
	private wikiPage Page;
	static int count=0;
	public static ArrayList<wikiPage> list=new ArrayList<wikiPage>();
  
    String temp1="";
     
    int first,second;
    String temp="";

    public void go(){
    	System.out.println(first+"   "+second);
    	//System.out.println("s2->>>   "+s2);
    	 
    }
    String link,link1;
     public void characters(char []buffer,int start,int end){
     	temp=new String(buffer,start,end);
   
     	temp1=temp1+temp;
     	link=new String(buffer,start,end);
     	link1=link+link1;
     }

     public void startElement(String uri,String LocalName,String qName,Attributes attributes)
     throws SAXException{
     	
     	//System.out.println("helloSTART --->>  "+qName);
     	if(qName.equalsIgnoreCase("page")){
     	//	System.out.println("haa");
     		Page=new wikiPage();
     	}
     	if(qName.equalsIgnoreCase("externalLink")){
     	  link="";link1="";
     	}
     	if(qName.equalsIgnoreCase("Category")){
           link="";link1="";
     	}
     }

     public void endElement(String uri,String LocalName,String qName) 
     throws SAXException{
   	   
   		 if(qName.equalsIgnoreCase("text")){ 
   		  Page.setText(temp1.trim());
     	}
     	else if(qName.equalsIgnoreCase("page")){
     		list.add(Page); 
     		temp1="";
     		temp="";
     	}
     	else if(qName.equalsIgnoreCase("id")){
     		Page.setId(temp1.trim()); 
     		temp1="";
     		temp="";
     	}
     	else if(qName.equalsIgnoreCase("title")){
     		Page.setTitle(temp1.trim());
     		temp1=""; 
     		temp="";
     	}else if(qName.equalsIgnoreCase("externalLink")){
            Page.setExternalLink(link1.trim());
             
     	}else if(qName.equalsIgnoreCase("Category")){
            Page.setCategory(link1.trim());
             
     	}

     }

     void display(){
     	//System.out.println("no of pages  "+list.size());

     	Iterator<wikiPage> it=list.iterator();
        stopWord.count=list.size();
     	while(it.hasNext()){
     		it.next().toString1();
     	}
     }

    public  static ArrayList<wikiPage> returnList(){
     	return list;
     }
 
}

// class for stemming
/*
  * Stemmer, implementing the Porter Stemming Algorithm
  *
  * The Stemmer class transforms a word into its root form.  The input
  * word can be provided a character at time (by calling add()), or at once
  * by calling one of the various stem(something) methods.
  */

 class Stemmer {

    private char[] b;
    private int i;     /* offset into b */
    private int i_end; /* offset to end of stemmed word */
    private int j;
    private int k;
    private static final int INC = 50;/* unit of size whereby b is increased */

    public Stemmer() {
       b = new char[INC];
       i = 0;
       i_end = 0;
    }

   /**
    * Add a character to the word being stemmed.  When you are finished
    * adding characters, you can call stem(void) to stem the word.
    */

   public void add(char ch) {
       if (i == b.length) {
           char[] new_b = new char[i+INC];
           for (int c = 0; c < i; c++)
               new_b[c] = b[c];
           b = new_b;
      }
      b[i++] = ch;
   }


   /** Adds wLen characters to the word being stemmed contained in a portion
    * of a char[] array. This is like repeated calls of add(char ch), but
    * faster.
    */

   public void add(char[] w, int wLen)
   {  if (i+wLen >= b.length)
      {  char[] new_b = new char[i+wLen+INC];
         for (int c = 0; c < i; c++) new_b[c] = b[c];
         b = new_b;
      }
      for (int c = 0; c < wLen; c++) b[i++] = w[c];
   }

   /**
    * After a word has been stemmed, it can be retrieved by toString(),
    * or a reference to the internal buffer can be retrieved by getResultBuffer
    * and getResultLength (which is generally more efficient.)
    */
   public String toString() { return new String(b,0,i_end); }

   /**
    * Returns the length of the word resulting from the stemming process.
    */
   public int getResultLength() { return i_end; }

   /**
    * Returns a reference to a character buffer containing the results of
    * the stemming process.  You also need to consult getResultLength()
    * to determine the length of the result.
    */
   public char[] getResultBuffer() { return b; }

   /* cons(i) is true <=> b[i] is a consonant. */

   private final boolean cons(int i)
   {  switch (b[i])
      {  case 'a': case 'e': case 'i': case 'o': case 'u': return false;
         case 'y': return (i==0) ? true : !cons(i-1);
         default: return true;
      }
   }

   /* m() measures the number of consonant sequences between 0 and j. if c is
      a consonant sequence and v a vowel sequence, and <..> indicates arbitrary
      presence,

         <c><v>       gives 0
         <c>vc<v>     gives 1
         <c>vcvc<v>   gives 2
         <c>vcvcvc<v> gives 3
         ....
   */

   private final int m() {
       int n = 0;
       int i = 0;
       while(true) {
           if (i > j)
               return n;
           if (! cons(i))
               break;
           i++;
       }
       i++;
       while(true) {
           while(true) {
               if (i > j)
                   return n;
               if (cons(i))
                   break;
               i++;
           }
           i++;
           n++;
           while(true) {
               if (i > j)
                   return n;
               if (! cons(i))
                   break;
               i++;
           }
           i++;
       }
   }

   /* vowelinstem() is true <=> 0,...j contains a vowel */

   private final boolean vowelinstem() {
       int i;
       for (i = 0; i <= j; i++)
           if (! cons(i))
               return true;
       return false;
   }

   /* doublec(j) is true <=> j,(j-1) contain a double consonant. */

   private final boolean doublec(int j) {
       if (j < 1)
           return false;
       if (b[j] != b[j-1])
           return false;
       return cons(j);
   }

   /* cvc(i) is true <=> i-2,i-1,i has the form consonant - vowel - consonant
      and also if the second c is not w,x or y. this is used when trying to
      restore an e at the end of a short word. e.g.

         cav(e), lov(e), hop(e), crim(e), but
         snow, box, tray.

   */

   private final boolean cvc(int i) {
       if (i < 2 || !cons(i) || cons(i-1) || !cons(i-2))
           return false;
       {
           int ch = b[i];
           if (ch == 'w' || ch == 'x' || ch == 'y')
               return false;
      }
      return true;
   }

   private final boolean ends(String s) {
       int l = s.length();
       int o = k-l+1;
       if (o < 0)
           return false;
       for (int i = 0; i < l; i++)
           if (b[o+i] != s.charAt(i)) return false;
      j = k-l;
      return true;
   }

   /* setto(s) sets (j+1),...k to the characters in the string s, readjusting
      k. */

   private final void setto(String s)
   {  int l = s.length();
      int o = j+1;
      for (int i = 0; i < l; i++) b[o+i] = s.charAt(i);
      k = j+l;
   }

   /* r(s) is used further down. */

   private final void r(String s) { if (m() > 0) setto(s); }

   /* step1() gets rid of plurals and -ed or -ing. e.g.

          caresses  ->  caress
          ponies    ->  poni
          ties      ->  ti
          caress    ->  caress
          cats      ->  cat

          feed      ->  feed
          agreed    ->  agree
          disabled  ->  disable

          matting   ->  mat
          mating    ->  mate
          meeting   ->  meet
          milling   ->  mill
          messing   ->  mess

          meetings  ->  meet

   */

   private final void step1()
   {  if (b[k] == 's')
      {  if (ends("sses")) k -= 2; else
         if (ends("ies")) setto("i"); else
         if (b[k-1] != 's') k--;
      }
      if (ends("eed")) { if (m() > 0) k--; } else
      if ((ends("ed") || ends("ing")) && vowelinstem())
      {  k = j;
         if (ends("at")) setto("ate"); else
         if (ends("bl")) setto("ble"); else
         if (ends("iz")) setto("ize"); else
         if (doublec(k))
         {  k--;
            {  int ch = b[k];
               if (ch == 'l' || ch == 's' || ch == 'z') k++;
            }
         }
         else if (m() == 1 && cvc(k)) setto("e");
     }
   }

   /* step2() turns terminal y to i when there is another vowel in the stem. */

   private final void step2() { if (ends("y") && vowelinstem()) b[k] = 'i'; }

   /* step3() maps double suffices to single ones. so -ization ( = -ize plus
      -ation) maps to -ize etc. note that the string before the suffix must give
      m() > 0. */

   private final void step3() { if (k == 0) return; /* For Bug 1 */ switch (b[k-1])
   {
       case 'a': if (ends("ational")) { r("ate"); break; }
                 if (ends("tional")) { r("tion"); break; }
                 break;
       case 'c': if (ends("enci")) { r("ence"); break; }
                 if (ends("anci")) { r("ance"); break; }
                 break;
       case 'e': if (ends("izer")) { r("ize"); break; }
                 break;
       case 'l': if (ends("bli")) { r("ble"); break; }
                 if (ends("alli")) { r("al"); break; }
                 if (ends("entli")) { r("ent"); break; }
                 if (ends("eli")) { r("e"); break; }
                 if (ends("ousli")) { r("ous"); break; }
                 break;
       case 'o': if (ends("ization")) { r("ize"); break; }
                 if (ends("ation")) { r("ate"); break; }
                 if (ends("ator")) { r("ate"); break; }
                 break;
       case 's': if (ends("alism")) { r("al"); break; }
                 if (ends("iveness")) { r("ive"); break; }
                 if (ends("fulness")) { r("ful"); break; }
                 if (ends("ousness")) { r("ous"); break; }
                 break;
       case 't': if (ends("aliti")) { r("al"); break; }
                 if (ends("iviti")) { r("ive"); break; }
                 if (ends("biliti")) { r("ble"); break; }
                 break;
       case 'g': if (ends("logi")) { r("log"); break; }
   } }

   /* step4() deals with -ic-, -full, -ness etc. similar strategy to step3. */

   private final void step4() {
       switch (b[k]) {
           case 'e':
               if (ends("icate")) {
                   r("ic"); break;
               }
               if (ends("ative")) {
                   r("");
                   break;
               }
               if (ends("alize")) {
                   r("al");
                   break;
               }
               break;
           case 'i':
               if (ends("iciti")) {
                   r("ic");
                   break;
               }
               break;
           case 'l':
               if (ends("ical")) {
                   r("ic");
                   break;
               }
               if (ends("ful")) {
                   r("");
                   break;
               }
               break;
           case 's':
               if (ends("ness")) {
                   r("");
                   break;
               }
               break;
       }
   }

   /* step5() takes off -ant, -ence etc., in context <c>vcvc<v>. */

   private final void step5() {
       if (k == 0)
           return; /* for Bug 1 */
       switch (b[k-1]) {
           case 'a':
               if (ends("al"))
                   break;
               return;
           case 'c':
               if (ends("ance"))
                   break;
               if (ends("ence"))
                   break;
               return;
           case 'e':
               if (ends("er"))
                   break;
               return;
           case 'i':
               if (ends("ic"))
                   break;
               return;
           case 'l':
               if (ends("able"))
                   break;
               if (ends("ible"))
                   break;
               return;
           case 'n':
               if (ends("ant"))
                   break;
               if (ends("ement"))
                   break;
               if (ends("ment"))
                   break;/* element etc. not stripped before the m */
               if (ends("ent"))
                   break;
               return;
           case 'o':
               if (ends("ion") && j >= 0 && (b[j] == 's' || b[j] == 't'))
                   break; /* j >= 0 fixes Bug 2 */
               if (ends("ou"))
                   break;
               return;/* takes care of -ous */
           case 's':
               if (ends("ism"))
                   break;
               return;
           case 't':
               if (ends("ate"))
                   break;
               if (ends("iti"))
                   break;
               return;
           case 'u':
               if (ends("ous"))
                   break;
               return;
           case 'v':
               if (ends("ive"))
                   break;
               return;
           case 'z':
               if (ends("ize"))
                   break;
               return;
           default:
               return;
       }
       if (m() > 1)
           k = j;
   }

   /* step6() removes a final -e if m() > 1. */

   private final void step6()
   {  j = k;
      if (b[k] == 'e')
      {  int a = m();
         if (a > 1 || a == 1 && !cvc(k-1)) k--;
      }
      if (b[k] == 'l' && doublec(k) && m() > 1) k--;
   }

   /** Stem the word placed into the Stemmer buffer through calls to add().
    * Returns true if the stemming process resulted in a word different
    * from the input.  You can retrieve the result with
    * getResultLength()/getResultBuffer() or toString().
    */
   public void stem()
   {  k = i - 1;
      if (k > 1) { step1(); step2(); step3(); step4(); step5(); step6(); }
      i_end = k+1; i = 0;
   }

   /** Test program for demonstrating the Stemmer.  It reads text from a
    * a list of files, stems each word, and writes the result to standard
    * output. Note that the word stemmed is expected to be in lower case:
    * forcing lower case must be done outside the Stemmer class.
    * Usage: Stemmer file-name file-name ...
    */

   public  String stemWord(String word) {
       int wordLen=word.length();
       char ch[]=new char[wordLen];
      for(int i=0;i<wordLen;i++){
          ch[i]=word.charAt(i);
          if (!Character.isLetter(ch[i]))
              return word;
      }
      add(ch,wordLen);
      stem();
      word = toString();
      return word;
}
}

class A implements Comparable<A>{
    long a; // priority
    long b; // page No
    A(long a,long b){
        this.a = a;
        this.b = b;
    }
    void get(){
        System.out.println(a+"  "+b);
    }

    long getA(){
        return a;
    }

    long getB(){
        return b;
    }

    public  String toString(){
        return a + "  "+b;
    }

    @Override
    public int compareTo(A emp) {
       if (this.a == emp.getA()) {
           return this.b < emp.getB() ? -1 : 1;
       } else if (this.a < emp.getA()) {
           return -1;
       }
       return 1;
    }
}

class split1{
    ArrayList<wikiPage> list=SAXhandler.returnList();
    //Map<String,Integer []> mp=new HashMap<String,Integer[]>();
    public static Map<String, TreeSet<A> > mainMap = new TreeMap<String, TreeSet<A> > ();
    static int pageNo = 0;
       String []strId;
       String []strTitle;
       String []strCategory;
       String []strExternalLink;
       String []strText;
        
       public void display(){
     	//System.out.println("no of pages  "+list.size());
        stopWord spWord=new stopWord();
        ArrayList<String> stopWordList=spWord.arraystoreStopWord();
     	Iterator<wikiPage> it=list.iterator();
        // stemmer object
        Stemmer stem=new Stemmer();
     	while(it.hasNext()){
            pageNo++;
            Map<String,Integer []> mp=new HashMap<String,Integer[]>();
     		wikiPage nextIterator=it.next();
     		String testId=nextIterator.getId();
     		//System.out.println("id ->>>>>" + '\n'+testId+'\n');
     		String testTitle=nextIterator.getTitle();
     		//System.out.println("title ->>>>>" + '\n'+testTitle+'\n');
     		String testText=nextIterator.getText();
     		//System.out.println("text"+testText);
     		String testCategory=nextIterator.getCategory();
     		//System.out.println("category ->>>>>" + '\n'+testCategory+'\n');
     		String testExternalLink=nextIterator.getExternalLink();
     		//System.out.println("external_link ->>>>>" + '\n'+testExternalLink+'\n');
     		String patternString=" ";
     		Pattern pattern=Pattern.compile(patternString);

     		String[] tokenId=pattern.split(testId);
     		String[] tokenTitle=pattern.split(testTitle);
     		String[] tokenText=pattern.split(testText);
     		String[] tokenCategory=pattern.split(testCategory);
     		String[] tokenExternalLink=pattern.split(testExternalLink);

            Integer[] count1=new Integer[]{0,0,0,0,0,0};
            Integer[] count = new Integer[]{0,0,0,0,0,0};
            //1-id , 2-tittle ,3- text, 4-category, 5 -external_link
            for(int i=0;i<tokenId.length;i++){
                int ind=Collections.binarySearch(stopWordList,tokenId[i]);
            
            	if(ind>0)  tokenId[i]=null;
                else {
                    tokenId[i]=stem.stemWord(tokenId[i]);
                     
                    if(mp.containsKey(tokenId[i])){
                        count=mp.get(tokenId[i]);
                        count[1]++;
                        mp.put(tokenId[i],count);
                        count = new Integer[]{0,0,0,0,0,0};
                    }else{
                        count[1]++;
                        mp.put(tokenId[i],count);
                        count = new Integer[]{0,0,0,0,0,0};
                    }
                 /*   System.out.println(tokenId[i]);
                    for(int k: mp.get(tokenId[i])) System.out.print(k+" ");
                    System.out.println("");
                  */
                }
            }
            for(int i=0;i<tokenTitle.length;i++){
            	int ind=Collections.binarySearch(stopWordList,tokenTitle[i]);
            	if(ind>0)  tokenTitle[i]=null;
                else {
                    tokenTitle[i]=stem.stemWord(tokenTitle[i]);
                    
                   
                    if(mp.containsKey(tokenTitle[i])){
                        
                        count=mp.get(tokenTitle[i]);
                        count[2]++;
                        mp.put(tokenTitle[i],count);
                        count = new Integer[]{0,0,0,0,0,0};
                    }else{
                        count[2]++;
                        mp.put(tokenTitle[i],count);
                        count = new Integer[]{0,0,0,0,0,0};
                    }
                  /*  System.out.println(tokenTitle[i]);
                    for(int k: mp.get(tokenTitle[i])) System.out.print(k+" ");
                    System.out.println("");
                    */
                }
                
            }
            for(int i=0;i<tokenText.length;i++){
            	int ind=Collections.binarySearch(stopWordList,tokenText[i]);
            	if(ind>0)  tokenText[i]=null;
                else {
                    tokenText[i]=stem.stemWord(tokenText[i]);
                   
                     if(mp.containsKey(tokenText[i])){
                        count=mp.get(tokenText[i]);
                        count[3]++;
                        mp.put(tokenText[i],count);
                        count = new Integer[]{0,0,0,0,0,0};
                    }else{
                        count[3]++;
                        mp.put(tokenText[i],count);
                        count = new Integer[]{0,0,0,0,0,0};
                    }
                  /*  System.out.println(tokenText[i]);
                    for(int k: mp.get(tokenText[i])) System.out.print(k+" ");
                    System.out.println("");
                    */
                }
            }
            for(int i=0;i<tokenCategory.length;i++){
            	int ind=Collections.binarySearch(stopWordList,tokenCategory[i]);
            	if(ind>0)  tokenCategory[i]=null;
                else {
                    tokenCategory[i]=stem.stemWord(tokenCategory[i]);
                   
                    if(mp.containsKey(tokenCategory[i])){
                        count=mp.get(tokenCategory[i]);
                        count[4]++;
                        mp.put(tokenCategory[i],count);
                        count = new Integer[]{0,0,0,0,0,0};
                    }else{
                        count[4]++;
                        mp.put(tokenCategory[i],count);
                        count = new Integer[]{0,0,0,0,0,0};
                    }
                  /*  System.out.println(tokenCategory[i]);
                    for(int k: mp.get(tokenCategory[i])) System.out.print(k+" ");
                    System.out.println("");
                    */
                }
            }
            for(int i=0;i<tokenExternalLink.length;i++){
            	int ind=Collections.binarySearch(stopWordList,tokenExternalLink[i]);
            	if(ind>0)  tokenExternalLink[i]=null;
               else {
                    tokenExternalLink[i]=stem.stemWord(tokenExternalLink[i]);
                // set.add(tokenExternalLink[i]);
                if(mp.containsKey(tokenExternalLink[i])){
                        count=mp.get(tokenExternalLink[i]);
                        count[5]++;
                        mp.put(tokenExternalLink[i],count);
                        count = new Integer[]{0,0,0,0,0,0};
                    }else{
                        count[5]++;
                        mp.put(tokenExternalLink[i],count);
                        count = new Integer[]{0,0,0,0,0,0};
                    }
                   
                }
                
            }
            //1-id , 2-tittle ,3- text, 4-category, 5 -external_link
            // id = 100, title = 80 ,text = 40 ,category = 30 , externalLink = 20
            long  sum =0;
            for(Map.Entry<String, Integer []> entry: mp.entrySet()) {
            /* System.out.println(entry.getKey() + " >>>>> " );
             for(int i:entry.getValue()) System.out.print(i+"  ");
            */
            sum=(entry.getValue()[1]*100 + entry.getValue()[2]*80+
                entry.getValue()[3]*30 +entry.getValue()[4]*30 +
                    entry.getValue()[5]*20);
           // System.out.println("key "+entry.getKey());
            A obj = new A(sum,pageNo);
            
            if(mainMap.containsKey(entry.getKey().toLowerCase())){
                TreeSet<A> tset = mainMap.get(entry.getKey().toLowerCase());
                tset.add(obj);
                System.out.println("hello  "+ entry.getKey().toLowerCase() );
                for(A la: tset){
                    System.out.println(la);
                }
                mainMap.put(entry.getKey().toLowerCase(),tset);
            }
            else{ 
                TreeSet<A> tsset = new TreeSet<A>();
                tsset.add(obj);
               // System.out.println(tsset);
                mainMap.put(entry.getKey().toLowerCase(),tsset);
            }
           }
            
         }
     }
    public  static Map< String , TreeSet<A> > getMAinMap(){
        return mainMap;
    }

}




class Main{
    public static Map<String ,TreeSet<A> > mainMap;
	public static void main(String ar[]){
		SAXhandler sc=new SAXhandler();
    
	try{    //System.out.println("hello");
  			SAXParserFactory sfact=SAXParserFactory.newInstance();
  			SAXParser sParse=sfact.newSAXParser();
  			sParse.parse("input.xml",sc);
	}catch(Exception e){
		System.out.println(e);
	}
		//sc.display();
	//System.out.println(sc.count);

	split1 Split1=new split1();
    Split1.display();
    try{
        mainMap = Split1.getMAinMap();
    }catch(Exception e){
        System.out.println(e);
    }
    String input;
    Scanner snr = new Scanner(System.in);
    input = snr.next();
    Stemmer stem=new Stemmer();
    input = stem.stemWord(input);
    TreeSet<A> listpage = mainMap.get(input.toLowerCase());
    if(listpage == null) 
        System.out.println("Page not found");
        else{
                Iterator itr = listpage.descendingIterator();
                while(itr.hasNext()){
                    A ob =  (A)itr.next();
                    ob.get();
                }
                System.out.println(Split1.pageNo);
         }
    }
}