package ca.qc.bdeb.inf203.tp1.controleur;

import ca.qc.bdeb.inf203.tp1.vue.FenetreSimulation;
import ca.qc.bdeb.inf203.tp1.vue.FenetreMenu;
import ca.qc.bdeb.inf203.tp1.modele.CalculGeneration;

/**
 * Le contrôleur régit la communication entre la fenêtre de simulation et le
 * modèle. De plus, il instancie la fenêtre du menu en plus des deux autres.
 * 
 * <p>Le contrôleur contient les méthdoes et fonctions qui servent à créer la
 * grille de boutons dans la vue et la grille de integer dans le modèle selon
 * les dimensions entrées par l'utilisateur (ceux-ci étant passées en
 * paramètres). Aussi, il sert à synchroniser la fenêtre de la simulation avec
 * le modèle pour qu'au moment où l'utilisateur appuie sur une case de la grille
 * de boutons celle-ci s'active dans la grille de integer en même temps. Il fait
 * aussi le pont lorsque le modèle doit envoyer à la vue les nouvelles matrices
 * de integer résultant d'une nouvelle génération. Finalement, il sert à envoyer
 * au modèle la grille à sauvegarder dans un fichier texte depuis la vue, et à
 * dire à la vue si le modèle juge qu'une nouvelle génération est pareille à la
 * dernière.</p>
 *
 * @author Philippe Marcotte
 */
public class GameOfLifeControleur {

    /*
     * Déclaration du modèle pour pouvoir l'initialiser et, ainsi, utiliser ses méthodes dans le contrôleur.
     */
    private CalculGeneration modele;
    /*
     * Déclaration de la fenêtre du menu pour pouvoir l'initialiser et commencer le programme.
     */
    private FenetreMenu fenetreMenu;
    /*
     * Déclaration de la fenêtre de simulation pour pouvoir l'initialiser et faire afficher la fenêtre de la grille.
     */
    private FenetreSimulation fenetreSimulation;

    /**
     * Initialise la fenêtre de la simulation en lui passant en paramètre si oui
     * ou non la demie génération doit être affiché, la longueur et la hauteur
     * de la grille de boutons. Ensuite, cette méthode initialise le modèle en
     * lui passant la longueur et la hauteur de la grille de integer et le type
     * de règle pour calculer une nouvelle génération.
     *
     * @param demieGeneration boolean qui détermine si, oui ou non, la vue doit
     * afficher les demies générations. Oui étant pour l'afficher et non pour ne
     * pas l'afficher.
     * @param dimensionY String qui représente la hauteur de la grille utilisée
     * pour la simulation
     * @param dimensionX String qui représente la longueur de la grille utilisée
     * pour la simulation
     * @param mode String qui représente le type de règle sélectionné par
     * l'utilisateur. Cela détermine comment les nouvelles générations vont être
     * calculées.
     */
    public void creationGrille(boolean demieGeneration, String dimensionY, String dimensionX, String mode) {
        this.fenetreSimulation = new FenetreSimulation(demieGeneration, Integer.parseInt(dimensionY), Integer.parseInt(dimensionX), this);
        this.modele = new CalculGeneration(Integer.parseInt(dimensionY), Integer.parseInt(dimensionX), mode);
    }

    /**
     * Méthode qui synchronise la fenêtre de la simulation avec le modèle pour
     * que, lorsque l'utilisateur active ou désactive une case dans la grille de
     * boutons, la grille de integer du modèle se modifie aussi.
     *
     * @param etatCellule Integer qui représente l'état d'une cellule. Dans ce
     * cas seul l'état mort et vivant est utilisé.
     * @param coordonneeX Integer qui représente où sur la longueur se situe la cellule
     * sélectionnée.
     * @param coordonneeY Integer qui représente où sur la hauteur se situe la cellule
     * sélectionnée.
     */
    public void setGrilleCG(int etatCellule, int coordonneeX, int coordonneeY) {
        this.modele.setEtatCellule(etatCellule, coordonneeX, coordonneeY);
    }

    /**
     * Permet d'envoyer au modèle l'information sur la grille à enregistrer dans
     * un fichier texte.
     *
     * @param nbGeneration Double qui représente la génération actuelle qui va
     * être enregistrée.
     */
    public void sauvegardeFichier(double nbGeneration) {
        this.modele.sauvegarderFichier(nbGeneration);
    }

    /**
     * Permet à la vue de recevoir la grille modifiée par le modèle et, ainsi,
     * afficher les résultats de la nouvelle génération.
     *
     * @param nbGeneration Double qui représente la génération actuelle pour
     * savoir si c'est une grille contenant les prédictions de la prochaine
     * génération (demie génération) ou si c'est une génération complète.
     * @return Une matrice d'integer représentant soit la grille de la nouvelle
     * génération complète, soit la grille des prédictions de la prochaine
     * génération.
     */
    public int[][] prochaineGeneration(double nbGeneration) {
        int[][] grilleProchaineGeneration = null;
        if (nbGeneration % 1 == 0.5) {
            grilleProchaineGeneration = this.modele.prediction();
        } else if (nbGeneration % 1 == 0) {
            grilleProchaineGeneration = this.modele.grilleGenerationComplete();
        }
        return grilleProchaineGeneration;
    }

    /**
     * Lors du calcul des prédictions dans le modèle, celui-ci va compter le
     * nombre de changements qu'il y aura lieu dans la prochaine génération. Si
     * le nombre de changements est de 0, alors cette méthode va renvoyer un
     * boolean true pour dire à la vue que la nouvelle génération est pareille à
     * la dernière.
     *
     * @return Boolean qui détermine si la nouvelle génération est pareille ou
     * non à la dernière.
     */
    public boolean testSimilarite() {
        boolean testSimilarite = false;
        if (this.modele.getNbChangement() == 0) {
            testSimilarite = true;
        } else if (this.modele.getNbChangement() != 0){
            testSimilarite = false;
        }
        return testSimilarite;
    }
    
    /*
     * Constructeur du contrôleur qui initialise la fenêtre du menu qui est la première fenêtre du programme.
     */
    public GameOfLifeControleur(){
        this.fenetreMenu = new FenetreMenu(this);
    }
    
    
}
