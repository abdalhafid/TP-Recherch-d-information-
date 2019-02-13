import java.util.ArrayList;


public class LesPositionsDUnMot {
	String phrase, mot;
	public LesPositionsDUnMot(String args, String mo){
		if(args.equals("") || mo.equals(""))
		{
			phrase = "M�thode d'apprentissage supervis�e qui raisonnent avec le principe sous-jacent dis-moi qui sont tes amis, je te dirais qui tu es�. Elle diff�re des m�thodes d'apprentissages traditionnelles car aucun mod�le n'est induit � partir d'exemple. A chaque fois que l'on veut classer un nouvel individu, on refait tourner l'algorithme et on cherche de nouveaux amis. Exemple : Si l'on veut pr�dire la probabilit� de survenue d'un cancer chez un nouveau patient on proc�de en deux �tapes : 1. On recherche selon les caract�ristiques de ce patient les patients qui lui ressemble 2. Si parmi ces �voisins�, il y a eu plus de cancer , alors le patient a une forte probabilit� d'avoir le cancer";
			mot = "cancer";
		}else
		{
			phrase = args;
			mot=mo;
		}
		
		
		
	}
	public String getPositions()
	{
		String mots[] = phrase.split(" ");
		int  exits=0;
		ArrayList pos = new ArrayList();
		for(int i=0; i<mots.length; i++)
		{
			if(mots[i].equals(mot))
			{
				exits = 1;
				pos.add(i+1);
			}
		}
		String result;
		if(exits == 1)
		{
			result = "le mots il exit et ses position : ";
			for(int i=0; i<pos.size(); i++)
			{
				result += pos.get(i)+" ";
			}
		}else{
			result = "le mot n'exit pas";
		}
		return result;
	}
	
	public static void main(String args[])
	{
		LesPositionsDUnMot m = new LesPositionsDUnMot("", "");
		System.out.println(m.getPositions());
	}
}
