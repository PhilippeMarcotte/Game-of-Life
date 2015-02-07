package ca.qc.bdeb.inf203.tp1.vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import ca.qc.bdeb.inf203.tp1.controleur.GameOfLifeControleur;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Première fenêtre de la vue où l'utilisateur détermine le déroulement de la
 * simulation.
 * 
 * <p>Cette classe affiche une fenêtre qui contient deux texts fields où
 * l'utilisateur entre les dimensions de la grille de la simulation (ne pouvant
 * pas être plus bas que 30 et plus haut que 100). Elle contient aussi la
 * sélection du type de règle, c'est-à-dire Conway ou Highlife qui vont
 * déterminer l'évolution des générations. Finalement, elle contient une case à
 * cocher pour déterminer si l'utilisateur veut voir les dmies générations ou
 * non.</p>
 *
 * @author Philippe Marcotte
 */
public class FenetreMenu extends JFrame {

    /*
     * Panneau principal contenant tous les éléments de la fenêtre et dictant leur disposition.
     */
    private JPanel pnlPrincipal = new JPanel(new GridLayout(7, 1));
    /*
     * Panneau contenant le label et les texts fields reliés à la détermination des dimensions de la grille de simulation
     */
    private JPanel pnlDimensions = new JPanel(new BorderLayout());
    /*
     * Panneau contenant les texts fields pour déterminer les dimensions del a girlle de simulation
     */
    private JPanel pnlTxtDimensions = new JPanel(new BorderLayout());
    /*
     * Panneau contenant la boîte de sélection du type de règle Conway ou HighLife
     */
    private JPanel pnlChoixMode = new JPanel(new BorderLayout());
    /*
     * Panneau contenant la case à cocher pour déterminer si l'utilisateur veut voir les demies générations ou non
     */
    private JPanel pnlDemieVie = new JPanel(new BorderLayout());
    /*
     * Panneau contenant le bouton pour démarrer la simulation et initialiser le modèle et la fenêtre de simulation.
     */
    private JPanel pnlDemarrer = new JPanel(new GridLayout(1, 2));
    /*
     * Panneaux vides pour aider à bien disposer tous les autres panneaux.
     */
    private JPanel pnlVide1 = new JPanel();
    private JPanel pnlVide2 = new JPanel();
    private JPanel pnlVide3 = new JPanel();
    private JPanel pnlVide4 = new JPanel();
    /*
     * Label pour identifier la section où l'on détermine les dimensions.
     */
    private JLabel lblDimensions = new JLabel("Dimensions de la grille");
    /*
     * Label pour identifier la section où l'on détermine si l'on voit les demies générations ou non.
     */
    private JLabel lblDemieVie = new JLabel("Voir les demies générations");
    /*
     * Case à sélectionner pour déterminer si l'utilisateur veut voir ou non les demies générations.
     */
    private JCheckBox ChBDemieVie = new JCheckBox();
    /*
     * Tableau de String qui contient les deux Strings qui identifient les types de règles.
     */
    private String[] choixModes = {"Conway", "HighLife"};
    /*
     * Objet visuel qui permet de défiler le tableau de String pour le choix du type de règle.
     */
    private JComboBox<String> listeModes = new JComboBox<>(choixModes);
    /*
     * Objet bouton pour démarrer la simulation.
     */
    private JButton btnDemarrer = new JButton("Démarrer");
    /*
     * Text field pour entrée la longueur de la grille de simulation.
     */
    private JTextField txtDimensionX = new JTextField();
    /*
     * Text field pour entrée la hauteur de la grille de simulation.
     */
    private JTextField txtDimensionY = new JTextField();
    /*
     * Boolean qui est vrai si l'utilisateur veut voir les demies générations et faux s'il ne veut pas. Il est faux par défaut.
     */
    private boolean demieGeneration = false;
    /*
     * Integer pour la longueur et hauteur minimum de la grille.
     */
    private int dimensionMin = 20;
    /*
     * Integer pour la longueur et hauteur maximum de la grille.
     */
    private int dimensionMax = 70;
    /*
     *Initialise le contrôleur pour que la fenêtre du menu puisse communiquer avec celui-ci et faire le lien avec le modèle.
     */
    GameOfLifeControleur controleur;

