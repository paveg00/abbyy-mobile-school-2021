package tasks;

public class Task01 {

    /**
     * Возвращает минимальное значение из массива.
     * Не использовать стандартную библиотеку!
     */
    public static int min(int[] ints) {
        int res_min = ints[0];
        for (int value : ints) {
            if (value < res_min) {
                res_min = value;
            }
        }
        return res_min;
    }

    public static float average(int[] ints) {
        int resSum = 0;
        for (int value : ints) {
            resSum += value;
        }
        return (float)resSum / ints.length;
    }

}
