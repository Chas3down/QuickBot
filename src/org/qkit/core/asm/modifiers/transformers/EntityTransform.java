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
import org.qkit.core.asm.analysis.Conditions;
import org.qkit.core.asm.modifiers.AbstractClassTransform;
import org.qkit.core.bot.Out;

/**
 * @author trDna
 */
public class EntityTransform extends AbstractClassTransform{

    private ClassNode theClass;
    @Override
    public boolean accept(ClassNode theClass) {
        this.theClass = theClass;

        String renderable = classNames.get("Renderable");
        if(theClass.superName.equals(renderable)){
            int count = 0;
            for(FieldNode fn : theClass.fields){
                if(fn.desc.equals("[I")){
                    ++count;
                }
            }
            if(count == 5){
                return true;
            }
        }
        return false;
    }

    @Override
    public void runTransform() {
        Out.ln("[Class] " + theClass.name + " -> Entity");
        classNames.put("Entity", theClass.name);
        findSmallX();
        findSmallY();
        addInterface(BotInternalConstants.ACCESSOR_PATH + "Entity");
    }

    private void findSmallX(){
        for(MethodNode mn : theClass.methods){

            FieldInsnNode fin;
            if((fin = Conditions.Entity.smallX.accept(mn.instructions.toArray())) != null){
                addGetterMethod(fin.name, fin.desc, "getSmallX", ARETURN);
                return;
            }
        }
    }

    private void findSmallY(){
        for(MethodNode mn : theClass.methods){

            FieldInsnNode fin;
            if((fin = Conditions.Entity.smallY.accept(mn.instructions.toArray())) != null){
                addGetterMethod(fin.name, fin.desc, "getSmallY", ARETURN);
                return;
            }
        }
    }
}
