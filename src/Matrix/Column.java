package Matrix;


import java.util.ArrayList;

public class Column {
    private ArrayList<Double> c;// c is a list of double numbers

    public Column() {
        this.c = new ArrayList<>();// we are defining this list of double numbers as column and so we can use this in other objects

    }

    public Column(Column c) {
        this.c = new ArrayList<>(c.getColumn());// here it is for making a column the current column,so it can be defined in functions
    }//as you can see here "this" is very important to use to not make a confusion between parameter and class attribute

    public Column(ArrayList<Double> c) {// this acc has pretty same reasons to previous one but c can be just refered as Arraylist so we also need to do for this
        this.c = c;// when a list put into column() it will create a new column from that list
        // so we are basically putting the parameter as class attribute for the function

    }


    public ArrayList<Double> getColumn() {// we may need to get the numbers in the column together as a one for calculations often in Matrix class so we can function it
        return this.c;// get the end column
        // we are basically return it so in the end we can output

    }


    public int getSize() {


        return c.size();//get the size of the column
        // it calculates the number of the items in column
        // even though it can be refered as by just size , putting it in a object would be more efficient and make it easier for future when also matrix is involved
    }

    public Column get(ArrayList<Integer> indices) {// the numbers in the array list being used for indicating items' index number
        Column col = new Column();
        for (int i : indices) {
            col.add(this.c.get(i));// so that the items which have indicated index numbers can be put into the column
        }
        return col;// which is going to be return here
    }// so we are basically creating a new column here that we want to return depending on their index number

    public double get(int i) {
        return c.get(i);
    }//get a element from a column
    // this is similar to previous one put this is just gets one specific item depending on the index numbered put into as parameter

    public void add(double j) {

        c.add(j);
    }// c is private memory therefore using void method is helping encapsulation.The internal workings of c is modified are hidden from the outside.So add method provides a clear and controlled way to interact with c.
}












