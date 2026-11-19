package foodshare.donations;

/*
 * FoodShare: UN SDG 2 (Zero Hunger)
 * Author: Suzanne Soares, x25130137
 * Purpose: food with a long shelf life, such as tins, pasta or rice
 */
public class NonPerishableItem extends FoodItem{ //inherits everything from FoodItem
    //attributes only non-perishable items have
    private String bestBeforeDate; 
    private String packagingType;

    public NonPerishableItem(int itemId, String name, String category, int quantity, String unit,
                             String dateDonated, Donor donor, String bestBeforeDate, String packagingType) {
        super(itemId, name, category, quantity, unit, dateDonated, donor); //super clls the constructor to fill in the shared part
        this.bestBeforeDate = bestBeforeDate; //then fills in its own part
        this.packagingType = packagingType;
    }

    public NonPerishableItem() {
        super();
        this.bestBeforeDate = "";
        this.packagingType = "";
    }

    
    //setters
    public void setBestBeforeDate(String bestBeforeDate) { 
        this.bestBeforeDate = bestBeforeDate; 
    }
    public void setPackagingType(String packagingType) { 
        this.packagingType = packagingType; 
    }
    
    
    //getters
    public String getBestBeforeDate() { 
        return bestBeforeDate; 
    }
    public String getPackagingType() { 
        return packagingType; 
    }

    @Override //replace the parent's abstract method
    public String getStorageInfo() {
        return "Store in a dry cupboard (" + packagingType + "), best before " + bestBeforeDate;
    }

    @Override //reuses the parent's text and adds its own
    public String printDetails() {
        return super.printDetails() + "\n   Non-perishable. " + getStorageInfo();
    }
}
