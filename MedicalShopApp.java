import java.io.*;
import java.util.*;

class User {
    String username;
    String password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

class Medicine {
    int id;
    String name;
    double price;

    Medicine(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

public class MedicalShopApp {

    static Scanner sc = new Scanner(System.in);
    static Map<String, String> users = new HashMap<>();
    static List<Medicine> medicineList = new ArrayList<>();
    static List<Medicine> cart = new ArrayList<>();

    static final String USER_FILE = "users.txt";
    static final String ORDER_FILE = "orders.txt";

    public static void main(String[] args) {
        loadUsers();
        loadMedicines();

        System.out.println("===== ONLINE MEDICAL SHOP SYSTEM =====");

        while (true) {
            System.out.println("\n1) Register");
            System.out.println("2) Login");
            System.out.println("3) Exit");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1: register(); break;
                case 2: if (login()) menu(); break;
                case 3: System.out.println("Thank you!"); System.exit(0);
                default: System.out.println("Invalid choice.");
            }
        }
    }

    // -------------------------------------------------------------------
    // USER REGISTRATION
    // -------------------------------------------------------------------
    static void register() {
        System.out.print("Enter username: ");
        String uname = sc.next();
        System.out.print("Enter password: ");
        String pwd = sc.next();

        if (users.containsKey(uname)) {
            System.out.println("❌ Username already exists!");
            return;
        }

        users.put(uname, pwd);
        saveUsers();

        System.out.println("✔ Registration Successful!");
    }

    // -------------------------------------------------------------------
    // USER LOGIN
    // -------------------------------------------------------------------
    static boolean login() {
        System.out.print("Username: ");
        String uname = sc.next();
        System.out.print("Password: ");
        String pwd = sc.next();

        if (users.containsKey(uname) && users.get(uname).equals(pwd)) {
            System.out.println("✔ Login Successful!");
            return true;
        } else {
            System.out.println("❌ Invalid Credentials!");
            return false;
        }
    }

    // -------------------------------------------------------------------
    // MAIN MENU AFTER LOGIN
    // -------------------------------------------------------------------
    static void menu() {
        while (true) {
            System.out.println("\n===== USER MENU =====");
            System.out.println("1) View Medicines");
            System.out.println("2) Add to Cart");
            System.out.println("3) View Cart");
            System.out.println("4) Place Order");
            System.out.println("5) Logout");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1: showMedicines(); break;
                case 2: addToCart(); break;
                case 3: viewCart(); break;
                case 4: placeOrder(); break;
                case 5: return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    // -------------------------------------------------------------------
    // MEDICINE LIST
    // -------------------------------------------------------------------
    static void loadMedicines() {
        medicineList.add(new Medicine(1, "Paracetamol", 20));
        medicineList.add(new Medicine(2, "Amoxicillin", 120));
        medicineList.add(new Medicine(3, "Cough Syrup", 80));
        medicineList.add(new Medicine(4, "Pain Relief Gel", 60));
    }

    static void showMedicines() {
        System.out.println("\n----- Available Medicines -----");
        for (Medicine m : medicineList) {
            System.out.println(m.id + ") " + m.name + " - ₹" + m.price);
        }
    }

    // -------------------------------------------------------------------
    // ADD MEDICINE TO CART
    // -------------------------------------------------------------------
    static void addToCart() {
        showMedicines();
        System.out.print("Enter medicine ID to add to cart: ");
        int id = sc.nextInt();

        for (Medicine m : medicineList) {
            if (m.id == id) {
                cart.add(m);
                System.out.println("✔ Added to cart!");
                return;
            }
        }
        System.out.println("❌ Invalid ID!");
    }

    // -------------------------------------------------------------------
    // VIEW CART
    // -------------------------------------------------------------------
    static void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("🛒 Cart is empty!");
            return;
        }

        double total = 0;

        System.out.println("\n----- Your Cart -----");
        for (Medicine m : cart) {
            System.out.println(m.name + " - ₹" + m.price);
            total += m.price;
        }
        System.out.println("----------------------");
        System.out.println("Total: ₹" + total);
    }

    // -------------------------------------------------------------------
    // PLACE ORDER
    // -------------------------------------------------------------------
    static void placeOrder() {
        if (cart.isEmpty()) {
            System.out.println("🛒 Cart is empty. Add items first.");
            return;
        }

        try (FileWriter fw = new FileWriter(ORDER_FILE, true)) {
            fw.write("Order:\n");
            double total = 0;

            for (Medicine m : cart) {
                fw.write(m.name + " - ₹" + m.price + "\n");
                total += m.price;
            }
            fw.write("Total: ₹" + total + "\n");
            fw.write("-------------------------\n");

            System.out.println("✔ Order Placed Successfully!");
            System.out.println("✔ Saved to orders.txt");

            cart.clear();
        } catch (Exception e) {
            System.out.println("Error writing order file.");
        }
    }

    // -------------------------------------------------------------------
    // FILE HANDLING - USERS
    // -------------------------------------------------------------------
    static void loadUsers() {
        try {
            File file = new File(USER_FILE);
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                users.put(parts[0], parts[1]);
            }
            br.close();
        } catch (Exception ignored) {}
    }

    static void saveUsers() {
        try (FileWriter fw = new FileWriter(USER_FILE)) {
            for (String u : users.keySet()) {
                fw.write(u + "," + users.get(u) + "\n");
            }
        } catch (Exception ignored) {}
    }
}
