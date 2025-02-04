Stock Market Prediction using Multilayer Perceptrons  

📌 Overview  
This project applies **Multilayer Perceptrons (MLPs)** to predict stock market trends based on historical data. The model is trained using **neural networks** and optimized with **Stochastic Gradient Descent (SGD)** to enhance prediction accuracy.  

🚀 Features  
- **Neural Network Architecture**: Built with fully connected layers, using **ReLU, LeakyReLU, and Sigmoid activations**.  
- **Batch Processing**: Implements batch learning for efficient training.  
- **Data Preprocessing**: Normalizes stock market data and scales inputs for better performance.  
- **Model Training & Optimization**: Uses **MSE Loss function** and **backpropagation** for learning.  
- **CSV-Based Input/Output**: Reads stock data from CSV files and outputs predictions for easy analysis.  

 🛠️ Technologies Used  
- **Java** (Primary language for neural network implementation)  
- **Data Handling**: CSV processing for stock market datasets  
- **Mathematical Operations**: Matrix manipulation for neural network calculations  
- **Optimization Algorithm**: Stochastic Gradient Descent (SGD)  

## 📂 Project Structure  
📦 Stock-Market-Prediction
├── 📂 Matrix/ # Matrix and Column classes for neural network operations
│ ├── Matrix.java # Matrix class for mathematical operations
│ ├── Column.java # Column class for handling individual data columns
│
├── 📂 nn/ # Neural Network implementation
│ ├── Linear.java # Linear layer for the network
│ ├── Activations/ # Activation functions (ReLU, LeakyReLU, Sigmoid, Tanh)
│ ├── MSELoss.java # Mean Squared Error loss function
│ ├── Sequential.java # Neural network sequential model
│ ├── optimise/SGD.java # Stochastic Gradient Descent optimizer
│
├── 📂 DataLoader/ # CSV and batch processing utilities
│ ├── CSV.java # Handles CSV file operations
│ ├── Batcher.java # Splits dataset into batches
│ ├── ExportCSV.java # Exports predictions to CSV
│
├── Main.java # Main script to train and test the model
├── .gitignore # Ignore unnecessary files
└── README.md # Documentation
