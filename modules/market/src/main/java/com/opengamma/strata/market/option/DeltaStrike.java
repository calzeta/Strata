/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.strata.market.option;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableValidator;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.strata.collect.ArgChecker;

/**
 * A strike based on absolute delta.
 * <p>
 * The absolute delta of a call option, D_c. This is in the range (0,1), where 0.5 is ATM (Delta-Neutral Straddle DNS), 
 * D_c > 0.5 are ITM  and D_c < 0.5 are OTM options.
 * The absolute delta of a put option is related by D_p = D_c - 1. Since prices are normally quoted for OTM options, 
 * D_c < 0.5 will be from calls, while D_c > 0.5 (D_p > -0.5) will be from puts.
 */
@BeanDefinition(builderScope = "private")
public final class DeltaStrike
    implements Strike, ImmutableBean, Serializable {

  /**
   * The value of absolute delta.
   */
  @PropertyDefinition(overrideGet = true)
  private final double value;

  /**
   * Obtains an instance of {@code Delta} with the value of absolute delta.
   * 
   * @param delta  the value of absolute delta
   * @return the instance
   */
  public static DeltaStrike of(double delta) {
    return new DeltaStrike(delta);
  }

  @ImmutableValidator
  private void validate() {
    ArgChecker.isTrue(value >= 0 && value <= 1.0, "Delta must be in the range (0,1)");
  }

  //-------------------------------------------------------------------------
  @Override
  public StrikeType getType() {
    return StrikeType.DELTA;
  }

  @Override
  public DeltaStrike withValue(double value) {
    return new DeltaStrike(value);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code DeltaStrike}.
   * @return the meta-bean, not null
   */
  public static DeltaStrike.Meta meta() {
    return DeltaStrike.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(DeltaStrike.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private DeltaStrike(
      double value) {
    this.value = value;
    validate();
  }

  @Override
  public DeltaStrike.Meta metaBean() {
    return DeltaStrike.Meta.INSTANCE;
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
   * Gets the value of absolute delta.
   * @return the value of the property
   */
  @Override
  public double getValue() {
    return value;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      DeltaStrike other = (DeltaStrike) obj;
      return JodaBeanUtils.equal(value, other.value);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(value);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("DeltaStrike{");
    buf.append("value").append('=').append(JodaBeanUtils.toString(value));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code DeltaStrike}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code value} property.
     */
    private final MetaProperty<Double> value = DirectMetaProperty.ofImmutable(
        this, "value", DeltaStrike.class, Double.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "value");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 111972721:  // value
          return value;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends DeltaStrike> builder() {
      return new DeltaStrike.Builder();
    }

    @Override
    public Class<? extends DeltaStrike> beanType() {
      return DeltaStrike.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code value} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Double> value() {
      return value;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 111972721:  // value
          return ((DeltaStrike) bean).getValue();
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
   * The bean-builder for {@code DeltaStrike}.
   */
  private static final class Builder extends DirectFieldsBeanBuilder<DeltaStrike> {

    private double value;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 111972721:  // value
          return value;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 111972721:  // value
          this.value = (Double) newValue;
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
    public DeltaStrike build() {
      return new DeltaStrike(
          value);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(64);
      buf.append("DeltaStrike.Builder{");
      buf.append("value").append('=').append(JodaBeanUtils.toString(value));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
