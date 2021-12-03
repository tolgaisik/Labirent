import java.util.Random;
//1:5/3:3/4:6/2:2/5:6/8:5/6:3/7:2/9:?
//1:5/?:3/?:6/?:2/?:6/?:5/?:3/?:2/9:?


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
