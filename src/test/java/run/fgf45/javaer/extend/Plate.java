package run.fgf45.javaer.extend;

public class Plate<T> {
    private T item;

    public Plate(T t) {
        this.item = t;
    }

    public T get() {
        return item;
    }

    public void set(T item) {
        this.item = item;
    }

}