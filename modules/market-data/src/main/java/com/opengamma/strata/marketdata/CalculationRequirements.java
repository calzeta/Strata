/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.marketdata;

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

import com.google.common.collect.ImmutableSet;
import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.marketdata.key.MarketDataKey;
import com.opengamma.strata.marketdata.key.ObservableKey;

/**
 * Specifies the market data required for a calculation.
 */
@BeanDefinition
public final class CalculationRequirements implements ImmutableBean {

  /** An empty set of requirements. */
  private static final CalculationRequirements EMPTY = CalculationRequirements.builder().build();

  /** Keys identifying the market data values required for the calculations. */
  @PropertyDefinition(validate = "notNull")
  private final ImmutableSet<? extends MarketDataKey<?>> singleValueRequirements;

  /** Keys identifying the time series of market data values required for the calculations. */
  @PropertyDefinition(validate = "notNull")
  private final ImmutableSet<ObservableKey> timeSeriesRequirements;

  // TODO Requirements for global values - will be required when they are integrated into market data building

  /**
   * The currencies used in the calculation results. The market data must include FX rates in the
   * to allow conversion into the reporting currency. The FX rates must have the output currency as the base
   * currency and the reporting currency as the counter currency.
   */
  @PropertyDefinition(validate = "notNull")
  private final ImmutableSet<Currency> outputCurrencies;

