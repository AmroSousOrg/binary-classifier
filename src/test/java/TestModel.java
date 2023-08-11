import javafx.scene.canvas.Canvas;
import najah.ai.binaryclassifierapp.Data;
import org.junit.jupiter.api.Test;
import najah.ai.binaryclassifierapp.Model;


public class TestModel {

    Model model;

    @Test
    public void testModel() {
        this.model = new Model();
        model.setMaxIteration(100);
        model.setLearningRate(0.08);
        model.initPerceptron();
        model.addPoint(new Data(new double[]{1, 3}, 0));
        model.addPoint(new Data(new double[]{2, 2}, 0));
        model.addPoint(new Data(new double[]{3, 3}, 0));
        model.addPoint(new Data(new double[]{4, 1}, 0));
        model.addPoint(new Data(new double[]{4, 6}, 1));
        model.addPoint(new Data(new double[]{5, 7}, 1));
        model.addPoint(new Data(new double[]{5, 3}, 1));
        model.drawClassificationLine(new Canvas());
    }
}