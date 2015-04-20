/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.finance.rate.swap;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.opengamma.strata.basics.date.DayCount;
import com.opengamma.strata.basics.index.Index;
import com.opengamma.strata.basics.schedule.Schedule;
import com.opengamma.strata.basics.schedule.SchedulePeriod;
import com.opengamma.strata.basics.value.ValueSchedule;
import com.opengamma.strata.collect.ArgChecker;
import com.opengamma.strata.finance.rate.FixedRateObservation;

/**
 * Defines the calculation of a fixed rate swap leg.
 * <p>
 * This defines the data necessary to calculate the amount payable on the leg.
 * The amount is based on a fixed rate, which can vary over the lifetime of the leg.
 */
@BeanDefinition
public final class FixedRateCalculation
    implements RateCalculation, ImmutableBean, Serializable {

  /**
   * The day count convention applicable.
   * <p>
   * This is used to convert schedule period dates to a numerical value.
   */
  @PropertyDefinition(validate = "notNull")
  private final DayCount dayCount;
  /**
   * The interest rate to be paid.
   * A 5% rate will be expressed as 0.05.
   * <p>
   * This defines the rate as an initial amount and a list of adjustments.
   * The rate is only permitted to change at accrual period boundaries.
   */
  @PropertyDefinition(validate = "notNull")
  private final ValueSchedule rate;

  //-------------------------------------------------------------------------
  /**
   * Obtains a rate calculation for the specified day count and rate.
   * <p>
   * The rate specified here does not vary during the life of the swap.
   * If this method provides insufficient control, use the {@linkplain #builder() builder}.
   * 
   * @param rate  the rate
   * @param dayCount  the day count
   * @return the calculation
   */
  public static FixedRateCalculation of(double rate, DayCount dayCount) {
    return FixedRateCalculation.builder()
        .dayCount(dayCount)
        .rate(ValueSchedule.of(rate))
        .build();
  }

  //-------------------------------------------------------------------------
  @Override
  public SwapLegType getType() {
    return SwapLegType.FIXED;
  }

  @Override
  public void collectIndices(ImmutableSet.Builder<Index> builder) {
    // no indices
  }

  @Override
  public ImmutableList<RateAccrualPeriod> expand(Schedule accrualSchedule, Schedule paymentSchedule) {
    ArgChecker.notNull(accrualSchedule, "accrualSchedule");
    ArgChecker.notNull(paymentSchedule, "paymentSchedule");
    // resolve data by schedule
    List<Double> resolvedRates = rate.resolveValues(accrualSchedule.getPeriods());
    // build accrual periods
    ImmutableList.Builder<RateAccrualPeriod> accrualPeriods = ImmutableList.builder();
    for (int i = 0; i < accrualSchedule.size(); i++) {
      SchedulePeriod period = accrualSchedule.getPeriod(i);
      accrualPeriods.add(RateAccrualPeriod.builder(period)
          .yearFraction(period.yearFraction(dayCount, accrualSchedule))
          .rateObservation(FixedRateObservation.of(resolvedRates.get(i)))
          .build());
    }
    return accrualPeriods.build();
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code FixedRateCalculation}.
   * @return the meta-bean, not null
   */
  public static FixedRateCalculation.Meta meta() {
    return FixedRateCalculation.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(FixedRateCalculation.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static FixedRateCalculation.Builder builder() {
    return new FixedRateCalculation.Builder();
  }

  private FixedRateCalculation(
      DayCount dayCount,
      ValueSchedule rate) {
    JodaBeanUtils.notNull(dayCount, "dayCount");
    JodaBeanUtils.notNull(rate, "rate");
    this.dayCount = dayCount;
    this.rate = rate;
  }

  @Override
  public FixedRateCalculation.Meta metaBean() {
    return FixedRateCalculation.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the day count convention applicable.
   * <p>
   * This is used to convert schedule period dates to a numerical value.
   * @return the value of the property, not null
   */
  public DayCount getDayCount() {
    return dayCount;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the interest rate to be paid.
   * A 5% rate will be expressed as 0.05.
   * <p>
   * This defines the rate as an initial amount and a list of adjustments.
   * The rate is only permitted to change at accrual period boundaries.
   * @return the value of the property, not null
   */
  public ValueSchedule getRate() {
    return rate;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      FixedRateCalculation other = (FixedRateCalculation) obj;
      return JodaBeanUtils.equal(getDayCount(), other.getDayCount()) &&
          JodaBeanUtils.equal(getRate(), other.getRate());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getDayCount());
    hash = hash * 31 + JodaBeanUtils.hashCode(getRate());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("FixedRateCalculation{");
    buf.append("dayCount").append('=').append(getDayCount()).append(',').append(' ');
    buf.append("rate").append('=').append(JodaBeanUtils.toString(getRate()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code FixedRateCalculation}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code dayCount} property.
     */
    private final MetaProperty<DayCount> dayCount = DirectMetaProperty.ofImmutable(
        this, "dayCount", FixedRateCalculation.class, DayCount.class);
    /**
     * The meta-property for the {@code rate} property.
     */
    private final MetaProperty<ValueSchedule> rate = DirectMetaProperty.ofImmutable(
        this, "rate", FixedRateCalculation.class, ValueSchedule.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "dayCount",
        "rate");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1905311443:  // dayCount
          return dayCount;
        case 3493088:  // rate
          return rate;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public FixedRateCalculation.Builder builder() {
      return new FixedRateCalculation.Builder();
    }

    @Override
    public Class<? extends FixedRateCalculation> beanType() {
      return FixedRateCalculation.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code dayCount} property.
     * @return the meta-property, not null
     */
    public MetaProperty<DayCount> dayCount() {
      return dayCount;
    }

    /**
     * The meta-property for the {@code rate} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ValueSchedule> rate() {
      return rate;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 1905311443:  // dayCount
          return ((FixedRateCalculation) bean).getDayCount();
        case 3493088:  // rate
          return ((FixedRateCalculation) bean).getRate();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code FixedRateCalculation}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<FixedRateCalculation> {

    private DayCount dayCount;
    private ValueSchedule rate;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(FixedRateCalculation beanToCopy) {
      this.dayCount = beanToCopy.getDayCount();
      this.rate = beanToCopy.getRate();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1905311443:  // dayCount
          return dayCount;
        case 3493088:  // rate
          return rate;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 1905311443:  // dayCount
          this.dayCount = (DayCount) newValue;
          break;
        case 3493088:  // rate
          this.rate = (ValueSchedule) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public FixedRateCalculation build() {
      return new FixedRateCalculation(
          dayCount,
          rate);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code dayCount} property in the builder.
     * @param dayCount  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder dayCount(DayCount dayCount) {
      JodaBeanUtils.notNull(dayCount, "dayCount");
      this.dayCount = dayCount;
      return this;
    }

    /**
     * Sets the {@code rate} property in the builder.
     * @param rate  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder rate(ValueSchedule rate) {
      JodaBeanUtils.notNull(rate, "rate");
      this.rate = rate;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("FixedRateCalculation.Builder{");
      buf.append("dayCount").append('=').append(JodaBeanUtils.toString(dayCount)).append(',').append(' ');
      buf.append("rate").append('=').append(JodaBeanUtils.toString(rate));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
