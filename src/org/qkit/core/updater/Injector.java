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

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import org.qkit.core.BotInternalConstants;
import org.qkit.core.asm.modifiers.AbstractClassTransform;
import org.qkit.core.asm.modifiers.transformers.*;

import org.qkit.core.bot.Out;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

/**
 * A lightweight injector. Also known as an updater.
 * This is where the magic happens!
 *
 * @author trDna
 * @since 1.7
 */
public class Injector {

    //The JarConstruct that transformations are based off of.
    private static JarConstruct jc;

    //The transformation classes
    private static ArrayList<AbstractClassTransform> transforms = new ArrayList<>();

    //The transformed classes
    protected HashMap<String, ClassNode> injClasses = new HashMap<>();


    /**
     * Constructs an Updater instance.
     */
    public Injector(JarConstruct jc){
        //Store the JAR construct.
        Injector.jc = jc;

        //Load all of the classes and store it into a HashMap
        jc.loadClasses();

        //TODO: Add the transformation classes - self explanatory
        transforms.add(new ImageProducerTransform());
        transforms.add(new WorldGraphTransform());
        transforms.add(new NodeTransform());
        transforms.add(new NodeSubTransform());
        transforms.add(new NodeListTransform());
        transforms.add(new RenderableTransform());
        transforms.add(new EntityTransform());
        transforms.add(new GameAppletTransform());
        transforms.add(new ClientTransform());


        //Run the transforms
        runTransforms();

        //Dump the injected class files to a JAR.
        try {
            dumpFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * The JarConstruct to work with.
     *
     * @return The JarConstruct with all loaded files.
     */
    protected JarConstruct getJar(){
        return jc;
    }


    private void runTransforms(){
        Out.ln("---------- QuickBot Updater (Game Revision 317) -----------");
        //Loop through the AbstractClassTransforms.
        for(AbstractClassTransform act : transforms){

            //Loop through the classes.
            for(ClassNode cn : getJar().loadedClassNodes.values()){

                //Allow a ClassNode to be processed IF the Transformation class filter accepts it.
                if(act.accept(cn)){

                    //Process the transform.
                    act.process(cn);

                    //Run the event transformations.
                    act.runTransform();

                    //Produce the events.
                    act.applyChanges();

                    //Get the altered ClassNode
                    ClassNode c = act.getResultingClassNode();

                    //Make the updater look nice - add some spaces between each class report.
                    Out.ln("");

                    //Store it onto a HashMap for the dumper's use.
                    injClasses.put(c.name, c);
                }
            }
        }

        Out.ln("------------- Updater End -------------");
    }

    /**
     * Dumps the injected files.
     * @throws IOException
     */
    private void dumpFiles() throws IOException {

        //Set up a JarOutputStream (self-explanatory)
        JarOutputStream out = new JarOutputStream(new FileOutputStream(new File(BotInternalConstants.INJ_JAR_PATH)));

        //Loop through the injected classes and write it to the injected JAR container.
        for(ClassNode c : injClasses.values()){

            //Set up the ClassWriter.
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

            //Have the injected ClassNode delegate its method calls to ClassWriter.
            c.accept(cw);

            //Write out the entry to the JAR.
            out.putNextEntry(new JarEntry(c.name + ".class"));

            //Write out the actual contents of the ClassNode (the bytes of the class file) to the entry.
            out.write(cw.toByteArray());

            //Remove the injected ClassNode from the loaded ClassNode list.
            //This way, an "untouched" ClassNode doesn't overwrite an injected ClassNode when placing the untouched classes back into the JAR.
            getJar().loadedClassNodes.remove(c.name);
        }

        Out.ln("Injected classes dumped.");

        //Loop through the [remaining] loaded class nodes.
        for(ClassNode c : getJar().loadedClassNodes.values()){

            //Set up the ClassWriter.
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

            //Have the injected ClassNode delegate its method calls to ClassWriter.
            c.accept(cw);

            //Write out the entry to the JAR.
            out.putNextEntry(new JarEntry(c.name + ".class"));

            //Write out the actual contents of the ClassNode (the bytes of the class file) to the entry.
            out.write(cw.toByteArray());
        }

        Out.ln("Norm classes dumped.");

        //Flush the output stream.
        out.flush();

        //Close the output stream.
        out.close();

    }

}

