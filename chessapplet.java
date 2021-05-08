import java.applet.*;
import java.awt.*;

/*
  Provides a graphical interface from a applet for playing chess
 */
public class chessapplet extends Applet
{
    chessgui cg=null;

    public void init()
    {
	if(cg!=null)
	    cg.dispose();
	chessboard board=new chessboard();
	cg=new chessgui(board,this);	
	Insets insets=cg.getInsets();
	cg.setSize(cg.width()+insets.left+insets.right,
		   cg.height()+insets.top+insets.bottom);
	cg.show();
    }

    public void paint(Graphics g)
    {
	g.setColor(Color.white);
	g.fillRect(0,0,getWidth(),getHeight());
    }
}
