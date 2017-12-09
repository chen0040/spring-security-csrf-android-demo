package chen0040.github.com.androidspringsecurity.functions;

/**
 * Created by chen0 on 3/7/2017.
 */

public interface TriConsumer<T1, T2, T3> {
    void apply(T1 item, T2 item2, T3 item3);
}
