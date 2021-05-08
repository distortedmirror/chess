import java.awt.*;
import java.awt.image.*;

/*
  Handles the checkers specific user interface
 */
public class checkersCanvas extends twoplayergameCanvas
{
    public static final int SINGLE_CHECKER=0;
    public static final int DOUBLE_CHECKER=1;
    public static final int XPIECE_INCREMENT=60;
    public static final int YPIECE_INCREMENT=60;
    public static final int NUMBER_OF_PIECES=1;
    public static final int NUMBER_OF_CHECKERS=12;
    int move_from_x;
    int move_from_y;
    DraggableImage[] checkersimgs;
    DraggableImage[] bcheckersimgs;
    minimax checkersminimax;
    boolean donepreparingimages;
    Image choffscreen;

    public checkersCanvas(twoplayergamegui g)
    {		
	super(g);			
	checkersminimax=new minimax();
	donepreparingimages=false;
	choffscreen=null;
    }
	
    public synchronized void paint(Graphics g)
    {
	if(!doneloadingimages && !donepreparingimages)
	    {
		//base class loads images
		super.paint(g);
	    }
	if(doneloadingimages && !donepreparingimages)
	    {
		Toolkit tk=getToolkit();
		MediaTracker mttrans=new MediaTracker(this);
		int ypos;
		int xpos;
		//Change all the black pixels to be transparent
		ImageFilter filter=new BlacktoTransparentFilter();
		ImageProducer producer=new FilteredImageSource(pieces.getSource(),filter);
		ImageProducer producer2=new FilteredImageSource(pieces2.getSource(),filter);

		Image transparentpieces=tk.createImage(producer);
		Image transparentpieces2=tk.createImage(producer2);
		mttrans.addImage(transparentpieces,1);
		mttrans.addImage(transparentpieces2,2);
		try
		    {
			mttrans.waitForAll();
		    }
		catch(Exception e1){e1.printStackTrace();}

		MediaTracker mtckr=new MediaTracker(this);
		filter=new CropImageFilter(0,0,XPIECE_INCREMENT,YPIECE_INCREMENT);
		producer=new FilteredImageSource(transparentpieces.getSource(),filter);
		Image ckrimg=tk.createImage(producer);
		mtckr.addImage(ckrimg,1);
		filter=new CropImageFilter(0,0,XPIECE_INCREMENT,YPIECE_INCREMENT);
		producer=new FilteredImageSource(transparentpieces2.getSource(),filter);
		Image ckrimg2=tk.createImage(producer);
		mtckr.addImage(ckrimg2,2);
		try
		    {
			mtckr.waitForAll();
		    }
		catch(Exception e2){e2.printStackTrace();}

		checkersimgs=new DraggableImage[NUMBER_OF_CHECKERS];
		ypos=8;
		xpos=-2;
		for(int c=0;c<NUMBER_OF_CHECKERS;c++)
		    {			
			xpos+=2;
			if(xpos==8) xpos=1;
			if(xpos==9) xpos=0;
			if( (c%4)==0 )  --ypos;
			checkersimgs[c]=new DraggableImage(this,ckrimg);
			checkersimgs[c].setPosition(new Point(XPIECE_INCREMENT*xpos,YPIECE_INCREMENT*ypos));
		    }
		
		//set up the black pieces
		bcheckersimgs=new DraggableImage[NUMBER_OF_CHECKERS];
		ypos=-1;
		xpos=-1;
		for(int c=0;c<NUMBER_OF_CHECKERS;c++)
		    {	
			xpos+=2;
			if(xpos==8) xpos=1;
			if(xpos==9) xpos=0;
			if( (c%4)==0 )  ++ypos;
			bcheckersimgs[c]=new DraggableImage(this,ckrimg2);
			bcheckersimgs[c].setPosition(new Point(xpos*XPIECE_INCREMENT,YPIECE_INCREMENT*ypos));
		    }
		donepreparingimages=true;
	    }	
	if(doneloadingimages && donepreparingimages)
	    {
		if(choffscreen==null)
		    {
			choffscreen=createImage(board.getWidth(this),
						board.getHeight(this));
		    }
		Graphics og=choffscreen.getGraphics();
		super.paint(og);
		g.drawImage(choffscreen,0,0,null);
		Toolkit.getDefaultToolkit().sync();
		og.dispose();
	    }
    }

