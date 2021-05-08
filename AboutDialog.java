import java.awt.*;

/*
  Displays about info
 */
public class AboutDialog extends Dialog
{
    public static final String ABOUT_TEXT=
	"En-Prise 5.0 Chess Engine\n"+
	"----------------------------------------------\n"+
	"Written by c0d3r \n\n"+
	"A demonstration of advanced Java graphics, "+
	"algorithmic programming, multithreaded programming, and object oriented design principles.  "+
	"This program is open source and free to all as I belive that knowledge "+
	"should be shared not hoarded and used to ones advantage for money and prestige.\n\n"+
	"TODO:\nFinish Checkmate Detection\nAdd En-Passant Rule.\nFix Castling to conform to standard chess rules\n"+
	"Optimize and precalculate for efficiency.\nFix hard coded values in code.\nDocumentation.\nLog games and keep "+
	"statistics on wins and losses\nDrawing and Stalemate.\nMore Strategy\nAlpha Beta Pruning\nKiller Heuristics";

    public AboutDialog(Frame parent)
    {
	super(parent,"About En-Prise",false);
	setLayout(new BorderLayout(20,20));
	TextArea txt=new TextArea(ABOUT_TEXT,10,40,TextArea.SCROLLBARS_VERTICAL_ONLY);
	txt.setText(ABOUT_TEXT);
	txt.setEditable(false);
	add(txt,BorderLayout.CENTER);
	Panel panel=new Panel(new FlowLayout(FlowLayout.CENTER));
	panel.add(new Button("OK"));
	add(panel,BorderLayout.SOUTH);
	pack();
	Dimension paneSize = getSize();
	Dimension screenSize = getToolkit().getScreenSize();
	setLocation((screenSize.width - paneSize.width) / 2,
			(screenSize.height - paneSize.height) / 2);
    }

    public boolean handleEvent(Event e)
    {
	switch (e.id)
	    {
	    case Event.ACTION_EVENT:
		if(e.target instanceof Button)
		    {
			dispose();
		    }
		return true;
	    case Event.WINDOW_DESTROY:
		if(e.target instanceof Dialog)
		    dispose();
		else
		    dispose();
		return true;
	    }
	return false;
    }
}
