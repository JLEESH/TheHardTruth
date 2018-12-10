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

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

/**
 * A class that deals with the relevant statistics.
 */
public class Statistics {
    
    private SummaryStatistics statistics;
    private TDistribution tDistribution;
    
    /**
     * Creates a Statistics object given an array of cubing times.
     * @param times The times to add while making the object.
     */
    public Statistics(double[] times) {
        statistics = new SummaryStatistics();
        tDistribution = new TDistribution(times.length - 1);
        
        for (int i = 0; i < times.length; ++i) {
            statistics.addValue(times[i]);
        }
    }
    
    /**
     * Deletes all previous values and updates the object with new values.
     * @param times The times to add after resetting the object.
     */
    public void setTimes(double[] times) {
        statistics.clear();
        tDistribution = new TDistribution(times.length - 1);
        
        for (int i = 0; i < times.length; ++i) {
            statistics.addValue(times[i]);
        }
    }
    
    /**
     * Adds new values.
     * @param times The times to add to the object.
     */
    public void addTimes(double[] times) {
        for (int i = 0; i < times.length; ++i) {
            statistics.addValue(times[i]);
        }
    }
    
    /**
     * Returns a double representing the sample mean of the data.
     * @return returns a double representing the sample mean of the data.
     */
    public double getMean() {
        return statistics.getMean();
    }
    
    /**
     * Returns a double representing the sample variance of the data.
     * @return returns a double representing the sample variance of the data.
     */
    public double getVariance() {
        return statistics.getVariance();
    }
    
    /**
     * Returns a double representing the sample standard deviation of the data.
     * @return returns a double representing the sample standard deviation of the data.
     */
    public double getStdDev() {
        return Math.sqrt(statistics.getVariance());
    }
    
    /**
     * Returns a double representing the standard error of the mean of the data.
     * @return returns a double representing the standard error of the mean of the data.
     */
    public double getSem() {
        return Math.sqrt(statistics.getVariance()) / Math.sqrt(statistics.getN());
    }
    
    /**
     * Returns a double representing the t-value.
     * @param time The time to test.
     * @return returns a double representing the t-value.
     */
    public double getTValue(double time) {
        return (getMean() - time) / getSem();
    }
    
    /**
     * Returns a double representing the p-value.
     * @param time The time to test.
     * @return returns a double representing the p-value.
     */
    public double getPValue(double time) {
        return 1 - tDistribution.cumulativeProbability(((getMean() - time) / getSem()));
    }
    
    /**
     * Evaluates whether a hypothesised "average" (in cubing terms; strictly the statistical population mean) 
     * of a cuber is likely to be his or her actual "average", 
     * given a level of significance.
     * @param time The hypothesised average of the cuber.
     * @param levelOfSignificance The value for smaller than which a p-value becomes significant.
     * @return returns true if the p-value is significant, 
     * false if it is not.
     */
    public boolean testAverage(double time, double levelOfSignificance) {
        return  (1 - tDistribution.cumulativeProbability(((getMean() - time) / getSem()))) > levelOfSignificance;
    }
    
    /**
     * Evaluates whether a hypothesised "average" (in cubing terms; strictly the statistical population mean) 
     * of a cuber is likely to be his or her actual "average, 
     * tested at a 5% level of significance.
     * @param time The hypothesised average of the cuber.
     * @return returns true if the p-value is smaller than 0.05, 
     * false if it is not.
     */
    public boolean testAverage(double time) {
        return (1 - tDistribution.cumulativeProbability(((getMean() - time) / getSem()))) > 0.05;
    }
}
