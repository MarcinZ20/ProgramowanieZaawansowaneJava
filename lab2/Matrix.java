import java.util.Arrays;
import java.util.Random;
import java.lang.Math;

public class Matrix {
    double[] data;
    int rows;
    int cols;

    //...
    Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new double[rows * cols];
    }

    //    =========================== 2.1 ===========================

    Matrix(double[][] d) {
        //Get longest row
        int max = d[0].length;

        for (int r = 1; r < d.length; r++) {
            if (d[r].length > max) {
                max = d[0].length;
            }
        }

        rows = d.length;
        cols = max;
        data = new double[rows * cols];
        Arrays.fill(data, 0);

        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[i].length; j++) {
                data[(cols * i) + j] = d[i][j];
            }
        }
    }

    public Matrix(Matrix another) {
        rows = another.rows;
        cols = another.cols;
        data = new double[rows * cols];

        if (rows * cols >= 0) {
            System.arraycopy(another.data, 0, data, 0, rows * cols);
        }
    }

    //    =========================== 2.2 ===========================

    double [][] asArray() {
        double[][] tab = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                tab[i][j] = data[i * cols + j];
            }
        }

        return tab;
    }

    //    =========================== 2.3 ===========================

    double get(int r, int c) {
        if (r >= rows || c >= cols) {
            throw new RuntimeException("Invalid index!");
        }

        return data[r * rows + c];
    }

    void set(int r, int c, double value) {
        if (r >= rows || c >= cols) {
            throw new RuntimeException("Invalid index!");
        }

        data[r * rows + c] = value;
    }

    //    =========================== 2.4 ===========================

    public String toString() {

        StringBuilder buf = new StringBuilder();

        buf.append("[");

        for(int i = 0; i < rows; i++) {
            buf.append("[ ");
            for (int j = 0; j < cols; j++) {
                buf.append(data[cols * i + j]);
                buf.append(" ");
            }
            buf.append("]");
            if (i == rows - 1) {
                break;
            } else {
                buf.append("\n ");
            }
        }

        buf.append("]");

        return buf.toString();
    }

    //    =========================== 2.5 ===========================

    void reshape(int newRows,int newCols) {

        if (rows * cols != newRows*newCols) {
            throw new RuntimeException(String.format("%d x %d matrix can't be reshaped to %d x %d",rows,cols,newRows,newCols));
        }

        rows = newRows;
        cols = newCols;
    }

    //    =========================== 2.6 ===========================

    int [] shape() {
        int[] tabShape = new int[2];
        tabShape[0] = rows;
        tabShape[1] = cols;

        return tabShape;
    }

    //    =========================== MATRIX METHODS ===========================

    Matrix add(Matrix m) {

        //Create new matrix
        Matrix newMatrix = new Matrix(Math.max(rows, m.rows), Math.max(cols, m.cols));
        Arrays.fill(newMatrix.data, 0);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newMatrix.data[i * cols + j] += data[i * rows + j];
            }
        }

        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                newMatrix.data[i * cols + j] += m.data[i * rows + j];
            }
        }

        return newMatrix;
    }

    Matrix sub(Matrix m) {

        //Create new matrix
        Matrix newMatrix = new Matrix(Math.max(rows, m.rows), Math.max(cols, m.cols));
        Arrays.fill(newMatrix.data, 0);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newMatrix.data[i * cols + j] -= data[i * rows + j];
            }
        }

        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                newMatrix.data[i * cols + j] -= m.data[i * rows + j];
            }
        }

        return newMatrix;
    }

    Matrix mul(Matrix m) {

        //Create new matrix
        Matrix newMatrix = new Matrix(Math.max(rows, m.rows), Math.max(cols, m.cols));
        Arrays.fill(newMatrix.data, 0);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newMatrix.data[i * cols + j] += data[i * rows + j];
            }
        }

        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                newMatrix.data[i * cols + j] *= m.data[i * rows + j];
            }
        }

        return newMatrix;
    }

    Matrix div(Matrix m) {

        //Create new matrix
        Matrix newMatrix = new Matrix(Math.max(rows, m.rows), Math.max(cols, m.cols));
        Arrays.fill(newMatrix.data, 0);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newMatrix.data[i * cols + j] += data[i * rows + j];
            }
        }

        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                newMatrix.data[i * cols + j] /= m.data[i * rows + j];
            }
        }

        return newMatrix;
    }

