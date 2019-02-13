import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class LireFicher {
	public File path ;
	public LireFicher(File args) 
	{
		
			path = args;
		
	}
	public String getFileText() throws IOException
	{
		String text = "";
		if(path.exists())
		{
			FileReader fichier = new FileReader(path);
			BufferedReader bf= new BufferedReader(fichier);
			while(bf.ready())
			{
				text += bf.readLine();
			}
		}else{
			text = "le fichier n'exist pas ";
		}
		
		return text;
	}
	
	public static void main(String args[]) throws IOException
	{
		LireFicher m = new LireFicher(new File("stopwords_fr.txt"));
		System.out.println(m.getFileText());
	}
}
