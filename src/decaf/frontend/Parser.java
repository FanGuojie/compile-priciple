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
public final static short DEFAULT=290;
public final static short IN=291;
public final static short SEALED=292;
public final static short UMINUS=293;
public final static short EMPTY=294;
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
   25,   25,   25,   25,   25,   30,   30,   30,   31,   31,
   31,   29,   29,   32,   32,   16,   17,   20,   15,   33,
   33,   18,   18,   19,
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
    3,    6,    6,    7,    9,    1,    1,    3,    1,    3,
    0,    1,    0,    3,    1,    5,    9,    1,    6,    2,
    0,    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    2,    0,    0,
    0,   14,   18,    0,    0,   18,    7,    8,    6,    9,
    0,    0,   12,   16,    0,    0,   17,    0,   10,    0,
    4,    0,    0,   13,    0,    0,   11,    0,   22,    0,
    0,    0,    0,    5,    0,    0,    0,   27,   24,   21,
   23,    0,    0,   87,   75,    0,    0,    0,    0,   98,
    0,    0,    0,    0,   86,    0,    0,    0,    0,    0,
    0,   25,   28,   36,   26,    0,   30,   31,   32,    0,
    0,    0,    0,   38,    0,    0,    0,    0,   56,    0,
    0,    0,    0,    0,    0,   54,   55,    0,    0,    0,
    0,    0,    0,   52,    0,    0,    0,    0,    0,    0,
    0,   29,   33,   34,   35,   37,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   48,    0,    0,    0,    0,    0,    0,    0,    0,
   41,    0,    0,    0,    0,    0,   73,   74,    0,    0,
   70,    0,    0,   88,    0,    0,    0,    0,    0,    0,
    0,   80,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   39,    0,
   76,    0,    0,  104,    0,    0,    0,   90,   89,    0,
    0,    0,    0,    0,   96,    0,    0,   40,   43,   77,
    0,    0,   79,    0,    0,    0,   53,   44,    0,    0,
   99,   78,    0,    0,   82,    0,  100,    0,   84,    0,
    0,   97,   85,
};
final static short yydgoto[] = {                          3,
    4,    5,   73,   25,   40,   10,   15,   27,   41,   42,
   74,   52,   75,   76,   77,   78,   79,   80,   81,   82,
   83,   84,  140,  141,   85,   96,   97,   88,  192,   89,
  111,  146,  211,
};
final static short yysindex[] = {                      -242,
 -255, -221,    0, -242,    0, -219, -230,    0, -226,  -68,
 -219,    0,    0,  -60,  198,    0,    0,    0,    0,    0,
 -213, -152,    0,    0,    7,  -90,    0,  496,    0,  -86,
    0,   31,  -18,    0,   33, -152,    0, -152,    0,  -85,
   35,   39,   36,    0,  -39, -152,  -39,    0,    0,    0,
    0,   -2,   48,    0,    0,   53,   55,  -23,   71,    0,
   95,   56,   58,   61,    0,   62, -174,   71,   71,   46,
   71,    0,    0,    0,    0,   59,    0,    0,    0,   60,
   63,   65,   67,    0, 1046,   51,    0, -164,    0, -163,
   71,   71,   71,   71, 1046,    0,    0,   77,   37,   71,
   86,   88,   71,    0,  -21,  -21, -147,  621,  781,    0,
  -28,    0,    0,    0,    0,    0,   71,   71,   71,   71,
   71,   71,  -83,   71,   71,   71,   71,   71,   71,   71,
   71,    0,   71,   71,   92,   89,  816,   76,  851, -112,
    0,  875,  105,   54, 1046,   -4,    0,    0,  901,  107,
    0, -141,  -83,    0, 1258, 1247,  -13,  -13, 1311, 1311,
  -83,    0,  -13,  -19,  -19,  -21,  -21,  -21,  -13,  -13,
  525, 1046,   71,   71,   29,   71,   29,   71,    0,   29,
    0,  913,   71,    0, -128,   71, -140,    0,    0, -137,
   71,  109,  110,  935,    0,  956, -108,    0,    0,    0,
 1046,  130,    0,   71,   71,  982,    0,    0,   71,   29,
    0,    0,  551,  -21,    0,  132,    0,   71,    0,   29,
  994,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  175,    0,   66,    0,    0,    0,    0,
   66,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  117,    0,    0,    0,  136,    0,  136,    0,    0,
    0,  137,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -57,    0,    0,    0,    0,    0,    0,  -55,    0,
    0,    0,    0,    0,    0,    0,    0,  -98,  -98,  -98,
  -41,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, 1081,  586,    0,    0,    0,
  -98,  -57,  -98, -114,  127,    0,    0,    0,    0,  -98,
    0,    0,  -98,    0,  159,  372,    0,    0,    0,  513,
    0,    0,    0,    0,    0,    0,  -98,  -98,  -98,  -98,
  -98,  -98,    0,  -98,  -98,  -98,  -98,  -98,  -98,  -98,
  -98,    0,  -98,  -98,   97,    0,    0,    0,    0,    0,
    0,    0,    0,  -98,   12,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  455,  154,  493, 1163,  843, 1367,
  -25,    0, 1339, 1107, 1188,  408,  432,  461, 1335, 1359,
    0,  -20,  -32,  -98,  -57,  -98,  -57,  -98,    0,  -57,
    0,    0,  -98,    0,    0,  -98,    0,    0,    0,  123,
  -98,    0,  153,    0,    0,    0,  -33,    0,    0,    0,
   16,    0,    0,  -98,  -98,    0,    0,    0,  -31,  -57,
    0,    0,    0,  485,    0,    0,    0,  -98,    0,  -57,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  193,   21,    8,   20,  188,  189,    0,  169,    0,
   14,    0,  -95,  -78,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   30, 1571,  527, 1135,    0,    0,  -56,
    0,   38,    0,
};
final static int YYTABLESIZE=1789;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        101,
   33,   47,   91,  103,   33,   33,  101,  161,   93,   47,
   42,  101,  179,  138,  110,  153,   93,  129,   91,    1,
   45,    6,  127,  129,  132,  101,  132,  128,  127,  125,
   69,  126,  132,  128,   26,   24,  184,   70,   45,  183,
    7,   30,   68,   39,    9,   39,   11,   26,   24,    2,
   12,   91,   95,   50,   13,   95,   94,  101,   49,   94,
   51,   69,   16,   29,  154,   31,  162,   91,   70,  133,
   36,  133,   38,   68,   37,   45,   47,  133,   69,  195,
   99,  197,   46,   48,  199,   70,   69,   90,   71,  101,
   68,  101,   91,   70,   92,  100,  188,  101,   68,   94,
  102,  103,  104,   69,  189,   17,   18,   19,   20,   21,
   70,  134,  135,  136,  217,   68,  143,  112,  113,   71,
   48,  114,   72,  115,  222,  116,  147,  144,  148,  150,
  216,  173,  174,   50,  176,  187,   71,   50,   50,   50,
   50,   50,   50,   50,   71,  181,   37,  186,  202,  207,
  204,   48,  205,  183,   50,   50,   50,   50,   50,   51,
  210,   71,   49,   51,   51,   51,   51,   51,   51,   51,
  212,   42,  220,  178,    1,    5,   20,   19,   49,   54,
   51,   51,   51,   51,   51,  102,   32,   50,   15,   50,
   35,   44,   65,   92,   69,   71,    8,   69,   14,   71,
   71,   71,   71,   71,   28,   71,   43,  198,    0,    0,
  193,   69,   69,   51,    0,   51,   71,   71,   71,   49,
   71,   49,    0,  101,  101,  101,  101,  101,  101,  101,
    0,  101,  101,  101,  101,   49,  101,  101,  101,  101,
  101,  101,  101,  101,   49,   49,   69,  101,    0,    0,
    0,   71,  101,  101,   53,   17,   18,   19,   20,   21,
   54,    0,   55,   56,   57,   58,    0,   59,   60,   61,
   62,   63,   64,   65,  123,  124,    0,    0,   66,    0,
    0,    0,    0,    0,   67,   53,   17,   18,   19,   20,
   21,   54,    0,   55,   56,   57,   58,    0,   59,   60,
   61,   62,   63,   64,   65,    0,    0,  107,   54,   66,
   55,    0,    0,    0,    0,   67,   54,   61,   55,   63,
   64,   65,   23,    0,    0,   61,   66,   63,   64,   65,
    0,    0,   67,   54,   66,   55,    0,    0,    0,    0,
   67,    0,   61,    0,   63,   64,   65,    0,    0,    0,
    0,   66,   17,   18,   19,   20,   21,   67,    0,    0,
    0,    0,    0,   50,   50,    0,    0,    0,    0,    0,
    0,   98,    0,    0,   50,   50,    0,    0,   50,   50,
   50,   50,    0,    0,   50,   50,    0,    0,    0,   51,
   51,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   51,   51,    0,    0,   51,   51,   51,   51,   72,    0,
   51,   51,   72,   72,   72,   72,   72,    0,   72,    0,
   69,   69,    0,    0,    0,   71,   71,    0,    0,   72,
   72,   72,   69,   72,    0,    0,   71,   71,    0,    0,
   71,   71,   71,   71,   59,    0,   71,   71,   59,   59,
   59,   59,   59,    0,   59,   17,   18,   19,   20,   21,
    0,    0,    0,    0,   72,   59,   59,   59,   60,   59,
    0,    0,   60,   60,   60,   60,   60,   22,   60,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   60,
   60,   60,    0,   60,    0,   68,    0,   61,   68,    0,
   59,   61,   61,   61,   61,   61,    0,   61,    0,    0,
    0,    0,   68,   68,    0,    0,    0,    0,   61,   61,
   61,   83,   61,    0,   60,   83,   83,   83,   83,   83,
    0,   83,    0,   66,    0,    0,   66,    0,    0,    0,
    0,    0,   83,   83,   83,    0,   83,   68,    0,   56,
   66,   66,    0,   61,   56,   56,   89,   56,   56,   56,
    0,  129,    0,    0,    0,    0,  127,  125,    0,  126,
  132,  128,   56,    0,   56,    0,    0,   83,   86,    0,
    0,    0,  191,    0,  131,   66,  130,  129,    0,    0,
    0,    0,  127,  125,    0,  126,  132,  128,    0,    0,
    0,    0,    0,   56,    0,   89,    0,    0,    0,    0,
  131,    0,  130,    0,    0,  133,    0,  190,   86,    0,
   34,    0,   55,    0,    0,    0,   46,   55,   55,    0,
   55,   55,   55,    0,    0,    0,    0,    0,   72,   72,
    0,  133,    0,  219,   46,   55,    0,   55,    0,   72,
   72,    0,    0,   72,   72,   72,   72,  129,    0,   72,
   72,  151,  127,  125,    0,  126,  132,  128,    0,    0,
    0,    0,    0,    0,   59,   59,   55,    0,    0,    0,
  131,    0,  130,    0,    0,   59,   59,    0,    0,   59,
   59,   59,   59,    0,    0,   59,   59,    0,   60,   60,
    0,   86,    0,   86,    0,    0,   86,    0,    0,   60,
   60,  133,    0,   60,   60,   60,   60,    0,    0,   60,
   60,   68,   68,    0,    0,    0,    0,   61,   61,    0,
    0,    0,   68,   68,    0,   86,   86,    0,   61,   61,
    0,    0,   61,   61,   61,   61,   86,    0,   61,   61,
    0,   83,   83,   17,   18,   19,   20,   21,    0,   66,
   66,    0,   83,   83,    0,    0,   83,   83,   83,   83,
   66,   66,   83,   83,    0,   22,   66,   66,    0,   56,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   56,   56,    0,    0,   56,   56,   56,   56,    0,    0,
   56,   56,  117,  118,    0,    0,  119,  120,  121,  122,
    0,    0,  123,  124,    0,    0,    0,  129,  218,    0,
    0,    0,  127,  125,    0,  126,  132,  128,  117,  118,
    0,    0,  119,  120,  121,  122,    0,    0,  123,  124,
  131,    0,  130,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  129,    0,    0,    0,  175,  127,  125,    0,
  126,  132,  128,   55,   55,    0,    0,   55,   55,   55,
   55,  133,    0,   55,   55,  131,    0,  130,    0,    0,
    0,    0,    0,   62,    0,    0,   62,  129,    0,    0,
    0,  177,  127,  125,    0,  126,  132,  128,  117,  118,
   62,   62,  119,  120,  121,  122,  133,    0,  123,  124,
  131,  129,  130,    0,    0,    0,  127,  125,    0,  126,
  132,  128,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  180,    0,  131,   62,  130,  129,    0,    0,
    0,  133,  127,  125,  185,  126,  132,  128,    0,  129,
    0,    0,    0,    0,  127,  125,    0,  126,  132,  128,
  131,    0,  130,    0,    0,  133,    0,    0,    0,    0,
    0,  129,  131,    0,  130,  208,  127,  125,    0,  126,
  132,  128,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  133,  129,    0,  131,    0,  130,  127,  125,    0,
  126,  132,  128,  133,    0,  200,    0,    0,    0,    0,
    0,    0,    0,    0,  209,  131,    0,  130,  129,    0,
    0,    0,    0,  127,  125,  133,  126,  132,  128,    0,
  129,    0,    0,    0,    0,  127,  125,    0,  126,  132,
  128,  131,    0,  130,    0,    0,  133,  152,    0,    0,
    0,    0,    0,  131,    0,  130,    0,    0,  117,  118,
    0,    0,  119,  120,  121,  122,    0,    0,  123,  124,
    0,    0,  133,    0,  215,    0,    0,    0,    0,    0,
    0,    0,  129,    0,  133,    0,  223,  127,  125,    0,
  126,  132,  128,  117,  118,    0,    0,  119,  120,  121,
  122,    0,    0,  123,  124,  131,    0,  130,    0,   62,
   62,    0,    0,    0,    0,    0,    0,   54,    0,    0,
   62,   62,   54,   54,    0,   54,   54,   54,  117,  118,
    0,    0,  119,  120,  121,  122,  133,    0,  123,  124,
   54,    0,   54,    0,    0,    0,    0,   57,    0,   57,
   57,   57,  117,  118,    0,    0,  119,  120,  121,  122,
    0,    0,  123,  124,   57,   57,   57,    0,   57,    0,
    0,   54,    0,    0,    0,    0,    0,    0,  117,  118,
    0,    0,  119,  120,  121,  122,   87,    0,  123,  124,
  117,  118,    0,    0,  119,  120,  121,  122,    0,   57,
  123,  124,    0,   67,    0,    0,   67,    0,    0,    0,
    0,    0,  117,  118,    0,    0,  119,  120,  121,  122,
   67,   67,  123,  124,    0,    0,   87,    0,   58,    0,
   58,   58,   58,  117,  118,    0,    0,  119,  120,  121,
  122,    0,    0,  123,  124,   58,   58,   58,    0,   58,
    0,    0,    0,    0,    0,   67,    0,    0,    0,  117,
  118,    0,    0,  119,  120,  121,  122,    0,    0,  123,
  124,  117,  118,    0,    0,  119,  120,  121,  122,    0,
   58,  123,  124,  129,    0,    0,    0,    0,  127,  125,
    0,  126,  132,  128,  129,    0,    0,    0,    0,  127,
  125,    0,  126,  132,  128,    0,  131,    0,  130,   87,
    0,   87,    0,    0,   87,    0,    0,  131,    0,  130,
    0,    0,    0,  117,  118,    0,    0,  119,  120,  121,
  122,    0,    0,  123,  124,    0,    0,  133,    0,    0,
    0,    0,    0,   87,   87,    0,    0,  129,  133,    0,
    0,    0,  127,  125,   87,  126,  132,  128,   54,   54,
    0,    0,   54,   54,   54,   54,    0,    0,   54,   54,
  131,    0,  130,   57,   57,   65,    0,    0,   65,   81,
    0,    0,   81,    0,   57,   57,    0,    0,   57,   57,
   57,   57,   65,   65,   57,   57,   81,   81,   81,   64,
   81,  133,   64,    0,    0,    0,    0,   63,    0,    0,
   63,    0,    0,    0,    0,    0,   64,   64,    0,    0,
    0,    0,    0,    0,   63,   63,    0,   65,    0,   67,
   67,   81,    0,    0,    0,    0,    0,    0,    0,    0,
   67,   67,    0,    0,    0,    0,   67,   67,    0,    0,
    0,   64,    0,    0,   58,   58,    0,    0,    0,   63,
    0,    0,    0,    0,    0,   58,   58,    0,    0,   58,
   58,   58,   58,    0,    0,   58,   58,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  117,    0,    0,    0,  119,  120,
  121,  122,    0,    0,  123,  124,    0,    0,    0,  119,
  120,  121,  122,    0,    0,  123,  124,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  119,  120,    0,    0,    0,    0,  123,  124,
    0,   65,   65,    0,    0,   81,   81,    0,    0,    0,
    0,    0,   65,   65,    0,    0,   81,   81,   65,   65,
   81,   81,   81,   81,    0,   64,   64,    0,    0,   95,
    0,    0,    0,   63,   63,    0,   64,   64,  105,  106,
  108,  109,   64,   64,   63,   63,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  137,    0,  139,  142,    0,    0,    0,    0,    0,
  145,    0,    0,  149,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  155,  156,  157,
  158,  159,  160,    0,  163,  164,  165,  166,  167,  168,
  169,  170,    0,  171,  172,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  182,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  145,  194,    0,  196,    0,  142,    0,
    0,    0,    0,  201,    0,    0,  203,    0,    0,    0,
    0,  206,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  213,  214,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  221,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   91,   59,   44,   59,   91,   91,   40,   91,   41,   41,
  125,   45,  125,   92,   71,   44,   40,   37,   44,  262,
   41,  277,   42,   37,   46,   59,   46,   47,   42,   43,
   33,   45,   46,   47,   15,   15,   41,   40,   59,   44,
  262,   22,   45,   36,  264,   38,  277,   28,   28,  292,
  277,   93,   41,   46,  123,   44,   41,   91,   45,   44,
   47,   33,  123,  277,   93,   59,  123,   93,   40,   91,
   40,   91,   40,   45,   93,   41,   41,   91,   33,  175,
   61,  177,   44,  123,  180,   40,   33,   40,   91,  123,
   45,  125,   40,   40,   40,   40,  153,   40,   45,  123,
   40,   40,  277,   33,  161,  258,  259,  260,  261,  262,
   40,   61,  277,  277,  210,   45,   40,   59,   59,   91,
  123,   59,  125,   59,  220,   59,   41,   91,   41,  277,
  209,   40,   44,   37,   59,  277,   91,   41,   42,   43,
   44,   45,   46,   47,   91,   41,   93,   41,  277,   41,
  291,  123,  290,   44,   58,   59,   60,   61,   62,   37,
  269,   91,  277,   41,   42,   43,   44,   45,   46,   47,
   41,  286,   41,  286,    0,   59,   41,   41,  277,  263,
   58,   59,   60,   61,   62,   59,  277,   91,  123,   93,
  277,  277,  276,   41,   41,   37,    4,   44,   11,   41,
   42,   43,   44,   45,   16,   47,   38,  178,   -1,   -1,
  173,   58,   59,   91,   -1,   93,   58,   59,   60,  277,
   62,  277,   -1,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,  277,  270,  271,  272,  273,
  274,  275,  276,  277,  277,  277,   93,  281,   -1,   -1,
   -1,   93,  286,  287,  257,  258,  259,  260,  261,  262,
  263,   -1,  265,  266,  267,  268,   -1,  270,  271,  272,
  273,  274,  275,  276,  288,  289,   -1,   -1,  281,   -1,
   -1,   -1,   -1,   -1,  287,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,  268,   -1,  270,  271,
  272,  273,  274,  275,  276,   -1,   -1,  262,  263,  281,
  265,   -1,   -1,   -1,   -1,  287,  263,  272,  265,  274,
  275,  276,  125,   -1,   -1,  272,  281,  274,  275,  276,
   -1,   -1,  287,  263,  281,  265,   -1,   -1,   -1,   -1,
  287,   -1,  272,   -1,  274,  275,  276,   -1,   -1,   -1,
   -1,  281,  258,  259,  260,  261,  262,  287,   -1,   -1,
   -1,   -1,   -1,  267,  268,   -1,   -1,   -1,   -1,   -1,
   -1,  277,   -1,   -1,  278,  279,   -1,   -1,  282,  283,
  284,  285,   -1,   -1,  288,  289,   -1,   -1,   -1,  267,
  268,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  278,  279,   -1,   -1,  282,  283,  284,  285,   37,   -1,
  288,  289,   41,   42,   43,   44,   45,   -1,   47,   -1,
  267,  268,   -1,   -1,   -1,  267,  268,   -1,   -1,   58,
   59,   60,  279,   62,   -1,   -1,  278,  279,   -1,   -1,
  282,  283,  284,  285,   37,   -1,  288,  289,   41,   42,
   43,   44,   45,   -1,   47,  258,  259,  260,  261,  262,
   -1,   -1,   -1,   -1,   93,   58,   59,   60,   37,   62,
   -1,   -1,   41,   42,   43,   44,   45,  280,   47,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   58,
   59,   60,   -1,   62,   -1,   41,   -1,   37,   44,   -1,
   93,   41,   42,   43,   44,   45,   -1,   47,   -1,   -1,
   -1,   -1,   58,   59,   -1,   -1,   -1,   -1,   58,   59,
   60,   37,   62,   -1,   93,   41,   42,   43,   44,   45,
   -1,   47,   -1,   41,   -1,   -1,   44,   -1,   -1,   -1,
   -1,   -1,   58,   59,   60,   -1,   62,   93,   -1,   37,
   58,   59,   -1,   93,   42,   43,   44,   45,   46,   47,
   -1,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   60,   -1,   62,   -1,   -1,   93,   52,   -1,
   -1,   -1,   58,   -1,   60,   93,   62,   37,   -1,   -1,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,
   -1,   -1,   -1,   91,   -1,   93,   -1,   -1,   -1,   -1,
   60,   -1,   62,   -1,   -1,   91,   -1,   93,   92,   -1,
  125,   -1,   37,   -1,   -1,   -1,   41,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   -1,   -1,   -1,  267,  268,
   -1,   91,   -1,   93,   59,   60,   -1,   62,   -1,  278,
  279,   -1,   -1,  282,  283,  284,  285,   37,   -1,  288,
  289,   41,   42,   43,   -1,   45,   46,   47,   -1,   -1,
   -1,   -1,   -1,   -1,  267,  268,   91,   -1,   -1,   -1,
   60,   -1,   62,   -1,   -1,  278,  279,   -1,   -1,  282,
  283,  284,  285,   -1,   -1,  288,  289,   -1,  267,  268,
   -1,  175,   -1,  177,   -1,   -1,  180,   -1,   -1,  278,
  279,   91,   -1,  282,  283,  284,  285,   -1,   -1,  288,
  289,  267,  268,   -1,   -1,   -1,   -1,  267,  268,   -1,
   -1,   -1,  278,  279,   -1,  209,  210,   -1,  278,  279,
   -1,   -1,  282,  283,  284,  285,  220,   -1,  288,  289,
   -1,  267,  268,  258,  259,  260,  261,  262,   -1,  267,
  268,   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,
  278,  279,  288,  289,   -1,  280,  284,  285,   -1,  267,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,   -1,
  288,  289,  278,  279,   -1,   -1,  282,  283,  284,  285,
   -1,   -1,  288,  289,   -1,   -1,   -1,   37,  268,   -1,
   -1,   -1,   42,   43,   -1,   45,   46,   47,  278,  279,
   -1,   -1,  282,  283,  284,  285,   -1,   -1,  288,  289,
   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   37,   -1,   -1,   -1,   41,   42,   43,   -1,
   45,   46,   47,  278,  279,   -1,   -1,  282,  283,  284,
  285,   91,   -1,  288,  289,   60,   -1,   62,   -1,   -1,
   -1,   -1,   -1,   41,   -1,   -1,   44,   37,   -1,   -1,
   -1,   41,   42,   43,   -1,   45,   46,   47,  278,  279,
   58,   59,  282,  283,  284,  285,   91,   -1,  288,  289,
   60,   37,   62,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   58,   -1,   60,   93,   62,   37,   -1,   -1,
   -1,   91,   42,   43,   44,   45,   46,   47,   -1,   37,
   -1,   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,
   60,   -1,   62,   -1,   -1,   91,   -1,   -1,   -1,   -1,
   -1,   37,   60,   -1,   62,   41,   42,   43,   -1,   45,
   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   91,   37,   -1,   60,   -1,   62,   42,   43,   -1,
   45,   46,   47,   91,   -1,   93,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   59,   60,   -1,   62,   37,   -1,
   -1,   -1,   -1,   42,   43,   91,   45,   46,   47,   -1,
   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,   46,
   47,   60,   -1,   62,   -1,   -1,   91,  267,   -1,   -1,
   -1,   -1,   -1,   60,   -1,   62,   -1,   -1,  278,  279,
   -1,   -1,  282,  283,  284,  285,   -1,   -1,  288,  289,
   -1,   -1,   91,   -1,   93,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   37,   -1,   91,   -1,   93,   42,   43,   -1,
   45,   46,   47,  278,  279,   -1,   -1,  282,  283,  284,
  285,   -1,   -1,  288,  289,   60,   -1,   62,   -1,  267,
  268,   -1,   -1,   -1,   -1,   -1,   -1,   37,   -1,   -1,
  278,  279,   42,   43,   -1,   45,   46,   47,  278,  279,
   -1,   -1,  282,  283,  284,  285,   91,   -1,  288,  289,
   60,   -1,   62,   -1,   -1,   -1,   -1,   41,   -1,   43,
   44,   45,  278,  279,   -1,   -1,  282,  283,  284,  285,
   -1,   -1,  288,  289,   58,   59,   60,   -1,   62,   -1,
   -1,   91,   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,
   -1,   -1,  282,  283,  284,  285,   52,   -1,  288,  289,
  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,   93,
  288,  289,   -1,   41,   -1,   -1,   44,   -1,   -1,   -1,
   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,
   58,   59,  288,  289,   -1,   -1,   92,   -1,   41,   -1,
   43,   44,   45,  278,  279,   -1,   -1,  282,  283,  284,
  285,   -1,   -1,  288,  289,   58,   59,   60,   -1,   62,
   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,   -1,  278,
  279,   -1,   -1,  282,  283,  284,  285,   -1,   -1,  288,
  289,  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,
   93,  288,  289,   37,   -1,   -1,   -1,   -1,   42,   43,
   -1,   45,   46,   47,   37,   -1,   -1,   -1,   -1,   42,
   43,   -1,   45,   46,   47,   -1,   60,   -1,   62,  175,
   -1,  177,   -1,   -1,  180,   -1,   -1,   60,   -1,   62,
   -1,   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,
  285,   -1,   -1,  288,  289,   -1,   -1,   91,   -1,   -1,
   -1,   -1,   -1,  209,  210,   -1,   -1,   37,   91,   -1,
   -1,   -1,   42,   43,  220,   45,   46,   47,  278,  279,
   -1,   -1,  282,  283,  284,  285,   -1,   -1,  288,  289,
   60,   -1,   62,  267,  268,   41,   -1,   -1,   44,   41,
   -1,   -1,   44,   -1,  278,  279,   -1,   -1,  282,  283,
  284,  285,   58,   59,  288,  289,   58,   59,   60,   41,
   62,   91,   44,   -1,   -1,   -1,   -1,   41,   -1,   -1,
   44,   -1,   -1,   -1,   -1,   -1,   58,   59,   -1,   -1,
   -1,   -1,   -1,   -1,   58,   59,   -1,   93,   -1,  267,
  268,   93,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  278,  279,   -1,   -1,   -1,   -1,  284,  285,   -1,   -1,
   -1,   93,   -1,   -1,  267,  268,   -1,   -1,   -1,   93,
   -1,   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,  282,
  283,  284,  285,   -1,   -1,  288,  289,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  278,   -1,   -1,   -1,  282,  283,
  284,  285,   -1,   -1,  288,  289,   -1,   -1,   -1,  282,
  283,  284,  285,   -1,   -1,  288,  289,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  282,  283,   -1,   -1,   -1,   -1,  288,  289,
   -1,  267,  268,   -1,   -1,  267,  268,   -1,   -1,   -1,
   -1,   -1,  278,  279,   -1,   -1,  278,  279,  284,  285,
  282,  283,  284,  285,   -1,  267,  268,   -1,   -1,   59,
   -1,   -1,   -1,  267,  268,   -1,  278,  279,   68,   69,
   70,   71,  284,  285,  278,  279,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   91,   -1,   93,   94,   -1,   -1,   -1,   -1,   -1,
  100,   -1,   -1,  103,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  117,  118,  119,
  120,  121,  122,   -1,  124,  125,  126,  127,  128,  129,
  130,  131,   -1,  133,  134,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  144,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  173,  174,   -1,  176,   -1,  178,   -1,
   -1,   -1,   -1,  183,   -1,   -1,  186,   -1,   -1,   -1,
   -1,  191,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  204,  205,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  218,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=294;
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
"IF_DIV","VAR","ARRAY_REPEAT","ARRAY_ADD","DEFAULT","IN","SEALED","UMINUS",
"EMPTY",
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
"Expr : Expr '[' Expr ']' DEFAULT Expr",
"Expr : '[' Expr FOR IDENTIFIER IN Expr ']'",
"Expr : '[' Expr FOR IDENTIFIER IN Expr IF Expr ']'",
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

