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
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.awt.*;


public class AddCustomGraphicsAdapter extends ClassVisitor implements Opcodes {

    private ClassVisitor next;

    private int retInsn;

    private String targetClazzName;

    public AddCustomGraphicsAdapter(final ClassVisitor cv, String targetClazzName){
        super(ASM4, new ClassNode());
        next = cv;
        this.targetClazzName = targetClazzName;
    }

    @Override
    public void visitEnd(){
        ClassNode cn = (ClassNode) cv;

        String imgVar = null;
        for (FieldNode f : cn.fields) {
            if (f.desc.contains(Image.class.getName().replace('.', '/')))
                imgVar = f.name;
        }

        MethodNode mm = null, mv = null;

        for (MethodNode m : cn.methods) {
            if(m.name.equals("drawGraphics")){
                mm = m;
                mv = new MethodNode(ACC_PUBLIC, "drawGraphics", "(ILjava/awt/Graphics;I)V", null, null);
                mv.visitCode();
                mv.visitVarInsn(ALOAD, 0);
                mv.visitMethodInsn(INVOKESPECIAL, "RSImageProducer", "method239", "()V");
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, "RSImageProducer", "anImage320", "Ljava/awt/Image;");
                mv.visitVarInsn(ILOAD, 1);
                mv.visitVarInsn(ILOAD, 3);
                mv.visitMethodInsn(INVOKESTATIC, "RSImageProducer", "render", "(Ljava/awt/Image;II)Z");
                Label l0 = new Label();
                mv.visitJumpInsn(IFEQ, l0);
                mv.visitInsn(RETURN);
                mv.visitLabel(l0);
                mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                mv.visitVarInsn(ALOAD, 2);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, "RSImageProducer", "anImage320", "Ljava/awt/Image;");
                mv.visitVarInsn(ILOAD, 3);
                mv.visitVarInsn(ILOAD, 1);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/awt/Graphics", "drawImage", "(Ljava/awt/Image;IILjava/awt/image/ImageObserver;)Z");
                mv.visitInsn(POP);
                mv.visitInsn(RETURN);
                mv.visitMaxs(5, 4);
                mv.visitEnd();
            }
        }

        cn.methods.remove(mm);
        cn.methods.add(mv);

        System.out.println("          [+M] Added gfx method");


        try{
            cn.accept(next);
        }catch (Exception ez)  {
            ez.printStackTrace();
        }

    }

}
