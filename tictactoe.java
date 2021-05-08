/*
  A graphical interface to TTT
 */
public class tictactoe
{
	static tictactoegui cg=null;
	public static void main(String[] args)
	{
		newgame();
	}
	public static void newgame()
	{
		if(cg!=null)
			cg.dispose();
		tictacboard board=new tictacboard();
		cg=new tictactoegui(board);
		cg.resize(cg.width(),cg.height()+8);
		cg.show();
	}
}


