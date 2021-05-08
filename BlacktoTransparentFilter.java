import java.awt.*;
import java.awt.image.*;

/*
  Converts black into a transparent pixel value
 */
public class BlacktoTransparentFilter extends RGBImageFilter
{
	public BlacktoTransparentFilter()
	{
		canFilterIndexColorModel=true;
	}
	public int filterRGB(int x,int y, int rgb)
	{
		if(rgb==Color.black.getRGB())
			return 0x00000000;
		else 
			return rgb;
	}
}
