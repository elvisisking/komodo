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
package org.komodo.rest.relational;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.komodo.rest.relational.response.RestVdbCondition;
import org.komodo.rest.relational.response.RestVdbMask;
import org.komodo.rest.relational.response.RestVdbPermission;
import org.komodo.spi.type.TriState;

@SuppressWarnings( { "javadoc", "nls" } )
public final class RestVdbPermissionTest {

    private static final String NAME = "MyPermission";
    private static final TriState ALLOW_ALTER = TriState.TRUE;
    private static final TriState ALLOW_CREATE = TriState.TRUE;
    private static final TriState ALLOW_DELETE = TriState.TRUE;
    private static final TriState ALLOW_EXECUTE = TriState.TRUE;
    private static final TriState ALLOW_LANGUAGE = TriState.TRUE;
    private static final TriState ALLOW_READ = TriState.TRUE;
    private static final TriState ALLOW_UPDATE = TriState.TRUE;

    private static final List<RestVdbCondition> CONDITIONS = new ArrayList<>();

    private static final List<RestVdbMask> MASKS = new ArrayList<>();

    static {
        RestVdbCondition condition1 = new RestVdbCondition();
        condition1.setName("over");
        condition1.setConstraint(true);

        RestVdbCondition condition2 = new RestVdbCondition();
        condition1.setName("the");
        condition1.setConstraint(false);

        RestVdbCondition condition3 = new RestVdbCondition();
        condition1.setName("rainbow");
        condition1.setConstraint(true);

        CONDITIONS.add(condition1);
        CONDITIONS.add(condition2);
        CONDITIONS.add(condition3);

        RestVdbMask mask1 = new RestVdbMask();
        mask1.setName("this");
        mask1.setOrder("that");

        RestVdbMask mask2 = new RestVdbMask();
        mask1.setName("either");
        mask1.setOrder("or");

        RestVdbMask mask3 = new RestVdbMask();
        mask1.setName("sixofone");
        mask1.setOrder("halfdozenofanother");

        MASKS.add(mask1);
        MASKS.add(mask2);
        MASKS.add(mask3);
    }

    private RestVdbPermission permission;

    private RestVdbPermission copy() {
        final RestVdbPermission copy = new RestVdbPermission();
        copy.setName(this.permission.getName() );
        copy.setAllowAlter( this.permission.isAllowAlter() );
        copy.setAllowCreate( this.permission.isAllowCreate() );
        copy.setAllowDelete( this.permission.isAllowDelete() );
        copy.setAllowExecute( this.permission.isAllowExecute() );
        copy.setAllowLanguage( this.permission.isAllowLanguage() );
        copy.setAllowRead( this.permission.isAllowRead() );
        copy.setAllowUpdate( this.permission.isAllowUpdate() );
        copy.setLinks( this.permission.getLinks() );
        copy.setProperties( this.permission.getProperties() );

        return copy;
    }

    @Before
    public void init() {
        this.permission = new RestVdbPermission();
        this.permission.setName(NAME );
        this.permission.setAllowAlter( ALLOW_ALTER );
        this.permission.setAllowCreate( ALLOW_CREATE );
        this.permission.setAllowDelete( ALLOW_DELETE );
        this.permission.setAllowExecute( ALLOW_EXECUTE );
        this.permission.setAllowLanguage( ALLOW_LANGUAGE );
        this.permission.setAllowRead( ALLOW_READ );
        this.permission.setAllowUpdate( ALLOW_UPDATE );
    }

    @Test
    public void shouldBeEqual() {
        final RestVdbPermission thatPermission = copy();
        assertThat( this.permission, is( thatPermission ) );
    }

    @Test
    public void shouldBeEqualWhenComparingEmptyEntries() {
        final RestVdbPermission empty1 = new RestVdbPermission();
        final RestVdbPermission empty2 = new RestVdbPermission();
        assertThat( empty1, is( empty2 ) );
    }

    @Test
    public void shouldConstructEmptyPermission() {
        final RestVdbPermission empty = new RestVdbPermission();
        assertThat( empty.getName(), is( nullValue() ) );
        assertThat( empty.getProperties().isEmpty(), is( true ) );
        assertThat( empty.getLinks().size(), is( 0 ) );
    }

    @Test
    public void shouldHaveSameHashCode() {
        final RestVdbPermission thatPermission = copy();
        assertThat( this.permission.hashCode(), is( thatPermission.hashCode() ) );
    }

    @Test
    public void shouldNotBeEqualWhenAllowAlterIsDifferent() {
        final RestVdbPermission thatPermission = copy();
        final TriState different = TriState.FALSE;
        assertThat( this.permission.isAllowAlter(), is( not( different ) ) );

        thatPermission.setAllowAlter( different );
        assertThat( this.permission, is( not( thatPermission ) ) );
    }

