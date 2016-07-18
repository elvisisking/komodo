/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.komodo.relational.commands.workspace;

import org.komodo.core.KomodoLexicon;
import org.komodo.relational.commands.RelationalCommandsI18n;
import org.komodo.relational.datasource.Datasource;
import org.komodo.relational.workspace.WorkspaceManager;
import org.komodo.shell.CommandResultImpl;
import org.komodo.shell.api.CommandResult;
import org.komodo.shell.api.WorkspaceStatus;
import org.komodo.shell.util.KomodoObjectUtils;
import org.komodo.utils.i18n.I18n;

/**
 * A shell command to create a Datasource object.
 */
public final class CreateDatasourceCommand extends WorkspaceShellCommand {

    static final String NAME = "create-datasource"; //$NON-NLS-1$

    /**
     * @param status
     *        the shell's workspace status (cannot be <code>null</code>)
     */
    public CreateDatasourceCommand( final WorkspaceStatus status ) {
        super( status, NAME );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#doExecute()
     */
    @Override
    protected CommandResult doExecute() {
        CommandResult result = null;

        try {
            final String sourceName = requiredArgument( 0, I18n.bind( WorkspaceCommandsI18n.missingDatasourceName ) );
            
            // Can optionally supply the type of datasource (jdbc vs non-jdbc)
            final String isJdbc = optionalArgument( 1, Boolean.TRUE.toString() );

            if ( !KomodoObjectUtils.TRUE_STRING.equals( isJdbc ) && !KomodoObjectUtils.FALSE_STRING.equals( isJdbc ) ) {
                return new CommandResultImpl( I18n.bind( WorkspaceCommandsI18n.invalidDatasourceIndicator, isJdbc ) );
            }
            
            final WorkspaceManager mgr = getWorkspaceManager();
            
            // Do not allow create if object with this name already exists
            if(mgr.hasChild(getTransaction(), sourceName, KomodoLexicon.DataSource.NODE_TYPE)) {
                return new CommandResultImpl( false, I18n.bind( RelationalCommandsI18n.cannotCreateChildAlreadyExistsError, sourceName, Datasource.class.getSimpleName() ), null );
            }

            Datasource datasrc = mgr.createDatasource( getTransaction(), null, sourceName );
            datasrc.setJdbc(getTransaction(), Boolean.parseBoolean( isJdbc ));

            result = new CommandResultImpl( I18n.bind( WorkspaceCommandsI18n.datasourceCreated, sourceName ) );
        } catch ( final Exception e ) {
            result = new CommandResultImpl( e );
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#getMaxArgCount()
     */
    @Override
    protected int getMaxArgCount() {
        return 2;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#printHelpDescription(int)
     */
    @Override
    protected void printHelpDescription( final int indent ) {
        print( indent, I18n.bind( WorkspaceCommandsI18n.createDatasourceHelp, getName() ) );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#printHelpExamples(int)
     */
    @Override
    protected void printHelpExamples( final int indent ) {
        print( indent, I18n.bind( WorkspaceCommandsI18n.createDatasourceExamples ) );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#printHelpUsage(int)
     */
    @Override
    protected void printHelpUsage( final int indent ) {
        print( indent, I18n.bind( WorkspaceCommandsI18n.createDatasourceUsage ) );
    }
    
}
