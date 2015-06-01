/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.function.marketdata.scenarios.curves;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.testng.annotations.Test;

import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.collect.TestHelper;
import com.opengamma.strata.engine.marketdata.BaseMarketData;
import com.opengamma.strata.engine.marketdata.DefaultMarketDataFactory;
import com.opengamma.strata.engine.marketdata.MarketDataRequirements;
import com.opengamma.strata.engine.marketdata.ScenarioMarketData;
import com.opengamma.strata.engine.marketdata.ScenarioMarketDataResult;
import com.opengamma.strata.engine.marketdata.config.MarketDataConfig;
import com.opengamma.strata.engine.marketdata.functions.ObservableMarketDataFunction;
import com.opengamma.strata.engine.marketdata.functions.TimeSeriesProvider;
import com.opengamma.strata.engine.marketdata.mapping.FeedIdMapping;
import com.opengamma.strata.engine.marketdata.scenarios.PerturbationMapping;
import com.opengamma.strata.engine.marketdata.scenarios.ScenarioDefinition;
import com.opengamma.strata.market.curve.ConstantNodalCurve;
import com.opengamma.strata.market.curve.Curve;
import com.opengamma.strata.market.curve.CurveGroupName;
import com.opengamma.strata.market.curve.CurveName;
import com.opengamma.strata.market.id.DiscountCurveId;

@Test
public class CurveParallelShiftTest {

  public void absolute() {
    CurveParallelShift shift = CurveParallelShift.absolute(0.1);
    Curve shiftedCurve = shift.apply(ConstantNodalCurve.of("curveName", 2d));
    checkCurveValues(shiftedCurve, 2.1);
  }

  public void relative() {
    CurveParallelShift shift = CurveParallelShift.relative(0.1);
    Curve shiftedCurve = shift.apply(ConstantNodalCurve.of("curveName", 2d));
    checkCurveValues(shiftedCurve, 2.2);
  }

  public void absoluteScenarios() {
    CurveName curveName = CurveName.of("curveName");
    CurveGroupName curveGroupName = CurveGroupName.of("curveGroupName");
    Curve curve = ConstantNodalCurve.of(curveName, 2);
    PerturbationMapping<Curve> mapping = PerturbationMapping.of(
        Curve.class,
        CurveNameFilter.of(curveName),
        CurveParallelShift.absolute(0.1),
        CurveParallelShift.absolute(0.2),
        CurveParallelShift.absolute(0.3));
    DiscountCurveId curveId = DiscountCurveId.of(Currency.GBP, curveGroupName);
    BaseMarketData marketData = BaseMarketData.builder(TestHelper.date(2011, 3, 8))
        .addValue(curveId, curve)
        .build();
    ScenarioDefinition scenarioDefinition = ScenarioDefinition.ofMappings(mapping);
    DefaultMarketDataFactory marketDataFactory = new DefaultMarketDataFactory(
        mock(TimeSeriesProvider.class),
        mock(ObservableMarketDataFunction.class),
        FeedIdMapping.identity());
    MarketDataRequirements requirements = MarketDataRequirements.builder().addValues(curveId).build();
    ScenarioMarketDataResult result = marketDataFactory.buildScenarioMarketData(
        requirements,
        marketData,
        scenarioDefinition,
        MarketDataConfig.empty());
    ScenarioMarketData scenarioData = result.getMarketData();
    List<Curve> curves = scenarioData.getValues(curveId);
    assertThat(curves).hasSize(3);
    checkCurveValues(curves.get(0), 2.1);
    checkCurveValues(curves.get(1), 2.2);
    checkCurveValues(curves.get(2), 2.3);
  }

  // It's not possible to do an equality test on the curves because shifting them wraps them in a different type
  private void checkCurveValues(Curve curve, double expectedValue) {
    for (int i = 0; i < 10; i++) {
      assertThat(curve.yValue((double) i)).isEqualTo(expectedValue);
    }
  }
}