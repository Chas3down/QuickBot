/*
 This file is a part of QuickBot.

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.qkit.core.updater;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import org.qkit.core.bot.Out;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * A representation of a JAR content vector.
 *
 * @author trDna
 * @since 1.7
 */
public class JarConstruct  {

    /**
     * The URLClassLoader that loads the JAR from a given path.
     */
    private URLClassLoader url;

    /**
     * This is where the classes are stored. For use only in the loader package!
     */
    protected HashMap<String, ClassNode> loadedClassNodes = new HashMap<String, ClassNode>();

    /**
     * The JAR's path.
     */
    private String jarPath;

    /**
     * The JAR's URL
     */
    private String jarUrl;

    /**
     * The JAR's URL path.
     */
    private URL jarUrlPath;



    /**
     * Creates a representation of a loaded jar.
     *
     * @param jarPath The path to the JAR.
     */
    public JarConstruct(final String jarPath){
        try {

            //Set up logger and print out the path.
            Out.ln("JarConstruct " + getClass().hashCode());
            Out.ln("Path to JAR: " + jarPath);

            //Creates a new URLClassLoader and loads the JAR.
            jarUrlPath = new URL("file:" + jarPath);

            //Referencing the JAR's path.
            this.jarPath = jarPath;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a representation of a loaded JAR.
     *
     * @param jarUrl The URL to the JAR.
     * @param flags For future use
     *
     */
    public JarConstruct(final String jarUrl, int flags){
        try {

            //Set up logger and print out the path.
            Out.ln("JarConstruct " + getClass().hashCode());
            Out.ln("Path to JAR: " + (jarUrl + "!").replace("http://", "file:"));

            //Creates a new URLClassLoader and loads the JAR.
            url = new URLClassLoader(new URL[]{new URL((jarUrl + "!/").replace("http://", "jar:http://"))});

            //Referencing the JAR's URL.
            this.jarUrl = jarUrl;

            jarUrlPath = new URL((jarUrl + "!/").replace("http://", "jar:http://"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all classes from the given JAR file and stores them into a HashMap for future use.
     *
     * @return True if it was successful, False if it was not.
     */
    public boolean loadClasses(){
        short count = 0;

        try {
            //Startup
            p("---------------------------------------------------------------------");
            p("--------------------      Jar Loader     ----------------------------");
            p("File: " + jarPath);

            p("JC Hash: " + getClass().hashCode());


            JarFile jf;

            //Referencing the JAR file.
            if(jarUrl != null) {

                System.out.println(jarUrlPath.toString());

                //Connect to the JAR directly online
                JarURLConnection u = (JarURLConnection) jarUrlPath.openConnection();

                //Get the JarFile representation from the JarURLConnection
                jf = u.getJarFile();

            } else {

                //If the if statement leads here, then the JAR is being run locally
                jf = new JarFile(jarPath);

            }

            //Make sure that a non-null value was passed in from either constructor
            assert jarPath != null || jarUrl != null : "Jar loader failure!";


            //Print out the size of the JarFile
            p("JarFile Size = " + jf.size());
            p("-----------------------------------------------------------------");

            //Referencing the entries.
            Enumeration<? extends JarEntry> en = jf.entries();

            //Looping through the elements (the entries).
            while(en.hasMoreElements()){

                //The entry to work with.
                JarEntry entry = en.nextElement();

                //Grabbing solely the class files
                if (entry.getName().endsWith(".class")) {

                    //Count out the entries
                    ++count;

                    //Print out the entry
                    p("Entry " + count + ") " + entry.getName());
                    p(" -> Decompressed Size = " + entry.getSize() + " Bytes" + "\n");

                    //ClassReader retrieves the bytes from a given entry.
                    ClassReader cr = new ClassReader(jf.getInputStream(entry));

                    //Creating a new ClassNode to act as a representative of a class.
                    ClassNode cn = new ClassNode();

                    //Delegating all method calls and data from ClassReader to ClassNode.
                    //Think of it as data from 'cr' are being entrusted or put into 'cn' (such as the class bytes).
                    cr.accept(cn, 0);

                    Out.ln("");

                    //Put it into the local package's HashMap as a ClassNode.
                    loadedClassNodes.put(cn.name, cn);
                }
            }




            System.out.println(count + " classes were loaded and stored!");
            p("-----------------------------------------------------------------");
            p("-----------------------------------------------------------------");

            Out.ln("Load successful.");

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Retrieves a given class from the class HashMap.
     *
     * @param clazz - The class name.
     * @return - A Class object.
     */
    public ClassNode retrieveClass(final String clazz){
        //Assume that the HashMap
        return loadedClassNodes.get(clazz);
    }

    private void p(Object msg){
        System.out.println(msg);
    }


}

