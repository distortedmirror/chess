import java.awt.*;

/*
  Used to select a level
 */
public class LevelDialog extends Dialog
{
    Checkbox[] cbxes;
    
    public LevelDialog(Frame parent)
    {
	super(parent,"Please Select a Level (Ply 3 to 6)",false);
	setLayout(new BorderLayout(20,20));
	Panel p1=new Panel();
	CheckboxGroup cbg=new CheckboxGroup();
	cbxes=new Checkbox[4];
	cbxes[0]=new Checkbox("Easiest",cbg,searchthread.SEARCHPLY==4?true:false);
	cbxes[1]=new Checkbox("Easier",cbg,searchthread.SEARCHPLY==5?true:false);
	cbxes[2]=new Checkbox("Easy",cbg,searchthread.SEARCHPLY==6?true:false);
	cbxes[3]=new Checkbox("Still Easy",cbg,searchthread.SEARCHPLY==7?true:false);
	p1.add(cbxes[0]);
	p1.add(cbxes[1]);
	p1.add(cbxes[2]);
	p1.add(cbxes[3]);
	Panel p2=new Panel();
	p2.add(new Button("OK"));
	add("Center",p1);
	add("South",p2);
	pack();
    }
    
    public boolean handleEvent(Event e)
    {
	switch (e.id)
	    {
	    case Event.ACTION_EVENT:
		if(e.target instanceof Checkbox)
		    {
			if(e.target==cbxes[0])
			    {
				searchthread.SEARCHPLY=4;
			    }
			else if(e.target==cbxes[1])
			    {
				searchthread.SEARCHPLY=5;
			    }
			else if(e.target==cbxes[2])
			    {
				searchthread.SEARCHPLY=6;
			    }
			else if(e.target==cbxes[3])
			    {
				searchthread.SEARCHPLY=7;
			    }
		    }
		else if(e.target instanceof Button)
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
	    }//switch
	return false;
    }
}
