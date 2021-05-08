import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.net.*;
import java.io.*;

/*
  Provides functionality that allows a user to move a draggable
  image followed by a move by the algorithm.  This is the
  main drag and drop functionality provided as a base class 
  so many games can feature drag and drop functionality quickly
  and easily without having to implement complex drag and drop
  graphical logic.
 */
public abstract class twoplayergameCanvas extends Canvas
    implements Runnable
{
    public static final String PACKAGEURI="";
    twoplayergamegui game;
    boolean doneloadingimages;
    Image board;
    Image pieces;
    Image pieces2;
    Image offscreen;
    DraggableImage di;
    int xdiff;
    int ydiff;
    boolean computersturn;
    Object mouseLock=new Object();

    public abstract DraggableImage[] getPieceImages();
    public abstract DraggableImage[] getPieceImages2();
    public abstract void movefrom(int x,int y);
    public abstract boolean moveto(DraggableImage di,int x,int y);
    public abstract void computersmove();
    
    public twoplayergameCanvas(twoplayergamegui g)
    {
	game=g;
	doneloadingimages=false;
	board=pieces=pieces2=offscreen=null;
	di=null;
	xdiff=ydiff=0;
	computersturn=false;
    }
    
    public synchronized void paint(Graphics g)
    {
	if(!doneloadingimages)
	    {
		String loading="Loading Images";
		int loadingwidth=g.getFontMetrics().stringWidth(loading);
		g.drawString(loading,game.width()/2-loadingwidth/2,game.height()/2);
		MediaTracker tracker=new MediaTracker(this);
		Toolkit tk=Toolkit.getDefaultToolkit();
		try
		    {
			if(game.baseapplet!=null)
			    board=tk.getImage(new URL(game.baseapplet.getCodeBase().toString()+
						      PACKAGEURI+game.BoardImageName()));
			else
			    board=tk.getImage(game.BoardImageName());
			tracker.addImage(board,1);
			if(game.baseapplet!=null)
			    {
				pieces=tk.getImage(new URL(game.baseapplet.getCodeBase().toString()+
							   PACKAGEURI+game.PiecesImageName()));
				tracker.addImage(pieces,2);
			    }
			else
			    {
				if(game.PiecesImageName()!=null)
				    {
					pieces=tk.getImage(game.PiecesImageName());
					tracker.addImage(pieces,2);
				    }
			    }
			if(game.baseapplet!=null)
			    {
				pieces2=tk.getImage(new URL(game.baseapplet.getCodeBase().toString()+
							    PACKAGEURI+game.Pieces2ImageName()));
				tracker.addImage(pieces2,3);
			    }
			else
			    {
				if(game.Pieces2ImageName()!=null)
				    {
					pieces2=tk.getImage(game.Pieces2ImageName());
					tracker.addImage(pieces2,3);
				    }
			    }
			try
			    {
				tracker.waitForAll();
			    }
			catch(InterruptedException ie)
			    {
				ie.printStackTrace();
			    }
		    }
		catch(Exception aex)
		    {
			aex.printStackTrace();
		    }
		doneloadingimages=true;
	    }
	else
	    {
		g.drawImage(board,0,0,null);
		DraggableImage[] imgs=getPieceImages();
		DraggableImage[] imgs2=getPieceImages2();
		if(imgs!=null)
		    for(int c=0;c<imgs.length;c++)
			imgs[c].paint(g);
		if(imgs!=null)
		    for(int c=0;c<imgs2.length;c++)
			imgs2[c].paint(g);
		Toolkit.getDefaultToolkit().sync();
	    }			
    }
    
    public boolean mouseDown(Event e,int x,int y)
    {
	if(computersturn)
	    return false;
	if(!doneloadingimages) 
	    return false;
	if(computersturn) 
	    return false;
	DraggableImage[] dimgs=getPieceImages();
	for(int c=0;c<dimgs.length;c++)
	    {
		if(dimgs[c].isTouching(new Point(x,y)))
		    {		    
			synchronized(mouseLock)
			    {
				di=dimgs[c];
				xdiff=x-di.xpos();
				ydiff=y-di.ypos();				
				movefrom(di.xpos(),di.ypos());
				dimgs[c].startDrag();
				return true;
			    }
		    }
	    }
	return false;
    }

    public boolean mouseDrag(Event e,int x, int y)
    {		
	if(computersturn)
	    return false;
	if(!doneloadingimages) 
	    return false;
	if(!computersturn && di!=null && di.inMidDrag())
	    {
		synchronized(mouseLock)
		    {
			di.setPosition(new Point(x-xdiff,y-ydiff));
			if(offscreen==null)
			    {
				offscreen=game.createImage(board.getWidth(this),board.getHeight(this));
			    }
			Graphics og=offscreen.getGraphics();
			paint(og);
			Toolkit.getDefaultToolkit().sync();
			og.dispose();
			Graphics g=this.getGraphics();
			g.drawImage(offscreen,0,0,this);		
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
			return true;
		    }
	    }
	return false;
    }

    public boolean mouseUp(Event e,int x,int y)
    {
	if(computersturn)
	    return false;
	if(!doneloadingimages) 
	    return false;
	if(!computersturn && di !=null && di.inMidDrag())
	    {
		synchronized(mouseLock)
		    {
			boolean movecompleted=false;
			if(moveto(di,x,y))
			    {
				movecompleted=true;
			    }
			di.endDrag();				
			if(offscreen==null)
			    {
				offscreen=game.createImage(board.getWidth(this),board.getHeight(this));
			    }
			Graphics g=this.getGraphics();
			Graphics og=offscreen.getGraphics();
			paint(og);
			Toolkit.getDefaultToolkit().sync();
			g.drawImage(offscreen,0,0,this);		
			Toolkit.getDefaultToolkit().sync();
			if(movecompleted)
			    {
				computersturn=true;
				(new Thread(this)).start();
			    }
			og.dispose();
			g.dispose();
			return true;
		    }
	    }
	return false;
    }
    
    public void run()
    {
	computersmove();
	Graphics g=this.getGraphics();
	Graphics og=offscreen.getGraphics();
	paint(og);
	Toolkit.getDefaultToolkit().sync();
	g.drawImage(offscreen,0,0,this);		
	Toolkit.getDefaultToolkit().sync();
	g.dispose();
	og.dispose();
    }

    public int xclickdifference()
    {
	return xdiff;
    }
    
    public int yclickdifference()
    {
	return ydiff;
    }
}
