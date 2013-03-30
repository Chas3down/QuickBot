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
public class ClientTransform extends AbstractClassTransform {

    private ClassNode theClass;
    private InstructionSearcher rif;
    private RegexInsnFinder reg;


    @Override
    public boolean accept(ClassNode theClass) {
        this.theClass = theClass;
        return theClass.name.equalsIgnoreCase("client");
    }


    @Override
    public void runTransform() {
        Out.ln("[Class] " + theClass.name + " -> Client");
        classNames.put("client", theClass.name);
        reg = new RegexInsnFinder(theClass, theClass.methods.get(0));

        /**
         * int.class
         */

        /** Number of Fields **/
        num0fFields = 22;

        /** CameraX **/
        findAndRetrieveMethod("I", "getCameraX", "CameraX not found!", IRETURN, "D2I ISTORE ILOAD IFNE ALOAD_0 DUP GETFIELD ILOAD IADD PUTFIELD");

        /** CameraY **/
        findAndRetrieveMethod("I", "getCameraY", "CameraY not found!", IRETURN, "ISTORE ILOAD_3 ALOAD_0 GETFIELD ISUB ISTORE_3 GETSTATIC");

        /** CameraZ **/
        findAndRetrieveMethod("I", "getCameraZ", "CameraZ not found!", IRETURN, "ILOAD_3 ALOAD_0 GETFIELD ISUB ISTORE ILOAD_2 ALOAD_0");

        /** Camera Zoom **/
        findAndRetrieveMethod("I", "getCameraZoom", "CameraZoom not found!", IRETURN, "ILOAD_1 GETSTATIC ISUB ISTORE ILOAD IFEQ");

        /** Plane **/
        findAndRetrieveMethod("I", "getPlane", "Plane not found!", IRETURN, "ALOAD_0 GETFIELD IALOAD ISTORE ILOAD_3 ILOAD IMUL ILOAD_1 ILOAD IMUL IADD BIPUSH");

        /** Current Exp - Come back to this later.**/
        //  findAndRetrieveMethod(Type.getDescriptor(int.class), "getCurrentExp", "Current Stats not found!", IRETURN, "ALOAD_0 BIPUSH NEWARRAY PUTFIELD ALOAD_0 BIPUSH");

        /** CameraCurveX **/
        findAndRetrieveMethod("I", "getCameraCurveX", "CameraCurveX not found!", IRETURN, "IFLE ALOAD_0 DUP GETFIELD ALOAD_0 GETFIELD ILOAD ALOAD_0 GETFIELD IMUL");

        /** CameraCurveY **/
        findAndRetrieveMethod("I", "getCameraCurveY", "CameraCurveY not found!", IRETURN, "ALOAD_0 ILOAD PUTFIELD ALOAD_0 ILOAD PUTFIELD", 8);

        /** MenuScreenArea **/
        findAndRetrieveMethod("I", "getMenuScreenArea", "MenuScreenArea not found!", IRETURN, "IFEQ ALOAD_0 GETFIELD ICONST_1 IF_ICMPNE ICONST_1 PUTSTATIC");

        /** MenuOffsetX **/
        findAndRetrieveMethod("I", "getMenuOffsetX", "MenuOffsetX not found!", IRETURN, "ILOAD_2 ALOAD_0 GETFIELD BIPUSH ISUB IF_ICMPLT ILOAD_2 ALOAD_0 GETFIELD ALOAD_0 GETFIELD IADD");

        /** MenuOffsetY **/
        findAndRetrieveMethod("I", "getMenuOffsetY", "MenuOffsetY not found!", IRETURN, "BIPUSH IADD IF_ICMPGT ILOAD_3 ALOAD_0 GETFIELD BIPUSH ISUB IF_ICMPLT ILOAD_3 ALOAD_0 GETFIELD");

        /** MenuWidth **/
        findAndRetrieveMethod("I", "getMenuWidth", "MenuWidth not found!", IRETURN, "ALOAD_0 ILOAD_1 PUTFIELD ALOAD_0 BIPUSH ALOAD_0 GETFIELD IMUL");

        /** MenuHeight **/
        findAndRetrieveMethod("I", "getMenuHeight", "MenuHeight not found!", IRETURN, "IMUL BIPUSH IADD PUTFIELD", 1);

        /** MapBaseX **/
        //findAndRetrieveMethod("I", "getMapBaseX", "MapBaseX not found!", IRETURN, "ILOAD_2 ALOAD_0 GETFIELD IADD INVOKEVIRTUAL");
        findMapBaseX();

        /** MapBaseY **/
        findAndRetrieveMethod("I", "getMapBaseY", "MapBaseY not found!", IRETURN, "ILOAD_3 ALOAD_0 GETFIELD IADD INVOKEVIRTUAL ALOAD_0 GETFIELD IADD INVOKEVIRTUAL ALOAD_0 GETFIELD ILOAD");

        /** DestX **/
        findAndRetrieveMethod("I", "getDestX", "DestX not found!", IRETURN, "BIPUSH ISHR ALOAD_0 GETFIELD IF_ICMPNE GETSTATIC");

        /** DestY **/
        findAndRetrieveMethod("I", "getDestY", "DestY not found!", IRETURN, "ICONST_0 IALOAD PUTFIELD ICONST_1 ISTORE", 2);

        /** MinimapInt1 **/
        findAndRetrieveMethod("I", "getMinimapInt1", "MiniMapInt1 not found!", IRETURN, "ALOAD_0 ALOAD_0 GETFIELD ALOAD_0 GETFIELD ICONST_2 IDIV IADD SIPUSH IAND PUTFIELD");

        /** MinimapInt2 **/
        findAndRetrieveMethod("I", "getMinimapInt2", "MiniMapInt2 not found!", IRETURN, "ALOAD_0 GETFIELD IADD SIPUSH IAND ISTORE GETSTATIC ILOAD IALOAD", 1);

        /** MinimapInt3 **/
        findAndRetrieveMethod("I", "getMinimapInt3", "MiniMapInt3 not found!", IRETURN, "ILOAD IALOAD ISTORE ILOAD ALOAD_0 GETFIELD SIPUSH IADD IMUL");


        /**
         * int[].class current stats
         */

        /** PlayerIndices **/
        findAndRetrieveMethod("[I", "getPlayerIndices", "PlayerIndices not found!", ARETURN, "GETFIELD ILOAD_2 ALOAD_0 GETFIELD ISUB IALOAD AALOAD ASTORE_3");

        /** NPCIndices **/
        findAndRetrieveMethod("[I", "getNPCIndices", "NPCIndices not found!", ARETURN, "IF_ICMPGE ALOAD_0 GETFIELD ILOAD_1 IALOAD ISTORE_2 ALOAD_0 GETFIELD ILOAD_2 AALOAD");



        /**
         * boolean.class
         */
        findAndRetrieveMethod("Z", "isLoggedIn", "isLoggedIn not found!", IRETURN, "GETSTATIC IFNE ALOAD_0 INVOKEVIRTUAL GOTO");

        /**
         * WorldGraph
         */
      //  printPossibleMatches("L" + classNames.get("WorldGraph"), "getTileNodeSettings", "", ARETURN, "ILOAD ICONST_4 IF_ICMPGE ALOAD_0 GETFIELD ILOAD NEW DUP");

        Out.ln(numOfFieldsIdentified +  "/" + num0fFields + " identified in " + theClass.name + ".class");
        addInterface(BotInternalConstants.ACCESSOR_PATH + "Client");
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
                    Out.ln(node.desc + " possible match.");
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

    private void findMapBaseX(){
        for(MethodNode mn : theClass.methods){

            FieldInsnNode fin;
            if((fin = org.qkit.core.asm.analysis.Conditions.Client.baseXFilter.accept(mn.instructions.toArray())) != null){
                addGetterMethod(fin.name, fin.desc, "getMapBaseX", IRETURN);
                return;
            }
        }
    }


}
