/*
  Game boards are defined by implementing this
  interface.
 */
public abstract interface boardtype
{
  public static final int DRAW=3;
  public static final int NO_WIN=0;
  public abstract int evaluate_position();
  public abstract int win();
  public abstract movetype[] generate_moves(int player);
  public abstract boardtype clone_board();
  public abstract void make_move(movetype move);
  public abstract void unmake_move(movetype move);
}
