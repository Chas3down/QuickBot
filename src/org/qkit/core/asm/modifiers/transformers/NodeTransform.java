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
import org.qkit.core.asm.analysis.RegexInsnFinder;
import org.qkit.core.asm.modifiers.AbstractClassTransform;

import org.qkit.core.bot.Out;

import java.util.ListIterator;

/**
 * @author trDna
 */
public class NodeTransform extends AbstractClassTransform{

    private ClassNode theClass;
    private RegexInsnFinder reg;

    @Override
    public boolean accept(ClassNode theClass) {
        this.theClass = theClass;
        int classType = 0, longType = 0;

        ListIterator<FieldNode> list = theClass.fields.listIterator();

        while (list.hasNext()){
            FieldNode fn = list.next();
            if((fn.access & ACC_STATIC) == 0){
                if(fn.desc.equals(String.format("L%s;", theClass.name))){
                    ++classType;
                }else if(fn.desc.equals("J")){
                    ++longType;
                }
            }
        }

        return longType == 1 && classType == 2;
    }

    @Override
    public void runTransform() {
        Out.ln("[Class] " + theClass.name + " -> Node");
        classNames.put("Node", theClass.name);

        reg = new RegexInsnFinder(theClass, theClass.methods.get(0));

        num0fFields = 3;

        /** ID **/
        for(FieldNode fn : theClass.fields){
            if(fn.desc.equals("J")){
                addGetterMethod(fn.name, "J", "getID", LRETURN);
                ++numOfFieldsIdentified;
                break;
            }
        }

        /** Next **/
        findAndRetrieveMethod(String.format("L%s;", theClass.name), "getNext", "NextNode not found!", ARETURN, "ALOAD_0 GETFIELD IFNONNULL GOTO");

        /** Previous **/
        findAndRetrieveMethod(String.format("L%s;", theClass.name), "getPrevious", "PreviousNode not found!", ARETURN, "ACONST_NULL PUTFIELD ALOAD_0 ACONST_NULL");

        Out.ln(numOfFieldsIdentified +  "/" + num0fFields + " identified in " + theClass.name + ".class");
        addInterface(BotInternalConstants.ACCESSOR_PATH + "Node");
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
        int totalSkips = 0;
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
                        Out.ln("  " + ++counter + ")  [+M] Possible match: " + accessorMethodName + " -> " + node.name);
                        continue;
                    }else {
                        continue;
                    }
                }

            }

        }
    }
}
