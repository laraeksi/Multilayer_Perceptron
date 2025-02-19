package nn.optimise;

import nn.Optimisers;
import nn.Sequential;
import Matrix.Matrix;

public class SGD implements Optimisers {
    private double lr;
    public SGD(double lr) {
        this.lr = lr;
    }

    public void step(Sequential network) {
        for (int layer = network.getNetwork().size() - 1 ; layer >= 0 ; layer--) {
            Matrix gradb = network.getNetwork().get(layer).getBgrad();
            Matrix gradW = network.getNetwork().get(layer).getWgrad();
            if ((gradb != null) && (gradW != null)) { // only linear satisifies
                Matrix b = new Matrix(network.getNetwork().get(layer).getB());
                Matrix W = new Matrix(network.getNetwork().get(layer).getW());
                network.getNetwork().get(layer).setW(W.subtract(gradW.multiply(this.lr)));
                network.getNetwork().get(layer).setB(b.subtract(gradb.multiply(this.lr)));
            }
        }
    }

    public void changeLR(double lr) {
        this.lr = lr;
    }
}
