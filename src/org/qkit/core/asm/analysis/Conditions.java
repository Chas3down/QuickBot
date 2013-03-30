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
package org.qkit.core.asm.analysis;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;

import org.qkit.core.bot.Out;
import org.qkit.ext.utils.Filter;

import java.awt.*;

/**
 * A set of conditions for use of the updater.
 * @author trDna
 */
public interface Conditions {

    class GameApplet{

        public static Filter<FieldInsnNode, AbstractInsnNode[]> realGraphicsFilter = new Filter<FieldInsnNode, AbstractInsnNode[]>() {
            @Override
            public FieldInsnNode accept(AbstractInsnNode[] ains) {
                for (int i = 0; i < ains.length; i++) {
                    if (ains[i].getOpcode() == Opcodes.GETFIELD &&
                            ((FieldInsnNode) ains[i]).desc.equals(Type.getDescriptor(Graphics.class))){
                        Out.ln("Found!");
                        FieldInsnNode fin = (FieldInsnNode) ains[i];
                        return fin;
                    }
                }
                return null;
            }
        };

    }

    class Client{

        private static int[] baseXInsns = {Opcodes.ILOAD, Opcodes.ALOAD, Opcodes.GETFIELD, Opcodes.IADD, Opcodes.INVOKEVIRTUAL};

        public static Filter<FieldInsnNode, AbstractInsnNode[]> baseXFilter = new Filter<FieldInsnNode, AbstractInsnNode[]>() {
            @Override
            public FieldInsnNode accept(AbstractInsnNode[] ains) {
                int skips = 0;

                for (int i = 0; i < ains.length; i++) {
                    if(ains[i].getOpcode() == baseXInsns[0]
                            && ains[i + 1].getOpcode() == baseXInsns[1]
                            && ains[i + 2].getOpcode() == baseXInsns[2] //getfield
                            && ains[i + 3].getOpcode() == baseXInsns[3]
                            && ains[i + 4].getOpcode() == baseXInsns[4]){

                        if(skips != 3){
                            ++skips;
                            continue;
                        }

                        FieldInsnNode fin = (FieldInsnNode) ains[i + 2];
                        return fin;
                    }
                }
                return null;
            }
        };

    }

    class NodeSub{
        public static Filter<FieldInsnNode, AbstractInsnNode[]> next = new Filter<FieldInsnNode, AbstractInsnNode[]>() {
            @Override
            public FieldInsnNode accept(AbstractInsnNode[] ains) {

                for (int i = 0; i < ains.length; i++) {
                    if(ains[i].getOpcode() == Opcodes.ALOAD
                            && ains[i + 1].getOpcode() == Opcodes.GETFIELD
                            && ains[i + 2].getOpcode() == Opcodes.ALOAD
                            && ains[i + 3].getOpcode() == Opcodes.GETFIELD
                            && ains[i + 4].getOpcode() == Opcodes.PUTFIELD
                            && ((FieldInsnNode) ains[i + 1]).desc.equals("LNode;")){

                        FieldInsnNode fin = (FieldInsnNode) ains[i + 1];
                        return fin;
                    }
                }
                return null;
            }
        };
    }

    class Entity{
        public static Filter<FieldInsnNode, AbstractInsnNode[]> smallX = new Filter<FieldInsnNode, AbstractInsnNode[]>() {
            @Override
            public FieldInsnNode accept(AbstractInsnNode[] ains) {

                for (int i = 0; i < ains.length; i++) {
                    if(ains[i].getOpcode() == Opcodes.ALOAD
                            && ains[i + 1].getOpcode() == Opcodes.GETFIELD
                            && ains[i + 2].getOpcode() == Opcodes.ICONST_0
                            && ains[i + 3].getOpcode() == Opcodes.IALOAD
                            && ains[i + 4].getOpcode() == Opcodes.ISUB
                            && ((FieldInsnNode) ains[i + 1]).desc.equals("[I")){

                        FieldInsnNode fin = (FieldInsnNode) ains[i + 1];
                        return fin;
                    }
                }
                return null;
            }
        };

        public static Filter<FieldInsnNode, AbstractInsnNode[]> smallY = new Filter<FieldInsnNode, AbstractInsnNode[]>() {
            @Override
            public FieldInsnNode accept(AbstractInsnNode[] ains) {
                int skips = 1;
                for (int i = 0; i < ains.length; i++) {
                    if(ains[i].getOpcode() == Opcodes.GETFIELD
                            && ains[i + 1].getOpcode() == Opcodes.ICONST_0
                            && ains[i + 2].getOpcode() == Opcodes.IALOAD
                            && ains[i + 3].getOpcode() == Opcodes.ISUB
                            && ains[i + 4].getOpcode() == Opcodes.ISTORE
                            && ((FieldInsnNode) ains[i]).desc.equals("[I")){

                        if(skips != 2){
                            ++skips;
                            continue;
                        }

                        FieldInsnNode fin = (FieldInsnNode) ains[i];
                        return fin;
                    }
                }
                return null;
            }
        };
    }


}
