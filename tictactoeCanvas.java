import java.awt.*;
import java.awt.image.*;

/*
  Handles TTT specific gui input and output
*/
public class tictactoeCanvas extends twoplayergameCanvas implements Runnable
{
    int move_num=0;
    int move_from_x;
    int move_from_y;
    int x_from;
    int y_from;
    int usermove;
    Thread waiting_thread=null;
    boolean movemade=false;
    boolean gameover=false;
    boolean nodraggameover=false;
    minimax tictactoeminimax=new minimax();
    
    public tictactoeCanvas(twoplayergamegui g)
    {		
	super(g);
    }	

    public DraggableImage[] getPieceImages()
    {
	return null;
    }

    public DraggableImage[] getPieceImages2()
    {
	return null;
    }

    public void movefrom(int x,int y)
    {
	move_from_x=x;
	move_from_y=y;
    }
    
    public boolean moveto(DraggableImage di,int x,int y)
    {
	return true;
    }
    
    public void computersmove()
    {		
	if(gameover)
	    {
		nodraggameover=true;
		return;
	    }
	computersturn=true;
	tictacmovetype move=(tictacmovetype)tictactoeminimax.makedecision(game.GameBoard());
	move.make_move(game.GameBoard());
	int x=0,y=0;
	int m=move.move();
	System.out.println("Computer:"+m+"\n");
	if(m==0) {x=0;y=0;}
	if(m==1) {x=150;y=0;}
	if(m==2) {x=320;y=0;}
	if(m==3) {x=0;y=150;}
	if(m==4) {x=150;y=150;}
	if(m==5) {x=320;y=150;}
	if(m==6) {x=0;y=300;}
	if(m==7) {x=150;y=300;}
	if(m==8) {x=320;y=300;}
	
	if(offscreen==null)
	    {
		offscreen=game.createImage(board.getWidth(this),board.getHeight(this));
		Graphics osg=offscreen.getGraphics();
		osg.drawImage(board,0,0,this);
		osg.dispose();
	    }
	Graphics g=getGraphics();
	Graphics os=offscreen.getGraphics();
	os.setColor(new Color(0,0,255));
	
	switch(move_num)
	    {
	    case 0:
		drawflower(g,x+10,y+10);
		drawflower(os,x+10,y+10);
		break;
	    case 1:
		drawspiral(g,x+10,y+10);
		drawspiral(os,x+10,y+10);
		break;
	    case 2:
		drawtorus(g,x+10,y+10);
		drawtorus(os,x+10,y+10);
		break;
	    case 3:
		drawsphere(g,x+10,y+10);
		drawsphere(os,x+10,y+10);
		break;
	    }
	move_num++;
	if(move_num>3) move_num=0;
	computersturn=false;
	movemade=false;
	if(game.GameBoard().win()==-1)
	    {
		gameover=true;
		Font font=new Font("Helvetica",Font.BOLD,96);
		g.setFont(font);
		g.setColor(new Color(255,255,255));
		os.setFont(font);
		os.setColor(new Color(255,255,255));
		g.drawString("I WIN!",100,250);
		os.drawString("I WIN!",100,250);
	    }
	g.dispose();
	os.dispose();
    }
    
    public synchronized void paint(Graphics g)
    {
	if(!doneloadingimages)
	    {
		super.paint(g);
	    }
	if(doneloadingimages)
	    {
		if(offscreen==null)
		    {
			offscreen=game.createImage(board.getWidth(this),board.getHeight(this));
			Graphics osg=offscreen.getGraphics();
			osg.drawImage(board,0,0,this);
			osg.dispose();
		    }
		g.drawImage(offscreen,0,0,this);
	    }			
    }
    
    public void drawspiral(Graphics g,int xpos,int ypos)
    {
	g.setColor(new Color(0,255,0));
	double x,y;
	double px=0,py=0;
	boolean first=true;
	double r=0;
	for(double a=0  ;a< 10*3.14 ; a+=.02)
	    {
		r+=.0005;
		x=Math.sin(r)*80*Math.cos(a);
		y=Math.sin(r)*80*Math.sin(a);
		x+=70+xpos;
		y+=80+ypos;
		if(first)
		    first=false;
		else
		    g.drawLine((int)px,(int)py,(int)x,(int)y);
		px=x; py=y;
	    }
    }
    
    public void drawflower(Graphics g,int xpos,int ypos)
    {
	g.setColor(new Color(0,255,0));
	double x,y;
	double px=0,py=0;
	boolean first=true;
	for(double a=-3.14; a< 3.14 ; a+=.02)
	    {
		x=Math.cos(10*a)*60*Math.cos(a);
		y=Math.cos(10*a)*60*Math.sin(a);
		x+=70+xpos;
		y+=70+ypos;
		if(first)
		    first=false;
		else
		    g.drawLine((int)px,(int)py,(int)x,(int)y);
		px=x; py=y;
	    }
    }

