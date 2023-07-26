import java.time.LocalDate;
import javax.swing.*;

public class itemData {

    int itemid;
    String Description;
    int price;
    int Quantity;
    LocalDate CreationDate;

    public itemData(int itemid, String description, int price, int quantity, LocalDate creationDate) {
        this.itemid = itemid;
        Description = description;
        this.price = price;
        Quantity = quantity;
        CreationDate = creationDate;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public LocalDate getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        CreationDate = creationDate;
    }

    public void print(int id) {

        String table = "\n----------------------------------------------------------------------------------------------------------\n";
        table += "Item ID: " + id + "\n";
        table += "Description: " + Description + "\n";
        table += "Price: " + price + "\n";
        table += "Quantity: " + Quantity + "\n";
        table += "Creation Date: " + CreationDate + "\n";
        table += "----------------------------------------------------------------------------------------------------------\n";
        
        JOptionPane.showMessageDialog(null, table);
    }
}