    /**
     * Constructeur de la fenêtre du menu. Il affiche tous les éléments du menu
     * et ajoute des actions listener là où il en faut.
     *
     * @param controleur Permet de passer le contrôleur en paramètre pour
     * utiliser ses méthodes dans la vue.
     */
    public FenetreMenu(GameOfLifeControleur controleur) {
        txtDimensionY.setText("Hauteur");
        txtDimensionX.setText("Longueur");
        /*Ces lignes de codes viennent de l'utilisateur "eugener" sur
         * http://stackoverflow.com/questions/1178312/how-to-select-all-text-in-a-jformattedtextfield-when-it-gets-focus/1178596#1178596
         * Elles permettent que le texte informatif qui est écrit par défaut dans les labels txtDimensionX et txtDimensionY soit sélectionné
         * automatiquement lorsque ces texts fields ont le focus.
         * D'ici...
         */
        txtDimensionY.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        txtDimensionY.selectAll();
                    }
                });
            }
        });
        txtDimensionX.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        txtDimensionX.selectAll();
                    }
                });
            }
        });
        // ...jusqu'à ici!

        pnlDemieVie.add(lblDemieVie, BorderLayout.WEST);
        pnlDemieVie.add(ChBDemieVie);
        ChBDemieVie.addActionListener(new EtatListener());

        pnlChoixMode.add(listeModes);

        pnlTxtDimensions.add(txtDimensionY, BorderLayout.WEST);
        pnlTxtDimensions.add(txtDimensionX, BorderLayout.EAST);

        pnlDimensions.add(lblDimensions, BorderLayout.WEST);
        pnlDimensions.add(pnlTxtDimensions, BorderLayout.EAST);

        pnlDemarrer.add(pnlVide4);
        pnlDemarrer.add(btnDemarrer);
        btnDemarrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (testDimension(Integer.parseInt(txtDimensionY.getText()), Integer.parseInt(txtDimensionX.getText()))) {
                    appelerControlleur(txtDimensionY.getText(), txtDimensionX.getText(), (String) listeModes.getSelectedItem(), demieGeneration);
                    setVisible(false);
                } else if (!testDimension(Integer.parseInt(txtDimensionY.getText()), Integer.parseInt(txtDimensionX.getText()))) {
                    dimensionIncorrectes(Integer.parseInt(txtDimensionY.getText()), Integer.parseInt(txtDimensionX.getText()));
                }

            }
        });

        pnlPrincipal.add(pnlDimensions);
        pnlPrincipal.add(pnlVide1);
        pnlPrincipal.add(pnlChoixMode);
        pnlPrincipal.add(pnlVide2);
        pnlPrincipal.add(pnlDemieVie);
        pnlPrincipal.add(pnlVide3);
        pnlPrincipal.add(pnlDemarrer);

        this.add(pnlPrincipal);

        
        this.controleur = controleur;
        this.setTitle("Game of Life");
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /*
     * Fonction qui teste si les dimensions entrées par l'utilisateur respectent le minimum et le maximum imposés.
     * @param dimensionX La longueur de la grille de simulation entrée par l'utilisateur.
     * @param dimensionY La hauteur de la grille de simulation entrée par l'utilisateur.
     */
    private boolean testDimension(int dimensionX, int dimensionY) {
        boolean succesTest = false;
        if (Integer.parseInt(txtDimensionY.getText()) >= dimensionMin && Integer.parseInt(txtDimensionY.getText()) <= dimensionMax
                && Integer.parseInt(txtDimensionX.getText()) >= dimensionMin && Integer.parseInt(txtDimensionX.getText()) <= dimensionMax) {
            succesTest = true;
        }
        return succesTest;
    }

    /*
     * Sous classe contenant l'action listener pour la case à cocher qui détermine si l'on affiche les demies générations ou non.
     */
    class EtatListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JCheckBox) {
                JCheckBox box1 = (JCheckBox) e.getSource();
                if (box1.isSelected()) {
                    demieGeneration = true;
                }
            }
        }
    }

    /*
     * Méthode qui appelle la méthode du contrôleur qui initialise la fenêtre de simulation et le modèle.
     * @param dimensionX String représentant la longueur de la grille de simulation entrée par l'utilisateur.
     * @param dimensionY String représentant la hauteur de la grille de simulation entrée par l'utilisateur.
     * @param mode String représentant le type de règle sélectionné par l'utilisateur.
     * @param demieGeneration boolean qui dit s'il l'on affiche ou non les demies générations.
     */
    private void appelerControlleur(String dimensionX, String dimensionY, String mode, boolean demieGeneration) {
        controleur.creationGrille(demieGeneration, dimensionX, dimensionY, mode);
    }

    /*
     * Méthode qui modifie le label de message d'erreur si l'utilisateur entre uine dimension trop petite ou trop grande
     * @param dimensionX La longueur de la grille de simulation entrée par l'utilisateur.
     * @param dimensionY La hauteur de la grille de simulation entrée par l'utilisateur.
     */
    private void dimensionIncorrectes(int dimensionX, int dimensionY) {
        String messageErreur = null;
        JLabel lblMsgErreur = new JLabel();
        if (dimensionX < dimensionMin) {
            messageErreur = "La longueur de la grille est trop petite pour que le programme performe correctement.";
        } else if (dimensionX > dimensionMax) {
            messageErreur = "La longueur de la grille est trop grande pour le bien du programme.";
        } else if (dimensionY < dimensionMin) {
            messageErreur = "La hauteur de la grille est trop petite pour que le programme performe correctement.";
        } else if (dimensionY > dimensionMax) {
            messageErreur = "La hauteur de la grille est trop grande pour le bien du programme.";
        }
        JOptionPane.showMessageDialog(null, messageErreur);
    }
}
