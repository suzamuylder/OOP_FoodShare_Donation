/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodshare.donations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * FoodShare: UN SDG 2 (Zero Hunger)
 * Author: Suzanne Soares, x25130137
 * Represents a household or shop that donates food
 */
public class DonationManager {
    
    private ArrayList<FoodItem> items; //list of food items - holds both child types
    private ArrayList<Donor> donors; //list of donors
    private String fileName;
    
    public DonationManager(){
        items = new ArrayList<>();
        donors = new ArrayList<>();
        fileName = "donations.dat";
        loadFromFile();  //when the section opens, it brings back the saved data
    }
    
    
    //ADD
    //adds the object to the list and saves the file, so nothing is lost if the program closes
    public void addItem(FoodItem item){
        items.add(item);
        saveToFile();
    }
    
    public void addDonor(Donor donor){
        donors.add(donor);
        saveToFile();
    }
    
    public ArrayList<Donor> getDonors(){
        return donors;
    }
    
     
    //Saving the id number, take the highest number and add 1
    public int getNextItemId(){
        int max = 0;
        for(FoodItem item : items){
            if(item.getItemId() > max){
                max = item.getItemId();
            }
        }
        return max + 1;
    }

    public int getNextDonorId(){
        int max = 0;
        for(Donor d : donors){
            if(d.getDonorId() > max){
                max = d.getDonorId();
            }
        }
        return max + 1;
    }
    
    
    //DISPLAY
    //Polymorphism
    //the variable has type FoodItem, but Java runs the version of
    //printDetails() that belongs to each object's REAL class.
    public String displayAll(){
        if(items.isEmpty()){
            return "No donations recorded yet.";
        }
        String text = "";
        for(FoodItem item : items){
            text += item.printDetails() + "\n\n";
        }
        return text;
    }
    
    
    //SEARCH
    //by name, returns one item
    //by cathegory, returns a list
    //because several items can sahre a category
    public FoodItem searchByName(String name){
        for(FoodItem item : items){
            if(item.getName().equalsIgnoreCase(name)){
                return item;
            }
        }
        return null;  //if not found
    }

    public ArrayList<FoodItem> searchByCategory(String category){
        ArrayList<FoodItem> results = new ArrayList<>();
        for(FoodItem item : items){
            if(item.getCategory().equalsIgnoreCase(category)){
                results.add(item);
            }
        }
        return results;
    }

    //Only perishable items can expire, so instanceof checks the real type
    public ArrayList<FoodItem> getExpiringSoon() {
        ArrayList<FoodItem> results = new ArrayList<>();
        for(FoodItem item : items){
            if(item instanceof PerishableItem){
                PerishableItem p = (PerishableItem) item;
                if(p.isExpiringSoon()){
                    results.add(p);
                }
            }
        }
        return results;
    }
    
    
    //DELETE
    //searches by ID, if found, removes it, saves and returns true
    public boolean deleteItem(int itemId){
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).getItemId() == itemId){
                items.remove(i);
                saveToFile();
                return true;
            }
        }
        return false;  //if there is no item with that ID
    }
    
    
    //FILE
    public void saveToFile(){
        //closes the file automatically at the end, even if an error occurs
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))){
            out.writeObject(items); //writes the whole list at once
            out.writeObject(donors); 
        }catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(){
        File file = new File(fileName);
        if(!file.exists()){ //first run - no file yet
            return;  
        }
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
            items = (ArrayList<FoodItem>) in.readObject();
            donors = (ArrayList<Donor>) in.readObject();
        }catch(IOException | ClassNotFoundException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}
