import java.util.*;

/*
  Defines the structure, rules and strategies used
  for the game of checkers.
 */
public class checkersboard implements boardtype
{
    public static final int DOUBLE_CHECKER_VALUE=2;
    public static final int CHECKER_VALUE=1;
    
    public static final int RED=-1;
    public static final int BLACK=1;
    
    public static final int NO_WIN=0;
    public static final int DRAW=3;
    
    protected int checkersboard[][]= {
	{ 0,-1, 0,-1, 0,-1,0, -1},
	{-1, 0,-1, 0,-1, 0,-1, 0},
	{ 0,-1, 0,-1, 0,-1,0, -1},
	{ 0, 0, 0, 0, 0, 0, 0, 0},
	{ 0, 0, 0, 0, 0, 0, 0, 0},
	{+1, 0,+1, 0,+1,0, +1, 0},
	{ 0,+1, 0,+1, 0,+1, 0,+1},
	{+1, 0,+1, 0,+1,0, +1, 0},
    };

    public checkersboard()
    {
    }
    
    public checkersboard(checkersboard board)
    {
	for(int i=7;i>=0;i--)
	    for(int j=7;j>=0;j--)
		{	
		    this.checkersboard[i][j]=board.checkersboard[i][j];
		}
    }

    public void printboard()
    {
	String sgn;
	String piece;
	for(int i=0;i<8;i++)
	    {
		System.out.println("\n");
		for(int j=0;j<8;j++)
		    {
			sgn=(checkersboard[i][j]>0)?"+":"-";
			switch(checkersboard[i][j])
			    {
			    case 1:
				piece="X";
				break;
			    case 2:
				piece="#";
				break;
			    case -1:
				piece="O";
				break;
			    case -2:
				piece="@";
				break;
			    default:
				sgn=" ";
				piece="_";
				break;
			    }
			System.out.print(sgn+piece);
		    }
	    }
	System.out.println("\n");
	System.out.flush();
    }

    public int win()
    {
	return NO_WIN;
    }

    public int evaluate_position()
    {
	int score=0;
	int piece;
	boolean isblack;
	
	for(int i=7;i>=0;--i)
	    for(int j=7;j>=0;--j)
		{
		    piece=checkersboard[i][j];
		    if(piece==0) continue;
		    isblack=(piece>0);
		    switch(piece)
			{
			case 1:
			    score+=CHECKER_VALUE;
			    break;
			case 2:
			    score+=DOUBLE_CHECKER_VALUE;
			    break;
			case -1:
			    score-=CHECKER_VALUE;
			    break;
			case -2:
			    score-=DOUBLE_CHECKER_VALUE;
			    break;
			}	
		}
	return score;
    }

    public int sign(int x)
    {
	return x==0?0:(x>0?1:-1);
    }

