package hashiGRP3.ObjectGraphique;

import hashiGRP3.Logic.Ile;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ileGraphique {

    private final Ile ile;

    public ileGraphique(Ile ile) {
        this.ile = ile;
    }

    /**
     * Crée le dessin de l'ile
     * @param cellSize taille d'une case de la grille
     * @return un Group JavaFX representant l'ile
     */
    public StackPane draw(double  gridCellSize) {
        Circle circle = new Circle(gridCellSize/2);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);

        Label label = new Label(String.valueOf(ile.getNbPontsRequis()));
        label.setFont(Font.font("System", FontWeight.BOLD, 18));

        StackPane group = new StackPane(circle, label);

        group.setLayoutX(ile.getCoordonnees().x * gridCellSize);
        group.setLayoutY(ile.getCoordonnees().y * gridCellSize);

        return group;
    }
}