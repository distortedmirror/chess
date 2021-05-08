import java.awt.*;
import java.applet.*;

/*
  Defines the user interface, menu features and game board
  information specific to the game of chess.
 */
public class chessgui extends twoplayergamegui
{
    public static final int WIDTH=445;
    public static final int HEIGHT=469;
    public static final String BOARDIMAGE="board.gif";
    public static final String PIECESIMAGE="pieces.gif";
    public static final String PIECES2IMAGE="bpieces.gif";
    public static final String GAMETITLE="En-Prise 5.0 Chess Engine";
    public boardtype board;
    MenuBar menubar;
    LevelDialog leveldialog;
    
    public chessgui(boardtype board,Applet a)
    {
	super(GAMETITLE,a);
	init(board);
    }
    
    public chessgui(boardtype board)
    {
	super(GAMETITLE);
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
	mnuGame.addSeparator();
	mnuGame.add(new MenuItem("About En-Prise"));
	mnuGame.addSeparator();
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
		try
		    {
			System.exit(0);
		    }
		catch(Exception ex){;}
		return true;

		case Event.ACTION_EVENT:
				if(e.target instanceof MenuItem)
				{
					if(((String)e.arg).equals("New Game"))
					{
					    if(baseapplet!=null)
						baseapplet.init();
					    else
						chess.newgame();
					}
					else if(((String)e.arg).equals("About En-Prise"))
					{
					    AboutDialog dlg=new AboutDialog(this);
					    dlg.show();
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
					    this.hide();
					    this.dispose();
					    try
						{
						    System.exit(0);
						}
					    catch(Exception ex){;}
					}
				}
				return true;
		}
		return false;
	}
	public Canvas GameCanvas()
	{
		return new ChessCanvas(this);
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
