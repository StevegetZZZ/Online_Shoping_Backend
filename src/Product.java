////TIP Для <b>запуска</b> кода нажмите <shortcut actionId="Run"/> или
//// щелкните значок <icon src="AllIcons.Actions.Execute"/> в боковой области.
//import <String>;

public class Product {
    private String title;
    private double price;

    // Пустой конструктор
    public Product() {
    }


    // Конструктор с параметрами
    public Product(String title, double price) {
        this.title = title;
        this.price = price;
    }


    // Геты
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



    // Переопределённый метод toString
    public String toString() {
        return "Product{title='" + title + "', price=" + price + '}';
    }


}

class Main {
    public static void main(String[] args) {
        Product data = new Product("Title", 0);

        System.out.println("Имя: " + data.getTitle());
        System.out.println("Цена: " + data.getPrice());






    }

}






