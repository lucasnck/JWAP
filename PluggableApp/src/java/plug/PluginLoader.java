/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plug;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Secretaria1
 */
public class PluginLoader {

    public static Class open(String path, String name) {
        Class c = null;
        try {
            JarFile jarFile = new JarFile(path);
            Enumeration<JarEntry> e = jarFile.entries();
            URL[] urls = {new URL("jar:file:" + path + "!/")};
            URLClassLoader cl = URLClassLoader.newInstance(urls);
            while (e.hasMoreElements()) {
                JarEntry je = e.nextElement();
                System.out.println("name: " + je.getName());
                if (je.isDirectory() || !je.getName().endsWith(".class")) {
                    continue;
                }
                // -6 because of .class
                String className = je.getName().substring(0, je.getName().length() - 6);
                className = className.replace('/', '.');
                c = cl.loadClass(name);
                System.out.println("c: " + className);
            }
        } catch (IOException ex) {
            Logger.getLogger(PluginLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PluginLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    public static void copyResourcesToDirectory(JarFile fromJar, String jarDir, String destDir) throws IOException {
        for (Enumeration<JarEntry> entries = fromJar.entries(); entries.hasMoreElements();) {
            JarEntry jarEntry = entries.nextElement();
            System.out.println("jarEntry.getName(): " + jarEntry.getName());
            System.out.println("start with: " + jarEntry.getName().startsWith(jarDir + "/"));
            System.out.println("directory: " + jarEntry.isDirectory());
            if (!jarEntry.getName().startsWith(jarDir + "/") && !jarEntry.isDirectory()) {
                File dest = new File(destDir + "/" + jarEntry.getName());
                System.out.println("dest: " + dest.getAbsolutePath());
                File parent = dest.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }

                FileOutputStream out = new FileOutputStream(dest);
                InputStream in = fromJar.getInputStream(jarEntry);

                try {
                    byte[] buffer = new byte[8 * 1024];

                    int s = 0;
                    while ((s = in.read(buffer)) > 0) {
                        out.write(buffer, 0, s);
                    }
                } catch (IOException e) {
                    throw new IOException("Could not copy asset from jar file", e);
                } finally {
                    try {
                        in.close();
                    } catch (IOException ignored) {
                    }
                    try {
                        out.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }
    }
    
}
