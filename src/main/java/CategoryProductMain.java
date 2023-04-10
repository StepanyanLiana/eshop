import manager.CategoryManager;
import manager.ProductManager;
import model.Category;
import model.Product;

import java.util.List;
import java.util.Scanner;

public class CategoryProductMain implements Command {
    private static CategoryManager categoryManager = new CategoryManager();
    private static ProductManager productManager = new ProductManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            Command.printCommands();
            String command = scanner.nextLine();
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case ADD_CATEGORY:
                    addCategory();
                    break;
                case EDIT_CATEGORY_BY_ID:
                   editCategoryById();
                    break;
                case DELETE_CATEGORY_BY_ID:
                    deleteCategoryById();
                    break;
                case ADD_PRODUCT:
                    addProduct();
                    break;
                case EDIT_PRODUCT_BY_ID:
                    editProductById();
                    break;
                case DELETE_PRODUCT_BY_ID:
                    deleteProductById();
                    break;
                case PRINT_SUM_OF_PRODUCTS:
                    printSumOfProduct();
                    break;
                case PRINT_MAX_OF_PRICE_PRODUCT:
                    printMaxOfPriceProduct();
                    break;
                case PRINT_MIN_OF_PRICE_PRODUCT:
                    printMinOfPriceProduct();
                    break;
                case AVG_OF_PRICE_PRODUCT:
                    avgOfPriceProduct();
                    break;
                default:
                    System.out.println("invalid command");
            }
        }
    }

    private static void avgOfPriceProduct() {
        productManager.printAvgPrice();
    }

    private static void printMinOfPriceProduct() {
        productManager.printMinPrice();
    }

    private static void printMaxOfPriceProduct() {
        productManager.printMaxPrice();
    }

    private static void printSumOfProduct() {
        productManager.printSum();
    }

    private static void deleteProductById() {
        List<Product> productList = productManager.getAll();
        for (Product product : productList) {
            System.out.println(product);
        }
        System.out.println("please choose product id");
        int id = Integer.parseInt(scanner.nextLine());
        productManager.deleteById(id);
    }


    private static void editProductById() {
        List<Product> productList = productManager.getAll();
        for (Product product : productList) {
            System.out.println(product);
        }
        System.out.println("please choose product id");
        int id = Integer.parseInt(scanner.nextLine());
        if (productManager.getById(id) != null) {
            System.out.println("please input product name,description,price,quantity");
            String productStr = scanner.nextLine();
            String[] productData = productStr.split(",");
            Product product = new Product();
            product.setId(id);
            product.setName(productData[0]);
            product.setDescription(productData[1]);
            product.setPrice(Integer.parseInt(productData[2]));
            product.setQuantity(Integer.parseInt(productData[3]));
            productManager.edit(product);
        } else {
            System.out.println("product does not exists");
        }
    }

    private static void addProduct() {
        List<Category> categoryList = categoryManager.getAll();
        for (Category category : categoryList) {
            System.out.println(category);
        }
        System.out.println("please choose category");
        int id = Integer.parseInt(scanner.nextLine());
        Category byId = categoryManager.getById(id);
        if (byId != null) {
            System.out.println("please input name,description,price,quantity");
            String productStr = scanner.nextLine();
            String[] productData = productStr.split(",");
            Product product = new Product();
            product.setCategory(byId);
            product.setName(productData[0]);
            product.setDescription(productData[1]);
            product.setPrice(Integer.parseInt(productData[2]));
            product.setQuantity(Integer.parseInt(productData[3]));
            productManager.add(product);
        }
    }


    private static void deleteCategoryById() {
        List<Category> categoryList = categoryManager.getAll();
        for (Category category : categoryList) {
            System.out.println(category);
        }
        System.out.println("please choose category id");
        int id = Integer.parseInt(scanner.nextLine());
        categoryManager.deleteById(id);
        System.out.println("category deleted");
    }

    private static void editCategoryById() {
        List<Category> categoryList = categoryManager.getAll();
        for (Category category : categoryList) {
            System.out.println(category);
        }
        System.out.println("please choose category id");
        int id = Integer.parseInt(scanner.nextLine());
        if (categoryManager.getById(id) != null) {
            System.out.println("please input category name");
            String categoryName = scanner.nextLine();
            Category category = new Category();
            category.setId(id);
            category.setName(categoryName);
            categoryManager.editCategory(category);
        } else {
            System.out.println("category does not exists");
        }
    }

    private static void addCategory() {
        System.out.println("please input category name");
        String categoryName = scanner.nextLine();
        Category category = new Category();
        category.setName(categoryName);
        categoryManager.add(category);
    }
}
