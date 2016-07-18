/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.komodo.relational.commands.permission;

import org.komodo.relational.commands.RelationalCommandsI18n;
import org.komodo.relational.vdb.Mask;
import org.komodo.relational.vdb.Permission;
import org.komodo.shell.CommandResultImpl;
import org.komodo.shell.api.CommandResult;
import org.komodo.shell.api.WorkspaceStatus;
import org.komodo.utils.i18n.I18n;

/**
 * A shell command to add a Mask to a Permission.
 */
public final class AddMaskCommand extends PermissionShellCommand {

    static final String NAME = "add-mask"; //$NON-NLS-1$

    /**
     * @param status
     *        the shell's workspace status (cannot be <code>null</code>)
     */
    public AddMaskCommand( final WorkspaceStatus status ) {
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
            final String maskName = requiredArgument( 0, I18n.bind( PermissionCommandsI18n.missingMaskName ) );

            final Permission permission = getPermission();
            
            // Do not allow add if object of type with this name already exists
            Mask[] existingMasks = permission.getMasks(getTransaction(), maskName);
            if(existingMasks.length>0) {
                return new CommandResultImpl( false, I18n.bind( RelationalCommandsI18n.cannotAddChildAlreadyExistsError, maskName, Mask.class.getSimpleName() ), null );
            }
            
            permission.addMask( getTransaction(), maskName );

            result = new CommandResultImpl( I18n.bind( PermissionCommandsI18n.maskAdded, maskName ) );
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
        print( indent, I18n.bind( PermissionCommandsI18n.addMaskHelp, getName() ) );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#printHelpExamples(int)
     */
    @Override
    protected void printHelpExamples( final int indent ) {
        print( indent, I18n.bind( PermissionCommandsI18n.addMaskExamples ) );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.BuiltInShellCommand#printHelpUsage(int)
     */
    @Override
    protected void printHelpUsage( final int indent ) {
        print( indent, I18n.bind( PermissionCommandsI18n.addMaskUsage ) );
    }

}
