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
package org.qkit.core.asm.modifiers.transformers;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.qkit.core.asm.modifiers.AbstractClassTransform;
import org.qkit.core.bot.Out;

/**
 * @author trDna
 */
public class SyntheticaTransform extends AbstractClassTransform {

    private ClassNode theClass;
    @Override
    public boolean accept(ClassNode theClass) {
        this.theClass = theClass;
        return theClass.name.equals("de.javasoft.plaf.synthetica.SyntheticaRootPaneUI".replace(".", "/"));
    }

    @Override
    public void runTransform() {
        Out.ln("SyntheticaRootPaneUI found!");
        for(FieldNode fn : theClass.fields){
            String name = fn.name;

            switch (name){
                case "EVAL_COPY":
                    Out.ln("Changing eval..");
                    fn.value = new Boolean(false);
                    break;
                case "EVAL_TEXT":
                    fn.value = "";
                    Out.ln("Changing eval text...");
                    break;
            }

        }
        MethodNode retTrue = new MethodNode(ACC_PUBLIC | ACC_STATIC | ACC_FINAL, "isEvalCopy", Type.getDescriptor(boolean.class), null, null);
        retTrue.instructions.add(new InsnNode(ICONST_0));
        retTrue.instructions.add(new InsnNode(IRETURN));

        for(MethodNode mn : theClass.methods){
            if(mn.name.equals("isEvalCopy")){
                Out.ln("Changing method..");
                mn.instructions = retTrue.instructions;
            }
        }
        Out.ln("Done mods.");
    }
}
