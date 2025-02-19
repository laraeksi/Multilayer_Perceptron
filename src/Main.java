import DataLoader.Batcher;
import DataLoader.CSV;
import DataLoader.ExportCSV;
import nn.Activations.ReLU;
import nn.Activations.LeakyReLU;
import nn.Activations.Sigmoid;
import nn.Activations.Tanh;
import nn.Linear;
import nn.MSELoss;
import nn.optimise.SGD;
import nn.Sequential;
import Matrix.Matrix;
import Matrix.Column;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    // Main method declaration which is the entry point of the program
    public static void main(String[] args) throws IOException {
        // Initializes a Sequential neural network with a given name 'mlp'
        Sequential nn = new Sequential("mlp");
        // Adds layers to the neural network: three Linear layers with LeakyReLU activations in between
        nn.addModule(
                new Linear(20, 64),
                new LeakyReLU(-0.2),
                new Linear(64, 16),
                new LeakyReLU(-0.2),
                new Linear(16, 1)
        );

        // Initializes CSV objects for input and output datasets, specifying the file paths
        CSV InputDataset = new CSV("GOLD10Days_xtrain.csv");
        CSV OutputDataset = new CSV("GOLD10Days_ytrain.csv");
        // Reads the datasets from the files, with scaling enabled.
        InputDataset.readFile(true);
        OutputDataset.readFile(true);

        // Creates Batcher objects for input and output datasets with a batch size of 8
        Batcher xBatcher = new Batcher(8, InputDataset);
        Batcher yBatcher = new Batcher(8, OutputDataset);
        // Asserts that the number of batches in both batchers is equal
        assert xBatcher.size() == yBatcher.size();

        // Initializes lists to track batch-wise and epoch-wise loss
        ArrayList<Double> BatchWiseLoss = new ArrayList<>();
        ArrayList<Double> EpochWiseLoss = new ArrayList<>();
        // Initializes an SGD optimizer with a learning rate of 0.01
        SGD optim = new SGD(0.01);
        // Initializes a mean squared error loss criterion
        MSELoss criterion = new MSELoss();

        // Specifies the number of training epochs
        int epoch = 10;
        // Training loop over the specified number of epochs
        for (int i = 0 ; i < epoch ; i++) {
            // Initializes the loss for the current epoch
            double loss = 0;
            // Iterates over each batch in the dataset
            for (int batchNo = 0 ; batchNo < xBatcher.size() ; batchNo++) {
                // Retrieves the current batch of input and output data
                Matrix Xtrain = xBatcher.getBatch(batchNo);
                Matrix Ytrain = yBatcher.getBatch(batchNo);
                // Forward pass: computes the network's output for the current batch
                Matrix outs = nn.forward(Xtrain);
                // Computes the loss between the predicted outputs and the actual outputs
                loss = criterion.compute(Ytrain, outs);
                // Adds the current batch's loss to the list for tracking
                BatchWiseLoss.add(loss);
                // Backward pass: computes gradients for the loss with respect to the network parameters
                nn.backward(criterion);
                // Updates the network parameters using the computed gradients
                optim.step(nn);
                // Prints the loss information every 100 batches
                if (batchNo % 100 == 0) {
                    System.out.println("Epoch " + (i+1) + "/" + epoch + "; Batch " + batchNo + "/" + xBatcher.size() + "; loss - " + loss);
                }
            }
            // Prints the loss at the end of each epoch
            System.out.println("Epoch " + (i+1) + "/" + epoch + "; loss - " + loss);
            // Adds the epoch's loss to the list for tracking
            EpochWiseLoss.add(loss);
        }
        // Exports the batch-wise and epoch-wise loss to CSV files for analysis
        ExportCSV.export(BatchWiseLoss, "batchwiseloss.csv");
        ExportCSV.export(EpochWiseLoss, "epochwiseloss.csv");

        // Indicates the end of training.
        System.out.println("*** Training Completed ***");
        // Instructions for the user for evaluation phase.
        System.out.println("-> Input Empty String to terminate");
        System.out.println("-> Expected File Type to be CSV");
        // Loop for evaluation phase, allowing the user to test the model with new data
        String fname;
        while (true) {
            System.out.print("-> Evaluation Data Filename: ");
            Scanner scanner = new Scanner(System.in);
            fname = scanner.nextLine();
            // Breaks the loop if the input string is empty
            if (fname.equalsIgnoreCase("")) {
                break;
            }
            else {
                CSV Xeval;
                // Checks if the filename ends with '.csv', appending it if necessary
                if (fname.endsWith(".csv")) { Xeval = new CSV(fname); }
                else { Xeval = new CSV(fname + ".csv"); }
                try {
                    // Reads the evaluation dataset from the file without scaling
                    Xeval.readFile(false);

                    // Processes the evaluation data
                    Matrix eval = new Matrix();
                    for (Column c : Xeval.getData().transpose().getMatrix()) {
                        // Scales the evaluation data using the same scaling as the training input dataset
                        Column scaled = new Column(InputDataset.scale(c.getColumn()));
                        eval.addCtoM(scaled);
                    }

                    // Performs a forward pass on the evaluation data
                    Matrix Yeval = new Matrix(nn.forward(eval));
                    Matrix result = new Matrix();
                    for (Column c : Yeval.getMatrix()) {
                        // Inverse scales the predicted data to its original scale using the scaling from the output dataset
                        ArrayList<Double> row = new ArrayList<>(c.getColumn());
                        Column results = new Column(OutputDataset.inverseScale(row));
                        result.addCtoM(results);
                    }

                    // Prompts the user for an output filename to export the predictions
                    System.out.print("-> Output Data Filname: ");
                    String outfname = scanner.nextLine();
                    // Exports the predictions to a CSV file, appending '.csv' if necessary
                    if (outfname.endsWith(".csv")) { ExportCSV.export(result.transpose(), outfname); }
                    else { ExportCSV.export(result.transpose(), outfname + ".csv"); }
                }
                catch (IOException ioe) {
                    // Handles file not found errors
                    System.out.println("-> File Not Found, Please try again");
                }
            }
        }
    }
}
