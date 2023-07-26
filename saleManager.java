
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import java.io.*;

public class saleManager {

    static ArrayList<saledata> sales;
    static ArrayList<saleLineItem> saleLineItems;
    int Total;

    public saleManager() {
        sales = new ArrayList<saledata>();
        saleLineItems = new ArrayList<saleLineItem>();
    }

    public void saleMenu() {
        ArrayList<itemData> itemsS = ItemManger.items;
        ArrayList<customerdata> customersS = customerManager.customers;

        Total = 0;
        String inputsale;
        int sc, lineno = 0;

        int maxSalesId = 0;
        for (int i = 0; i < sales.size(); i++) {
            saledata sale = sales.get(i);
            if (sale.SalesId > maxSalesId) {
                maxSalesId = sale.SalesId;
            }
        }
        int salesId = maxSalesId + 1;

        int customerid = initialmenu();
        boolean icheck = false, ccheck = false, itemcheck = false;
        String customername = "";

        for (int j = 0; j < customersS.size(); j++) {
            customerdata customer = customersS.get(j);

            if (customerid == customer.CustomerId) {
                customername = customer.Name;
                System.out.println("Customer Found");

                LocalDate date = LocalDate.now();
                String status = "unpaid";

                for (;;) {
                    inputsale = JOptionPane.showInputDialog(" Sales Id: " + salesId +
                            "\n Total amount payable: Rs." + Total + "\n Press 1 to Enter New Item "
                            + "\n Press 2 to End Sale"
                            + "\n Press 3 to Remove an existing Item from the current sale"
                            + "\n Press 4 to Cancel Sale");

                    try {
                        sc = Integer.parseInt(inputsale);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 1 and 4.");
                        continue; // restart the loop
                    }
                    switch (sc) {
                        case 1: {
                            int itemId = newItemMenu();
                            int quantityi = 0;

                            for (int i = 0; i < itemsS.size(); i++) {
                                itemData item = itemsS.get(i);

                                if (itemId == item.itemid) {

                                    lineno++;
                                    String Quantity = JOptionPane.showInputDialog(
                                            " Item Id: " + itemId + "\n Description: " + item.Description
                                                    + "\n Price: Rs." + item.price + "\n Enter Quantity of item: ");

                                    try {
                                        quantityi = Integer.parseInt(Quantity);
                                    } catch (NumberFormatException e) {
                                        itemcheck = true;
                                        JOptionPane.showMessageDialog(null,
                                                "Invalid input. Quantity should be a number.");
                                        break;
                                    }

                                    // checking if quantity is no more then available quantity
                                    if (quantityi > item.Quantity) {
                                        itemcheck = true;
                                        JOptionPane.showMessageDialog(null, "Quantity is not available");
                                        break;
                                    }

                                    int subTotal = item.price * quantityi;
                                    Total = Total + subTotal;

                                    // updating the quantity of the item
                                    item.Quantity = item.Quantity - quantityi;

                                    saleLineItem saleLineitem = new saleLineItem(lineno, salesId, itemId,
                                            item.Description, quantityi, subTotal);
                                    saleLineItems.add(saleLineitem);

                                    icheck = false;
                                    break;
                                } else {
                                    icheck = true;

                                }
                            }
                            if (icheck == true && itemcheck == false) {
                                JOptionPane.showMessageDialog(null, "item not found");
                                continue;
                            }
                            break;
                        }

                        case 2: { // End Sale

                            if (Total == 0) {
                                JOptionPane.showMessageDialog(null, "No items added to the sale");
                                return;
                            }

                            boolean check = false;
                            for (int i = 0; i < customersS.size(); i++) {
                                customerdata customer1 = customersS.get(i);

                                if (customerid == customer1.CustomerId) {
                                    if (Total > customer1.SalesLimit) {
                                        JOptionPane.showMessageDialog(null, "Sale Limit exceeded buy within the limit");
                                        check = true;
                                        break;
                                    }
                                }
                            }
                            if (check == true) {
                                break;
                            }

                            String table = " Sales Id:" + salesId + "\n Customer Id: " + customerid + "\n Sales Date: "
                                    + date + "\n Name:" + customername;
                            table += "\n----------------------------------------------------------------------------------------------------------\n";
                            table += "LineNo    Item Id    Description    Quantity    Amount    \n";
                            table += "----------------------------------------------------------------------------------------------------------\n";

                            for (int i = 0; i < saleLineItems.size(); i++) {

                                if (salesId == saleLineItems.get(i).SalesId) {
                                    table += saleLineItems.get(i).LineNo + "             " + saleLineItems.get(i).ItemId
                                            + "               " + saleLineItems.get(i).Description + "             "
                                            + saleLineItems.get(i).Quantity + "             "
                                            + saleLineItems.get(i).Amount
                                            + "              \n";
                                }
                            }
                            table += "----------------------------------------------------------------------------------------------------------\n";
                            table += "                                     Total Sales: Rs." + Total;

                            table += "\n----------------------------------------------------------------------------------------------------------\n";
                            table += "Press any key to continue…\n";

                            saledata sale = new saledata(salesId, customerid, date, status);
                            sales.add(sale);

                            // set amount payable in customer to +Total
                            for (int i = 0; i < customersS.size(); i++) {
                                customerdata customer1 = customersS.get(i);
                                if (customerid == customer1.CustomerId) {
                                    customer1.AmountPayable += Total;
                                }
                            }
                            // addng recipt data to recipts arraylist
                            ArrayList<recipt> recipts = paymentsManager.receipts;
                            recipt recipt = new recipt(salesId, salesId, Total);
                            recipts.add(recipt);

                            JOptionPane.showMessageDialog(null, table);
                            JOptionPane.showMessageDialog(null, " Sale Performed Suceesfully");

                            return;
                        }
                        case 3:
                            // Remove an existing Item
                            removeExistingItem(salesId);
                            break;
                        case 4:
                            // Cancel Sale

                            cancelSale(salesId);
                            return;

                        default:
                            JOptionPane.showMessageDialog(null,
                                    "Invalid input. Please enter a number between 1 and 4.");
                            break;
                    }
                    ccheck = false;
                }

            } else {
                ccheck = true;

            }
        }
        if (ccheck == true) {
            JOptionPane.showMessageDialog(null, "Customer not found");
            return;
        }
    }

