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
import org.objectweb.asm.tree.*;

import java.util.ArrayList;
import java.util.List;


public class InstructionSearcher {
        private InsnList list;
        private AbstractInsnNode current;

        public InstructionSearcher(MethodNode m) {
                this.list = m.instructions;
                this.current = list.getFirst();
        }
        public AbstractInsnNode getCurrent() {
                return current;
        }
        public void setCurrent(AbstractInsnNode in) {
                current = in;
        }
        public AbstractInsnNode getNext(int opcode) {
                while (current != null) {
                        if (current.getOpcode() == opcode) {
                                AbstractInsnNode old = current;
                                current = current.getNext();
                                return old;
                        }
                        current = current.getNext();
                }
                return null;
        }
        public AbstractInsnNode getNext() {
                if (current != null) {
                        current = current.getNext();
                        while (current != null && current.getOpcode() == -1) {
                                current = current.getNext();
                        }
                }
                return current;
        }
        public AbstractInsnNode getPrevious(int opcode) {
                while (current != null) {
                        if (current.getOpcode() == opcode) {
                                AbstractInsnNode old = current;
                                current = current.getPrevious();
                                return old;
                        }
                        current = current.getPrevious();
                }
                return null;
        }
        public AbstractInsnNode getPrevious() {
                current = current.getPrevious();
                while (current.getOpcode() == -1) current = current.getPrevious();
                return current;
        }
        public LdcInsnNode getNextLDC(Object cst) {
                AbstractInsnNode in;
                while ((in = getNext(Opcodes.LDC)) != null) {
                        LdcInsnNode ln = (LdcInsnNode) in;
                        if (ln.cst.equals(cst)) return ln;
                }
                return null;
        }
        public LdcInsnNode getPreviousLDC(Object cst) {
                AbstractInsnNode in;
                while ((in = getPrevious(Opcodes.LDC)) != null) {
                        LdcInsnNode ln = (LdcInsnNode) in;
                        if (ln.cst.equals(cst))
                                return ln;
                }
                return null;
        }
        public IntInsnNode getNextPush(int opcode, int value) {
                AbstractInsnNode in;
                while ((in = getNext(opcode)) != null) {
                        IntInsnNode iin = (IntInsnNode) in;
                        if (iin.operand == value) return iin;
                }
                return null;
        }
        public List<AbstractInsnNode> analyze(int opcode) {
                reset();
                List<AbstractInsnNode> list = new ArrayList<AbstractInsnNode>();
                AbstractInsnNode in;
                while ((in = getNext(opcode)) != null) {
                        list.add(in);
                }
                return list;
        }
        public int getIndex() {
                return list.indexOf(current);
        }
        public void setIndex(int index) {
                current = list.get(index);
        }
        public void reset() {
                current = list.getFirst();
        }
}