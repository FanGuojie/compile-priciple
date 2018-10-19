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
public final static short SEALED=287;
public final static short UMINUS=288;
public final static short EMPTY=289;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    2,    2,    6,    6,    7,    7,    7,    9,    9,
   10,   10,    8,    8,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   22,   23,
   23,   23,   24,   21,   14,   14,   14,   28,   28,   26,
   26,   27,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   30,   30,
   29,   29,   31,   31,   16,   17,   20,   15,   32,   32,
   18,   18,   19,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    2,
    3,    6,    7,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    1,
    1,    1,    2,    2,    2,    1,    2,    1,    4,    3,
    1,    0,    3,    6,    3,    1,    0,    2,    0,    2,
    4,    5,    1,    1,    1,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    2,
    2,    3,    3,    1,    4,    5,    6,    5,    1,    1,
    1,    0,    3,    1,    5,    9,    1,    6,    2,    0,
    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    2,    0,    0,
    0,   14,   18,    0,    0,   18,    7,    8,    6,    9,
    0,    0,   12,   16,    0,    0,   17,    0,   10,    0,
    4,    0,    0,   13,    0,    0,   11,    0,   22,    0,
    0,    0,    0,    5,    0,    0,    0,   27,   24,   21,
   23,    0,    0,   80,   74,    0,    0,    0,    0,   87,
    0,    0,    0,    0,   79,    0,    0,    0,    0,   25,
   28,   36,   26,    0,   30,   31,   32,    0,    0,    0,
    0,   38,    0,    0,    0,    0,   55,    0,    0,    0,
    0,    0,    0,   53,   54,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   29,   33,   34,   35,   37,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   48,    0,    0,    0,    0,    0,    0,
    0,    0,   41,    0,    0,    0,    0,    0,   72,   73,
    0,    0,   69,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   39,    0,   75,    0,    0,   93,
    0,    0,   51,    0,    0,    0,   85,    0,    0,   40,
   43,   76,    0,    0,   78,   52,   44,    0,    0,   88,
   77,    0,   89,    0,   86,
};
final static short yydgoto[] = {                          3,
    4,    5,   71,   25,   40,   10,   15,   27,   41,   42,
   72,   52,   73,   74,   75,   76,   77,   78,   79,   80,
   81,   82,  132,  133,   83,   94,   95,   86,  174,   87,
  138,  190,
};
final static short yysindex[] = {                      -240,
 -264, -239,    0, -240,    0, -231, -237,    0, -233,  -62,
 -231,    0,    0,  -59,   80,    0,    0,    0,    0,    0,
 -207, -121,    0,    0,   12,  -88,    0,  257,    0,  -87,
    0,   61,   21,    0,   73, -121,    0, -121,    0,  -86,
   64,   85,   89,    0,   19, -121,   19,    0,    0,    0,
    0,   -3,   91,    0,    0,   95,  104,  -25,  612,    0,
 -202,  112,  114,  115,    0,  116,  612,  612,  587,    0,
    0,    0,    0,   87,    0,    0,    0,   98,  100,  102,
  103,    0,  563,  109,    0, -106,    0, -105,  612,  612,
  612,  612,  563,    0,    0,  133,   83,  612,  135,  143,
  612,  -28,  -28,  -90,  364,    0,    0,    0,    0,    0,
  612,  612,  612,  612,  612,  612,  612,  612,  612,  612,
  612,  612,  612,    0,  612,  612,  148,  149,  388,  142,
  399, -117,    0,  420,  151,  595,  563,    8,    0,    0,
  452,  153,    0,  715,  678,  -18,  -18,  742,  742,  -26,
  -26,  -28,  -28,  -28,  -18,  -18,  463,  563,  612,  612,
   22,  612,   22,  612,    0,   22,    0,  489,  612,    0,
  -75,  612,    0,  162,  160,  516,    0,  542,  -63,    0,
    0,    0,  563,  166,    0,    0,    0,  612,   22,    0,
    0,  167,    0,   22,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  209,    0,   88,    0,    0,    0,    0,
   88,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  154,    0,    0,    0,  169,    0,  169,    0,    0,
    0,  173,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -58,    0,    0,    0,    0,    0,    0,  -57,    0,
    0,    0,    0,    0,    0,    0,  -61,  -61,  -61,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  648,  263,    0,    0,    0,  -61,  -58,
  -61,  -91,  156,    0,    0,    0,    0,  -61,    0,    0,
  -61,   65,   74,    0,    0,    0,    0,    0,    0,    0,
  -61,  -61,  -61,  -61,  -61,  -61,  -61,  -61,  -61,  -61,
  -61,  -61,  -61,    0,  -61,  -61,   35,    0,    0,    0,
    0,    0,    0,    0,    0,  -61,   42,    0,    0,    0,
    0,    0,    0,  -27,   -5,  119,  287,  533,  764,  772,
  851,  106,  138,  334,  428,  621,    0,    9,  -32,  -61,
  -58,  -61,  -58,  -61,    0,  -58,    0,    0,  -61,    0,
    0,  -61,    0,    0,  176,    0,    0,    0,  -33,    0,
    0,    0,   59,    0,    0,    0,    0,  -31,  -58,    0,
    0,    0,    0,  -58,    0,
};
final static short yygindex[] = {                         0,
    0,  214,   20,   53,   23,  210,  206,    0,  185,    0,
   40,    0, -120,  -84,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   72,  947,  776,  784,    0,    0,    0,
   90,    0,
};
final static int YYTABLESIZE=1136;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         90,
   47,   92,   33,   33,   33,  130,   90,  165,   82,   47,
  121,   90,    6,   67,   91,  119,   67,  124,  121,  124,
  120,    1,    7,  119,  117,   90,  118,  124,  120,   68,
   67,   67,    9,   42,   24,   68,   69,   26,   68,   11,
  177,   67,  179,   12,   30,  181,    2,   24,  170,   45,
   26,  169,   68,   68,   68,   17,   18,   19,   20,   21,
   13,   69,  125,   16,  125,   67,   67,   45,  193,   29,
   31,   50,  125,  195,   96,   50,   50,   50,   50,   50,
   50,   50,   84,   97,   49,   84,   51,   68,   39,   90,
   39,   90,   50,   50,   50,   50,   50,   92,   50,   83,
   36,   70,   83,  192,   45,   70,   70,   70,   70,   70,
   71,   70,   38,   37,   71,   71,   71,   71,   71,   48,
   71,   70,   70,   70,   70,   50,   70,   50,   46,   47,
   88,   71,   71,   71,   89,   71,   17,   18,   19,   20,
   21,   48,   58,   90,   48,  106,   58,   58,   58,   58,
   58,   98,   58,   99,  100,  101,  107,   70,  108,   65,
  109,  110,   65,   58,   58,   58,   71,   58,  164,  126,
  127,  128,  135,  136,   59,  139,   65,   65,   59,   59,
   59,   59,   59,  140,   59,   49,  142,  159,   32,   35,
   44,  167,  160,  172,   42,   59,   59,   59,   58,   59,
  162,  184,  186,  169,   23,  189,  191,  194,    1,   20,
   15,   65,    5,   19,   91,   49,   81,    8,   49,   49,
   14,   28,   43,   90,   90,   90,   90,   90,   90,   90,
   59,   90,   90,   90,   90,  180,   90,   90,   90,   90,
   90,   90,   90,   90,   49,   49,    0,   90,  175,    0,
   67,   67,   90,   53,   17,   18,   19,   20,   21,   54,
    0,   55,   56,   57,   58,    0,   59,   60,   61,   62,
   63,   64,   65,   68,    0,    0,    0,   66,   53,   17,
   18,   19,   20,   21,   54,    0,   55,   56,   57,   58,
    0,   59,   60,   61,   62,   63,   64,   65,    0,   54,
    0,    0,   66,   46,   54,   54,    0,   54,   54,   54,
    0,    0,   50,   50,    0,    0,   50,   50,   50,   50,
    0,   46,   54,    0,   54,    0,    0,   66,    0,    0,
   66,    0,    0,    0,    0,    0,    0,   17,   18,   19,
   20,   21,   70,   70,   66,   66,   70,   70,   70,   70,
    0,   71,   71,   54,    0,   71,   71,   71,   71,   22,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   60,    0,    0,    0,   60,   60,   60,   60,   60,   66,
   60,   34,    0,   58,   58,    0,    0,   58,   58,   58,
   58,   60,   60,   60,    0,   60,   65,   65,    0,    0,
  121,    0,   65,   65,  143,  119,  117,    0,  118,  124,
  120,    0,    0,    0,    0,   59,   59,    0,    0,   59,
   59,   59,   59,  123,  121,  122,   60,    0,  161,  119,
  117,    0,  118,  124,  120,  121,    0,    0,    0,  163,
  119,  117,    0,  118,  124,  120,    0,  123,    0,  122,
    0,    0,    0,    0,  125,    0,  121,    0,  123,    0,
  122,  119,  117,    0,  118,  124,  120,    0,   64,    0,
    0,   64,    0,    0,    0,    0,    0,  166,  125,  123,
    0,  122,    0,    0,    0,   64,   64,    0,  121,  125,
    0,    0,    0,  119,  117,  171,  118,  124,  120,  121,
    0,    0,    0,    0,  119,  117,    0,  118,  124,  120,
  125,  123,    0,  122,   17,   18,   19,   20,   21,    0,
   64,    0,  123,    0,  122,  121,    0,    0,    0,    0,
  119,  117,    0,  118,  124,  120,   22,    0,    0,    0,
   54,   54,  125,    0,   54,   54,   54,   54,  123,    0,
  122,    0,  121,  125,    0,  173,  187,  119,  117,    0,
  118,  124,  120,    0,   66,   66,    0,    0,    0,    0,
   66,   66,    0,   61,    0,  123,   61,  122,  121,  125,
    0,  182,    0,  119,  117,    0,  118,  124,  120,    0,
   61,   61,    0,    0,    0,    0,    0,    0,    0,  121,
  188,  123,    0,  122,  119,  117,  125,  118,  124,  120,
    0,   60,   60,    0,    0,   60,   60,   60,   60,   68,
    0,    0,  123,    0,  122,   61,   69,   68,    0,    0,
    0,   67,  125,    0,   69,    0,    0,    0,    0,   67,
    0,  111,  112,    0,   68,  113,  114,  115,  116,    0,
    0,   69,    0,  125,    0,    0,   67,    0,    0,    0,
    0,   63,    0,    0,   63,  111,  112,    0,    0,  113,
  114,  115,  116,    0,    0,    0,  111,  112,   63,   63,
  113,  114,  115,  116,   53,    0,    0,   37,    0,   53,
   53,    0,   53,   53,   53,    0,    0,  111,  112,    0,
    0,  113,  114,  115,  116,   64,   64,   53,    0,   53,
    0,   64,   64,   63,  121,    0,    0,    0,    0,  119,
  117,    0,  118,  124,  120,    0,    0,    0,    0,  111,
  112,    0,    0,  113,  114,  115,  116,  123,   53,  122,
  111,  112,    0,    0,  113,  114,  115,  116,    0,    0,
    0,  121,    0,    0,    0,    0,  119,  117,    0,  118,
  124,  120,    0,    0,    0,    0,  111,  112,  125,    0,
  113,  114,  115,  116,  123,    0,  122,    0,  121,    0,
    0,    0,    0,  119,  117,    0,  118,  124,  120,    0,
    0,    0,    0,  111,  112,    0,    0,  113,  114,  115,
  116,  123,    0,  122,   62,  125,    0,   62,    0,    0,
   61,   61,   56,    0,   56,   56,   56,    0,    0,  111,
  112,   62,   62,  113,  114,  115,  116,   84,    0,   56,
   56,   56,  125,   56,    0,   85,    0,    0,    0,    0,
  111,  112,    0,    0,  113,  114,  115,  116,  104,   54,
    0,   55,    0,    0,    0,    0,   62,   54,   61,   55,
   63,   64,   65,    0,   56,   84,   61,   66,   63,   64,
   65,    0,    0,   85,   54,   66,   55,    0,    0,    0,
    0,    0,    0,   61,    0,   63,   64,   65,    0,    0,
    0,   57,   66,   57,   57,   57,    0,    0,   63,   63,
    0,    0,    0,    0,   63,   63,    0,    0,   57,   57,
   57,    0,   57,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   53,   53,    0,    0,   53,
   53,   53,   53,    0,    0,    0,   84,    0,   84,    0,
    0,   84,    0,   57,   85,    0,   85,    0,    0,   85,
    0,    0,    0,    0,    0,  111,    0,    0,    0,  113,
  114,  115,  116,   84,   84,    0,    0,    0,    0,   84,
    0,   85,   85,    0,    0,    0,    0,   85,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  113,  114,  115,  116,
    0,    0,    0,    0,    0,   93,    0,    0,    0,    0,
    0,    0,    0,  102,  103,  105,    0,    0,    0,    0,
    0,    0,    0,  113,  114,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  129,    0,  131,  134,    0,
    0,   62,   62,    0,  137,    0,    0,  141,    0,   56,
   56,    0,    0,   56,   56,   56,   56,  144,  145,  146,
  147,  148,  149,  150,  151,  152,  153,  154,  155,  156,
    0,  157,  158,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  168,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  137,  176,    0,  178,    0,
  134,    0,    0,    0,    0,  183,    0,    0,  185,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   57,   57,
    0,    0,   57,   57,   57,   57,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   59,   91,   91,   91,   90,   40,  125,   41,   41,
   37,   45,  277,   41,   40,   42,   44,   46,   37,   46,
   47,  262,  262,   42,   43,   59,   45,   46,   47,   33,
   58,   59,  264,  125,   15,   41,   40,   15,   44,  277,
  161,   45,  163,  277,   22,  166,  287,   28,   41,   41,
   28,   44,   58,   59,   33,  258,  259,  260,  261,  262,
  123,   40,   91,  123,   91,   93,   45,   59,  189,  277,
   59,   37,   91,  194,  277,   41,   42,   43,   44,   45,
   46,   47,   41,   61,   45,   44,   47,   93,   36,  123,
   38,  125,   58,   59,   60,   61,   62,  123,   46,   41,
   40,   37,   44,  188,   41,   41,   42,   43,   44,   45,
   37,   47,   40,   93,   41,   42,   43,   44,   45,  123,
   47,  125,   58,   59,   60,   91,   62,   93,   44,   41,
   40,   58,   59,   60,   40,   62,  258,  259,  260,  261,
  262,  123,   37,   40,  123,   59,   41,   42,   43,   44,
   45,   40,   47,   40,   40,   40,   59,   93,   59,   41,
   59,   59,   44,   58,   59,   60,   93,   62,  286,   61,
  277,  277,   40,   91,   37,   41,   58,   59,   41,   42,
   43,   44,   45,   41,   47,  277,  277,   40,  277,  277,
  277,   41,   44,   41,  286,   58,   59,   60,   93,   62,
   59,  277,   41,   44,  125,  269,   41,   41,    0,   41,
  123,   93,   59,   41,   59,  277,   41,    4,  277,  277,
   11,   16,   38,  257,  258,  259,  260,  261,  262,  263,
   93,  265,  266,  267,  268,  164,  270,  271,  272,  273,
  274,  275,  276,  277,  277,  277,   -1,  281,  159,   -1,
  278,  279,  286,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,   -1,  270,  271,  272,  273,
  274,  275,  276,  279,   -1,   -1,   -1,  281,  257,  258,
  259,  260,  261,  262,  263,   -1,  265,  266,  267,  268,
   -1,  270,  271,  272,  273,  274,  275,  276,   -1,   37,
   -1,   -1,  281,   41,   42,   43,   -1,   45,   46,   47,
   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,
   -1,   59,   60,   -1,   62,   -1,   -1,   41,   -1,   -1,
   44,   -1,   -1,   -1,   -1,   -1,   -1,  258,  259,  260,
  261,  262,  278,  279,   58,   59,  282,  283,  284,  285,
   -1,  278,  279,   91,   -1,  282,  283,  284,  285,  280,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   37,   -1,   -1,   -1,   41,   42,   43,   44,   45,   93,
   47,  125,   -1,  278,  279,   -1,   -1,  282,  283,  284,
  285,   58,   59,   60,   -1,   62,  278,  279,   -1,   -1,
   37,   -1,  284,  285,   41,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,  282,
  283,  284,  285,   60,   37,   62,   93,   -1,   41,   42,
   43,   -1,   45,   46,   47,   37,   -1,   -1,   -1,   41,
   42,   43,   -1,   45,   46,   47,   -1,   60,   -1,   62,
   -1,   -1,   -1,   -1,   91,   -1,   37,   -1,   60,   -1,
   62,   42,   43,   -1,   45,   46,   47,   -1,   41,   -1,
   -1,   44,   -1,   -1,   -1,   -1,   -1,   58,   91,   60,
   -1,   62,   -1,   -1,   -1,   58,   59,   -1,   37,   91,
   -1,   -1,   -1,   42,   43,   44,   45,   46,   47,   37,
   -1,   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,
   91,   60,   -1,   62,  258,  259,  260,  261,  262,   -1,
   93,   -1,   60,   -1,   62,   37,   -1,   -1,   -1,   -1,
   42,   43,   -1,   45,   46,   47,  280,   -1,   -1,   -1,
  278,  279,   91,   -1,  282,  283,  284,  285,   60,   -1,
   62,   -1,   37,   91,   -1,   93,   41,   42,   43,   -1,
   45,   46,   47,   -1,  278,  279,   -1,   -1,   -1,   -1,
  284,  285,   -1,   41,   -1,   60,   44,   62,   37,   91,
   -1,   93,   -1,   42,   43,   -1,   45,   46,   47,   -1,
   58,   59,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   37,
   59,   60,   -1,   62,   42,   43,   91,   45,   46,   47,
   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,   33,
   -1,   -1,   60,   -1,   62,   93,   40,   33,   -1,   -1,
   -1,   45,   91,   -1,   40,   -1,   -1,   -1,   -1,   45,
   -1,  278,  279,   -1,   33,  282,  283,  284,  285,   -1,
   -1,   40,   -1,   91,   -1,   -1,   45,   -1,   -1,   -1,
   -1,   41,   -1,   -1,   44,  278,  279,   -1,   -1,  282,
  283,  284,  285,   -1,   -1,   -1,  278,  279,   58,   59,
  282,  283,  284,  285,   37,   -1,   -1,   93,   -1,   42,
   43,   -1,   45,   46,   47,   -1,   -1,  278,  279,   -1,
   -1,  282,  283,  284,  285,  278,  279,   60,   -1,   62,
   -1,  284,  285,   93,   37,   -1,   -1,   -1,   -1,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,  278,
  279,   -1,   -1,  282,  283,  284,  285,   60,   91,   62,
  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,   -1,
   -1,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   -1,   -1,   -1,   -1,  278,  279,   91,   -1,
  282,  283,  284,  285,   60,   -1,   62,   -1,   37,   -1,
   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,
  285,   60,   -1,   62,   41,   91,   -1,   44,   -1,   -1,
  278,  279,   41,   -1,   43,   44,   45,   -1,   -1,  278,
  279,   58,   59,  282,  283,  284,  285,   52,   -1,   58,
   59,   60,   91,   62,   -1,   52,   -1,   -1,   -1,   -1,
  278,  279,   -1,   -1,  282,  283,  284,  285,  262,  263,
   -1,  265,   -1,   -1,   -1,   -1,   93,  263,  272,  265,
  274,  275,  276,   -1,   93,   90,  272,  281,  274,  275,
  276,   -1,   -1,   90,  263,  281,  265,   -1,   -1,   -1,
   -1,   -1,   -1,  272,   -1,  274,  275,  276,   -1,   -1,
   -1,   41,  281,   43,   44,   45,   -1,   -1,  278,  279,
   -1,   -1,   -1,   -1,  284,  285,   -1,   -1,   58,   59,
   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,  282,
  283,  284,  285,   -1,   -1,   -1,  161,   -1,  163,   -1,
   -1,  166,   -1,   93,  161,   -1,  163,   -1,   -1,  166,
   -1,   -1,   -1,   -1,   -1,  278,   -1,   -1,   -1,  282,
  283,  284,  285,  188,  189,   -1,   -1,   -1,   -1,  194,
   -1,  188,  189,   -1,   -1,   -1,   -1,  194,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  282,  283,  284,  285,
   -1,   -1,   -1,   -1,   -1,   59,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   67,   68,   69,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  282,  283,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   89,   -1,   91,   92,   -1,
   -1,  278,  279,   -1,   98,   -1,   -1,  101,   -1,  278,
  279,   -1,   -1,  282,  283,  284,  285,  111,  112,  113,
  114,  115,  116,  117,  118,  119,  120,  121,  122,  123,
   -1,  125,  126,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  136,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  159,  160,   -1,  162,   -1,
  164,   -1,   -1,   -1,   -1,  169,   -1,   -1,  172,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,
   -1,   -1,  282,  283,  284,  285,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=289;
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
"IF_DIV","SEALED","UMINUS","EMPTY",
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

//#line 467 "Parser.y"
    
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
//#line 626 "Parser.java"
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
//#line 279 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 53:
//#line 288 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 56:
//#line 294 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 298 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 302 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 306 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 310 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 314 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 318 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 322 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 326 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 330 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 334 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 338 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 68:
//#line 342 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 69:
//#line 346 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 70:
//#line 350 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 71:
//#line 354 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 72:
//#line 358 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 73:
//#line 362 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 74:
//#line 366 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 75:
//#line 370 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 76:
//#line 374 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 77:
//#line 378 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 78:
//#line 382 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 79:
//#line 388 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 80:
//#line 392 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 82:
//#line 399 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 83:
//#line 406 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 84:
//#line 410 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 85:
//#line 417 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 86:
//#line 423 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 87:
//#line 429 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 88:
//#line 435 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 89:
//#line 441 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 90:
//#line 445 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 91:
//#line 451 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 92:
//#line 455 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 93:
//#line 461 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1257 "Parser.java"
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