    public movetype[] generate_moves(int player)
    {
	//Vector v=new Vector(30);
	checkersmovetype[] cmoves=new checkersmovetype[36];
	int cm=0;
	int piece;
	for(int i=0;i<8;i++)
	    {
		for(int j=0;j<8;j++)
		    {				
			piece=checkersboard[i][j];
			//Skip it if theres not piece on that square
			//or if the piece doesn't belong the that player
			if(piece==0) continue;
			if(player<0 && piece>0) continue;
			if(player>0 && piece<0) continue;
			switch(Math.abs(piece))
			    {
				
			    case 1://checker
				if(player<0)
				    { //black
					if(j>0 && i<7 && checkersboard[i+1][j-1]==0)
					    cmoves[cm++]=(new checkersmovetype(i,j,i+1,j-1));
					if(j<7 && i<7 && checkersboard[i+1][j+1]==0)
					    cmoves[cm++]=(new checkersmovetype(i,j,i+1,j+1));
					
					if(j>1 && i<6 && checkersboard[i+1][j-1]>0 && checkersboard[i+2][j-2]==0)
					    {
						cmoves[cm]=(new checkersmovetype(i,j,i+2,j-2));
						cmoves[cm].set_isjump(true);
						cmoves[cm].set_jumpedsquarey(i+1);
						cmoves[cm].set_jumpedsquarex(j-1);
						++cm;
					    }
					if(j<6 && i<6 && checkersboard[i+1][j+1]>0 && checkersboard[i+2][j+2]==0)
					    {
						cmoves[cm]=(new checkersmovetype(i,j,i+2,j+2));
						cmoves[cm].set_isjump(true);
						cmoves[cm].set_jumpedsquarey(i+1);
						cmoves[cm].set_jumpedsquarex(j+1);
						++cm;
					    }
				    }
				else
				    { //red
					if(j>0 && i>0 && checkersboard[i-1][j-1]==0)
					    cmoves[cm++]=(new checkersmovetype(i,j,i-1,j-1));
					if(j>1 && i>1 && checkersboard[i-1][j-1]<0 && checkersboard[i-2][j-2]==0)
					    {
						cmoves[cm]=(new checkersmovetype(i,j,i-2,j-2));
						cmoves[cm].set_isjump(true);
						cmoves[cm].set_jumpedsquarey(i-1);
						cmoves[cm].set_jumpedsquarex(j-1);
						++cm;
						
					    }
					
					if(j<7 && i>0 && checkersboard[i-1][j+1]==0)
					    cmoves[cm++]=(new checkersmovetype(i,j,i-1,j+1));
					
					if(j<6 && i>1 && checkersboard[i-1][j+1]<0 && checkersboard[i-2][j+2]==0)
					    {
						cmoves[cm]=(new checkersmovetype(i,j,i-2,j+2));
						cmoves[cm].set_isjump(true);
						cmoves[cm].set_jumpedsquarey(i-1);
						cmoves[cm].set_jumpedsquarex(j+1);
						++cm;
					    }
					
				    }
				break;
			    case 2://Double
				if(j>0 && i<7 && checkersboard[i+1][j-1]==0)
				    cmoves[cm++]=(new checkersmovetype(i,j,i+1,j-1));
				if(j>1 && i<6 && 
				   ((player<0 && checkersboard[i+1][j-1]>0) || (player>0 && checkersboard[i+1][j-1]<0)) 
				   && checkersboard[i+2][j-2]==0)
				    {
					cmoves[cm]=(new checkersmovetype(i,j,i+2,j-2));
					cmoves[cm].set_isjump(true);
					cmoves[cm].set_jumpedsquarey(i+1);
					cmoves[cm].set_jumpedsquarex(j-1);
					++cm;
					
				    }
				if(j<7 && i<7 && checkersboard[i+1][j+1]==0)
				    cmoves[cm++]=(new checkersmovetype(i,j,i+1,j+1));
				
				if(j<6 && i<6 
				   && ((player<0 && checkersboard[i+1][j+1]>0) || (player>0 && checkersboard[i+1][j+1]<0)) 
				   && checkersboard[i+2][j+2]==0)
				    {
					cmoves[cm]=(new checkersmovetype(i,j,i+2,j+2));
					cmoves[cm].set_isjump(true);
					cmoves[cm].set_jumpedsquarey(i+1);
					cmoves[cm].set_jumpedsquarex(j+1);
					++cm;
				    }
				
				if(j>0 && i>0 && checkersboard[i-1][j-1]==0)
				    cmoves[cm++]=(new checkersmovetype(i,j,i-1,j-1));
				if(j>1 && i>1 
				   && ((player<0 && checkersboard[i-1][j-1]>0) || (player>0 && checkersboard[i-1][j-1]<0)) 
				   && checkersboard[i-2][j-2]==0)
				    {
					cmoves[cm]=(new checkersmovetype(i,j,i-2,j-2));
					cmoves[cm].set_isjump(true);
					cmoves[cm].set_jumpedsquarey(i-1);
					cmoves[cm].set_jumpedsquarex(j-1);
					++cm;
				    }
				if(j<7 && i>0 && checkersboard[i-1][j+1]==0)
				    cmoves[cm++]=(new checkersmovetype(i,j,i-1,j+1));
				
				if(j<6 && i>1 
				   && ((player<0 && checkersboard[i-1][j+1]>0) || (player>0 && checkersboard[i-1][j+1]<0)) 
				   && checkersboard[i-2][j+2]==0)
				    {
					cmoves[cm]=(new checkersmovetype(i,j,i-2,j+2));
					cmoves[cm].set_isjump(true);
					cmoves[cm].set_jumpedsquarey(i-1);
					cmoves[cm].set_jumpedsquarex(j+1);
					++cm;
				    }
			    }
			
		    }
	    }
	//flag moves as capture? 
	//checkersmovetype[] cmt=new checkersmovetype[v.size()];
	checkersmovetype[] cmt=new checkersmovetype[cm];
	System.arraycopy(cmoves,0,cmt,0,cm);
	//v.copyInto(cmt);
	return cmt;
	//	return cmoves
    }

    public boardtype clone_board()
    {
	return new checkersboard(this);
    }

    public void make_move(movetype move)
    {
	checkersmovetype m=(checkersmovetype)move;
	
	if(m.is_jump())
	    {
		m.save_unmove_piece(checkersboard[m.jumpedsquarey()][m.jumpedsquarex()]);
		checkersboard[m.jumpedsquarey()][m.jumpedsquarex()]=0;
	    }
	checkersboard[m.movetoy()][m.movetox()]=
	    checkersboard[m.movefromy()][m.movefromx()];
	checkersboard[m.movefromy()][m.movefromx()]=0;
	if(m.movetoy==0 || m.movetoy==7)
	    {//Crowning Checker
		int x=checkersboard[m.movetoy()][m.movetox()];
		if(Math.abs(x)!=2)
		    x*=2;
		checkersboard[m.movetoy()][m.movetox()]=x;
	    }
    }

    public void unmake_move(movetype move)
    {
	checkersmovetype m=(checkersmovetype)move;
	checkersboard[m.movefromy()][m.movefromx()]=
	    checkersboard[m.movetoy()][m.movetox()];
	if(m.is_jump())
	    checkersboard[m.jumpedsquarey()][m.jumpedsquarex()]=m.unmove_piece();
    }
}
