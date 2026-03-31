import java.util.ArrayList;
import java.util.List;

// Класс Catalog (Каталог)
class Catalog {
    private List<Category> mainCategories;
    private static int mainCategoryCount = 0; // счётчик для категорий

    public Catalog() {
        this.mainCategories = new ArrayList<>();
    }

    // Метод для добавления основной категории
    public void addMainCategory(Category category) {
        mainCategories.add(category);
        mainCategoryCount++;
    }

    // Метод для показа всех категорий
    public void showAllCategories() {
        System.out.println("\n=== ОСНОВНЫЕ КАТЕГОРИИ ===");
        for (Category category : mainCategories) {
            category.showCategory();
            System.out.println("---");
        }
        System.out.println("Всего основных категорий: " + mainCategoryCount);
    }

    // Геттер для количества основных категорий
    public int getMainCategoryCount() {
        return mainCategoryCount;
    }

    // Геттер для списка основных категорий
    public List<Category> getMainCategories() {
        return new ArrayList<>(mainCategories);
    }
}