/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import plug.PluginLoader;

/**
 *
 * @author e0261
 */
@ManagedBean
public class PluginBean {
    
    private Object plugin;

    public PluginBean() {
        
    }
    
    @PostConstruct
    public void init() {
        try {
            JarFile jarFile = new JarFile("E:\\projects\\WebPlugin\\PluggableApp\\web\\WEB-INF\\lib\\HelloMessagePlugin.jar");
            PluginLoader.copyResourcesToDirectory(jarFile, "hello.xhtml", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/"));
            System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/"));
        } catch (IOException ex) {
            Logger.getLogger(PluginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Object getByName(String name) {
        Class c = PluginLoader.open("E:\\projects\\WebPlugin\\PluggableApp\\web\\WEB-INF\\lib\\HelloMessagePlugin.jar", name);
        Object obj = null;
        try {
            obj = c.newInstance();
            System.out.println("d: " + obj.toString());
        } catch (InstantiationException ex) {
            Logger.getLogger(PluginBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PluginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj;
    }

    public Object getPlugin() {
        return plugin;
    }

    public void setPlugin(Object plugin) {
        this.plugin = plugin;
    }
}
