/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recipesearch;


import java.util.Iterator;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    private JList searchList;
    private List<Recipe> recipes;
    
    private JLabel recipeNameLabel;
    
    
    public RecipeSearchController(JComboBox ingredientDropDownList, 
            JComboBox kitchenDropDownList, JComboBox levelDropDownList, 
            JSlider priceSlider, JSpinner timeSpinner, JList searchList, 
            JLabel recipeNameLabel){
        this.ingredientDropDownList = ingredientDropDownList;
        this.kitchenDropDownList = kitchenDropDownList;
        this.levelDropDownList = levelDropDownList;
        this.priceSlider = priceSlider;
        this.timeSpinner = timeSpinner;
        this.searchList = searchList;
        this.recipeNameLabel = recipeNameLabel;
        
        
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
        
        
        this.db = RecipeDatabase.getSharedInstance();
        
        recipes = db.search(new SearchFilter(level,maxTime,kitchen,maxPrice,mainIngredient));
        Iterator<Recipe> iter = recipes.iterator();
        
        DefaultListModel listModel = new DefaultListModel();
        searchList.setModel(listModel);
        listModel.clear();
        
        searchList.addListSelectionListener(new MyListSelectionListener());
        
        while(iter.hasNext()){
            Recipe recipe = iter.next();
            listModel.addElement(recipe.getName() + " - " + 
                    recipe.getTime() + "min - " + recipe.getPrice() + "kr");
        }
    }
    
    class MyListSelectionListener  implements ListSelectionListener{
        
        public void valueChanged(ListSelectionEvent e){
            
            JList listan = (JList) e.getSource();
            int i = listan.getSelectedIndex();
           try { 
                Recipe r = recipes.get(i);
                System.out.println(r.getName());
                recipeNameLabel.setText(r.getName());
               
                
           } catch (Exception ex) {
               System.out.println("fel! ");
           }
            
            
        }
    }
    
    
    
}
