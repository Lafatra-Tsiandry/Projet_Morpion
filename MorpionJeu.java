package morpionPackage;
import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;
import java.util.*;

public class MorpionJeu
{
    private JFrame fenetre = new JFrame("Morpion");
    private JButton[][] boutons = new JButton[3][3];
    private ImageIcon fenetreIcone = new ImageIcon(getClass().getResource("/Icon_Image/tic-tac-toe.png"));

/*XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX[---AFFICHAGE_GRAPHIQUE---]XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX*/
    //---------------------------------(CONSTRUCTEUR)---------------------------------
    public MorpionJeu()
    {
        definirFenetre();
        ajouterBouton();
        accueil();
    }
    /********************************************************************************/
                                            //
                                            //
                                            //
    //-----------------------------(DEFINIR_LA_FENETRE)-----------------------------
    private void definirFenetre()
    {
        fenetre.setSize(500, 500);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setIconImage(fenetreIcone.getImage());
        fenetre.setLayout(new GridLayout(3, 3));
        fenetre.setLocationRelativeTo(null);
    }
    /********************************************************************************/
                                            //
                                            //
                                            //
    //--------------------------(AJOUT_BOUTON_A_LA_FENETRE)--------------------------
    private void ajouterBouton()
    {
        for (int i = 0; i < 3; i++)
        {   
            for (int j = 0; j < 3; j++)
            {
                boutons[i][j] = new JButton("");
                boutons[i][j].setFont(new Font("Arial", Font.BOLD, 100));
                boutons[i][j].setFocusPainted(false);
                int ligne = i;
                int colonne = j;
                boutons[i][j].addActionListener(e -> gestionClicBouton(ligne, colonne));
                fenetre.add(boutons[i][j]);
            }
        }
    }
    /********************************************************************************/    
                                            //
                                            //
                                            //
    //------------------------------(ACCUEIL_AU_DEBUT)--------------------------------
    private String joueurActuel;
    private void accueil() 
    {
        int choix = JOptionPane.showOptionDialog
        (
            fenetre,
            "Bienvenue dans le jeu de Morpion !\nQue voulez-vous faire ?", 
            "Écran d'accueil",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new String[]{"Commencer", "Quitter"},
            "Commencer"
        );

        if (choix == JOptionPane.NO_OPTION) 
        {
            System.exit(0);
        }

        initialiserScore();
        joueurActuel = selectPremierJoueur();
        afficherFenetre();
    }
    /********************************************************************************/
                                            //
                                            //
                                            //
    //------------------------------(INITIALISER_SCORE)------------------------------
    private String CharJoueurs[] = {"X", "O"};
    private Map <String, Integer> scoreJoueur = new HashMap<>();
    private void initialiserScore()
    {
        scoreJoueur.put(CharJoueurs[0], 0);
        scoreJoueur.put(CharJoueurs[1], 0);
    }
    /********************************************************************************/
                                            //
                                            //
                                            //
    //------------------------(JOUEUR_QUI_COMMENCE_LE_PREMIER)------------------------
    private String selectPremierJoueur()
    {
        int choix = JOptionPane.showOptionDialog
        (
            fenetre,
            "Qui commence le jeu?",
            "Choix joueur 1",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            CharJoueurs,
            CharJoueurs[0]
        );

        if(choix == JOptionPane.NO_OPTION) return CharJoueurs[1];
        return CharJoueurs[0];
    }
    /********************************************************************************/
    //-----------------------------(AFFICHAGE_DE_FENETRE)-----------------------------
    public void afficherFenetre() 
    {
        fenetre.setVisible(true); 
    }
    /********************************************************************************/
                                            //
                                            //
                                            //
    //-----------------------(FONCTIONNEMENT_LORS_D'UNE_CLIC)-----------------------
    private void gestionClicBouton(int lignePrm, int colonnePrm) 
    {

        JButton boutonActuel = boutons[lignePrm][colonnePrm];
        if (!boutonActuel.getText().isEmpty()) return;



        boutonActuel.setText(joueurActuel);
        boutonActuel.setBackground(Color .WHITE);
        if (joueurActuel == CharJoueurs[0]) boutonActuel.setForeground(Color .BLUE);
        else boutonActuel.setForeground(Color.GREEN);

        if (victoire(joueurActuel)) 
        {   
            JOptionPane.showMessageDialog(fenetre, "Le joueur " + joueurActuel + " a gagné !","RÉSULTAT DE LA PARTIE", JOptionPane.INFORMATION_MESSAGE);
            incrementerScore(joueurActuel);
            afficherScore();
            reinitialiserJeu();
            return;
        }

        if (estRempli()) 
        {
            JOptionPane.showMessageDialog(fenetre, "Match nul !", "RÉSULTAT DE LA PARTIE",JOptionPane.INFORMATION_MESSAGE);
            afficherScore();
            reinitialiserJeu();
            changementJoueur();
            return;
        }

        changementJoueur();
    }
    /********************************************************************************/
                                            //
                                            //
                                            //
    //-----------------------------(INCREMENTTER_SCORE)-----------------------------
    private void incrementerScore(String joueurActuelPrm)
    {
        int scoreJoueurActuel = scoreJoueur.get(joueurActuelPrm);
        scoreJoueur.put(joueurActuelPrm, scoreJoueurActuel + 1);

    }
    /********************************************************************************/
                                            //
                                            //
                                            //
    //-------------------------------(AFFICHER_SCORE)-------------------------------
    private void afficherScore()
    {
        JOptionPane.showMessageDialog
                (fenetre, 
                 CharJoueurs[0] + " : "+ scoreJoueur.get(CharJoueurs[0]) + "\n"+
                 CharJoueurs[1] + " : " + scoreJoueur.get(CharJoueurs[1]), 
                 "SCORE",
                 JOptionPane.INFORMATION_MESSAGE
                );
    }
    /********************************************************************************/
                                            //
                                            //
                                            //
    //-----------------------------(REINITIALISER_JEU)-----------------------------
    private void reinitialiserJeu() 
    {
        reinitialiserBoutons();
        int choix = JOptionPane.showOptionDialog(
            fenetre,
            "Voulez-vous continuer, rejouer ou quitter le jeu ?",
            "Partie terminée",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Continuer","Rejouer", "Quitter"},
            "Continuer"
        );

        if (choix == JOptionPane.CANCEL_OPTION) 
        {
            System.exit(0);
        }

        else if (choix == JOptionPane.YES_OPTION)
        {
            return;
        } 
        else if(choix == JOptionPane.NO_OPTION);
        {
            initialiserScore();
            joueurActuel = selectPremierJoueur();
            return;
        }

    }
    /********************************************************************************/
                                            //
                                            //
                                            //
/*XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX[---LOGIQUE---]XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX*/

