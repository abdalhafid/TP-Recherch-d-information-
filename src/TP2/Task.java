package TP2;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
public class Task extends SwingWorker implements PropertyChangeListener{

	
	String text;
	public Task()
	{
		
	}
	
	public static String analyseLixical(File file)
	{
		try {
			FileReader f = new FileReader(file);
			BufferedReader bf = new BufferedReader(f);
			String line="", mots[], text1="";
			String letter, nmot="";
			int i=0;
			final String[] motsVide = exo4.motVides.split(" ");
			FileReader swr = new FileReader(new File("stopwords_fr.txt"));
			BufferedReader swbr = new BufferedReader(swr);
			while(swbr.ready())
			{
				exo4.motVides += swbr.readLine()+" ";
			}
			while(bf.ready())
			{
				line = bf.readLine();
				mots = line.split(" ");
				boolean isVide=false, isSpecial=false;
				
				
				for(i=0; i<mots.length; i++)
				{
					
					nmot="";
					for(int mol=0; mol<mots[i].length(); mol++)
					{
						
						isSpecial=false;
						letter=mots[i].substring(mol, mol+1);
						for(int mi=0; mi<exo4.metaCharacters.length; mi++)
						{
							
							
							if(letter.equals(exo4.metaCharacters[mi]))
								{isSpecial=true;}
							
							if(letter.equals("'"))
							{
								nmot="";
								isSpecial=true;
							}
						}
						if(!isSpecial)
							{nmot += letter;}
						
						
					}
					mots[i]=nmot;
					
				}
				
				
				
				for(i=0; i<mots.length; i++)
				{
					isVide = false;
					for(int j=0; j<motsVide.length; j++)
					{
						if(motsVide[j].equals(mots[i]))
						{
							isVide = true;
						}
						
					}
					if(!isVide)
						text1 += mots[i]+" ";
					
				}
			}
			
			return text1;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (IOException e) {
			return null;
		}
	}
	
	
	
	
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		
			exo4.prof.setVisible(true);
			ArrayList <Terme> mot = new ArrayList();
			Terme t = new Terme("Mthode",10);
			String []mots;
			String []tt;
			String txt;
			Element root = exo4.doc.getDocumentElement(), document, document1, corpus, nom;
			FileReader fr;
			BufferedReader bfr;
			NodeList corpusList = root.getElementsByTagName("corpus"), documents, noms;
			int num = 0, numDoc=0, numDocCount=0, intc, pos[], proDocCount = 0 ;//h1, j2;
			String txt1, result="", hh;
			boolean exist1, exist2;
			boolean exists ;
			double frs;
			for(int i=0; i<corpusList.getLength(); i++)
			{
				
				corpus= (Element) corpusList.item(i);
				documents = corpus.getElementsByTagName("document");
				noms = corpus.getElementsByTagName("nom");
				nom = (Element) noms.item(0);
				if(nom.getTextContent().equals(exo4.listCorpus.getSelectedItem().toString()))
				{
					
					for(int j=0; j<documents.getLength(); j++)
					{
						mot.clear();
						document = (Element) documents.item(j);
						txt = analyseLixical(new File(document.getAttribute("path")));
						txt = analyseLixical(new File(document.getAttribute("path")));
						
						mots = txt.split(" ");
						result += "document: "+document.getAttribute("path")+":\n";
						exo4.progs = 0;
						exo4.docP = document.getAttribute("path");
						
						/*for(int ahm=0; ahm<mots.length; ahm++)
						{
							
						}*/
						
						//extrqction des défférente mots exist dans le document sans répitition 
						for(int k=0; k<mots.length; k++)
						{
							
							exists = false;
							
							for(int h1=0; h1<mot.size(); h1++)
							{
								
								if(mot.get(h1).isMyTerm(mots[k]))
								{
									exists = true;
									
									
								}

							}
							/*h1=0;
							while(!mot.get(h1).isMyTerm(mots[k]) && h1<mot.size())
								h1++;
							System.out.println(h1);*/
							if(!exists)
							{
								mot.add(new Terme(mots[k]));
								
							}
						}
						
						
						for(int h=0; h<mot.size(); h++)
						{
							num=0;
							numDocCount=0;
							numDoc=0;
							for(int j1 = 0; j1<mots.length; j1++)
							{
								if(mot.get(h).getTerme().equals(mots[j1]))
								{
									num++;
								}
							}
							mot.get(h).setFreq(num);
							
							for(int j1 = 0; j1<documents.getLength(); j1++)
							{
								numDoc++;
								
								document1 = (Element) documents.item(j1);
								txt1 = analyseLixical(new File(document1.getAttribute("path")));
								tt = txt1.split(" ");
								exist2=false;
								for(int j2=0; j2<tt.length; j2++)
								{
									if(mot.get(h).isMyTerm(tt[j2]))
									{
										exist2 = true;
									}
								}
								/*j2=0;
								while(!mot.get(h).isMyTerm(tt[j2]) && j2<=tt.length)
									j2++;*/
								
								if(exist2)
									numDocCount++;
							}
							frs = Math.log((double)numDoc/(double)numDocCount);
							mot.get(h).setIDF(frs);
							result += mot.get(h).getTerme()+":\ttf="+mot.get(h).getTF()+",\tidf="+mot.get(h).getIDF()+",\ttf/idf="+(mot.get(h).getTFSurIDF())+"\n";
							exo4.progs = (h*100)/mot.size();
							exo4.progress.setValue(exo4.progs);
						}
						
					}
				}
				
				
				
			}
			text = result;
		return null;
	}
	
	
	public void done() {
        Toolkit.getDefaultToolkit().beep();
        exo4.prof.setVisible(false);
        try {
			FileWriter fr = new FileWriter(new File("exo4 result.txt"));
			BufferedWriter bfr = new BufferedWriter(fr);
			String lines[] = text.split("\n");
			for(int i=0; i<lines.length; i++)
			{
				bfr.write(lines[i]);
				bfr.newLine();
			}
			bfr.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(text);
    }

	public void propertyChange(PropertyChangeEvent arg0) {
		
		
	}

}
