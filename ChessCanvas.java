import java.awt.*;
import java.awt.image.*;

/*
  Implements chess specific gui functionality such as castling, capturing and 
  all other piece movement.
*/
public class ChessCanvas extends twoplayergameCanvas
{
    public static final int KING=0;
    public static final int QUEEN=1;
    public static final int ROOK=2;
    public static final int KNIGHT=3;
    public static final int BISHOP=4;
    public static final int PAWN=5;
    public static final int XPIECE_INCREMENT=57;
    public static final int YPIECE_INCREMENT=59;
    public static final int NUMBER_OF_PIECES=6;
    
    int move_from_x;
    int move_from_y;
    Image[] wimgs;
    Image[] bimgs;
    DraggableImage[] chessimgs;
    DraggableImage[] bchessimgs;
    minimax chessminimax;
    boolean donecroppingchessimages;
    Image chessoffscreen;
    
    public ChessCanvas(twoplayergamegui g)
    {		
	super(g);		
	chessminimax=new minimax();
	donecroppingchessimages=false;
	chessoffscreen=null;
	wimgs=new Image[NUMBER_OF_PIECES*2];
	bimgs=new Image[NUMBER_OF_PIECES*2];
    }	
    
    public synchronized void paint(Graphics g)
    {
	if(!doneloadingimages && !donecroppingchessimages)
	    {
		//base class loads images
		super.paint(g);
	    }
	if(doneloadingimages && !donecroppingchessimages)
	    {
		//crop pieces and make their backgrounds transparent
		DraggableImage[] dimgs;
		DraggableImage[] dimgs2;
		dimgs=new DraggableImage[NUMBER_OF_PIECES*2];
		dimgs2=new DraggableImage[NUMBER_OF_PIECES*2];
		//Change all the black pixels to be transparent
		ImageFilter filter=new BlacktoTransparentFilter();
		ImageProducer producer=new FilteredImageSource(pieces.getSource(),filter);
		ImageProducer producer2=new FilteredImageSource(pieces2.getSource(),filter);
		MediaTracker mt=new MediaTracker(this);
		Toolkit tk=getToolkit();
		Image transparentpieces=tk.createImage(producer);
		Image transparentpieces2=tk.createImage(producer2);
		mt.addImage(transparentpieces,1);
		mt.addImage(transparentpieces2,2);
		try
		    {
			mt.waitForAll();
		    }
		catch(Exception e){e.printStackTrace();}
		//Crop the pieces
		for(int c=0;c<NUMBER_OF_PIECES;c++)
		{
			filter=new CropImageFilter(XPIECE_INCREMENT*c+2*c,0,
				XPIECE_INCREMENT-2*c,YPIECE_INCREMENT-1);
			producer=new FilteredImageSource(transparentpieces.getSource(),filter);
			producer2=new FilteredImageSource(transparentpieces2.getSource(),filter);

			MediaTracker pmt1=new MediaTracker(this);
			Image pimg=tk.createImage(producer);
			Image pimg2=tk.createImage(producer2);
			pmt1.addImage(pimg,1);
			pmt1.addImage(pimg2,2);
			try
			    {
				pmt1.waitForAll();
			    }
			catch(Exception e1){e1.printStackTrace();}
			wimgs[c]=pimg;
			bimgs[c]=pimg2;
			dimgs[c]=new DraggableImage(this,pimg);
			dimgs2[c]=new DraggableImage(this,pimg2);

			MediaTracker pmt2=new MediaTracker(this);
			pimg=tk.createImage(producer);
			pimg2=tk.createImage(producer2);
			pmt2.addImage(pimg,1);
			pmt2.addImage(pimg2,2);
			try
			    {
				pmt2.waitForAll();
			    }
			catch(Exception e2){e2.printStackTrace();}
			dimgs[c+NUMBER_OF_PIECES]=new DraggableImage(this,pimg);
			dimgs2[c+NUMBER_OF_PIECES]=new DraggableImage(this,pimg2);
		}
		//Sets up the board
		chessimgs=new DraggableImage[16];
		chessimgs[0]=dimgs[ROOK];
		chessimgs[1]=dimgs[KNIGHT];
		chessimgs[2]=dimgs[BISHOP];
		chessimgs[3]=dimgs[QUEEN];
		chessimgs[4]=dimgs[KING];
		chessimgs[5]=dimgs[BISHOP+NUMBER_OF_PIECES];
		chessimgs[6]=dimgs[KNIGHT+NUMBER_OF_PIECES];
		chessimgs[7]=dimgs[ROOK+NUMBER_OF_PIECES];		
		filter=new CropImageFilter(XPIECE_INCREMENT*(NUMBER_OF_PIECES-1)+6,0,
				XPIECE_INCREMENT-6,YPIECE_INCREMENT-1);
		producer=new FilteredImageSource(transparentpieces.getSource(),filter);
		for(int c=8;c<16;c++)
		{	
		    MediaTracker pmt1=new MediaTracker(this);
		    Image cropimg=tk.createImage(producer);
		    pmt1.addImage(cropimg,2);
		    try
			{
			    pmt1.waitForAll();
			}
		    catch(Exception e1){e1.printStackTrace();}
		    chessimgs[c]=new DraggableImage(this,cropimg);
		    chessimgs[c].setPosition(new Point((c-8)*XPIECE_INCREMENT,YPIECE_INCREMENT*6));
		}
		for(int c=0;c<8;c++)
		{
			chessimgs[c].setPosition(new Point(c*XPIECE_INCREMENT,YPIECE_INCREMENT*7));
		}
		//set up the black pieces
		bchessimgs=new DraggableImage[16];
		bchessimgs[0]=dimgs2[ROOK];
		bchessimgs[1]=dimgs2[KNIGHT];
		bchessimgs[2]=dimgs2[BISHOP];
		bchessimgs[3]=dimgs2[QUEEN];
		bchessimgs[4]=dimgs2[KING];
		bchessimgs[5]=dimgs2[BISHOP+NUMBER_OF_PIECES];
		bchessimgs[6]=dimgs2[KNIGHT+NUMBER_OF_PIECES];
		bchessimgs[7]=dimgs2[ROOK+NUMBER_OF_PIECES];		
		filter=new CropImageFilter(XPIECE_INCREMENT*(NUMBER_OF_PIECES-1)+6,0,
				XPIECE_INCREMENT-6,YPIECE_INCREMENT-1);
		producer=new FilteredImageSource(transparentpieces2.getSource(),filter);
		for(int c=8;c<16;c++)
		{			
		    MediaTracker pmt1=new MediaTracker(this);
		    Image cropimg=tk.createImage(producer);
		    pmt1.addImage(cropimg,2);
		    try
			{
			    pmt1.waitForAll();
			}
		    catch(Exception e1){e1.printStackTrace();}
		    bchessimgs[c]=new DraggableImage(this,cropimg);
		    bchessimgs[c].setPosition(new Point((c-8)*XPIECE_INCREMENT,YPIECE_INCREMENT*1));
		}
		for(int c=0;c<8;c++)
		{
			bchessimgs[c].setPosition(new Point(c*XPIECE_INCREMENT,YPIECE_INCREMENT*0));
		}
		donecroppingchessimages=true;
	    }
	if(doneloadingimages && donecroppingchessimages)
	    {
		if(chessoffscreen==null)
		    {
			chessoffscreen=createImage(board.getWidth(this),
						   board.getHeight(this));
		    }
		Graphics og=chessoffscreen.getGraphics();
		super.paint(og);
		g.drawImage(chessoffscreen,0,0,null);
		Toolkit.getDefaultToolkit().sync();
		og.dispose();
	    }
    }

