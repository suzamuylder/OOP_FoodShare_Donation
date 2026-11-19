package foodshare.donations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/*
 * FoodShare: UN SDG 2 (Zero Hunger)
 * Author: Suzanne Soares, x25130137
 * Purpose: food that expires quickly, such as dairy, bread or vegetables
 */
public class PerishableItem extends FoodItem{ //inherits everything from FoodItem

    private String expiryDate;   //attributes only perishable items have
    private boolean needsFridge;

    public PerishableItem(int itemId, String name, String category, int quantity, String unit,
                          String dateDonated, Donor donor, String expiryDate, boolean needsFridge) {
        super(itemId, name, category, quantity, unit, dateDonated, donor);  //super calls the parent constructor to fill shared part
        this.expiryDate = expiryDate;
        this.needsFridge = needsFridge;
    }

    public PerishableItem() {
        super();
        this.expiryDate = "";
        this.needsFridge = false;
    }
    
    //setters
    public void setExpiryDate(String expiryDate) { 
        this.expiryDate = expiryDate; 
    }
    public void setNeedsFridge(boolean needsFridge) { 
        this.needsFridge = needsFridge; 
    }
    
    //getters
    public String getExpiryDate() { 
        return expiryDate; 
    }
    public boolean getNeedsFridge() { 
        return needsFridge; 
    }

    //Converts the text date text into a LocalDate and compares it with today +2 days
    public boolean isExpiringSoon() {
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate expiry = LocalDate.parse(expiryDate, format);
            return !expiry.isAfter(LocalDate.now().plusDays(2));
            //"not after two days from now" = expires within 2 days or has already expired
        } catch (DateTimeParseException e) {
            return false;  //if the date was typed wrongly, the program does not crash
        }
    }

    @Override //replaces the parent's abstract method
    public String getStorageInfo() {
        if (needsFridge) {
            return "Keep refrigerated, use by " + expiryDate;
        }
        return "Store in a cool place, use by " + expiryDate;
    }

    @Override //reuses the parent's text and adds its own
    public String printDetails() {
        String text = super.printDetails() + "\n   Perishable. " + getStorageInfo();
        if (isExpiringSoon()) {
            text += "   ** EXPIRING SOON **";
        }
        return text;
    }
}
