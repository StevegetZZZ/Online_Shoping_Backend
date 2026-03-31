////TIP Для <b>запуска</b> кода нажмите <shortcut actionId="Run"/> или
//// щелкните значок <icon src="AllIcons.Actions.Execute"/> в боковой области.
import java.util.*;



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



    // Метод show info
    public abstract String showInfo(List<Product> products);



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


№3
Инкапсуляцией защищаем класс от наружного доступа.
Класс продукт. В него добавить абстрактный метод "show info" и преобразовать в других классах.
В классе электроники и других переопределить этот метод через @Override
Создать несколько объектов и добавить их в список по категориям через метод добавления.
Через метод "show info" теперь должен вызываться список.




Сделать саб категории в отдельном файле

*/



