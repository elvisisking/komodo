/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.komodo.spi.type;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

@SuppressWarnings( { "javadoc", "nls" } )
public final class TriStateTest {

    @Test
    public void shouldBeFalse() {
        final TriState actual = TriState.FALSE;
        assertThat( actual.isFalse(), is( true ) );
        assertThat( actual.isTrue(), is( false ) );
        assertThat( actual.isUnset(), is( false ) );
    }

    @Test
    public void shouldBeTrue() {
        final TriState actual = TriState.TRUE;
        assertThat( actual.isTrue(), is( true ) );
        assertThat( actual.isFalse(), is( false ) );
        assertThat( actual.isUnset(), is( false ) );
    }

    @Test
    public void shouldBeUnset() {
        final TriState actual = TriState.UNSET;
        assertThat( actual.isUnset(), is( true ) );
        assertThat( actual.isFalse(), is( false ) );
        assertThat( actual.isTrue(), is( false ) );
    }

    @Test( expected = IllegalStateException.class )
    public void shouldErrorBooleanValueOfUnset() {
        final TriState actual = TriState.UNSET;
        actual.booleanValue();
    }

    @Test
    public void shouldGetFalse() {
        assertThat( TriState.get( "false" ), is( TriState.FALSE ) );
        assertThat( TriState.get( "FALSE" ), is( TriState.FALSE ) );
    }

    @Test
    public void shouldGetTrue() {
        assertThat( TriState.get( "true" ), is( TriState.TRUE ) );
        assertThat( TriState.get( "TRUE" ), is( TriState.TRUE ) );
    }

    @Test
    public void shouldGetUnset() {
        assertThat( TriState.get( null ), is( TriState.UNSET ) );
    }

    @Test
    public void shouldHaveBooleanValueOfFalse() {
        final TriState actual = TriState.FALSE;
        assertThat( actual.booleanValue(), is( false ) );
    }

    @Test
    public void shouldHaveBooleanValueOfTrue() {
        final TriState actual = TriState.TRUE;
        assertThat( actual.booleanValue(), is( true ) );
    }

    @Test
    public void shouldHaveValueOfFalse() {
        assertThat( TriState.valueOf( false ), is( TriState.FALSE ) );
    }

    @Test
    public void shouldHaveValueOfTrue() {
        assertThat( TriState.valueOf( true ), is( TriState.TRUE ) );
    }

    @Test
    public void shouldHaveValueOfUnset() {
        assertThat( TriState.valueOf( ( Boolean )null ), is( TriState.UNSET ) );
    }
    
}