    @Test
    public void shouldNotBeEqualWhenAllowCreateIsDifferent() {
        final RestVdbPermission thatPermission = copy();
        final TriState different = TriState.FALSE;
        assertThat( this.permission.isAllowCreate(), is( not( different ) ) );

        thatPermission.setAllowCreate( different );
        assertThat( this.permission, is( not( thatPermission ) ) );
    }

    @Test
    public void shouldNotBeEqualWhenAllowDeleteIsDifferent() {
        final RestVdbPermission thatPermission = copy();
        final TriState different = TriState.FALSE;
        assertThat( this.permission.isAllowDelete(), is( not( different ) ) );

        thatPermission.setAllowDelete( different );
        assertThat( this.permission, is( not( thatPermission ) ) );
    }

    @Test
    public void shouldNotBeEqualWhenAllowExecuteIsDifferent() {
        final RestVdbPermission thatPermission = copy();
        final TriState different = TriState.FALSE;
        assertThat( this.permission.isAllowExecute(), is( not( different ) ) );

        thatPermission.setAllowExecute( different );
        assertThat( this.permission, is( not( thatPermission ) ) );
    }

    @Test
    public void shouldNotBeEqualWhenAllowLanguageIsDifferent() {
        final RestVdbPermission thatPermission = copy();
        final TriState different = TriState.FALSE;
        assertThat( this.permission.isAllowLanguage(), is( not( different ) ) );

        thatPermission.setAllowLanguage( different );
        assertThat( this.permission, is( not( thatPermission ) ) );
    }

    @Test
    public void shouldNotBeEqualWhenAllowReadIsDifferent() {
        final RestVdbPermission thatPermission = copy();
        final TriState different = TriState.FALSE;
        assertThat( this.permission.isAllowRead(), is( not( different ) ) );

        thatPermission.setAllowRead( different );
        assertThat( this.permission, is( not( thatPermission ) ) );
    }

    @Test
    public void shouldNotBeEqualWhenAllowUpdateIsDifferent() {
        final RestVdbPermission thatPermission = copy();
        final TriState different = TriState.FALSE;
        assertThat( this.permission.isAllowUpdate(), is( not( different ) ) );

        thatPermission.setAllowUpdate( different );
        assertThat( this.permission, is( not( thatPermission ) ) );
    }

    @Test
    public void shouldNotBeEqualWhenNameIsDifferent() {
        final RestVdbPermission thatPermission = copy();
        thatPermission.setName( this.permission.getName() + "blah" );
        assertThat( this.permission, is( not( thatPermission ) ) );
    }

    @Test
    public void shouldSetAllowAlter() {
        final TriState newValue = TriState.FALSE;
        assertThat( this.permission.isAllowAlter(), is( not( newValue ) ) );

        this.permission.setAllowAlter( newValue );
        assertThat( this.permission.isAllowAlter(), is( newValue ) );
    }

    @Test
    public void shouldSetAllowCreate() {
        final TriState newValue = TriState.FALSE;
        assertThat( this.permission.isAllowCreate(), is( not( newValue ) ) );

        this.permission.setAllowCreate( newValue );
        assertThat( this.permission.isAllowCreate(), is( newValue ) );
    }

    @Test
    public void shouldSetAllowDelete() {
        final TriState newValue = TriState.FALSE;
        assertThat( this.permission.isAllowDelete(), is( not( newValue ) ) );

        this.permission.setAllowDelete( newValue );
        assertThat( this.permission.isAllowDelete(), is( newValue ) );
    }

    @Test
    public void shouldSetAllowExecute() {
        final TriState newValue = TriState.FALSE;
        assertThat( this.permission.isAllowExecute(), is( not( newValue ) ) );

        this.permission.setAllowExecute( newValue );
        assertThat( this.permission.isAllowExecute(), is( newValue ) );
    }

    @Test
    public void shouldSetAllowLanguage() {
        final TriState newValue = TriState.FALSE;
        assertThat( this.permission.isAllowLanguage(), is( not( newValue ) ) );

        this.permission.setAllowLanguage( newValue );
        assertThat( this.permission.isAllowLanguage(), is( newValue ) );
    }

    @Test
    public void shouldSetAllowRead() {
        final TriState newValue = TriState.FALSE;
        assertThat( this.permission.isAllowRead(), is( not( newValue ) ) );

        this.permission.setAllowRead( newValue );
        assertThat( this.permission.isAllowRead(), is( newValue ) );
    }

    @Test
    public void shouldSetAllowUpdate() {
        final TriState newValue = TriState.FALSE;
        assertThat( this.permission.isAllowUpdate(), is( not( newValue ) ) );

        this.permission.setAllowUpdate( newValue );
        assertThat( this.permission.isAllowUpdate(), is( newValue ) );
    }

    @Test
    public void shouldSetName() {
        final String newName = "blah";
        this.permission.setName( newName );
        assertThat( this.permission.getName(), is( newName ) );
    }

}