    public void drawtorus(Graphics g,int xpos,int ypos)
    {
	g.setColor(new Color(0,255,0));
	double px,py,pz,py2;
	double rot=3.14/16;
	double prevx=0,prevy=0;
	boolean first=true;
	for(double x=-3.14;x<3.14;x+=.3)
	    for(double y=-3.14;y<3.14;y+=.3)
		{
		    
		    //Formula for tourus
		    px=.5*((1+Math.cos(x))*Math.cos(y));
		    py=.5*(1+Math.cos(x))*Math.sin(y);
		    pz=Math.sin(x);
		    
		    //increase doughnut hole
		    px+=2.5*Math.cos(y);
		    py+=2.5*Math.sin(y);				
		    
		    //slight rotation
		    py2=py*Math.cos(rot)-pz*Math.sin(rot);
		    pz=py*Math.sin(rot)+pz*Math.cos(rot);
		    py=py2;
		    
		    //increase depth
		    pz+=4;
		    //project
		    px/=pz;
		    py/=pz;	
		    //scale
		    px*=50;
		    py*=50;
		    //translate
		    px+=70+xpos;
		    py+=78+ypos;
		    if(first)
			first=false;
		    else
			g.drawLine((int)prevx,(int)prevy,(int)px,(int)py);
		    prevx=px;prevy=py;
		}
    }

    public void drawsphere(Graphics g,int xpos,int ypos)
    {
	g.setColor(new Color(0,255,0));
	double px,py,pz,py2;
	double rot=3.14/16;
	boolean first=true;
	double prevx=0,prevy=0;
	for(double x=-3.14;x<3.14;x+=.2)
	    for(double y=-3.14;y<3.14;y+=.2)
		{
		    
		    //Formula for tourus
		    px=.5*Math.cos(x)*Math.cos(y);
		    py=.5*Math.cos(x)*Math.sin(y);
		    pz=Math.sin(x);
		    
		    //slight rotation
		    py2=py*Math.cos(rot)-pz*Math.sin(rot);
		    pz=py*Math.sin(rot)+pz*Math.cos(rot);
		    py=py2;
		    
		    //increase depth
		    pz+=2;
		    //project
		    px/=pz;
		    py/=pz;	
		    //scale
		    px*=150;
		    py*=150;
		    //translate
		    px+=70+xpos;
		    py+=70+ypos;
		    if(first)
			first=false;
		    else
			g.drawLine((int)prevx,(int)prevy,(int)px,(int)py);
		    prevx=px;prevy=py;
		}
    }

    public boolean mouseDown(Event e,int x,int y)
    {
	x_from=x;
	y_from=y;
	if(gameover)
	    return true;
	if(computersturn)
	    return false;
	if(!movemade)
	    {
		movemade=true;

		/*
		  A thread is started that waits a few seconds and then
		  draws its move.  Note that move legality is not
		  implemented due to the trivial nature of this example.
		 */
		waiting_thread=new Thread(this);
		waiting_thread.start();

		if(y<150)
			{
			    if(x<150)
				usermove=0;
			    else 
				if(x<320)
				    usermove=1;
				else 
				    usermove=2;
			}
		    else
			if(y<300)
			    {
				if(x<150)
				    usermove=3;
				else
				    if(x<320)
					usermove=4;
				    else 
					usermove=5;
			    }
			else
			    {
				if(x<150)
				    usermove=6;
				else
				    if(x<320)
					usermove=7;
				    else 
					usermove=8;
			    }
		    System.out.println("usermove:"+usermove+"\n");
		    tictacmovetype mv=new tictacmovetype(usermove,tictacmovetype.XPLAYER);
		    game.GameBoard().make_move(mv);
		    if(game.GameBoard().win()==1)
			{
			    Graphics g=getGraphics();
			    Graphics os=offscreen.getGraphics();
			    gameover=true;
			    Font font=new Font("Helvetica",Font.BOLD,96);
			    g.setFont(font);
			    g.setColor(new Color(255,255,255));
			    os.setFont(font);
			    os.setColor(new Color(255,255,255));
			    g.drawString("YOU WIN!",20,250);
			    os.drawString("YOU WIN!",20,250);
			    g.dispose();
			    os.dispose();
			}
		    else
			if(game.GameBoard().win()==tictacboard.DRAW)
			    {
				Graphics g=getGraphics();
				Graphics os=offscreen.getGraphics();
				gameover=true;
				Font font=new Font("Helvetica",Font.BOLD,96);
				g.setFont(font);
				g.setColor(new Color(255,255,255));
				os.setFont(font);
				os.setColor(new Color(255,255,255));
				g.drawString("TIE!",140,250);
				os.drawString("TIE!",140,250);
				g.dispose();
				os.dispose();
			}
		}
		if(offscreen==null)
		{
			offscreen=game.createImage(board.getWidth(this),board.getHeight(this));
			Graphics osg=offscreen.getGraphics();
			osg.drawImage(board,0,0,this);
			Toolkit.getDefaultToolkit().sync();
			osg.dispose();
		}
		return true;
	}

	public void run()
	{ 
	    // waits a few seconds and then draws the computer's move
	    try
		{
		    Thread.sleep(4000);
		}
	    catch(InterruptedException ex){;}
	    computersmove();
	}

	public boolean mouseDrag(Event e,int x, int y)
	{	
	    if(nodraggameover)
		return true;
	    if(!computersturn)
		{
		    Graphics os=offscreen.getGraphics();
		    os.setColor(new Color(0,0,255));
		    Graphics g=this.getGraphics();
		    g.setColor(new Color(0,0,255));
		    for(int i=-4;i<5;i++)
			for(int j=-4;j<5;j++)
			    {
				os.drawLine(x_from+j,y_from+i,x+j,y+i);
				g.drawLine(x_from+j,y_from+i,x+j,y+i);					
			    }
		    x_from=x;
		    y_from=y;
		    os.dispose();
		    g.dispose();
		    return true;
		}
	    return false;
	}
    
    public boolean mouseUp(Event e,int x,int y)
    {
	x_from=0;
	y_from=0;
	return true;
    }
}
