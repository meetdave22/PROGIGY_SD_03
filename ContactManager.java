
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


class Contact {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Getters and Setters
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return String.format("Name: %s | Phone: %s | Email: %s", name, phone, email);
    }
}

public class ContactManager {
    private static ArrayList<Contact> contacts = new ArrayList<>();
    private static final String FILENAME = "contacts.txt";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadContactsFromFile();
        
        while (true) {
            System.out.println("\nContact Management System");
            System.out.println("1. Add Contact");
            System.out.println("2. View All Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    viewContacts();
                    break;
                case 3:
                    editContact();
                    break;
                case 4:
                    deleteContact();
                    break;
                case 5:
                    saveContactsToFile();
                    System.out.println("Contacts saved. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addContact() {
        System.out.println("\nAdd New Contact");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        contacts.add(new Contact(name, phone, email));
        System.out.println("Contact added successfully!");
    }

    private static void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("\nNo contacts available.");
            return;
        }

        System.out.println("\nContact List:");
        for (int i = 0; i < contacts.size(); i++) {
            System.out.println((i + 1) + ". " + contacts.get(i));
        }
    }

    private static void editContact() {
        viewContacts();
        if (contacts.isEmpty()) return;

        System.out.print("\nEnter contact number to edit: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline

        if (index < 0 || index >= contacts.size()) {
            System.out.println("Invalid contact number.");
            return;
        }

        Contact contact = contacts.get(index);
        System.out.println("Editing: " + contact);
        
        System.out.print("Enter new name (leave blank to keep current): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) contact.setName(name);

        System.out.print("Enter new phone (leave blank to keep current): ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) contact.setPhone(phone);

        System.out.print("Enter new email (leave blank to keep current): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) contact.setEmail(email);

        System.out.println("Contact updated successfully!");
    }

    private static void deleteContact() {
        viewContacts();
        if (contacts.isEmpty()) return;

        System.out.print("\nEnter contact number to delete: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline

        if (index < 0 || index >= contacts.size()) {
            System.out.println("Invalid contact number.");
            return;
        }

        Contact removed = contacts.remove(index);
        System.out.println("Deleted: " + removed);
    }

    private static void loadContactsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    contacts.add(new Contact(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                }
            }
        } catch (IOException e) {
            // File doesn't exist is okay - we'll create it when saving
        }
    }

    private static void saveContactsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
            for (Contact contact : contacts) {
                writer.println(contact.getName() + " | " + contact.getPhone() + " | " + contact.getEmail());
            }
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }
}

    

