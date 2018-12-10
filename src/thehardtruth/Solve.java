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

/**
 * A class representing a solve.
 */
public class Solve {
    private double time;
    private String scramble;
    private String comment;
    private boolean isDnf;
    private boolean hasPenalty;
    
    /**
     * Creates a solve based on the solve time alone.
     * @param time The recorded time of the solve.
     */
    public Solve(double time) {
        this.time = time;
        scramble = "No scramble data found.";
        comment = "No comments found.";
        isDnf = false;
        hasPenalty = false;
    }
    
    /**
     * Creates a solve based on the solve time and the scramble.
     * @param time The recorded time of the solve.
     * @param scramble The scramble of the solve.
     */
    public Solve(double time, String scramble) {
        this.time = time;
        this.scramble = scramble;
        this.comment = "No comments found.";
        isDnf = false;
        hasPenalty = false;
    }
    
    /**
     * Creates a solve based on a variety of parameters.
     * @param time The recorded time of the solve.
     * @param scramble The scramble of the solve.
     * @param comment The comments on the solve.
     * @param isDnf Whether the solve was a DNF.
     * @param hasPenalty Whether the solve had a penalty (does not alter the solve time automatically).
     */
    public Solve(double time, String scramble, String comment, boolean isDnf, boolean hasPenalty) {
        this.time = time;
        this.scramble = scramble;
        this.comment = comment;
        this.isDnf = isDnf;
        this.hasPenalty = hasPenalty;
    }
    
    public void setTime(double time) {
        this.time = time;
    }
    
    public void setScramble(String scramble) {
        this.scramble = scramble;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public void setIsDnf(boolean isDnf) {
        this.isDnf = isDnf;
    }
    
    public void setHasPenalty(boolean hasPenalty) {
        this.hasPenalty = hasPenalty;
    }
    
    public double getTime() {
        return time;
    }
    
    public String getScramble() {
        return scramble;
    }
    
    public String getComment() {
        return comment;
    }
    
    public boolean getIsDnf() {
        return isDnf;
    }
    
    public boolean getHasPenalty() {
        return hasPenalty;
    }
}
