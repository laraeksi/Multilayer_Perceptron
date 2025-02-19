package nn.Activations;

import Matrix.Matrix;
import Matrix.Column;
import nn.NNComponent;
public class Sigmoid implements NNComponent {
    private String name;
    private Matrix ins;
    public Sigmoid(String name){this.name=name;}
    public Sigmoid(){this.name="Sigmoid";}
    public Matrix forward (Matrix A){
        this.ins= new Matrix(A);
        Matrix res= new Matrix();
        for (Column c:A.getMatrix()){
            Column temp= new Column();
            for(double x:c.getColumn()){
                temp.add(this.sig(x));
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
            temp.add(this.grad_sig(x));

            }
            res.addCtoM(temp);

        }
        return A.dot(res.transpose());

    }
    public Matrix getW(){return null;}
    public void setW(Matrix newW){}
    public Matrix getB(){return null;}
    public void setB(Matrix newb){}

private double sig(double x){return(double)(1 / (1 + Math.exp(-x)));}//the formula of the sigmoid which is explained in the report for forward propagation
private double grad_sig(double x){return this.sig(x)*(1- this.sig(x));}//back propagation
    public boolean hasGrad(){return false;}//will be used if the gradient found,defined with their initial value
    public Matrix getWgrad(){return null;}// to get the gradient of weight

    public Matrix getBgrad(){return null;}


}
