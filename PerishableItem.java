package foodshare.donations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/*
 * FoodShare: UN SDG 2 (Zero Hunger)
 * Author: Suzanne Soares, x25130137
 * Purpose: food that expires quickly, such as dairy, bread or vegetables
 */
public class PerishableItem extends FoodItem {

    private String expiryDate;   // format dd/MM/yyyy
    private boolean needsFridge;

    public PerishableItem(int itemId, String name, String category, int quantity, String unit,
                          String dateDonated, Donor donor, String expiryDate, boolean needsFridge) {
        super(itemId, name, category, quantity, unit, dateDonated, donor);  // parent constructor
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

    //True if the item expires within the next two days or has already expired
    public boolean isExpiringSoon() {
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate expiry = LocalDate.parse(expiryDate, format);
            return !expiry.isAfter(LocalDate.now().plusDays(2));
        } catch (DateTimeParseException e) {
            return false;  // date typed in the wrong format
        }
    }

    @Override
    public String getStorageInfo() {
        if (needsFridge) {
            return "Keep refrigerated, use by " + expiryDate;
        }
        return "Store in a cool place, use by " + expiryDate;
    }

    @Override
    public String printDetails() {
        String text = super.printDetails() + "\n   Perishable. " + getStorageInfo();
        if (isExpiringSoon()) {
            text += "   ** EXPIRING SOON **";
        }
        return text;
    }
}