    public int initialmenu() {
        String input;
        int in;

        while (true) {
            input = JOptionPane.showInputDialog("Sales Date: " + LocalDate.now() + "\n Enter the customer ID");

            try {
                in = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
            }
        }

        return in;
    }

    public int newItemMenu() {
        String inputItemid;
        int itemId;

        while (true) {
            inputItemid = JOptionPane.showInputDialog(" Enter Item Id: ");

            try {
                itemId = Integer.parseInt(inputItemid);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
            }
        }

        return itemId;
    }

    public void removeExistingItem(int salesId) {
        ArrayList<itemData> itemsS = ItemManger.items;
        String input;
        int in = 0;

        while (true) {
            input = JOptionPane.showInputDialog("Enter the Item Id to remove");
            try {
                in = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
                return;
            }
        }
        boolean check = false;

        for (int i = 0; i < saleLineItems.size(); i++) {
            saleLineItem saleLineitem = saleLineItems.get(i);

            if (in == saleLineitem.ItemId && salesId == saleLineitem.SalesId) {
                saleLineItems.remove(i);
                // updating the quantity of the item
                for (int j = 0; j < itemsS.size(); j++) {
                    itemData item = itemsS.get(j);
                    if (in == item.itemid) {
                        item.Quantity = item.Quantity + saleLineitem.Quantity;
                    }
                }
                JOptionPane.showMessageDialog(null, "Item Removed successfully");
                Total = Total - saleLineitem.Amount;
                check = false;
                break;
            } else {
                check = true;
            }
        }

        if (check == true) {
            JOptionPane.showMessageDialog(null, "Item not found");
        }
    }

