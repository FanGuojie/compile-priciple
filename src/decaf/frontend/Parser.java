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
public final static short UMINUS=286;
public final static short EMPTY=287;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    2,    6,    6,    7,    7,    7,    9,    9,   10,
   10,    8,    8,   11,   12,   12,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   21,   14,   14,   14,
   25,   25,   23,   23,   24,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   27,   27,   26,   26,   28,   28,   16,   17,   20,
   15,   29,   29,   18,   18,   19,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    2,
    3,    6,    2,    0,    2,    2,    0,    1,    0,    3,
    1,    7,    6,    3,    2,    0,    1,    2,    1,    1,
    1,    2,    2,    2,    1,    2,    6,    3,    1,    0,
    2,    0,    2,    4,    5,    1,    1,    1,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    2,    2,    3,    3,    1,    4,    5,    6,
    5,    1,    1,    1,    0,    3,    1,    5,    9,    1,
    6,    2,    0,    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   13,   17,
    0,    7,    8,    6,    9,    0,    0,   12,   15,    0,
    0,   16,   10,    0,    4,    0,    0,    0,    0,   11,
    0,   21,    0,    0,    0,    0,    5,    0,    0,    0,
   26,   23,   20,   22,    0,    0,   73,   67,    0,    0,
    0,    0,   80,    0,    0,    0,    0,   72,    0,    0,
    0,    0,   24,   27,   35,   25,    0,   29,   30,   31,
    0,    0,    0,    0,    0,    0,    0,    0,   48,    0,
    0,    0,    0,    0,   46,   47,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   28,   32,   33,   34,
   36,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   41,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   65,   66,    0,    0,
   62,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   68,    0,    0,   86,    0,    0,   44,    0,    0,
    0,   78,    0,    0,   69,    0,    0,   71,   45,   37,
    0,    0,   81,   70,    0,   82,    0,   79,
};
final static short yydgoto[] = {                          2,
    3,    4,   64,   20,   33,    8,   11,   22,   34,   35,
   65,   45,   66,   67,   68,   69,   70,   71,   72,   73,
   74,   75,   85,   86,   78,  159,   79,  126,  173,
};
final static short yysindex[] = {                      -231,
 -238,    0, -231,    0, -224,    0, -235,  -79,    0,    0,
  261,    0,    0,    0,    0, -232, -206,    0,    0,  -12,
  -87,    0,    0,  -86,    0,    9,  -35,   19, -206,    0,
 -206,    0,  -85,   22,   29,   33,    0,  -45, -206,  -45,
    0,    0,    0,    0,   -8,   46,    0,    0,   47,   48,
   49,  470,    0,  417,   51,   59,   66,    0,   68,  470,
  470,  369,    0,    0,    0,    0,   41,    0,    0,    0,
   50,   52,   53,   54,  492,   57,    0, -167,    0, -156,
  470,  470,  470,  492,    0,    0,   84,   35,  470,   91,
   93,  470,  -43,  -43, -142,  286,    0,    0,    0,    0,
    0,  470,  470,  470,  470,  470,  470,  470,  470,  470,
  470,  470,  470,  470,    0,  470,  470,   96,   94,  312,
   78,  378,   98,  377,  492,  -24,    0,    0,  402,  100,
    0,  563,  539,  338,  338,  575,  575,  -19,  -19,  -43,
  -43,  -43,  338,  338,  413,  492,  470,  470,   17,  470,
   17,    0,  434,  470,    0, -135,  470,    0,  102,  103,
  445,    0,  471, -125,    0,  492,  108,    0,    0,    0,
  470,   17,    0,    0,  110,    0,   17,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  161,    0,   39,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  109,    0,    0,  111,    0,
  111,    0,    0,    0,  129,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -58,    0,    0,    0,    0,    0,
    0,  -57,    0,    0,    0,    0,    0,    0,    0, -106,
 -106, -106,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  513,   34,    0,    0,    0,
 -106,  -58, -106,  115,    0,    0,    0,    0, -106,    0,
    0, -106,   60,   86,    0,    0,    0,    0,    0,    0,
    0, -106, -106, -106, -106, -106, -106, -106, -106, -106,
 -106, -106, -106, -106,    0, -106, -106,   23,    0,    0,
    0,    0,    0, -106,  -11,    0,    0,    0,    0,    0,
    0,  163,    2,  629,  666,  335,  769,  719,  741,  113,
  122,  152,  749,  761,    0,  -25,  -31, -106,  -58, -106,
  -58,    0,    0, -106,    0,    0, -106,    0,    0,  135,
    0,    0,    0,  -33,    0,   -6,    0,    0,    0,    0,
  -30,  -58,    0,    0,    0,    0,  -58,    0,
};
final static short yygindex[] = {                         0,
    0,  174,  167,  -10,   -3,    0,    0,    0,  149,    0,
  -16,    0, -136,  -73,    0,    0,    0,    0,    0,    0,
    0,  778,  497,  655,    0,    0,    0,   36,    0,
};
final static int YYTABLESIZE=1048;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         83,
   40,   85,  115,   27,   27,   27,   83,   21,  121,   75,
   40,   83,  162,   24,  164,   38,  155,  112,   32,  154,
   32,   42,  110,   44,   61,   83,  115,  111,   43,   77,
    1,   62,   77,   38,   76,  176,   60,   76,    5,    7,
  178,    9,   61,   10,   23,   61,   25,  116,   29,   61,
   88,   12,   13,   14,   15,   16,   62,   30,   31,   43,
   61,   60,   38,   43,   43,   43,   43,   43,   43,   43,
   47,  116,   39,   40,   39,   47,   47,   41,   47,   47,
   47,   43,   43,   43,   43,   80,   81,   82,   83,   83,
   89,   83,   39,   47,   61,   47,   63,  175,   90,   97,
   63,   63,   63,   63,   63,   91,   63,   92,   98,  118,
   99,  100,  101,   43,   41,   43,   63,  117,   63,   63,
  119,   63,   64,  123,   47,  124,   64,   64,   64,   64,
   64,  127,   64,  128,  130,  147,  150,  148,  152,   41,
  157,  167,  169,  172,   64,   64,  154,   64,  174,   51,
  177,   19,   63,   51,   51,   51,   51,   51,   52,   51,
    1,   14,   52,   52,   52,   52,   52,    5,   52,   18,
   42,   51,   51,   84,   51,   74,    6,   19,   64,   36,
   52,   52,  160,   52,    0,    0,    0,    0,   53,   26,
   28,   37,   53,   53,   53,   53,   53,    0,   53,    0,
    0,    0,    0,   60,    0,   51,   60,    0,    0,    0,
   53,   53,    0,   53,   52,    0,    0,    0,   42,   42,
    0,   60,    0,   83,   83,   83,   83,   83,   83,   83,
    0,   83,   83,   83,   83,    0,   83,   83,   83,   83,
   83,   83,   83,   83,   53,   42,   42,   83,   46,   12,
   13,   14,   15,   16,   47,   60,   48,   49,   50,   51,
    0,   52,   53,   54,   55,   56,   57,   58,    0,    0,
    0,    0,   59,   46,   12,   13,   14,   15,   16,   47,
   61,   48,   49,   50,   51,    0,   52,   53,   54,   55,
   56,   57,   58,    0,    0,    0,    0,   59,    0,    0,
   43,   43,    0,    0,   43,   43,   43,   43,    0,    0,
    0,   47,   47,    0,    0,   47,   47,   47,   47,    0,
    0,    0,  112,    0,    0,    0,  131,  110,  108,    0,
  109,  115,  111,    0,    0,    0,    0,   63,   63,    0,
    0,   63,   63,   63,   63,  114,    0,  113,  112,    0,
    0,    0,  149,  110,  108,    0,  109,  115,  111,    0,
    0,    0,    0,   64,   64,    0,    0,   64,   64,   64,
   64,  114,    0,  113,  112,   54,  116,    0,   54,  110,
  108,    0,  109,  115,  111,   18,    0,    0,    0,    0,
   51,   51,    0,   54,   51,   51,   51,   51,    0,   52,
   52,   61,  116,   52,   52,   52,   52,    0,   62,   61,
    0,    0,    0,   60,  112,    0,   62,    0,  151,  110,
  108,   60,  109,  115,  111,    0,    0,   54,  116,   53,
   53,    0,    0,   53,   53,   53,   53,  114,  112,  113,
   60,   60,    0,  110,  108,  156,  109,  115,  111,  112,
    0,    0,    0,    0,  110,  108,    0,  109,  115,  111,
    0,  114,    0,  113,    0,    0,    0,    0,  116,   30,
  112,    0,  114,    0,  113,  110,  108,    0,  109,  115,
  111,  112,    0,    0,    0,  170,  110,  108,    0,  109,
  115,  111,  116,  114,    0,  113,    0,    0,    0,    0,
    0,    0,   61,  116,  114,  158,  113,  112,    0,   62,
    0,    0,  110,  108,   60,  109,  115,  111,   12,   13,
   14,   15,   16,    0,  116,    0,  165,    0,  112,  171,
  114,    0,  113,  110,  108,  116,  109,  115,  111,    0,
   17,   76,    0,    0,    0,    0,    0,    0,    0,   46,
    0,  114,    0,  113,   46,   46,    0,   46,   46,   46,
    0,  116,    0,  102,  103,    0,    0,  104,  105,  106,
  107,    0,   46,    0,   46,  112,    0,    0,   76,    0,
  110,  108,  116,  109,  115,  111,    0,    0,    0,  102,
  103,    0,    0,  104,  105,  106,  107,    0,  114,  112,
  113,    0,    0,   46,  110,  108,    0,  109,  115,  111,
    0,  112,   54,   54,    0,    0,  110,  108,    0,  109,
  115,  111,  114,    0,  113,    0,    0,    0,    0,  116,
   95,   47,    0,   48,  114,    0,  113,    0,    0,   47,
   54,   48,   56,   57,   58,   76,    0,   76,   54,   59,
   56,   57,   58,  116,    0,  102,  103,   59,    0,  104,
  105,  106,  107,    0,    0,  116,    0,   76,   76,   58,
    0,    0,   58,   76,   12,   13,   14,   15,   16,  102,
  103,    0,    0,  104,  105,  106,  107,   58,    0,    0,
  102,  103,    0,   87,  104,  105,  106,  107,    0,   77,
    0,    0,    0,    0,    0,    0,   59,    0,    0,   59,
    0,  102,  103,    0,    0,  104,  105,  106,  107,    0,
    0,   58,  102,  103,   59,    0,  104,  105,  106,  107,
    0,    0,   47,    0,   48,    0,   77,    0,    0,    0,
    0,   54,    0,   56,   57,   58,    0,    0,  102,  103,
   59,    0,  104,  105,  106,  107,    0,    0,   59,   49,
    0,   49,   49,   49,    0,    0,    0,    0,    0,  102,
  103,    0,    0,  104,  105,  106,  107,   49,   49,    0,
   49,   50,    0,   50,   50,   50,    0,    0,    0,   57,
   46,   46,   57,    0,   46,   46,   46,   46,    0,   50,
   50,   56,   50,   77,   56,   77,    0,   57,    0,   55,
    0,   49,   55,    0,    0,    0,  102,    0,    0,   56,
  104,  105,  106,  107,    0,   77,   77,   55,    0,   84,
    0,   77,    0,   50,    0,    0,    0,   93,   94,   96,
    0,   57,    0,    0,  104,  105,  106,  107,    0,    0,
    0,    0,    0,   56,    0,    0,  104,  105,  120,    0,
  122,   55,    0,    0,    0,    0,  125,    0,    0,  129,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  132,
  133,  134,  135,  136,  137,  138,  139,  140,  141,  142,
  143,  144,    0,  145,  146,    0,    0,    0,    0,    0,
    0,  153,    0,    0,    0,    0,   58,   58,    0,    0,
    0,    0,   58,   58,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  125,  161,    0,  163,    0,    0,
    0,  166,    0,    0,  168,    0,    0,    0,    0,    0,
    0,    0,    0,   59,   59,    0,    0,    0,    0,   59,
   59,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   49,   49,    0,    0,
   49,   49,   49,   49,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   50,   50,
    0,    0,   50,   50,   50,   50,   57,   57,    0,    0,
    0,    0,   57,   57,    0,    0,    0,    0,   56,   56,
    0,    0,    0,    0,   56,   56,   55,   55,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   59,   46,   91,   91,   91,   40,   11,   82,   41,
   41,   45,  149,   17,  151,   41,   41,   37,   29,   44,
   31,   38,   42,   40,   33,   59,   46,   47,   39,   41,
  262,   40,   44,   59,   41,  172,   45,   44,  277,  264,
  177,  277,   41,  123,  277,   44,   59,   91,   40,   33,
   54,  258,  259,  260,  261,  262,   40,   93,   40,   37,
   59,   45,   41,   41,   42,   43,   44,   45,   46,   47,
   37,   91,   44,   41,   41,   42,   43,  123,   45,   46,
   47,   59,   60,   61,   62,   40,   40,   40,   40,  123,
   40,  125,   59,   60,   93,   62,   37,  171,   40,   59,
   41,   42,   43,   44,   45,   40,   47,   40,   59,  277,
   59,   59,   59,   91,  123,   93,  125,   61,   59,   60,
  277,   62,   37,   40,   91,   91,   41,   42,   43,   44,
   45,   41,   47,   41,  277,   40,   59,   44,   41,  123,
   41,  277,   41,  269,   59,   60,   44,   62,   41,   37,
   41,   41,   93,   41,   42,   43,   44,   45,   37,   47,
    0,  123,   41,   42,   43,   44,   45,   59,   47,   41,
  277,   59,   60,   59,   62,   41,    3,   11,   93,   31,
   59,   60,  147,   62,   -1,   -1,   -1,   -1,   37,  277,
  277,  277,   41,   42,   43,   44,   45,   -1,   47,   -1,
   -1,   -1,   -1,   41,   -1,   93,   44,   -1,   -1,   -1,
   59,   60,   -1,   62,   93,   -1,   -1,   -1,  277,  277,
   -1,   59,   -1,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,   -1,  270,  271,  272,  273,
  274,  275,  276,  277,   93,  277,  277,  281,  257,  258,
  259,  260,  261,  262,  263,   93,  265,  266,  267,  268,
   -1,  270,  271,  272,  273,  274,  275,  276,   -1,   -1,
   -1,   -1,  281,  257,  258,  259,  260,  261,  262,  263,
  279,  265,  266,  267,  268,   -1,  270,  271,  272,  273,
  274,  275,  276,   -1,   -1,   -1,   -1,  281,   -1,   -1,
  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,   -1,
   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,
   -1,   -1,   37,   -1,   -1,   -1,   41,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   -1,   -1,  278,  279,   -1,
   -1,  282,  283,  284,  285,   60,   -1,   62,   37,   -1,
   -1,   -1,   41,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,
  285,   60,   -1,   62,   37,   41,   91,   -1,   44,   42,
   43,   -1,   45,   46,   47,  125,   -1,   -1,   -1,   -1,
  278,  279,   -1,   59,  282,  283,  284,  285,   -1,  278,
  279,   33,   91,  282,  283,  284,  285,   -1,   40,   33,
   -1,   -1,   -1,   45,   37,   -1,   40,   -1,   41,   42,
   43,   45,   45,   46,   47,   -1,   -1,   93,   91,  278,
  279,   -1,   -1,  282,  283,  284,  285,   60,   37,   62,
  278,  279,   -1,   42,   43,   44,   45,   46,   47,   37,
   -1,   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,
   -1,   60,   -1,   62,   -1,   -1,   -1,   -1,   91,   93,
   37,   -1,   60,   -1,   62,   42,   43,   -1,   45,   46,
   47,   37,   -1,   -1,   -1,   41,   42,   43,   -1,   45,
   46,   47,   91,   60,   -1,   62,   -1,   -1,   -1,   -1,
   -1,   -1,   33,   91,   60,   93,   62,   37,   -1,   40,
   -1,   -1,   42,   43,   45,   45,   46,   47,  258,  259,
  260,  261,  262,   -1,   91,   -1,   93,   -1,   37,   59,
   60,   -1,   62,   42,   43,   91,   45,   46,   47,   -1,
  280,   45,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   37,
   -1,   60,   -1,   62,   42,   43,   -1,   45,   46,   47,
   -1,   91,   -1,  278,  279,   -1,   -1,  282,  283,  284,
  285,   -1,   60,   -1,   62,   37,   -1,   -1,   82,   -1,
   42,   43,   91,   45,   46,   47,   -1,   -1,   -1,  278,
  279,   -1,   -1,  282,  283,  284,  285,   -1,   60,   37,
   62,   -1,   -1,   91,   42,   43,   -1,   45,   46,   47,
   -1,   37,  278,  279,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   60,   -1,   62,   -1,   -1,   -1,   -1,   91,
  262,  263,   -1,  265,   60,   -1,   62,   -1,   -1,  263,
  272,  265,  274,  275,  276,  149,   -1,  151,  272,  281,
  274,  275,  276,   91,   -1,  278,  279,  281,   -1,  282,
  283,  284,  285,   -1,   -1,   91,   -1,  171,  172,   41,
   -1,   -1,   44,  177,  258,  259,  260,  261,  262,  278,
  279,   -1,   -1,  282,  283,  284,  285,   59,   -1,   -1,
  278,  279,   -1,  277,  282,  283,  284,  285,   -1,   45,
   -1,   -1,   -1,   -1,   -1,   -1,   41,   -1,   -1,   44,
   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,
   -1,   93,  278,  279,   59,   -1,  282,  283,  284,  285,
   -1,   -1,  263,   -1,  265,   -1,   82,   -1,   -1,   -1,
   -1,  272,   -1,  274,  275,  276,   -1,   -1,  278,  279,
  281,   -1,  282,  283,  284,  285,   -1,   -1,   93,   41,
   -1,   43,   44,   45,   -1,   -1,   -1,   -1,   -1,  278,
  279,   -1,   -1,  282,  283,  284,  285,   59,   60,   -1,
   62,   41,   -1,   43,   44,   45,   -1,   -1,   -1,   41,
  278,  279,   44,   -1,  282,  283,  284,  285,   -1,   59,
   60,   41,   62,  149,   44,  151,   -1,   59,   -1,   41,
   -1,   93,   44,   -1,   -1,   -1,  278,   -1,   -1,   59,
  282,  283,  284,  285,   -1,  171,  172,   59,   -1,   52,
   -1,  177,   -1,   93,   -1,   -1,   -1,   60,   61,   62,
   -1,   93,   -1,   -1,  282,  283,  284,  285,   -1,   -1,
   -1,   -1,   -1,   93,   -1,   -1,  282,  283,   81,   -1,
   83,   93,   -1,   -1,   -1,   -1,   89,   -1,   -1,   92,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  102,
  103,  104,  105,  106,  107,  108,  109,  110,  111,  112,
  113,  114,   -1,  116,  117,   -1,   -1,   -1,   -1,   -1,
   -1,  124,   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,
   -1,   -1,  284,  285,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  147,  148,   -1,  150,   -1,   -1,
   -1,  154,   -1,   -1,  157,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  278,  279,   -1,   -1,   -1,   -1,  284,
  285,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,
  282,  283,  284,  285,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,
   -1,   -1,  282,  283,  284,  285,  278,  279,   -1,   -1,
   -1,   -1,  284,  285,   -1,   -1,   -1,   -1,  278,  279,
   -1,   -1,   -1,   -1,  284,  285,  278,  279,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=287;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,
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
"UMINUS","EMPTY",
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

