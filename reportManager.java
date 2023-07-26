
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class reportManager {

    private int reamaningAmount = 0;
    private int total = 0;
    
    public void stockInHand() {
        ArrayList<itemData> itemsR = ItemManger.items;

        String frominput, toinput;
        int fIdItem, tIdItem;

        frominput = JOptionPane.showInputDialog("Enter the Item Id range from 1 to " + itemsR.size() + "\n From:");
        toinput = JOptionPane.showInputDialog("To: ");

        try {
            tIdItem = Integer.parseInt(toinput);
            fIdItem = Integer.parseInt(frominput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: ID must be a number.");
            return;
        }

        if (fIdItem > itemsR.size() || tIdItem > itemsR.size() || fIdItem < 1 || tIdItem < 1 || fIdItem > tIdItem) {
            JOptionPane.showMessageDialog(null, "Invalid Item Id");
            return;
        }

        for (int i = 0; i < itemsR.size(); i++) {
            if (fIdItem == itemsR.get(i).itemid) {

                for (int j = fIdItem - 1; j < tIdItem; j++) {
                    String table = "\n----------------------------------------------------------------------------------------------------------\n";
                    table += "Item ID: " + itemsR.get(j).itemid + "\n";
                    table += "Description: " + itemsR.get(j).Description + "\n";
                    table += "Price: " + itemsR.get(j).price + "\n";
                    table += "Quantity: " + itemsR.get(j).Quantity + "\n";
                    table += "Creation Date: " + itemsR.get(j).CreationDate + "\n";
                    table += "----------------------------------------------------------------------------------------------------------\n";

                    JOptionPane.showMessageDialog(null, table);
                }

            }
        }
    }

    public void customerBalance() {
        ArrayList<customerdata> customersR = customerManager.customers;
        String input;
        int IdCustomer;

        try {
            input = JOptionPane.showInputDialog("Enter the Customer Id ");
            IdCustomer = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a numeric value for the customer ID.");
            return;
        }

        boolean found = false;

        for (int i = 0; i < customersR.size(); i++) {
            if (IdCustomer == customersR.get(i).CustomerId) {
                JOptionPane.showMessageDialog(null,
                        " Customer ID: " + IdCustomer + "\n Customer Name: " + customersR.get(i).Name + "\n Address: "
                                + customersR.get(i).Address + "\n Phone: " + customersR.get(i).Phone + "\n Email: "
                                + customersR.get(i).Email + "\n Balance: Rs. " + customersR.get(i).AmountPayable);
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "Customer not found");
        }
    }

    public void salesReport() {
        String startDateStr = "";
        String endDateStr = "";
        total = 0;
        // Prompt user for start date
        startDateStr = JOptionPane.showInputDialog(null, "Enter start date (yyyy/MM/dd):");
        // Prompt user for end date
        endDateStr = JOptionPane.showInputDialog(null, "Enter end date (yyyy/MM/dd):");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate startDate = null;
        LocalDate endDate = null;
        try {
            if (startDateStr != null && !startDateStr.trim().isEmpty()) {
                startDate = LocalDate.parse(startDateStr, formatter);
            }
            if (endDateStr != null && !endDateStr.trim().isEmpty()) {
                endDate = LocalDate.parse(endDateStr, formatter);
            }
            // Throw an exception if both start and end dates are not provided
            if (startDate == null && endDate == null) {

                throw new Exception("Both start and end dates must be provided");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error: Invalid date format.");
            return;
        }
        // If either start or end date is blank, generate report for a single day
        if (startDateStr.trim().isEmpty() || endDateStr.trim().isEmpty()) {
            if (startDateStr.trim().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Generating report for Date: \n" + endDateStr);
                boolean salesFound = findingSalesData(endDate);
                JOptionPane.showMessageDialog(null, "Total Sales: Rs." + total);
                if (!salesFound) {
                    JOptionPane.showMessageDialog(null, " No more sales found for the specified period.");
                    return;
                }

            } else {

                JOptionPane.showMessageDialog(null, "Generating report for Date: \n" + startDateStr);
                boolean salesFound = findingSalesData(startDate);
                JOptionPane.showMessageDialog(null, "Total Sales: Rs." + total);
                if (!salesFound) {
                    JOptionPane.showMessageDialog(null, " No more sales found for the specified period.");
                    return;
                }
            }
        } else {
            // Generate report for the specified period
            JOptionPane.showMessageDialog(null,
                    "Generating report for the period: \n" + startDateStr + " to " + endDateStr);
            LocalDate temp = startDate;
            boolean salesFound = false;
            for (;;) {
                if (temp.equals(endDate)) {
                    salesFound = findingSalesData(temp);
                    break;
                }
                salesFound = findingSalesData(temp);
                temp = temp.plusDays(1);
            }
            JOptionPane.showMessageDialog(null, "Total Sales: Rs." + total);
            if (!salesFound) {
                JOptionPane.showMessageDialog(null, "No more sales found for the specified period.");
                return;
            }

        }
    }

    public boolean findingSalesData(LocalDate date) {

        ArrayList<saledata> salesR = saleManager.sales;
        ArrayList<saleLineItem> saleLineItemsR = saleManager.saleLineItems;
        boolean salesFound = false;
        
        // find sale id for the date
        for (int i = 0; i < salesR.size(); i++) {
            if (date.equals(salesR.get(i).Date)) {
                int salesId = salesR.get(i).SalesId;

                // find data in salelineitem of salesId
                for (int j = 0; j < saleLineItemsR.size(); j++) {
                    if (salesId == saleLineItemsR.get(j).SalesId) {
                        // finding item data in salineitem of sale id

                        String table = " Sale Date: " + date
                                + "\n----------------------------------------------------------------------------------------------------------\n";

                        table += " Item Id: " + saleLineItemsR.get(j).ItemId + " \n Description: "
                                + saleLineItemsR.get(j).Description + "\n Quantity: "
                                + saleLineItemsR.get(j).Quantity + "\n Price: Rs." + saleLineItemsR.get(j).Amount;
                        table += "\n----------------------------------------------------------------------------------------------------------\n";

                        JOptionPane.showMessageDialog(null, table);
                        total = total + saleLineItemsR.get(j).Amount;
                        salesFound = true;
                    }
                }
            } else {
                salesFound = false;
            }

        }
        return salesFound;
    }

    public void outstandingSales() {

        String startDateStr = "";
        String endDateStr = "";
        reamaningAmount = 0;

        // Prompt user for start date
        startDateStr = JOptionPane.showInputDialog(null, "Enter start date (yyyy/MM/dd):");
        // Prompt user for end date
        endDateStr = JOptionPane.showInputDialog(null, "Enter end date (yyyy/MM/dd):");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate startDate = null;
        LocalDate endDate = null;
        try {
            if (startDateStr != null && !startDateStr.trim().isEmpty()) {
                startDate = LocalDate.parse(startDateStr, formatter);
            }
            if (endDateStr != null && !endDateStr.trim().isEmpty()) {
                endDate = LocalDate.parse(endDateStr, formatter);
            }
            // Throw an exception if both start and end dates are not provided
            if (startDate == null && endDate == null) {

                throw new Exception("Both start and end dates must be provided");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid date format");
            return;
        }
        if (startDateStr.trim().isEmpty() || endDateStr.trim().isEmpty()) {
            if (startDateStr.trim().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Generating report for " + endDateStr);
                boolean salesFound = findOutsatndingSalesData(endDate);
                JOptionPane.showMessageDialog(null, "Total Remaining Amount: Rs." + reamaningAmount);
                if (!salesFound) {
                    JOptionPane.showMessageDialog(null, "No more sales found for the specified period.");
                    return;
                }

            } else {

                JOptionPane.showMessageDialog(null, "Generating report for " + startDateStr);
                boolean salesFound = findOutsatndingSalesData(startDate);
                JOptionPane.showMessageDialog(null, "Total Remaining Amount: Rs." + reamaningAmount);
                if (!salesFound) {
                    JOptionPane.showMessageDialog(null, "No more sales found for the specified period.");
                    return;
                }
            }
        } else {
            // Generate report for the specified period
            JOptionPane.showMessageDialog(null,
                    "Generating report for the period: \n" + startDateStr + " to " + endDateStr);
            LocalDate temp = startDate;
            boolean salesFound = false;
            for (;;) {
                if (temp.equals(endDate)) {
                    salesFound = findOutsatndingSalesData(temp);
                    break;
                }
                salesFound = findOutsatndingSalesData(temp);
                temp = temp.plusDays(1);
            }
            JOptionPane.showMessageDialog(null, "Total Remaning Amount: Rs." + reamaningAmount);
            if (!salesFound) {
                JOptionPane.showMessageDialog(null, "No more sales found for the specified period.");
                return;
            }

        }

    }

    public boolean findOutsatndingSalesData(LocalDate date) {
        ArrayList<saledata> salesR = saleManager.sales;
        ArrayList<recipt> recipts = paymentsManager.receipts;
        ArrayList<customerdata> customersR = customerManager.customers;
        boolean salesFound = false;
        // finding customer id from salesid in saledata class
        for (int i = 0; i < salesR.size(); i++) {
            if (date.equals(salesR.get(i).Date)) {
                int salesId = salesR.get(i).SalesId;
                int customerId = salesR.get(i).CustomerId;
                int totalSale = 0;

                // finding customer data from customer id in customerdata
                for (int j = 0; j < customersR.size(); j++) {
                    if (customerId == customersR.get(j).CustomerId) {

                        for (int k = 0; k < recipts.size(); k++) {
                            if (salesId == recipts.get(k).SalesID) {
                                totalSale = recipts.get(k).TotalSale;
                            }
                        }
                        String table = " Sale Date: " + date
                                + "\n----------------------------------------------------------------------------------------------------------\n";
                        table += " Sales Id: " + salesId + "\n Customer Name: " + customersR.get(j).Name
                                + "\n Total Sale Amount: Rs." + totalSale + "\n Remaning Amount: Rs. "
                                + customersR.get(j).AmountPayable;
                        table += "\n----------------------------------------------------------------------------------------------------------\n";
                        JOptionPane.showMessageDialog(null, table);

                        reamaningAmount = reamaningAmount + customersR.get(j).AmountPayable;
                    }
                    salesFound = true;

                }

            } else {
                salesFound = false;
            }

        }
        return salesFound;
    }

    public void ReportsMenu() {
        String inputreports;
        int rc;
        for (;;) {
            inputreports = JOptionPane.showInputDialog(
                    " Enter 1 for Stock in hand" + "\n Enter 2 for Customer Balance" + "\n Enter 3 for Sales Report"
                            + "\n Enter 4 for Outstanding Sales" + " \n Enter 5 to go Back to Main Menu");

            try {
                rc = Integer.parseInt(inputreports);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 1 and 5.");
                continue; // restart the loop
            }
            switch (rc) {
                case 1:
                    // stockInHand();
                    stockInHand();
                    break;

                case 2:
                    // customer balance
                    customerBalance();
                    break;
                case 3:
                    // sales report
                    salesReport();
                    break;
                case 4:
                    // outstanding sales
                    outstandingSales();
                    break;
                case 5:
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 1 and 5.");
                    break;
            }
        }

    }

}
