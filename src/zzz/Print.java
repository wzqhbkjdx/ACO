package zzz;

import java.io.File;
import java.io.PrintStream;

public class Print 
{
	public void PP(int[][] plank) throws Exception
	{
		File toFile = new File("q:/RFILMS/" + "pp" + ".txt");
    	if(!toFile.exists())
    	{
    		toFile.createNewFile();
    	}
    	PrintStream out = new PrintStream(toFile);
    	System.setOut(out);
		for(int i = 0; i < plank.length; i++)
		{
			for(int j = 0; j < plank[0].length; j++)
			{
				System.out.print(plank[i][j]);
			}
			System.out.println("");
		}
	}

}
