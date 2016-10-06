public class Vector { 

    private final int N;         // length of the vector
    private double[] data;       // array of vector's components

    // create the zero vector of length N
    public Vector(int N) {
        this.N = N;
        this.data = new double[N];
    }

    // create a vector from an array
    public Vector(double[] data) {
        N = data.length;
        
        // defensive copy so that client can't alter our copy of data[]
        this.data = new double[N];
        for (int i = 0; i < N; i++)
            this.data[i] = data[i];
    }

    // create a vector from either an array or a vararg list
    // this constructor uses Java's vararg syntax to support
    // a constructor that takes a variable number of arguments, such as
    // Vector x = new Vector(1.0, 2.0, 3.0, 4.0);
    // Vector y = new Vector(5.0, 2.0, 4.0, 1.0);
/*
    public Vector(double... data) {
        N = data.length;

        // defensive copy so that client can't alter our copy of data[]
        this.data = new double[N];
        for (int i = 0; i < N; i++)
            this.data[i] = data[i];
    }
*/
    // return the length of the vector
    public int length() {
        return N;
    }
    
    public double getX() {	
    	return data[0];
    }
    
    public double getY() {	
    	return data[1];
    }

    // return the inner product of this Vector a and b
    public double dot(Vector that) {
        if (this.length() != that.length()) throw new RuntimeException("Dimensions don't agree");
        double sum = 0.0;
        for (int i = 0; i < N; i++)
            sum = sum + (this.data[i] * that.data[i]);			//mul x and y dimensions together
        return sum;
    }

    // return the Euclidean norm of this Vector
    public double magnitude() {
        return Math.sqrt(this.dot(this));
    }

    // return the Euclidean distance between this and that
    public double distanceTo(Vector that) {
        if (this.length() != that.length()) throw new RuntimeException("Dimensions don't agree");
        return this.minus(that).magnitude();
    }

    // return this + that
    public Vector plus(Vector that) {
        if (this.length() != that.length()) throw new RuntimeException("Dimensions don't agree");
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = this.data[i] + that.data[i];
        return c;
    }

    // return this - that
    public Vector minus(Vector that) {
        if (this.length() != that.length()) throw new RuntimeException("Dimensions don't agree");
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = this.data[i] - that.data[i];
        return c;
    }

    // return the corresponding coordinate
    public double cartesian(int i) {
        return data[i];
    }

    // create and return a new object whose value is (this * factor)
    public Vector times(double factor) {
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = factor * data[i];
        return c;
    }


    // return the corresponding unit vector
    public Vector direction() {
        if (this.magnitude() == 0.0) throw new RuntimeException("Zero-vector has no direction");
        return this.times(1.0 / this.magnitude());
    }
    

    // return a string representation of the vector
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("(");
        for (int i = 0; i < N; i++) {
            s.append(data[i]);
            if (i < N-1) s.append(", ");
        }
        s.append(")");
        return s.toString();
    }


    // test client
    public static void main(String[] args) {
        double[] adata = { 0.1, 0.1 };				//x and y coordinates
        double[] bdata = { 0.1, 0.1 };				//x and y coordinates

        Vector a = new Vector(adata);
        Vector b = new Vector(bdata);

        System.out.println("x        =  " + a);
        System.out.println("y        =  " + b);
        for (int i = 0; i<10; i++){
        	
        	a = a.plus(b);
        	System.out.println("x + y    =  " + a);
        }
        
        System.out.println("10x      =  " + a.times(10.0));
        System.out.println("|x|      =  " + a.magnitude());
        System.out.println("<x, y>   =  " + a.dot(b));
        System.out.println("|x - y|  =  " + a.minus(b).magnitude());
    }
}
