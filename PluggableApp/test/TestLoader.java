
import java.util.logging.Level;
import java.util.logging.Logger;
import plug.PluginLoader;
import plugin.Plugin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Secretaria1
 */
public class TestLoader {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Class c = PluginLoader.open("E:\\projects\\WebPlugin\\test\\HelloMessagePlugin.jar", "HelloMessagePlugin");
        System.out.println("class: " + c.getSimpleName());
        try {
            Object obj = c.newInstance();
            System.out.println("d: " + obj.toString());
        } catch (InstantiationException ex) {
            Logger.getLogger(TestLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TestLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
