package nn.Activations;
import Matrix.Matrix;
import Matrix.Column;
import nn.NNComponent;//we need a component file to identify ReLU's job in forward and backpropagation parts.
public class ReLU implements NNComponent {
    private String name;// to debug the problems in the program from multiple matrices we need to identify them, so we gave names to the matrices.

    private Matrix ins;
    public ReLU(String name) {//Polymorphic constructor
        this.name = name;
    }

    public ReLU() {//Polymorphic constructor
        this.name = "ReLU";
    }


    public Matrix getW() {// to do forward propagation we get the weight
        return null;
    }

    public void setW(Matrix newW) {// to update a weight while backpropagation we need to set a new weight
    }

    public Matrix getB() {// to forward propagation we need to get bias to calculate the ReLU
        return null;
    }

    public void setB(Matrix newb) {// to update the biases we need to set a bias
    }


    public Matrix forward(Matrix A) {
        this.ins= new Matrix(A);// to store input matrix
        Matrix res = new Matrix();//create other matrix to do forward prob
       for (Column c:A.getMatrix()){//looping -a column in A matrix copied into the column c
            Column temp= new Column();// column is created for every repeating in the loop
           for(double x:c.getColumn()){//looping -the element in the column c copied into x
                temp.add(relu(x));//the element goes through from the ReLU and added into the new column every time column is repeated
            }

            res.addCtoM(temp);// the column added into the new matrix res
        }
        return res;
    }

    public Matrix backward(Matrix A) {
        Matrix res = new Matrix();
         for(Column c:this.ins.getMatrix()){
             Column temp= new Column();
             for(double x:c.getColumn()){
                 temp.add(grad_relu(x));
             }


        res.addCtoM(temp);
    }
        return A.dot(res.transpose());
}
private double relu(Double x){return Math.max(0,x);}//this is relu formula which is explained in report
private float grad_relu(Double x){//this is formula of the gradient of relu , it will be used in backpropagation
        if(x<=0){
            return 0;
        } else{
            return 1;
        }
}
public boolean hasGrad(){return false;}//will be used if the gradient found,defined with their initial value
public Matrix getWgrad(){return null;}// to get the gradient of weight

public Matrix getBgrad(){return null;}// to get the bias value



}