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
   25,   25,   30,   30,   30,   31,   31,   31,   29,   29,
   32,   32,   16,   17,   20,   15,   33,   33,   18,   18,
   19,
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
    3,    6,    1,    1,    3,    1,    3,    0,    1,    0,
    3,    1,    5,    9,    1,    6,    2,    0,    2,    1,
    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    2,    0,    0,
    0,   14,   18,    0,    0,   18,    7,    8,    6,    9,
    0,    0,   12,   16,    0,    0,   17,    0,   10,    0,
    4,    0,    0,   13,    0,    0,   11,    0,   22,    0,
    0,    0,    0,    5,    0,    0,    0,   27,   24,   21,
   23,    0,    0,   84,   75,    0,    0,    0,    0,   95,
    0,    0,    0,    0,   83,    0,    0,    0,    0,    0,
    0,   25,   28,   36,   26,    0,   30,   31,   32,    0,
    0,    0,    0,   38,    0,    0,    0,    0,   56,    0,
    0,    0,    0,    0,    0,   54,   55,    0,    0,    0,
    0,    0,    0,   52,    0,    0,    0,    0,   86,    0,
   29,   33,   34,   35,   37,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   48,    0,    0,    0,    0,    0,    0,    0,    0,   41,
    0,    0,    0,    0,    0,   73,   74,    0,    0,   70,
    0,   85,    0,    0,    0,    0,    0,    0,   80,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   39,    0,   76,    0,    0,
  101,    0,    0,   87,   51,    0,    0,    0,    0,   93,
    0,    0,   40,   43,   77,    0,    0,   79,    0,   53,
   44,    0,    0,   96,   78,   82,    0,   97,    0,   94,
};
final static short yydgoto[] = {                          3,
    4,    5,   73,   25,   40,   10,   15,   27,   41,   42,
   74,   52,   75,   76,   77,   78,   79,   80,   81,   82,
   83,   84,  139,  140,   85,   96,   97,   88,  187,   89,
  110,  145,  204,
};
final static short yysindex[] = {                      -243,
 -262, -241,    0, -243,    0, -237, -232,    0, -225,  -70,
 -237,    0,    0,  -64,  137,    0,    0,    0,    0,    0,
 -211, -132,    0,    0,    8,  -86,    0,  304,    0,  -85,
    0,   32,   -8,    0,   48, -132,    0, -132,    0,  -77,
   36,   49,   55,    0,  -26, -132,  -26,    0,    0,    0,
    0,   -2,   60,    0,    0,   62,   63,  -22,   71,    0,
 -152,   72,   74,   75,    0,   77, -182,   71,   71,   46,
  -55,    0,    0,    0,    0,   59,    0,    0,    0,   65,
   73,   76,   87,    0,  765,   58,    0, -155,    0, -146,
   71,   71,   71,   71,  765,    0,    0,   93,   45,   71,
  107,  108,   71,    0,  -37,  -37, -127,  327,    0,  -36,
    0,    0,    0,    0,    0,   71,   71,   71,   71,   71,
   71,  -55,   71,   71,   71,   71,   71,   71,   71,   71,
    0,   71,   71,  111,  109,  535,   95,  576, -112,    0,
  597,  120,   54,  765,  -21,    0,    0,  631,  122,    0,
  -55,    0,  938,  861,  -13,  -13,  968,  968,    0,  -13,
   14,   14,  -37,  -37,  -37,  -13,  -13,  478,  765,   71,
   71,   29,   71,   29,   71,    0,   29,    0,  655,   71,
    0, -106,   71,    0,    0,   71,  131,  129,  708,    0,
  904,  -94,    0,    0,    0,  765,  135,    0,  738,    0,
    0,   71,   29,    0,    0,    0,  136,    0,   29,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  179,    0,   57,    0,    0,    0,    0,
   57,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  127,    0,    0,    0,  157,    0,  157,    0,    0,
    0,  163,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -58,    0,    0,    0,    0,    0,    0,  -57,    0,
    0,    0,    0,    0,    0,    0,    0,  -71,  -71,  -71,
  -28,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  806,  508,    0,    0,    0,
  -71,  -58,  -71, -108,  148,    0,    0,    0,    0,  -71,
    0,    0,  -71,    0,  123,  152,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -71,  -71,  -71,  -71,  -71,
  -71,    0,  -71,  -71,  -71,  -71,  -71,  -71,  -71,  -71,
    0,  -71,  -71,   97,    0,    0,    0,    0,    0,    0,
    0,    0,  -71,  -16,    0,    0,    0,    0,    0,    0,
    0,    0,    5,  515,  307,  993,  448,  901,    0,  143,
  829, 1017,  401,  425,  454, 1039, 1061,    0,  -19,  -31,
  -71,  -58,  -71,  -58,  -71,    0,  -58,    0,    0,  -71,
    0,    0,  -71,    0,    0,  -71,    0,  168,    0,    0,
    0,  -33,    0,    0,    0,   27,    0,    0,    0,    0,
    0,  -30,  -58,    0,    0,    0,    0,    0,  -58,    0,
};
final static short yygindex[] = {                         0,
    0,  209,   22,   37,   20,  204,  201,    0,  180,    0,
   35,    0, -133,  -89,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   47, 1244,  279,  301,    0,    0,  -67,
    0,   53,    0,
};
final static int YYTABLESIZE=1430;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         98,
   47,  100,  137,  109,   33,   33,   98,  151,  131,   90,
   47,   98,  176,   33,    6,   88,   42,   93,    1,  181,
    7,   45,  180,  128,   92,   98,    9,   92,  126,  124,
   69,  125,  131,  127,   26,   71,   24,   70,  190,   45,
  192,   30,   68,  194,   11,   68,    2,   26,   68,   24,
  128,   12,   13,  132,  159,  126,  152,   98,   16,  131,
  127,   69,   68,   68,   88,   29,   31,   91,   70,  208,
   91,   36,   39,   68,   39,  210,   45,  132,   69,   49,
   99,   51,   50,  184,   37,   70,   69,   38,   71,   98,
   68,   98,   46,   70,  104,   47,   48,   68,   68,   90,
   94,   91,   92,   69,  132,   17,   18,   19,   20,   21,
   70,  100,  207,  101,  102,   68,  103,  111,  133,   71,
   48,  134,   72,  112,   98,   17,   18,   19,   20,   21,
  135,  113,  142,   50,  114,  143,   71,   50,   50,   50,
   50,   50,   50,   50,   71,  115,   37,  146,  147,  149,
  170,   48,  171,  173,   50,   50,   50,   50,   50,   71,
  178,   71,  183,   71,   71,   71,   71,   71,   49,   71,
  197,  200,  180,  175,  203,  205,  209,   42,    1,   15,
   71,   71,   71,   81,   71,    5,   81,   50,   72,   50,
   32,   35,   72,   72,   72,   72,   72,   20,   72,   44,
   81,   81,   81,   19,   81,   49,   99,   54,   89,   72,
   72,   72,    8,   72,   14,   71,   28,   43,   49,   49,
   65,  193,  188,   98,   98,   98,   98,   98,   98,   98,
    0,   98,   98,   98,   98,   81,   98,   98,   98,   98,
   98,   98,   98,   98,   72,   49,   49,   98,    0,    0,
    0,    0,   98,   98,   53,   17,   18,   19,   20,   21,
   54,   23,   55,   56,   57,   58,    0,   59,   60,   61,
   62,   63,   64,   65,  122,  123,    0,    0,   66,    0,
    0,    0,   68,   68,   67,   53,   17,   18,   19,   20,
   21,   54,    0,   55,   56,   57,   58,    0,   59,   60,
   61,   62,   63,   64,   65,    0,    0,  107,   54,   66,
   55,    0,    0,    0,    0,   67,   54,   61,   55,   63,
   64,   65,    0,    0,    0,   61,   66,   63,   64,   65,
   86,    0,   67,   54,   66,   55,    0,    0,    0,    0,
   67,    0,   61,    0,   63,   64,   65,   66,    0,    0,
   66,   66,   87,    0,    0,    0,    0,   67,    0,    0,
    0,    0,    0,  128,   66,   66,    0,  150,  126,  124,
   86,  125,  131,  127,   50,   50,    0,    0,   50,   50,
   50,   50,    0,    0,   50,   50,  130,    0,  129,    0,
    0,    0,   87,    0,   17,   18,   19,   20,   21,   66,
   71,   71,    0,    0,   71,   71,   71,   71,    0,    0,
   71,   71,    0,    0,    0,    0,   22,  132,    0,    0,
   81,   81,    0,    0,   81,   81,   81,   81,   34,   72,
   72,    0,    0,   72,   72,   72,   72,   59,    0,   72,
   72,   59,   59,   59,   59,   59,    0,   59,    0,    0,
   86,    0,   86,    0,    0,   86,    0,    0,   59,   59,
   59,   60,   59,    0,    0,   60,   60,   60,   60,   60,
    0,   60,   87,    0,   87,    0,    0,   87,    0,    0,
   86,   86,   60,   60,   60,    0,   60,   86,   62,    0,
   61,   62,    0,   59,   61,   61,   61,   61,   61,    0,
   61,    0,   87,   87,    0,   62,   62,    0,    0,   87,
    0,   61,   61,   61,  128,   61,    0,   60,    0,  126,
  124,    0,  125,  131,  127,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  186,    0,  130,    0,  129,
   62,    0,    0,    0,   55,    0,   61,    0,   46,   55,
   55,    0,   55,   55,   55,   69,    0,    0,   69,    0,
    0,   17,   18,   19,   20,   21,   46,   55,  132,   55,
  185,  128,   69,   69,    0,  172,  126,  124,    0,  125,
  131,  127,    0,   22,   66,   66,    0,    0,    0,    0,
   66,   66,    0,    0,  130,    0,  129,    0,   55,    0,
    0,    0,    0,    0,  116,  117,    0,   69,  118,  119,
  120,  121,  128,    0,  122,  123,  174,  126,  124,    0,
  125,  131,  127,    0,    0,  132,    0,    0,    0,    0,
    0,    0,    0,  128,    0,  130,    0,  129,  126,  124,
    0,  125,  131,  127,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  177,    0,  130,    0,  129,    0,
    0,    0,    0,    0,    0,    0,  132,  128,    0,    0,
    0,    0,  126,  124,  182,  125,  131,  127,   59,   59,
    0,    0,   59,   59,   59,   59,    0,  132,   59,   59,
  130,  128,  129,    0,    0,    0,  126,  124,    0,  125,
  131,  127,   60,   60,    0,    0,   60,   60,   60,   60,
    0,    0,   60,   60,  130,    0,  129,    0,    0,    0,
    0,  132,    0,    0,    0,   62,   62,    0,    0,    0,
    0,   61,   61,    0,    0,   61,   61,   61,   61,    0,
    0,   61,   61,    0,  128,  132,    0,  195,  201,  126,
  124,    0,  125,  131,  127,  116,  117,    0,    0,  118,
  119,  120,  121,    0,    0,  122,  123,  130,    0,  129,
    0,    0,    0,    0,  128,    0,    0,    0,    0,  126,
  124,    0,  125,  131,  127,   55,   55,    0,    0,   55,
   55,   55,   55,   69,    0,   55,   55,  130,  132,  129,
    0,  128,    0,    0,    0,    0,  126,  124,    0,  125,
  131,  127,  116,  117,    0,    0,  118,  119,  120,  121,
    0,    0,  122,  123,  130,    0,  129,    0,  132,    0,
  206,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   54,    0,    0,    0,    0,   54,   54,    0,
   54,   54,   54,  116,  117,  132,    0,  118,  119,  120,
  121,    0,    0,  122,  123,   54,    0,   54,    0,   57,
    0,   57,   57,   57,  116,  117,    0,    0,  118,  119,
  120,  121,    0,    0,  122,  123,   57,   57,   57,    0,
   57,    0,    0,    0,    0,    0,   54,  128,    0,    0,
    0,    0,  126,  124,    0,  125,  131,  127,  116,  117,
    0,    0,  118,  119,  120,  121,    0,    0,  122,  123,
  130,   57,  129,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  116,  117,    0,    0,  118,  119,  120,  121,
  128,   63,  122,  123,   63,  126,  124,    0,  125,  131,
  127,  132,    0,    0,    0,    0,    0,    0,   63,   63,
    0,    0,  202,  130,    0,  129,    0,    0,    0,    0,
    0,    0,    0,    0,  128,    0,    0,    0,    0,  126,
  124,    0,  125,  131,  127,  116,  117,    0,    0,  118,
  119,  120,  121,   63,  132,  122,  123,  130,    0,  129,
    0,    0,    0,    0,  128,    0,    0,    0,    0,  126,
  124,    0,  125,  131,  127,  116,  117,    0,    0,  118,
  119,  120,  121,    0,    0,  122,  123,  130,  132,  129,
    0,    0,    0,   67,    0,    0,   67,    0,    0,    0,
    0,    0,  116,  117,    0,    0,  118,  119,  120,  121,
   67,   67,  122,  123,    0,    0,    0,   58,  132,   58,
   58,   58,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   58,   58,   58,    0,   58,   65,
    0,    0,   65,   54,   54,   67,    0,   54,   54,   54,
   54,    0,    0,   54,   54,    0,   65,   65,    0,    0,
    0,   64,    0,    0,   64,    0,   57,   57,    0,   58,
   57,   57,   57,   57,    0,    0,   57,   57,   64,   64,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   65,    0,    0,    0,    0,    0,    0,  116,    0,
    0,    0,  118,  119,  120,  121,    0,    0,  122,  123,
    0,    0,    0,   64,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   63,   63,
    0,  116,  117,    0,    0,  118,  119,  120,  121,    0,
    0,  122,  123,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  118,
  119,  120,  121,    0,    0,  122,  123,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  118,
  119,    0,    0,    0,    0,  122,  123,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   67,   67,    0,    0,    0,    0,   67,   67,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   58,   58,    0,    0,   58,   58,
   58,   58,   95,    0,   58,   58,    0,    0,    0,    0,
    0,  105,  106,  108,    0,    0,   65,   65,    0,    0,
    0,    0,   65,   65,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  136,    0,  138,  141,   64,   64,
    0,    0,    0,  144,   64,   64,  148,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  153,
  154,  155,  156,  157,  158,    0,  160,  161,  162,  163,
  164,  165,  166,  167,    0,  168,  169,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  179,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  144,  189,    0,  191,    0,  141,    0,
    0,    0,    0,  196,    0,    0,  198,    0,    0,  199,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   59,   92,   71,   91,   91,   40,   44,   46,   41,
   41,   45,  125,   91,  277,   44,  125,   40,  262,   41,
  262,   41,   44,   37,   41,   59,  264,   44,   42,   43,
   33,   45,   46,   47,   15,   91,   15,   40,  172,   59,
  174,   22,   45,  177,  277,   41,  290,   28,   44,   28,
   37,  277,  123,   91,  122,   42,   93,   91,  123,   46,
   47,   33,   58,   59,   93,  277,   59,   41,   40,  203,
   44,   40,   36,   45,   38,  209,   41,   91,   33,   45,
   61,   47,   46,  151,   93,   40,   33,   40,   91,  123,
   45,  125,   44,   40,  277,   41,  123,   93,   45,   40,
  123,   40,   40,   33,   91,  258,  259,  260,  261,  262,
   40,   40,  202,   40,   40,   45,   40,   59,   61,   91,
  123,  277,  125,   59,  277,  258,  259,  260,  261,  262,
  277,   59,   40,   37,   59,   91,   91,   41,   42,   43,
   44,   45,   46,   47,   91,   59,   93,   41,   41,  277,
   40,  123,   44,   59,   58,   59,   60,   61,   62,   37,
   41,   91,   41,   41,   42,   43,   44,   45,  277,   47,
  277,   41,   44,  286,  269,   41,   41,  286,    0,  123,
   58,   59,   60,   41,   62,   59,   44,   91,   37,   93,
  277,  277,   41,   42,   43,   44,   45,   41,   47,  277,
   58,   59,   60,   41,   62,  277,   59,  263,   41,   58,
   59,   60,    4,   62,   11,   93,   16,   38,  277,  277,
  276,  175,  170,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,   93,  270,  271,  272,  273,
  274,  275,  276,  277,   93,  277,  277,  281,   -1,   -1,
   -1,   -1,  286,  287,  257,  258,  259,  260,  261,  262,
  263,  125,  265,  266,  267,  268,   -1,  270,  271,  272,
  273,  274,  275,  276,  288,  289,   -1,   -1,  281,   -1,
   -1,   -1,  278,  279,  287,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,  268,   -1,  270,  271,
  272,  273,  274,  275,  276,   -1,   -1,  262,  263,  281,
  265,   -1,   -1,   -1,   -1,  287,  263,  272,  265,  274,
  275,  276,   -1,   -1,   -1,  272,  281,  274,  275,  276,
   52,   -1,  287,  263,  281,  265,   -1,   -1,   -1,   -1,
  287,   -1,  272,   -1,  274,  275,  276,   41,   -1,   -1,
   44,  281,   52,   -1,   -1,   -1,   -1,  287,   -1,   -1,
   -1,   -1,   -1,   37,   58,   59,   -1,   41,   42,   43,
   92,   45,   46,   47,  278,  279,   -1,   -1,  282,  283,
  284,  285,   -1,   -1,  288,  289,   60,   -1,   62,   -1,
   -1,   -1,   92,   -1,  258,  259,  260,  261,  262,   93,
  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,   -1,
  288,  289,   -1,   -1,   -1,   -1,  280,   91,   -1,   -1,
  278,  279,   -1,   -1,  282,  283,  284,  285,  125,  278,
  279,   -1,   -1,  282,  283,  284,  285,   37,   -1,  288,
  289,   41,   42,   43,   44,   45,   -1,   47,   -1,   -1,
  172,   -1,  174,   -1,   -1,  177,   -1,   -1,   58,   59,
   60,   37,   62,   -1,   -1,   41,   42,   43,   44,   45,
   -1,   47,  172,   -1,  174,   -1,   -1,  177,   -1,   -1,
  202,  203,   58,   59,   60,   -1,   62,  209,   41,   -1,
   37,   44,   -1,   93,   41,   42,   43,   44,   45,   -1,
   47,   -1,  202,  203,   -1,   58,   59,   -1,   -1,  209,
   -1,   58,   59,   60,   37,   62,   -1,   93,   -1,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   58,   -1,   60,   -1,   62,
   93,   -1,   -1,   -1,   37,   -1,   93,   -1,   41,   42,
   43,   -1,   45,   46,   47,   41,   -1,   -1,   44,   -1,
   -1,  258,  259,  260,  261,  262,   59,   60,   91,   62,
   93,   37,   58,   59,   -1,   41,   42,   43,   -1,   45,
   46,   47,   -1,  280,  278,  279,   -1,   -1,   -1,   -1,
  284,  285,   -1,   -1,   60,   -1,   62,   -1,   91,   -1,
   -1,   -1,   -1,   -1,  278,  279,   -1,   93,  282,  283,
  284,  285,   37,   -1,  288,  289,   41,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   91,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   37,   -1,   60,   -1,   62,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   58,   -1,   60,   -1,   62,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   91,   37,   -1,   -1,
   -1,   -1,   42,   43,   44,   45,   46,   47,  278,  279,
   -1,   -1,  282,  283,  284,  285,   -1,   91,  288,  289,
   60,   37,   62,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,  278,  279,   -1,   -1,  282,  283,  284,  285,
   -1,   -1,  288,  289,   60,   -1,   62,   -1,   -1,   -1,
   -1,   91,   -1,   -1,   -1,  278,  279,   -1,   -1,   -1,
   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,
   -1,  288,  289,   -1,   37,   91,   -1,   93,   41,   42,
   43,   -1,   45,   46,   47,  278,  279,   -1,   -1,  282,
  283,  284,  285,   -1,   -1,  288,  289,   60,   -1,   62,
   -1,   -1,   -1,   -1,   37,   -1,   -1,   -1,   -1,   42,
   43,   -1,   45,   46,   47,  278,  279,   -1,   -1,  282,
  283,  284,  285,  279,   -1,  288,  289,   60,   91,   62,
   -1,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,  278,  279,   -1,   -1,  282,  283,  284,  285,
   -1,   -1,  288,  289,   60,   -1,   62,   -1,   91,   -1,
   93,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,
   45,   46,   47,  278,  279,   91,   -1,  282,  283,  284,
  285,   -1,   -1,  288,  289,   60,   -1,   62,   -1,   41,
   -1,   43,   44,   45,  278,  279,   -1,   -1,  282,  283,
  284,  285,   -1,   -1,  288,  289,   58,   59,   60,   -1,
   62,   -1,   -1,   -1,   -1,   -1,   91,   37,   -1,   -1,
   -1,   -1,   42,   43,   -1,   45,   46,   47,  278,  279,
   -1,   -1,  282,  283,  284,  285,   -1,   -1,  288,  289,
   60,   93,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,
   37,   41,  288,  289,   44,   42,   43,   -1,   45,   46,
   47,   91,   -1,   -1,   -1,   -1,   -1,   -1,   58,   59,
   -1,   -1,   59,   60,   -1,   62,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   37,   -1,   -1,   -1,   -1,   42,
   43,   -1,   45,   46,   47,  278,  279,   -1,   -1,  282,
  283,  284,  285,   93,   91,  288,  289,   60,   -1,   62,
   -1,   -1,   -1,   -1,   37,   -1,   -1,   -1,   -1,   42,
   43,   -1,   45,   46,   47,  278,  279,   -1,   -1,  282,
  283,  284,  285,   -1,   -1,  288,  289,   60,   91,   62,
   -1,   -1,   -1,   41,   -1,   -1,   44,   -1,   -1,   -1,
   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,
   58,   59,  288,  289,   -1,   -1,   -1,   41,   91,   43,
   44,   45,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   58,   59,   60,   -1,   62,   41,
   -1,   -1,   44,  278,  279,   93,   -1,  282,  283,  284,
  285,   -1,   -1,  288,  289,   -1,   58,   59,   -1,   -1,
   -1,   41,   -1,   -1,   44,   -1,  278,  279,   -1,   93,
  282,  283,  284,  285,   -1,   -1,  288,  289,   58,   59,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   93,   -1,   -1,   -1,   -1,   -1,   -1,  278,   -1,
   -1,   -1,  282,  283,  284,  285,   -1,   -1,  288,  289,
   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,
   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,
   -1,  288,  289,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  282,
  283,  284,  285,   -1,   -1,  288,  289,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  282,
  283,   -1,   -1,   -1,   -1,  288,  289,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  278,  279,   -1,   -1,   -1,   -1,  284,  285,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,  282,  283,
  284,  285,   59,   -1,  288,  289,   -1,   -1,   -1,   -1,
   -1,   68,   69,   70,   -1,   -1,  278,  279,   -1,   -1,
   -1,   -1,  284,  285,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   91,   -1,   93,   94,  278,  279,
   -1,   -1,   -1,  100,  284,  285,  103,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  116,
  117,  118,  119,  120,  121,   -1,  123,  124,  125,  126,
  127,  128,  129,  130,   -1,  132,  133,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  143,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  170,  171,   -1,  173,   -1,  175,   -1,
   -1,   -1,   -1,  180,   -1,   -1,  183,   -1,   -1,  186,
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
"Expr : Expr '[' Expr ':' Expr ']'",
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

