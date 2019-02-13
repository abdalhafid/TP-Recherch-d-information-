package TP2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

public class nonMotsVide {
	public static void main(String args[]) throws IOException
	{
		final JFrame fe = new JFrame();
		fe.setTitle("les mots des fichier ");
		fe.setSize(400,400);
		fe.setLocationRelativeTo(null);
		fe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String motVides="";
		FileReader swr = new FileReader(new File("stopwords_fr.txt"));
		BufferedReader swbr = new BufferedReader(swr);
		while(swbr.ready())
		{
			motVides += swbr.readLine()+" ";
		}
		 final String[] metaCharacters = {"^","$","{","}","[","]","(",")",".","*","+","?","|","<",">","-","&","%", ",", ":", "1", "2", "3", "4", "5", "6", "7", "8", "9", String.valueOf('"')};
		final String[] motsVide = motVides.split(" ");
		JButton browser = new JButton("importer fichier");
		JButton save = new JButton("sauve garder le fichier de sortie");
		JPanel bar = new JPanel(new FlowLayout());
		bar.add(save);
		bar.add(browser);
		final JTextArea text = new JTextArea();
		JScrollPane tb = new JScrollPane(text);
		
		fe.add(bar, BorderLayout.PAGE_START);
		fe.add(tb, BorderLayout.CENTER);
		fe.setVisible(true);
		
		browser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent av)
			{
				JFileChooser fc = new JFileChooser();
				String[] extensions = {"txt"};
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Document Text (.txt)", extensions);
				fc.setFileFilter(filter);
				fc.setAcceptAllFileFilterUsed(false);
				int c = fc.showOpenDialog(fe);
				if(c == 0)
				{
					
					
					
					
					FileReader f;
					int i=0;
					try {
						f = new FileReader(fc.getSelectedFile());
						BufferedReader bf = new BufferedReader(f);
						String line="", mots[], text1="";
						String letter, nmot="";
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
									System.out.println(letter);
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
								for(int j=0; j<motsVide.length; j++)
								{
									if(motsVide[j].equals(mots[i]))
									{
										isVide = true;
									}
									
								}
								if(!isVide)
									text1 += mots[i]+"\n";
							}
						}
						text.setText(text1);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		});	
		
		save.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fcs = new JFileChooser();
				int c = fcs.showSaveDialog(fe);
				File file;
				if(c == 0)
				{
					file = fcs.getSelectedFile();
				}else{
					file = new File("text.txt");
				}
				FileWriter fr;
				try {
					fr = new FileWriter(file);
					BufferedWriter bfr = new BufferedWriter(fr);
					String mots[] = text.getText().split("\n");
					for(int i=0; i<mots.length; i++)
					{
						bfr.write(mots[i]);
						bfr.newLine();
					}
					bfr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
	}
}
