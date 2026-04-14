
package Hashcode_and_toString;

import java.util.*;
import static java.lang.System.out;


public class Main {

    // Метод для группировки товаров по названию
    public static Map<String, List<Product>> groupProductsByName(List<Product> products) {
        Map<String, List<Product>> map = new HashMap<>();
        for (Product p : products) {
            String title = p.getTitle();
            // computeIfAbsent создаёт новый список, если ключа ещё нет
            map.computeIfAbsent(title, k -> new ArrayList<>()).add(p);
        }
        return map;
    }

    // Метод для вывода всех групп (название один раз, затем список цен)
    public static void printProductGroups(List<Product> products) {
        Map<String, List<Product>> groups = groupProductsByName(products);
        for (Map.Entry<String, List<Product>> entry : groups.entrySet()) {
            String title = entry.getKey();
            List<Product> sameName = entry.getValue();
            System.out.print("Товар: " + title + " -> цены: ");
            for (int i = 0; i < sameName.size(); i++) {
                System.out.print(sameName.get(i).getPrice() + " руб");
                if (i < sameName.size() - 1) System.out.print(", ");
            }
            System.out.println();
        }
    }

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

        // Товар дублекат
        productList.add(new Electronic("Telephone", 16969.69));


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

        // Выводим товары по категориям (с помощью showInfo)
        System.out.println("\n=== ТОВАРЫ В КАТЕГОРИЯХ ===");
        System.out.println(electronicSample.showInfo(productList));
        System.out.println(gardenSample.showInfo(productList));

        // Вывод сгрупированные товаров по названию.
        System.out.println("\n=== ГРУППИРОВКА ТОВАРОВ ПО НАЗВАНИЮ ===");
        printProductGroups(productList);
    }
}


// интерфейс пеабл. Метод пэй. Гетпей в класе Пейабл. за оплату отвечает
// интерфейс финансовл. Провереяет статус оплаты.
// иклс, хэшкод, инсероф и тостринг.
// создать товары и эти товары сравнивать.

// Добавить расширение класса. Полноценно реализовать иклс.
// добавть иксл, тустринг и хешкод в нужных местах и инсёроф.
// 2 интерфейса финансебл и пейабл.


// Пейабл отвечает за покупку. Метод возврашающий дабл гет файнал прайс. ....


// Метод войд пей, принимает параметр маунт дабл.
// Метод булен изпейд (isPaid)



// Финансбл имеет 3 метода. 1 возвращает дабл, называется чекбаланс.
// 2 метод возвращает булен (хэсъэнавъмани) проверяет состояние средств, хватает денег или нет.
// 3 метод стринг гетъфинанслъстатус (можно не стринг)



// Пейабл связан с покупками, а финансбл с товаром.



// Создать класс пёрсон (возможно асбтрактный) и из него будет наследоваться класс "клиент" (у него свой id, mane, баланс и волид.)
//
