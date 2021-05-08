import java.io.*;

/*
   This class provides a console based interface for playing chess
 */
public class chesstext
{
	public static void main(String[] args)
	{
		DataInputStream myin=new DataInputStream(System.in);
		minimax chessminimax=new minimax();
		chessboard board=new chessboard();
		String input=null;
		chessmovetype move=null;
		chessmovetype playersmove=null;
		int mv=0;

		while(board.win()==chessboard.NO_WIN)
		{
			board.printboard();
			do
			{
				System.out.println("Enter Move: ");
				try
				{
					input=myin.readLine();
					if(input.equals("666")) System.exit(0);
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
			}while(false);
			try
			{
				playersmove=new chessmovetype(
						Integer.valueOf(input.substring(0,1)).intValue(),
						Integer.valueOf(input.substring(1,2)).intValue(),
						Integer.valueOf(input.substring(2,3)).intValue(),
						Integer.valueOf(input.substring(3,4)).intValue() );
											
			}
			catch(StringIndexOutOfBoundsException sioobe)
			{
				System.err.println("This shouldn't happen");
				System.exit(1);
			}
			playersmove.make_move(board);
			board.printboard();
			if(board.win()!=chessboard.NO_WIN) break;
			move=(chessmovetype)chessminimax.makedecision(board);
			move.make_move(board);
		}
		
	}
}
