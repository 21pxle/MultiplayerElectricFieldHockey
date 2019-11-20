package primitive;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PuckCircle extends Circle {

    private SimpleDoubleProperty charge = new SimpleDoubleProperty(this, "charge", 20);
    private SimpleDoubleProperty mass = new SimpleDoubleProperty(this, "mass", 1);
    private SimpleDoubleProperty accX = new SimpleDoubleProperty(this, "accX", 0);
    private SimpleDoubleProperty velX = new SimpleDoubleProperty(this, "velX", 0);
    private SimpleDoubleProperty accY = new SimpleDoubleProperty(this, "accY", 0);
    private SimpleDoubleProperty velY = new SimpleDoubleProperty(this, "velY", 0);

    public PuckCircle() {
        super(10);
        setFill(Color.BLUE);
    }


    public double getCharge() {
        return charge.get();
    }

    public SimpleDoubleProperty chargeProperty() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge.set(charge);
    }

    public double getMass() {
        return mass.get();
    }

    public SimpleDoubleProperty massProperty() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass.set(mass);
    }

    public double getAccX() {
        return accX.get();
    }

    public SimpleDoubleProperty accXProperty() {
        return accX;
    }

    public void setAccX(double accX) {
        this.accX.set(accX);
    }

    public double getVelX() {
        return velX.get();
    }

    public SimpleDoubleProperty velXProperty() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX.set(velX);
    }

    public double getAccY() {
        return accY.get();
    }

    public SimpleDoubleProperty accYProperty() {
        return accY;
    }

    public void setAccY(double accY) {
        this.accY.set(accY);
    }

    public double getVelY() {
        return velY.get();
    }

    public SimpleDoubleProperty velYProperty() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY.set(velY);
    }
}
