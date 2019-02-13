import java.util.*;
public class ConterLesMotsDUnChaine {
	public  String phrase;
	private int numMots;
	public  ConterLesMotsDUnChaine(String args){
		if(args.equals(""))
			phrase = "M�thode d'apprentissage supervis�e qui raisonnent avec le principe sous-jacent dis-moi qui sont tes amis, je te dirais qui tu es";
		else
			phrase = args;

	}
	public int getNumMots()
	{
		StringTokenizer st = new StringTokenizer(phrase);
		return st.countTokens();
	}
	
	public static void main(String args[])
	{
		ConterLesMotsDUnChaine m = new ConterLesMotsDUnChaine("");
		System.out.println(m.getNumMots());
	}
}
