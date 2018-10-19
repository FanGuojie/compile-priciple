//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short SCOPY=257;
public final static short VOID=258;
public final static short BOOL=259;
public final static short INT=260;
public final static short STRING=261;
public final static short CLASS=262;
public final static short NULL=263;
public final static short EXTENDS=264;
public final static short THIS=265;
public final static short WHILE=266;
public final static short FOR=267;
public final static short IF=268;
public final static short ELSE=269;
public final static short RETURN=270;
public final static short BREAK=271;
public final static short NEW=272;
public final static short PRINT=273;
public final static short READ_INTEGER=274;
public final static short READ_LINE=275;
public final static short LITERAL=276;
public final static short IDENTIFIER=277;
public final static short AND=278;
public final static short OR=279;
public final static short STATIC=280;
public final static short INSTANCEOF=281;
public final static short LESS_EQUAL=282;
public final static short GREATER_EQUAL=283;
public final static short EQUAL=284;
public final static short NOT_EQUAL=285;
public final static short IF_DIV=286;
public final static short VAR=287;
public final static short SEALED=288;
public final static short UMINUS=289;
public final static short EMPTY=290;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    2,    2,    6,    6,    7,    7,    7,    9,    9,
   10,   10,    8,    8,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   22,   23,
   23,   23,   24,   21,   14,   14,   14,   28,   28,   26,
   26,   26,   27,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   30,
   30,   29,   29,   31,   31,   16,   17,   20,   15,   32,
   32,   18,   18,   19,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    2,
    3,    6,    7,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    1,
    1,    1,    2,    2,    2,    1,    2,    1,    4,    3,
    1,    0,    3,    6,    3,    1,    0,    2,    0,    2,
    4,    2,    5,    1,    1,    1,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    2,    2,    3,    3,    1,    4,    5,    6,    5,    1,
    1,    1,    0,    3,    1,    5,    9,    1,    6,    2,
    0,    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    2,    0,    0,
    0,   14,   18,    0,    0,   18,    7,    8,    6,    9,
    0,    0,   12,   16,    0,    0,   17,    0,   10,    0,
    4,    0,    0,   13,    0,    0,   11,    0,   22,    0,
    0,    0,    0,    5,    0,    0,    0,   27,   24,   21,
   23,    0,    0,   81,   75,    0,    0,    0,    0,   88,
    0,    0,    0,    0,   80,    0,    0,    0,    0,    0,
   25,   28,   36,   26,    0,   30,   31,   32,    0,    0,
    0,    0,   38,    0,    0,    0,    0,   56,    0,    0,
    0,    0,    0,    0,   54,   55,    0,    0,    0,    0,
    0,    0,   52,    0,    0,    0,    0,   29,   33,   34,
   35,   37,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   48,    0,    0,    0,    0,
    0,    0,    0,    0,   41,    0,    0,    0,    0,    0,
   73,   74,    0,    0,   70,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   39,    0,   76,    0,
    0,   94,    0,    0,   51,    0,    0,    0,   86,    0,
    0,   40,   43,   77,    0,    0,   79,   53,   44,    0,
    0,   89,   78,    0,   90,    0,   87,
};
final static short yydgoto[] = {                          3,
    4,    5,   72,   25,   40,   10,   15,   27,   41,   42,
   73,   52,   74,   75,   76,   77,   78,   79,   80,   81,
   82,   83,  134,  135,   84,   95,   96,   87,  176,   88,
  140,  192,
};
final static short yysindex[] = {                      -254,
 -256, -237,    0, -254,    0, -231, -238,    0, -233,  -78,
 -231,    0,    0,  -72,  334,    0,    0,    0,    0,    0,
 -223,   95,    0,    0,    1,  -87,    0,  535,    0,  -85,
    0,   33,  -10,    0,   44,   95,    0,   95,    0,  -74,
   47,   45,   56,    0,  -28,   95,  -28,    0,    0,    0,
    0,   -2,   60,    0,    0,   65,   66,  -20,   71,    0,
 -126,   67,   69,   70,    0,   72, -163,   71,   71,   46,
    0,    0,    0,    0,   58,    0,    0,    0,   59,   63,
   78,   79,    0,  610,   85,    0, -158,    0, -146,   71,
   71,   71,   71,  610,    0,    0,   99,   49,   71,  107,
  108,   71,    0,  -14,  -14, -124,  444,    0,    0,    0,
    0,    0,   71,   71,   71,   71,   71,   71,   71,   71,
   71,   71,   71,   71,   71,    0,   71,   71,  128,  118,
  466,  119,  496, -107,    0,  327,  136,   54,  610,   31,
    0,    0,  366,  142,    0,  783,  710,   10,   10,  -32,
  -32,   24,   24,  -14,  -14,  -14,   10,   10,  507,  610,
   71,   71,   29,   71,   29,   71,    0,   29,    0,  528,
   71,    0,  -92,   71,    0,  145,  143,  539,    0,  580,
  -81,    0,    0,    0,  610,  150,    0,    0,    0,   71,
   29,    0,    0,  157,    0,   29,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  200,    0,   81,    0,    0,    0,    0,
   81,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  146,    0,    0,    0,  160,    0,  160,    0,    0,
    0,  161,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -58,    0,    0,    0,    0,    0,    0,  -57,    0,
    0,    0,    0,    0,    0,    0,    0,  -70,  -70,  -70,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  648,  433,    0,    0,    0,  -70,
  -58,  -70, -116,  149,    0,    0,    0,    0,  -70,    0,
    0,  -70,    0,  113,  122,    0,    0,    0,    0,    0,
    0,    0,  -70,  -70,  -70,  -70,  -70,  -70,  -70,  -70,
  -70,  -70,  -70,  -70,  -70,    0,  -70,  -70,   83,    0,
    0,    0,    0,    0,    0,    0,    0,  -70,   37,    0,
    0,    0,    0,    0,    0,    5,  -17,  458,  520,  504,
  841,  791,  811,  152,  380,  404,  640,  661,    0,  -22,
  -25,  -70,  -58,  -70,  -58,  -70,    0,  -58,    0,    0,
  -70,    0,    0,  -70,    0,    0,  168,    0,    0,    0,
  -33,    0,    0,    0,   52,    0,    0,    0,    0,   -1,
  -58,    0,    0,    0,    0,  -58,    0,
};
final static short yygindex[] = {                         0,
    0,  209,    8,   12,    7,  205,  201,    0,  180,    0,
   20,    0,  -83,  -88,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   55,  989,  712,  785,    0,    0,    0,
   61,    0,
};
final static int YYTABLESIZE=1163;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         91,
   47,   93,  132,   33,  123,   33,   91,    1,   42,  121,
  119,   91,  120,  126,  122,   83,   33,  167,   45,   92,
    6,   26,   24,   69,    7,   91,   69,  125,   30,  124,
   69,  126,    9,    2,   26,   24,   45,   70,   11,   47,
   69,   69,   68,   12,   13,   68,  123,   39,   68,   39,
   16,  121,  119,   29,  120,  126,  122,   50,  127,   31,
  123,   69,   68,   68,   49,  121,   51,   98,   70,  126,
  122,  172,   36,   68,  171,   69,  127,   85,   69,  179,
   85,  181,   37,   38,  183,   70,   69,   45,   46,   91,
   68,   91,   84,   70,   48,   84,   47,   68,   68,   89,
  127,  194,   93,   69,   90,   91,   99,  195,  100,  101,
   70,  102,  197,  103,  127,   68,  108,  109,  129,   50,
   48,  110,   71,   50,   50,   50,   50,   50,   50,   50,
  130,   17,   18,   19,   20,   21,  111,  112,  137,  138,
   50,   50,   50,   50,   50,  128,   37,  141,  142,   71,
   97,   48,  144,   71,   71,   71,   71,   71,   72,   71,
   49,  162,   72,   72,   72,   72,   72,  161,   72,   42,
   71,   71,   71,   50,   71,   50,  169,  164,  166,   72,
   72,   72,  174,   72,  186,  188,  171,  191,   59,   32,
  193,   35,   59,   59,   59,   59,   59,  196,   59,    1,
   20,   19,   44,   15,    5,   71,   49,   92,   82,   59,
   59,   59,    8,   59,   72,   14,   28,   43,   49,   49,
  182,  177,    0,   91,   91,   91,   91,   91,   91,   91,
    0,   91,   91,   91,   91,    0,   91,   91,   91,   91,
   91,   91,   91,   91,   59,    0,    0,   91,    0,  115,
  116,   49,   91,   91,   53,   17,   18,   19,   20,   21,
   54,   69,   55,   56,   57,   58,    0,   59,   60,   61,
   62,   63,   64,   65,    0,   49,    0,    0,   66,    0,
    0,    0,   68,   68,   67,   53,   17,   18,   19,   20,
   21,   54,    0,   55,   56,   57,   58,    0,   59,   60,
   61,   62,   63,   64,   65,    0,    0,  106,   54,   66,
   55,    0,    0,    0,    0,   67,   54,   61,   55,   63,
   64,   65,    0,    0,    0,   61,   66,   63,   64,   65,
    0,    0,   67,   54,   66,   55,    0,    0,    0,    0,
   67,    0,   61,    0,   63,   64,   65,    0,    0,    0,
    0,   66,   17,   18,   19,   20,   21,   67,    0,    0,
   50,   50,    0,  123,   50,   50,   50,   50,  121,  119,
    0,  120,  126,  122,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  168,    0,  125,    0,  124,    0,
   71,   71,    0,    0,   71,   71,   71,   71,    0,   72,
   72,    0,  123,   72,   72,   72,   72,  121,  119,  173,
  120,  126,  122,    0,    0,    0,   60,  127,    0,    0,
   60,   60,   60,   60,   60,  125,   60,  124,    0,   59,
   59,    0,    0,   59,   59,   59,   59,   60,   60,   60,
   61,   60,    0,    0,   61,   61,   61,   61,   61,    0,
   61,    0,    0,    0,    0,    0,  127,    0,   23,    0,
    0,   61,   61,   61,    0,   61,    0,    0,    0,   55,
    0,    0,   60,   46,   55,   55,    0,   55,   55,   55,
  123,    0,    0,    0,  145,  121,  119,    0,  120,  126,
  122,   46,   55,    0,   55,    0,   61,    0,   66,    0,
    0,   66,  123,  125,    0,  124,  163,  121,  119,    0,
  120,  126,  122,    0,    0,   66,   66,    0,    0,    0,
    0,    0,    0,   55,    0,  125,    0,  124,    0,    0,
    0,    0,  123,    0,  127,    0,  165,  121,  119,    0,
  120,  126,  122,  123,   62,    0,    0,   62,  121,  119,
   66,  120,  126,  122,    0,  125,  127,  124,    0,    0,
   67,   62,   62,   67,  123,    0,  125,    0,  124,  121,
  119,    0,  120,  126,  122,  123,    0,   67,   67,  189,
  121,  119,    0,  120,  126,  122,  127,  125,    0,  124,
    0,   17,   18,   19,   20,   21,   62,  127,  125,  175,
  124,    0,    0,    0,  113,  114,    0,    0,  115,  116,
  117,  118,   67,   22,    0,    0,  123,    0,  127,    0,
  184,  121,  119,    0,  120,  126,  122,    0,    0,  127,
    0,    0,    0,    0,    0,    0,    0,    0,  190,  125,
    0,  124,    0,  113,  114,    0,  123,  115,  116,  117,
  118,  121,  119,    0,  120,  126,  122,   60,   60,   34,
    0,   60,   60,   60,   60,    0,    0,    0,    0,  125,
  127,  124,    0,    0,    0,    0,    0,    0,    0,    0,
   65,   61,   61,   65,   54,   61,   61,   61,   61,   54,
   54,    0,   54,   54,   54,    0,    0,   65,   65,    0,
  127,   64,    0,    0,   64,    0,    0,   54,    0,   54,
   55,   55,    0,    0,   55,   55,   55,   55,   64,   64,
    0,  113,  114,    0,    0,  115,  116,  117,  118,    0,
    0,    0,   65,    0,    0,   66,   66,    0,   54,    0,
    0,   66,   66,  113,  114,    0,  123,  115,  116,  117,
  118,  121,  119,   64,  120,  126,  122,    0,    0,    0,
    0,    0,    0,   85,    0,    0,    0,    0,    0,  125,
    0,  124,    0,  113,  114,    0,    0,  115,  116,  117,
  118,   62,   62,    0,  113,  114,    0,    0,  115,  116,
  117,  118,   17,   18,   19,   20,   21,   67,   67,    0,
  127,    0,   85,   67,   67,  113,  114,    0,    0,  115,
  116,  117,  118,    0,   22,    0,  113,  114,    0,  123,
  115,  116,  117,  118,  121,  119,    0,  120,  126,  122,
    0,   57,    0,   57,   57,   57,   86,    0,    0,    0,
    0,    0,  125,    0,  124,    0,    0,    0,   57,   57,
   57,   58,   57,   58,   58,   58,    0,  113,  114,    0,
    0,  115,  116,  117,  118,    0,    0,    0,   58,   58,
   58,    0,   58,  127,   85,   86,   85,    0,    0,   85,
    0,   63,    0,   57,   63,    0,    0,  113,  114,    0,
    0,  115,  116,  117,  118,    0,    0,    0,   63,   63,
    0,   85,   85,   58,    0,    0,    0,   85,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   65,   65,    0,
    0,    0,    0,   65,   65,   54,   54,    0,    0,   54,
   54,   54,   54,   63,    0,    0,    0,    0,   64,   64,
    0,    0,    0,    0,   64,   64,    0,   86,    0,   86,
    0,    0,   86,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   86,   86,    0,    0,    0,    0,
   86,    0,    0,    0,    0,    0,    0,  113,    0,    0,
    0,  115,  116,  117,  118,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   94,    0,    0,
    0,    0,    0,    0,    0,    0,  104,  105,  107,    0,
    0,    0,    0,    0,  115,  116,  117,  118,   57,   57,
    0,    0,   57,   57,   57,   57,    0,    0,  131,    0,
  133,  136,    0,    0,    0,    0,    0,  139,   58,   58,
  143,    0,   58,   58,   58,   58,    0,    0,    0,    0,
    0,  146,  147,  148,  149,  150,  151,  152,  153,  154,
  155,  156,  157,  158,    0,  159,  160,    0,   63,   63,
    0,    0,    0,    0,    0,    0,  170,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  139,
  178,    0,  180,    0,  136,    0,    0,    0,    0,  185,
    0,    0,  187,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   59,   91,   91,   37,   91,   40,  262,  125,   42,
   43,   45,   45,   46,   47,   41,   91,  125,   41,   40,
  277,   15,   15,   41,  262,   59,   44,   60,   22,   62,
   33,   46,  264,  288,   28,   28,   59,   40,  277,   41,
   58,   59,   45,  277,  123,   41,   37,   36,   44,   38,
  123,   42,   43,  277,   45,   46,   47,   46,   91,   59,
   37,   33,   58,   59,   45,   42,   47,   61,   40,   46,
   47,   41,   40,   45,   44,   93,   91,   41,   33,  163,
   44,  165,   93,   40,  168,   40,   33,   41,   44,  123,
   45,  125,   41,   40,  123,   44,   41,   93,   45,   40,
   91,  190,  123,   33,   40,   40,   40,  191,   40,   40,
   40,   40,  196,  277,   91,   45,   59,   59,  277,   37,
  123,   59,  125,   41,   42,   43,   44,   45,   46,   47,
  277,  258,  259,  260,  261,  262,   59,   59,   40,   91,
   58,   59,   60,   61,   62,   61,   93,   41,   41,   37,
  277,  123,  277,   41,   42,   43,   44,   45,   37,   47,
  277,   44,   41,   42,   43,   44,   45,   40,   47,  286,
   58,   59,   60,   91,   62,   93,   41,   59,  286,   58,
   59,   60,   41,   62,  277,   41,   44,  269,   37,  277,
   41,  277,   41,   42,   43,   44,   45,   41,   47,    0,
   41,   41,  277,  123,   59,   93,  277,   59,   41,   58,
   59,   60,    4,   62,   93,   11,   16,   38,  277,  277,
  166,  161,   -1,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,   -1,  270,  271,  272,  273,
  274,  275,  276,  277,   93,   -1,   -1,  281,   -1,  282,
  283,  277,  286,  287,  257,  258,  259,  260,  261,  262,
  263,  279,  265,  266,  267,  268,   -1,  270,  271,  272,
  273,  274,  275,  276,   -1,  277,   -1,   -1,  281,   -1,
   -1,   -1,  278,  279,  287,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,  268,   -1,  270,  271,
  272,  273,  274,  275,  276,   -1,   -1,  262,  263,  281,
  265,   -1,   -1,   -1,   -1,  287,  263,  272,  265,  274,
  275,  276,   -1,   -1,   -1,  272,  281,  274,  275,  276,
   -1,   -1,  287,  263,  281,  265,   -1,   -1,   -1,   -1,
  287,   -1,  272,   -1,  274,  275,  276,   -1,   -1,   -1,
   -1,  281,  258,  259,  260,  261,  262,  287,   -1,   -1,
  278,  279,   -1,   37,  282,  283,  284,  285,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   58,   -1,   60,   -1,   62,   -1,
  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,  278,
  279,   -1,   37,  282,  283,  284,  285,   42,   43,   44,
   45,   46,   47,   -1,   -1,   -1,   37,   91,   -1,   -1,
   41,   42,   43,   44,   45,   60,   47,   62,   -1,  278,
  279,   -1,   -1,  282,  283,  284,  285,   58,   59,   60,
   37,   62,   -1,   -1,   41,   42,   43,   44,   45,   -1,
   47,   -1,   -1,   -1,   -1,   -1,   91,   -1,  125,   -1,
   -1,   58,   59,   60,   -1,   62,   -1,   -1,   -1,   37,
   -1,   -1,   93,   41,   42,   43,   -1,   45,   46,   47,
   37,   -1,   -1,   -1,   41,   42,   43,   -1,   45,   46,
   47,   59,   60,   -1,   62,   -1,   93,   -1,   41,   -1,
   -1,   44,   37,   60,   -1,   62,   41,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   58,   59,   -1,   -1,   -1,
   -1,   -1,   -1,   91,   -1,   60,   -1,   62,   -1,   -1,
   -1,   -1,   37,   -1,   91,   -1,   41,   42,   43,   -1,
   45,   46,   47,   37,   41,   -1,   -1,   44,   42,   43,
   93,   45,   46,   47,   -1,   60,   91,   62,   -1,   -1,
   41,   58,   59,   44,   37,   -1,   60,   -1,   62,   42,
   43,   -1,   45,   46,   47,   37,   -1,   58,   59,   41,
   42,   43,   -1,   45,   46,   47,   91,   60,   -1,   62,
   -1,  258,  259,  260,  261,  262,   93,   91,   60,   93,
   62,   -1,   -1,   -1,  278,  279,   -1,   -1,  282,  283,
  284,  285,   93,  280,   -1,   -1,   37,   -1,   91,   -1,
   93,   42,   43,   -1,   45,   46,   47,   -1,   -1,   91,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   60,
   -1,   62,   -1,  278,  279,   -1,   37,  282,  283,  284,
  285,   42,   43,   -1,   45,   46,   47,  278,  279,  125,
   -1,  282,  283,  284,  285,   -1,   -1,   -1,   -1,   60,
   91,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   41,  278,  279,   44,   37,  282,  283,  284,  285,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   58,   59,   -1,
   91,   41,   -1,   -1,   44,   -1,   -1,   60,   -1,   62,
  278,  279,   -1,   -1,  282,  283,  284,  285,   58,   59,
   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,
   -1,   -1,   93,   -1,   -1,  278,  279,   -1,   91,   -1,
   -1,  284,  285,  278,  279,   -1,   37,  282,  283,  284,
  285,   42,   43,   93,   45,   46,   47,   -1,   -1,   -1,
   -1,   -1,   -1,   52,   -1,   -1,   -1,   -1,   -1,   60,
   -1,   62,   -1,  278,  279,   -1,   -1,  282,  283,  284,
  285,  278,  279,   -1,  278,  279,   -1,   -1,  282,  283,
  284,  285,  258,  259,  260,  261,  262,  278,  279,   -1,
   91,   -1,   91,  284,  285,  278,  279,   -1,   -1,  282,
  283,  284,  285,   -1,  280,   -1,  278,  279,   -1,   37,
  282,  283,  284,  285,   42,   43,   -1,   45,   46,   47,
   -1,   41,   -1,   43,   44,   45,   52,   -1,   -1,   -1,
   -1,   -1,   60,   -1,   62,   -1,   -1,   -1,   58,   59,
   60,   41,   62,   43,   44,   45,   -1,  278,  279,   -1,
   -1,  282,  283,  284,  285,   -1,   -1,   -1,   58,   59,
   60,   -1,   62,   91,  163,   91,  165,   -1,   -1,  168,
   -1,   41,   -1,   93,   44,   -1,   -1,  278,  279,   -1,
   -1,  282,  283,  284,  285,   -1,   -1,   -1,   58,   59,
   -1,  190,  191,   93,   -1,   -1,   -1,  196,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,   -1,
   -1,   -1,   -1,  284,  285,  278,  279,   -1,   -1,  282,
  283,  284,  285,   93,   -1,   -1,   -1,   -1,  278,  279,
   -1,   -1,   -1,   -1,  284,  285,   -1,  163,   -1,  165,
   -1,   -1,  168,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  190,  191,   -1,   -1,   -1,   -1,
  196,   -1,   -1,   -1,   -1,   -1,   -1,  278,   -1,   -1,
   -1,  282,  283,  284,  285,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   68,   69,   70,   -1,
   -1,   -1,   -1,   -1,  282,  283,  284,  285,  278,  279,
   -1,   -1,  282,  283,  284,  285,   -1,   -1,   90,   -1,
   92,   93,   -1,   -1,   -1,   -1,   -1,   99,  278,  279,
  102,   -1,  282,  283,  284,  285,   -1,   -1,   -1,   -1,
   -1,  113,  114,  115,  116,  117,  118,  119,  120,  121,
  122,  123,  124,  125,   -1,  127,  128,   -1,  278,  279,
   -1,   -1,   -1,   -1,   -1,   -1,  138,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  161,
  162,   -1,  164,   -1,  166,   -1,   -1,   -1,   -1,  171,
   -1,   -1,  174,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=290;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"SCOPY","VOID","BOOL","INT",
