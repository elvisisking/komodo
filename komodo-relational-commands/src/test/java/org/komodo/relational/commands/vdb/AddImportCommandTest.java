/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.komodo.relational.commands.vdb;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.komodo.relational.commands.AbstractCommandTest;
import org.komodo.relational.vdb.Vdb;
import org.komodo.relational.vdb.VdbImport;
import org.komodo.relational.workspace.WorkspaceManager;
import org.komodo.shell.api.CommandResult;

/**
 * Test Class to test {@link AddImportCommand}.
 */
@SuppressWarnings( {"javadoc", "nls"} )
public final class AddImportCommandTest extends AbstractCommandTest {

    @Test
    public void testAdd1() throws Exception {
        final String[] commands = {
            "create-vdb myVdb vdbPath",
            "cd myVdb",
            "add-import myImport" };
        final CommandResult result = execute( commands );
        assertCommandResultOk(result);

        WorkspaceManager wkspMgr = WorkspaceManager.getInstance(_repo, getTransaction());
        Vdb[] vdbs = wkspMgr.findVdbs(getTransaction());

        assertEquals(1, vdbs.length);

        VdbImport[] imports = vdbs[0].getImports(getTransaction());
        assertEquals(1, imports.length);
        assertEquals("myImport", imports[0].getName(getTransaction())); //$NON-NLS-1$
    }

    @Test( expected = AssertionError.class )
    public void shouldNotCreateImportWithNameThatAlreadyExists() throws Exception {
        final String cmd = "add-import myImport";
        final String[] commands = { "create-vdb myVdb vdbPath",
                                    "cd myVdb",
                                    cmd,
                                    cmd };

        execute( commands );
    }

}
