import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;


public class ContextAvantEtArrier {
	public static String phrase, mot;
	public int taill;
	public ContextAvantEtArrier(String args, String Mot, int tail){
		if(args.equals("") || Mot.equals(""))
		{
			phrase = "M�thode d'apprentissage supervis�e qui raisonnent avec le principe sous-jacent dis-moi qui sont tes amis, je te dirais qui tu es�. Elle diff�re des m�thodes d'apprentissages traditionnelles car aucun mod�le n'est induit � partir d'exemple. A chaque fois que l'on veut classer un nouvel individu, on refait tourner l'algorithme et on cherche de nouveaux amis. Exemple : Si l'on veut pr�dire la probabilit� de survenue d'un cancer chez un nouveau patient on proc�de en deux �tapes : 1. On recherche selon les caract�ristiques de ce patient les patients qui lui ressemble 2. Si parmi ces �voisins�, il y a eu plus de cancer , alors le patient a une forte probabilit� d'avoir le cancer .";
			mot = "cancer";
			taill = tail;
		}else{
			phrase = args;
			mot = Mot;
			taill = tail;
		}
		
		
	}
	public ArrayList<String> getContext()
	{
		StringTokenizer st = new StringTokenizer(phrase);
		String mots[] = phrase.split(" "), con = "";
		ArrayList <String> conList = new ArrayList();
		for(int i=0; i<mots.length; i++)
		{
			
				if(mots[i].equals(mot))
				{
					if(taill>i)
					{
						con = "la taill est supérieur aux nombre des tous les mots avant le mot "+mot+" en position "+i+"!!";
					}else if(taill> (mots.length - (i+1)))
					{
						con = "la taill est supérieur aux nombre des tous les mots arriere le mot "+mot+" en position "+i+"!!";
					}else{
						con = "Context Avant de mot "+mot+" en position "+i+" est: "  ;
						for(int j=1; j<=taill; j++)
						{
							con += mots[i-j]+" ";
						}
						con += "\nContext Arriere est: ";
						for(int j=1; j<=taill; j++)
						{
							con += mots[i+j]+" ";
						}
					}
					conList.add(con);
				}
				
		}
		return conList	;
	}
	
	public static void main(String args[])
	{
		System.out.print("Entrez la taill de context: ");
		Scanner s =new Scanner(System.in);
		int n = s.nextInt();
		ContextAvantEtArrier m = new ContextAvantEtArrier("","", n);
		System.out.println();
		ArrayList mes = m.getContext();
		for(int i=0; i<mes.size(); i++)
		{
			System.out.println(mes.get(i));
			System.out.println();
		}
	}
}
