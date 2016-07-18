/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.komodo.relational.commands.model;

import org.komodo.relational.commands.RelationalCommandsI18n;
import org.komodo.relational.model.Model;
import org.komodo.relational.model.Table;
import org.komodo.shell.CommandResultImpl;
import org.komodo.shell.api.CommandResult;
import org.komodo.shell.api.WorkspaceStatus;
import org.komodo.utils.i18n.I18n;

/**
 * A shell command to add a Table to a Model.
 */
public final class AddTableCommand extends ModelShellCommand {

    static final String NAME = "add-table"; //$NON-NLS-1$

    /**
     * @param status
     *        the shell's workspace status (cannot be <code>null</code>)
     */
    public AddTableCommand( final WorkspaceStatus status ) {
        super( NAME, status );
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
            final String tableName = requiredArgument( 0, I18n.bind( ModelCommandsI18n.missingTableName ) );

            final Model model = getModel();
            
            // Do not allow add if object of type with this name already exists
            Table[] tables = model.getTables(getTransaction(), tableName);
            if(tables.length>0) {
                return new CommandResultImpl( false, I18n.bind( RelationalCommandsI18n.cannotAddChildAlreadyExistsError, tableName, Table.class.getSimpleName() ), null );
            }
            
            model.addTable( getTransaction(), tableName );

            result = new CommandResultImpl( I18n.bind( ModelCommandsI18n.tableAdded, tableName ) );
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
        return 1;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#printHelpDescription(int)
     */
    @Override
    protected void printHelpDescription( final int indent ) {
        print( indent, I18n.bind( ModelCommandsI18n.addTableHelp, getName() ) );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#printHelpExamples(int)
     */
    @Override
    protected void printHelpExamples( final int indent ) {
        print( indent, I18n.bind( ModelCommandsI18n.addTableExamples ) );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#printHelpUsage(int)
     */
    @Override
    protected void printHelpUsage( final int indent ) {
        print( indent, I18n.bind( ModelCommandsI18n.addTableUsage ) );
    }

}
