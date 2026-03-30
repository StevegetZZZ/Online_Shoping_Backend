////TIP Для <b>запуска</b> кода нажмите <shortcut actionId="Run"/> или
//// щелкните значок <icon src="AllIcons.Actions.Execute"/> в боковой области.
import java.util.*;

// Класс Category (Категория)
class Category {
    private String name;
    private List<Category> subcategories; // подкатегории


    // Создания листа в категории
    public Category(String name) {
        this.name = name;
        this.subcategories = new ArrayList<>();
    }


    // Получение имени
    public String getName() {
        return name;
    }


    // Подкатегории
    public void addSubcategory(Category subcategory) {
        subcategories.add(subcategory);
    }
    // взаимодействие подкатегории с листом
    public List<Category> getSubcategories() {
        return new ArrayList<>(subcategories);
    }

    // Показ чего наворатили
    public void showCategory() {
        System.out.println("Категория: " + name);
        if (!subcategories.isEmpty()) // Если не пустой
        {
            // показ пока не закончатся
            System.out.println("Подкатегории:");
            for (Category sub : subcategories) {
                System.out.println("  - " + sub.getName());
            }
        } else {
            System.out.println("Подкатегорий нет");
        }
    }
}

// Абстрактный класс Product
abstract class Product {
    private static int nextId = 1;
    private int id;
    private String title;
    private double price;
    private Category category; // Шиза, но так можно и нужно.

    // Map для учёта категорий. По условиям нужно через Map
    private static Map<Category, List<Product>> categoryProducts = new HashMap<>();

    public Product(String title, double price, Category category) {
        this.id = nextId++; // повышение id
        this.title = title;
        this.price = price;
        this.category = category;

        // Добавляем продукт в соответствующую категорию
        if (!categoryProducts.containsKey(category)) {
            categoryProducts.put(category, new ArrayList<>());
        }
        categoryProducts.get(category).add(this);
    }

    // Геты
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    // Абстрактный метод для суммы стоимости товаров. Ниже по коду, дублирован в двух классах.
    public abstract double calculateTotalPrice(List<Product> products);

    @Override
    // Показ
    public String toString() {
        return "Product{id=" + id + ", title='" + title + "', price=" + price +
                ", category='" + category.getName() + "'}";
    }

    // Статический метод для получения статистики по категориям
    public static Map<Category, Integer> getCategoryStatistics() {
        Map<Category, Integer> stats = new HashMap<>(); // stats хранит итоговую статистику.
        for (var entry : categoryProducts.entrySet()) {
            Category category = entry.getKey(); // Key возвращает ключ записи. Нужен для работы с Map. Помогает в переборе
            int productCount = entry.getValue().size(); // Value работает с самими значениями в записи.
            stats.put(category, productCount);
        }
        return stats;
    }

}

// Класс электроники, наследник Product
class Electronic extends Product {

    public Electronic(String title, double price) {
        super(title, price, new Category("Electronics"));
    }

    @Override
    // Расчёт стоимости товаров
    public double calculateTotalPrice(List<Product> products) {
        double total = 0;
        for (Product product : products) {
            if (product instanceof Electronic) // instanceof это оператор, который проверяет, является ли объект экземпляром класса
            {
                total += product.getPrice();
            }
        }
        return total;
    }
}

// Класс товаров для сада, наследник Product
class Garden extends Product {

    public Garden(String title, double price) {
        super(title, price, new Category("Garden"));
    }

    @Override
    // Расчёт стоимости товаров
    public double calculateTotalPrice(List<Product> products) {
        double total = 0;
        for (Product product : products) {
            if (product instanceof Garden) // instanceof это оператор, который проверяет, является ли объект экземпляром класса
            {
                total += product.getPrice();
            }
        }
        return total;
    }
}





/*
 Сделать Prooduct абстрактным классом. Сделать id, prise, hashmap (категория. Уникальная).
 Сделать arraylist hashmap'а.
 Сделать абстрактный метод. Принимает стоимость товаров и выдаёт сумму.
 Создать классы электроники и сада, они наследники каталога.
 Создать разные из каждого 3 объекта (электроники и сада). Вывести на экран
 Каталог. id создаётся автоматически.



 В классе категорий сделать лист, там указать тип данных самого класса. сделать саб категории,
 Добавить методы в каталог чтобы добавлять новые категории.
 Метод должен добавлять новую саб категорию. Тип данных должен быть как у листа
 Тип данных саб категории это категории.
 В класс категории добавить имя и лист под названием саб категории.
 Сделать show категории
 Создать класс "каталог". В нём сделать основные категории (список базовых)

В каталог добавить счётчик который показывает сколько есть в наличии главных категорий.

*/