//    =========================== SCALAR METHODS ===========================

    Matrix add(double value) {

        Matrix newMatrix = new Matrix(this);

        for (int i = 0; i < rows * cols; i++) {
            newMatrix.data[i] += value;
        }

        return newMatrix;
    }

    Matrix sub(double value) {

        Matrix newMatrix = new Matrix(this);

        for (int i = 0; i < rows * cols; i++) {
            newMatrix.data[i] -= value;
        }

        return newMatrix;
    }

    Matrix mul(double value) {

        Matrix newMatrix = new Matrix(this);

        for (int i = 0; i < rows * cols; i++) {
            newMatrix.data[i] *= value;
        }

        return newMatrix;
    }

    Matrix div(double value) {

        Matrix newMatrix = new Matrix(this);

        for (int i = 0; i < rows * cols; i++) {
            newMatrix.data[i] /= value;
        }

        return newMatrix;
    }

    //    =========================== 2.9 ===========================

    Matrix dot(Matrix m) {

        if (cols != m.rows) {
            throw new RuntimeException("Matrices can't be multiplied together!");
        }

        Matrix newMatrix = new Matrix(rows, m.cols);
        Arrays.fill(newMatrix.data, 0);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < m.cols; c++) {
                for (int i = 0; i < m.rows; i++) {
                    newMatrix.data[newMatrix.cols * r + c] += data[cols * r + i] * m.data[m.cols * i + c];
                }
            }
        }
        return newMatrix;
    }

    //    =========================== 2.10 ===========================
    double frobenius() {
        Matrix newMatrix = this.dot(this);
        double sum = 0;

        for (int i = 0; i < rows * cols; i++) {
            sum += newMatrix.data[i];
        }

        return Math.sqrt(sum);
    }

    //    =========================== 2.11 ===========================

    public static Matrix random(int rows, int cols) {
        Matrix m = new Matrix(rows,cols);
        Random r = new Random();

        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.rows; j++) {
                m.set(i,j,r.nextDouble());
            }
        }
        return m;
    }

    public static Matrix eye(int n) {
        Matrix m = new Matrix(n, n);

        for (int i = 0; i < m.rows; i++) {
            for (int j = 0 ; j < m.cols; j++) {
                if (i == j) {
                    m.data[i * m.cols + j] = 1;
                } else {
                    m.data[i * m.cols + j] = 0;
                }
            }
        }
        return m;
    }


    public static void main(String[] args) {
        System.out.println("Programowanie zaawansowane - Laboratorium 2\nWykonał: Marcin Zub");
        Matrix m = new Matrix(new double[][]{{1,2,3},{4,5,6},{7,8,9}});
        Matrix n = new Matrix(new double[][]{{10,11,12}, {13, 14}, {15}});
        Matrix k = new Matrix(new double[][]{{5,4,3,2,1}, {6,5,4,3,2}, {7,6,5,4,3}});
        System.out.println(m.toString());
        System.out.println("\n");
        double [][] tab = m.asArray();

        System.out.println("\n");
//        System.out.println(m.get(4,5)); --> error
        System.out.println(m.get(2,0));
        m.set(2,0,5);
        System.out.println(m.get(2,0));
        System.out.println("\n");
        System.out.println(String.format("Rows: %d\nCols: %d", m.rows, m.cols));
        System.out.println(Arrays.toString(m.shape()));
//        m.reshape(2,2); --> error
        m.reshape(9,1);
        System.out.println(String.format("Rows: %d\nCols: %d", m.rows, m.cols));
        System.out.println(Arrays.toString(m.shape()));
        m.reshape(3,3);
        Matrix b = m.add(2);
        System.out.println(b.toString());
        Matrix c = m.sub(2);
        System.out.println(c.toString());
        Matrix d = m.mul(3);
        System.out.println(d.toString());
        Matrix e = m.div(6);
        System.out.println(e.toString());
        Matrix f = m.add(n);
        System.out.println(f.toString());
        Matrix g = m.sub(n);
        System.out.println(g.toString());
        Matrix h = m.mul(n);
        System.out.println(h.toString());
        Matrix i = m.div(n);
        System.out.println(i.toString());
        Matrix j = m.dot(k);
        System.out.println(j.toString());
        System.out.println(m.frobenius());

    }
}