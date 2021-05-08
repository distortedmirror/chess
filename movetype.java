/*
  All movements for two player games are defined
  by implementing this interface
 */
public abstract interface movetype
{
  public abstract void make_move(boardtype board);
  public abstract void unmake_move(boardtype board);
  public abstract boolean equals(movetype move);
}
