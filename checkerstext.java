import java.io.*;

/*
  A text based interfact to checkers
 */
public class checkerstext
{
    public static void main(String[] args)
    {
	DataInputStream myin=new DataInputStream(System.in);
	minimax checkersminimax=new minimax();
	checkersboard board=new checkersboard();
	String input=null;
	checkersmovetype move=null;
	checkersmovetype playersmove=null;
	int mv=0;
	
	while(board.win()==checkersboard.NO_WIN)
	    {
		board.printboard();
		do
		    {
			System.out.println("Enter Move: ");
			try
			    {
				input=myin.readLine();
				if(input.equals("quit")) System.exit(0);
			    }
			catch(IOException ioe)
			    {
				System.err.println("IO Error");
				System.exit(1);
			    }
			if(input.length()<4) continue;
			try
			    {
				mv=Integer.parseInt(input);
			    }
			catch(NumberFormatException nfe)
			    {
				continue;
			    }				
			
			try
			    {
				playersmove=new checkersmovetype(
								 Integer.valueOf(input.substring(0,1)).intValue(),
								 Integer.valueOf(input.substring(1,2)).intValue(),
								 Integer.valueOf(input.substring(2,3)).intValue(),
								 Integer.valueOf(input.substring(3,4)).intValue() );
				movetype[] moves;
				//need to use the generated move
				moves=board.generate_moves(checkersboard.BLACK);
				boolean legalmove=false;
				for(int x=0;x<moves.length;x++)
				    {
					checkersmovetype mxv=(checkersmovetype)moves[x];
					System.out.println("mv:"+mxv.movefromy()+mxv.movefromx()+mxv.movetoy()+mxv.movetox());
					if(moves[x].equals(playersmove))
					    {
						playersmove=(checkersmovetype)moves[x];
						legalmove=true;
						break;
					    }
				    }
				if(legalmove)
				    break; 
			    }
			catch(StringIndexOutOfBoundsException sioobe)
			    {
				System.err.println("This shouldn't happen");
				System.exit(1);
			    }
		    }while(true);
		playersmove.make_move(board);
		board.printboard();
		if(board.win()!=checkersboard.NO_WIN) break;
		move=(checkersmovetype)checkersminimax.makedecision(board);
		move.make_move(board);
	    }
	
    }
}
