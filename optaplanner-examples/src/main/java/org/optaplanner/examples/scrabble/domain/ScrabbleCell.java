/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.examples.scrabble.domain;

import java.util.Set;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.CustomShadowVariable;
import org.optaplanner.examples.common.domain.AbstractPersistable;
import org.optaplanner.examples.scrabble.domain.solver.CellUpdatingVariableListener;

@PlanningEntity()
@XStreamAlias("ScrabbleCell")
public class ScrabbleCell extends AbstractPersistable {

    private int x;
    private int y;

    @CustomShadowVariable(variableListenerClass = CellUpdatingVariableListener.class,
            sources = {@CustomShadowVariable.Source(entityClass = ScrabbleWordAssignment.class, variableName = "startCell"),
                    @CustomShadowVariable.Source(entityClass = ScrabbleWordAssignment.class, variableName = "direction"),})
    private Set<ScrabbleWordAssignment> wordSet;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Set<ScrabbleWordAssignment> getWordSet() {
        return wordSet;
    }

    public void setWordSet(Set<ScrabbleWordAssignment> wordSet) {
        this.wordSet = wordSet;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public String getLabel() {
        return "(" + x + "," + y + ")";
    }

    public void insertWordAssignment(ScrabbleWordAssignment wordAssignment, char c) {
        boolean added = wordSet.add(wordAssignment);
        if (!added) {
            throw new IllegalStateException("The wordAssignment (" + wordAssignment
                    + ") is already in the cell (" + this + ").");
        }
    }

    public void retractWordAssignment(ScrabbleWordAssignment wordAssignment, char c) {
        boolean removed = wordSet.remove(wordAssignment);
        if (!removed) {
            throw new IllegalStateException("The wordAssignment (" + wordAssignment
                    + ") is not in the cell (" + this + ").");
        }
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}
