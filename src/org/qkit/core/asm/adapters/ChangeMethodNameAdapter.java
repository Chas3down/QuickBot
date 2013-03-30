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
package org.qkit.core.asm.adapters;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

/**
 * @author trDna
 */
public class ChangeMethodNameAdapter extends ClassVisitor implements Opcodes {

    private String desc;

    private boolean isFieldPresent = false;
    private boolean isMethodPresent = false;
    private boolean isStatic = false;

    private ClassVisitor next;

    private int retInsn;

    private String targetMethodName, wantedMethodName;

    public ChangeMethodNameAdapter(final ClassVisitor cv, String targetMethodName, String wantedMethodName){
        super(ASM4, new ClassNode());
        next = cv;
        this.targetMethodName = targetMethodName;
        this.wantedMethodName = wantedMethodName;
    }

    @Override
    public void visitEnd(){
        ClassNode cn = (ClassNode) cv;

        for(MethodNode mn : cn.methods){
            if(mn.name.equals(targetMethodName)){
                mn.name = wantedMethodName;
                System.out.println("    [!!M] Changed Method name: " + targetMethodName + " -> " + wantedMethodName);
            }
        }



        try{
            cn.accept(next);
        }catch (Exception ez)  {
            ez.printStackTrace();
        }

    }

}
