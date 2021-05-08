/*
  Defines the state of a ttt game and its
  manipulation
 */
public class tictacboard implements boardtype
{
  public static final int NO_WIN=0;
  public static final int DRAW=3;
  protected int tictacboard[]=new int[9];
  public tictacboard()
  {
    for(int i=0;i<9;i++)
      tictacboard[i]=0;
  }
  public tictacboard(tictacboard board)
  {
    for(int i=0;i<9;i++)
      tictacboard[i]=board.tictacboard[i];
  }
  public void printboard()
  {
    System.out.println("\n");
    for(int i=0;i<9;i++)
      {
	System.out.print((i%3)==0?"\n":"");
	System.out.print(tictacboard[i]);
      }
    System.out.println("\n");
  }
  public int win()
  {
    //horizontal or vertical win
    for(int j=-1;j<2;j+=2)
      {
	for(int i=0;i<3;i++)
	  if(( (tictacboard[i*3]==j) &&  //horiz
	       (tictacboard[i*3+1]==j) && 
	       (tictacboard[i*3+2]==j) ) 
	     || 
	     ( (tictacboard[i]==j) &&   //vert
	       (tictacboard[i+3]==j) &&
	       (tictacboard[i+6]==j)
	       ) )
	  return j;
	if(( (tictacboard[0]==j) &&  //diag
	     (tictacboard[4]==j) &&
	     (tictacboard[8]==j))
	   ||
	   ( (tictacboard[2]==j) &&
	     (tictacboard[4]==j) &&
	     (tictacboard[6]==j)))
	  return j;
      }

    int i;
    for(i=0;i<9;i++)
      if(tictacboard[i]==0) break;
    if(i==9) return DRAW;

    return NO_WIN;
  }
  public int evaluate_position()
  {
    int score=tictacboard[4]*3;
    score+=tictacboard[0]+tictacboard[2]+tictacboard[6]+tictacboard[8];
    return score;
  }
  public movetype[] generate_moves(int player)
  {
    movetype moves[]=new movetype[9];
    int ct=0;
    for(int i=0;i<9;i++)
      if(tictacboard[i]==0)
	  moves[ct++]=new tictacmovetype(i,player);
    movetype rmoves[]=new movetype[ct];
    for(int i=0;i<ct;i++)
      rmoves[i]=moves[i];
    return rmoves;
  }
  public boardtype clone_board()
  {
    return new tictacboard(this);
  }
  public void make_move(movetype move)
  {
    tictacmovetype m=(tictacmovetype)move;
    tictacboard[m.move()]=m.player();
  }
  public void unmake_move(movetype move)
  {
    tictacmovetype m=(tictacmovetype)move;
    tictacboard[m.move()]=0;
  }
}