//#line 511 "Parser.y"
    
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
//#line 700 "Parser.java"
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
//#line 59 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 65 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 69 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 79 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 85 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 89 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 93 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 97 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 101 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 11:
//#line 105 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 12:
//#line 111 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc,false);
					}
break;
case 13:
//#line 115 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc,true);
					}
break;
case 14:
//#line 121 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 15:
//#line 125 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 16:
//#line 131 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 17:
//#line 135 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 18:
//#line 139 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 20:
//#line 147 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 21:
//#line 154 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 22:
//#line 158 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 23:
//#line 165 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 169 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 175 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 26:
//#line 181 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 27:
//#line 185 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 28:
//#line 192 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 29:
//#line 197 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 39:
//#line 214 "Parser.y"
{
					yyval.stmt = new Tree.GuardedStmt(val_peek(1).elist, val_peek(1).loc);
				}
break;
case 40:
//#line 219 "Parser.y"
{
					yyval.elist.add(val_peek(0).expr);
				}
break;
case 41:
//#line 224 "Parser.y"
{
                    yyval = new SemValue();
                    yyval.elist = new ArrayList<Expr> ();
                    yyval.elist.add(val_peek(0).expr);
                }
break;
case 42:
//#line 230 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 43:
//#line 236 "Parser.y"
{
					yyval.expr = new Tree.IfBranch(val_peek(2).expr, val_peek(0).stmt, val_peek(2).loc);				
				}
