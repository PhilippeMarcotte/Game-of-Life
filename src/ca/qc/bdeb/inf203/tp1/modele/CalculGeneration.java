package ca.qc.bdeb.inf203.tp1.modele;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * La méthode permet de calculer, selon le type de règle sélectionné dans la
 * fenêtre menu, les nouvelles générations.
 * 
 * <p>Elle contient les algorithmes pour les règles de Conway et celle de High
 * Life. Elle utilisera ces règles dépendament de ce qui a été sélectionné. Pour
 * calculer, elle commence par faire les prédictions, c'est-à-dire qu'elle ne
 * fait que déterminer, dans une grille d'integer de prédiction, lesquelles
 * cellules vont mourire, vont rester en vie ou vont naître. Ensuite, dans une
 * autre grille, elle applique les changements prédits. De plus, le modèle sert
 * à enregistrer les grilles lorsque demandé dans des fichiers textes.</p>
 *
 * @author Philippe Marcotte
 */
public class CalculGeneration {

    /*
     * Matrice d'integer qui contient toutes les cellules juste avant la prochaine génération et, après calcul, la nouvelle génération.
     */
    private int[][] grilleGenerationComplete;
    /*
     * Matrice d'integer qui contient les prédictions pour la prochaine génération.
     */
    private int[][] grilleDemieGeneration;
    /*
     * Integer représentant l'état d'une cellule morte.
     */
    private final int MORT = 0;
    /*
     * Integer représentant l'état d'une cellule naissante.
     */
    private final int NAISSANT = 1;
    /*
     * Integer représentant l'état d'une cellule mourrante.
     */
    private final int MOURRANT = 2;
    /*
     * Integer représentant l'état d'une vivante.
     */
    private final int VIVANT = 3;
    /*
     * Tableau d'integer représentant les règles choisies par l'utilisateur relatives à la naissance des cellules.
     */
    private int[] regleNaissance = null;
    /*
     * Tableau d'integer représentant les règles choisies par l'utilisateur relatives à la survie des cellules.
     */
    private int[] regleSurvie = null;
    /*
     * Tableau d'integer représentant les règles de Conway relatives à la naissance des cellules.
     */
    private int[] naissanceConway = {3};
    /*
     * Tableau D'integer représentant les règles de Conway relatives à la survie des cellules.
     */
    private int[] survieConway = {2, 3};
    /*
     * Tableau d'integer représentant les règles HighLife relatives à la naissance des cellules.
     */
    private int[] naissanceHighLife = {3, 6};
    /*
     * Tableau d'integer représentant les règles Highlife relatives à la survie des cellules.
     */
    private int[] survieHighLife = {2, 3};
    /*
     * Integer représentant la longueur que l'utilisateur a déterminée pour la grille de la simulation.
     */
    private int dimensionXMax;
    /*
     * Integer représentant la hauteur que l'utilisateur a déterminée pour la grille de la simulation.
     */
    private int dimensionYMax;
    /*
     * Integer représentant le nombre de changement que la nouvelle génération subit.
     */
    private int nbChangement;

    /**
     * Constructeur du modèle qui va initialiser les deux grilles d'integer, les
     * dimensions des grilles et quel type de règle a été sélectionné.
     *
     * @param dimensionXMax Integer représentant la longueur que l'utilisateur a
     * déterminée pour la grille de la simulation.
     * @param dimensionYMax Integer représentant la hauteur que l'utilisateur a
     * déterminée pour la grille de la simulation.
     * @param mode String représentant le type de règle sélectionné par
     * l'utilisateur.
     */
    public CalculGeneration(int dimensionXMax, int dimensionYMax, String mode) {
        grilleGenerationComplete = new int[dimensionXMax][dimensionYMax];
        grilleDemieGeneration = new int[dimensionXMax][dimensionYMax];
        this.dimensionXMax = dimensionXMax;
        this.dimensionYMax = dimensionYMax;
        if (mode.equalsIgnoreCase("Conway")) {
            regleNaissance = naissanceConway;
            regleSurvie = survieConway;
        } else if (mode.equalsIgnoreCase("HighLife")) {
            regleNaissance = naissanceHighLife;
            regleSurvie = survieHighLife;
        }
    }

