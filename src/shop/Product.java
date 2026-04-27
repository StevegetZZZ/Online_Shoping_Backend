package shop;
import java.util.*;
////TIP Для <b>запуска</b> кода нажмите <shortcut actionId="Run"/> или
//// щелкните значок <icon src="AllIcons.Actions.Execute"/> в боковой области.


abstract class Product implements Payable, Financial, Comparable<Product> {
    private static int nextId = 1;
    private int id;
    private String title;
    private double price;
    private Category category;

    // Поля для оплаты
    private boolean paid;
    private double paidAmount;

    // Поле для статуса заказа
    private OrderStatus orderStatus = OrderStatus.NEW;

    private static Map<Category, List<Product>> categoryProducts = new HashMap<>();

    public Product(String title, double price, Category category) {
        this.id = nextId++;
        this.title = title;
        this.price = price;
        this.category = category;
        this.paid = false;
        this.paidAmount = 0;

        if (!categoryProducts.containsKey(category)) {
            categoryProducts.put(category, new ArrayList<>());
        }
        categoryProducts.get(category).add(this);
    }

    // Геттеры
    public int getId() { return id; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public Category getCategory() { return category; }

    // Для статуса заказа
    public OrderStatus getOrderStatus() { return orderStatus; }
    public void setOrderStatus(OrderStatus orderStatus) { this.orderStatus = orderStatus; }

    // Реализация Payable
    @Override
    public void pay(double amount) {
        if (amount > 0) {
            paid = true;
            paidAmount += amount;
        }
    }

    @Override
    public double getPay() { return paidAmount; }

    // Реализация Financial
    @Override
    public boolean isPaid() { return paid; }
    @Override
    public void setPaid(boolean paid) { this.paid = paid; }

    // Абстрактные методы
    public abstract double calculateTotalPrice(List<Product> products);
    public abstract String showInfo(List<Product> products);

    @Override
    public String toString() {
        return "Product{id=" + id + ", title='" + title + "', price=" + price +
                ", category='" + category.getName() + "', status=" + orderStatus + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    public static Map<Category, Integer> getCategoryStatistics() {
        Map<Category, Integer> stats = new HashMap<>();
        for (var entry : categoryProducts.entrySet()) {
            stats.put(entry.getKey(), entry.getValue().size());
        }
        return stats;
    }

    @Override
    public int compareTo(Product other) { return Double.compare(this.price, other.price); }
}



//    if (this.id = other.id && this.title == other.title) {
//        Product
//    }
//    @Override
//    public boolean equals(Object obj) {
//
//        if (this == obj) return true;
//        if (obj == null) return false;
//        if (!(obj instanceof Product)) return  false;
//        if (this.id == nextId) return true;
//        Product other = (Product) obj;
//
//
//        //return this.id.equals(((Product) obj).id) && this.title == other.title;
//        return false;
//    };
//













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



