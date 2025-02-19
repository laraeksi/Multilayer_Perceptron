package Matrix;

import java.util.ArrayList;
import java.util.Arrays;

public class Matrix {
    private ArrayList<Column> matrix;//matrix is a list of columns which is means a list of lists of numbers

    private String name;// we will need a name since there will be weight ,biases and more matrices  also for identifying the bugs much faster
public Matrix(){//here we are defining the list of columns as Matrix
    this.name="";//
    this.matrix= new ArrayList<Column>();
}
public Matrix(String special,ArrayList<Integer> dim){//dim holds two elements :number of rows and the number of columns e.g.(rowNum,ColNum)
    this.matrix=new ArrayList<>();
    switch(special){
        case("I")://identity matrix to find if two matrices are inverse of one each other, we might need this while doing derivative on backpropagation
           if (dim.get(0).equals(dim.get(1))){//identity matrix is square ,so first we are checking if it is squared by being sure the number of columns and rows are equal.
               for(int col=0;col<dim.get(1);col++){//this loop iterates for each column
                   Column c = new Column();
                   for (int row=0;row<dim.get(0);row++){//this loop iterates for each row
                       if(col==row){
                           c.add(1);//this is the structure of the identity matrix if the number of the row and column are equal, 1 is added.
                       }else {
                           c.add(0);//if not 0 is added to that place
                       }

                   }
                   this.matrix.add(c);//after column is created, it is added into the matrix
               }//and it creates thw next column until it reaches the number of the columns in dim(dimemtion).

        }else {
               throw new ArithmeticException("Identity matrix should be square!! ");// added exception here if in the case the numbers of columns and rows are not equal , to identify the bugs better in the continous of the project
           }break;

        case("0"):
            for(int col=0;col<dim.get(1);col++){
                Column c= new Column();
                for (int row=0;row<dim.get(0);row++){
                    c.add(0);
                }
                this.matrix.add(c);//the matrix is just filled with 0's
            }break;
        case("1"):
            for(int col=0;col<dim.get(1);col++){
                Column c= new Column();
                for(int row=0;row<dim.get(0);row++){
                    c.add(1);
                }
                this.matrix.add(c);
            }break;
    }

}
public Matrix(Matrix A ){
    this.matrix= new ArrayList<>();
    for(int i=0;i<A.size().get(1);i++){
        this.matrix.add(A.getColumn(i));
    }
}
    public Matrix(String name) {//identify with name (W ,B,...)
        this.matrix = new ArrayList<Column>();
        this.name = name;
    }
    public Matrix(Matrix A, String name) {//matrix A(putting it to public)
        this.matrix = new ArrayList<>();

        for (int i = 0; i < A.size().get(1); i++) {// getting the elements that is in the index 1, the number of the columns
            this.matrix.add(A.getColumn(i));

        }
        this.name=name;
    }
    public Matrix(ArrayList<Column> A, String name){
        this.matrix=A;
        this.name=name;
    }

    public void setName(String name) {  //there will be more than one matrix will be used so to not make an confusion to computer.SO I added class for names.
        this.name = name;
    }
    public String name() {//specify the name

        return name;
    }
    public Matrix(ArrayList<Column> matrix){this.matrix=matrix;}

    public ArrayList<Column> getMatrix() {
        return this.matrix;
    }
    public ArrayList<Integer> size() {
        ArrayList<Integer> size_tuple = new ArrayList<>();
        size_tuple.add(matrix.get(0).getSize());//get 0 column and get the size means the numbrows
        size_tuple.add(matrix.size());//number of columns

        return size_tuple;

    }
    public Column getColumn(int colNum) {//return the column according to the colNum
        return this.matrix.get(colNum);

    }
    public Column getRow(int rowNum) {//get row
        Column rowsCol = new Column();//creating a column
        for (Column c : this.matrix) {//choose all the columns one by one
            rowsCol.add(c.get(rowNum));//take the choosen column and take the rowNumth place number and put it into rowsCol
        }
        return rowsCol;//return it,NOTE:it is return as column because it will be use on multiplication so that change on m atrix rows as columns and than multiply.
    }
    public void addCtoM(Column c) {//add column c to the matrix
        this.matrix.add(c);

    }
    public double get(int row, int column) {
        return this.matrix.get(column).get(row);

    }

