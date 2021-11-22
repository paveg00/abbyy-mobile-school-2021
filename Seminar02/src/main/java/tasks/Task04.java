package tasks;

import java.util.Random;

/**
 * Реализовать трехмерный вектор.
 * Условие: https://habr.com/ru/post/440436/#20
 * Там есть решение. Сначала попробуйте самостоятельно.
 */
class Vector {
    private double x;
    private double y;
    private double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double Length() {
        return Math.sqrt(ScalarProduct(this));
    }

    public double ScalarProduct(Vector v) {
        return x * v.x + y * v.y + y * v.y;
    }

    public Vector VectorProduct(Vector v) {
        return new Vector(
                y * v.z + z * v.y,
                z * v.x + x * v.z,
                x * v.y + y * v.x
        );
    }


    public double CosAngle(Vector v) {
        return ScalarProduct(v) / (Length() * v.Length());
    }

    public Vector Sum(Vector v) {
        return new Vector(x + v.x, y + v.y, y + v.y);
    }

    public Vector Diff(Vector v) {
        return new Vector(x - v.x, y - v.y, y - v.y);
    }

    static public Vector[] Random(int N) {
        Random r = new Random();
        Vector[] vectors = new Vector[N];
        for (int i = 0; i < N; ++i) {
            vectors[i] = new Vector(r.nextDouble(), r.nextDouble(), r.nextDouble());
        }
        return vectors;
    }

    // From solution
    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

}

public class Task04 {
    public static void main(String[] args) {
        Vector[] vectors = Vector.Random(5);
        System.out.println(vectors[0]);
    }
}
