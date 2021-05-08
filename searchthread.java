import java.util.*;

/*
  The thread that implements the minimax algorithm which
  is used to do a tree search for the best move.  
 */
public class searchthread extends Thread 
{
  protected static int SEARCHPLY=5;
  protected int ply;
  protected int player;
  protected int resultscore;
  protected boardtype board;
  protected movetype searchmoves[];
  protected minimax dmaker;

  protected searchthread(boardtype board, minimax dm)
  {
    ply=1;// this is set to 1 because DecisionMaker handles 0
    player=-1;
    searchmoves=new movetype[SEARCHPLY];
	dmaker=dm;
    this.board=board;
  }

  public int resultscore()
  {
    return resultscore;
  }

  public void run()
  {
	resultscore=search();
  }

  int search()
  {
    int score,highscore;
    movetype moves[];
    ++ply; player*=-1;
    highscore=(player>0)?Integer.MIN_VALUE:Integer.MAX_VALUE;
    
    if(ply<SEARCHPLY)
    {
	moves=board.generate_moves(player);
	for(int ct=0;ct<moves.length;ct++)
	  {
	    board.make_move(moves[ct]);
	    if((score=board.win())!=boardtype.NO_WIN)
		{
		    if(score==boardtype.DRAW)
			score=0;
		    else
			score=score-ply; 
		}
	    else
	      score=search();
	    
	    if(player==1)
	    {
		if(score>highscore)
		  {
		    highscore=score;
		    searchmoves[ply-1]=moves[ct];
		  }
	    }
	    else
	    {
		if(score<highscore)
		  {
		    highscore=score;
		    searchmoves[ply-1]=moves[ct];
		  }
	    }
	    board.unmake_move(moves[ct]);
	  }
    }
    else
    {
	--ply;
	player*=-1;
	return board.evaluate_position();
    }
    --ply;
    player*=-1;
    return highscore;
  }
}
