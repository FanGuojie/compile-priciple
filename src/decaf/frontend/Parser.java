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
public final static short ARRAY_REPEAT=288;
public final static short ARRAY_ADD=289;
public final static short SEALED=290;
public final static short UMINUS=291;
public final static short EMPTY=292;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    2,    2,    6,    6,    7,    7,    7,    9,    9,
   10,   10,    8,    8,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   22,   23,
   23,   23,   24,   21,   14,   14,   14,   28,   28,   26,
   26,   26,   27,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   25,   30,   30,   30,   31,   31,   31,   29,   29,   32,
   32,   16,   17,   20,   15,   33,   33,   18,   18,   19,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    2,
    3,    6,    7,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    1,
    1,    1,    2,    2,    2,    1,    2,    1,    4,    3,
    1,    0,    3,    6,    3,    1,    0,    2,    0,    2,
    4,    2,    5,    1,    1,    1,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    2,    2,    3,    3,    1,    4,    5,    6,    5,    3,
    3,    1,    1,    3,    1,    3,    0,    1,    0,    3,
    1,    5,    9,    1,    6,    2,    0,    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    2,    0,    0,
    0,   14,   18,    0,    0,   18,    7,    8,    6,    9,
    0,    0,   12,   16,    0,    0,   17,    0,   10,    0,
    4,    0,    0,   13,    0,    0,   11,    0,   22,    0,
    0,    0,    0,    5,    0,    0,    0,   27,   24,   21,
   23,    0,    0,   83,   75,    0,    0,    0,    0,   94,
    0,    0,    0,    0,   82,    0,    0,    0,    0,    0,
    0,   25,   28,   36,   26,    0,   30,   31,   32,    0,
    0,    0,    0,   38,    0,    0,    0,    0,   56,    0,
    0,    0,    0,    0,    0,   54,   55,    0,    0,    0,
    0,    0,    0,   52,    0,    0,    0,    0,   85,    0,
   29,   33,   34,   35,   37,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   48,    0,    0,    0,    0,    0,    0,    0,    0,   41,
    0,    0,    0,    0,    0,   73,   74,    0,    0,   70,
    0,   84,    0,    0,    0,    0,    0,    0,   80,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   39,    0,   76,    0,    0,
  100,    0,    0,   86,   51,    0,    0,    0,   92,    0,
    0,   40,   43,   77,    0,    0,    0,   53,   44,    0,
    0,   95,   78,    0,   96,    0,   93,
};
final static short yydgoto[] = {                          3,
    4,    5,   73,   25,   40,   10,   15,   27,   41,   42,
   74,   52,   75,   76,   77,   78,   79,   80,   81,   82,
   83,   84,  139,  140,   85,   96,   97,   88,  186,   89,
  110,  145,  202,
};
final static short yysindex[] = {                      -244,
 -257, -226,    0, -244,    0, -220, -227,    0, -216,  -60,
 -220,    0,    0,  -53,  -49,    0,    0,    0,    0,    0,
 -204,   22,    0,    0,  -14,  -88,    0,  295,    0,  -86,
    0,   37,  -11,    0,   40,   22,    0,   22,    0,  -78,
   43,   49,   55,    0,  -28,   22,  -28,    0,    0,    0,
    0,   -2,   60,    0,    0,   62,   63,  -25,   71,    0,
 -131,   65,   66,   67,    0,   68, -167,   71,   71,   46,
  -70,    0,    0,    0,    0,   76,    0,    0,    0,   77,
   89,   90,   91,    0,  523,   52,    0, -163,    0, -158,
   71,   71,   71,   71,  523,    0,    0,   82,   34,   71,
   85,  110,   71,    0,  -38,  -38, -124,  155,    0,  -34,
    0,    0,    0,    0,    0,   71,   71,   71,   71,   71,
   71,  -70,   71,   71,   71,   71,   71,   71,   71,   71,
    0,   71,   71,  114,  117,  327,  104,  385, -111,    0,
  409,  131,   54,  523,  -16,    0,    0,  435,  132,    0,
  -70,    0,  585,  557,  -13,  -13,  615,  615,    0,  523,
   18,   18,  -38,  -38,  -38,  -13,  -13,  447,  523,   71,
   71,   29,   71,   29,   71,    0,   29,    0,  459,   71,
    0, -103,   71,    0,    0,  135,  133,  471,    0,  499,
  -91,    0,    0,    0,  523,  138, -232,    0,    0,   71,
   29,    0,    0,  140,    0,   29,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  184,    0,   64,    0,    0,    0,    0,
   64,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  127,    0,    0,    0,  153,    0,  153,    0,    0,
    0,  154,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -58,    0,    0,    0,    0,    0,    0,  -57,    0,
    0,    0,    0,    0,    0,    0,    0,  -74,  -74,  -74,
  -22,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  535,  123,    0,    0,    0,
  -74,  -58,  -74, -106,  145,    0,    0,    0,    0,  -74,
    0,    0,  -74,    0,  789,  847,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -74,  -74,  -74,  -74,  -74,
  -74,    0,  -74,  -74,  -74,  -74,  -74,  -74,  -74,  -74,
    0,  -74,  -74,   97,    0,    0,    0,    0,    0,    0,
    0,    0,  -74,   10,    0,    0,    0,    0,    0,    0,
    0,    0,  429,   74,  717,  745,  842,  941,    0,    8,
  355,  919,  874,  883,  910,  855,  939,    0,  -18,  -32,
  -74,  -58,  -74,  -58,  -74,    0,  -58,    0,    0,  -74,
    0,    0,  -74,    0,    0,    0,  164,    0,    0,    0,
  -33,    0,    0,    0,   31,    0,  817,    0,    0,  -30,
  -58,    0,    0,    0,    0,  -58,    0,
};
final static short yygindex[] = {                         0,
    0,  203,   12,    1,   20,  197,  200,    0,  180,    0,
  -41,    0,  -89,  -76,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   47, 1137,  285,  590,    0,    0,  -54,
    0,   51,    0,
};
final static int YYTABLESIZE=1320;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         97,
   47,   99,   33,   49,   33,   51,   97,  131,   89,  151,
   47,   97,   33,  176,   93,  137,  109,    1,   42,    6,
   71,   87,   45,  128,  181,   97,   24,  180,  126,  124,
   69,  125,  131,  127,   26,    7,   39,   70,   39,   24,
   45,   30,   68,    9,   31,    2,   50,   26,   81,   11,
   91,   81,  132,   91,  128,  122,  123,   97,  152,  126,
   12,   69,   13,  131,  127,   81,   81,  159,   70,   16,
   87,   90,   29,   68,   90,   23,   36,  132,   69,   38,
   99,   37,  189,   45,  191,   70,   69,  193,   71,   97,
   68,   97,   46,   70,   48,   47,  184,   94,   68,   90,
   81,   91,   92,   69,  100,  101,  102,  103,  132,  104,
   70,  205,  133,  134,   69,   68,  207,   69,  135,   71,
   48,  142,   72,  204,  143,  146,   17,   18,   19,   20,
   21,   69,   69,   50,  111,  112,   71,   50,   50,   50,
   50,   50,   50,   50,   71,   98,   37,  113,  114,  115,
  147,   48,  149,  170,   50,   50,   50,   50,   50,   55,
  171,   71,  173,   46,   55,   55,   69,   55,   55,   55,
   49,  178,  183,  196,  175,  198,  180,  201,  203,   42,
  206,   46,   55,    1,   55,    5,   15,   50,   32,   50,
   35,  128,   54,   20,   19,  150,  126,  124,   44,  125,
  131,  127,   49,   98,   88,   65,    8,   14,   17,   18,
   19,   20,   21,   55,  130,   28,  129,   43,   49,   49,
  187,  192,    0,   97,   97,   97,   97,   97,   97,   97,
   22,   97,   97,   97,   97,    0,   97,   97,   97,   97,
   97,   97,   97,   97,   49,  132,   49,   97,    0,  122,
  123,    0,   97,   97,   53,   17,   18,   19,   20,   21,
   54,    0,   55,   56,   57,   58,    0,   59,   60,   61,
   62,   63,   64,   65,  122,  123,    0,    0,   66,   17,
   18,   19,   20,   21,   67,   53,   17,   18,   19,   20,
   21,   54,    0,   55,   56,   57,   58,    0,   59,   60,
   61,   62,   63,   64,   65,  122,  123,  107,   54,   66,
   55,    0,    0,    0,    0,   67,   54,   61,   55,   63,
   64,   65,    0,    0,    0,   61,   66,   63,   64,   65,
    0,    0,   67,   54,   66,   55,   86,    0,    0,    0,
   67,    0,   61,    0,   63,   64,   65,    0,    0,    0,
    0,   66,   69,    0,    0,    0,    0,   67,    0,    0,
    0,    0,    0,  128,    0,    0,    0,  172,  126,  124,
    0,  125,  131,  127,   50,   50,   86,    0,   50,   50,
   50,   50,    0,    0,   50,   50,  130,    0,  129,    0,
    0,    0,    0,    0,    0,   57,    0,   57,   57,   57,
   55,   55,    0,    0,   55,   55,   55,   55,    0,    0,
   55,   55,   57,   57,   57,    0,   57,  132,    0,   34,
    0,  128,    0,    0,    0,  174,  126,  124,    0,  125,
  131,  127,  116,  117,    0,    0,  118,  119,  120,  121,
    0,    0,  122,  123,  130,  128,  129,   57,    0,    0,
  126,  124,    0,  125,  131,  127,   86,    0,   86,    0,
    0,   86,    0,    0,    0,    0,  177,    0,  130,   68,
  129,  128,   68,    0,    0,  132,  126,  124,  182,  125,
  131,  127,    0,  128,   86,   86,   68,   68,  126,  124,
   86,  125,  131,  127,  130,  128,  129,    0,    0,  132,
  126,  124,    0,  125,  131,  127,  130,  128,  129,    0,
    0,  199,  126,  124,    0,  125,  131,  127,  130,    0,
  129,   68,    0,    0,    0,  132,    0,    0,    0,    0,
  130,    0,  129,    0,    0,  128,    0,  132,    0,  185,
  126,  124,    0,  125,  131,  127,    0,    0,    0,  132,
    0,  194,   17,   18,   19,   20,   21,  200,  130,  128,
  129,  132,    0,    0,  126,  124,    0,  125,  131,  127,
    0,   54,    0,    0,   22,    0,   54,   54,    0,   54,
   54,   54,  130,    0,  129,    0,    0,    0,    0,  132,
    0,    0,    0,  128,   54,    0,   54,    0,  126,  124,
    0,  125,  131,  127,  116,  117,    0,    0,  118,  119,
  120,  121,    0,  132,  122,  123,  130,    0,  129,    0,
    0,  128,    0,    0,    0,   54,  126,  124,    0,  125,
  131,  127,   57,   57,    0,    0,   57,   57,   57,   57,
    0,   87,    0,    0,  130,    0,  129,  132,    0,    0,
    0,  128,    0,    0,    0,    0,  126,  124,    0,  125,
  131,  127,  116,  117,    0,    0,  118,  119,  120,  121,
    0,    0,  122,  123,  130,  132,  129,    0,    0,    0,
    0,   87,    0,    0,    0,    0,  116,  117,    0,    0,
  118,  119,  120,  121,    0,    0,  122,  123,    0,    0,
    0,    0,    0,    0,    0,  132,   68,   68,    0,    0,
    0,    0,  116,  117,    0,    0,  118,  119,  120,  121,
    0,    0,  122,  123,  116,  117,    0,    0,  118,  119,
  120,  121,    0,    0,  122,  123,  116,  117,    0,    0,
  118,  119,  120,  121,    0,    0,  122,  123,  116,  117,
    0,    0,  118,  119,  120,  121,    0,   66,  122,  123,
   66,   87,    0,   87,    0,    0,   87,    0,    0,    0,
    0,    0,    0,    0,   66,   66,  116,  117,    0,    0,
  118,  119,  120,  121,    0,   67,  122,  123,   67,   87,
   87,    0,    0,    0,    0,   87,    0,    0,    0,    0,
  116,  117,   67,   67,  118,  119,  120,  121,    0,   66,
  122,  123,   54,   54,    0,    0,   54,   54,   54,   54,
    0,    0,   54,   54,    0,   71,    0,    0,    0,   71,
   71,   71,   71,   71,  116,   71,    0,   67,  118,  119,
  120,  121,    0,    0,  122,  123,   71,   71,   71,    0,
   71,    0,    0,   79,    0,    0,    0,   79,   79,   79,
   79,   79,   79,   79,    0,    0,  118,  119,  120,  121,
    0,    0,  122,  123,   79,   79,   79,    0,   79,    0,
    0,   71,   62,   72,    0,   62,    0,   72,   72,   72,
   72,   72,    0,   72,    0,   65,  118,  119,   65,   62,
   62,    0,  122,  123,   72,   72,   72,   79,   72,   79,
   59,    0,   65,   65,   59,   59,   59,   59,   59,   60,
   59,    0,    0,   60,   60,   60,   60,   60,    0,   60,
    0,   59,   59,   59,   62,   59,    0,    0,    0,   72,
   60,   60,   60,    0,   60,    0,   61,   65,    0,    0,
   61,   61,   61,   61,   61,    0,   61,    0,    0,   58,
    0,   58,   58,   58,    0,    0,   59,   61,   61,   61,
    0,   61,    0,    0,    0,   60,   58,   58,   58,   64,
   58,   63,   64,    0,   63,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   66,   66,   64,   64,   63,   63,
   66,   66,   61,    0,    0,    0,    0,    0,    0,    0,
    0,   58,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   67,   67,    0,    0,    0,    0,   67,   67,
    0,   64,    0,   63,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   71,   71,    0,    0,
   71,   71,   71,   71,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   79,   79,    0,    0,   79,   79,
   79,   79,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   62,
   62,    0,    0,    0,   72,   72,    0,    0,   72,   72,
   72,   72,   65,   65,    0,    0,    0,    0,   65,   65,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   59,   59,    0,    0,   59,   59,   59,   59,    0,
   60,   60,    0,    0,   60,   60,   60,   60,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   61,   61,    0,
    0,   61,   61,   61,   61,   95,   58,   58,    0,    0,
   58,   58,   58,   58,  105,  106,  108,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   64,   64,   63,   63,
    0,    0,   64,   64,    0,    0,    0,  136,    0,  138,
  141,    0,    0,    0,    0,    0,  144,    0,    0,  148,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  153,  154,  155,  156,  157,  158,    0,  160,
  161,  162,  163,  164,  165,  166,  167,    0,  168,  169,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  179,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  144,  188,    0,  190,
    0,  141,    0,    0,    0,    0,  195,    0,    0,  197,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   59,   91,   45,   91,   47,   40,   46,   41,   44,
   41,   45,   91,  125,   40,   92,   71,  262,  125,  277,
   91,   44,   41,   37,   41,   59,   15,   44,   42,   43,
   33,   45,   46,   47,   15,  262,   36,   40,   38,   28,
   59,   22,   45,  264,   59,  290,   46,   28,   41,  277,
   41,   44,   91,   44,   37,  288,  289,   91,   93,   42,
  277,   33,  123,   46,   47,   58,   59,  122,   40,  123,
   93,   41,  277,   45,   44,  125,   40,   91,   33,   40,
   61,   93,  172,   41,  174,   40,   33,  177,   91,  123,
   45,  125,   44,   40,  123,   41,  151,  123,   45,   40,
   93,   40,   40,   33,   40,   40,   40,   40,   91,  277,
   40,  201,   61,  277,   41,   45,  206,   44,  277,   91,
  123,   40,  125,  200,   91,   41,  258,  259,  260,  261,
  262,   58,   59,   37,   59,   59,   91,   41,   42,   43,
   44,   45,   46,   47,   91,  277,   93,   59,   59,   59,
   41,  123,  277,   40,   58,   59,   60,   61,   62,   37,
   44,   91,   59,   41,   42,   43,   93,   45,   46,   47,
  277,   41,   41,  277,  286,   41,   44,  269,   41,  286,
   41,   59,   60,    0,   62,   59,  123,   91,  277,   93,
  277,   37,  263,   41,   41,   41,   42,   43,  277,   45,
   46,   47,  277,   59,   41,  276,    4,   11,  258,  259,
  260,  261,  262,   91,   60,   16,   62,   38,  277,  277,
  170,  175,   -1,  257,  258,  259,  260,  261,  262,  263,
  280,  265,  266,  267,  268,   -1,  270,  271,  272,  273,
  274,  275,  276,  277,  277,   91,  277,  281,   -1,  288,
  289,   -1,  286,  287,  257,  258,  259,  260,  261,  262,
  263,   -1,  265,  266,  267,  268,   -1,  270,  271,  272,
  273,  274,  275,  276,  288,  289,   -1,   -1,  281,  258,
  259,  260,  261,  262,  287,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,  268,   -1,  270,  271,
  272,  273,  274,  275,  276,  288,  289,  262,  263,  281,
  265,   -1,   -1,   -1,   -1,  287,  263,  272,  265,  274,
  275,  276,   -1,   -1,   -1,  272,  281,  274,  275,  276,
   -1,   -1,  287,  263,  281,  265,   52,   -1,   -1,   -1,
  287,   -1,  272,   -1,  274,  275,  276,   -1,   -1,   -1,
   -1,  281,  279,   -1,   -1,   -1,   -1,  287,   -1,   -1,
   -1,   -1,   -1,   37,   -1,   -1,   -1,   41,   42,   43,
   -1,   45,   46,   47,  278,  279,   92,   -1,  282,  283,
  284,  285,   -1,   -1,  288,  289,   60,   -1,   62,   -1,
   -1,   -1,   -1,   -1,   -1,   41,   -1,   43,   44,   45,
  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,   -1,
  288,  289,   58,   59,   60,   -1,   62,   91,   -1,  125,
   -1,   37,   -1,   -1,   -1,   41,   42,   43,   -1,   45,
   46,   47,  278,  279,   -1,   -1,  282,  283,  284,  285,
   -1,   -1,  288,  289,   60,   37,   62,   93,   -1,   -1,
   42,   43,   -1,   45,   46,   47,  172,   -1,  174,   -1,
   -1,  177,   -1,   -1,   -1,   -1,   58,   -1,   60,   41,
   62,   37,   44,   -1,   -1,   91,   42,   43,   44,   45,
   46,   47,   -1,   37,  200,  201,   58,   59,   42,   43,
  206,   45,   46,   47,   60,   37,   62,   -1,   -1,   91,
   42,   43,   -1,   45,   46,   47,   60,   37,   62,   -1,
   -1,   41,   42,   43,   -1,   45,   46,   47,   60,   -1,
   62,   93,   -1,   -1,   -1,   91,   -1,   -1,   -1,   -1,
   60,   -1,   62,   -1,   -1,   37,   -1,   91,   -1,   93,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,   91,
   -1,   93,  258,  259,  260,  261,  262,   59,   60,   37,
   62,   91,   -1,   -1,   42,   43,   -1,   45,   46,   47,
   -1,   37,   -1,   -1,  280,   -1,   42,   43,   -1,   45,
   46,   47,   60,   -1,   62,   -1,   -1,   -1,   -1,   91,
   -1,   -1,   -1,   37,   60,   -1,   62,   -1,   42,   43,
   -1,   45,   46,   47,  278,  279,   -1,   -1,  282,  283,
  284,  285,   -1,   91,  288,  289,   60,   -1,   62,   -1,
   -1,   37,   -1,   -1,   -1,   91,   42,   43,   -1,   45,
   46,   47,  278,  279,   -1,   -1,  282,  283,  284,  285,
   -1,   52,   -1,   -1,   60,   -1,   62,   91,   -1,   -1,
   -1,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,  278,  279,   -1,   -1,  282,  283,  284,  285,
   -1,   -1,  288,  289,   60,   91,   62,   -1,   -1,   -1,
   -1,   92,   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,
  282,  283,  284,  285,   -1,   -1,  288,  289,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   91,  278,  279,   -1,   -1,
   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,
   -1,   -1,  288,  289,  278,  279,   -1,   -1,  282,  283,
  284,  285,   -1,   -1,  288,  289,  278,  279,   -1,   -1,
  282,  283,  284,  285,   -1,   -1,  288,  289,  278,  279,
   -1,   -1,  282,  283,  284,  285,   -1,   41,  288,  289,
   44,  172,   -1,  174,   -1,   -1,  177,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   58,   59,  278,  279,   -1,   -1,
  282,  283,  284,  285,   -1,   41,  288,  289,   44,  200,
  201,   -1,   -1,   -1,   -1,  206,   -1,   -1,   -1,   -1,
  278,  279,   58,   59,  282,  283,  284,  285,   -1,   93,
  288,  289,  278,  279,   -1,   -1,  282,  283,  284,  285,
   -1,   -1,  288,  289,   -1,   37,   -1,   -1,   -1,   41,
   42,   43,   44,   45,  278,   47,   -1,   93,  282,  283,
  284,  285,   -1,   -1,  288,  289,   58,   59,   60,   -1,
   62,   -1,   -1,   37,   -1,   -1,   -1,   41,   42,   43,
   44,   45,   46,   47,   -1,   -1,  282,  283,  284,  285,
   -1,   -1,  288,  289,   58,   59,   60,   -1,   62,   -1,
   -1,   93,   41,   37,   -1,   44,   -1,   41,   42,   43,
   44,   45,   -1,   47,   -1,   41,  282,  283,   44,   58,
   59,   -1,  288,  289,   58,   59,   60,   91,   62,   93,
   37,   -1,   58,   59,   41,   42,   43,   44,   45,   37,
   47,   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,
   -1,   58,   59,   60,   93,   62,   -1,   -1,   -1,   93,
   58,   59,   60,   -1,   62,   -1,   37,   93,   -1,   -1,
   41,   42,   43,   44,   45,   -1,   47,   -1,   -1,   41,
   -1,   43,   44,   45,   -1,   -1,   93,   58,   59,   60,
   -1,   62,   -1,   -1,   -1,   93,   58,   59,   60,   41,
   62,   41,   44,   -1,   44,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  278,  279,   58,   59,   58,   59,
  284,  285,   93,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   93,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  278,  279,   -1,   -1,   -1,   -1,  284,  285,
   -1,   93,   -1,   93,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,
  282,  283,  284,  285,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,  282,  283,
  284,  285,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  278,
  279,   -1,   -1,   -1,  278,  279,   -1,   -1,  282,  283,
  284,  285,  278,  279,   -1,   -1,   -1,   -1,  284,  285,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,
  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,   -1,
   -1,  282,  283,  284,  285,   59,  278,  279,   -1,   -1,
  282,  283,  284,  285,   68,   69,   70,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,  278,  279,
   -1,   -1,  284,  285,   -1,   -1,   -1,   91,   -1,   93,
   94,   -1,   -1,   -1,   -1,   -1,  100,   -1,   -1,  103,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  116,  117,  118,  119,  120,  121,   -1,  123,
  124,  125,  126,  127,  128,  129,  130,   -1,  132,  133,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  143,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  170,  171,   -1,  173,
   -1,  175,   -1,   -1,   -1,   -1,  180,   -1,   -1,  183,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=292;
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
"IF_DIV","VAR","ARRAY_REPEAT","ARRAY_ADD","SEALED","UMINUS","EMPTY",
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
"Expr : Expr ARRAY_REPEAT Constant",
"Expr : Expr ARRAY_ADD Expr",
"Constant : LITERAL",
"Constant : NULL",
"Constant : '[' ListConst ']'",
"ListConst : Constant",
"ListConst : ListConst ',' Constant",
"ListConst :",
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