"STRING","CLASS","NULL","EXTENDS","THIS","WHILE","FOR","IF","ELSE","RETURN",
"BREAK","NEW","PRINT","READ_INTEGER","READ_LINE","LITERAL","IDENTIFIER","AND",
"OR","STATIC","INSTANCEOF","LESS_EQUAL","GREATER_EQUAL","EQUAL","NOT_EQUAL",
"IF_DIV","VAR","SEALED","UMINUS","EMPTY",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : VOID",
"Type : BOOL",
"Type : STRING",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ClassDef : SEALED CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : StmtBlock",
"Stmt : OCStmt ';'",
"Stmt : GuardedStmt",
"GuardedStmt : IF '{' IfBranchList '}'",
"IfBranchList : IfBranchList IF_DIV IfBranch",
"IfBranchList : IfBranch",
"IfBranchList :",
"IfBranch : Expr ':' Stmt",
"OCStmt : SCOPY '(' IDENTIFIER ',' Expr ')'",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"LValue : VAR IDENTIFIER",
"Call : Receiver IDENTIFIER '(' Actuals ')'",
"Expr : LValue",
"Expr : Call",
"Expr : Constant",
"Expr : Expr '+' Expr",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr '<' Expr",
"Expr : Expr '>' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '(' Expr ')'",
"Expr : '-' Expr",
"Expr : '!' Expr",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Constant : LITERAL",
"Constant : NULL",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' SimpleStmt ';' Expr ';' SimpleStmt ')' Stmt",
"BreakStmt : BREAK",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
};

