import java.util.Random;

public class Test {
    String[] test = new String[10000];

    Test() {

    }

    void recurseWithoutParam(int depth) {
        try {
            test[depth] = "DEPTH";
            recurseWithoutParam(depth + 1);
        } catch (Exception e) {
            return;
        }
    }

    void recurseWithParam(String[] _test, int depth) {
        try {
            _test[depth] = "DEPTH";
            recurseWithoutParam(depth + 1);
        } catch (Exception e) {
            return;
        }
    }

    int size = 16;

    public static void main(String[] args) {
        Test t = new Test();
        int val = new Random().nextInt(t.size - 2) + 2;
        System.out.println(val);
    }
}