    //-----------------------------------(VICTOIRE)-----------------------------------
    private boolean victoire(String joueurActuelPrm) 
    {
        for (int i = 0; i < 3; i++) 
        {
            if(
                (
                    boutons[i][0].getText() == joueurActuelPrm
                                        && 
                    boutons[i][1].getText() == joueurActuelPrm
                                        && 
                    boutons[i][2].getText() == joueurActuelPrm
                )                 
                                        ||
                (
                    boutons[0][i].getText() == joueurActuelPrm
                                        && 
                    boutons[1][i].getText() == joueurActuelPrm
                                        && 
                    boutons[2][i].getText() == joueurActuelPrm
                )
              ) return true;
        }

        return (
                    boutons[0][0].getText() == joueurActuelPrm 
                                    && 
                    boutons[1][1].getText() == joueurActuelPrm 
                                    && 
                    boutons[2][2].getText() == joueurActuelPrm
               )
                                    ||
               (
                    boutons[0][2].getText() == joueurActuelPrm 
                                    && 
                    boutons[1][1].getText() == joueurActuelPrm 
                                    && 
                    boutons[2][0].getText() == joueurActuelPrm
               );
    }
    /********************************************************************************/
                                            //
                                            //
                                            //
    //---------------------------(TESTE_SI_REMPLI_OU_PAS)---------------------------
     private boolean estRempli() 
    {
        for (int i = 0; i < 3; i++) 
            for (int j = 0; j < 3; j++) 
                if (boutons[i][j].getText() == "") return false;
        return true;
    }
    /********************************************************************************/
                                            //
                                            //
                                            //
    //----------------------------(CHANGEMENT_DE_JOUEUR)----------------------------
    private void changementJoueur() 
    {
        joueurActuel = (joueurActuel == CharJoueurs[0]) ? CharJoueurs[1] : CharJoueurs[0]; 
    }
    /********************************************************************************/
                                            //
                                            //
                                            //
    //--------------------------(REINITIALISATION BOUTONS)--------------------------
    private void reinitialiserBoutons()
    {
        for (int i = 0; i < 3; i++) 
            for (int j = 0; j < 3; j++)
            {
                boutons[i][j].setText("");
                boutons[i][j].setBackground(UIManager.getColor("Button.background"));
            } 
    }
    /********************************************************************************/
}
