package DataLoader; // Defines the package name for this class.

import Matrix.Matrix; // Imports the Matrix class from the Matrix package.

public class Batcher { // Declares the Batcher class.
    private Integer batchSize; // Variable to store the size of each data batch.
    private CSV csv; // Variable to store the CSV data handler.

    public Batcher(Integer batchSize, CSV csv) { // Constructor for the Batcher class.
        this.batchSize = batchSize; // Initializes batchSize with the provided argument.
        this.csv = csv; // Initializes csv with the provided CSV handler.
    }

    public Integer getBatchSize() { // Getter method for batchSize.
        return batchSize; // Returns the current batch size.
    }

    public void setBatchSize(Integer batchSize) { // Setter method for batchSize.
        this.batchSize = batchSize; // Sets the batchSize to the provided value.
    }

    public int size() { // Calculates the total number of batches that can be formed.
        return csv.getData().size().get(0) - this.batchSize + 1; // Returns the calculation based on the CSV data size and batchSize.
    }

    public Matrix getBatch(Integer index) throws IllegalArgumentException { // Method to get a specific batch of data as a Matrix.
        if (index + this.batchSize > csv.getData().size().get(0)) { // Checks if the requested batch exceeds the data bounds.
            throw new IllegalArgumentException("index too high"); // Throws an exception if the index is too high.
        }
        else {
            Matrix batch = new Matrix(); // Creates a new Matrix to hold the batch data.
            for (int i = index ; i < index + this.batchSize ; i++) { // Loops over the rows of the batch.
                batch.addCtoM(csv.getRow(i)); // Adds each row to the Matrix.
            }
            return batch; // Returns the batch Matrix.
        }
    }
}