
package shop;


import java.util.*;
import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;

public class Main {


    public static Map<String, List<Product>> groupProductsByName(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getTitle));
    }

    public static void printProductGroups(List<Product> products) {
        groupProductsByName(products).entrySet().stream()
                .forEach(entry -> {
                    String prices = entry.getValue().stream()
                            .map(p -> p.getPrice() + " руб")
                            .collect(Collectors.joining(", "));
                    System.out.println("Товар: " + entry.getKey() + " -> цены: " + prices);
                });
    }

    public static List<Product> filterProducts(List<Product> products, ProductFilter filter) {
        return products.stream()
                .filter(filter::test)
                .collect(Collectors.toList());
    }

//    // Метод для группировки товаров по названию
//    public static Map<String, List<Product>> groupProductsByName(List<Product> products) {
//        Map<String, List<Product>> map = new HashMap<>();
//        for (Product p : products) {
//            String title = p.getTitle();
//            // computeIfAbsent создаёт новый список, если ключа ещё нет
//            map.computeIfAbsent(title, k -> new ArrayList<>()).add(p);
//        }
//        return map;
//    }
//
//    // Метод для вывода всех групп (название один раз, затем список цен)
//    public static void printProductGroups(List<Product> products) {
//        Map<String, List<Product>> groups = groupProductsByName(products);
//        for (Map.Entry<String, List<Product>> entry : groups.entrySet()) {
//            String title = entry.getKey();
//            List<Product> sameName = entry.getValue();
//            System.out.print("Товар: " + title + " -> цены: ");
//            for (int i = 0; i < sameName.size(); i++) {
//                System.out.print(sameName.get(i).getPrice() + " руб");
//                if (i < sameName.size() - 1) System.out.print(", ");
//            }
//            System.out.println();
//        }
//    }
//
//    // Метод для фильтрации товаров по заданному условию
//    public static List<Product> filterProducts(List<Product> products, ProductFilter filter) {
//        return products.stream()
//                .filter(filter::test)
//                .collect(Collectors.toList());
//    }

    public static void main(String[] args) {
        // Создаём каталог
        Catalog catalog = new Catalog();

        Category electronicsCat = new Category("Электроника");
        Category gardenCat = new Category("Товары для сада");

        electronicsCat.addSubcategory(new Category("Телефоны"));
        electronicsCat.addSubcategory(new Category("Ноутбуки"));
        gardenCat.addSubcategory(new Category("Инструменты"));
        gardenCat.addSubcategory(new Category("Полив"));

        catalog.addMainCategory(electronicsCat);
        catalog.addMainCategory(gardenCat);

        List<Product> productList = new ArrayList<>();
        productList.add(new Electronic("Telephone", 29999.99, electronicsCat));
        productList.add(new Electronic("Laptop", 79999.99, electronicsCat));
        productList.add(new Electronic("Headphones", 4999.99, electronicsCat));
        productList.add(new Garden("Shovel", 1999.99, gardenCat));
        productList.add(new Garden("Watering can", 2499.99, gardenCat));
        productList.add(new Garden("Garden gloves", 899.99, gardenCat));
        productList.add(new Electronic("Telephone", 16969.69, electronicsCat)); // дубликат для группировки

        // Профиль
        cart cart = new cart();
        Person user = new Person("Дмитрий", 100_000, 50_000);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Главный цикл
        while (running) {
            System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
            System.out.println("1. Каталог товаров");
            System.out.println("2. Корзина");
            System.out.println("3. Оплатить заказ");
            System.out.println("4. Профиль");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    catalogMenu(productList, cart, scanner);
                    break;
                case "2":
                    showCart(cart, scanner);
                    break;
                case "3":
                    checkout(cart, user, scanner);
                    break;
                case "4":
                    showProfile(user);
                    break;
                case "0":
                    running = false;
                    System.out.println("До свидания!");
                    break;
                default:
                    System.out.println("Неизвестная команда.");
            }
        }
        scanner.close();
    }

    // Меню каталога
    private static void catalogMenu(List<Product> productList, cart cart, Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== КАТАЛОГ ===");
            System.out.println("1. Все товары");
            System.out.println("2. По категориям");
            System.out.println("3. Фильтры");
            System.out.println("0. Назад в главное меню");
            System.out.print("Выберите: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showAllProducts(productList, cart, scanner);
                    break;
                case "2":
                    showByCategory(productList, cart, scanner);
                    break;
                case "3":
                    applyFilters(productList, cart, scanner);
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Неизвестная команда.");
            }
        }
    }

    private static void showAllProducts(List<Product> products, cart cart, Scanner scanner) {
        System.out.println("\n=== ВСЕ ТОВАРЫ ===");
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i));
        }
        addToCartPrompt(products, cart, scanner);
    }


    private static void showByCategory(List<Product> products, cart cart, Scanner scanner) {
        System.out.println("\nКатегории: 1 - Электроника, 2 - Товары для сада");
        System.out.print("Введите номер категории (0 - отмена): ");
        String input = scanner.nextLine();

        // Фильтрация через Stream
        List<Product> filtered = switch (input) {
            case "1" -> products.stream()
                    .filter(p -> p instanceof Electronic)
                    .collect(Collectors.toList());
            case "2" -> products.stream()
                    .filter(p -> p instanceof Garden)
                    .collect(Collectors.toList());
            default -> Collections.emptyList();
        };

        if (filtered.isEmpty()) {
            System.out.println("Товаров в этой категории нет.");
            return;
        }

        for (int i = 0; i < filtered.size(); i++) {
            System.out.println((i + 1) + ". " + filtered.get(i));
        }
        addToCartPrompt(filtered, cart, scanner);

    }

    private static void applyFilters(List<Product> allProducts, cart cart, Scanner scanner) {
        System.out.println("\nВыберите фильтр:");
        System.out.println("1. Только доставленные (В разработке)");
        System.out.println("2. Дешевле 5000 руб.");
        System.out.println("3. Дорогая электроника (>50000)");
        System.out.println("0. Отмена");
        String choice = scanner.nextLine();

        // Используем ProductFilter через filterProducts (стрим внутри)
        List<Product> filtered = switch (choice) {
            case "1" -> filterProducts(allProducts, ProductFilters.deliveredOnly());
            case "2" -> filterProducts(allProducts, ProductFilters.cheaperThan(5000));
            case "3" -> filterProducts(allProducts, ProductFilters.expensiveElectronics(50000));
            default -> Collections.emptyList();
        };

        if (filtered.isEmpty()) {
            System.out.println("Ничего не найдено.");
            return;
        }

        for (int i = 0; i < filtered.size(); i++) {
            Product p = filtered.get(i);
            System.out.println((i + 1) + ". " + p.getTitle() + " — " +
                    p.getPrice() + " руб. [" + p.getOrderStatus() + "]");
        }
    }


    private static void addToCartPrompt(List<Product> products, cart cart, Scanner scanner) {
        System.out.print("Введите номер товара для добавления в корзину (0 - отмена): ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index == -1) return;
            if (index >= 0 && index < products.size()) {
                cart.addProduct(products.get(index));
                System.out.println("Товар добавлен в корзину.");
            } else {
                System.out.println("Неверный номер.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Введите число.");
        }
    }

    // Корзина
    private static void showCart(cart cart, Scanner scanner) {
        if (cart.isEmpty()) {
            System.out.println("\nКорзина пуста.");
            return;
        }
        System.out.println("\n=== КОРЗИНА ===");
        List<Product> items = cart.getItems();







        double total = items.stream().mapToDouble(Product::getPrice).sum();
        System.out.printf("Итого: %.2f руб.\n", total);

        System.out.println("\nДействия: 1 - Удалить товар, 2 - Очистить корзину, 0 - Назад");
        String action = scanner.nextLine();
        switch (action) {
            case "1":
                System.out.print("Введите номер товара для удаления: ");
                try {
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (idx >= 0 && idx < items.size()) {
                        cart.removeProduct(items.get(idx));
                        System.out.println("Товар удалён.");
                    } else {
                        System.out.println("Неверный номер.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка ввода.");
                }
                break;
            case "2":
                cart.clear();
                System.out.println("Корзина очищена.");
                break;
        }
    }


    // Оплата
    private static void checkout(cart cart, Person user, Scanner scanner) {
        if (cart.isEmpty()) {
            System.out.println("Корзина пуста.");
            return;
        }

        double total = cart.getTotalPrice();
        System.out.printf("\nСумма к оплате: %.2f руб.\n", total);
        System.out.printf("Ваш баланс: %.2f руб., доступный кредит: %.2f руб.\n",
                user.getBalance(), user.getAvailableCredit());

        String options = PaymentService.getPaymentOptions(user, total);
        System.out.println(options);
        System.out.print("Выберите вариант оплаты: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());

            PaymentService.PaymentMethod method;
            switch (choice) {
                case 1:
                    method = PaymentService.PaymentMethod.CASH;
                    break;
                case 2:
                    method = PaymentService.PaymentMethod.CREDIT;
                    break;
                case 3:
                    method = PaymentService.PaymentMethod.MIXED;
                    break;
                case 0:
                    System.out.println("Оплата отменена.");
                    return;
                default:
                    System.out.println("Неверный выбор. Доступны варианты: 1, 2, 3 или 0 для отмены.");
                    return;
            }

            boolean paymentSuccess = PaymentService.processPayment(user, total, method);

            if (paymentSuccess) {
                cart.getItems().forEach(p -> p.setOrderStatus(OrderStatus.PAID));
                cart.clear();
                System.out.println("Оплата прошла успешно!");
                showPaymentSummary(user, total, method);
            } else {
                System.out.println("Недостаточно средств или кредитного лимита для выбранного способа оплаты.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода. Пожалуйста, введите число.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }




    private static void showPaymentSummary(Person user, double amount,
                                           PaymentService.PaymentMethod method) {
        System.out.println("\n=== ЧЕК ===");
        System.out.printf("Сумма: %.2f руб.\n", amount);
        System.out.printf("Метод оплаты: %s\n", method);
        System.out.printf("Остаток баланса: %.2f руб.\n", user.getBalance());
        System.out.printf("Использовано кредита: %.2f руб.\n", user.getCredit_used());
        System.out.printf("Долг с процентами: %.2f руб.\n", user.getCredit_must());
    }


    // Профиль
    private static void showProfile(Person user) {
        System.out.println("\n=== ПРОФИЛЬ ===");
        System.out.println("Имя: " + user.getName());
        System.out.println("Баланс: " + String.format("%.2f", user.getBalance()) + " руб.");
        System.out.println("Кредитный лимит: " + String.format("%.2f", user.getCredit_limit()) + " руб.");
        System.out.println("Использовано кредита: " + String.format("%.2f", user.getCredit_used()) + " руб.");
        System.out.println("Долг с процентами: " + String.format("%.2f", user.getCredit_must()) + " руб.");
        System.out.println("Доступно кредита: " + String.format("%.2f", user.getAvailableCredit()) + " руб.");
    }


}















//}
//}




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
//
// Реализовать enum, Functional Interface и Lambda функцию. enum должен отображать статус заказа.
// Functional Interface должен работать с клиентами или товарами.
// С помощью Lambda функцию создать фильтр или улучшить сортировку.
// Нужно чтобы лямбда функции были отдельно от мейна. Условно в новом классе для вывода сортировки.



// этап 8.
//
// Применение стим мапе. Нужно применить к спискам товаров и заказов.
// Нужно стим операции использовать для обработки коллекций. ( прим. Вывод товаров по категориям, поиск, сортировка )
// Циклы превращать в стим мапе. Расширить пользовательское меню.
//
//


// этап SOLID.
//
// Применить в солиде букву с, о, д ( по принципам ). Сканировать все свои файлы.
// s, o, d
// Добавить отдельную логику для работы с товарами.
// Может в класс, объект или ютил util класс. Может статик ? Для поиска, сортировки и подсчёта
//
// С помощью стим мап должно находить товары по категориям, дороже дешевле указанной цены, только доступные твоары.
// Отсортировать товары по цене, по названию. Посчитать товары в категориях.
// Нужно чтобы можно было найти самый дорогой и самый дешёвый товар макс ифом.
// Сохранить анследование, иерархию, полеморфизм, инкапсуляцию, энамы, лямбда выражения.
//


// этап 9.
//
// У клиента есть кошелёк, дебетовый и кредитный. Реализовать их. Для кредита есть лимит.
// ( вроде не надо ) Реализовать механизм перевода между счетами не вручную, ответственным методом.
// Реализация ограничения. Дебетовый не может в минус и кредит не может быть превышен. Кредит не может быть накопительным счётом.
// При покупке хватает ли. Если нету то предложить кредитом покрыть часть. Кредит должен иметь проценты.
// Должна быть история операций. За какие покупки делалась оплата.
// java.time.LocalDateTime; для сохранения истории операций.
// Нужно чтобы показывал успешно ил купил или нет, хватало ли денег или нет.
// SOLID'у должен соответствовать
// активно применять стим мап.