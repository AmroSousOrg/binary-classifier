package najah.ai.binaryclassifierapp;

/**
 *  Data Record represent the data for perceptron training and testing
 *  -features: array of doubles that represents the features vector of data
 *  -color: is the actual class of this particular data
 */
public record Data(double[] features, int recordClass) {}
