import java.util.ArrayList;
import javax.swing.*;
import java.time.LocalDate;
import java.io.*;

public class ItemManger {

    static ArrayList<itemData> items;

    public ItemManger() {
        items = new ArrayList<itemData>();
    }

    public void addItem() {

        LocalDate creationDate;

        int maxItemId = 0;
        for (int i = 0; i < items.size(); i++) {
            itemData item = items.get(i);
            if (item.itemid > maxItemId) {
                maxItemId = item.itemid;
            }
        }
        int id = maxItemId + 1;
      

        JOptionPane.showMessageDialog(null, "Item Id is: " + id, "Result", JOptionPane.INFORMATION_MESSAGE);

        String Description = JOptionPane.showInputDialog("Enter Description of item");
        if (Description == null || Description.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invalid command: Description cannot be empty.");
            return;
        }

        String price = JOptionPane.showInputDialog("Enter price of item");
        if (price == null) {
            JOptionPane.showMessageDialog(null, "Error: Price cannot be null.");
            return;
        }
        try {
            Integer.parseInt(price);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Price must be a number.");
            return;
        }

        String Quantity = JOptionPane.showInputDialog("Enter Quantity of item");
        if (Quantity == null) {
            JOptionPane.showMessageDialog(null, "Error: Quantity cannot be null.");
            return;
        }
        try {
            Integer.parseInt(Quantity);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Quantity must be a number.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "Save item information?");
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        creationDate = LocalDate.now();

        itemData item = new itemData(id, Description, Integer.parseInt(price), Integer.parseInt(Quantity),
                creationDate);
        items.add(item);
        JOptionPane.showMessageDialog(null, "Item information successfully saved.");
        System.out.println("Item added successfully. Id is: " + id);

    }

    public void modifyItems(String idString) {
        int id = 0;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number for the item ID.");
            return;
        }

        boolean itemFound = false;
        for (int i = 0; i < items.size(); i++) {
            itemData item = (itemData) items.get(i);

            if (id == item.itemid) {
                item.print(id);
                String Description = JOptionPane.showInputDialog("Enter Description of item");
                String price = JOptionPane.showInputDialog("Enter price of item");
                String Quantity = JOptionPane.showInputDialog("Enter Quantity of item");

                // Null or empty input handling
                if (Description == null || Description.equals("")) {
                    Description = item.getDescription();
                }
                if (price == null || price.equals("")) {
                    price = String.valueOf(item.getPrice());
                }
                if (Quantity == null || Quantity.equals("")) {
                    Quantity = String.valueOf(item.getQuantity());
                }

                try {
                    item.setDescription(Description);
                    item.setPrice(Integer.parseInt(price));
                    item.setQuantity(Integer.parseInt(Quantity));

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid input. Please enter a valid number for price and quantity.");
                    return;
                } finally {
                    itemFound = true;
                }
            }
        }
        if (!itemFound) {
            JOptionPane.showMessageDialog(null, "Item not found");
        } else {
            // Confirmation to save the modified item data
            int confirm = JOptionPane.showConfirmDialog(null, "Save modified item data?", "Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.out.println("Item information successfully saved");
            }
        }
    }

    public void findItem() {
        String itemId = JOptionPane.showInputDialog(
                "Please specify at least one of the following to find the item. Leave all fields blank to return to Customers Menu.\n\nItem ID:");
        String description = JOptionPane.showInputDialog("Description:");
        String price = JOptionPane.showInputDialog("Price:");
        String quantity = JOptionPane.showInputDialog("Quantity:");

        boolean found = false;
        for (int i = 0; i < items.size(); i++) {
            itemData item = items.get(i);
            if ((itemId == null || itemId.isEmpty() || Integer.parseInt(itemId) == item.itemid) &&
                    (description == null || description.isEmpty() || description.equals(item.Description)) &&
                    (price == null || price.isEmpty() || Integer.parseInt(price) == item.price) &&
                    (quantity == null || quantity.isEmpty() || Integer.parseInt(quantity) == item.Quantity)) {
                item.print(item.itemid);
                System.out.println("Item found successfully");
                found = true;
            }
        }

        if (!found) {
            System.out.println("Item not found");
            JOptionPane.showMessageDialog(null, "Item not found", "Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void removeItem() {

        String inputremove;
        int id;
        boolean itemFound = false;
        inputremove = JOptionPane.showInputDialog(" Enter id of item to be removed:");

        try {
            id = Integer.parseInt(inputremove);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number for the item ID.");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            itemData item = (itemData) items.get(i);

            if (id == item.itemid) { // make another check if sale is made or not
                item.print(id);
                items.remove(i);
                JOptionPane.showMessageDialog(null, "Item removed successfully");
                itemFound = false;
                break;
            } else {
                itemFound = true;
            }
        }
        if (itemFound == true) {

            JOptionPane.showMessageDialog(null, "Item not found", "Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void saveItemsToFile() {
        try {
            FileWriter fw = new FileWriter("items.txt");
            PrintWriter pw = new PrintWriter(fw);

            for (int i = 0; i < items.size(); i++) {
                String line = items.get(i).itemid + ";" + items.get(i).Description + ";" + items.get(i).price + ";"
                        + items.get(i).Quantity + ";" + items.get(i).CreationDate + ";";
                pw.println(line);
            }

            pw.flush();
            pw.close();
            fw.close();
            System.out.println("Data saved to items file successfully!");
        } catch (IOException ioEx) {
            System.out.println("An error occurred while saving data to file: " + ioEx.getMessage());
        }
    }

    public void loadItemsFromFile() {
        try {
            FileReader fr = new FileReader("items.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                int itemid = Integer.parseInt(fields[0]);
                String description = fields[1];
                int price = Integer.parseInt(fields[2]);
                int quantity = Integer.parseInt(fields[3]);
                LocalDate creationDate = LocalDate.parse(fields[4]);
                items.add(new itemData(itemid, description, price, quantity, creationDate));
            }

            br.close();
            fr.close();
            System.out.println("Data loaded from items file successfully!");
        } catch (IOException ioEx) {
            System.out.println("An error occurred while loading data from file: " + ioEx.getMessage());
        }
    }

    public void ItemsMenu() {
        String inputitem;
        int ic;
        boolean check = false;

        for (;;) {
            if (check == true) {
                break;
            }
            inputitem = JOptionPane.showInputDialog(
                    " Enter 1 to Add new Item" + "\n Enter 2 to Update Item details" + "\n Enter 3 to Find Items"
                            + "\n Enter 4 to Remove Existing Item " + " \n Enter 5 to Back to Main Menu ");
            try {
                ic = Integer.parseInt(inputitem);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 1 and 5.");
                continue; // restart the loop
            }
            switch (ic) {
                case 1:
                    addItem();
                    break;
                case 2: {
                    String inputupdate;
                    // int id;
                    inputupdate = JOptionPane.showInputDialog(" Enter id of item to be updated");
                    // id = Integer.parseInt(inputupdate);
                    modifyItems(inputupdate);
                    break;
                }
                case 3:
                    findItem();
                    break;
                case 4: {

                    removeItem();
                    break;
                }
                case 5:
                    check = true;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 1 and 5.");
                    break;
            }

        }
    }

}
