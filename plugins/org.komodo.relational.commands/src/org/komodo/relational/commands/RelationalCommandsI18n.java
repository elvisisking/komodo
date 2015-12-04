/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.komodo.relational.commands;

import org.komodo.utils.i18n.I18n;

/**
 * Localized messages for the {@link org.komodo.relational.commands}.
 */
@SuppressWarnings( "javadoc" )
public final class RelationalCommandsI18n extends I18n {

    public static String findExamples;
    public static String findHelp;
    public static String findUsage;

    public static String setCustomPropertyHelp;
    public static String setCustomPropertyExamples;
    public static String setCustomPropertyUsage;

    public static String unsetCustomPropertyExamples;
    public static String unsetCustomPropertyHelp;
    public static String unsetCustomPropertyUsage;

    public static String invalidType;
    public static String missingPropertyNameValue;
    public static String missingTypeName;
    public static String noObjectsFound;
    public static String relationalCommandCategory;
    public static String setPropertySuccess;
    public static String typeHeader;
    public static String unsetMissingPropertyName;
    public static String unsetPropertySuccess;

    static {
        final RelationalCommandsI18n i18n = new RelationalCommandsI18n();
        i18n.initialize();
    }

    /**
     * Don't allow construction outside of this class.
     */
    private RelationalCommandsI18n() {
        // nothing to do
    }

}