import java.util.*;

/*
  Starts up multiple concurrent searchthreads which together
  perform a tree search using the minimax algorithm in order
  to pick the best move.
 */
public class minimax implements DecisionMaker 
{
  public static final int COMPUTER=-1;
  public static final int PLAYER=1;
  protected movetype moves[];
  protected searchthread threads[];
  protected boardtype searchboard;
  public int ThreadMutex;
  public Object mutex=new Object();
  
  public synchronized movetype makedecision(boardtype board)
  {
    moves=board.generate_moves(COMPUTER);
    threads=new searchthread[moves.length];

    for(int i=0;i<moves.length;i++)
      {
	searchboard=board.clone_board();
	searchboard.make_move(moves[i]);
	threads[i]=new searchthread(searchboard,this);
	threads[i].start();
      }

	for(int i=0;i<moves.length;i++)
	  {
		try
		  {
			threads[i].join();
		  }
		catch(InterruptedException ex)
		  {
			System.out.println("Join Exception");
			for(int j=0;j<moves.length;j++)
			  threads[j].stop();
			return null;
		  }
	  }
	int bestscore=Integer.MAX_VALUE;
	int bestmoveindex=0;
	for(int i=0;i<moves.length;i++)
	    {
		if(threads[i].resultscore()<bestscore)
		    {
			bestscore=threads[i].resultscore();
			bestmoveindex=i;
		    }
	    }
	return moves[bestmoveindex];
  }
}
