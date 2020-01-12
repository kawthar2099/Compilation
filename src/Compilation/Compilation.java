 package Compilation;


import java.awt.Color;
import java.awt.Cursor;
import static java.awt.Cursor.HAND_CURSOR;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
 import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Compilation {

	private JFrame frmMonAnalyseur;
	JTextArea textArea;
	
	static JFileChooser file_chooser = new JFileChooser("");                  //les fichiers a choisir
	static FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers text", "compila"); //choisir l'extenion qui est accepté
	static ArrayList<String> mots = new ArrayList<String>();                     //tableau des mots type string
	static ArrayList<String> lignes = new ArrayList<String>();                //tableau lignes
	static ArrayList<String> sortie_lexic = new ArrayList<String>();          //tableau sortie lexical
	static String[] mot;

	

	public static void charger() throws FileNotFoundException {               //choix de fichiers verifier s'il existe ou pas
		file_chooser.addChoosableFileFilter(filter);
		if(file_chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			File file = file_chooser.getSelectedFile();
			Scanner sc_lignes = new Scanner(file);
			Scanner sc_mots = new Scanner(file);
			mots.clear();
			lignes.clear();
				while(sc_lignes.hasNextLine()){
					lignes.add(sc_lignes.nextLine());
				}
				while(sc_mots.hasNext()){
					mots.add(sc_mots.next());
					}

			sc_mots.close();
			sc_lignes.close();
			}
	}

//pour les chiffres

	public boolean isNum(String chaine, int i) {
		char[] nombre = {'0' , '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		int j = 0;
		while (j < nombre.length) {
			if (chaine.charAt(i) == nombre[j]) {                     //charAt Return the first character of a string Syntax string.charAt(index)
				return true;
			}
			j++;
		}

		return false;
	}
//les nbr entier et reel
	public String num(String chaine) {
		int i = 0;
		int token_pos = 0;
		boolean point_unique = true;        //??
		while (i < chaine.length()) {
			if (isNum(chaine, i)) token_pos++;                        //isNum verifier si la chaine est un nombre (isNum= is number)
			else if(point_unique & chaine.charAt(token_pos)==',') {
				token_pos++;
				point_unique = false;
			}
			i++;
		}

		if (token_pos == chaine.length() && !chaine.contains(",")) return "Nombre entier";
		else if (token_pos == chaine.length() && !point_unique) return "Nombre reel";
		return null;

	}

//pour les chiffres
	public boolean isChar(String chaine, int i) {
		char[] alphabet = { 'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 'G', 'g', 'H', 'h', 'I', 'i',
				'J', 'j', 'K', 'k', 'L', 'l', 'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T',
				't', 'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x', 'Y', 'y', 'Z', 'z' };
		int k = 0;
		while (k < alphabet.length) {
			if (chaine.charAt(i) == alphabet[k]) {                   //afficher le premier caractere de la chaine a chaque saisie
				return true;
			}
			k++;
		}
		return false;

	}
	public String id(String chaine) {
		boolean verifier_Premier = false;
		boolean tiret_unique = true;
		int token_pos = 0;
		int i = 0;
		if (isChar(chaine, 0)) {
			token_pos++;
			verifier_Premier = true;
		}
		if (verifier_Premier == true && chaine.length() == 1)
			return "identificateur";

		else if (chaine.length() > 1) {
			i = 1;
			while (i < chaine.length()) {

				if (isChar(chaine, i))
					{token_pos++;
					tiret_unique=true;
					}
				else if (isNum(chaine, i))
					{token_pos++;
					tiret_unique=true;
					}
				else if (chaine.charAt(i) == '_' && tiret_unique) {
					tiret_unique=true;
					token_pos++;
				}
				i++;
			}
			if (token_pos == chaine.length())
				return "Identificateur";
		}
		return null;
	}


//les mots reserver

	public String UL_reserve(String chaine) {
	    String[] mot_reserve = { "<", ">", ",",
                        "Start_Program", "Int_Number",
                        ";;", "Give", "Real_Number",
                        "If", "--", "Else",
			"Start", "Affect","to", "Finish",
                        "ShowMes","ShowVal",":",
                        "End_Program",
                        "//." , "\""
                         };

	    String[] Affichage = { 
			"symbol inferieur", "symbol superieur", "caractere reservé virgule",
			"Mot reserve debut du programme", " Mot reserve declaration d'un entier",
			"Mot reserve fin instruction", "Mot reserve pour affectation entre variables", " Mot reserve debut declaration d'un Real",
			" Mot reserve pour condition SI", "Mot reserve pour condition", "Mot reserve pour condition SINON", 
                        "Debut d'un bloc","Mot reserve pour affectation", "Mot reserve pour affectation", "Fin d'un bloc",
			"Mot reservé pour afficher un message","Mot reservé pour afficher une valeur ","mot reservé pour affectation",
                        "Mot reserve Fin du programme",
                        "Mot reservé pour un commentaire", "mot reservé pour affichage d une chaine de caracteres "
                         };
		int i = 0;
		while (i < mot_reserve.length) {
			if (chaine.equals(mot_reserve[i])) {
				return Affichage[i];
			}
			i++;
		}
		return null;
	}

//l'analyse syntaxique

	public String syntax(String chaine){
                char d=':';
		char g='\"';
		if(chaine.equals("Start_Program")) return "Début du programme";
		else if(chaine.equals("Else")) return "SINON";
		else if(chaine.equals("Start")) return "Début d'un bloc";
		else if(chaine.equals("Finish")) return "Fin d'un bloc";
		else if(chaine.startsWith("//.")) return "un commentaire";
		else if(chaine.startsWith("ShowMes"+" "+d+" "+g) && chaine.endsWith(g+" "+";;")) return "Affichage d'un message";
		else if(chaine.contains(" ")) {
			mot = chaine.split(" ");                                 //devise la chaine a des sous-chaine 
			int i=0, k=1;

				if(mot[i].equals("Int_Number")){
					i++;
					if(mot[i].equals(" "))
						i++;
                                        if(mot[i].equals(":"))i++;
                                        if(mot[i].equals(" "))i++;
						if(id(mot[i])!= null){
							i++;
							while(mot[i].equals(",")){
								i++;
								k++;
								if(id(mot[i]) != null) i++;
							}
							if(mot[i].equals(";;")) return "Déclaration de "+k+" variables entières";
						}
					

				}
				else if(mot[i].equals("Give")){
					i++;
					if(id(mot[i]) != null){
						i++;
					if(mot[i].equals(" "))i++;
                                        if(mot[i].equals(":"))i++;
                                        if(mot[i].equals(" "))i++;
						if(num(mot[i]) == "Nombre entier") {
							i++;
                                                        
							if(mot[i].equals(";;")) return "affectation dune valeur entiere à "+mot[i-3];
						}
                                                if(mot[i].equals(":"))i++;
                                                if(mot[i].equals(" "))i++;
						else if(num(mot[i]) == "Nombre reel"){
							i++;
                                            
							if(mot[i].equals(";;")) return "affectation dune valeur reel à "+mot[i-3];
						}

					
				}

				}
				
				else if(mot[i].equals("Real_Number")){
					i++;
					if(mot[i].equals(" "))i++;
                                        if(mot[i].equals(":"))i++;
                                        if(mot[i].equals(" "))i++;
						if(id(mot[i]) != null)i++;
							if(mot[i].equals(";;")) return "Déclaration de  variable reel";
						}


				
				
				else if(mot[i].equals("If")){
					i++;
                                        if(mot[i].equals(" "))i++;
					if(mot[i].equals("--")){
						i++;
                                        if(mot[i].equals(" "))i++;
					if(id(mot[i]) != null){
						i++;
						if(mot[i].equals("<") || mot[i].equals(">") || mot[i].equals("==")){
						i++;
						if(id(mot[i]) != null){
							i++;
						if(mot[i].equals("--")) return "condition"; 
							}}}}
				}
				
				
				
				else if(mot[i].equals("Affect")){
					i++;
					if(id(mot[i]) != null){
						i++;
					if(mot[i].equals("to")){
						i++;
						if(id(mot[i]) != null) {
							i++;
							if(mot[i].equals(";;")) return "affectation de la valeur de "+mot[i-3]+" à "+mot[i-1];
						}

					}

				}

				}
				
				
				else if(mot[i].equals("ShowMes")){
					i++;
					if(mot[i].equals(" "))i++;
                                        if(mot[i].equals(":"))i++;
                                        if(mot[i].equals(" "))i++;
						if(id(mot[i]) != null)i++;
							if(mot[i].equals(";;")) return "affichage";
						}
				

				
								}
                if(chaine.equals("End_Program")) return "Fin du programme";
		return "erreur lexicale";
	}

	
	
//

	
	public String semantique(String chaine){
		if(chaine.equals("Start_Program")) return "public static void main(String[] args) {";
		else if(chaine.equals("Else")) return "else";
		else if(chaine.equals("Start")) return "{";
		else if(chaine.equals("Finish")) return "}";
		else if(chaine.startsWith("//.")) return "/*ceci est un commentaire*/";
		else if(chaine.equals("End_Program ")) return "}";
		else if(chaine.startsWith("ShowMes : \" ") && chaine.endsWith(" \" ;;")) return "System.out.println(\"Affichage d'un message à l'ecran\");";
		else if(chaine.contains(" ")) {
			mot = chaine.split(" ");
			int i=0;

				if(mot[i].equals("Int_Number")){
					i++;
					if(mot[i].equals(" "))
						i++;
                                        if(mot[i].equals(":"))
						i++;
                                        if(mot[i].equals(" "))
						i++;
						if(id(mot[i]) != null){
							i++;
							while(mot[i].equals(",")){
								i++;
								if(id(mot[i]) != null) i++;
							}
							if(mot[i].equals(";;"))return "int"+" "+mot[i-5]+","+mot[i-3]+","+mot[i-1]+";";
						}
					

				}
				
				else if(mot[i].equals("Give")){
					i++;
					if(id(mot[i]) != null){
						i++;
					if(mot[i].equals(" "))i++;
                                        if(mot[i].equals(":"))i++;
                                        if(mot[i].equals(" "))i++;
						if(num(mot[i]) == "Nombre entier") {
							i++;
							if(mot[i].equals(";;")) return mot[i-3]+"="+mot[i-1]+";";
						}
                                                if(mot[i].equals(":"))i++;
                                        if(mot[i].equals(" "))i++;
						else if(num(mot[i]) == "Nombre reel"){
							i++;
							if(mot[i].equals(";;")) return mot[i-3]+"="+mot[i-1]+";";
						}

					
				}

				}
				
				else if(mot[i].equals("Real_Number")){
					i++;
					if(mot[i].equals(" "))i++;
                                        if(mot[i].equals(":"))
						i++;
                                        if(mot[i].equals(" "))
						i++;
						if(id(mot[i]) != null)i++;
							if(mot[i].equals(";;")) return "float "+mot[i-1]+";";
						}


				
				
				else if(mot[i].equals("If")){
					i++;
					if(mot[i].equals("--")){
						i++;
					if(id(mot[i]) != null){
						i++;
						if(mot[i].equals("<") || mot[i].equals(">") || mot[i].equals("==")){
						i++;
						if(id(mot[i]) != null){
							i++;
						if(mot[i].equals("--")) return "if"+"("+mot[i-3]+mot[i-2]+mot[i-1]+")"; 
							}}}}
				}
				
				
				
				else if(mot[i].equals("Affect")){
					i++;
					if(id(mot[i]) != null){
						i++;
					if(mot[i].equals("to")){
						i++;
						if(id(mot[i]) != null) {
							i++;
							if(mot[i].equals(";;")) return  mot[i-3]+"="+mot[i-1]+";";
						}

					}

				}

				}
				
				
				else if(mot[i].equals("ShowMes")){
					i++;
					if(mot[i].equals(" "))i++;
                                        if(mot[i].equals(":"))i++;
                                        if(mot[i].equals(" "))i++;
						if(id(mot[i]) != null)i++;
							if(mot[i].equals(";;")) return "System.out.println("+mot[i-1]+");";
						}

				

				
								}
		return "erreur sémantique";
		
	}

	
	
//
	public void lexicale(List<String> liste) {
		int i = 0;

		while (i < mots.size()) {
			if (UL_reserve(mots.get(i)) != null) {
				sortie_lexic.add(UL_reserve(mots.get(i)));
			} else if (id(mots.get(i)) != null) {
				sortie_lexic.add(id(mots.get(i)));
			} else if (num(mots.get(i)) != null) {
				sortie_lexic.add(num(mots.get(i)));
			}
			else sortie_lexic.add("Erreur");

			i++;
		}

	}

//
	
public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Compilation window = new Compilation ();
					window.frmMonAnalyseur.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Compilation () {
		initialize();
	}

        
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
           
		frmMonAnalyseur = new JFrame();
		frmMonAnalyseur.setResizable(false);
		frmMonAnalyseur.setTitle("Analyseur lexicale,syntaxique,sémantique");
		frmMonAnalyseur.getContentPane().setBackground(new Color(0,153,153));
                frmMonAnalyseur.setLocationRelativeTo(null);
		frmMonAnalyseur.getContentPane().setLayout(null);
		//frmMonAnalyseur.setLocationRelativeTo(null);
		
		Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
                Cursor cursor2 = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
		
                
				JButton charger = new JButton("Charger",new ImageIcon("icons8-add-file-64.png"));
				charger.setCursor(cursor2);
				charger.setBounds(30, 340, 300,100);
				charger.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
				 
						try {
							textArea.setText("");
							charger();	

							int i = 0;
							while (i < lignes.size()) {
								textArea.setText(textArea.getText()+lignes.get(i)+"\n");
								i++;}
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					  
					}  
				});
				//panelButt.setLayout(null);
				charger.setForeground(new Color(255, 69, 0));
				charger.setBackground(UIManager.getColor("Button.foreground"));
				charger.setFont(new Font("Dialog", Font.ITALIC, 22));
				frmMonAnalyseur.add(charger);
				
				JButton Alexicale = new JButton("Analyse Lexicale");
				Alexicale.setCursor(cursor2);
				Alexicale.setBounds(60,60,250,60);
				Alexicale.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						textArea.setText("");
						lexicale(mots);
						int i = 0;
						while (i < mots.size()) {
							textArea.setText(textArea.getText()+mots.get(i) + " : " + sortie_lexic.get(i)+"\n");
							i++;}
					}
				});
				Alexicale.setForeground(new Color(255, 69, 10));
				Alexicale.setBackground(UIManager.getColor("Button.foreground"));
				Alexicale.setFont(new Font("Dialog", Font.ITALIC, 20));
				frmMonAnalyseur.add(Alexicale);
				
				JButton Asyntaxique = new JButton("Analyse Syntaxique");
				Asyntaxique.setCursor(cursor2);
				Asyntaxique.setBounds(60,150,250,60);
				Asyntaxique.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						textArea.setText("");
						int i = 0;
						while (i < lignes.size()) {
							textArea.setText(textArea.getText()+lignes.get(i) + " : " +syntax(lignes.get(i))+"\n");
							i++;}
					}
				});
				Asyntaxique.setForeground(new Color(255, 69, 0));
				Asyntaxique.setBackground(UIManager.getColor("Button.foreground"));
				Asyntaxique.setFont(new Font("Dialog", Font.ITALIC, 20));
				frmMonAnalyseur.add(Asyntaxique);
				
				JButton Asmantique = new JButton("Analyse Sémantique");
				Asmantique.setCursor(cursor2);
				Asmantique.setBounds(60,240,250,60);
				Asmantique.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						textArea.setText("");
						int i = 0;
						
						
						while (i < lignes.size()) {
							textArea.setText(textArea.getText()+lignes.get(i) + " ==> " +semantique(lignes.get(i))+"\n");
							
							i++;}
					}
				});
				Asmantique.setForeground(new Color(255, 69, 0));
				Asmantique.setBackground(UIManager.getColor("Button.foreground"));
				Asmantique.setFont(new Font("Dialog", Font.ITALIC, 20));
				frmMonAnalyseur.add(Asmantique);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.setBounds(400, 60, 400, 400);
		frmMonAnalyseur.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
                JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 380, 380);
		panel_1.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		textArea.setForeground(Color.cyan);
		textArea.setBackground(Color.DARK_GRAY);
		textArea.setFont(new Font("Perpetua", Font.BOLD, 16));
                textArea.setBounds(2, 2, 2, 2);
		frmMonAnalyseur.setBounds(150, 10, 900, 550);
		frmMonAnalyseur.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}