import java.awt.*;

/*
   chess2 provides a graphical interface for playing chess
   starting from a command prompt
 */
public class chess
{
	static chessgui cg=null;
	public static void main(String[] args)
	{
		newgame();
	}
	public static void newgame()
	{
		if(cg!=null)
		    cg.dispose();
		chessboard board=new chessboard();
		cg=new chessgui(board);
		Insets insets=cg.getInsets();
		cg.setSize(cg.width()+insets.left+insets.right,
			   cg.height()+insets.top+insets.bottom);
		cg.show();
	}
}
