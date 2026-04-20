package Hashcode_and_toString;
import java.util.*;
import java.util.List;


// Класс товаров для сада, наследник Product
class Garden extends Product {
    public Garden(String title, double price, Category category) {
        super(title, price, category);
    }

    @Override
    public double calculateTotalPrice(List<Product> products) {
        double total = 0;
        for (Product product : products) {
            if (product instanceof Garden) {
                total += product.getPrice();
            }
        }
        return total;
    }

    @Override
    public String showInfo(List<Product> products) {
        StringBuilder info = new StringBuilder("Товары для сада:\n");
        boolean hasGardenItems = false;

        for (Product product : products) {
            if (product instanceof Garden) {
                info.append("  - ").append(product.getTitle())
                        .append(" (").append(product.getPrice()).append(" руб.)\n");
                hasGardenItems = true;
            }
        }

        if (!hasGardenItems) {
            info.append("  Товары не найдены\n");
        }
        return info.toString();
    }
}
