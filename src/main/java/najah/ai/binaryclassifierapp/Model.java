package najah.ai.binaryclassifierapp;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


public class Model {

    /**
     *  class attributes
     */
    private final List<Data> points = new ArrayList<>();
    private final Perceptron perceptron = new Perceptron();
    private int maxIteration;

    /**
     *  setters and getters
     */
    public void setMaxIteration(int value) {
        this.maxIteration = value;
    }

    public void setLearningRate(double learningRate) {
        perceptron.setLearningRate(learningRate);
    }

    /**
     *  functionality methods
     */
    public void addPoint(Data point) {
        points.add(point);
    }

    public void deleteLastPoint() {
        if (points.isEmpty()) return;
        points.remove(points.size() - 1);
    }

    public void deleteAllPoints() {
        points.clear();
    }

    public void train() {
        for (int itr = 1; itr <= maxIteration; itr++) {
            points.forEach(perceptron::learn);
        }
    }

    public void initPerceptron() {
        perceptron.initialize();
    }

    public int predictData(double x, double y) {
        return perceptron.predictClass(new double[] {x, y});
    }

    public void clearPointsNear(double x, double y, double radius) {
        List<Data> toRemove = points.stream().filter(
                p -> calcDistance(x, y, p.features()[0], p.features()[1]) <= radius).toList();
        points.removeAll(toRemove);
    }

    public void drawClassificationLine(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLUE);
        double th = perceptron.getThreshold();
        double w1 = perceptron.getWeights()[0];
        double w2 = perceptron.getWeights()[1];
        double slope = -w1 / w2;
        double yIntercept = th / w2;
        double x1, x2, y1, y2;

        if (slope >= 0) {
            if (yIntercept <= 0) {
                x1 = 0;
                y1 = th / w2;
            } else {
                y1 = 0;
                x1 = th / w1;
            }
            y2 = canvas.getHeight();
            x2 = (th - y2 * w2) / w1;
        }
        else {
            x1 = 0;
            y1 = th / w2;
            y2 = 0;
            x2 = th / w1;
        }
        System.out.printf("Weights --> " + w1 + " , " + w2 + " , threshold = " + th + "\n");
        System.out.printf("Line --> " + x1 + " , " + y1 + " , " + x2 + " , " + y2 + "\n");
        gc.strokeLine(x1, y1, x2, y2);
    }

    public void paintPoints(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        points.forEach(p -> {
            gc.setFill(p.recordClass() == 0 ? Color.BLACK : Color.RED);
            gc.fillOval(p.features()[0] - 2, p.features()[1] - 2, 5, 5);
        });
    }

    private double calcDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
