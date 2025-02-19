package nn;
import Matrix.Matrix;
import Matrix.Column;

public class MSELoss implements Loss {
    private double loss;
    private Matrix lossGrad;

    public double compute(Matrix target, Matrix output) throws ArithmeticException {
        if (!target.size().equals(output.size())) {
            throw new ArithmeticException("Targetted Matrix Size should be identical to the Network Output");
        }

        Matrix directional = target.subtract(output);
        this.loss = MatrixAvg(directional.dot(directional));
        this.lossGrad = directional.multiply(-2.0);
        return this.loss;
    }

    private double MatrixAvg(Matrix x) {
        double result = 0;
        for (Column col : x.getMatrix()) {
            for (double val : col.getColumn()) {
                result += val;
            }
        }
        return result / (x.size().get(0) * x.size().get(1));
    }

    public Matrix getLossGrad() {
        return this.lossGrad.transpose();
    }
}
