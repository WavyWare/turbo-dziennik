package pl.zsgornik.util;

public class Tuple<t1, t2> {
    private final t1 param1;
    private final t2 param2;

    public Tuple(t1 param1, t2 param2) {
        this.param1 = param1;
        this.param2 = param2;
    }

    public t1 getParam1() {
        return param1;
    }

    public t2 getParam2() {
        return param2;
    }
}
