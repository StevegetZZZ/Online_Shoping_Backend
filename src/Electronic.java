package Hashcode_and_toString;
import java.util.*;

import java.util.List;

// Класс электроники, наследник Product
class Electronic extends Product {

    public Electronic(String title, double price) {
        super(title, price, new Category("Electronics"));
    }


    @Override
    public double calculateTotalPrice(List<Product> products) {
        double total = 0;
        for (Product product : products) {
            if (product instanceof Electronic) {
                total += product.getPrice();
            }
        }
        return total;
    }


    @Override
    // Показ всех товаров
    public String showInfo(List<Product> products) {
        StringBuilder info = new StringBuilder("Электроника:\n");
        boolean hasElectronics = false;

        for (Product product : products) {
            if (product instanceof Electronic) {
                info.append("  - ").append(product.getTitle())
                        .append(" (").append(product.getPrice()).append(" руб.)\n");
                hasElectronics = true;
            }
        }

        if (!hasElectronics) {
            info.append("  Товары не найдены\n");
        }
        return info.toString();
    }


}