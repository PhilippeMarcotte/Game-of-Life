package ca.qc.bdeb.inf203.tp1.vue;

import java.awt.Color;
import javax.swing.JButton;

/**
 * Objet qui permet de faire des objets boutons modifiés pour ce programme. Ils
 * ont des coordonnées associées à chacun et plusieurs états dans lesquels ils
 * peuvent êtres.
 *
 * @author Philippe Marcotte
 */
public final class JCell extends JButton {

    /*
     * Integer qui représente où sur la longueur se situe la cellule
     * sélectionnée.
     */
    private int coordonneeX = 0;
    /*
     * Integer qui représente où sur la hauteur se situe la cellule
     * sélectionnée.
     */
    private int coordonneeY = 0;
    /*
     * Integer qui représente l'état d'une cellule.
     */
    private int etat;
    /*
     * Constante qui représente l'état mort d'une cellule.
     */
    public final int MORT = 0;
    /*
     * Constante qui représente l'état naissant d'une cellule.
     */
    public final int NAISSANT = 1;
    /*
     * Constante qui représente l'état mourrant d'une cellule.
     */
    public final int MOURRANT = 2;
    /*
     * Constante qui représente l'état vivant d'une cellule.
     */
    public final int VIVANT = 3;

    /*
     * Constructeur d'une cellule où l'on détermine ses coordonnées dans la grille de simulation.
     * @param coordonneeX Integer qui représente où sur la longueur se situe la cellule
     * sélectionnée.
     * @param coordonneeY Integer qui représente où sur la hauteur se situe la cellule
     * sélectionnée.
     */
    public JCell(int coordonneeX, int coordonneeY) {
        this.setCoordonneeX(coordonneeX);
        this.setCoordonneeY(coordonneeY);
        etat = MORT;
    }

    /*
     * Permet d'obtenir la coordonnee sur la longueur d'une cellule. Utilisable uniquement dans ce package.
     * @return Où se situe la cellule sur la longueur.
     */
    protected int getCoordonneeX() {
        return coordonneeX;
    }

    /*
     * Permet de déterminer la coordonnee sur la longueur d'une cellule. Utilisable uniquement dans ce package.
     * @param coordonneeX Integer qui représente où sur la longueur se situe la cellule
     * sélectionnée.
     */
    protected void setCoordonneeX(int coordonneeX) {
        this.coordonneeX = coordonneeX;
    }

    /*
     * Permet d'obtenir la coordonnee sur la hauteur d'une cellule. Utilisable uniquement dans ce package.
     * @return Où se situe la cellule sur la hauteur.
     */
    protected int getCoordonneeY() {
        return coordonneeY;
    }

    /*
     * Permet de déterminer la coordonnee sur la hauteur d'une cellule. Utilisable uniquement dans ce package.
     * @param coordonneeY Integer qui représente où sur la hauteur se situe la cellule
     * sélectionnée.
     */
    protected void setCoordonneeY(int coordonneeY) {
        this.coordonneeY = coordonneeY;
    }

    /*
     * Permet d'obtenir l'état d'une cellule. (mort, naissant, mourrant, vivant)
     * @return Integer qui représente l'état.
     */
    protected int getEtatActif() {
        return etat;
    }

    /*
     * Permet de déterminer l'état d'une cellule. Le bouton changea de couleur dépendament de son état.
     * @param etat Integer représentant l'état de la cellule
     */
    protected void setEtatActif(int etat) {
        this.etat = etat;
        switch (etat) {
            case VIVANT:
                this.setBackground(Color.yellow);
                break;
            case MORT:
                this.setBackground(Color.black);
                break;
            case NAISSANT:
                this.setBackground(Color.blue);
                break;
            case MOURRANT:
                this.setBackground(Color.red);
                break;
        }
    }
}
