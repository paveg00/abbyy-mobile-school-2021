package tasks;

import java.util.Random;

/**
 * Напишите класс, конструктор которого принимает два массива:
 * массив значений и массив весов значений.
 * Класс должен содержать метод, который будет возвращать элемент
 * из первого массива случайным образом, с учётом его веса.
 * Пример:
 * Дан массив [1, 2, 3], и массив весов [1, 2, 10].
 * В среднем, значение «1» должно возвращаться в 2 раза реже,
 * чем значение «2» и в десять раз реже, чем значение «3».
 */
class RandomFromArray {
    private final int[] values;
    private final int[] thresholds;
    int resSum;
    RandomFromArray(int[] values, int[] weights) {
        this.values = values;
        thresholds = new int[weights.length];
        thresholds[0] = weights[0];
        for (int i = 1; i < weights.length; ++i) {
            thresholds[i] = thresholds[i-1] + weights[i];
        }
        resSum = thresholds[weights.length - 1];
    }

    int Next() {
        int idx = 0;
        Random r = new Random();
        int randBucket = r.nextInt(resSum);

        // maybe binsearch, but really needed?
        while (thresholds[idx] <= randBucket) {
            ++idx;
        }
        return values[idx];
    }

}

public class Task05 {
    public static void main(String[] args) {
        int[] values = {0, 1, 20};
        int[] weights = {100, 1, 1};
        for (int i =0; i < 100; ++i) {
            System.out.println(new RandomFromArray(values, weights).Next());
        }
    }
}
