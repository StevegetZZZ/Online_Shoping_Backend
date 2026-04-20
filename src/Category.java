package Hashcode_and_toString;

import java.util.ArrayList;
import java.util.List;

import java.util.Objects;

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
        if (!subcategories.isEmpty()) { // Если не пустой
            // показ пока не закончатся
            System.out.println("Подкатегории:");
            for (Category sub : subcategories) {
                System.out.println("  - " + sub.getName());
            }
        } else {
            System.out.println("Подкатегорий нет");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
