import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
public class finalExo {
	public static String typeInput;
	public static ConterLesMotsDUnChaine M ;
	public static Nombredoccurence n;
	public static LesPositionsDUnMot poss;
	public static ContextAvantEtArrier cont;
	public static LireFicher lf;
	public static ChercheMotifDUnMot mtf;
	public static void main(String args[])
	{
		final JFrame f= new JFrame("TP0 Recherche d'un mot ");
		f.setSize(800,220);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		JPanel pan1 = new JPanel();
		JPanel pan2 = new JPanel();
		
		
		// pan1 content 
		final JTextField tf = new JTextField(10);
		final JButton typeMot = new JButton("Recher comme motif");
		JButton nbrMots = new JButton("nombre de mots du text");
		JButton nbr = new JButton("nombre de répitions");
		JButton pos = new JButton("positions");
		JButton con1 = new JButton("contexte");
		JButton fichier = new JButton("importer le text from un fichier");
		final JFileChooser openFichier = new JFileChooser();
		pan1.setLayout(new BoxLayout(pan1, BoxLayout.Y_AXIS));
		
		pan1.add(tf);
		pan1.add(nbrMots);
		pan1.add(typeMot);
		
		pan1.add(nbr);
		pan1.add(pos);
		pan1.add(con1);
		
		f.add(pan1, BorderLayout.WEST);
		
		
		// pan2 content 
		
		final JTextArea ta = new JTextArea();
		ta.setWrapStyleWord(true);
		ta.setLineWrap(true);
		JScrollPane tasc = new JScrollPane(ta);
		pan2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pan2.setLayout(new BorderLayout());
		pan2.add(tasc);
		f.add(pan2, BorderLayout.CENTER);
		
		
		f.setVisible(true);
		
	
		
		fichier.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				int c = openFichier.showOpenDialog(f);
				if(c == 0)
				{
					LireFicher file = new LireFicher(openFichier.getSelectedFile());
					try {
						ta.setText(file.getFileText());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(f, "le fichier n'est pas valide");
					}
				}
				
			}
		});
		
		
		
		nbrMots.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(!ta.getText().equals(""))
				{
					M = new ConterLesMotsDUnChaine(ta.getText());
					JOptionPane.showMessageDialog(f, "Le nombre de dans ce text est: "+M.getNumMots());
				}else{
					JOptionPane.showMessageDialog(f, "Le  text est vide");
				}
			}
		});
		
		nbr.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(!ta.getText().equals("") && !tf.getText().equals(""))
				{
					n = new Nombredoccurence(ta.getText(), tf.getText());
					JOptionPane.showMessageDialog(f, n.getNumRep());
				}else{
					JOptionPane.showMessageDialog(f, "Le  text est vide ou le mots n'exist pas");
				}
			}
		});
		
		
		pos.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(!ta.getText().equals("") && !tf.getText().equals(""))
				{
					poss = new LesPositionsDUnMot(ta.getText(), tf.getText());
					JOptionPane.showMessageDialog(f, poss.getPositions());
				}else{
					JOptionPane.showMessageDialog(f, "Le  text est vide ou le mots n'exist pas");
				}
			}
		});
		
		
		con1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				String tail = JOptionPane.showInputDialog("Entrer la taill de context: "); 
				int taill =2;
				while(tail.equals(""))
					tail = JOptionPane.showInputDialog("Entrer la taill de context: ");  
					
				try{
					taill = Integer.parseInt(tail);
					if(!ta.getText().equals("") && !tf.getText().equals(""))
					{
						cont = new ContextAvantEtArrier(ta.getText(), tf.getText(), taill);
						ArrayList conList = cont.getContext();
						if(conList.size() == 0)
						{
							JOptionPane.showMessageDialog(f, "Le mot entrée n'exist pas dans le text" );
						}else{
							
							for(int i=0; i<conList.size(); i++)
							{
								JOptionPane.showMessageDialog(f, conList.get(i) );
							}
						}
						
					}else{
						JOptionPane.showMessageDialog(f, "Le  text est vide ou le mots n'exist pas");
					}
				}catch(NumberFormatException ne)
				{
					JOptionPane.showMessageDialog(f, "le nombre entrer n'est pas valide");
				}
				
			}
		});
		
		
		typeMot.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(!ta.getText().equals("") && !tf.getText().equals(""))
				{
					mtf = new ChercheMotifDUnMot(ta.getText(), tf.getText());
					JOptionPane.showMessageDialog(f,  mtf.getMotifPositions());
				}else{
					JOptionPane.showMessageDialog(f, "Le  text est vide ou le mots n'exist pas");
				}
			}
		});
		
		
}
}
