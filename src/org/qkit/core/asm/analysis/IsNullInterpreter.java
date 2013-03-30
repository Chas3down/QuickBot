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


import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.analysis.*;

/**
 * Checks if there's a possible NPE.
 *
 * @author trDna
 */
public class IsNullInterpreter extends BasicInterpreter {

    public final static BasicValue NULL = new BasicValue(null);
    public final static BasicValue MAYBE_NULL = new BasicValue(null);

    public IsNullInterpreter() {
        super(ASM4);
    }
    @Override
    public BasicValue newOperation(AbstractInsnNode insn) throws AnalyzerException {
        if (insn.getOpcode() == ACONST_NULL) {
            return NULL;
        }
        return super.newOperation(insn);
    }
    @Override
    public BasicValue merge(BasicValue v, BasicValue w) {
        if (isRef(v) && isRef(w) && v != w) {
            return MAYBE_NULL;
        }
        return super.merge(v, w);
    }

    private boolean isRef(Value v) {
        return v == BasicValue.REFERENCE_VALUE || v == NULL || v == MAYBE_NULL;
    }

}
