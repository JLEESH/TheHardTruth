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
        
        tDistribution = new TDistribution(statistics.getN() - 1);
    }
    
    //Note: Limit calls to other methods in the Statistics class in a method that calculates values to isolate methods.
    
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
        return Math.sqrt(statistics.getVariance() / statistics.getN());
    }
    
    /**
     * Returns a double representing the t-value.
     * @param time The time to test.
     * @return returns a double representing the t-value.
     */
    public double getTValue(double time) {
        return (time - statistics.getMean()) / Math.sqrt(statistics.getVariance() / statistics.getN());
    }
    
    /**
     * Returns a double representing the p-value for the hypothesis that the "average" of the cuber is at least a given time.
     * @param time The time to test.
     * @return returns a double representing the p-value.
     */
    public double getPValue(double time) {
        return tDistribution.cumulativeProbability((time - statistics.getMean()) / Math.sqrt(statistics.getVariance() / statistics.getN()));
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
        double pValue = tDistribution.cumulativeProbability((time - statistics.getMean()) / Math.sqrt(statistics.getVariance() / statistics.getN()));
        return  pValue < levelOfSignificance / 2 ? true : 1 - pValue < levelOfSignificance / 2;
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
        double pValue = tDistribution.cumulativeProbability((time - statistics.getMean()) / Math.sqrt(statistics.getVariance() / statistics.getN()));
        return  pValue < 0.025 ? true : 1 - pValue < 0.025;
    }
    
    /**
     * Returns an array representing the confidence interval at a 95% confidence level.
     * To get confidence intervals at other confidence levels, use getConfidenceInterval(double confidenceLevel).
     * @return returns an array with the first element representing the lower endpoint of the interval and the second element representing the upper endpoint of the interval.
     */
    public double[] getConfidenceInterval() {
        //To minimise repeated calculations of the same expression, a variable was used.
        double d = tDistribution.inverseCumulativeProbability(0.025) * Math.sqrt(statistics.getVariance() / statistics.getN());
        return new double[]{statistics.getMean() + d, statistics.getMean() - d};
    }
    
    /**
     * Returns an array representing the confidence interval at a certain confidence level.
     * @param confidenceLevel The desired level of confidence of the interval in decimal form.
     * @return returns an array with the first element representing the lower endpoint of the interval and the second element representing the upper endpoint of the interval.
     */
    public double[] getConfidenceInterval(double confidenceLevel) {
        //To minimise repeated calculations of the same expression, a variable was used.
        double d = tDistribution.inverseCumulativeProbability((1 - confidenceLevel) / 2) * Math.sqrt(statistics.getVariance() / statistics.getN());
        return new double[]{statistics.getMean() + d, statistics.getMean() - d};
    }
    
    /**
     * Returns an array representing the prediction interval at a 95% predictive confidence level.
     * Note that this assumes that the distribution of times is normal.
     * To get prediction intervals at other confidence levels, use getPredictionInterval(double predictiveConfidenceLevel).
     * @return returns an array with the first element representing the lower endpoint of the interval and the second element representing the upper endpoint of the interval.
     */
    public double[] getPredictionInterval() {
        //To minimise repeated calculations of the same expression, a variable was used.
        double d = tDistribution.inverseCumulativeProbability(0.025) * Math.sqrt(statistics.getVariance() * (1 + 1.0 / statistics.getN()));
        return new double[]{statistics.getMean() + d, statistics.getMean() - d};
    }
    /**
     * Returns an array representing the prediction interval at a certain predictive confidence level.
     * Note that this assumes that the distribution of times is normal.
     * @param predictiveConfidenceLevel The desired predictive confidence level of the interval in decimal form.
     * @return returns an array with the first element representing the lower endpoint of the interval and the second element representing the upper endpoint of the interval.
     */
    public double[] getPredictionInterval(double predictiveConfidenceLevel) {
        //To minimise repeated calculations of the same expression, a variable was used.
        double d = tDistribution.inverseCumulativeProbability((1 - predictiveConfidenceLevel) / 2) * Math.sqrt(statistics.getVariance() * (1 + 1.0 / statistics.getN()));
        return new double[]{statistics.getMean() + d, statistics.getMean() - d};
    }
}
