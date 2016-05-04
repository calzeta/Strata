/**
 * Copyright (C) 2016 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.strata.pricer.impl.tree;

import java.util.Arrays;

import com.opengamma.strata.collect.array.DoubleArray;
import com.opengamma.strata.collect.array.DoubleMatrix;

/**
 * Option function interface used in tree option pricing.
 */
public interface OptionFunction {

  /**
   * Obtains strike value.
   * 
   * @return strike
   */
  public abstract double getStrike();

  /**
   * Obtains time to expiry.
   * 
   * @return time to expiry
   */
  public abstract double getTimeToExpiry();

  /**
   * Computes payoff at expiry for trinomial tree.
   * <p>
   * The payoff values for individual nodes at expiry are computed.
   * If trinomial tree has {@code n} steps, the returned {@code DoubleArray} has the size {@code 2 * n + 1}.
   * 
   * @param spot  the spot
   * @param downFactor  the down factor
   * @param middleFactor  the middle factor
   * @param numberOfSteps  
   * @return the payoff at expiry
   */
  public default DoubleArray getPayoffAtExpiryTrinomial(
      double spot,
      double downFactor,
      double middleFactor,
      int numberOfSteps) {

    int nNodes = 2 * numberOfSteps + 1;
    double[] values = new double[nNodes];
    for (int i = 0; i < nNodes; ++i) {
      values[i] = spot * Math.pow(downFactor, numberOfSteps - i) * Math.pow(middleFactor, i);
    }
    return getPayoffAtExpiryTrinomial(DoubleArray.ofUnsafe(values), numberOfSteps);
  }

  /**
   * Computes payoff at expiry for trinomial tree.
   * 
   * @param stateValue
   * @return
   */
  public abstract DoubleArray getPayoffAtExpiryTrinomial(DoubleArray stateValue, int numberOfSteps);

  /**
   * Computes the option values in the intermediate nodes.
   * <p>
   * Given a set of option values in the (i+1)-th layer, option values in the i-th layer are derived.
   * For an option with path-dependence, {@link #getNextOptionValues(double, DoubleMatrix, DoubleArray, DoubleArray, int)} 
   * should be overridden rather than this method. 
   * <p>
   * The size of {@code values} must be (2*i+3). However, this is not checked because of its repeated usage.
   * 
   * @param discountFactor  the discount factor between the two layers
   * @param upProbability  the up probability
   * @param middleProbability  the middle probability
   * @param downProbability  the down probability
   * @param values  the option values in the (i+1)-th layer
   * @param spot  the spot
   * @param downFactor  the down factor
   * @param middleFactor  the middle factor
   * @param i  the steps
   * @return the option values in the i-th layer
   */
  public default DoubleArray getNextOptionValues(
      double discountFactor,
      double upProbability,
      double middleProbability,
      double downProbability,
      DoubleArray values,
      double spot,
      double downFactor,
      double middleFactor,
      int i) {

    int nNodes = 2 * i + 1;
    //    double[] res = new double[nNodes];

    double[] probsAtNode = new double[] {downProbability, middleProbability, upProbability };
    double[][] probs = new double[nNodes][];
    Arrays.fill(probs, probsAtNode);
    DoubleArray stateValue = DoubleArray.of(nNodes, k -> spot * Math.pow(downFactor, i - k) * Math.pow(middleFactor, k));
    //    
    //    for (int j = 0; j < nNodes; ++j) {
    //      res[j] = discountFactor *
    //          (upProbability * values.get(j + 2) + middleProbability * values.get(j + 1) + downProbability * values.get(j));
    //    }
    //    return DoubleArray.ofUnsafe(res);
    return getNextOptionValues(discountFactor, DoubleMatrix.ofUnsafe(probs), stateValue, values, i);
  }

  /**
   * Computes the option values in the intermediate nodes.
   * <p>
   * Given a set of option values in the (i+1)-th layer, option values in the i-th layer are derived.
   * The down, middle and up probabilities of the j-th lowest node are stored in the {i,0}, {i,1}, {i,2} components of  
   * {@code transitionProbability}, respectively.
   * <p>
   * For an option with path-dependence, this method should be overridden. 
   * 
   * @param discountFactor  the discount factor between the two layers
   * @param transitionProbability  the transition probability
   * @param stateValue  the state value
   * @param values  the option value
   * @param i  the steps
   * @return the option values in the i-th layer
   */
  public default DoubleArray getNextOptionValues(
      double discountFactor,
      DoubleMatrix transitionProbability,
      DoubleArray stateValue,
      DoubleArray values,
      int i) {

    int nNodes = 2 * i + 1;
    double[] res = new double[nNodes];
    for (int j = 0; j < nNodes; ++j) {
      double upProbability = transitionProbability.get(j, 2);
      double middleProbability = transitionProbability.get(j, 1);
      double downProbability = transitionProbability.get(j, 0);
      res[j] = discountFactor *
          (upProbability * values.get(j + 2) + middleProbability * values.get(j + 1) + downProbability * values.get(j));
    }
    return DoubleArray.ofUnsafe(res);
  }

}