//#line 523 "Parser.y"
    
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
//#line 784 "Parser.java"
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
//#line 60 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 66 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 70 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 80 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 86 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 90 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 94 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 98 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 102 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 11:
//#line 106 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 12:
//#line 112 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc,false);
					}
break;
case 13:
//#line 116 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc,true);
					}
break;
case 14:
//#line 122 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 15:
//#line 126 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 16:
//#line 132 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 17:
//#line 136 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 18:
//#line 140 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 20:
//#line 148 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 21:
//#line 155 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 22:
//#line 159 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 23:
//#line 166 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 170 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 176 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 26:
//#line 182 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 27:
//#line 186 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 28:
//#line 193 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 29:
//#line 198 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 39:
//#line 215 "Parser.y"
{
					yyval.stmt = new Tree.GuardedStmt(val_peek(1).elist, val_peek(1).loc);
				}
break;
case 40:
//#line 220 "Parser.y"
{
					yyval.elist.add(val_peek(0).expr);
				}
break;
case 41:
//#line 225 "Parser.y"
{
                    yyval = new SemValue();
                    yyval.elist = new ArrayList<Expr> ();
                    yyval.elist.add(val_peek(0).expr);
                }
break;
case 42:
//#line 231 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 43:
//#line 237 "Parser.y"
{
					yyval.expr = new Tree.IfBranch(val_peek(2).expr, val_peek(0).stmt, val_peek(2).loc);				
				}