    /**
     * Méthode qui change l'état d'une cellule dans la grille qui contient les
     * integer avant la nouvelle génération, selon les changements subits par la
     * grille de boutons dans la vue.
     *
     * @param etatCellule Integer qui représente l'état d'une cellule. Dans ce
     * cas seul l'état mort et vivant est utilisé.
     * @param coordonneeX Integer qui représente où sur la longueur se situe la
     * cellule sélectionnée.
     * @param coordonneeY Integer qui représente où sur la hauteur se situe la
     * cellule sélectionnée.
     */
    public void setEtatCellule(int etatCellule, int coordonneeX, int coordonneeY) {
        if (etatCellule == MORT) {
            grilleGenerationComplete[coordonneeX][coordonneeY] = MORT;
        } else if (etatCellule == VIVANT) {
            grilleGenerationComplete[coordonneeX][coordonneeY] = VIVANT;
        }
    }

    /**
     * Fonction qui applique les changements prédits par la grille de demie
     * génération dans la grille de génération complète.
     *
     * @return La matrice d'integer de génération complète
     */
    public int[][] grilleGenerationComplete() {
        prediction();
        for (int i = 0; i < this.dimensionXMax; i++) {
            for (int j = 0; j < this.dimensionYMax; j++) {
                if (grilleDemieGeneration[i][j] == MOURRANT) {
                    grilleGenerationComplete[i][j] = MORT;
                } else if (grilleDemieGeneration[i][j] == NAISSANT) {
                    grilleGenerationComplete[i][j] = VIVANT;
                } else if (grilleDemieGeneration[i][j] == MORT) {
                    grilleGenerationComplete[i][j] = MORT;
                } else if (grilleDemieGeneration[i][j] == VIVANT) {
                    grilleGenerationComplete[i][j] = VIVANT;
                }
            }
        }
        return grilleGenerationComplete;
    }

    /*
     * Fonction qui calcule le nombre de voisins vivant autour de la cellule sélectionnée.
     * 
     * @param coordonneX Integer qui représente où sur la longueur se situe la cellule
     * sélectionnée.
     * @param coordonneY Integer qui représente où sur la hauteur se situe la cellule
     * sélectionnée.
     * @return Le nombre de voisins vivant
     */
    private int nbVoisinsActif(int coordonneeX, int coordonneeY) {
        int nbVoisins = 0;
        for (int i = coordonneeX - 1; i <= coordonneeX + 1; i++) {
            for (int j = coordonneeY - 1; j <= coordonneeY + 1; j++) {
                if (!(i == coordonneeX && j == coordonneeY)
                        && (i >= 0 && i < this.dimensionXMax) && (j >= 0 && j < this.dimensionYMax)) {
                    if (grilleGenerationComplete[i][j] == VIVANT) {
                        nbVoisins++;
                    }
                }
            }
        }
        return nbVoisins;
    }

    /**
     * Fonction qui calcul les changements que la nouvelle génération va amener
     * selon le nombre de voisins calculés et le type de règle choisis.
     *
     * @return Matrice d'integer représentants les changements prédits.
     */
    public int[][] prediction() {
        nbChangement = 0;
        for (int i = 0; i < this.dimensionXMax; i++) {
            for (int j = 0; j < this.dimensionYMax; j++) {
                int nbVoisinsActif = nbVoisinsActif(i, j);
                switch (grilleGenerationComplete[i][j]) {
                    case MORT:
                        if (verifieNaissance(nbVoisinsActif)) {
                            grilleDemieGeneration[i][j] = NAISSANT;
                            nbChangement++;
                        } else if (!verifieNaissance(nbVoisinsActif)) {
                            grilleDemieGeneration[i][j] = MORT;
                        }
                        break;
                    case VIVANT:
                        if (verifieSurvie(nbVoisinsActif)) {
                            grilleDemieGeneration[i][j] = VIVANT;
                        } else if (!verifieSurvie(nbVoisinsActif)) {
                            grilleDemieGeneration[i][j] = MOURRANT;
                            nbChangement++;
                        }
                        break;
                }
            }
        }
        return grilleDemieGeneration;
    }

