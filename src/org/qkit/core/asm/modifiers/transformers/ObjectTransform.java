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
import org.qkit.core.asm.analysis.InstructionSearcher;
import org.qkit.core.asm.analysis.RegexInsnFinder;
import org.qkit.core.asm.modifiers.AbstractClassTransform;

/**
 * @author trDna
 */
public class ObjectTransform extends AbstractClassTransform {

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
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
