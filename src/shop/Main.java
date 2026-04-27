
package shop;

import java.util.*;

import java.util.List;
import java.util.stream.Collectors;

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

    // Метод для фильтрации товаров по заданному условию (добавлено)
    public static List<Product> filterProducts(List<Product> products, ProductFilter filter) {
        return products.stream()
                .filter(filter::test)
                .collect(Collectors.toList());
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
        productList.add(new Electronic("Telephone", 29999.99, electronicsCat));
        productList.add(new Electronic("Laptop", 79999.99, electronicsCat));
        productList.add(new Electronic("Headphones", 4999.99, electronicsCat));

        // Создаём 3 объекта товаров для сада
        productList.add(new Garden("Shovel", 1999.99, gardenCat));
        productList.add(new Garden("Watering can", 2499.99, gardenCat));
        productList.add(new Garden("Garden gloves", 899.99, gardenCat));

        // Товар‑дубликат
        productList.add(new Electronic("Telephone", 16969.69, electronicsCat));

        // Устанавливаем статусы для некоторых товаров (для примера)
        productList.get(0).setOrderStatus(OrderStatus.PAID);
        productList.get(1).setOrderStatus(OrderStatus.SHIPPED);
        productList.get(2).setOrderStatus(OrderStatus.DELIVERED);


        // Выводим все объекты на экран
        System.out.println("=== ВСЕ ТОВАРЫ В КАТАЛОГЕ ===");
        for (Product product : productList) {
            System.out.println(product);
        }

        // Показываем категории каталога
        catalog.showAllCategories();

        // Рассчитываем и выводим суммы по категориям
        Electronic electronicSample = new Electronic("Sample", 0.0, electronicsCat);
        Garden gardenSample = new Garden("Sample", 0.0, gardenCat);

        System.out.println("\n=== СУММЫ ПО КАТЕГОРИЯМ ===");
        System.out.printf("Электроника: %.2f руб.%n", electronicSample.calculateTotalPrice(productList));
        System.out.printf("Товары для сада: %.2f руб.%n", gardenSample.calculateTotalPrice(productList));

        // Выводим статистику по категориям
        System.out.println("\n=== СТАТИСТИКА ПО КАТЕГОРИЯМ ===");
        Map<Category, Integer> categories = Product.getCategoryStatistics();
        for (Map.Entry<Category, Integer> entry : categories.entrySet()) {
            System.out.println(entry.getKey().getName() + ": " + entry.getValue() + " товаров");
        }

        // ВЫВОД ТОВАРОВ ПО КАТЕГОРИЯМ (основная часть вопроса)
        System.out.println("\n=== ТОВАРЫ В КАТЕГОРИЯХ ===");

        // Создаём временные объекты для вызова showInfo()
        Electronic electronicInfo = new Electronic("Sample", 0.0, electronicsCat);
        Garden gardenInfo = new Garden("Sample", 0.0, gardenCat);

        // Выводим список электроники
        System.out.println(electronicInfo.showInfo(productList));

        // Выводим список товаров для сада
        System.out.println(gardenInfo.showInfo(productList));

        // Остальные разделы (группировка, сортировка и т. д.)
        System.out.println("\n=== ГРУППИРОВКА ТОВАРОВ ПО НАЗВАНИЮ ===");
        printProductGroups(productList);

        System.out.println("\n=== ОТСОРТИРОВАННЫЕ ТОВАРЫ ПО ЦЕНЕ ===");
        Collections.sort(productList);
        for (Product product : productList) {
            System.out.println(product);
        }


        /// Лямбда часть


        System.out.println("\n=== ФИЛЬТРАЦИЯ ТОВАРОВ ===");

        // 1 Только доставленные товары
        List<Product> deliveredProducts = filterProducts(productList, ProductFilters.deliveredOnly());
        System.out.println("Доставленные товары:");
        deliveredProducts.forEach(System.out::println);

        // 2 Товары дешевле 5000 рублей
        List<Product> cheapProducts = filterProducts(productList, ProductFilters.cheaperThan(5000.0));
        System.out.println("\nТовары дешевле 5 000 руб.:");
        cheapProducts.forEach(System.out::println);

        // 3 Только электроника
        List<Product> electronicsOnly = filterProducts(productList, ProductFilters.inCategory("Электроника"));
        System.out.println("\nТолько электроника:");
        electronicsOnly.forEach(System.out::println);

        // 4 Электроника дороже 20000 рублей
        List<Product> expensiveElectronics = filterProducts(productList, ProductFilters.expensiveElectronics(20000.0));
        System.out.println("\nЭлектроника дороже 20 000 руб.:");
        expensiveElectronics.forEach(System.out::println);

        // 5 Фильтрация по статусу (PAID)
        List<Product> paidProducts = filterProducts(productList, ProductFilters.withStatus(OrderStatus.PAID));
        System.out.println("\nОплаченные товары:");
        paidProducts.forEach(System.out::println);


    }
}




//enum OrderStatus {
//    NEW;
//    PAID;
//    SHIPPED;
//    DELIVERED;
//    CANCELLED;
//}

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
//


// этап 7.

// Реализовать enum, Functional Interface и Lambda функцию. enum должен отображать статус заказа.
// Functional Interface должен работать с клиентами или товарами.
// С помощью Lambda функцию создать фильтр или улучшить сортировку.
// Нужно чтобы лямбда функции были отдельно от мейна. Условно в новом классе для вывода сортировки.