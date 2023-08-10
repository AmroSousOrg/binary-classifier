package najah.ai.binaryclassifierapp;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *  view controller that handles UI events.
 *  It is the interface between UI and application logic
 */
public class ViewController {

    /**
     *  Model Class injection
     */
    private final Model model = new Model();

    /**
     *  canvas mode
     */
    private enum Mode {
        DEFAULT,
        BLACK,
        RED,
        ERASER,
        TEST
    }
    private Mode mode = Mode.DEFAULT;

    /**
     *
     *  UI elements
     */
    @FXML
    private Slider learningRate;

    @FXML
    private Label learningValueLabel;

    @FXML
    private TextField maxIterationField;

    @FXML Slider maxIteration;

    @FXML
    private Label messageLabel;

    @FXML
    private Canvas canvas;

    /**
     *
     *  UI listeners
     */
    @FXML
    private void learningSliderChanged() {
        learningValueLabel.setText(String.format("%.2f", learningRate.getValue()));
    }

    @FXML
    private void maxIterationChanged() {
        maxIterationField.setText(String.valueOf((int)maxIteration.getValue()));
    }

    @FXML
    private void maxIterationFieldChanged() {
        try {
            maxIteration.setValue(Double.parseDouble(maxIterationField.getText()));
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid Iteration Value.");
            maxIterationField.setText("5");
        }
    }

    @FXML
    private void blackPinFired() {
        canvas.setCursor(Cursor.CROSSHAIR);
        mode = Mode.BLACK;
    }

    @FXML
    private void redPinFired() {
        canvas.setCursor(Cursor.CROSSHAIR);
        mode = Mode.RED;
    }

    @FXML
    private void eraserFired() {
        canvas.setCursor(Cursor.CROSSHAIR);
        mode = Mode.ERASER;
    }

    @FXML
    private void clearPointsFired() {
        defaultCursorFired();
        model.deleteAllPoints();
        model.paintPoints(canvas);
    }

    @FXML
    private void undoPointFired() {
        defaultCursorFired();
        model.deleteLastPoint();
        model.paintPoints(canvas);
    }

    @FXML
    private void testPointFired() {
        canvas.setCursor(Cursor.CROSSHAIR);
        mode = Mode.TEST;
    }

    @FXML
    private void defaultCursorFired() {
        canvas.setCursor(Cursor.DEFAULT);
        mode = Mode.DEFAULT;
    }

    @FXML
    private void canvasDragged(MouseEvent e) {
        if (mode == Mode.ERASER) {
            model.clearPointsNear(e.getX(), e.getY(), 10);
            model.paintPoints(canvas);
        }
    }

    @FXML
    private void canvasClicked(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        switch (mode) {
            case BLACK -> {
                gc.setFill(Color.BLACK);
                model.addPoint(new Data(new double[] {x, y}, 0));
                model.paintPoints(canvas);
            }

            case RED -> {
                gc.setFill(Color.RED);
                model.addPoint(new Data(new double[] {x, y}, 1));
                model.paintPoints(canvas);
            }

            case TEST -> {
                int result = model.predictData(x, y);
                model.addPoint(new Data(new double[] {x, y}, result));
                gc.setFill(result == 0 ? Color.BLACK : Color.RED);
                gc.fillOval(x - 2, y - 2, 5, 5);
            }

            case ERASER -> {
                model.clearPointsNear(x, y, 10);
                model.paintPoints(canvas);
            }
        }
    }

    @FXML
    private void startLearningFired() {
        model.setLearningRate(learningRate.getValue());
        model.setMaxIteration((int) maxIteration.getValue());
        model.initPerceptron();
        model.train();
        model.drawClassificationLine(canvas);
    }
}