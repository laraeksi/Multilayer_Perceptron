package nn;
import Matrix.Matrix;


public interface NNComponent {//to make the other objects avaliable to components
    public Matrix forward(Matrix A);//first principle is forward propagation
    public Matrix backward(Matrix A);//second is backpropagation
   public Matrix getW();
   public void setW(Matrix newW);
    public Matrix getB();
    public void setB(Matrix newB);
    public boolean hasGrad();
    public Matrix getWgrad();
    public Matrix getBgrad();

}
