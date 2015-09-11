/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.komodo.relational.commands.server;

import static org.komodo.relational.commands.server.ServerCommandMessages.Common.Connected;
import static org.komodo.relational.commands.server.ServerCommandMessages.Common.CurrentTeiid;
import static org.komodo.relational.commands.server.ServerCommandMessages.Common.NotConnected;
import static org.komodo.relational.commands.server.ServerCommandMessages.Common.serverStatusText;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import org.komodo.relational.Messages;
import org.komodo.relational.teiid.Teiid;
import org.komodo.relational.workspace.WorkspaceManager;
import org.komodo.shell.api.ShellCommand;
import org.komodo.shell.api.ShellCommandProvider;
import org.komodo.shell.api.WorkspaceStatus;
import org.komodo.spi.KException;
import org.komodo.spi.repository.KomodoObject;
import org.komodo.spi.repository.Repository;
import org.komodo.spi.runtime.TeiidInstance;

/**
 * A shell command provider for VDBs.
 */
public class ServerCommandProvider implements ShellCommandProvider {

    /**
     * Key for storage of default server in the workspace.
     */
    public static final String SERVER_DEFAULT_KEY = "SERVER_DEFAULT"; //$NON-NLS-1$

    /**
     * Constructs a command provider for VDB shell commands.
     */
    public ServerCommandProvider() {
        // nothing to do
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.api.ShellCommandProvider#provideCommands()
     */
    @Override
    public Set< Class< ? extends ShellCommand > > provideCommands() {
        final Set< Class< ? extends ShellCommand > > result = new HashSet< >();

        result.add( ServerConnectCommand.class );
        result.add( ServerDisconnectCommand.class );
        result.add( ServerSetCommand.class );
        result.add( ServerShowVdbsCommand.class );
        result.add( ServerShowTranslatorsCommand.class );
        result.add( ServerShowDatasourcesCommand.class );
        result.add( ServerShowDatasourceTypesCommand.class );
        result.add( ServerDeployVdbCommand.class );
        result.add( ServerUndeployVdbCommand.class );
        result.add( ServerImportVdbCommand.class );
        result.add( ServerShowVdbCommand.class );
        result.add( ServerShowTranslatorCommand.class );
        result.add( ServerShowDatasourceCommand.class );
        result.add( ServerShowDatasourceTypeCommand.class );

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.api.ShellCommandProvider#resolve(org.komodo.spi.repository.Repository.UnitOfWork,
     *      org.komodo.spi.repository.KomodoObject)
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public Teiid resolve ( final Repository.UnitOfWork uow, final KomodoObject kObj ) throws KException {
        if(Teiid.RESOLVER.resolvable(uow, kObj)) {
            return Teiid.RESOLVER.resolve(uow, kObj);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.api.ShellCommandProvider#getTypeDisplay(org.komodo.spi.repository.Repository.UnitOfWork,
     *      org.komodo.spi.repository.KomodoObject)
     */
    @Override
    public String getTypeDisplay ( final Repository.UnitOfWork uow, final KomodoObject kObj ) throws KException {
        final Teiid resolved = resolve( uow, kObj );
        return ( ( resolved == null ) ? null : resolved.getTypeDisplayName() );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.api.ShellCommandProvider#getStatusMessage(org.komodo.spi.repository.Repository.UnitOfWork,
     *      org.komodo.spi.repository.KomodoObject)
     */
    @Override
    public String getStatusMessage ( final Repository.UnitOfWork uow, final KomodoObject kObj ) throws KException {
        if(Teiid.RESOLVER.resolvable(uow, kObj)) {
            Teiid teiid = (Teiid)kObj;

            TeiidInstance teiidInstance = teiid.getTeiidInstance(uow);
            String teiidName = teiid.getName(uow);
            String teiidUrl = teiidInstance.getUrl();
            String teiidConnected = teiidInstance.isConnected() ? getMessage(Connected) : getMessage(NotConnected);
            String currentServerText = getMessage(serverStatusText, teiidName, teiidUrl, teiidConnected);

            String resultMessage = getMessage(CurrentTeiid,currentServerText);

            return resultMessage;
        }
        return null;
    }

    private String getMessage(Enum< ? > key, Object... parameters) {
        return Messages.getString(ServerCommandMessages.RESOURCE_BUNDLE,key.toString(),parameters);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.shell.api.ShellCommandProvider#initWorkspaceState(org.komodo.shell.api.WorkspaceStatus)
     */
    @Override
    public void initWorkspaceState(WorkspaceStatus wsStatus) throws KException {
        Properties globalProps = wsStatus.getProperties();
        // Look for Server default key.  If found, attempt to set the state object
        if(globalProps.containsKey(ServerCommandProvider.SERVER_DEFAULT_KEY)) {
            String defaultServerName = globalProps.getProperty(ServerCommandProvider.SERVER_DEFAULT_KEY);
            WorkspaceManager wsMgr = WorkspaceManager.getInstance(wsStatus.getCurrentContext().getRepository());
            Teiid teiid = ServerUtils.getWorkspaceTeiidObject(wsMgr, wsStatus, defaultServerName);
            wsStatus.setStateObject(ServerCommandProvider.SERVER_DEFAULT_KEY, teiid);
        }
    }

}