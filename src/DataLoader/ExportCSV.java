package DataLoader;

import Matrix.Matrix;
import Matrix.Column;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExportCSV {

    public static void export(Matrix x, String fname) throws IOException {
        FileWriter fw = new FileWriter(fname);
        for (Column c : x.transpose().getMatrix()) {
            for (double datapoints : c.getColumn()) {
                fw.write(datapoints + ",");
            }
            fw.write("\n");
        }
        fw.close();
    }

    public static void export(ArrayList<Double> x, String fname) throws IOException {
        FileWriter fw = new FileWriter(fname);
        for (double num : x) {
            fw.write(num + "\n");
        }
        fw.close();
    }
}
