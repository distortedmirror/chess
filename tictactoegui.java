import java.awt.*;
import java.applet.*;
import java.awt.image.*;

public class tictactoegui extends twoplayergamegui
{
	public static final int WIDTH=480+8;
	public static final int HEIGHT=480+42;
	public static final String BOARDIMAGE="ttboard.gif";
	public static final String GAMETITLE="Tic-Tac-Toe";
	public boardtype board;
	MenuBar menubar;
	LevelDialog leveldialog;
	
	public tictactoegui(boardtype board)
	{
		super(GAMETITLE);
		init(board);
	}

	public tictactoegui(boardtype board,Applet a)
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
						tictactoe.newgame();
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
		return new tictactoeCanvas(this);
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
		return null;
	}
	public String Pieces2ImageName()
	{
		return null;
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
