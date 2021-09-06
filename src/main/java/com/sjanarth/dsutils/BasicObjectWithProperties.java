package com.sjanarth.dsutils;

import java.util.*;

/**
 * A simple object class with associated properties that are modelled as key/value pairs.
 *
 * The values associated with the properties could be of primitive types or standard java collection classes
 * or any valid java class. If the values are of a type that inherits from java.util.Collection, this implementation
 * automatically appends the new values to the existing values without having to explicitly do so.
 *
 * @see <a href="#addProperty">addProperty</a> for more info.
 */
public class BasicObjectWithProperties
{
    /**
     * Constructs an empty instance of BasicObjectWithProperties.
     */
    public BasicObjectWithProperties()  {
        props = new HashMap<>();
    }

    /**
     * Fetch the value of the property associated with the given key.
     * @param key the key whose value must be fetched.
     * @return the value currently associated with the property.
     */
    public Optional<Object> getProperty (Object key)	{
        return Optional.ofNullable(props.get(key));
    }

    /**
     * Sets the value of the property associated with the given key.
     * @param key the key of the property whose value must be set.
     * @param value the value to associate with the property.
     * @return the previous value associated with the property.
     */
    public Optional<Object> setProperty (Object key, Object value)	{
        Object oldValue = props.get(key);
        props.put(key, value);
        return Optional.ofNullable(oldValue);
    }

    /**
     * Adds a given value to a property identified by the key.
     * @param key the key of the property whose value must be modified.
     * @param value the value to set the property to.
     * <p>
     *        If the property already exists and has a current value
     *          that is a java collection, this method will add the given
     *          value to the collection and not overwrite the previous values.
     *        If the property already exists and has a current value
     *          that is not a java collection, this method will create a new
     *          <a href="java.util.Set">java.util.Set</a> to hold the values of this
     *          property and initialize it with the both the previous and the current values.
     *        If the property does not already exist, this method will create a new
     *          <a href="java.util.Set">java.util.Set</a> to hold the values of this
     *          property and initialize it with the given value.
     * </p>
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addProperty (Object key, Object value) {
        Object oldValue = props.get(key);
        if (oldValue != null)	{
            if (oldValue instanceof Collection)	{
                Collection ov = (Collection) oldValue;
                if (value instanceof Collection)
                    ov.addAll((Collection)value);
                else
                    ov.add(value);
            } else if (oldValue instanceof Map)	{
                Map ov = (Map) oldValue;
                if (value instanceof Map)
                    ov.putAll((Map)value);
                else
                    setProperty(key, value);
            } else {
                Set<Object> sov = new HashSet<>();
                sov.add(oldValue);
                sov.add(value);
                setProperty(key, sov);
            }
        } else {
            if (value instanceof Collection || value instanceof Map)    {
                setProperty(key, value);
            } else {
                Set<Object> sov = new HashSet<>();
                sov.add(value);
                setProperty(key, sov);
            }
        }
    }

    protected Map<Object,Object> props;
}
