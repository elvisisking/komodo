/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.komodo.relational.commands.virtualprocedure;

import org.komodo.relational.commands.RelationalCommandsI18n;
import org.komodo.relational.model.Parameter;
import org.komodo.relational.model.VirtualProcedure;
import org.komodo.shell.CommandResultImpl;
import org.komodo.shell.api.CommandResult;
import org.komodo.shell.api.WorkspaceStatus;
import org.komodo.utils.i18n.I18n;

/**
 * A shell command to add a Parameter to a VirtualProcedure.
 */
public final class AddParameterCommand extends VirtualProcedureShellCommand {

    static final String NAME = "add-parameter"; //$NON-NLS-1$

    /**
     * @param status
     *        the shell's workspace status (cannot be <code>null</code>)
     */
    public AddParameterCommand( final WorkspaceStatus status ) {
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
            final String paramName = requiredArgument( 0, I18n.bind( VirtualProcedureCommandsI18n.missingParameterName ) );

            final VirtualProcedure proc = getVirtualProcedure();
            
            // Do not allow add if object of type with this name already exists
            Parameter[] params = proc.getParameters(getTransaction(), paramName);
            if(params.length>0) {
                return new CommandResultImpl( false, I18n.bind( RelationalCommandsI18n.cannotAddChildAlreadyExistsError, paramName, Parameter.class.getSimpleName() ), null );
            }
            
            proc.addParameter( getTransaction(), paramName );

            result = new CommandResultImpl( I18n.bind( VirtualProcedureCommandsI18n.parameterAdded, paramName ) );
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
        print( indent, I18n.bind( VirtualProcedureCommandsI18n.addParameterHelp, getName() ) );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#printHelpExamples(int)
     */
    @Override
    protected void printHelpExamples( final int indent ) {
        print( indent, I18n.bind( VirtualProcedureCommandsI18n.addParameterExamples ) );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#printHelpUsage(int)
     */
    @Override
    protected void printHelpUsage( final int indent ) {
        print( indent, I18n.bind( VirtualProcedureCommandsI18n.addParameterUsage ) );
    }

}
