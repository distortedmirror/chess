import java.io.*;

/*
  A console text based interface to TTT
 */
public class tictactoetext
{
  public static void main(String[] args)
  {
    minimax tictacminimax;
    tictacboard board;
    tictacmovetype move;
    String input=null;
    tictacmovetype playersmove;
    DataInputStream myin=new DataInputStream(System.in);
    int mv=0;

    board=new tictacboard();
    tictacminimax=new minimax();

    while(board.win()==tictacboard.NO_WIN)
    {
      board.printboard();
      do
	{
	  System.out.println("Enter Move: ");
	  try
	    {
	      input=myin.readLine();
	    }
	  catch(IOException ioe)
	    {
	      System.err.println("IO Error");
	      System.exit(1);
	    }
	  try
	    {
	      mv=Integer.parseInt(input);
	    }
	  catch(NumberFormatException nfe)
	    {
	      continue;
	    }
	}
      while(false);
      
      playersmove=new tictacmovetype(mv,tictacmovetype.XPLAYER);
      playersmove.make_move(board);
      board.printboard();
      if(board.win()!=tictacboard.NO_WIN) break;
      move=(tictacmovetype)tictacminimax.makedecision(board);
      move.make_move(board);
    }

  }
}
