/*
  Defines a checkers move.
 */
public class checkersmovetype implements movetype
{
	public static final int WHITE=1;
	public static final int BLACK=-1;
	protected int movefromy;
	protected int movetoy;
	protected int movefromx;
	protected int movetox;
	protected int unmove_piece;
	protected int jumpedsquarex;
	protected int jumpedsquarey;
	protected boolean iscapture;
	protected boolean is_jump;
	public checkersmovetype(int movefromy, int movefromx, int movetoy, int movetox)
	{
		this.movefromy=movefromy;
		this.movefromx=movefromx;
		this.movetoy=movetoy;
		this.movetox=movetox;
		this.unmove_piece=0;
		this.iscapture=false;
		this.is_jump=false;
	}
	public boolean equals(movetype mv)
	{
		checkersmovetype chmv=(checkersmovetype)mv;
		return (movefromy==chmv.movefromy &&
				movefromx==chmv.movefromx &&
				movetoy==chmv.movetoy &&
				movetox==chmv.movetox);
	}
	public void set_iscapture(boolean b)
	{
		iscapture=b;
	}
	public void set_isjump(boolean b)
	{
		is_jump=b;
	}
	public void set_jumpedsquarex(int x)
	{
		jumpedsquarex=x;
	}
	public void set_jumpedsquarey(int y)
	{
		jumpedsquarey=y;
	}
	public int jumpedsquarex()
	{
		return jumpedsquarex;
	}
	public int jumpedsquarey()
	{
		return jumpedsquarey;
	}
	boolean is_jump()
	{
		return is_jump;
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
	protected boolean iscapture()
	{
		return iscapture;
	}
}
