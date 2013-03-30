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

import org.qkit.core.BotInternalConstants;
import org.qkit.core.asm.analysis.*;
import org.qkit.core.asm.modifiers.AbstractClassTransform;
import org.qkit.core.bot.Out;


/**
 * @author trDna
 */
public class GameAppletTransform extends AbstractClassTransform {


    private ClassNode theClass;
    private RegexInsnFinder reg;

    @Override
    public boolean accept(ClassNode theClass) {
        this.theClass = theClass;
        return theClass.superName.equals("java/applet/Applet");
    }

    @Override
    public void runTransform() {
        Out.ln("[Class] " + theClass.name + " -> RSApplet");
        classNames.put("GameApplet", theClass.name);
        reg = new RegexInsnFinder(theClass, theClass.methods.get(0));

        findGraphics();

        addInterface(BotInternalConstants.ACCESSOR_PATH + "GameApplet");
        changeSuper(BotInternalConstants.ACCESSOR_PATH + "SuperApplet");
    }

    public void findAndRetrieveMethod(String desc, String accessorMethodName, String errMsg, int retCode, String regex){
        for(MethodNode mn : theClass.methods)  {
            reg.setMethod(theClass, mn);
            boolean brLoop = false;

            for(AbstractInsnNode ain : reg.find(regex)){
                if(ain.equals(null)){
                    Out.err(errMsg);
                    break;
                }


                if(ain instanceof FieldInsnNode){
                    FieldInsnNode node = (FieldInsnNode) ain;
                    if(node.desc.equals(desc)) {
                        addGetterMethod(node.name, desc, accessorMethodName, retCode);
                        ++numOfFieldsIdentified;
                        return;
                    }
                }

            }

        }
    }

    public void findAndRetrieveMethod(String desc, String accessorMethodName, String errMsg, int retCode, String regex, int skips){
        int totalSkips = -1;
        for(MethodNode mn : theClass.methods)  {
            reg.setMethod(theClass, mn);
            boolean brLoop = false;

            for(AbstractInsnNode ain : reg.find(regex)){
                if(ain.equals(null)){
                    Out.err(errMsg);
                    break;
                }


                if(ain instanceof FieldInsnNode){
                    FieldInsnNode node = (FieldInsnNode) ain;
                    ++totalSkips;
                    if(totalSkips == skips){
                        if(node.desc.equals(desc)) {
                            addGetterMethod(node.name, desc, accessorMethodName, retCode);
                            ++numOfFieldsIdentified;
                            return;
                        }
                    }else {
                        continue;
                    }
                }

            }

        }
    }

    public void printPossibleMatches(String desc, String accessorMethodName, String errMsg, int retCode, String regex){
        int counter = 0;
        for(MethodNode mn : theClass.methods)  {
            reg.setMethod(theClass, mn);
            boolean brLoop = false;

            for(AbstractInsnNode ain : reg.find(regex)){
                if(ain.equals(null)){
                    Out.err(errMsg);
                    break;
                }


                if(ain instanceof FieldInsnNode){
                    FieldInsnNode node = (FieldInsnNode) ain;
                    if(node.desc.equals(desc)) {
                        Out.ln("  " + (++counter - 1) + ")  [+M] Possible match: " + accessorMethodName + " -> " + node.name);
                        continue;
                    }else {
                        continue;
                    }
                }

            }

        }
    }

    public void findGraphics(){
        for(MethodNode mn : theClass.methods){

            FieldInsnNode fin;
            if((fin = org.qkit.core.asm.analysis.Conditions.GameApplet.realGraphicsFilter.accept(mn.instructions.toArray())) != null){
                addGetterMethod(fin.name, fin.desc, "getRealGraphics", ARETURN);
                return;
            }
        }
    }
}
