/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recipesearch;


import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
    private JSlider timeSlider;
    private RecipeDatabase db;
    private JList searchList;
    private List<Recipe> recipes;
    
    private JLabel recipeNameLabel;
    private JTextArea descriptionText;
    private JLabel timeLabel;
    private JLabel portionLabel;
    private JTextArea ingredientText;
    private JLabel recipeImage;
    private DefaultListModel listModel;
    
    
    public RecipeSearchController(JComboBox ingredientDropDownList, 
            JComboBox kitchenDropDownList, JComboBox levelDropDownList, 
            JSlider priceSlider, JSlider timeSlider, JList searchList, 
            JLabel recipeNameLabel, JLabel timeLabel, JLabel portionLabel, JTextArea descriptionText,
    JTextArea ingredientText, JLabel recipeImage){
        this.ingredientDropDownList = ingredientDropDownList;
        this.kitchenDropDownList = kitchenDropDownList;
        this.levelDropDownList = levelDropDownList;
        this.priceSlider = priceSlider;
        this.timeSlider = timeSlider;
        this.searchList = searchList;
        this.recipeNameLabel = recipeNameLabel;
        this.timeLabel = timeLabel;
        this.portionLabel = portionLabel;
        this.descriptionText = descriptionText;
        this.ingredientText = ingredientText;
        this.recipeImage = recipeImage;
        
        init();
    }
    
    public void init(){
        listModel = new DefaultListModel();
        searchList.setModel(listModel);
    }
    
    public String validateParam(JComboBox b){
        String s = (String) b.getSelectedItem();
        if (s.equals("Alla")){
            return null;
        } else {
            return s;
        }
    }
    
    public void searchRecipe(){
        String mainIngredient = validateParam(ingredientDropDownList);
        String kitchen = validateParam(kitchenDropDownList);
        String level = validateParam(levelDropDownList);
        int maxPrice = priceSlider.getValue();
        int maxTime = (Integer) timeSlider.getValue();
        
        System.out.println("## New Search ##\nmain ingredient: " + mainIngredient + "\nkithen: " + kitchen 
                + "\nlevel: " + level + "\nmaxPrice: " + maxPrice + "\nmaxTime: " + maxTime);
        
        this.db = RecipeDatabase.getSharedInstance();
        recipes = db.search(new SearchFilter(level,maxTime,kitchen,maxPrice,mainIngredient));
        presentSearchResult(recipes);   
    }
    
   
    
    
    public void presentSearchResult(List<Recipe> recipes){
        
        Iterator<Recipe> iter = recipes.iterator();
        
        listModel.clear();
        
        searchList.addListSelectionListener(new MyListSelectionListener());
        
        while(iter.hasNext()){
            Recipe recipe = iter.next();
            listModel.addElement(recipe.getName() + "\t\t" + 
                    recipe.getTime() + "min \t" + recipe.getPrice() + "kr");
        }
        
        searchList.setSelectedIndex(0);
    
    }
    
    public void presentRecipe(Recipe r){
        
        System.out.println(r.getName());
        recipeNameLabel.setText(r.getName());
        timeLabel.setText(Integer.toString(r.getTime()) + " min");
        portionLabel.setText(Integer.toString(r.getServings()) + " portioner");
        descriptionText.setText(r.getDescription());
        ImageIcon pic = r.getImage(recipeImage.getWidth(), recipeImage.getHeight());
        recipeImage.setIcon(pic);

        List<Ingredient> ingredients = r.getIngredients();
        Iterator<Ingredient> iter = ingredients.iterator();

        ingredientText.setText("");
        while(iter.hasNext()){
            Ingredient ingredient = iter.next();
            ingredientText.setText(ingredientText.getText() + ingredient.getName() + "\t" + 
                    Integer.toString(ingredient.getAmount()) + " " + ingredient.getUnit() + "\n");
        }
    }
    
    class MyListSelectionListener  implements ListSelectionListener{
        public void valueChanged(ListSelectionEvent e){
            JList listan = (JList) e.getSource();
            int i = listan.getSelectedIndex();
            Recipe r = recipes.get(i);
            presentRecipe(r);
        }
    }
}
