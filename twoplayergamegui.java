import java.awt.*;
import java.applet.*;

/*
  All two player games should extend this class and implement
  its abstract methods to define the board, pieces, canvas and 
  other information specific to each individual game.
 */
public abstract class twoplayergamegui extends Frame
{
    public abstract String GameTitle();
    public abstract String BoardImageName();
    public abstract String PiecesImageName();
    public abstract String Pieces2ImageName();
    public abstract int width();
    public abstract int height();
    public abstract Canvas GameCanvas();
    public abstract boardtype GameBoard();
    
    protected Applet baseapplet;

    twoplayergamegui(String s)
    {
	super(s);
	baseapplet=null;
    }

    twoplayergamegui(String s,Applet a)
    {
	super(s);
	baseapplet=a;
    }

    public void setupframe()
    {
	setLayout(new BorderLayout(0,0));
	add("Center",GameCanvas());
	pack();
    }

    public void show()
    {
	super.show();
	Dimension paneSize = getSize();
	Dimension screenSize = getToolkit().getScreenSize();
	setLocation((screenSize.width - paneSize.width) / 2,
		    (screenSize.height - paneSize.height) / 2);
	setResizable(false);
    }
}
