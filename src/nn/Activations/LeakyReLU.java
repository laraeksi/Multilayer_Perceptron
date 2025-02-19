package nn.Activations;
import Matrix.Matrix;
import Matrix.Column;
import nn.NNComponent;

public class LeakyReLU implements NNComponent {
    private String name;
    private double gradient;//gradient of LeakyReLU, equivalent to alpha

    public Matrix getW(){return null;}
    public void setW(Matrix newW){}

    public Matrix getB(){return null;}
    public void setB(Matrix newB){}

    private Matrix ins;

    public LeakyReLU(String name, double grad) {
        this.name = name;
        this.gradient = grad;
    }

    public LeakyReLU(double grad) {
        this.name = "ReLU";
        this.gradient = grad;
    }
    public Matrix forward(Matrix A){
        this.ins= new Matrix(A);
        Matrix res= new Matrix();
    for(Column c:A.getMatrix()){
        Column temp= new Column();
        for(double x:c.getColumn()){
            temp.add(lrelu(x));//putting each element in the matrix to the lrelu function
        }
        res.addCtoM(temp);
    }
    return res;
    }
    public Matrix backward(Matrix A){
       Matrix res = new Matrix();
       for(Column c:this.ins.getMatrix()){
           Column temp= new Column();
           for(double x:c.getColumn()){
               temp.add(grad_lrelu(x));
           }
           res.addCtoM(temp);
       }
       return A.dot(res.transpose());// dot and transpose ekle
    }
    private double lrelu(Double x) {
        // max(0, x)
        return Math.max(this.gradient * x, x);//to solve the dying ReLU problem//shouldn't we put a if statement to have condition to send it to the next layer
    }

    private double grad_lrelu(Double x) {
        if (x < 0) {
            return this.gradient;
        } else {
            return 1;
        }
    }
    public boolean hasGrad(){return false;}//will be used if the gradient found,defined with their initial value
    public Matrix getWgrad(){return null;}// to get the gradient of weight

    public Matrix getBgrad(){return null;}// to get the bias value

}