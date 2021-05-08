/*
  A graphical checkers game
 */
public class checkers
{
    static checkersgui cg=null;

    public static void main(String[] args)
    {
	newgame();
    }
    public static void newgame()
    {
	if(cg!=null)
	    cg.dispose();
	checkersboard board=new checkersboard();
	cg=new checkersgui(board);
	cg.resize(cg.width(),cg.height()+8);
	cg.show();
    }
}