break;
case 44:
//#line 242 "Parser.y"
{
					yyval.stmt = new Scopy(val_peek(3).ident,val_peek(1).expr,val_peek(5).loc);	
				}
break;
case 45:
//#line 248 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 46:
//#line 252 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 47:
//#line 256 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 49:
//#line 263 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 50:
//#line 269 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 51:
//#line 276 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 52:
//#line 280 "Parser.y"
{
                	yyval.lvalue = new Tree.Var(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
                }
break;
case 53:
//#line 289 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 54:
//#line 298 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 57:
//#line 304 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 308 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 312 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 316 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 320 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 324 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 328 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 332 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 336 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 340 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 344 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 68:
//#line 348 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 69:
//#line 352 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 70:
//#line 356 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 71:
//#line 360 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 72:
//#line 364 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 73:
//#line 368 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 74:
//#line 372 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 75:
//#line 376 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 76:
//#line 380 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 77:
//#line 384 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 78:
//#line 388 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 79:
//#line 392 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 80:
//#line 396 "Parser.y"
{
                		yyval.expr = new Tree.ArrayRepeat(val_peek(2).expr, val_peek(0).expr, val_peek(2).loc);
                	}
break;
case 81:
//#line 400 "Parser.y"
{
                		yyval.expr = new Tree.ArrayAdd(val_peek(2).expr, val_peek(0).expr, val_peek(2).loc);
	                }
break;
case 82:
//#line 404 "Parser.y"
{
                		yyval.expr = new Tree.subArray(val_peek(5).expr, val_peek(3).expr,val_peek(1).expr,val_peek(5).loc);
	            	}
break;
case 83:
//#line 408 "Parser.y"
{
                		yyval.expr = new Tree.ArrayDefault(val_peek(5).expr, val_peek(3).expr,val_peek(0).expr,val_peek(5).loc);
	            	}
break;
case 84:
//#line 412 "Parser.y"
{
                		yyval.expr = new Tree.ArrayPython(val_peek(5).expr, val_peek(3).ident,val_peek(1).expr,val_peek(5).loc);
	            	}
break;
case 85:
//#line 416 "Parser.y"
{
                		yyval.expr = new Tree.ArrayPython(val_peek(7).expr, val_peek(5).ident,val_peek(3).expr,val_peek(1).expr,val_peek(7).loc);
	            	}
break;
case 86:
//#line 422 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 87:
//#line 426 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 88:
//#line 430 "Parser.y"
{
				
						yyval.expr = new Tree.ListConst(val_peek(1).elist,  val_peek(1).loc);
				}
break;
case 89:
//#line 437 "Parser.y"
{
					yyval = new SemValue();
                    yyval.elist = new ArrayList<Expr> ();
                    yyval.elist.add(val_peek(0).expr);
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
					yyval = new SemValue();
				}
break;
case 93:
//#line 455 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 94:
//#line 462 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 95:
//#line 466 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 96:
//#line 473 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 97:
//#line 479 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 98:
//#line 485 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 99:
//#line 491 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 100:
//#line 497 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 101:
//#line 501 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 102:
//#line 507 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 103:
//#line 511 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 104:
//#line 517 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1487 "Parser.java"
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
