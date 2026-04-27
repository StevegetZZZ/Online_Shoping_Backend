package shop;

import java.util.List;
import java.util.function.Predicate;

public class ProductFilters {


     // Фильтр только для доставленных товары (статус DELIVERED)
    public static ProductFilter deliveredOnly() {
        return product -> product.getOrderStatus() == OrderStatus.DELIVERED;
    }


     // Фильтр для товаров дешевле указанной
     // @param maxPrice максимальная цена
    public static ProductFilter cheaperThan(double maxPrice) {
        return product -> product.getPrice() < maxPrice;
    }


     // Фильтр товаров определённой категории
     // @param categoryName название категории
    public static ProductFilter inCategory(String categoryName) {
        return product -> product.getCategory().getName().equals(categoryName);
    }


     // Комбинированный фильтр (Электроника дороже указанной суммы)
     // @param minPrice минимальная цена
    public static ProductFilter expensiveElectronics(double minPrice) {
        return product -> product instanceof Electronic && product.getPrice() > minPrice;
    }


     // Фильтр товаров с определённым статусом заказа
     // @param status статус заказа
    public static ProductFilter withStatus(OrderStatus status) {
        return product -> product.getOrderStatus() == status;
    }
}