//#line 504 "Parser.y"
    
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
//#line 675 "Parser.java"
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
//#line 393 "Parser.y"
{
                		yyval.expr = new Tree.ArrayRepeat(val_peek(2).expr, val_peek(0).expr, val_peek(2).loc);
                	}
break;
case 81:
//#line 397 "Parser.y"
{
                		yyval.expr = new Tree.ArrayAdd(val_peek(2).expr, val_peek(0).expr, val_peek(2).loc);
	                }
break;
case 82:
//#line 403 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 83:
//#line 407 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 84:
//#line 411 "Parser.y"
{
				
						yyval.expr = new Tree.ListConst(val_peek(1).elist,  val_peek(1).loc);
				}
break;
case 85:
//#line 418 "Parser.y"
{
					yyval = new SemValue();
                    yyval.elist = new ArrayList<Expr> ();
                    yyval.elist.add(val_peek(0).expr);
				}
break;
case 86:
//#line 424 "Parser.y"
{
					yyval.elist.add(val_peek(0).expr);
				}
break;
case 87:
//#line 428 "Parser.y"
{
					yyval = new SemValue();
				}
break;
case 89:
//#line 436 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 90:
//#line 443 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 91:
//#line 447 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 92:
//#line 454 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 93:
//#line 460 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 94:
//#line 466 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 95:
//#line 472 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 96:
//#line 478 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 97:
//#line 482 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 98:
//#line 488 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 99:
//#line 492 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 100:
//#line 498 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1354 "Parser.java"
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
