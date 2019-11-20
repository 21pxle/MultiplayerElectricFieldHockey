package primitive;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;

public class Puck extends Circle {
    private SimpleDoubleProperty charge = new SimpleDoubleProperty(this, "charge", 20);
    private SimpleDoubleProperty mass = new SimpleDoubleProperty(this, "mass", 1);
    private SimpleDoubleProperty accX = new SimpleDoubleProperty(this, "accX", 0);
    private SimpleDoubleProperty velX = new SimpleDoubleProperty(this, "velX", 0);
    private SimpleDoubleProperty accY = new SimpleDoubleProperty(this, "accY", 0);
    private SimpleDoubleProperty velY = new SimpleDoubleProperty(this, "velY", 0);

    public Puck() {
        super(10);
        setFill(Color.BLUE);
    }

    public double calculateForce(Field field) {
        List<Charge> charges = field.getCharges();
        // Coulomb's Law - F = k * q1 * q2 / r^2 - in this case, k = 10.
        double force_x, force_y;
        for (Charge charge : charges) {
            Point2D point = charge.getPosition();
            double x1 = point.getX();
            double y1 = point.getY();

            double x2 = getPosition().getX();
            double y2 = getPosition().getY();

            double dx = x2 - x1;
            double dy = y2 - y1;

            double distance = Math.hypot(dx, dy);
            double angle = Math.atan2(dy, dx);

            double force = 10 * charge.getCharge() * this.charge.get() / (distance * distance);
            force_x = 0;

        }
        return 0;
    }




    public void setPosition(double x, double y) {
        super.setLayoutX(x);
        super.setLayoutY(y);
    }

    public Point2D getPosition() {
        return new Point2D(getLayoutX(), getLayoutY());
    }
}
