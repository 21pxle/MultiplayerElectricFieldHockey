package primitive;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Charge extends Circle {
    public Charge() {
        super(10);
        setFill(Color.RED);
    }

    public void setPosition(double x, double y) {
        super.setLayoutX(x);
        super.setLayoutY(y);
    }

    public Point2D getPosition() {
        return new Point2D(getLayoutX(), getLayoutY());
    }

    public double getCharge() {
        return 10;
    }
}
