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

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;

import org.objectweb.asm.tree.MethodNode;
import org.qkit.core.BotInternalConstants;
import org.qkit.core.asm.modifiers.AbstractClassTransform;
import org.qkit.core.bot.Out;

/**
 * @author trDna
 */
public class NodeSubTransform extends AbstractClassTransform{

    private ClassNode theClass;

    @Override
    public boolean accept(ClassNode theClass) {
        this.theClass = theClass;

        String node = classNames.get("Node");
        if (theClass.superName.equals(node)) {
            int count = 0;
            for (FieldNode fn : theClass.fields) {
                if (fn.desc.equals("L" + theClass.name + ";")) {
                    ++count;
                }
            }
            return count == 2;
        }
        return false;
    }

    @Override
    public void runTransform() {
        Out.ln("[Class] " + theClass.name + " -> NodeSub");
        classNames.put("NodeSub", theClass.name);
        findNext();
        addInterface(BotInternalConstants.ACCESSOR_PATH + "NodeSub");
    }

    private void findNext(){
        for(MethodNode mn : theClass.methods){

            FieldInsnNode fin;
            if((fin = org.qkit.core.asm.analysis.Conditions.NodeSub.next.accept(mn.instructions.toArray())) != null){
                addGetterMethod(fin.name, fin.desc, "getNext", ARETURN);
                return;
            }
        }
    }
}
