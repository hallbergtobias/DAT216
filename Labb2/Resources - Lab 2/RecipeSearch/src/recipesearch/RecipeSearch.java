/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package recipesearch;

public class RecipeSearch {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Starting RecipeSearch");
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RecipeSearchView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        new RecipeSearchView().setVisible(true);
        
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                //This code will run before shutdown
                System.out.println("Closing RecipeSearch");
            }
        }));
    }
}
