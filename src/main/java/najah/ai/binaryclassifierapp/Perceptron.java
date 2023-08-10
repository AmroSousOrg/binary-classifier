package najah.ai.binaryclassifierapp;

import java.util.Random;

public class Perceptron {

    /**
     *  perceptron attributes
     */
    private final double[] weights = {0, 0};
    private double learningRate = 0.3;
    private double threshold = 0.3;

    /**
     *  setters and getters
     */
    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public double[] getWeights() {
        return weights.clone();
    }

    public double getThreshold() {
        return threshold;
    }

    /**
     *  activation function
     */
    private int stepFunction(double value) {
        if (value < 0) return 0;
        return 1;
    }

    /**
     *  compute X by multiplying each input
     *  with the corresponding weight and accumulate result
     */
    private double calcBigX(final double[] inputs) {
        double bigX = 0;
        int length = inputs.length;

        for (int i = 0; i < length; i++) {
            bigX += weights[i] * inputs[i];
        }
        return bigX - threshold;
    }

    /**
     *  method used to adjust weights in training phase
     */
    private void adjustWeights(double[] inputs, int err) {
        int length = inputs.length;
        double dw, corr = err * learningRate;

        for (int i = 0; i < length; i++) {
            dw = corr * inputs[i];
            weights[i] += dw;
        }
        threshold += corr;
    }

    /**
     *  learn method that take single data
     *  and adjust weights depending on features vector
     */
    public void learn(Data data) {

        if (data == null) return;

        double[] inputs = data.features();
        double bigX = calcBigX(inputs);
        int actualOutput = stepFunction(bigX);
        int desiredOutput = data.recordClass();
        int err = desiredOutput - actualOutput;

        adjustWeights(inputs, err);
    }

    /**
     *  put random values for weights and threshold
     *  in range [-0.5, 0.5)
     */
    public void initialize() {
        Random rand = new Random();
        for (int i = 0; i < weights.length; i++) {
            weights[i] = rand.nextDouble(-0.5, 0.5);
        }
        threshold = rand.nextDouble(-0.5, 0.5);
    }

    /**
     *  method take test data and return
     *  the expected class depending on current weights
     */
    public int predictClass(double[] inputs) {
        double bigX = calcBigX(inputs);
        return stepFunction(bigX);
    }
}