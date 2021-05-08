public class chessmovetype implements movetype
{
	public static final int WHITE=1;
	public static final int BLACK=-1;
	protected int movefromy;
	protected int movetoy;
	protected int movefromx;
	protected int movetox;
	protected int unmove_piece;
	protected boolean iscapture;
	protected boolean iscastling;
        protected boolean ispawnpromotion;
	public chessmovetype(int movefromy, int movefromx, int movetoy, int movetox)
	{
		this.movefromy=movefromy;
		this.movefromx=movefromx;
		this.movetoy=movetoy;
		this.movetox=movetox;
		this.unmove_piece=0;
		this.iscapture=false;
		this.ispawnpromotion=false;
	}
	public boolean equals(movetype mv)
	{
		chessmovetype chmv=(chessmovetype)mv;
		return (movefromy==chmv.movefromy &&
				movefromx==chmv.movefromx &&
				movetoy==chmv.movetoy &&
				movetox==chmv.movetox);
	}
	void set_ispawnpromotion(boolean b)
	{
	    ispawnpromotion=b;
	}
	void set_iscastling(boolean b)
	{
		iscastling=b;
	}
	void set_iscapture(boolean b)
	{
		iscapture=b;
	}	
	void save_unmove_piece(int piece)
	{
		unmove_piece=piece;
	}
	public void make_move(boardtype board)
	{
		board.make_move(this);
	}
	public void unmake_move(boardtype board)
	{
		board.unmake_move(this);
	}
	protected int movefromy()
	{
		return movefromy;
	}
	protected int movetoy()
	{
		return movetoy;
	}
	protected int movefromx()
	{
		return movefromx;
	}
	protected int movetox()
	{
		return movetox;
	}
	protected int unmove_piece()
	{
		return unmove_piece;
	}
	protected boolean ispawnpromotion()
	{
	    return ispawnpromotion;
	}
	protected boolean iscapture()
	{
		return iscapture;
	}
	protected boolean iscastling()
	{
		return iscastling;
	}
}
