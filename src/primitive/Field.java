package primitive;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Field extends Application {
    private List<Charge> charges = new ArrayList<>();
    private List<Timeline> timelines = new ArrayList<>();
    private double velX = 0, velY = 0, accX = 0, accY = 0, blueEventX = 0, blueEventY = 0, redEventX = 0, redEventY = 0, redCooldownTime = 0, blueCooldownTime = 0;
    private int redGoalNum = 0, blueGoalNum = 0, redFastballNum = 0, blueFastballNum = 0, redPowerUpGoals = 0, bluePowerUpGoals = 0;


    private int redGoals = 0, blueGoals = 0, time = 9000, displayTime = 0, powerupCooldownTime = (int) (500 + 500 * Math.random());
    private int redPowerUpTime = 0, bluePowerUpTime = 0;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        Circle puck = new Circle(20);
        RadialGradient gradient = new RadialGradient(0, 0, 0.5, 0.5, 1, true, CycleMethod.REPEAT,
                new Stop(0, Color.RED),
                new Stop(0.15d, Color.YELLOW),
                new Stop(0.2d, Color.LIME),
                new Stop(0.3d, Color.CYAN),
                new Stop(0.4d, Color.BLUE),
                new Stop(0.5d, Color.MAGENTA));
        puck.setFill(gradient);
        Circle c1 = new Circle(10);

        puck.setLayoutX(600);
        puck.setLayoutY(400);
        c1.setFill(Color.RED);
        c1.setVisible(false);

        Text textGoals = new Text("Score: ");
        textGoals.setFont(Font.font(40));

        Text blueScores = new Text("0");
        blueScores.setFill(Color.BLUE);
        blueScores.setFont(Font.font(40));

        Text transition = new Text(" to ");
        transition.setFont(Font.font(40));

        Text redScores = new Text("0");
        redScores.setFont(Font.font(40));
        redScores.setFill(Color.RED);

        Text timeRemaining = new Text(" - Time Remaining: ");
        timeRemaining.setFont(Font.font(40));

        Text timeRemaining2 = new Text();
        timeRemaining2.setFont(Font.font(40));
        int s1 =  (time / 100) % 60;
        int s01 =  (time / 10) % 600;
        s01 -= 10 * s1;

        timeRemaining2.setText(time / 6000 + ":" + ((s1 < 10) ? "0" : "") + s1 + "." + s01);

        TextFlow textFlow = new TextFlow(textGoals, redScores, transition, blueScores, timeRemaining, timeRemaining2);
        textFlow.setLayoutX(600 - textFlow.getBoundsInLocal().getWidth() / 2);
        textFlow.setLayoutY(0);

        textFlow.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        textFlow.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Text text = new Text("Fastball!");
        text.setVisible(false);

        text.setFont(Font.font(40));
        int tmp = time;

        Line line = new Line(600, 0, 600, 800);
        line.setStrokeWidth(10);

        Circle c2 = new Circle(10);
        c2.setFill(Color.BLUE);
        c2.setVisible(false);

        Rectangle r1 = new Rectangle(0, 250, 10, 300);
        r1.setFill(Color.RED);
        r1.setStroke(Color.RED);
        Rectangle r2 = new Rectangle(1177, 250, 10, 300);
        r2.setFill(Color.BLUE);
        r2.setStroke(Color.BLUE);

        Line l1 = new Line(150, 0, 150, 800);
        l1.setStroke(Color.RED);


        Line l2 = new Line(1027, 0, 1027, 800);
        l2.setStroke(Color.BLUE);


        TextFlow textFlow2 = new TextFlow(text);
        textFlow2.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        textFlow2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        textFlow2.setLayoutX(600 - text.getBoundsInLocal().getWidth() / 2);
        textFlow2.setLayoutY(250 - text.getBoundsInLocal().getWidth() / 2);
        textFlow2.setVisible(false);

        Text powerupTime = new Text();
        powerupTime.setFont(Font.font(40));

        TextFlow textFlow3 = new TextFlow(powerupTime);
        textFlow3.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        textFlow3.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        textFlow3.setLayoutX(600 - powerupTime.getBoundsInLocal().getWidth() / 2);
        textFlow3.setLayoutY(700);




        Circle powerup = new Circle(20);
        Group group = new Group(l1, l2, line, powerup, puck, c1, c2, r1, r2, textFlow, textFlow2, textFlow3);
        Scene scene = new Scene(group);

        stage.setScene(scene);
        stage.setWidth(1200);
        stage.setResizable(false);
        stage.setTitle("Prototype of Multiplayer Electric Field Hockey Game");
        stage.setHeight(800);

        powerup.setFill(Color.GREEN);
        powerup.setVisible(false);

        scene.setOnMouseClicked(e -> {
            if (!timelines.isEmpty()) {
                timelines.get(0).stop();
            }

            if (redCooldownTime > 0) {
                blueGoals += 2;
                if (redPowerUpTime > 0) {
                    blueGoals += 2 * (int) (Math.random() * 3);
                }
                blueScores.setText("" + blueGoals);
            }
            if (blueCooldownTime > 0) {
                redGoals += 2;
                if (blueCooldownTime > 0) {
                    redGoals += 2 * (int) (Math.random() * 3);
                }
                redScores.setText("" + redGoals);
            }
            if (e.getX() <= 600 && e.getX() > 150) {
                c1.setLayoutX(e.getX());
                c1.setLayoutY(e.getY());
                redCooldownTime = 40;
                c1.setVisible(true);
            } else if (e.getX() > 600 && e.getX() < 1027) { //Blame the Java System for this quirk.
                c2.setLayoutX(e.getX());
                c2.setLayoutY(e.getY());
                blueCooldownTime = 40;
                c2.setVisible(true);
            }

            //Acceleration: Inversely Proportional to Distance.
            Timeline timeline = new Timeline();

            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(0.01), actionEvent -> {
                        double centerX = puck.getLayoutX();
                        double centerY = puck.getLayoutY();
                        if (e.getX() <= 600 && e.getX() > 150) {
                            redEventX = e.getX();
                            redEventY = e.getY();
                        } else if (e.getX() > 600 && e.getX() < 1027) {
                            blueEventX = e.getX();
                            blueEventY = e.getY();
                        }

                        double redAcc = 500 / (Math.pow(Math.hypot(redEventX - centerX, redEventY - centerY), 2));
                        redAcc = Double.isNaN(redAcc) ? 0 : redAcc;
                        double redAngle = Math.atan2(redEventY - centerY, redEventX - centerX); //Angle of the force exerted on the puck.
                        redAngle = Double.isNaN(redAngle) ? 0 : redAngle;

                        double blueAcc = 500 / (Math.pow(Math.hypot(blueEventX - centerX, blueEventY - centerY), 2));
                        blueAcc = Double.isNaN(blueAcc) ? 0 : blueAcc;
                        double blueAngle = Math.atan2(blueEventY - centerY, blueEventX - centerX); //Angle of the force exerted on the puck.
                        blueAngle = Double.isNaN(blueAngle) ? 0 : blueAngle;


                        double redEvtAccX = redAcc * -Math.cos(redAngle);
                        double redEvtAccY = redAcc * -Math.sin(redAngle);
                        double blueEvtAccX = blueAcc * -Math.cos(blueAngle);
                        double blueEvtAccY = blueAcc * -Math.sin(blueAngle);

                        accX = redEvtAccX + blueEvtAccX;
                        accY = redEvtAccY + blueEvtAccY;
                        if (e.getX() < 600 && Math.hypot(velX, velY) > 10 && displayTime == 0) {
                            redGoals += 2;
                            redFastballNum += 2;
                            if (redPowerUpTime > 0) {
                                int powerupBonus = 2 + 2 * (int) (Math.random() * 3);
                                redPowerUpGoals += powerupBonus;
                                redGoals += powerupBonus;
                            }
                            redScores.setText("" + redGoals);
                            displayTime = 300;
                            text.setFill(Color.RED);
                            text.setText("Fastball!");
                        } else if (e.getX() > 600 && Math.hypot(velX, velY) > 10 && displayTime == 0) {
                            blueFastballNum += 2;
                            blueGoals += 2;
                            if (bluePowerUpTime > 0) {
                                int powerupBonus = 2 + 2 * (int) (Math.random() * 3);
                                bluePowerUpGoals += powerupBonus;
                                blueGoals += powerupBonus;
                            }
                            blueScores.setText("" + blueGoals);
                            displayTime = 300;
                            text.setFill(Color.BLUE);
                            text.setText("Fastball!");
                        }

                        velX += accX;
                        velY += accY;

                        if (puck.getLayoutX() < puck.getRadius() ||
                                puck.getLayoutX() > stage.getWidth() - puck.getRadius()) {
                            velX *= -0.95;
                            if (puck.getLayoutX() <= puck.getRadius()) {
                                puck.setLayoutX(puck.getRadius());
                                if (puck.getLayoutY() < 550 - puck.getRadius() && puck.getLayoutY() > 250 + puck.getRadius()) {
                                    text.setFill(Color.BLUE);
                                    text.setText("Goal!");
                                    displayTime = 200;
                                    blueGoals += 3;
                                    blueGoalNum += 3;
                                    if (bluePowerUpTime > 0) {
                                        int powerupBonus = 3 + 3 * (int) (Math.random() * 3);
                                        blueGoals += powerupBonus;
                                        bluePowerUpGoals += powerupBonus;
                                    }
                                    displayTime = 300;
                                    blueScores.setText("" + blueGoals);
                                }
                            } else {
                                puck.setLayoutX(stage.getWidth() - puck.getRadius());
                                if (puck.getLayoutY() < 550 - puck.getRadius() && puck.getLayoutY() > 250 + puck.getRadius()) {
                                    text.setFill(Color.RED);
                                    text.setText("Goal!");
                                    displayTime = 200;
                                    if (redPowerUpTime > 0) {
                                        int powerupBonus = 3 + 3 * (int) (Math.random() * 3);
                                        redGoals += powerupBonus;
                                        redPowerUpGoals += powerupBonus;
                                    }
                                    redGoals += 3;
                                    redGoalNum += 3;
                                    redScores.setText("" + redGoals);
                                }
                            }
                        }

                        if (displayTime > 0) {
                            textFlow2.setVisible(true);
                            text.setVisible(true);
                            textFlow.setLayoutX(600 - textFlow.getBoundsInLocal().getWidth() / 2);
                            textFlow2.setLayoutX(600 - text.getBoundsInLocal().getWidth() / 2);
                            textFlow2.setLayoutY(250 - text.getBoundsInLocal().getHeight() / 2);
                            displayTime--;
                        } else {
                            textFlow2.setVisible(false);
                        }

                        if (puck.getLayoutY() < puck.getRadius() ||
                                puck.getLayoutY() > stage.getHeight() - 2 * puck.getRadius()) {
                            velY *= -0.95;
                            if (puck.getLayoutY() < puck.getRadius()) {
                                puck.setLayoutY(puck.getRadius());
                            } else {
                                puck.setLayoutY(stage.getHeight() - 2 * puck.getRadius());
                            }
                        }

                        puck.setLayoutX(puck.getLayoutX() + velX);
                        puck.setLayoutY(puck.getLayoutY() + velY);
                        //Goal
                        time--;
                        if (powerupCooldownTime > 0) {
                            powerupCooldownTime--;
                        }

                        int seconds =  (time / 100) % 60;
                        int tenthsOfASecond =  (time / 10) % 600;
                        tenthsOfASecond -= 10 * seconds;

                        timeRemaining2.setText(time / 6000 + ":" + ((seconds < 10) ? "0" : "") + seconds + "." + tenthsOfASecond);

                        if (time < 1000) {
                            if (time % 200 >= 100) {
                                int sign = (int) Math.signum(redGoals - blueGoals);
                                if (sign == 0) {
                                    timeRemaining2.setFill(Color.MAGENTA);
                                } else {
                                    timeRemaining2.setFill(sign == 1 ? Color.RED : Color.BLUE);
                                }
                            } else {
                                timeRemaining2.setFill(Color.BLACK);
                            }
                        }

                        if (powerupCooldownTime == 0 && !powerup.isVisible()) {
                            powerup.setVisible(true);
                            powerup.setLayoutX(150 + 877 * Math.random());
                            powerup.setLayoutY(600 * Math.random());
                        }


                        if (powerup.getBoundsInParent().intersects(puck.getBoundsInParent()) && powerup.isVisible()) {
                            powerupCooldownTime = (int) (1500 + 1500 * Math.random()); //20 to 50 seconds
                            if (new Random().nextBoolean()) {
                                if (new Random().nextBoolean()) {
                                    redPowerUpTime = 1000;
                                    powerupTime.setText("Power Up: " + (redPowerUpTime / 100));
                                    powerupTime.setFill(Color.RED);
                                } else {
                                    bluePowerUpTime = 1000;
                                    powerupTime.setText("Power Up: " + (bluePowerUpTime / 100));
                                    powerupTime.setFill(Color.BLUE);
                                }
                            } else {
                                velX *= 2;
                                velY *= 2;
                            }
                            powerup.setVisible(false);
                        }

                        if (redPowerUpTime > 0) {
                            powerupTime.setText("Power Up: " + (redPowerUpTime / 100) + "." + (redPowerUpTime / 10 % 10));
                            textFlow3.setVisible(true);
                            powerupTime.setVisible(true);
                            redPowerUpTime--;
                        }
                        if (bluePowerUpTime > 0) {
                            powerupTime.setText("Power Up: " + (bluePowerUpTime / 100) + "." + (bluePowerUpTime / 10 % 10));
                            textFlow3.setVisible(true);
                            powerupTime.setVisible(true);
                            bluePowerUpTime--;
                        }
                        if ((redPowerUpTime == 0 && bluePowerUpTime == 0) || time == 0) {
                            textFlow3.setVisible(false);
                            powerupTime.setVisible(false);
                        }

                        if (time == 0 && blueGoals > redGoals) {
                            text.setFill(Color.BLUE);
                            text.setText("Blue Wins!");
                            text.setVisible(true);
                            textFlow.setLayoutX(600 - textFlow.getBoundsInLocal().getWidth() / 2);
                            textFlow2.setLayoutX(600 - text.getBoundsInLocal().getWidth());
                            textFlow2.setLayoutY(250 - text.getBoundsInLocal().getHeight() / 2);
                            textFlow2.setVisible(true);
                        } else if (time == 0 && redGoals > blueGoals) {
                            text.setFill(Color.RED);
                            text.setText("Red Wins!");
                            textFlow.setLayoutX(600 - textFlow.getBoundsInLocal().getWidth() / 2);
                            textFlow2.setLayoutX(600 - text.getBoundsInLocal().getWidth());
                            textFlow2.setLayoutY(250 - text.getBoundsInLocal().getHeight() / 2);
                            textFlow2.setVisible(true);
                            text.setVisible(true);
                        } else if (time == 0) {
                            LinearGradient rainbow = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                                    new Stop(0, Color.RED),
                                    new Stop(0.2d, Color.YELLOW),
                                    new Stop(0.4d, Color.LIME),
                                    new Stop(0.6d, Color.CYAN),
                                    new Stop(0.8d, Color.BLUE),
                                    new Stop(1.0d, Color.MAGENTA));
                            text.setFill(rainbow);
                            text.setText("Everyone Wins!");
                            text.setVisible(true);
                            textFlow2.setLayoutX(600 - text.getBoundsInLocal().getWidth());
                            textFlow2.setLayoutY(250 - text.getBoundsInLocal().getHeight() / 2);
                            textFlow2.setVisible(true);
                        }
                        if (time == 0) {
                            System.out.println("Red Stats:");
                            System.out.println("Red Score: " + redGoals);
                            System.out.println("Fastball Points: " + redFastballNum);
                            System.out.println("Goal Points: " + redGoalNum);
                            System.out.println("Powerup Points: " + redPowerUpGoals);
                            System.out.println("Penalty Points: " + (redGoals - (redPowerUpGoals + redFastballNum + redGoalNum)));
                            System.out.println("Blue Stats:");
                            System.out.println("Blue Score: " + blueGoals);
                            System.out.println("Fastball Points: " + blueFastballNum);
                            System.out.println("Goal Points: " + blueGoalNum);
                            System.out.println("Powerup Points: " + bluePowerUpGoals);
                            System.out.println("Penalty Points: " + (blueGoals - (bluePowerUpGoals + blueFastballNum + blueGoalNum)));
                            timeline.stop();
                            scene.setOnMouseClicked(null);
                        }
                        if (redCooldownTime > 0) {
                            redCooldownTime--;
                        }
                        if (blueCooldownTime > 0) {
                            blueCooldownTime--;
                        }
                    })
            );
            timelines.clear();
            timelines.add(timeline);
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        });
        stage.show();
    }

    public List<Charge> getCharges() {
        return charges;
    }
}