    /*
     * Détermine si la cellule sélectionnée devrait naître ou non
     * @param nbVoisins Le nombre de voisins qui permet de déterminer si la naissance à lieu ou non
     * @return Boolean qui est vrai s'il y a naissance et faux si rien ne se passe.
     */
    private boolean verifieNaissance(int nbVoisins) {
        boolean naissance = false;
        for (int i = 0; i < this.regleNaissance.length; i++) {
            if (nbVoisins == this.regleNaissance[i]) {
                naissance = true;
            }
        }

        return naissance;
    }

    /*
     * Détermine si la cellule sélectionnée survie ou meurt
     * @param nbVoisins Le nombre de voisins qui permet de déterminer si la cellule vie ou non
     * @return Boolean qui est vrai si la cellule survie et faux si elle meurt.
     */
    private boolean verifieSurvie(int nbVoisins) {
        boolean survie = false;
        for (int i = 0; i < this.regleSurvie.length; i++) {
            if (nbVoisins == this.regleSurvie[i]) {
                survie = true;
            }
        }
        return survie;
    }

    /**
     * Fonction qui permet d'obtenir le nombre de changement qu'une nouvelle
     * génération subit à l'extérieur de cette classe
     *
     * @return Integer représentant le nombre de changement
     */
    public int getNbChangement() {
        return nbChangement;
    }

    /**
     * Méthode qui enregistre la grille d'integer dans un fichier texte au
     * moment que l'option "sauvegarder" est sélectionnée dans la vue.
     *
     * @param nbGeneration Double qui permet de déterminer la génération
     * actuelle, et de différencier si c'est une demie génération ou une
     * génération complète.
     */
    public void sauvegarderFichier(double nbGeneration) {
        Date date = new Date();
        PrintWriter ecrire = null;
        try {
            ecrire = new PrintWriter(("Génération_" + nbGeneration));

            if (nbGeneration % 1 == 0.5) {
                ecrire.println("Grille des générations intermédiaires : " + nbGeneration);
                for (int i = 0; i < this.dimensionXMax; i++) {
                    for (int j = 0; j < this.dimensionYMax; j++) {
                        if (grilleDemieGeneration[i][j] == VIVANT) {
                            ecrire.print("O" + " ");
                        } else if (grilleDemieGeneration[i][j] == MORT) {
                            ecrire.print("." + " ");
                        } else if (grilleDemieGeneration[i][j] == NAISSANT) {
                            ecrire.print("V" + " ");
                        } else if (grilleDemieGeneration[i][j] == MOURRANT) {
                            ecrire.print("M" + " ");
                        }
                    }
                    ecrire.println();
                }
            } else if (nbGeneration % 1 == 0) {
                ecrire.println("Grille des générations complètes : " + nbGeneration);
                for (int i = 0; i < this.dimensionXMax; i++) {
                    for (int j = 0; j < this.dimensionYMax; j++) {
                        if (grilleGenerationComplete[i][j] == VIVANT) {
                            ecrire.print("O" + " ");
                        } else if (grilleGenerationComplete[i][j] == MORT) {
                            ecrire.print("." + " ");
                        }
                    }
                    ecrire.println();
                }
            }
            ecrire.close();
        } catch (IOException e) {
            System.out.println("Erreur de fichier!");
        }

    }    
}
