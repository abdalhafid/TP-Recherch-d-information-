package TP2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


import javax.swing.SwingWorker;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;


public class exo4 {
	//definition des variable qui doive etre globale
		public static DocumentBuilder db;
		public static Document doc ; 
		public static boolean isPressed =false, cont=false;
		public static int selectedItem, progs;
		final static String[] metaCharacters = {"^","$","{","}","[","]","(",")",".","*","+","?","|","<",">","-","&","%", ",", ":", "1", "2", "3", "4", "5", "6", "7", "8", "9", String.valueOf('"')};
		static String motVides="", docP;
		public static JProgressBar progress = new JProgressBar(0, 100);
		final static JFrame prof = new JFrame("Proccess des document");
		final static JPanel proo = new JPanel(new FlowLayout());
		public final static JComboBox listCorpus = new JComboBox();
		public static boolean strcomp(String s1, String s2)
		{
			System.out.println(s1+" et "+s2+" == "+s1.equals(s2));
			return s1.equals(s2);
		}
		public static String analyseLixical(File file)
		{
			try {
				FileReader f = new FileReader(file);
				BufferedReader bf = new BufferedReader(f);
				String line="", mots[], text1="";
				String letter, nmot="";
				int i=0, j;
				final String[] motsVide = motVides.split(" ");
				FileReader swr = new FileReader(new File("stopwords_fr.txt"));
				BufferedReader swbr = new BufferedReader(swr);
				while(swbr.ready())
				{
					motVides += swbr.readLine()+" ";
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
							for(int mi=0; mi<metaCharacters.length; mi++)
							{
								
								
								if(letter.equals(metaCharacters[mi]))
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
						/*for(int j=0; j<motsVide.length; j++)
						{
							if(motsVide[j].equals(mots[i]))
							{
								isVide = true;
							}
							
						}*/
						j=0;
						while(!motsVide[j].equals(mots[i]) && j<motsVide.length)
							j++; 
						if(j>=motsVide.length)
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
		
		
		
		public static String extractKeyWords(String selectedCorpus) throws IOException, TransformerException
		{
			
			ArrayList <Terme> mot = new ArrayList();
			Terme t = new Terme("Mthode",10);
			String []mots;
			String []tt;
			String txt;
			Element root = doc.getDocumentElement(), document, document1, corpus, nom;
			FileReader fr;
			BufferedReader bfr;
			NodeList corpusList = root.getElementsByTagName("corpus"), documents, noms;
			int num = 0, numDoc=0, numDocCount=0, intc, pos[], proDocCount = 0, h1, j2;
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
				if(nom.getTextContent().equals(selectedCorpus))
				{
					
					for(int j=0; j<documents.getLength(); j++)
					{
						mot.clear();
						document = (Element) documents.item(j);
						txt = analyseLixical(new File(document.getAttribute("path")));
						txt = analyseLixical(new File(document.getAttribute("path")));
						mots = txt.split(" ");
						result += "document: "+document.getAttribute("path")+":\n";
						progs = 0;
						docP = document.getAttribute("path");
						
						/*for(int ahm=0; ahm<mots.length; ahm++)
						{
							
						}*/
						
						//extrqction des défférente mots exist dans le document sans répitition 
						for(int k=0; k<mots.length; k++)
						{
							
							exists = false;
							
							/*for(int h1=0; h1<mot.size(); h1++)
							{
								
								if(mot.get(h1).isMyTerm(mots[k]))
								{
									exists = true;
									
									
								}

							}*/
							h1=0;
							while(!mot.get(h1).isMyTerm(mots[k]) && h1<mot.size())
								h1++;
							
							if(h1<=mot.size())
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
								/*for(int j2=0; j2<tt.length; j2++)
								{
									if(mot.get(h).isMyTerm(tt[j2]))
									{
										exist2 = true;
									}
								}*/
								j2=0;
								while(!mot.get(h).isMyTerm(tt[j2]) && j2<=tt.length)
									j2++;
								
								if(exist2)
									numDocCount++;
							}
							frs = Math.log((double)numDoc/(double)numDocCount);
							mot.get(h).setIDF(frs);
							result += mot.get(h).getTerme()+"//: tf="+mot.get(h).getTF()+", idf="+mot.get(h).getIDF()+"=> tf/idf="+(mot.get(h).getTFSurIDF())+"\n";
							progs = (h*100)/mot.size();
							progress.setValue(progs);
							
						}
						
					}
				}
				
				
				
			}
			return result;
		}
		
		
		
		public static void upadteCorpusList( JComboBox listCorpus)
		{
			listCorpus.removeAllItems();
			Element root = doc.getDocumentElement();
			NodeList corpusList = root.getElementsByTagName("corpus");
			for(int i=0; i<corpusList.getLength(); i++)
			{
				Element crp = (Element) corpusList.item(i);
				NodeList nomes = crp.getElementsByTagName("nom");
				Element nom = (Element) nomes.item(0);
				listCorpus.addItem(nom.getTextContent());
			}
		}
		public static void update(JComboBox listDoc, JTextField nbrDeDoc, JComboBox listCorpus)
		{
			listDoc.removeAllItems();
			
			Element root = doc.getDocumentElement();
			NodeList corpusList = root.getElementsByTagName("corpus");
			Element corpus = null;
			int i=0;
			Element nom=null;
			do
			{

				Element crp = (Element) corpusList.item(i);
				NodeList nomes = crp.getElementsByTagName("nom");
				nom = (Element) nomes.item(0);
					corpus = crp;
					i++;
			}while(!nom.getTextContent().equals(listCorpus.getSelectedItem().toString()) && i < corpusList.getLength());
			NodeList documentList = corpus.getElementsByTagName("document");
			for(i=0; i<documentList.getLength(); i++)
			{
				Element docm = (Element) documentList.item(i);
				listDoc.addItem(docm.getAttribute("path"));
				
			}
			nbrDeDoc.setText(String.valueOf(i));
		}
		public static void main(String args[]) throws BadLocationException, IOException
		{
			
			// l'interface 
			JFrame f = new JFrame("Les corpus");
			f.setSize(800, 500);
			f.setResizable(false);
			f.setLocationRelativeTo(null);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			final JPanel fenetre = new JPanel();
			fenetre.setLayout(new BoxLayout(fenetre, BoxLayout.Y_AXIS));
			fenetre.setBackground(Color.YELLOW);
			JPanel bar1 = new JPanel();
			FlowLayout fl = new FlowLayout();
			fl.setAlignment(fl.RIGHT);
			Font fnt = new Font("Arial", Font.ITALIC, 12);
			bar1.setLayout(fl);
			bar1.setBackground(Color.yellow);
			final JTextField nameDeCorpus = new JTextField(12);
			JButton creeCorpus = new JButton("Créer Corpus");
			creeCorpus.setPreferredSize(new Dimension(150, 20));
			creeCorpus.setFont(fnt);
			
			
			bar1.setBorder(BorderFactory.createTitledBorder("Corpus"));
			bar1.add(listCorpus);
			bar1.add(nameDeCorpus);
			bar1.add(creeCorpus);
			
			JPanel bar2 = new JPanel();
			bar2.setLayout(new BoxLayout(bar2, BoxLayout.Y_AXIS));
			bar2.setBackground(Color.yellow);
			bar2.setBorder(BorderFactory.createTitledBorder("Document"));
			final JTextField documentPath = new JTextField(55);
			JButton browser = new JButton("Ajouter");
			browser.setFont(fnt);
			browser.setPreferredSize(new Dimension(150, 20));
			JPanel bar21 = new JPanel(new FlowLayout());
			bar21.add(documentPath);
			bar21.add(browser);
			bar21.setBackground(Color.yellow);
			JLabel LDD = new JLabel("Liste de documment");
			LDD.setFont(fnt);
			final JComboBox listDoc = new JComboBox();
			listDoc.setMaximumSize(new Dimension(400, 30));
			listDoc.setAlignmentX(JComboBox.LEFT_ALIGNMENT);
			JPanel bar221 = new JPanel();
			bar221.setLayout(new BoxLayout(bar221, BoxLayout.Y_AXIS));
			bar221.add(LDD);
			bar221.add(listDoc);
			JLabel text1 = new JLabel("Nombre de document dans le corpus");
			final JTextField nbrDeDoc = new JTextField(5);
			text1.setAlignmentX(JLabel.LEFT_ALIGNMENT);
			nbrDeDoc.setAlignmentX(JTextField.LEFT_ALIGNMENT);
			nbrDeDoc.setEditable(false);
			JPanel bar22 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
			bar22.setBackground(Color.yellow);
			bar221.setBackground(Color.yellow);
			bar221.setPreferredSize(new Dimension(460, 50));
			bar22.add(bar221);
			bar22.add(text1);
			bar22.add(nbrDeDoc);
			JPanel bar231 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
			JPanel bar232 = new JPanel(new FlowLayout());
			JPanel bar23 = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 10));
			bar23.setBackground(Color.yellow);
			bar231.setBackground(Color.yellow);
			bar231.setPreferredSize(new Dimension(556, 30));
			bar232.setBackground(Color.yellow);
			JButton btn1 = new JButton("Supprimer");
			JButton btn2 = new JButton("Consulter");
			JButton btn3 = new JButton("Rechercher");
			final JButton btn4 = new JButton("exporter analyse des terme ");
			btn1.setPreferredSize(new Dimension(100, 20));
			btn1.setFont(fnt);
			
			btn2.setPreferredSize(new Dimension(100, 20));
			btn2.setFont(fnt);
			
			btn3.setPreferredSize(new Dimension(100, 20));
			btn3.setFont(fnt);
			
			btn4.setPreferredSize(new Dimension(190, 20));
			btn4.setFont(fnt);
			
			final JTextField mot = new JTextField(8);
			final JTextArea monText = new JTextArea();
			monText.setLineWrap(true);
			monText.setWrapStyleWord(true);
			JScrollPane scrollMonText = new JScrollPane(monText);
			scrollMonText.setPreferredSize(new Dimension(500,400));
			bar231.add(btn1);
			bar231.add(btn2);
			bar231.add(btn4);
			bar232.add(btn3);
			bar232.add(mot);
			bar23.add(bar231);
			bar23.add(bar232);
			
			bar2.setPreferredSize(new Dimension(500, 400));
			bar2.add(bar21);
			bar2.add(bar22);
			bar2.add(bar23);
			bar2.add(scrollMonText);
			fenetre.add(bar1);
			fenetre.add(bar2);
			
			
			f.add(fenetre);
			f.setVisible(true);
			
			
			//le proccess 
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try {
				db = dbf.newDocumentBuilder();
				doc = db.parse(new File("baseCorpus.xml"));
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			upadteCorpusList(listCorpus);
			selectedItem = listCorpus.getSelectedIndex();
			creeCorpus.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ev)
				{
					if(!nameDeCorpus.getText().equals(""))
					{
						Element base=doc.getDocumentElement();
						Element corpus = doc.createElement("corpus");
						Element nom = doc.createElement("nom");
						Text txt=doc.createTextNode(nameDeCorpus.getText());
						nom.appendChild(txt);
						corpus.appendChild(nom);
						base.appendChild(corpus);
						TransformerFactory tf= TransformerFactory.newInstance();
						try {
							
							DOMSource dom = new DOMSource(doc);
							Transformer t = tf.newTransformer();
							StreamResult sr= new StreamResult(new File("baseCorpus.xml"));
							//StreamResult show= new StreamResult(System.out);
							t.transform(dom,  sr);
							//t.transform(dom,  show);
							selectedItem=listCorpus.getSelectedIndex();
							upadteCorpusList(listCorpus);
							isPressed = true;
							listCorpus.setSelectedIndex(selectedItem);
							isPressed = false;
						} catch (TransformerConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (TransformerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}else
					{
						JOptionPane.showMessageDialog(fenetre, "Vous dovez repmlir le champs de nom de corpus!!");
					}
				}
			});
			browser.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent av)
				{
					JFileChooser fc = new JFileChooser();
					String[] extensions = {"txt"};
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Document Text (.txt)", extensions);
					fc.setFileFilter(filter);
					fc.setAcceptAllFileFilterUsed(false);
					int c = fc.showOpenDialog(fenetre);
					if(c == 0)
					{
						File file = fc.getSelectedFile();
						documentPath.setText(file.getAbsolutePath());
						NodeList corpusList = doc.getElementsByTagName("corpus");
						Element corpus=null;
						int i=0;
						String nm="";
						do{
							corpus = (Element) corpusList.item(i);
							NodeList noms = corpus.getElementsByTagName("nom");
							Element nom = (Element) noms.item(0);
							nm = nom.getTextContent();
							i++;
						}while(!nm.equals(listCorpus.getSelectedItem().toString()) && i<corpusList.getLength());
									
						Element document = doc.createElement("document");
						document.setAttribute("path", file.getAbsolutePath());
						if(corpus != null)
						{
							corpus.appendChild(document);
							try {
								TransformerFactory tf = TransformerFactory.newInstance();
								Transformer t= tf.newTransformer();
								DOMSource nd = new DOMSource(doc);
								StreamResult save = new StreamResult(new File("baseCorpus.xml"));
								//StreamResult show = new StreamResult(System.out);
								t.transform(nd, save);
								//t.transform(nd, show);
								update(listDoc, nbrDeDoc, listCorpus);
								JOptionPane.showMessageDialog(null, "le decoment "+file.getName()+" été ajouter");
							} catch (TransformerConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (TransformerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else
							JOptionPane.showMessageDialog(null, "il n' y a pas aucune corpus !");
						
						
						
						
					}
				}
			});
			
			/*
			 	Element root = doc.getDocumentElement();
			NodeList corpusList = root.getElementsByTagName("corpus");
			Element corpus = (Element) corpusList.item(0);
			if(corpus !=null )
			{
				NodeList documentList = corpus.getElementsByTagName("document");
				int i;
				for(i=0; i<documentList.getLength(); i++)
				{
					Element docm = (Element) documentList.item(i);
					listDoc.addItem(docm.getAttribute("path"));
					
				}
				nbrDeDoc.setText(String.valueOf(i));
			}*/
			
			// button de supprission 
			btn1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ec)
				{
					Element root = doc.getDocumentElement();
					NodeList corpusList = root.getElementsByTagName("corpus");
					
					Element corpus=null;
					int ii=0;
					String nm="";
					do{
						corpus = (Element) corpusList.item(ii);
						NodeList noms = corpus.getElementsByTagName("nom");
						Element nom = (Element) noms.item(0);
						nm = nom.getTextContent();
						ii++;
					}while(!nm.equals(listCorpus.getSelectedItem().toString()) && ii<corpusList.getLength());
								
					if(corpus !=null )
					{
						String pathAcual = listDoc.getSelectedItem().toString();
						NodeList documentList = corpus.getElementsByTagName("document");
						int i;
						for(i=0; i<documentList.getLength(); i++)
						{
							Element docm = (Element) documentList.item(i);
							if(docm.getAttribute("path").equals(pathAcual))
							{
								docm.getParentNode().removeChild(docm);
								TransformerFactory tf = TransformerFactory.newInstance();
								try {
									Transformer t = tf.newTransformer();
									DOMSource d = new DOMSource(doc);
									StreamResult st= new StreamResult(new File("baseCorpus.xml"));
									t.transform(d, st);
									update(listDoc, nbrDeDoc, listCorpus);
								} catch (TransformerConfigurationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (TransformerException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
							
						}
					}
				}
			});
			 
			
			btn3.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ec)
				{
					String p = listDoc.getSelectedItem().toString();
					File f = new File(p);
					FileReader fr;
					try {
						fr = new FileReader(f);
						BufferedReader bf = new BufferedReader(fr);
						String text ="";
						while(bf.ready())
						{
							text += bf.readLine()+"\n";
						}
						monText.setText(text);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					HighlightPainter hp = new DefaultHighlighter.DefaultHighlightPainter(Color.yellow);
					Highlighter hi= monText.getHighlighter();
					String text = monText.getText();
					int pos=0;
					while((pos=text.indexOf(mot.getText(), pos))>0)
					{
						try {
							hi.addHighlight(text.indexOf(mot.getText(), pos), pos+mot.getText().length(), hp);
							pos=pos+mot.getText().length();
						} catch (BadLocationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			
			btn2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ec)
				{
					String p = listDoc.getSelectedItem().toString();
					File f = new File(p);
					FileReader fr;
					try {
						fr = new FileReader(f);
						BufferedReader bf = new BufferedReader(fr);
						String text ="";
						while(bf.ready())
						{
							text += bf.readLine()+"\n";
						}
						monText.setText(text);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			update(listDoc, nbrDeDoc, listCorpus);
			
			listCorpus.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0) {
					
					/*listDoc.removeAllItems();
					Element root = doc.getDocumentElement();
					NodeList corpusList = root.getElementsByTagName("corpus");
					
					
					if(corpusList.getLength() !=0 )
					{
						Element corpus = null;
						for(int i=0; i<corpusList.getLength(); i++)
						{
							Element crp = (Element) corpusList.item(i);
							NodeList nomes = crp.getElementsByTagName("nom");
							Element nom = (Element) nomes.item(0);
							
							if(nom!=null && listCorpus!=null)
							{
								if(nom.getTextContent().equals(listCorpus.getSelectedItem().toString()));
								{
									corpus = crp;
								}
							}
							
						}
						
						NodeList documentList = corpus.getElementsByTagName("document");
						int i;
						for(i=0; i<documentList.getLength(); i++)
						{
							Element docm = (Element) documentList.item(i);
							listDoc.addItem(docm.getAttribute("path"));
							
						}
						nbrDeDoc.setText(String.valueOf(i));
					}*/
					if(isPressed)
					{
						update(listDoc, nbrDeDoc, listCorpus);
						isPressed =false;
					}
				}
				
			});
			
			
			
			
			
			
			btn4.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ex)
				{
					
					
				    fenetre.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					Task ts = new Task();
					ts.execute();
					/*try {
						
						extractKeyWords(listCorpus.getSelectedItem().toString());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TransformerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					fenetre.setCursor(null);
				}
			});
			
			
			
			
			
			listCorpus.addMouseListener(new MouseListener(){

				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				public void mouseEntered(MouseEvent arg0) {
					isPressed = true;
					selectedItem =  listCorpus.getSelectedIndex();
					
					
				}

				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				public void mousePressed(MouseEvent arg0) {
					
					
				}

				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			proo.setBackground(Color.black);
			prof.setSize(300, 100);
			prof.setLocationRelativeTo(f);
			proo.add(progress);
			prof.add(proo);
			prof.setVisible(false);
			exo4.progress.setStringPainted(true);
			
			progress.addPropertyChangeListener(new PropertyChangeListener(){
				
				public void propertyChange(PropertyChangeEvent arg0) {
				            progress.setValue(progs);
					
				}
			});
			
			
			
		}
}
