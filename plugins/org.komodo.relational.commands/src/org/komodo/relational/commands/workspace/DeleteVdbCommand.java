/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.komodo.relational.commands.workspace;

import static org.komodo.relational.commands.workspace.WorkspaceCommandMessages.DeleteVdbCommand.DELETE_VDB_ERROR;
import static org.komodo.relational.commands.workspace.WorkspaceCommandMessages.DeleteVdbCommand.VDB_DELETED;
import static org.komodo.relational.commands.workspace.WorkspaceCommandMessages.DeleteVdbCommand.VDB_NOT_FOUND;
import static org.komodo.relational.commands.workspace.WorkspaceCommandMessages.General.MISSING_VDB_NAME;
import java.util.ArrayList;
import java.util.List;
import org.komodo.relational.workspace.WorkspaceManager;
import org.komodo.shell.CommandResultImpl;
import org.komodo.shell.api.Arguments;
import org.komodo.shell.api.CommandResult;
import org.komodo.shell.api.WorkspaceStatus;
import org.komodo.spi.repository.KomodoObject;
import org.komodo.spi.repository.Repository.UnitOfWork;
import org.modeshape.sequencer.teiid.lexicon.VdbLexicon;

/**
 * A shell command to delete a Vdb.
 */
public final class DeleteVdbCommand extends WorkspaceShellCommand {

    static final String NAME = "delete-vdb"; //$NON-NLS-1$

    /**
     * @param status
     *        the shell's workspace status (cannot be <code>null</code>)
     */
    public DeleteVdbCommand( final WorkspaceStatus status ) {
        super( status, NAME );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#doExecute()
     */
    @Override
    protected CommandResult doExecute() {
        try {
            final String vdbName = requiredArgument( 0, getMessage( MISSING_VDB_NAME ) );

            // Make sure the VDB exists
            if(!getWorkspaceManager().hasChild(getTransaction(), vdbName, VdbLexicon.Vdb.VIRTUAL_DATABASE)) {
                return new CommandResultImpl( false, getMessage( VDB_NOT_FOUND, vdbName ), null );
            } 

            // Delete the VDB
            final KomodoObject vdbToDelete = getWorkspaceManager().getChild(getTransaction(), vdbName, VdbLexicon.Vdb.VIRTUAL_DATABASE);
            getWorkspaceManager().delete(getTransaction(), vdbToDelete);
            return new CommandResultImpl( getMessage( VDB_DELETED, vdbName ) );
            
        } catch ( final Exception e ) {
            return new CommandResultImpl( false, getMessage( DELETE_VDB_ERROR ), e );
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#getMaxArgCount()
     */
    @Override
    protected int getMaxArgCount() {
        return 1;
    }
    
    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#tabCompletion(java.lang.String, java.util.List)
     */
    @Override
    public int tabCompletion( final String lastArgument,
                              final List< CharSequence > candidates ) throws Exception {
        final Arguments args = getArguments();

        final UnitOfWork uow = getTransaction();
        final WorkspaceManager mgr = getWorkspaceManager();
        final KomodoObject[] vdbs = mgr.findVdbs(uow);
        List<String> existingVdbNames = new ArrayList<String>(vdbs.length);
        for(KomodoObject vdb : vdbs) {
            existingVdbNames.add(vdb.getName(uow));
        }

        if ( args.isEmpty() ) {
            if ( lastArgument == null ) {
                candidates.addAll( existingVdbNames );
            } else {
                for ( final String item : existingVdbNames ) {
                    if ( item.toUpperCase().startsWith( lastArgument.toUpperCase() ) ) {
                        candidates.add( item );
                    }
                }
            }

            return 0;
        }

        // no tab completion
        return -1;
    }

}
