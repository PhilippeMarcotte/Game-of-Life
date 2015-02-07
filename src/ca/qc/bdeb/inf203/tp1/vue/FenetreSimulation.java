package ca.qc.bdeb.inf203.tp1.vue;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ca.qc.bdeb.inf203.tp1.controleur.GameOfLifeControleur;
import java.awt.Color;
import java.awt.MenuItem;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

/**
 * Classe qui contient toutes les méthodes et fonctions pour le fonctionnement
 * de la fenêtre qui simule le game of life.
 * 
 * 
 * <p>Ceci correspond à la classe de la vue qui va afficher la grille de boutons
 * que l'utilisateur va utiliser pour représenter les "formes de vies" qu'il
 * veut. Ensuite en cliquant sur le bouton continuer la classe va appeler les
 * méthodes qui vont calculer, selon le type de règle sélectionner au début,
 * comment la forme de vie va évoluer à la prochaine génération. Cette fenêtre
 * contient aussi un menu d'option pour que l'utilisateur puisse faire une
 * nouvelle forme de vie de la génération 0, sauvegarder le résultat obtenue à
 * la génération actuel et quitter le programme.</p>
 *
 * @author Philippe Marcotte
 */
public class FenetreSimulation extends JFrame {

    /**
     * La longueur de la grille, cette donnée est déécidée par l'utilisateur.
     */
    private int dimensionXMax = 0;
    /**
     * La hauteur de la grille, cette donnée est décidée par l'utilisateur.
     */
    private int dimensionYMax = 0;
    /**
     * Détermine les dimensions de la grille de droite contenant le bouton
     * prochaine génération et le nombre de génération.
     */
    private GridLayout grilleContinuer = new GridLayout(6, 1);
    /*
     * Panneau principal contenant la barre de menu, la grille de boutons et le bouton continuer.
     */
    private JPanel pnlPrincipal = new JPanel();
    /*
     * Panneau contenant la grille de boutons.
     */
    private JPanel pnlGrille;
    /*
     * Panneau contenant le bouton continuer et le nombre de génération.
     */
    private JPanel pnlContinuer = new JPanel(grilleContinuer);
    /*
     * Des panneaux vides servant à ajuster la position du label du nombre de générations et du bouton continuer.
     */
    private JPanel pnlVide1 = new JPanel();
    private JPanel pnlVide2 = new JPanel();
    private JPanel pnlVide3 = new JPanel();
    private JPanel pnlVide4 = new JPanel();
    /*
     * Bouton qui enclenche la suite de méthodes et d'algorithmes pour calculer la prochaine génération et afficher les résultats.
     */
    private JButton btnContinuer = new JButton("Prochaine génération");
    /*
     * Un nombre qui représente le nombre de génération auquel le programme est rendu.
     */
    private double generation = 0;
    /*
     * Un label qui sert à afficher le nombre de génération passé.
     */
    private JLabel lblGenerations = new JLabel("Générations : " + generation);
    /*
     * Menu qui contient les options possibles lors du fonctionnement du game of life.
     */
    private MenuBar menuGrille = new MenuBar();
    /*
     * Objet de menu qui contient les options nouvelle partie, sauvegarder et quitter.
     */
    private Menu menuFichier = new Menu("Fichier");
    /*
     * Objet qui permet de faire une nouvelle partie en cliquant dessus.
     */
    private MenuItem mnINouvellePartie = new MenuItem("Nouvelle partie");
    /*
     * Objet qui permet de quitter le programme en cliquant dessus.
     */
    private MenuItem mnIQuitter = new MenuItem("Quitter");
    /*
     * Objet qui permet de sauvegarder la grille dans son état acutel dans un fichier texte en cliquant dessus.
     */
    private MenuItem mnISauvegarder = new MenuItem("Sauvegarder");
    /*
     * Initialise le contrôleur pour que la fenêtre de la simulation puisse communiquer avec celui-ci et faire le lien avec le modèle.
     */
    GameOfLifeControleur controleur;
    /*
     * Tableau de boutons modifiés pour correspondre aux cellules de la grille.
     */
    private JCell[][] cellule;
    /*
     * Permet de savoir si l'option de voir les demies générations a été sélectionnée.
     */
    private boolean demieGeneration = false;
    /*
     * Label pour la légende qui montre la couleur d'une cellule naissante.
     */
    private JLabel lblNaissance = new JLabel("Naissance d'une cellule");
    /*
     * Label pour la légende qui montre la couleur d'une cellule mourante.
     */
    private JLabel lblMourante = new JLabel("Cellule mourante");
    /*
     * Label pour la légende qui montre la couleur d'une cellule vivante.
     */
    private JLabel lblVivante = new JLabel ("Cellule vivante");
    /*
     * Permet d'obtenir les dimensions de l'écran qui affiche le programme.
     */
    private Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Constructeur la fenêtre contenant la grille de la simulation qui affiche
     * les résultats de génération en génération et du menu d'options.
     *
     * @param demieGeneration Permet de savoir si l'option de voir les demies
     * générations a été coché.
     * @param dimensionXMax La longueur de la grille entrée par l'utilisateur.
     * @param dimensionYMax La hauteur de la grille entrée par l'utilisateur.
     * @param controleur Permet de passer le contrôleur en paramètre pour
     * utiliser ses méthodes dans la vue.
     */
    public FenetreSimulation(final boolean demieGeneration, final int dimensionXMax,
            final int dimensionYMax, GameOfLifeControleur controleur) {
        this.dimensionXMax = dimensionXMax;
        this.dimensionYMax = dimensionYMax;
        cellule = new JCell[dimensionXMax][dimensionYMax];

        pnlPrincipal.setLayout(new BoxLayout(pnlPrincipal, BoxLayout.X_AXIS));

        pnlGrille = new JPanel(new GridLayout(dimensionXMax, dimensionYMax));
        pnlGrille.setPreferredSize(new Dimension(tailleEcran.width-300, tailleEcran.height-100));

        for (int dimensionX = 0; dimensionX < dimensionXMax; dimensionX++) {
            for (int dimensionY = 0; dimensionY < dimensionYMax; dimensionY++) {
                cellule[dimensionX][dimensionY] = new JCell(dimensionX, dimensionY);
                cellule[dimensionX][dimensionY].setBackground(Color.black);
                cellule[dimensionX][dimensionY].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                cellule[dimensionX][dimensionY].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() instanceof JCell) {
                            JCell celluleClique = (JCell) e.getSource();
                            if (celluleClique.getEtatActif() == celluleClique.VIVANT || celluleClique.getEtatActif() == celluleClique.MOURRANT || celluleClique.getEtatActif() == celluleClique.NAISSANT) {
                                celluleClique.setEtatActif(celluleClique.MORT);
                                appelerControleur(celluleClique.getEtatActif(), celluleClique.getCoordonneeX(), celluleClique.getCoordonneeY());
                            } else if (celluleClique.getEtatActif() == celluleClique.MORT) {
                                celluleClique.setEtatActif(celluleClique.VIVANT);
                                appelerControleur(celluleClique.getEtatActif(), celluleClique.getCoordonneeX(), celluleClique.getCoordonneeY());
                            }
                        }
                    }
                });
                pnlGrille.add(cellule[dimensionX][dimensionY]);
            }
        }

        pnlContinuer.add(pnlVide1);
        pnlVide1.setBackground(Color.DARK_GRAY);
        pnlContinuer.add(lblNaissance);
        lblNaissance.setForeground(Color.blue);
        pnlContinuer.add(lblMourante);
        lblMourante.setForeground(Color.red);
        pnlContinuer.add(lblVivante);
        lblVivante.setForeground(Color.yellow);
        pnlContinuer.add(lblGenerations);
        lblGenerations.setForeground(Color.white);
        pnlContinuer.add(btnContinuer);
        pnlContinuer.setBackground(Color.darkGray);
        btnContinuer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] grilleCell = prochaineGeneration(demieGeneration);
                for (int i = 0; i < dimensionXMax; i++) {
                    for (int j = 0; j < dimensionYMax; j++) {
                        if (grilleCell[i][j] == cellule[i][j].VIVANT) {
                            cellule[i][j].setEtatActif(cellule[i][j].VIVANT);
                        } else if (grilleCell[i][j] == cellule[i][j].MORT) {
                            cellule[i][j].setEtatActif(cellule[i][j].MORT);
                        } else if (grilleCell[i][j] == cellule[i][j].NAISSANT) {
                            cellule[i][j].setEtatActif(cellule[i][j].NAISSANT);
                        } else if (grilleCell[i][j] == cellule[i][j].MOURRANT) {
                            cellule[i][j].setEtatActif(cellule[i][j].MOURRANT);
                        }
                    }
                }
                testSimilarite();
            }
        });


        pnlPrincipal.add(pnlGrille);
        pnlPrincipal.add(pnlContinuer);
        menuFichier.add(mnINouvellePartie);
        mnINouvellePartie.addActionListener(new menuListener());
        menuFichier.add(mnISauvegarder);
        mnISauvegarder.addActionListener(new menuListener());
        menuFichier.addSeparator();
        menuFichier.add(mnIQuitter);
        mnIQuitter.addActionListener(new menuListener());
        menuGrille.add(menuFichier);

        this.setMenuBar(menuGrille);

        this.add(pnlPrincipal);
        this.controleur = controleur;
        this.setTitle("Game of Life");
        this.setVisible(true);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Appelle la méthode du contrôleur qui va changer l'état d'une cellule dans
     * le tableau de integer du modèle.
     *
     * @param etatActif Permet de savoir quel est l'état de la cellule
     * sélectionner (mort ou vivant).
     * @param dimensionX Permet de savoir où est situé la cellule sur la
     * longueur.
     * @param dimensionY Permet de savoir où est situé la cellule sur la
     * hauteur.
     */
    private void appelerControleur(int etatActif, int dimensionX, int dimensionY) {
        controleur.setGrilleCG(etatActif, dimensionX, dimensionY);
    }

    /**
     * Fonction qui fait augmenter le nombre de générations en fonction de si
     * l'option de demie génération a été sélectionné ou non. De plus, elle
     * renvoie la matrice de integer qui correspond aux modifications effectuées
     * par le modèle.
     *
     * @param demieGeneration Permet de savoir si l'option de voir es demies
     * générations a été cochée.
     * @return Un tableau de int repésentant soit les prédictions pour une demie
     * génération, soit la génération complète.
     */
    private int[][] prochaineGeneration(boolean demieGeneration) {
        if (demieGeneration) {
            lblGenerations.setText("Générations : " + (generation = generation + 0.5));
        } else if (!demieGeneration) {
            lblGenerations.setText("Générations : " + ++generation);
        }
        return controleur.prochaineGeneration(generation);
    }

    /*
     * Méthode qui appelle la méthode qui test si la nouvelle génération est différente de la précédente.
     * Si c'est le cas elle modifie le label lblInfo pour qu'il affiche l'avertissement.
     */
    private void testSimilarite() {
        String message = "Les générations semblent ne pas différer les unes des autres";
        if (controleur.testSimilarite()) {
            JOptionPane.showMessageDialog(null, message);
        }
    }

    /*
     * Sous-classe contenant l'actionListener pour le menu d'option. Lorsque l'utilisateur appuie sur nouvelle partie, sauvegarder ou quitter c'est lui qui gère
     * l'enclenchement des méthodes pour que l'option séectionnée s'effectue.
     */
    class menuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof MenuItem) {
                MenuItem selection = (MenuItem) e.getSource();
                if (selection.equals(mnINouvellePartie)) {
                    generation = 0;
                    lblGenerations.setText("Générations : " + generation);
                    for (int dimensionX = 0; dimensionX < dimensionXMax; dimensionX++) {
                        for (int dimensionY = 0; dimensionY < dimensionYMax; dimensionY++) {
                            if (cellule[dimensionX][dimensionY].getEtatActif() != cellule[dimensionX][dimensionY].MORT) {
                                cellule[dimensionX][dimensionY].setEtatActif(cellule[dimensionX][dimensionY].MORT);
                                appelerControleur(cellule[dimensionX][dimensionY].getEtatActif(), dimensionX, dimensionY);
                            }
                        }
                    }
                } else if (selection.equals(mnISauvegarder)) {
                    controleur.sauvegardeFichier(generation);
                } else if (selection.equals(mnIQuitter)) {
                    System.exit(0);
                }
            }
        }
    }
}
