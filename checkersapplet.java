import java.applet.*;
import java.awt.*;

/*
  An applet based checkers game
 */
public class checkersapplet extends Applet
{
    checkersgui cg=null;
    
    public void init()
    {
	if(cg!=null)
	    cg.dispose();
	checkersboard board=new checkersboard();
	cg=new checkersgui(board,this);	
	cg.resize(cg.width(),cg.height()+27);
	cg.show();
    }

    public void paint(Graphics g)
    {
	g.setColor(Color.white);
	g.fillRect(0,0,getWidth(),getHeight());
    }
}
