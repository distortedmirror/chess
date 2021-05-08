/*
    The interface implemented to choose a move
    from a board position
 */
public abstract interface DecisionMaker
{
  public abstract movetype makedecision(boardtype board);
}