//#line 474 "Parser.y"
    
	/**
	 * 打印当前归约所用的语法规则<br>
	 * 请勿修改。
	 */
    public boolean onReduce(String rule) {
		if (rule.startsWith("$$"))
			return false;
		else
			rule = rule.replaceAll(" \\$\\$\\d+", "");

   	    if (rule.endsWith(":"))
    	    System.out.println(rule + " <empty>");
   	    else
			System.out.println(rule);
		return false;
    }
    
    public void diagnose() {
		addReduceListener(this);
		yyparse();
	}
//#line 634 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 57 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 63 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 67 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 77 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 83 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 87 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 91 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 95 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 99 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 11:
//#line 103 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 12:
//#line 109 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc,false);
					}
break;
case 13:
//#line 113 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc,true);
					}
break;
case 14:
//#line 119 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 15:
//#line 123 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 16:
//#line 129 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 17:
//#line 133 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 18:
//#line 137 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 20:
//#line 145 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 21:
//#line 152 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 22:
//#line 156 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 23:
//#line 163 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 167 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 173 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 26:
//#line 179 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 27:
//#line 183 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 28:
//#line 190 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 29:
//#line 195 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 39:
//#line 212 "Parser.y"
{
					yyval.stmt = new Tree.GuardedStmt(val_peek(1).elist, val_peek(1).loc);
				}
