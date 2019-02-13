package TP2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class extrarDesMots {
	public static void main(String args[])
	{
		final JFrame fe = new JFrame();
		fe.setTitle("les mots des fichier");
		fe.setSize(400,400);
		fe.setLocationRelativeTo(null);
		
		fe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
						while(bf.ready())
						{
							line = bf.readLine();
							mots = line.split(" ");
							for(i=0; i<mots.length; i++)
							{
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
