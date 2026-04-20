package Hashcode_and_toString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Catalog {
    private List<Category> mainCategories;
    private static int mainCategoryCount = 0;

    public Catalog() {
        this.mainCategories = new ArrayList<>();
    }


    public void addMainCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Категория не может быть null");
        }
        mainCategories.add(category);
        mainCategoryCount++;
    }

    public boolean removeMainCategory(Category category) {
        Objects.requireNonNull(category, "Категория не может быть null");
        boolean removed = mainCategories.remove(category);
        if (removed) {
            mainCategoryCount--;
        }
        return removed;
    }


    public void showAllCategories() {
        System.out.println("\n=== ОСНОВНЫЕ КАТЕГОРИИ ===");
        if (mainCategories.isEmpty()) {
            System.out.println("В каталоге нет основных категорий.");
            return;
        }

        for (Category category : mainCategories) {
            category.showCategory();
            System.out.println("---");
        }
        System.out.println("Всего основных категорий: " + mainCategoryCount);
    }

    public int getMainCategoryCount() {
        return mainCategoryCount;
    }

    public List<Category> getMainCategories() {
        return new ArrayList<>(mainCategories);
    }

    public boolean containsCategory(Category category) {
        Objects.requireNonNull(category, "Категория не может быть null");
        return mainCategories.contains(category);
    }

    public void clearAllCategories() {
        mainCategories.clear();
        mainCategoryCount = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return mainCategoryCount == catalog.mainCategoryCount &&
                Objects.equals(mainCategories, catalog.mainCategories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainCategories, mainCategoryCount);
    }

    @Override
    public String toString() {
        return "Catalog{mainCategories=" + mainCategories +
                ", mainCategoryCount=" + mainCategoryCount + '}';
    }
}
