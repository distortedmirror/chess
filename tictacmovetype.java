/*
  Defines a move in TTT with associated tasks
 */
public class tictacmovetype implements movetype
{
  public static final int XPLAYER=1;
  public static final int OPLAYER=-1;
  protected int player;
  protected int move;
  public tictacmovetype()
  {
    player=move=0;
  }
  public tictacmovetype(int move,int player)
  {
    this.player=player;
    this.move=move;
  }
  public boolean equals(movetype mv)
  {
	  tictacmovetype ttmv=(tictacmovetype)mv;
	  return (player==ttmv.player &&
				move==ttmv.move);
  }
  public void make_move(boardtype board)
  {
    board.make_move(this);
  }
  public void unmake_move(boardtype board)
  {
    board.unmake_move(this);
  }
  protected int player()
  {
    return player;
  }
  protected int move()
  {
    return move;
  }
}