//#line 427 "Parser.y"
    
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
//#line 589 "Parser.java"
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
//#line 53 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 59 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 63 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 73 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 79 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 83 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 87 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 91 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 95 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 11:
//#line 99 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 12:
//#line 105 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 13:
//#line 111 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 14:
//#line 115 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 15:
//#line 121 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 16:
//#line 125 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 17:
//#line 129 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 19:
//#line 137 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 20:
//#line 144 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 21:
//#line 148 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 22:
//#line 155 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 23:
//#line 159 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 165 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 25:
//#line 171 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 26:
//#line 175 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 27:
//#line 182 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 28:
//#line 187 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 38:
//#line 205 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 39:
//#line 209 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 40:
//#line 213 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 42:
//#line 220 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 43:
//#line 226 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 44:
//#line 233 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 45:
//#line 239 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 46:
//#line 248 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 49:
//#line 254 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 50:
//#line 258 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 51:
//#line 262 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 52:
//#line 266 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 53:
//#line 270 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 54:
//#line 274 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 55:
//#line 278 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 282 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 286 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 290 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 294 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 298 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 302 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 306 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 63:
//#line 310 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 314 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 318 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 66:
//#line 322 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 67:
//#line 326 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 68:
//#line 330 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 69:
//#line 334 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 70:
//#line 338 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 71:
//#line 342 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 72:
//#line 348 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 73:
//#line 352 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 75:
//#line 359 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 76:
//#line 366 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 77:
//#line 370 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 78:
//#line 377 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 79:
//#line 383 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 80:
//#line 389 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 81:
//#line 395 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 82:
//#line 401 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 83:
//#line 405 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 84:
//#line 411 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 85:
//#line 415 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 86:
//#line 421 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1176 "Parser.java"
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
