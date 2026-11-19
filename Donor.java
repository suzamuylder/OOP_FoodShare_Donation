package foodshare.donations;

import java.io.Serializable;

/*
 * FoodShare: UN SDG 2 (Zero Hunger)
 * Author: Suzanne Soares, x25130137
 */
public class Donor implements Serializable {

    private int donorId;
    private String name;
    private String donorType;
    private String phone;
    private String email;
    
    //constructor with parameters - creates a donor with all its data
    public Donor(int donorId, String name, String donorType, String phone, String email) {
        this.donorId = donorId;
        this.name = name;
        this.donorType = donorType;
        this.phone = phone;
        this.email = email;
    }
    
    //empty constructor: calls the other constructor with default values
    public Donor() {
        this(0, "", "", "", "");
    }
    
    
    //setters
    public void setDonorId(int donorId){ 
        this.donorId = donorId; 
    }
    public void setName(String name){ 
        this.name = name; 
    }
    public void setDonorType(String donorType){ 
        this.donorType = donorType; 
    }
    public void setPhone(String phone){ 
        this.phone = phone; 
    }
    public void setEmail(String email){ 
        this.email = email; 
    }
    
    
    //getters
    public int getDonorId(){ 
        return donorId; 
    }
    public String getName(){ 
        return name; 
    }
    public String getDonorType(){ 
        return donorType; 
    }
    public String getPhone(){ 
        return phone; 
    }
    public String getEmail(){ 
        return email; 
    }
    
    //display
    public String printDetails(){
        return "Donor " + donorId + ": " + name + " (" + donorType + "), " + phone + ", " + email;
    }
}
