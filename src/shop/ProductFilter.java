package shop;

@FunctionalInterface
public interface ProductFilter {
    boolean test(Product product);
}
