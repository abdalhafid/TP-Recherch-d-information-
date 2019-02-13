package TP2;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class Corpus {
	//definition des variable qui doive etre globale
	public static DocumentBuilder db;
	public static Document doc ;
	public static void update(JComboBox listDoc, JTextField nbrDeDoc)
	{
		listDoc.removeAllItems();
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
		}
	}
	public static void main(String args[]) throws BadLocationException
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
		
		btn1.setPreferredSize(new Dimension(100, 20));
		btn1.setFont(fnt);
		
		btn2.setPreferredSize(new Dimension(100, 20));
		btn2.setFont(fnt);
		
		btn3.setPreferredSize(new Dimension(100, 20));
		btn3.setFont(fnt);
		
		final JTextField mot = new JTextField(8);
		final JTextArea monText = new JTextArea();
		monText.setLineWrap(true);
		monText.setWrapStyleWord(true);
		JScrollPane scrollMonText = new JScrollPane(monText);
		scrollMonText.setPreferredSize(new Dimension(500,400));
		bar231.add(btn1);
		bar231.add(btn2);
		
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
		
		creeCorpus.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev)
			{
				if(!nameDeCorpus.getText().equals(""))
				{
					Element base=doc.getDocumentElement() ;
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
						StreamResult show= new StreamResult(System.out);
						t.transform(dom,  sr);
						t.transform(dom,  show);
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
					Element corpus = (Element) corpusList.item(0);
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
							StreamResult show = new StreamResult(System.out);
							t.transform(nd, save);
							t.transform(nd, show);
							update(listDoc, nbrDeDoc);
							JOptionPane.showMessageDialog(null, "le decoment "+file.getName()+" �t� ajouter");
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
		}
		btn1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ec)
			{
				Element root = doc.getDocumentElement();
				NodeList corpusList = root.getElementsByTagName("corpus");
				Element corpus = (Element) corpusList.item(0);
				if(corpus !=null )
				{
					System.out.print("corpus ok");
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
								update(listDoc, nbrDeDoc);
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
		
	}
}
