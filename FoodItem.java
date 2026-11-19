package foodshare.donations;

import java.io.Serializable;

/*
 * FoodShare: UN SDG 2 (Zero Hunger)
 * Author: Suzanne Soares, x25130137
 * Attributes are protected so the child classes can use them directly.
 */
public abstract class FoodItem implements Serializable {
    //Parent class
    //Abstract because you can't create a new FoodItem()
    //Only the child classes are created
    protected int itemId; //protected so the child class can have access
    protected String name;
    protected String category;
    protected int quantity;
    protected String unit;
    protected String dateDonated;
    protected Donor donor; //each item stores WHO donated it

    protected FoodItem(int itemId, String name, String category, int quantity,
                       String unit, String dateDonated, Donor donor) {
        this.itemId = itemId;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.unit = unit;
        this.dateDonated = dateDonated;
        this.donor = donor;
    }

    protected FoodItem() {
        this(0, "", "", 0, "", "", null);
    }
    
    
    //setters
    public void setItemId(int itemId) { 
        this.itemId = itemId; 
    }
    public void setName(String name) { 
        this.name = name; 
    }
    public void setCategory(String category) { 
        this.category = category; 
    }
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
    }
    public void setUnit(String unit) { 
        this.unit = unit; 
    }
    public void setDateDonated(String dateDonated) { 
        this.dateDonated = dateDonated; 
    }
    public void setDonor(Donor donor) { 
        this.donor = donor; 
    }
    
    
    //getters
    public int getItemId() { 
        return itemId; 
    }
    public String getName() { 
        return name; 
    }
    public String getCategory() { 
        return category; 
    }
    public int getQuantity() { 
        return quantity; 
    }
    public String getUnit() { 
        return unit; 
    }
    public String getDateDonated() { 
        return dateDonated; 
    }
    public Donor getDonor() { 
        return donor; 
    }

    //details shared by every food item
    public String printDetails() {
        String donorName = (donor != null) ? donor.getName() : "Unknown";
        return "ID " + itemId + ": " + name + " | " + category + " | "
                + quantity + " " + unit + " | donated " + dateDonated + " by " + donorName;
    }

    //Abstract: each child class must say how its food should be stored
    public abstract String getStorageInfo();
}
