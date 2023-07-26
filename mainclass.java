import javax.swing.*;

public class mainclass {

    public static void main(String args[]) {

        ItemManger itemManger = new ItemManger();
        customerManager customerManager = new customerManager();
        saleManager saleManager = new saleManager();
        reportManager reportManager = new reportManager();
        paymentsManager paymentManager = new paymentsManager();

        itemManger.loadItemsFromFile();
        customerManager.loaadCustomerToFiles();
        saleManager.loadSaleToFile();
        saleManager.loadSaleLineToFile();
        paymentManager.loadFromFile();

        boolean check = false;
        int c;
        String input;

        while (true) {
            input = JOptionPane.showInputDialog(" Enter 1 to Manage Items " + "\n Enter 2 to Manage Customer  "
                    + " \n Enter 3 to Make New Sale " + " \n Enter 4 to Make Payments "
                    + " \n Enter 5 to Print Reports " + " \n Enter 6 to Exit ");

            try {
                c = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 1 and 6.");
                continue; // restart the loop
            }

            switch (c) {
                // Items Menu
                case 1:
                    itemManger.ItemsMenu();
                    break;
                // Customer Menu
                case 2:
                    customerManager.CustomerMenu();
                    break;
                case 3:
                    saleManager.saleMenu();
                    break;
                case 4:
                    paymentManager.makepayment();
                    break;
                // Reports Menu
                case 5:
                    reportManager.ReportsMenu();
                    break;
                case 6:
                    check = true;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 1 and 6.");
                    break;
            }

            if (check == true) {
                itemManger.saveItemsToFile();
                customerManager.saveCustomerToFiles();
                saleManager.saveSalesToFile();
                saleManager.saveLineSaleToFile();
                paymentManager.saveToFile();

                break;
            }
        }

    }
}
