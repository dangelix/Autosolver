/**
 *   Copyright 2015 TIKAL-TECHNOLOGY
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */
package com.tikal.tallerWeb.servicio.monitor.imp;

/**
 *
 * @author Nekorp
 */
public class EditorMonitorData {
    private final LimitedStack<EditorLog> undoStack;
    private final LimitedStack<EditorLog> redoStack;
    
    public EditorMonitorData(int maxSize) {
        undoStack = new LimitedStack<>(maxSize);
        redoStack = new LimitedStack<>(maxSize);
    }

    public void pushRedo(EditorLog object) {
        redoStack.push(object);
    }

    public EditorLog popRedo() {
        return redoStack.pop();
    }

    public boolean emptyRedo() {
        return redoStack.empty();
    }

    public void clearRedo() {
        redoStack.clear();
    }

    public void pushUndo(EditorLog object) {
        undoStack.push(object);
    }

    public EditorLog popUndo() {
        return undoStack.pop();
    }

    public boolean emptyUndo() {
        return undoStack.empty();
    }

    public void clearUndo() {
        undoStack.clear();
    }
}
