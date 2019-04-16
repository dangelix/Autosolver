/**
 *   Copyright 2013 Nekorp
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

package com.tikal.tallerWeb.servicio.reporte.cliente;

/**
 *
 */
public class BordeSeccion {

    private int leftColumn;
    private int upperRow;
    private int rightColumn;
    private int lowerRow;

    public BordeSeccion() {
        super();
    }
    
    public BordeSeccion(BordeSeccion otro) {
        copy(otro);
    }
    public int getLeftColumn() {
        return leftColumn;
    }

    public void setLeftColumn(int LeftColumn) {
        this.leftColumn = LeftColumn;
    }

    public int getUpperRow() {
        return upperRow;
    }

    public void setUpperRow(int upperRow) {
        this.upperRow = upperRow;
    }

    public int getRightColumn() {
        return rightColumn;
    }

    public void setRightColumn(int rightColumn) {
        this.rightColumn = rightColumn;
    }

    public int getLowerRow() {
        return lowerRow;
    }

    public void setLowerRow(int lowerRow) {
        this.lowerRow = lowerRow;
    }
    
    private void copy(BordeSeccion borde) {
        this.leftColumn = borde.leftColumn;
        this.lowerRow = borde.lowerRow;
        this.rightColumn = borde.rightColumn;
        this.upperRow = borde.upperRow;
    }
}
