import java.awt.*;
import java.applet.*;

/*
  Handles the graphical input and display for
  checkers
 */
public class checkersgui extends twoplayergamegui
{
    public static final int WIDTH=480+4;
    public static final int HEIGHT=480+42;
    public static final String BOARDIMAGE="ckrzboar.gif";
    public static final String PIECESIMAGE="ackr.gif";
    public static final String PIECES2IMAGE="bckr.gif";
    public static final String GAMETITLE="Checkers!";
    public boardtype board;
    MenuBar menubar;
    LevelDialog leveldialog;
    
    public checkersgui(boardtype board)
    {
	super(GAMETITLE);
	init(board);
    }
    
    public checkersgui(boardtype board,Applet a)
    {
	super(GAMETITLE,a);
	init(board);
    }
    
    public void init(boardtype board)
    {
	this.board=board;
	setupframe();
	menubar=new MenuBar();
	Menu mnuGame=new Menu("Game");
	mnuGame.add(new MenuItem("New Game"));
	mnuGame.add(new MenuItem("Level"));
	mnuGame.add(new MenuItem("Quit"));
	/*
	  Menu mnuAction=new Menu("Actions");
	  mnuAction.add(new MenuItem("Resign"));
	  mnuAction.add(new MenuItem("Force Move"));
	  mnuAction.add(new MenuItem("Take Back"));
	  mnuAction.add(new MenuItem("Hint"));
	*/
	menubar.add(mnuGame);
	setMenuBar(menubar);
	//menubar.add(mnuAction);
    }

    public boolean handleEvent(Event e)
    {
	switch(e.id)
	    {
	    case Event.WINDOW_DESTROY:
		this.hide();
		this.dispose();
		return true;
	    case Event.ACTION_EVENT:
		if(e.target instanceof MenuItem)
		    {
			if(((String)e.arg).equals("New Game"))
			    {
				if(baseapplet!=null)
				    baseapplet.init();
				else
				    checkers.newgame();
			    }
			else if(((String)e.arg).equals("Level"))
			    {
				leveldialog=new LevelDialog(this);
				leveldialog.resize(300,150);
				Dimension paneSize = leveldialog.getSize();
				Dimension screenSize = getToolkit().getScreenSize();
				leveldialog.setLocation((screenSize.width - paneSize.width) / 2,
							(screenSize.height - paneSize.height) / 2);
				leveldialog.show();
			    }
			else if(((String)e.arg).equals("Quit"))
			    {
				dispose();
			    }
		    }
		return true;			
	    }
	return false;
    }

    public Canvas GameCanvas()
    {
	return new checkersCanvas(this);
    }

    public String GameTitle()
    {
	return GAMETITLE;
    }

    public String BoardImageName()
    {
	return BOARDIMAGE;
    }

    public String PiecesImageName()
    {
	return PIECESIMAGE;
    }
    
    public String Pieces2ImageName()
    {
	return PIECES2IMAGE;
    }

    public int width()
    {
	return WIDTH;
    }
    
    public int height()
    {
	return HEIGHT;
    }

    public boardtype GameBoard()
    {
	return board;
    }
}
