import java.util.*;

/*
  Defines the data structures that represent and manipulate the board 
  positions and also holds all the logic necessary to define the rules of 
  chess, and how the program scores positions.
 */
public class chessboard implements boardtype
{
	public static final int KING_VALUE=300;
	public static final int QUEEN_VALUE=90;
	public static final int ROOK_VALUE=50;
	public static final int BISHOP_VALUE=32;
	public static final int KNIGHT_VALUE=30;
	public static final int PAWN_VALUE=10;

	public static final int WHITE_KING=1;
	public static final int BLACK_KING=-1;

	public static final int NO_WIN=0;
	public static final int DRAW=3;
	protected int chessboard[][]= {
		{-5,-3,-4,-2,-1,-4,-3,-5},
		{-6,-6,-6,-6,-6,-6,-6,-6},
		{ 0, 0, 0, 0, 0, 0, 0, 0},
		{ 0, 0, 0, 0, 0, 0, 0, 0},
		{ 0, 0, 0, 0, 0, 0, 0, 0},
		{ 0, 0, 0, 0, 0, 0, 0, 0},
		{+6,+6,+6,+6,+6,+6,+6,+6},
		{+5,+3,+4,+2,+1,+4,+3,+5},
	};
	public chessboard()
	{
	}
	public chessboard(chessboard board)
	{
		for(int i=0;i<8;i++)
		for(int j=0;j<8;j++)
		{	
			this.chessboard[i][j]=board.chessboard[i][j];
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
				sgn=(chessboard[i][j]>0)?"+":"-";
				switch(Math.abs(chessboard[i][j]))
				{
					case 1:
						piece="K";
						break;
					case 2:
						piece="Q";
						break;
					case 3:
						piece="N";
						break;
					case 4:
						piece="B";
						break;
					case 5:
						piece="R";
						break;
					case 6:
						piece="P";
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
		boolean iswhite;
		
		for(int i=7;i>=0;--i)
		for(int j=7;j>=0;--j)
		{
			piece=chessboard[i][j];
			if(piece==0) continue;
			iswhite=(piece>0);
			switch(piece)
			{
				case 1:
					score+=KING_VALUE;
					break;
				case 2:
					score+=QUEEN_VALUE;
					break;
				case 3:
					//double knights

					//center
					if(i<=4 && j>=2 && j<=5) score+=1;
					if(i<=4 && j>=3 && j<=4) score+=2;

					score+=KNIGHT_VALUE;
					//unmoved
					if(i==7) score-=4;
					break;
				case 4:
					//double bishops

					//center
					if(i<=4 && j>=2 && j<=5) score+=1;
					if(i<=4 && j>=3 && j<=4) score+=1;

					score+=BISHOP_VALUE;
					//unmoved
					if(i==7) score-=3;
					break;
				case 5:
					

					score+=ROOK_VALUE;
					break;
				case 6:
					//unmoved center pawn
					if( (j==3 || j==4) && i==7) score-=1;
					//center pawns
					if(i<=4 && j>=2 && j<=5) score+=2;
					if(i<=4 && j>=3 && j<=4) score+=3;
					//passed pawns
					if(i<=3) score+=5;
					if(i<=2) score+=5;
					//protecting pieces
					if(i>=1 && j>=1 && j<=6 && 
						( (chessboard[i-1][j+1]>0) || (chessboard[i-1][j-1]>0) ))
						score+=3;
					//attacking
					if(i>=1 && j>=1 && j<=6 && 
						( (chessboard[i-1][j+1]<0) || (chessboard[i-1][j-1]<0) ))
						score+=4;
					//protecting king
					if(i==6 && ((j<3) || (j>4)) && chessboard[7][j]==WHITE_KING)
						score+=4;
					//double pawns
					for(int k=i-1;k>=0;k--)
						if(chessboard[k][j]==1) score-=3;
					
					score+=PAWN_VALUE;
					break;

				case -1:
					score-=KING_VALUE*2;
					break;
				case -2:
					score-=QUEEN_VALUE*1.2;
					break;
				case -3:
					//double knights

					//center 
					if(i>=3 && j>=2 && j<=5) score-=1;
					if(i>=3 && j>=3 && j<=4) score-=2;

					score-=KNIGHT_VALUE;
					//unmoved
					if(i==0) score+=4;
					break;
				case -4:
					//double bishops

					//center 
					if(i>=3 && j>=2 && j<=5) score-=1;
					if(i>=3 && j>=3 && j<=4) score-=1;

					score-=BISHOP_VALUE*1.1;
					//unmoved
					if(i==0) score+=3;
					break;
				case -5:
					score-=ROOK_VALUE;
					break;
				case -6:
					//unmoved center pawn
					if( (j==3 || j==4) && i==1) score+=1;
					//center pawns
					if(i>=3 && j>=2 && j<=5) score-=2;
					if(i>=3 && j>=3 && j<=4) score-=3;
					//passed pawns
					if(i>=4) score-=5;
					if(i>=5) score-=5;
					//protecting pieces
					if(i<=6 && j>=1 && j<=6 && 
						( (chessboard[i+1][j+1]<0) || (chessboard[i+1][j-1]<0) ))
						score-=3;
					//attacking with pawn
					if(i<=6 && j>=1 && j<=6 && 
						( (chessboard[i+1][j+1]>0) || (chessboard[i+1][j-1]>0) ))
						score-=4;
					//protecting king
					if(i==1 && ((j<3) || (j>4)) && chessboard[0][j]==BLACK_KING)
						score-=4;
					//double pawns
					for(int k=i+1;k<8;k++)
						if(chessboard[k][j]==-1) score+=3;

					score-=PAWN_VALUE;
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
		chessmovetype[] cmoves=new chessmovetype[80];
		int cm=0;
		int piece;
		boolean flaga;
		boolean flagb;
		boolean flagc;
		flaga=flagb=flagc=true;
		
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{				
				piece=chessboard[i][j];
				//Skip it if theres not piece on that square
				//or if the piece doesn't belong the that player
				if(piece==0) continue;
				if(player<0 && piece>0) continue;
				if(player>0 && piece<0) continue;
				switch(Math.abs(piece))
				{
					case 1://king
						if(i>0)
						{
							if(sign(chessboard[i-1][j])!=player)
								cmoves[cm++]=new chessmovetype(i,j,i-1,j);
							if(j>0)
								if(sign(chessboard[i-1][j-1])!=player)
									cmoves[cm++]=(new chessmovetype(i,j,i-1,j-1));
							if(j<7)
								if(sign(chessboard[i-1][j+1])!=player)
									cmoves[cm++]=(new chessmovetype(i,j,i-1,j+1));
						}
						if(i<7)
						{
							if(sign(chessboard[i+1][j])!=player)
								cmoves[cm++]=(new chessmovetype(i,j,i+1,j));
							if(j>0)
								if(sign(chessboard[i+1][j-1])!=player)
									cmoves[cm++]=(new chessmovetype(i,j,i+1,j-1));
							if(j<7)
								if(sign(chessboard[i+1][j+1])!=player)
									cmoves[cm++]=(new chessmovetype(i,j,i+1,j+1));
						}
						if(j>0)
							if(sign(chessboard[i][j-1])!=player)
								cmoves[cm++]=(new chessmovetype(i,j,i,j-1));
						if(j<7)
							if(sign(chessboard[i][j+1])!=player)
								cmoves[cm++]=(new chessmovetype(i,j,i,j+1));
						if(j==4 && ( (i==0 && player<0) || (i==7 && player>0) ) )
						{//king is in startposition							
							if(chessboard[i][j+1]==0 && chessboard[i][j+2]==0 && 
								chessboard[i][j+3]==(5*player))
							{
								cmoves[cm++]=new chessmovetype(i,j,i,j+2);
								cmoves[cm-1].set_iscastling(true);
							}
							if(chessboard[i][j-1]==0 && chessboard[i][j-2]==0 &&
								chessboard[i][j-3]==0 && chessboard[i][j-4]==(5*player))
							{
								cmoves[cm++]=new chessmovetype(i,j,i,j-2);
								cmoves[cm-1].set_iscastling(true);
							}
						}
							
						break;
					case 2://queen
						//flags are to signify whether to stop
						// generating a column,row, or diagnol
						// once it's blocked
						flaga=flagb=flagc=true;
						for(int ii=1,jj=1;ii<=i;++ii,++jj)
						{
							if(flaga)
							{
								if(chessboard[i-ii][j]!=0)
								{
									flaga=false;
									if(sign(chessboard[i-ii][j])!=player)
										cmoves[cm++]=(new chessmovetype(i,j,i-ii,j));
								}
								else
									cmoves[cm++]=(new chessmovetype(i,j,i-ii,j));
							}
							if(flagb)
							{
								if((j-jj)>=0)
								{
									if(chessboard[i-ii][j-jj]!=0)
									{
										flagb=false;
										if(sign(chessboard[i-ii][j-jj])!=player)
											cmoves[cm++]=(new chessmovetype(i,j,i-ii,j-jj));
									}
									else
										cmoves[cm++]=(new chessmovetype(i,j,i-ii,j-jj));
								}
							}
							if(flagc)
							{
								if((j+jj)<=7)
								{
									if(chessboard[i-ii][j+jj]!=0)
									{
										flagc=false;
										if(sign(chessboard[i-ii][j+jj])!=player)
											cmoves[cm++]=(new chessmovetype(i,j,i-ii,j+jj));
									}
									else
										cmoves[cm++]=(new chessmovetype(i,j,i-ii,j+jj));
								}
							}
						}
						flaga=flagb=flagc=true;
						for(int ii=1,jj=1;(ii+i)<=7;++ii,++jj)
						{
							if(flaga)
							{
								if(chessboard[i+ii][j]!=0)
								{
									flaga=false;
									if(sign(chessboard[i+ii][j])!=player)
										cmoves[cm++]=(new chessmovetype(i,j,i+ii,j));
								}
								else
									cmoves[cm++]=(new chessmovetype(i,j,i+ii,j));
							}
							if(flagb)
							{
								if((j-jj)>=0)
								{
									if(chessboard[i+ii][j-jj]!=0)
									{
										flagb=false;
										if(sign(chessboard[i+ii][j-jj])!=player)
											cmoves[cm++]=(new chessmovetype(i,j,i+ii,j-jj));
									}
									else
										cmoves[cm++]=(new chessmovetype(i,j,i+ii,j-jj));
								}
							}
							if(flagc)
							{
								if((j+jj)<=7)
								{
									if(chessboard[i+ii][j+jj]!=0)
									{
										flagc=false;
										if(sign(chessboard[i+ii][j+jj])!=player)
											cmoves[cm++]=(new chessmovetype(i,j,i+ii,j+jj));
									}
									else
										cmoves[cm++]=(new chessmovetype(i,j,i+ii,j+jj));
								}
							}
						}

						for(int jj=1;(j-jj)>=0;++jj)
						{
							if(chessboard[i][j-jj]!=0)
							{
								if(sign(chessboard[i][j-jj])!=player)
									cmoves[cm++]=(new chessmovetype(i,j,i,j-jj));
								break; //hope this breaks outa the for loop not the switch
							}
							else
								cmoves[cm++]=(new chessmovetype(i,j,i,j-jj));
						}
						for(int jj=1;(jj+j)<=7;++jj)
						{
							if(chessboard[i][j+jj]!=0)
							{
								if(sign(chessboard[i][j+jj])!=player)
									cmoves[cm++]=(new chessmovetype(i,j,i,j+jj));
								break; //hope this breaks outa the for loop not the switch
							}
							else
								cmoves[cm++]=(new chessmovetype(i,j,i,j+jj));
						}
						break;
					case 3://knight
						if(i>0)
						{
							if(j>1)
								if(sign(chessboard[i-1][j-2])!=player)
									cmoves[cm++]=(new chessmovetype(i,j,i-1,j-2));
							if(j<6)
								if(sign(chessboard[i-1][j+2])!=player)
									cmoves[cm++]=(new chessmovetype(i,j,i-1,j+2));
						}
						if(i>1)
						{
							if(j>0)
								if(sign(chessboard[i-2][j-1])!=player)
									cmoves[cm++]=(new chessmovetype(i,j,i-2,j-1));
							if(j<7)
								if(sign(chessboard[i-2][j+1])!=player)
									cmoves[cm++]=(new chessmovetype(i,j,i-2,j+1));
						}
						if(i<7)
						{
							if(j>1)
								if(sign(chessboard[i+1][j-2])!=player)
									cmoves[cm++]=(new chessmovetype(i,j,i+1,j-2));
							if(j<6)
								if(sign(chessboard[i+1][j+2])!=player)
									cmoves[cm++]=(new chessmovetype(i,j,i+1,j+2));
						}
						if(i<6)
						{
							if(j>0)
								if(sign(chessboard[i+2][j-1])!=player)
									cmoves[cm++]=(new chessmovetype(i,j,i+2,j-1));
							if(j<7)
								if(sign(chessboard[i+2][j+1])!=player)
									cmoves[cm++]=(new chessmovetype(i,j,i+2,j+1));
						}
						break;
					case 4://bishop
						flagb=flagc=true;
						for(int ii=1,jj=1;ii<=i;++ii,++jj)
						{
							if(flagb)
							{
								if((j-jj)>=0)
								{
									if(chessboard[i-ii][j-jj]!=0)
									{
										flagb=false;
										if(sign(chessboard[i-ii][j-jj])!=player)
											cmoves[cm++]=(new chessmovetype(i,j,i-ii,j-jj));
									}
									else
										cmoves[cm++]=(new chessmovetype(i,j,i-ii,j-jj));
								}
							}
							if(flagc)
							{
								if((j+jj)<=7)
								{
									if(chessboard[i-ii][j+jj]!=0)
									{
										flagc=false;
										if(sign(chessboard[i-ii][j+jj])!=player)
											cmoves[cm++]=(new chessmovetype(i,j,i-ii,j+jj));
									}
									else
										cmoves[cm++]=(new chessmovetype(i,j,i-ii,j+jj));
								}
							}
						}
						flagb=flagc=true;
						for(int ii=1,jj=1;(ii+i)<=7;++ii,++jj)
						{
							if(flagb)
							{
								if((j-jj)>=0)
								{
									if(chessboard[i+ii][j-jj]!=0)
									{
										flagb=false;
										if(sign(chessboard[i+ii][j-jj])!=player)
											cmoves[cm++]=(new chessmovetype(i,j,i+ii,j-jj));
									}
									else
										cmoves[cm++]=(new chessmovetype(i,j,i+ii,j-jj));
								}
							}
							if(flagc)
							{
								if((j+jj)<=7)
								{
									if(chessboard[i+ii][j+jj]!=0)
									{
										flagc=false;
										if(sign(chessboard[i+ii][j+jj])!=player)
											cmoves[cm++]=(new chessmovetype(i,j,i+ii,j+jj));
									}
									else
										cmoves[cm++]=(new chessmovetype(i,j,i+ii,j+jj));
								}
							}
						}

						break;
					case 5://rook
						for(int ii=1,jj=1;ii<=i;++ii,++jj)
						{
								if(chessboard[i-ii][j]!=0)
								{
									if(sign(chessboard[i-ii][j])!=player)
										cmoves[cm++]=(new chessmovetype(i,j,i-ii,j));
									break;
								}
								else
									cmoves[cm++]=(new chessmovetype(i,j,i-ii,j));
						}

						for(int ii=1,jj=1;(ii+i)<=7;++ii,++jj)
						{
								if(chessboard[i+ii][j]!=0)
								{
									if(sign(chessboard[i+ii][j])!=player)
										cmoves[cm++]=(new chessmovetype(i,j,i+ii,j));
									break;
								}
								else
									cmoves[cm++]=(new chessmovetype(i,j,i+ii,j));
						}

						for(int jj=1;(j-jj)>=0;++jj)
						{
							if(chessboard[i][j-jj]!=0)
							{
								if(sign(chessboard[i][j-jj])!=player)
									cmoves[cm++]=(new chessmovetype(i,j,i,j-jj));
								break; //hope this breaks outa the for loop not the switch
							}
							else
								cmoves[cm++]=(new chessmovetype(i,j,i,j-jj));
						}
						for(int jj=1;(jj+j)<=7;++jj)
						{
							if(chessboard[i][j+jj]!=0)
							{
								if(sign(chessboard[i][j+jj])!=player)
									cmoves[cm++]=(new chessmovetype(i,j,i,j+jj));
								break; //hope this breaks outa the for loop not the switch
							}
							else
								cmoves[cm++]=(new chessmovetype(i,j,i,j+jj));
						}
						break;
					case 6://pawn
						if(player<0)
						{ //black
						    if(i==7)
							break;
						    if(i==1 && chessboard[i+1][j]==0 && chessboard[i+2][j]==0)
							cmoves[cm++]=(new chessmovetype(i,j,i+2,j));
						    if(j>0 && chessboard[i+1][j-1]>0)
							{
							    cmoves[cm++]=(new chessmovetype(i,j,i+1,j-1));
							    if(i==6)
								cmoves[cm-1].set_ispawnpromotion(true);
							}
						    if(j<7 && chessboard[i+1][j+1]>0)
							{
							    cmoves[cm++]=(new chessmovetype(i,j,i+1,j+1));
							    if(i==6)
								cmoves[cm-1].set_ispawnpromotion(true);
							}
						    if(chessboard[i+1][j]==0)
							{
							    cmoves[cm++]=(new chessmovetype(i,j,i+1,j));
							    if(i==6)
								cmoves[cm-1].set_ispawnpromotion(true);
							}
						}
						else
						{ //white
						    if(i==0)
							break;
						    if(i==6 && chessboard[i-1][j]==0 && chessboard[i-2][j]==0)
							cmoves[cm++]=(new chessmovetype(i,j,i-2,j));
						    if(j>0 && chessboard[i-1][j-1]<0)
							{
							    cmoves[cm++]=(new chessmovetype(i,j,i-1,j-1));
							    if(i==1)
								cmoves[cm-1].set_ispawnpromotion(true);
							}
						    if(j<7 && chessboard[i-1][j+1]<0)
							{
							    cmoves[cm++]=(new chessmovetype(i,j,i-1,j+1));
							    if(i==1)
								cmoves[cm-1].set_ispawnpromotion(true);
							}
						    if(chessboard[i-1][j]==0)
							{
							    cmoves[cm++]=(new chessmovetype(i,j,i-1,j));
							    if(i==1)
								cmoves[cm-1].set_ispawnpromotion(true);
							}
						}
						break;
				}
				
			}
		}
		//flag moves as capture? nah!		
		//chessmovetype[] cmt=new chessmovetype[v.size()];
		chessmovetype[] cmt=new chessmovetype[cm];
		System.arraycopy(cmoves,0,cmt,0,cm);
		//v.copyInto(cmt);
		return cmt;
	}
	public boardtype clone_board()
	{
		return new chessboard(this);
	}
	public void make_move(movetype move)
	{//hey! do pawn promotion or you'll get exceptions
		chessmovetype m=(chessmovetype)move;
		m.save_unmove_piece(chessboard[m.movetoy()][m.movetox()]);
		chessboard[m.movetoy()][m.movetox()]=
			chessboard[m.movefromy()][m.movefromx()];
		chessboard[m.movefromy()][m.movefromx()]=0;
		if(m.ispawnpromotion())
		    {
			chessboard[m.movetoy()][m.movetox()]=
			    (chessboard[m.movetoy()][m.movetox()]>0?1:-1)*2;
		    }
		if(m.iscastling())
		{
			if(m.movetox()>4)
			{//king side castling
			    chessboard[m.movefromy()][m.movefromx()+1]=
				chessboard[m.movefromy()][m.movefromx()+3];
			    chessboard[m.movefromy()][m.movefromx()+3]=0;
			}
			if(m.movetox()<4)
			{//queen side castling
			    chessboard[m.movefromy()][m.movefromx()-1]=
				chessboard[m.movefromy()][m.movefromx()-4];
			    chessboard[m.movefromy()][m.movefromx()-4]=0;
			}
		}
	}
	public void unmake_move(movetype move)
	{
		chessmovetype m=(chessmovetype)move;
		chessboard[m.movefromy()][m.movefromx()]=
			chessboard[m.movetoy()][m.movetox()];
		chessboard[m.movetoy()][m.movetox()]=m.unmove_piece();
		if(m.ispawnpromotion())
		    {
			chessboard[m.movefromy()][m.movefromx()]=
			    (chessboard[m.movefromy()][m.movefromx()]>0?1:-1)*6;
		    }
		if(m.iscastling())
		{
			if(m.movetox()>4)
			{//king side castling
				chessboard[m.movetoy()][m.movetox()+1]=
					chessboard[m.movetoy()][m.movetox()-1];
				chessboard[m.movetoy()][m.movetox()-1]=0;
			}
			else
			{//queen side castling
				chessboard[m.movetoy()][m.movetox()-2]=
					chessboard[m.movetoy()][m.movetox()+1];
				chessboard[m.movetoy()][m.movetox()+1]=0;
			}
		}
	}
}
