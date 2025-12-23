package pl.zsgornik.util;

public class Tuple<t1, t2> {
    private final t1 param1;
    private final t2 param2;

    public Tuple(t1 param1, t2 param2) {
        this.param1 = param1;
        this.param2 = param2;
    }

    public t1 getFirst() {
        return param1;
    }

    public t2 getSecond() {
        return param2;
    }
}
