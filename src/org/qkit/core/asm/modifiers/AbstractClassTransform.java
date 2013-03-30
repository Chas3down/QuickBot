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
package org.qkit.core.asm.modifiers;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import org.objectweb.asm.util.CheckClassAdapter;
import org.qkit.core.asm.adapters.*;

import java.util.HashMap;


/**
 *
 * TODO:
 * - Change it so that it relies on JarConstruct class files.
 *
 * @since 1.7
 * @author trDna
 */

public abstract class AbstractClassTransform extends Transform implements Opcodes {

    private ClassReader cr;
    private ClassWriter cw;

    private ClassVisitor currentAdapter;

    private byte[] classBytes;

    protected int num0fFields = 0, numOfFieldsIdentified = 0;

    protected static HashMap<String, String> classNames = new HashMap<String, String>();


    /**
     * Starts up the transform.
     *
     * @param cn - The class to use.
     */
    public void process(final ClassNode cn){

        try{
            //Set up the ClassWriter.
            cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

            //Delegate cn method calls and data to 'cw'.
            cn.accept(cw);

            //Set up the ClassReader
            cr = new ClassReader(cw.toByteArray());

            cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

            ClassVisitor cv = new CheckClassAdapter(cw);

            //Set the current adapter to be the ClassWriter.
            currentAdapter = cv;

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    /**
     * End-User could use this to manipulate files.
     */
    public abstract void runTransform();

    /**
     * Adds a getter to return a wanted value.
     *
     * @param targetVar - The target field name (to be changed so that it auto detects the field!).
     * @param descriptor - The descriptor of the target field.
     * @param getterName - The name of the getter method.
     * @param retInsn - The return instruction (which returns the value of what you want).
     *
     * @since 1.7
     */
    public void addGetterMethod(final String targetVar, final String descriptor, final String getterName, final int retInsn) {
        AddGetterAdapter am = new AddGetterAdapter(currentAdapter, targetVar, descriptor, getterName, retInsn);
        currentAdapter = am;
    }


    /**
     * Adds an interface to a given class.
     *
     * @param interfacesToAdd - The interfaces to add to the given class.
     */
    public void addInterface(final String... interfacesToAdd){
        AddInterfaceAdapter ai = new AddInterfaceAdapter(currentAdapter, interfacesToAdd);
        currentAdapter = ai;
    }

    public void changeSuper(final String superClass){
        ChangeSuperclassAdapter sc = new ChangeSuperclassAdapter(currentAdapter, superClass);
        currentAdapter = sc;
    }

    public void addCustomGraphics(String name){
        AddCustomGraphicsAdapter ac = new AddCustomGraphicsAdapter(currentAdapter, name);
        currentAdapter = ac;
    }


    public void applyChanges(){
        //Apply transformations from the top of the hierarchy backwards.
        cr.accept(currentAdapter, 0);

        //Have the new class bytes ready to go.
        classBytes = cw.toByteArray();

    }

    public byte[] getClassBytes(){
        return classBytes;
    }


    public ClassNode getResultingClassNode(){
        if(classBytes != null){
            ClassReader cr = new ClassReader(classBytes);
            ClassNode cn = new ClassNode(ASM4);
            cr.accept(cn, 0);
            return cn;
        } else{
            throw new NullPointerException("ClassNode bytes is null!");
        }
    }


}
