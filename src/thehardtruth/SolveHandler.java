/*
 * Copyright (C) 2018 leeseungha
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package thehardtruth;

import java.util.ArrayList;

/**
 * A class that handles Solve objects.
 */
public class SolveHandler {
    private final ArrayList<Solve> solves;
    
    /**
     * Creates a SolveHandler object without any Solve objects.
     */
    public SolveHandler() {
        this.solves = new ArrayList<>();
    }
    
    /**
     * Creates a SolveHandler object using an array of Solve objects.
     * @param solves The array of solve objects to store in the SolveHandler.
     */
    public SolveHandler(Solve[] solves) {
        this.solves = new ArrayList<>();
        for (int i = 0; i < solves.length; ++i) {
            this.solves.add(solves[i]);
        }
    }
    
    /**
     * Returns the number of Solves stored in the SolveHandler object.
     * @return returns the number of Solves stored in the SolveHandler object.
     */
    public int getSize() {
        return solves.size();
    }
    
    /**
     * Returns an array that represents the solve times of the Solve objects in the SolveHandler.
     * @return returns an array that represents the solve times of the Solve objects in the SolveHandler.
     */
    public double[] getSolveTimes() {
        double[] solveTimes = new double[solves.size()];
        for (int i = 0; i < solves.size(); ++i) {
            solveTimes[i] = solves.get(i).getTime();
        }
        
        return solveTimes;
    }
    
    /**
     * Returns a Solve object at index.
     * @param index The index of the Solve object.
     * @return returns the Solve object at index.
     */
    public Solve getSolve(int index) {
        return solves.get(index);
    }
    
    /**
     * Returns the time value of the Solve object.
     * @param index The index of the Solve object whose time value is returned.
     */
    public void getTime(int index) {
        solves.get(index).getTime();
    }
    
    /**
     * Sets the Solve object at index to be solve.
     * @param index The index to be set.
     * @param solve The Solve object to set at index.
     */
    public void setSolve(int index, Solve solve) {
        solves.set(index, solve);
    }
    
    /**
     * Changes the time of the Solve object at index.
     * @param index The index of the Solve object whose time is to be changed.
     * @param time The time to change to.
     */
    public void setTime(int index, double time) {
        solves.get(index).setTime(time);
    }
    
    /**
     * Adds a solve to the SolveHandler.
     * @param solve The Solve object to add to the SolveHandler.
     */
    public void addSolve(Solve solve) {
        solves.add(solve);
    }
    
    /**
     * Deletes a solve at position index in the SolveHandler.
     * @param index The index of the Solve to remove.
     */
    public void deleteSolve(int index) {
        solves.remove(index);
    }
}