  /**
   * Returns an empty set of requirements.
   *
   * @return an empty set of requirements
   */
  public static CalculationRequirements empty() {
    return EMPTY;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code CalculationRequirements}.
   * @return the meta-bean, not null
   */
  public static CalculationRequirements.Meta meta() {
    return CalculationRequirements.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(CalculationRequirements.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static CalculationRequirements.Builder builder() {
    return new CalculationRequirements.Builder();
  }

  private CalculationRequirements(
      Set<? extends MarketDataKey<?>> singleValueRequirements,
      Set<ObservableKey> timeSeriesRequirements,
      Set<Currency> outputCurrencies) {
    JodaBeanUtils.notNull(singleValueRequirements, "singleValueRequirements");
    JodaBeanUtils.notNull(timeSeriesRequirements, "timeSeriesRequirements");
    JodaBeanUtils.notNull(outputCurrencies, "outputCurrencies");
    this.singleValueRequirements = ImmutableSet.copyOf(singleValueRequirements);
    this.timeSeriesRequirements = ImmutableSet.copyOf(timeSeriesRequirements);
    this.outputCurrencies = ImmutableSet.copyOf(outputCurrencies);
  }

  @Override
  public CalculationRequirements.Meta metaBean() {
    return CalculationRequirements.Meta.INSTANCE;
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
   * Gets keys identifying the market data values required for the calculations.
   * @return the value of the property, not null
   */
  public ImmutableSet<? extends MarketDataKey<?>> getSingleValueRequirements() {
    return singleValueRequirements;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets keys identifying the time series of market data values required for the calculations.
   * @return the value of the property, not null
   */
  public ImmutableSet<ObservableKey> getTimeSeriesRequirements() {
    return timeSeriesRequirements;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the currencies used in the calculation results. The market data must include FX rates in the
   * to allow conversion into the reporting currency. The FX rates must have the output currency as the base
   * currency and the reporting currency as the counter currency.
   * @return the value of the property, not null
   */
  public ImmutableSet<Currency> getOutputCurrencies() {
    return outputCurrencies;
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
      CalculationRequirements other = (CalculationRequirements) obj;
      return JodaBeanUtils.equal(getSingleValueRequirements(), other.getSingleValueRequirements()) &&
          JodaBeanUtils.equal(getTimeSeriesRequirements(), other.getTimeSeriesRequirements()) &&
          JodaBeanUtils.equal(getOutputCurrencies(), other.getOutputCurrencies());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getSingleValueRequirements());
    hash = hash * 31 + JodaBeanUtils.hashCode(getTimeSeriesRequirements());
    hash = hash * 31 + JodaBeanUtils.hashCode(getOutputCurrencies());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(128);
    buf.append("CalculationRequirements{");
    buf.append("singleValueRequirements").append('=').append(getSingleValueRequirements()).append(',').append(' ');
    buf.append("timeSeriesRequirements").append('=').append(getTimeSeriesRequirements()).append(',').append(' ');
    buf.append("outputCurrencies").append('=').append(JodaBeanUtils.toString(getOutputCurrencies()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code CalculationRequirements}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code singleValueRequirements} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableSet<? extends MarketDataKey<?>>> singleValueRequirements = DirectMetaProperty.ofImmutable(
        this, "singleValueRequirements", CalculationRequirements.class, (Class) ImmutableSet.class);
    /**
     * The meta-property for the {@code timeSeriesRequirements} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableSet<ObservableKey>> timeSeriesRequirements = DirectMetaProperty.ofImmutable(
        this, "timeSeriesRequirements", CalculationRequirements.class, (Class) ImmutableSet.class);
    /**
     * The meta-property for the {@code outputCurrencies} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableSet<Currency>> outputCurrencies = DirectMetaProperty.ofImmutable(
        this, "outputCurrencies", CalculationRequirements.class, (Class) ImmutableSet.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "singleValueRequirements",
        "timeSeriesRequirements",
        "outputCurrencies");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -442841799:  // singleValueRequirements
          return singleValueRequirements;
        case -1437279660:  // timeSeriesRequirements
          return timeSeriesRequirements;
        case -1022597040:  // outputCurrencies
          return outputCurrencies;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public CalculationRequirements.Builder builder() {
      return new CalculationRequirements.Builder();
    }

    @Override
    public Class<? extends CalculationRequirements> beanType() {
      return CalculationRequirements.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code singleValueRequirements} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableSet<? extends MarketDataKey<?>>> singleValueRequirements() {
      return singleValueRequirements;
    }

    /**
     * The meta-property for the {@code timeSeriesRequirements} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableSet<ObservableKey>> timeSeriesRequirements() {
      return timeSeriesRequirements;
    }

    /**
     * The meta-property for the {@code outputCurrencies} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableSet<Currency>> outputCurrencies() {
      return outputCurrencies;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -442841799:  // singleValueRequirements
          return ((CalculationRequirements) bean).getSingleValueRequirements();
        case -1437279660:  // timeSeriesRequirements
          return ((CalculationRequirements) bean).getTimeSeriesRequirements();
        case -1022597040:  // outputCurrencies
          return ((CalculationRequirements) bean).getOutputCurrencies();
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
   * The bean-builder for {@code CalculationRequirements}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<CalculationRequirements> {

    private Set<? extends MarketDataKey<?>> singleValueRequirements = ImmutableSet.of();
    private Set<ObservableKey> timeSeriesRequirements = ImmutableSet.of();
    private Set<Currency> outputCurrencies = ImmutableSet.of();

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(CalculationRequirements beanToCopy) {
      this.singleValueRequirements = beanToCopy.getSingleValueRequirements();
      this.timeSeriesRequirements = beanToCopy.getTimeSeriesRequirements();
      this.outputCurrencies = beanToCopy.getOutputCurrencies();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -442841799:  // singleValueRequirements
          return singleValueRequirements;
        case -1437279660:  // timeSeriesRequirements
          return timeSeriesRequirements;
        case -1022597040:  // outputCurrencies
          return outputCurrencies;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -442841799:  // singleValueRequirements
          this.singleValueRequirements = (Set<? extends MarketDataKey<?>>) newValue;
          break;
        case -1437279660:  // timeSeriesRequirements
          this.timeSeriesRequirements = (Set<ObservableKey>) newValue;
          break;
        case -1022597040:  // outputCurrencies
          this.outputCurrencies = (Set<Currency>) newValue;
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
    public CalculationRequirements build() {
      return new CalculationRequirements(
          singleValueRequirements,
          timeSeriesRequirements,
          outputCurrencies);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code singleValueRequirements} property in the builder.
     * @param singleValueRequirements  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder singleValueRequirements(Set<? extends MarketDataKey<?>> singleValueRequirements) {
      JodaBeanUtils.notNull(singleValueRequirements, "singleValueRequirements");
      this.singleValueRequirements = singleValueRequirements;
      return this;
    }

    /**
     * Sets the {@code timeSeriesRequirements} property in the builder.
     * @param timeSeriesRequirements  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder timeSeriesRequirements(Set<ObservableKey> timeSeriesRequirements) {
      JodaBeanUtils.notNull(timeSeriesRequirements, "timeSeriesRequirements");
      this.timeSeriesRequirements = timeSeriesRequirements;
      return this;
    }

    /**
     * Sets the {@code timeSeriesRequirements} property in the builder
     * from an array of objects.
     * @param timeSeriesRequirements  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder timeSeriesRequirements(ObservableKey... timeSeriesRequirements) {
      return timeSeriesRequirements(ImmutableSet.copyOf(timeSeriesRequirements));
    }

    /**
     * Sets the {@code outputCurrencies} property in the builder.
     * @param outputCurrencies  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder outputCurrencies(Set<Currency> outputCurrencies) {
      JodaBeanUtils.notNull(outputCurrencies, "outputCurrencies");
      this.outputCurrencies = outputCurrencies;
      return this;
    }

    /**
     * Sets the {@code outputCurrencies} property in the builder
     * from an array of objects.
     * @param outputCurrencies  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder outputCurrencies(Currency... outputCurrencies) {
      return outputCurrencies(ImmutableSet.copyOf(outputCurrencies));
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(128);
      buf.append("CalculationRequirements.Builder{");
      buf.append("singleValueRequirements").append('=').append(JodaBeanUtils.toString(singleValueRequirements)).append(',').append(' ');
      buf.append("timeSeriesRequirements").append('=').append(JodaBeanUtils.toString(timeSeriesRequirements)).append(',').append(' ');
      buf.append("outputCurrencies").append('=').append(JodaBeanUtils.toString(outputCurrencies));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
