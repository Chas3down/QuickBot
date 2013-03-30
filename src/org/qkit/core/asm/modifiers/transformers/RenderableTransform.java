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
import org.objectweb.asm.tree.MethodNode;
import org.qkit.core.BotInternalConstants;
import org.qkit.core.asm.analysis.RegexInsnFinder;
import org.qkit.core.asm.modifiers.AbstractClassTransform;
import org.qkit.core.bot.Out;

/**
 * @author trDna
 */
public class RenderableTransform extends AbstractClassTransform{

    private ClassNode theClass;
    private RegexInsnFinder reg;

    @Override
    public boolean accept(ClassNode theClass) {
        this.theClass = theClass;
        int fieldCount = theClass.fields.size();
        int methodCount = theClass.methods.size();

        if(fieldCount == 2 && methodCount == 3){
            for(MethodNode mn : theClass.methods){
               if(mn.desc.equals("(IIIIIIIII)V")){
                   return true;
               }
            }
        }

        return false;
    }

    @Override
    public void runTransform() {
        Out.ln("[Class] " + theClass.name + " -> Renderable");
        classNames.put("Renderable", theClass.name);
        reg = new RegexInsnFinder(theClass, theClass.methods.get(0));

        addInterface(BotInternalConstants.ACCESSOR_PATH + "Renderable");

    }
}