    public void cancelSale(int salesId) {
        ArrayList<itemData> itemsS = ItemManger.items;
        boolean removed = false;
        for (int i = saleLineItems.size() - 1; i >= 0; i--) {
            saleLineItem saleLineitem = saleLineItems.get(i);
            if (saleLineitem.SalesId == salesId) {
                // updating the quantity of the item
                for (int j = 0; j < itemsS.size(); j++) {
                    itemData item = itemsS.get(j);
                    if (saleLineitem.ItemId == item.itemid) {
                        item.Quantity += saleLineitem.Quantity;
                        break;
                    }
                }
                saleLineItems.remove(i);
                removed = true;
            }
        }
        if (removed) {
            JOptionPane.showMessageDialog(null, " Sale cancelled successfully of ID: " + salesId);
        } else {
            JOptionPane.showMessageDialog(null, " No sales found with ID " + salesId);
        }

    }

    public void saveSalesToFile() {
        try {
            FileWriter fw = new FileWriter("sales.txt");
            PrintWriter pw = new PrintWriter(fw);
            String Line;
            for (int i = 0; i < sales.size(); i++) {

                Line = sales.get(i).SalesId + ";" + sales.get(i).CustomerId + ";" + sales.get(i).Date + ";"
                        + sales.get(i).Status + ";";

                pw.println(Line);
            }
            pw.flush();
            pw.close();
            fw.close();
            System.out.println("Data saved to sales file successfully!");
        }

        catch (IOException ioEx) {
            System.out.println("An error occurred while saving data to file: " + ioEx.getMessage());
        }
    }

    public void loadSaleToFile() {

        try {
            FileReader fr = new FileReader("sales.txt");
            BufferedReader br = new BufferedReader(fr);

            String Line;

            while ((Line = br.readLine()) != null) {
                String[] data = Line.split(";");
                int salesId = Integer.parseInt(data[0]);
                int customerId = Integer.parseInt(data[1]);
                LocalDate date = LocalDate.parse(data[2]);
                String status = data[3];

                saledata sale = new saledata(salesId, customerId, date, status);
                sales.add(sale);
            }
            br.close();
            fr.close();
            System.out.println("Data loaded from sales file successfully!");
        } catch (IOException ioEx) {
            System.out.println("An error occurred while loading data from file: " + ioEx.getMessage());
        }
    }

    public void saveLineSaleToFile() {
        try {
            FileWriter fw = new FileWriter("saleLineItems.txt");
            PrintWriter pw = new PrintWriter(fw);
            String Line;
            for (int i = 0; i < saleLineItems.size(); i++) {

                Line = saleLineItems.get(i).LineNo + ";" + saleLineItems.get(i).SalesId + ";"
                        + saleLineItems.get(i).ItemId + ";" + saleLineItems.get(i).Description + ";"
                        + saleLineItems.get(i).Quantity + ";" + saleLineItems.get(i).Amount;

                pw.println(Line);
            }
            pw.flush();
            pw.close();
            fw.close();
            System.out.println("Data saved to saleLineItems file successfully!");
        }

        catch (IOException ioEx) {
            System.out.println("An error occurred while saving data to file: " + ioEx.getMessage());
        }
    }

    public void loadSaleLineToFile() {

        try {
            FileReader fr = new FileReader("saleLineItems.txt");
            BufferedReader br = new BufferedReader(fr);

            String Line;

            while ((Line = br.readLine()) != null) {
                String[] data = Line.split(";");
                int lineNo = Integer.parseInt(data[0]);
                int salesId = Integer.parseInt(data[1]);
                int itemId = Integer.parseInt(data[2]);
                String description = data[3];
                int quantity = Integer.parseInt(data[4]);
                int amount = Integer.parseInt(data[5]);

                saleLineItem saleLineitem = new saleLineItem(lineNo, salesId, itemId, description, quantity, amount);
                saleLineItems.add(saleLineitem);
            }
            br.close();
            fr.close();
            System.out.println("Data loaded from saleLineItems file successfully!");
        } catch (IOException ioEx) {
            System.out.println("An error occurred while loading data from file: " + ioEx.getMessage());
        }
    }

}
