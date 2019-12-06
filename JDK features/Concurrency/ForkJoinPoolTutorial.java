import java.util.Arrays;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

class Fibonacci extends RecursiveTask<Integer> {

    private static final long serialVersionUID = 1L;
    Integer n;

    Fibonacci(Integer n) {
        this.n = n;
    }

    @Override
    public Integer compute() {
        if (n <= 1)
            return n;
        Fibonacci firstPart = new Fibonacci(n - 1);
        firstPart.fork();
        Fibonacci secondPart = new Fibonacci(n - 2);
        secondPart.fork();

        return firstPart.join() + secondPart.join();
    }
}

class MergeSort extends RecursiveAction {

    private static final long serialVersionUID = 1L;
    int array[];
    int high, low;
    final int THRESHOLD = 30;

    MergeSort(int array[]) {
        this.array = array;
        this.low = 0;
        this.high = array.length - 1;
    }

    MergeSort(int array[], int high, int low) {
        this.array = array;
        this.high = high;
        this.low = low;
    }

    @Override
    public void compute() {
        if (high - low <= THRESHOLD) {
            Arrays.sort(array);
            return;
        }
        int mid = (high + low) / 2;
        ForkJoinTask.invokeAll(new MergeSort(array, low, mid), new MergeSort(array, mid + 1, high));
        merge(low, mid, high);
    }

    private void merge(int lo, int m, int hi) {
        int i = lo, j = hi, k = lo;
        int temp[] = new int[array.length];
        while (i <= m && j <= hi) {
            if (array[i] < array[j])
                temp[k++] = array[i++];
            else
                temp[k++] = array[j++];
        }

        while (i <= m)
            temp[k++] = array[i++];
        while (j <= hi)
            temp[k++] = array[j++];
        for (k = lo; k <= hi; k++)
            array[k] = temp[k];
    }
}

public class ForkJoinPoolTutorial {
    public static void main(String[] args) {
        System.out.println(new Fibonacci(5).compute());
        int arr[] = { 5, 3, 6, 1, 4, 2 };
        MergeSort sort = new MergeSort(arr);
        sort.compute();
        for (int e : arr)
            System.out.print(e + " ");
    }
}