import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TP0 {
	
	public static int nomberDeMots(String s)
	{
		String[] mots = s.split(" ");
		return mots.length;
	}
	
	
	public static ArrayList rechercheDesMots(String input, String text)
	{	
		String InputMots[] = input.split(" "), TextMots[] = text.split(" ");
		int nbr;
		ArrayList  mots = new ArrayList();
		String  positions ;
		for(int i=0; i<InputMots.length; i++)
		{
			nbr=0;
			positions ="";
			for(int j=0; j<TextMots.length; j++)
			{
				if(TextMots[j].equals(InputMots[i]))
				{
					nbr++;
					positions += String.valueOf(j)+" ";
				}
			}
			mot m = new mot(InputMots[i], nbr);
			m.positions = positions;
			mots.add(m);
		}
		
		return mots;
	}
	
	
	public static void main(String args[])
	{
		final JFrame f= new JFrame("TP0 Recherche d'un mot ");
		f.setSize(1000,500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		JPanel pan1 = new JPanel();
		JPanel pan2 = new JPanel();
		
		
		// pan1 content 
		final JTextField tf = new JTextField(10);
		JButton nbrletters = new JButton("nombre de mots");
		JButton nbr = new JButton("nombre de répitions");
		JButton pos = new JButton("positions");
		JButton con1 = new JButton("content next");
		JButton con2 = new JButton("content avant");
		JButton color = new JButton("colorer");
		final JButton typeMot = new JButton("Recher comme motif");
		pan1.setLayout(new FlowLayout());
		pan1.add(tf);
		pan1.add(nbrletters);
		pan1.add(nbr);
		pan1.add(pos);
		pan1.add(typeMot);
		pan1.add(con1);
		pan1.add(con2);
		pan1.add(color);
		f.add(pan1, BorderLayout.NORTH);
		
		
		// pan2 content 
		
		final JTextArea ta = new JTextArea();
		JScrollPane tasc = new JScrollPane(ta);
		pan2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pan2.setLayout(new BorderLayout());
		pan2.add(tasc);
		f.add(pan2, BorderLayout.CENTER);
		
		
		f.setVisible(true);
		
	
		
		
		nbr.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				String inputMots = tf.getText();
				String inputText = ta.getText();
				ArrayList <mot> mots = rechercheDesMots(inputMots, inputText);
				for(int i=0; i<mots.size(); i++)
				{
					JOptionPane.showMessageDialog(f, "le nombre de répétitions de "+mots.get(i).s+" est: "+ mots.get(i).ndr);
				}
				
			}
		});
		
		nbrletters.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev)
			{
				String inputMots = tf.getText();
				String inputText = ta.getText();
				JOptionPane.showMessageDialog(f, "le nombre de mots dans votre phrase est: "+nomberDeMots(inputMots));
				JOptionPane.showMessageDialog(f, "le nombre de mots dans votre text est: "+nomberDeMots(inputText));
			}
		});
		
		pos.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev)
			{
				String inputMots = tf.getText();
				String inputText = ta.getText(), positions;
				ArrayList <mot> mots = rechercheDesMots(inputMots, inputText);
				for(int i=0; i<mots.size(); i++)
				{
					JOptionPane.showMessageDialog(f, "les position de mot '"+mots.get(i).s+"' sont: "+ mots.get(i).positions);
				}
			}
		});
		
		con1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ec)
			{
				String num = JOptionPane.showInputDialog("entrer le nombre des content next:");
				int pas= Integer.parseInt(num), poss[];
				String inputMots = tf.getText();
				String inputText = ta.getText(), positions[], text[] = inputText.split(" "), contentNext;
				ArrayList <mot> mots = rechercheDesMots(inputMots, inputText);
				for(int i=0; i<mots.size(); i++)
				{
					contentNext ="";
					positions = mots.get(i).positions.split(" ");
					for(int pp=0; pp<positions.length; pp++)
					{
						contentNext +="("+positions[pp]+")-> ";
						for(int pasp=1; pasp<=pas; pasp++)
							contentNext += text[Integer.parseInt(positions[pp])+pasp]+" ";
					}
					JOptionPane.showMessageDialog(f, "les "+pas+" next content de '"+mots.get(i).s+"' sont: "+contentNext);
				}
			}
		});
		
		
		con2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ec)
			{
				String num = JOptionPane.showInputDialog("entrer le nombre des content next:");
				int pas= Integer.parseInt(num), poss[];
				String inputMots = tf.getText();
				String inputText = ta.getText(), positions[], text[] = inputText.split(" "), contentNext;
				ArrayList <mot> mots = rechercheDesMots(inputMots, inputText);
				for(int i=0; i<mots.size(); i++)
				{
					contentNext ="";
					
					positions = mots.get(i).positions.split(" ");
					for(int pp=0; pp<positions.length; pp++)
					{
						contentNext +="("+positions[pp]+")-> ";
						for(int pasp=1; pasp<=pas; pasp++)
							contentNext += text[Integer.parseInt(positions[pp])-pasp]+" ";
					}
					JOptionPane.showMessageDialog(f, "les "+pas+" next content de '"+mots.get(i).s+"' sont: "+contentNext);
				}
			}
		});
		
		color.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				Highlighter h= ta.getHighlighter();
				HighlightPainter p = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
				int fromIndex=0;
				try {
					String inputMots = tf.getText();
					String inputText = ta.getText(), positions[];
					ArrayList <mot> mots = rechercheDesMots(inputMots, inputText);
					for(int i=0; i<mots.size(); i++)
					{
						positions = mots.get(i).positions.split(" ");
						for(int pp=0; pp<positions.length; pp++)
						{
							
							h.addHighlight(inputText.indexOf(inputMots, fromIndex), inputText.indexOf(inputMots, fromIndex)+mots.get(i).s.length(), p);
							fromIndex = inputText.indexOf(inputMots, fromIndex)+mots.get(i).s.length();
						}
					}
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		typeMot.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(!ta.getText().equals("") && !tf.getText().equals(""))
				{
					ChercheMotifDUnMot mtf = new ChercheMotifDUnMot(ta.getText(), tf.getText());
					JOptionPane.showMessageDialog(f,  mtf.getMotifPositions());
				}else{
					JOptionPane.showMessageDialog(f, "Le  text est vide ou le mots n'exist pas");
				}
			}
		});
	}
}
 class mot{
	public String s, positions;
	public int ndr;
	public mot(String s, int ndr)
	{
		this.s = s;
		this.ndr = ndr;
	}
}
