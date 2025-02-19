package nn;
import Matrix.Matrix;
public interface Loss {

   public double compute(Matrix target, Matrix output);
 public Matrix getLossGrad();
}
