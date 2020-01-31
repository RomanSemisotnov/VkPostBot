package myPackage.DAO;

@FunctionalInterface
public interface TripleConsumer<T, U, V> {

    public void accept(T t, U u, V v);

}
