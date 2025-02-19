package nn;

import Matrix.Matrix;
import Matrix.Column;
import java.util.Random;

public class Linear implements NNComponent {
    private String name;
    private int in_features;
    private int out_features;
    private boolean bias;
    private Matrix W;
    private Matrix b;
    private Matrix ins;
    private Matrix Wgrad;
    private Matrix bgrad;

    public Matrix getW() {
        return this.W;
    }

    public void setW(Matrix newW) {
        W = new Matrix(newW);
    }

    public Matrix getB() {
        return this.b;
    }

    public void setB(Matrix newb) {
        b = new Matrix(newb);
    }

    public Linear(String name, int in_features, int out_features, boolean bias) {
        this.in_features = in_features;
        this.out_features = out_features;
        this.name = name;
        this.bias = bias;
        this.W = new Matrix();
        this.b = new Matrix();

        init_w();
        init_b();
    }

    public Linear(int in_features, int out_features, boolean bias) {
        this.in_features = in_features;
        this.out_features = out_features;
        this.name = "";
        this.bias = bias;
        this.W = new Matrix();
        this.b = new Matrix();

        init_w();
        init_b();
    }

    public Linear(int in_features, int out_features) {
        this.in_features = in_features;
        this.out_features = out_features;
        this.name = "";
        this.bias = true;
        this.W = new Matrix();
        this.b = new Matrix();
        init_w();
        init_b();
    }

    private float LowerBound_init() {
        return (float) -Math.sqrt((double) 1 / in_features);
    }

    private float UpperBound_init() {
        return (float) Math.sqrt((double) 1 / in_features);
    }

    private float RandomUniform(float lb, float ub) {
        Random RandUniform = new Random();
        return (ub - lb) * RandUniform.nextFloat() + lb;
    }

    private void init_w() {
        for (int c = 0 ; c < this.in_features ; c++) {
            Column col = new Column();
            for (int r = 0 ; r < this.out_features ; r++) {
                col.add(RandomUniform(LowerBound_init(), UpperBound_init()));
            }
            this.W.addCtoM(col);
        }
    }

    private void init_b() {
        Column col = new Column();
        for (int r = 0 ; r < this.out_features ; r++) {
            col.add(RandomUniform(LowerBound_init(), UpperBound_init()));
        }
        this.b.addCtoM(col);
    }


    public Matrix forward(Matrix x) throws ArithmeticException {
        try {
            this.ins = x;
            Matrix xW = this.W.multiply(x);
            Matrix outs = new Matrix();
            for (int i = 0 ; i < xW.size().get(1) ; i++) {
                Matrix temp = new Matrix();
                temp.addCtoM(xW.getColumn(i));
                temp.addition(this.b);
                outs.addCtoM(temp.getColumn(0));
            }
            return outs;
        }
        catch (ArithmeticException AE) {
            throw new ArithmeticException("Neural Network Layer: " + this.name + " has an input shape mismatch of W shape " + this.W.size() + " and input size " + x.size());
        }
    }

    public Matrix backward(Matrix x) {
        Matrix inputGrad = x.multiply(this.W);
        this.Wgrad = x.transpose().multiply(this.ins.transpose());

        this.bgrad = new Matrix();
        this.bgrad.addCtoM(x.getRow(0));
        for (int i = 1 ; i < x.size().get(0) ; i++) {
            Matrix temp = new Matrix();
            temp.addCtoM(x.getRow(i));
            this.bgrad.addition(temp);
        }
        return inputGrad;
    }

    public boolean hasGrad() {
        return true;
    }

    public Matrix getWgrad() {
        return this.Wgrad;
    };
    public Matrix getBgrad() {
        return this.bgrad;
    }
}
