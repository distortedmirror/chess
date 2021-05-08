import java.applet.*;
import java.awt.*;

/*
  An graphical applet based Tic tac toe game
 */
public class tictactoeapplet extends Applet
{
    tictactoegui cg=null;

    public void init()
    {
	if(cg!=null)
	    cg.dispose();
	tictacboard board=new tictacboard();
	cg=new tictactoegui(board,this);
	cg.resize(cg.width(),cg.height()+8);
	cg.show();
    }

    public void paint(Graphics g)
    {
	g.setColor(Color.white);
	g.fillRect(0,0,getWidth(),getHeight());
    }
}