    public DraggableImage[] getPieceImages()
    {
	return chessimgs;
    }

    public DraggableImage[] getPieceImages2()
    {
	return bchessimgs;
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
	chessmovetype mv=new chessmovetype((move_from_y/YPIECE_INCREMENT),
					   (move_from_x/XPIECE_INCREMENT),
					   (y/YPIECE_INCREMENT),
					   (x/XPIECE_INCREMENT));
	movetype[] moves=game.GameBoard().generate_moves(1);
	int ct=0;
	for(ct=0;ct<moves.length;ct++)
	    {
		if(mv.equals(moves[ct])) break;
	    }
	if(ct<moves.length)
	    {
		mv=(chessmovetype)moves[ct];
	    }
	if(ct==moves.length)
	    {
		//illegal move so move piece back to original square
		di.setPosition(new Point(move_from_x,move_from_y));
	    }
	else
	    {//legal move
		game.GameBoard().make_move(mv);
		//remove captured piece
		chessmovetype chmv=(chessmovetype)mv;
		int x1=chmv.movefromx()*XPIECE_INCREMENT+5;
		int y1=chmv.movefromy()*YPIECE_INCREMENT+5;
		int x2=chmv.movetox()*XPIECE_INCREMENT;
		int y2=chmv.movetoy()*YPIECE_INCREMENT;
		for(int c=0;c<bchessimgs.length;c++)
		    {	
			if(bchessimgs[c].isTouching(new Point(x2+5,y2+5)))
			    {
				int e=0;
				DraggableImage[] chessimgs2=bchessimgs;
				bchessimgs=new DraggableImage[chessimgs2.length-1];
				for(int d=0;d<c;d++)
				    bchessimgs[e++]=chessimgs2[d];
				for(int d=c+1;d<chessimgs2.length;d++)
				    bchessimgs[e++]=chessimgs2[d];
			    }
		    }	
		//move the piece
		di.setPosition(new Point((x/XPIECE_INCREMENT)*XPIECE_INCREMENT,
					 (y/YPIECE_INCREMENT)*YPIECE_INCREMENT));
		if(chmv.ispawnpromotion())
		    {
			di.setImage(wimgs[QUEEN]);
		    }
		//Castling? move rook
		if(chmv.iscastling())
		    {
			if(chmv.movetox()>4)
			    {//king side castling
				int xpos=(chmv.movetox()+1)*XPIECE_INCREMENT;
				int ypos=(chmv.movetoy())*YPIECE_INCREMENT;
				int c;
				for(c=0;c<chessimgs.length;c++)
				    {
					if(chessimgs[c].isTouching(new Point(xpos+5,ypos+5)))
					    break;
				    }
				//move the rook 2 sqares to the left
				chessimgs[c].setPosition(new Point(xpos-(XPIECE_INCREMENT*2),ypos));
			    }
			if(chmv.movetox()<4)
			    {//queen side castling
				int xpos=(chmv.movetox()-2)*XPIECE_INCREMENT;
				int ypos=(chmv.movetoy())*YPIECE_INCREMENT;
				int c;
				for(c=0;c<chessimgs.length;c++)
				    {
					if(chessimgs[c].isTouching(new Point(xpos+5,ypos+5)))
					    break;
				    }
				//move the rook 2 sqares to the left
				chessimgs[c].setPosition(new Point(xpos+(XPIECE_INCREMENT*3),ypos));
			    }
		    }
		return true; //legal move
	    }
	return false; //illegal move
    }