break;
case 44:
//#line 241 "Parser.y"
{
					yyval.stmt = new Scopy(val_peek(3).ident,val_peek(1).expr,val_peek(5).loc);	
				}
break;
case 45:
//#line 247 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 46:
//#line 251 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 47:
//#line 255 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 49:
//#line 262 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 50:
//#line 268 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 51:
//#line 275 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 52:
//#line 279 "Parser.y"
{
                	yyval.lvalue = new Tree.Var(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
                }
break;
case 53:
//#line 288 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 54:
//#line 297 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 57:
//#line 303 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 307 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 311 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 315 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 319 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 323 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 327 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 331 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 335 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 339 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 343 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 68:
//#line 347 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 69:
//#line 351 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 70:
//#line 355 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 71:
//#line 359 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 72:
//#line 363 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 73:
//#line 367 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 74:
//#line 371 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 75:
//#line 375 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 76:
//#line 379 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 77:
//#line 383 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 78:
//#line 387 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 79:
//#line 391 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 80:
//#line 395 "Parser.y"
{
                		yyval.expr = new Tree.ArrayRepeat(val_peek(2).expr, val_peek(0).expr, val_peek(2).loc);
                	}
break;
case 81:
//#line 399 "Parser.y"
{
                		yyval.expr = new Tree.ArrayAdd(val_peek(2).expr, val_peek(0).expr, val_peek(2).loc);
	                }
break;
case 82:
//#line 403 "Parser.y"
{
                		yyval.expr = new Tree.subArray(val_peek(5).expr, val_peek(3).expr,val_peek(1).expr,val_peek(5).loc);
	            	}
break;
case 83:
//#line 410 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 84:
//#line 414 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 85:
//#line 418 "Parser.y"
{
				
						yyval.expr = new Tree.ListConst(val_peek(1).elist,  val_peek(1).loc);
				}
break;
case 86:
//#line 425 "Parser.y"
{
					yyval = new SemValue();
                    yyval.elist = new ArrayList<Expr> ();
                    yyval.elist.add(val_peek(0).expr);
				}
break;
case 87:
//#line 431 "Parser.y"
{
					yyval.elist.add(val_peek(0).expr);
				}
break;
case 88:
//#line 435 "Parser.y"
{
					yyval = new SemValue();
				}
break;
case 90:
//#line 443 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 91:
//#line 450 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 92:
//#line 454 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 93:
//#line 461 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 94:
//#line 467 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 95:
//#line 473 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 96:
//#line 479 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 97:
//#line 485 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 98:
//#line 489 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 99:
//#line 495 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 100:
//#line 499 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 101:
//#line 505 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1385 "Parser.java"
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
