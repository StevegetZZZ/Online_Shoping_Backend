import java.util.*;

import static java.lang.System.out;


public class Main {
    public static void main(String[] args) {
        // Создаём каталог
        Catalog catalog = new Catalog();

        // Создаём основные категории
        Category electronicsCat = new Category("Электроника");
        Category gardenCat = new Category("Товары для сада");

        // Добавляем подкатегории
        electronicsCat.addSubcategory(new Category("Телефоны"));
        electronicsCat.addSubcategory(new Category("Ноутбуки"));
        gardenCat.addSubcategory(new Category("Инструменты"));
        gardenCat.addSubcategory(new Category("Полив"));

        // Добавляем категории в каталог
        catalog.addMainCategory(electronicsCat);
        catalog.addMainCategory(gardenCat);

        // Создаём список продуктов
        List<Product> productList = new ArrayList<>();

        // Создаём 3 объекта электроники
        productList.add(new Electronic("Telephone", 29999.99));
        productList.add(new Electronic("Laptop", 79999.99));
        productList.add(new Electronic("Headphones", 4999.99));

        // Создаём 3 объекта товаров для сада
        productList.add(new Garden("Shovel", 1999.99));
        productList.add(new Garden("Watering can", 2499.99));
        productList.add(new Garden("Garden gloves", 899.99));

        // Выводим все объекты на экран
        System.out.println("=== ВСЕ ТОВАРЫ В КАТАЛОГЕ ===");
        for (Product product : productList) {
            System.out.println(product);
        }

        // Показываем категории каталога
        catalog.showAllCategories();

        // Рассчитываем и выводим суммы по категориям
        Electronic electronicSample = new Electronic("Sample", 0);
        Garden gardenSample = new Garden("Sample", 0);

        System.out.println("\n=== СУММЫ ПО КАТЕГОРИЯМ ===");
        System.out.printf("Электроника: %.2f руб.%n", electronicSample.calculateTotalPrice(productList));
        System.out.printf("Товары для сада: %.2f руб.%n", gardenSample.calculateTotalPrice(productList));

        // Выводим статистику по категориям
        System.out.println("\n=== СТАТИСТИКА ПО КАТЕГОРИЯМ ===");
        Map<Category, Integer> categories = Product.getCategoryStatistics();
        for (Map.Entry<Category, Integer> entry : categories.entrySet()) {
            System.out.println(entry.getKey().getName() + ": " + entry.getValue() + " товаров");
        }

        // ВЫВОД ТОВАРОВ ПО КАТЕГОРИЯМ
        System.out.println("\n=== ТОВАРЫ В КАТЕГОРИЯХ ===");
        System.out.println(electronicSample.showInfo(productList));
        System.out.println(gardenSample.showInfo(productList));
    }
}


