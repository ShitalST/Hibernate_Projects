package recipeManagement;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class RecipeDemo {

    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure("Recipe.xml").buildSessionFactory();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Main menu-driven method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RecipeDemo management = new RecipeDemo();

        while (true) {
            displayMainMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    management.addRecipe();
                    break;
                case 2:
                    management.handleViewRecipes();
                    break;
                case 3:
                    management.handleUpdateRecipe();
                    break;
                case 4:
                    management.deleteRecipe();
                    break;
                case 5:
                    management.exitApplication();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to display main menu
    private static void displayMainMenu() {
        System.out.println("\nRecipe Management System");
        System.out.println("1. Add Recipe");
        System.out.println("2. View Recipes");
        System.out.println("3. Update Recipe");
        System.out.println("4. Delete Recipe");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }



    // Case 2: Handle viewing recipes
    public void handleViewRecipes() {
    	int ch;
        Scanner scanner = new Scanner(System.in);
        do {
        System.out.println("\nView Recipes");
        System.out.println("1. View All Recipes");
        System.out.println("2. View Recipe by ID");
        System.out.println("3. View Recipes by Category");
        System.out.println("4.Back to main menu");
        System.out.print("Enter your choice: ");
        ch = scanner.nextInt();

        switch (ch) {
            case 1:
                viewRecipes();
                break;
            case 2:
                viewRecipeById();
                break;
            case 3:
                filterRecipesByCategory();
                break;
            case 4:
            	return;
            default:
                System.out.println("Invalid choice for viewing.");
        }
        }while(ch!=4);
    }

    // Case 3: Handle updating a recipe
    public void handleUpdateRecipe() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nUpdate Recipes");
        System.out.println("1. Update Recipe by ID");
        System.out.print("Enter your choice: ");
        int updateChoice = scanner.nextInt();

        switch (updateChoice) {
            case 1:
                updateRecipe();
                break;
            default:
                System.out.println("Invalid choice for updating.");
        }
    }



    // Case 5: Handle exiting the application
    public void exitApplication() {
        sessionFactory.close();
        System.out.println("Exiting...");
        System.exit(0);
    }

    // Method to add a new recipe
    public void addRecipe() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter recipe name: ");
        String name = scanner.nextLine();
        if (!isValidInput(name)) {
            System.out.println("Recipe name cannot be empty.");
            return;
        }

        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter instructions: ");
        String instructions = scanner.nextLine();

        System.out.print("Enter category name: ");
        String categoryName = scanner.nextLine();
        if (!isValidInput(categoryName)) {
            System.out.println("Category name cannot be empty.");
            return;
        }

        Category category = new Category(categoryName);
        Recipe recipe = new Recipe(name, description, instructions, category);

        System.out.print("Enter number of ingredients: ");
        int ingredientCount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < ingredientCount; i++) {
            System.out.print("Enter ingredient name: ");
            String ingredientName = scanner.nextLine();
            System.out.print("Enter quantity: ");
            String quantity = scanner.nextLine();
            Ingredient ingredient = new Ingredient(ingredientName, quantity);
            recipe.getIngredients().add(ingredient);
        }

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(category);
        session.save(recipe);
        transaction.commit();
        session.close();

        System.out.println("Recipe added successfully.");
    }

    // Method to view all recipes
    public void viewRecipes() {
        Session session = sessionFactory.openSession();
        List<Recipe> recipes = session.createQuery("from Recipe", Recipe.class).list();
        recipes.forEach(System.out::println);
        session.close();
    }

    // Method to view recipe by ID
    public void viewRecipeById() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter recipe ID: ");
        int recipeId = scanner.nextInt();

        Session session = sessionFactory.openSession();
        Recipe recipe = session.get(Recipe.class, recipeId);
        if (recipe != null) {
            System.out.println(recipe);
        } else {
            System.out.println("Recipe not found with ID: " + recipeId);
        }
        session.close();
    }

    // Method to filter recipes by category
    public void filterRecipesByCategory() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter category to filter: ");
        String category = scanner.nextLine();

        Session session = sessionFactory.openSession();
        List<Recipe> recipes = session.createQuery("from Recipe where category.name = :categoryName", Recipe.class)
                .setParameter("categoryName", category)
                .list();

        if (recipes.isEmpty()) {
            System.out.println("No recipes found in this category.");
        } else {
            recipes.forEach(System.out::println);
        }
        session.close();
    }

    // Method to update a recipe
    public void updateRecipe() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter recipe ID to update: ");
        int recipeId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Session session = sessionFactory.openSession();
        Recipe recipe = session.get(Recipe.class, recipeId);
        if (recipe != null) {
            System.out.print("Enter new recipe name: ");
            recipe.setName(scanner.nextLine());
            System.out.print("Enter new description: ");
            recipe.setDescription(scanner.nextLine());
            System.out.print("Enter new instructions: ");
            recipe.setInstructions(scanner.nextLine());

            Transaction transaction = session.beginTransaction();
            session.update(recipe);
            transaction.commit();
            System.out.println("Recipe updated successfully.");
        } else {
            System.out.println("Recipe not found.");
        }
        session.close();
    }

    // Method to delete a recipe
    public void deleteRecipe() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter recipe ID to delete: ");
        int recipeId = scanner.nextInt();

        Session session = sessionFactory.openSession();
        Recipe recipe = session.get(Recipe.class, recipeId);
        if (recipe != null) {
            Transaction transaction = session.beginTransaction();
            session.delete(recipe);
            transaction.commit();
            System.out.println("Recipe deleted successfully.");
        } else {
            System.out.println("Recipe not found.");
        }
        session.close();
    }

    // Helper method for input validation
    private boolean isValidInput(String input) {
        return input != null && !input.trim().isEmpty();
    }
}
