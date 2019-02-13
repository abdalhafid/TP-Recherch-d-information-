import java.util.StringTokenizer;


public class Nombredoccurence {
	public String phrase, mot;
	public  Nombredoccurence(String args, String mo){
		if(args.equals("") || mo.equals(""))
		{
			phrase = "M�thode d'apprentissage supervis�e qui raisonnent avec le principe sous-jacent dis-moi qui sont tes amis, je te dirais qui tu es�. Elle diff�re des m�thodes d'apprentissages traditionnelles car aucun mod�le n'est induit � partir d'exemple. A chaque fois que l'on veut classer un nouvel individu, on refait tourner l'algorithme et on cherche de nouveaux amis. Exemple : Si l'on veut pr�dire la probabilit� de survenue d'un cancer chez un nouveau patient on proc�de en deux �tapes : 1. On recherche selon les caract�ristiques de ce patient les patients qui lui ressemble 2. Si parmi ces �voisins�, il y a eu plus de cancer , alors le patient a une forte probabilit� d'avoir le cancer .";
			mot = "cancer";
		}else{
			phrase = args;
			mot=mo;
		}
		
		
	}
	
	public String getNumRep()
	{
		StringTokenizer st = new StringTokenizer(phrase);
		int num=0;
		while(st.hasMoreTokens())
		{
			if(st.nextToken().equals(mot))
			{
				num++;
			}
		}
		return "le nombre d'occurence de "+mot+" est: "+num;
	}
	
	
	public static void main(String args[])
	{
		Nombredoccurence m = new Nombredoccurence("", "");
		System.out.println(m.getNumRep());
	}
	
}
