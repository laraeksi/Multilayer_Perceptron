package DataLoader;

import Matrix.Matrix;
import Matrix.Column;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSV {
    private final String filename;
    private ArrayList<String> colName;
    private ArrayList<String> date;
    private Matrix data;
    private ArrayList<Double> columnMax;
    private ArrayList<Double> columnMin;

    public CSV(String filename) {
        this.filename = filename;
        this.data = new Matrix();
        this.date = new ArrayList<>();
        this.colName = new ArrayList<>();
        this.columnMax = new ArrayList<>();
        this.columnMin = new ArrayList<>();
    }

    public String getFilename() {
        return filename;
    }

    public Matrix getData() {
        return data;
    }

    public Column getRow(Integer index) {
        return new Column(data.getRow(index));
    }

    private void scale() {
        Matrix scaled = new Matrix();
        for (Column c : this.data.getMatrix()) {
            double min = ColumnMin(c);
            double max = ColumnMax(c);
            this.columnMin.add(min);
            this.columnMax.add(max);
            Matrix colMat = new Matrix();
            colMat.addCtoM(c);
            colMat = colMat.subtract(min).multiply(1 / (max - min));
            scaled.addCtoM(colMat.getColumn(0));
        }
        this.data = new Matrix(scaled);
    }

    private double ColumnMax(Column xs) {
        double max = 0;
        for (double x : xs.getColumn()) {
            max = Math.max(max, x);
        }
        return max;
    }

    private double ColumnMin(Column xs) {
        double min = 9999999;
        for (double x : xs.getColumn()) {
            min = Math.min(min, x);
        }
        return min;
    }

    public ArrayList<Double> scale(ArrayList<Double> x) throws IndexOutOfBoundsException {
        if (x.size() != this.columnMax.size()) {
            throw new IndexOutOfBoundsException("Input size does not match amount of columns expected");
        }
        else {
            ArrayList<Double> result = new ArrayList<>();
            for (int i = 0 ; i < x.size() ; i++) {
                result.add((x.get(i) - columnMin.get(i)) / (columnMax.get(i) - columnMin.get(i)));
            }
            return result;
        }
    }

    public ArrayList<Double> inverseScale(ArrayList<Double> x) throws IndexOutOfBoundsException {
        if (x.size() != this.columnMax.size()) {
            throw new IndexOutOfBoundsException("Input size does not match amount of columns expected");
        }
        else {
            ArrayList<Double> result = new ArrayList<>();
            for (int i = 0 ; i < x.size() ; i++) {
                result.add(x.get(i) * (columnMax.get(i) - columnMin.get(i)) + columnMin.get(i));
            }
            return result;
        }
    }

    public void readFile(boolean scale) throws IOException {
        boolean colNameDone = false;
        File file = new File(this.filename);
        FileReader reader = new FileReader(file);
        Column temp = new Column();
        StringBuilder cell = new StringBuilder();
        int c;
        while ((c = reader.read()) != -1) {
            String character = String.valueOf((char) c);
            if (character.equalsIgnoreCase("\n")) {
                if (!colNameDone) {
                    colNameDone = true;
                    colName.add(cell.toString());
                }
                else {
                    temp.add(Double.parseDouble(cell.toString()));
                    this.data.addCtoM(new Column(temp));
                }
                cell = new StringBuilder();
                temp = new Column();
            }
            else if (character.equalsIgnoreCase(",")) {
                if (!colNameDone) {
                    colName.add(cell.toString());
                }
                else {
                    if (colName.get(temp.getSize()).equalsIgnoreCase("date")) {
                        this.date.add(cell.toString());
                    }
                    else {
                        temp.add(Double.parseDouble(cell.toString()));
                    }
                }
                cell = new StringBuilder();
            }
            else {
                cell.append(character);
            }
        }
        reader.close();

        this.data = new Matrix(this.data.transpose());
        if (scale) {
            this.scale();
        }
    }
}