    public DraggableImage[] getPieceImages()
    {
	return checkersimgs;
    }
    
    public DraggableImage[] getPieceImages2()
    {
	return bcheckersimgs;
    }

    public  void movefrom(int x,int y)
    {
	move_from_x=x;
	move_from_y=y;
    }

    public boolean moveto(DraggableImage di,int x,int y)
    {
	//Generate moves for white and check to see
	//if the move made is legal
	checkersmovetype mv=new checkersmovetype((move_from_y/YPIECE_INCREMENT),
						 (move_from_x/XPIECE_INCREMENT),
						 (y/YPIECE_INCREMENT),
						 (x/XPIECE_INCREMENT));
	movetype[] moves=game.GameBoard().generate_moves(1);
	int ct=0;
	for(ct=0;ct<moves.length;ct++)
	    {
		if(mv.equals(moves[ct])) break;
	    }
	//to make sure the internal state of the move is ok (castling,capture..)
	if(ct<moves.length) mv=(checkersmovetype)moves[ct];
	if(ct==moves.length)
		{//illegal move so move piece back to original square
			di.setPosition(new Point(move_from_x,move_from_y));
		}
		else
		{//legal move
			game.GameBoard().make_move(mv);
			//remove captured piece
			checkersmovetype chmv=(checkersmovetype)mv;
			if(chmv.is_jump())
			{
				int x2=chmv.jumpedsquarex()*XPIECE_INCREMENT;
				int y2=chmv.jumpedsquarey()*YPIECE_INCREMENT;
				for(int c=0;c<bcheckersimgs.length;c++)
				{	
					if(bcheckersimgs[c].isTouching(new Point(x2+5,y2+5)))
					{
						int e=0;
						DraggableImage[] checkersimgs2=bcheckersimgs;
						bcheckersimgs=new DraggableImage[checkersimgs2.length-1];
						for(int d=0;d<c;d++)
							bcheckersimgs[e++]=checkersimgs2[d];
						for(int d=c+1;d<checkersimgs2.length;d++)
							bcheckersimgs[e++]=checkersimgs2[d];
					}
				}	
			}
			//move the piece
			di.setPosition(new Point((x/XPIECE_INCREMENT)*XPIECE_INCREMENT,
									(y/YPIECE_INCREMENT)*YPIECE_INCREMENT));
			return true; //legal move
		}
		return false; //illegal move
	}

	public void computersmove()
	{
		computersturn=true;
		checkersmovetype move=(checkersmovetype)checkersminimax.makedecision(game.GameBoard());
		move.make_move(game.GameBoard());
		checkersmovetype chmv=(checkersmovetype)move;
		checkersboard cb=(checkersboard)game.GameBoard();
		cb.printboard();
		int x1=chmv.movefromx()*XPIECE_INCREMENT+5;
		int y1=chmv.movefromy()*YPIECE_INCREMENT+5;
		int x2=chmv.movetox()*XPIECE_INCREMENT;
		int y2=chmv.movetoy()*YPIECE_INCREMENT;
		int x3=chmv.jumpedsquarex()*XPIECE_INCREMENT;
		int y3=chmv.jumpedsquarey()*YPIECE_INCREMENT;
		int c;
		//remove captured pieces
		if(chmv.is_jump())
		{
			for(c=0;c<checkersimgs.length;c++)
			{
				if(checkersimgs[c].isTouching(new Point(x3+5,y3+5)))
				{
					int e=0;
					DraggableImage[] checkersimgs2=checkersimgs;
					checkersimgs=new DraggableImage[checkersimgs2.length-1];
					for(int d=0;d<c;d++)
						checkersimgs[e++]=checkersimgs2[d];
					for(int d=c+1;d<checkersimgs2.length;d++)
						checkersimgs[e++]=checkersimgs2[d];
				}
			}
		}
		//move the piece
		for(c=0;c<bcheckersimgs.length;c++)
		{
			if(bcheckersimgs[c].isTouching(new Point(x1,y1)))
				break;
		}
		bcheckersimgs[c].setPosition(new Point(x2,y2));
		computersturn=false;
	}
}
