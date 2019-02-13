import java.util.ArrayList;
import java.util.StringTokenizer;


public class ChercheMotifDUnMot {
	public String phrase, motif;
	public ChercheMotifDUnMot(String args, String Mot){
		if(args.equals("") || Mot.equals(""))
		{
			phrase = "Méthode d'apprentissage supervis�e qui raisonnent avec le principe sous-jacent dis-moi qui sont tes amis, je te dirais qui tu es�. Elle diff�re des m�thodes d'apprentissages traditionnelles car aucun mod�le n'est induit � partir d'exemple. A chaque fois que l'on veut classer un nouvel individu, on refait tourner l'algorithme et on cherche de nouveaux amis. Exemple : Si l'on veut pr�dire la probabilit� de survenue d'un cancer chez un nouveau patient on proc�de en deux �tapes : 1. On recherche selon les caract�ristiques de ce patient les patients qui lui ressemble 2. Si parmi ces �voisins�, il y a eu plus de cancer , alors le patient a une forte probabilit� d'avoir le cancer .";
			motif = "can";
		}else{
			phrase = args;
			motif = Mot;
		}
		
		
	}
	
	public String getMotifPositions()
	{
		String mots[] = phrase.split(" "), motifPos="";
		ArrayList pos = new ArrayList();
		int exits =0;
		for(int i=0; i<mots.length; i++)
		{
			if(mots[i].length()>motif.length() && mots[i].substring(0, motif.length()).equals(motif))
			{
				exits = 1;
				pos.add(i+1);
			}
		}
		
		if(exits == 1)
		{
			motifPos = "le motifs il exit et ses position : ";
			for(int i=0; i<pos.size(); i++)
			{
				motifPos += pos.get(i)+" ";
			}
		}else{
			motifPos = "le mot n'exit pas";
		}
		return motifPos;
	}
	public static void main(String args[])
	{
		ChercheMotifDUnMot m = new ChercheMotifDUnMot("","");
		System.out.println(m.getMotifPositions());
	}
}
