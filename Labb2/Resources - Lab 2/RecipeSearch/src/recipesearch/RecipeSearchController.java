/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recipesearch;


import java.util.Iterator;
import java.util.List;
import javax.swing.*;
import se.chalmers.ait.dat215.lab2.*;

/**
 *
 * @author Tobias
 */
public class RecipeSearchController {
    private JComboBox ingredientDropDownList;
    private JComboBox kitchenDropDownList;
    private JComboBox levelDropDownList;
    private JSlider priceSlider;
    private JButton searchButton;
    private JSpinner timeSpinner;
    private RecipeDatabase db;
    private JList sourceList;
    
    
    public RecipeSearchController(JComboBox ingredientDropDownList, 
            JComboBox kitchenDropDownList, JComboBox levelDropDownList, 
            JSlider priceSlider, JSpinner timeSpinner, JList sourceList){
        this.ingredientDropDownList = ingredientDropDownList;
        this.kitchenDropDownList = kitchenDropDownList;
        this.levelDropDownList = levelDropDownList;
        this.priceSlider = priceSlider;
        this.timeSpinner = timeSpinner;
        this.sourceList = sourceList;
        
        
    }
    
    public void init(){
    }
    
    public void searchRecipe(){
        
        String mainIngredient = (String) ingredientDropDownList.getSelectedItem();
        String kitchen = (String) kitchenDropDownList.getSelectedItem();
        String level = (String) levelDropDownList.getSelectedItem();
        
        int maxPrice = priceSlider.getValue();
        int maxTime = (Integer) timeSpinner.getValue();
        System.out.println("## New Search ##\nmain ingredient: " + mainIngredient + "\nkithen: " + kitchen 
                + "\nlevel: " + level + "\nmaxPrice: " + maxPrice + "\nmaxTime: " + maxTime);
        
        db = RecipeDatabase.getSharedInstance();
        
        List<Recipe> recipes = db.search(new SearchFilter(level,maxTime,kitchen,maxPrice,mainIngredient));
        Iterator<Recipe> iter = recipes.iterator();
        
        DefaultListModel listModel = new DefaultListModel();
        sourceList.setModel(listModel);
        
        while(iter.hasNext()){
            Recipe recipe = iter.next();
            listModel.addElement(recipe.getName() + " - " + 
                    recipe.getTime() + "min - " + recipe.getPrice() + "kr");
        }

        
    
    }
    
    
}
