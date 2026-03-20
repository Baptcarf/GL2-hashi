//Att
package hashiGRP3.ObjectGraphique;

//Imports
import java.util.function.Consumer;
import hashiGRP3.Logic.EtatDuPont;
import hashiGRP3.Logic.Pont;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/** Classe d'un pont avec JavaFX */
public class pontGraphique {

    private static class Segment {
        final double startX, startY, endX, endY;

        Segment(double startX, double startY, double endX, double endY) {
            this.startX = startX; this.startY = startY; this.endX = endX; this.endY = endY;
        }
    }

    private final Pont pont;

    /**
     * Constructeur d'un pont 
     * @param pont Un objet pont logique.
     */
    public pontGraphique(Pont pont) {
        this.pont = pont;
    }

    /**
     * Crée le dessin du pont, avec gestion du clic
     * @param cellSize taille d'une case de la grille
     * @param onClick callback appele quand le pont est clique
     * @return un Group JavaFX representant le pont
     */
    public Group draw(double cellSize, Consumer<Pont> onClick) {
        var a = pont.getileA().getCoordonnees();
        var b = pont.getileB().getCoordonnees();
        double ax = a.x * cellSize + cellSize / 2;
        double ay = a.y * cellSize + cellSize / 2;
        double bx = b.x * cellSize + cellSize / 2;
        double by = b.y * cellSize + cellSize / 2;

        // Rayon pour ne pas dessiner sur les iles
        double islandRadius = cellSize / 2;

        // Calcul du segment du pont
        Segment seg = computeSegment(ax, ay, bx, by, islandRadius);

        Group group = new Group();


        // Ajout des traits selon l'etat du pont
        EtatDuPont etat = pont.getEtatActuel();
        boolean isHypothese = pont.isEstHypothese();
        switch (etat) {
            case SIMPLE -> group.getChildren().add(createLine(seg, cellSize, 0, isHypothese));
            case DOUBLE -> {
                double offset = Math.max(3, cellSize * 0.05);
                group.getChildren().add(createLine(seg, cellSize, offset, isHypothese));
                group.getChildren().add(createLine(seg, cellSize, -offset, isHypothese));
            }
            case VIDE -> {}
        }

        // Ajout d'une hitbox invisible pour le clic
        Line hitBox = new Line(seg.startX, seg.startY, seg.endX, seg.endY);
        hitBox.setStroke(Color.TRANSPARENT);
        hitBox.setStrokeWidth(Math.max(10, cellSize * 0.35));
        hitBox.setCursor(Cursor.HAND);
        hitBox.setOnMouseClicked(e -> onClick.accept(pont));
        hitBox.setOnMouseEntered(e -> hitBox.setStroke(Color.web(pont.pontEstPossible() ?  "#9b9b97" : "#ff0000"  , 0.5)));
        hitBox.setOnMouseExited(e -> hitBox.setStroke(Color.TRANSPARENT));
        group.getChildren().add(hitBox);

        return group;
    }

    /**
     * Calcule le segment du pont (hors iles) selon l’orientation
     */
    private Segment computeSegment(double ax, double ay, double bx, double by, double radius) {
        if (pont.getOrientation() == Pont.Orientation.HORIZONTAL) {
            double startX = Math.min(ax, bx) + radius;
            double endX = Math.max(ax, bx) - radius;
            double y = ay;
            return new Segment(startX, y, endX, y);
        } else {
            double startY = Math.min(ay, by) + radius;
            double endY = Math.max(ay, by) - radius;
            double x = ax;
            return new Segment(x, startY, x, endY);
        }
    }

/**
     * Crée un trait JavaFX pour un pont
     * @param isHypothese définit si le trait doit être gris (mode hypothèse)
     */
    private Line createLine(Segment seg, double cellSize, double offset, boolean isHypothese) {
        Line line;
        if (seg.startY == seg.endY) {
            line = new Line(seg.startX, seg.startY + offset, seg.endX, seg.endY + offset);
        } else {
            line = new Line(seg.startX + offset, seg.startY, seg.endX + offset, seg.endY);
        }

        // LOGIQUE DE COULEUR
        // Si c'est une hypothèse, on met en GRIS, sinon en NOIR
        line.setStroke(isHypothese ? Color.GRAY : Color.BLACK);
        
        line.setStrokeWidth(Math.max(2, cellSize * 0.06));
        return line;
    }
}
