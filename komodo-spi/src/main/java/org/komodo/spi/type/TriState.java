/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */

package org.komodo.spi.type;

/**
 * A three-state object.
 */
public enum TriState {

    /**
     * Equal to a <code>boolean</code> state of <code>false</code>.
     */
    FALSE,

    /**
     * Equal to a <code>boolean</code> state of <code>true</code>.
     */
    TRUE,

    /**
     * Indicates the state has not been set.
     */
    UNSET;

    /**
     * @param value
     *        the value used to create the <code>TriState</code> object (can be <code>null</code>)
     * @return the new instance (never <code>null</code>)
     */
    public static TriState get( final String value ) {
        if ( value == null ) {
            return UNSET;
        }
        
        return Boolean.parseBoolean( value ) ? TRUE : FALSE;
    }

    /**
     * @param value
     *        the value used to create the <code>TriState</code> object (can be <code>null</code>)
     * @return the new instance (never <code>null</code>)
     */
    public static TriState valueOf( final Boolean value ) {
        if ( value == null ) {
            return UNSET;
        }
        
        if ( value ) {
            return TRUE;
        }

        return TriState.FALSE;
    }

    /**
     * @return <code>true</code> if the state is {@link TriState#TRUE}; otherwise {@link TriState#FALSE}.
     * @throws IllegalStateException if the method is called when the state is {@link TriState#UNSET}.
     */
    public boolean booleanValue() throws IllegalStateException {
        if ( isFalse() ) {
            return false;
        }

        if ( isTrue() ) {
            return true;
        }

        throw new IllegalStateException();
    }

    /**
     * @return <code>true</code> if the state is {@link TriState#TRUE}.
     */
    public boolean isFalse() {
        return ( this == FALSE );
    }

    /**
     * @return <code>true</code> if the state is {@link TriState#FALSE}.
     */
    public boolean isTrue() {
        return ( this == TRUE );
    }

    /**
     * @return <code>true</code> if the state is {@link TriState#UNSET}.
     */
    public boolean isUnset() {
        return ( this == UNSET );
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        if ( this == TRUE ) {
            return "true"; //$NON-NLS-1$
        }
        
        if ( this == FALSE ) {
            return "false"; //$NON-NLS-1$
        }

        if ( this == TriState.UNSET ) {
            return null;
        }

        // should not get here
        throw new IllegalStateException( "Unknown enum value of " + this ); //$NON-NLS-1$
    }

}
