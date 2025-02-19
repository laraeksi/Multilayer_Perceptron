package nn;

import java.util.ArrayList;
import Matrix.Matrix;
import Matrix.Column;

public class Sequential implements NNComponent {
    private String name;
    private ArrayList<NNComponent> network;

    public Matrix getW() {return null;}
    public void setW(Matrix newW) {}
    public Matrix getB() {return null;}
    public void setB(Matrix newb) {}

    public Sequential(String name) {
        this.network = new ArrayList<>();
        this.name = name;
    }

    public Sequential() {
        this.network = new ArrayList<>();
        this.name = "";
    }

    public void addModule(NNComponent... module) throws IllegalArgumentException {
        for (NNComponent m : module) {
            if (m.getClass().getSimpleName().equals("Sequential")) {
                throw new IllegalArgumentException("Sequential Concatenation must be done using `Sequential.concat`");
            }
            else {
                network.add(m);
            }
        }
    }

    public ArrayList<NNComponent> getNetwork() {
        return this.network;
    }

    public void concat(Sequential s) {
        this.network.addAll(s.getNetwork());
    }

    public Matrix forward(Matrix Y) {
        for (NNComponent comp : this.network) {
            Y = comp.forward(Y);
        }
        return Y;
    }

    public Matrix backward(Matrix Y) {
        return null;
    }

    public void backward(Loss loss) {
        Matrix delta = loss.getLossGrad();
        for (int layer = network.size() - 1 ; layer >= 0 ; layer--) {
            delta = network.get(layer).backward(delta);
        }
    }

    public boolean hasGrad() {
        return false;
    }
    public Matrix getWgrad() { return null; }
    public Matrix getBgrad() { return null; }
}
