import java.util.StringTokenizer;


public class RechercherLaPositionDUnMot {
	public static void main(String args[]){
		String phrase = "Méthode d'apprentissage supervisée qui raisonnent avec le principe sous-jacent dis-moi qui sont tes amis, je te dirais qui tu es";
		String mot = "raisonnent";
		String mots[] = phrase.split(" ");
		int pos=0, exits=0;
		for(int i=0; i<mots.length; i++)
		{
			if(mots[i].equals(mot))
			{
				exits = 1;
				pos = i+1;
				break;
			}
		}
		
		if(exits == 1)
		{
			System.out.print("le mots il exit et sa position : "+ pos);
		}else{
			System.out.print("le mot n'exit pas");
		}
		
	}
}
