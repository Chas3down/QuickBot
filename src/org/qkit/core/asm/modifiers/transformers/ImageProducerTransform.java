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


import org.objectweb.asm.tree.*;

import org.qkit.core.asm.modifiers.AbstractClassTransform;
import org.qkit.core.bot.Out;


public class ImageProducerTransform extends AbstractClassTransform{

    private ClassNode theClass;

    @Override
    public boolean accept(ClassNode theClass) {
        this.theClass = theClass;
        int cModel = 0;
        return theClass.fields.size() == 6 && theClass.methods.size() == 11;
    }

    @Override
    public void runTransform() {
        Out.ln("[Class] " + theClass.name + " -> ImageProducer");
        classNames.put("ImageProducer", theClass.name);

       addCustomGraphics(theClass.name);
        for(FieldNode fn: theClass.fields){
            if(fn.desc.equals("Ljava/awt/Image;")){
                addGetterMethod(fn.name, fn.desc, "getImage", ARETURN);
                break;
            }
        }
        changeSuper("org.qkit.core.event.producers.Renderer".replace(".", "/"));

    }
}