break;
case 40:
//#line 217 "Parser.y"
{
					yyval.elist.add(val_peek(0).expr);
				}
break;
case 41:
//#line 222 "Parser.y"
{
                    yyval = new SemValue();
                    yyval.elist = new ArrayList<Expr> ();
                    yyval.elist.add(val_peek(0).expr);
                }
break;
case 42:
//#line 228 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 43:
//#line 234 "Parser.y"
{
					yyval.expr = new Tree.IfBranch(val_peek(2).expr, val_peek(0).stmt, val_peek(2).loc);				
				}
break;
case 44:
//#line 239 "Parser.y"
{
					yyval.stmt = new Scopy(val_peek(3).ident,val_peek(1).expr,val_peek(5).loc);	
				}
break;
case 45:
//#line 245 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 46:
//#line 249 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 47:
//#line 253 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 49:
//#line 260 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 50:
//#line 266 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 51:
//#line 273 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 52:
//#line 277 "Parser.y"
{
                	yyval.lvalue = new Tree.Var(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
                }
break;
case 53:
//#line 286 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 54:
//#line 295 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 57:
//#line 301 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 305 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 309 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 313 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 317 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 321 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 325 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 329 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 333 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 337 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 341 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 68:
//#line 345 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 69:
//#line 349 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 70:
//#line 353 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 71:
//#line 357 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 72:
//#line 361 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 73:
//#line 365 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 74:
//#line 369 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 75:
//#line 373 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 76:
//#line 377 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 77:
//#line 381 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 78:
//#line 385 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 79:
//#line 389 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 80:
//#line 395 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 81:
//#line 399 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 83:
//#line 406 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 84:
//#line 413 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 85:
//#line 417 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 86:
//#line 424 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 87:
//#line 430 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 88:
//#line 436 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 89:
//#line 442 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 90:
//#line 448 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 91:
//#line 452 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 92:
//#line 458 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 93:
//#line 462 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 94:
//#line 468 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1274 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