    public void computersmove()
    {
	computersturn=true;
	chessmovetype move=(chessmovetype)chessminimax.makedecision(game.GameBoard());
	move.make_move(game.GameBoard());
	chessboard cb=(chessboard)game.GameBoard();
	cb.printboard();
	int x1=move.movefromx()*XPIECE_INCREMENT+5;
	int y1=move.movefromy()*YPIECE_INCREMENT+5;
	int x2=move.movetox()*XPIECE_INCREMENT;
	int y2=move.movetoy()*YPIECE_INCREMENT;
	int c;
	//remove captured pieces
	for(c=0;c<chessimgs.length;c++)
	    {
		if(chessimgs[c].isTouching(new Point(x2+5,y2+5)))
		    {
			int e=0;
			DraggableImage[] chessimgs2=chessimgs;
			chessimgs=new DraggableImage[chessimgs2.length-1];
			for(int d=0;d<c;d++)
			    chessimgs[e++]=chessimgs2[d];
			for(int d=c+1;d<chessimgs2.length;d++)
			    chessimgs[e++]=chessimgs2[d];
		    }
	    }
	//move the piece
	for(c=0;c<bchessimgs.length;c++)
	    {
		if(bchessimgs[c].isTouching(new Point(x1,y1)))
		    break;
	    }
	bchessimgs[c].setPosition(new Point(x2,y2));
	chessmovetype chmv=(chessmovetype)move;
	if(chmv.ispawnpromotion())
	    {
		bchessimgs[c].setImage(bimgs[QUEEN]);
	    }
	//if castling move the rook
	if(chmv.iscastling())
	    {
		if(chmv.movetox()>4)
		    {//king side castling
			int xpos=(chmv.movetox()+1)*XPIECE_INCREMENT;
			int ypos=(chmv.movetoy())*YPIECE_INCREMENT;
			int c2;
			for(c2=0;c2<bchessimgs.length;c2++)
			    {
				if(bchessimgs[c2].isTouching(new Point(xpos+5,ypos+5)))
				    break;
			    }
			//move the rook 2 sqares to the left
			bchessimgs[c2].setPosition(new Point(xpos-(XPIECE_INCREMENT*2),ypos));
		    }
		if(chmv.movetox()<4)
		    {//queen side castling
			int xpos=(chmv.movetox()-2)*XPIECE_INCREMENT;
			int ypos=(chmv.movetoy())*YPIECE_INCREMENT;
			int c2;
			for(c2=0;c2<bchessimgs.length;c2++)
			    {
				if(bchessimgs[c2].isTouching(new Point(xpos+5,ypos+5)))
				    break;
			    }
			//move the rook 2 sqares to the left
			bchessimgs[c2].setPosition(new Point(xpos+(XPIECE_INCREMENT*3),ypos));
		    }
	    }	
	computersturn=false;
    }
}