    public Matrix addition(Matrix B) throws ArithmeticException {
        Matrix result = new Matrix("addition_B");//creating the matrix to return the result ( not sure about addition_B)
        if (this.size().equals(B.size())) {//checking if the sizes are equal to add them together
            for (int col = 0; col < this.size().get(1); col++) { //get the number of columns
                Column x = new Column();//create a column
                for (int row = 0; row < this.size().get(0); row++) {//get the number of rows
                    x.add(B.get(row, col) + this.get(row, col));// get the elements in locations in two matrixes and add them together

                }
                result.addCtoM(x);//add the column to matrix
            }
            return result;
        } else {
            throw new ArithmeticException("Matrix" + this.name() + "and matrix" + B.name() + "not suitable for addition because of the difference between their size's/dimension.");//if the numbers of the rows and columns are not equal ,print this


        }

    }
    public Matrix addition(double B) {
        Matrix result = new Matrix();
        for (int col = 0 ; col < this.size().get(1) ; col++) {
            Column res = new Column();
            for (int row = 0 ; row < this.size().get(0) ; row++) {
                res.add(this.get(row, col) + B);
            }
            result.addCtoM(res);
        }
        return result;
    }

    public Matrix multiply(Double i) {//why do why need to use Double with big D
        Matrix result = new Matrix();//create a matrix
        for (int col = 0; col < this.size().get(1); col++) {//create columns the number of the columns in the matrix
            Column x = new Column();
            for (int row = 0; row < this.size().get(0); row++) {//create the rows
                x.add(this.get(row, col) * i);//multiply every elements with i and store it in x
            }
            result.addCtoM(x);// add the columns in result matrix
        }
        return result;//return the result

    }

    public Matrix transpose() {
        Matrix result = new Matrix("T");//create a matrix
        for (int row = 0; row < this.size().get(0); row++) {//create columns according to number of the columns in the matrix
            Column x = new Column(this.getRow(row).getColumn());
            result.addCtoM(x);
           }
        return result;//return the result
    }

public Matrix dot(Matrix B) throws ArithmeticException{//does (dot)multiplication
    Matrix result= new Matrix();
    if(this.size().equals(B.size())){//checking that the left matrix and right matrix have equal number of columns
        for(int i=0;i<this.size().get(1);i++){// final product have dimension size of (A.row,B.column)
            Column col1= this.getColumn(i);
            Column col2= B.getColumn(i);
            Column temp=new Column();
            for (int j=0;j<this.size().get(0);j++){
                temp.add(col1.get(j)*col2.get(j));//ASK THIS IF  COL2 HAS NO ELEMENT ON THAT PLACE IS İT JUST PUTTING THE VALUE FRON THE COL1
            }
            result.addCtoM(temp);

        }
        return result;

    }else {
        throw new ArithmeticException("Matrix " + this.name + " and " + B.name + " does not match for dot");

    }

}

    public Matrix multiply(Matrix B) throws ArithmeticException {
    if(B.size().equals(Arrays.asList(1,1))){//if there is just one element in B matrix
        return this.multiply(B.get(0,0));//whole matrix will be multiplied with that one element in the B matrix
    }else if (this.size().equals(Arrays.asList(1,1))){//if there is just one element in the current matrix
        return B.multiply(this.get(0,0));//all the elements in the B matrix will be multiplied with that one element
    }

    else {Matrix result = new Matrix(); // (rows, columns)
        if (this.size().get(1).equals(B.size().get(0))) { // left matrix has same amount of columns as the right matrix's rows
            for (int i = 0 ; i < B.size().get(1) ; i++) { // because we expect the resulting matrix to have size (A.rows, B.columns)
                Column res = new Column();
                Column Bcol = B.getColumn(i);
                for (int j = 0 ; j < this.size().get(0) ; j++) {
                    Column Arow = this.getRow(j);
                    double temp = 0;
                    for (int k = 0 ; k < this.size().get(1) ; k++) {
                        temp += Arow.get(k) * Bcol.get(k);
                    }
                    res.add(temp);
                }
                result.addCtoM(res);
            }
            return result;
        }
        else {
            throw new ArithmeticException("Matrix " + this.name() + " dimensions does not match Matrix " + B.name() + "in multiplication");
        }//if the matrices are not suitable for multiplying ,throw this message
    }}
    public Matrix subtract(Matrix B){
        B=B.multiply(-1.0);//multiply B with minus one to make it negative and store it in B agaib
        return this.addition(B);//adding negative B is same with subtracting real B(A-B==A+(-B))


    }
    public Matrix subtract(Double B){
    return this.addition(-B);
    }
    public void print(){
        System.out.println("###Matrix"+this.name()+"###");//print the name of the matrix
        Matrix C=this.transpose();//transpose it
        for(int i=0;i<C.size().get(1);i++){// do this for the number of the columns
            System.out.println(C.getColumn(i).getColumn());// get the column of the C and print them that way the Column of the C's would be shown as row ,so it will actually show THE MATRIX's rows as rows and columns as columns
        }
    }

}


