package SEURAT.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import edu.wpi.cs.jburge.SEURAT.SEURATPlugin;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class SEURATMysqlPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	/**
	 * This constructors sets up the SEURAT Mysql Preferences Page
	 * so that the field editors can be initialized and automatically
	 * handle changing SEURAT's Mysql Database Settings.
	 */
	public SEURATMysqlPreferencePage() {
		super(GRID);
		setPreferenceStore(SEURATPlugin.getDefault().getPreferenceStore());
		setDescription("SEURAT MySQL Database Setup Preferences");
	}
	
	private RadioGroupFieldEditor serverLocEditor;
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		String l_radioData[][] = new String[][] {
				new String[]{"Use Server on Local Machine", "local"}, 
				new String[]{"Use Remote Server", "remote"} 
		};
		
		serverLocEditor = new RadioGroupFieldEditor(
				PreferenceConstants.P_MYSQLLOCATION,
				"MySQL Server Location Configuration",
				2,
				l_radioData,
				getFieldEditorParent()
			);
		
		addField(serverLocEditor);		
		
		addField(
				new StringFieldEditor(PreferenceConstants.P_MYSQLADDRESS, "&Server address:", getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstants.P_MYSQLPORT, "&Server port:", getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstants.P_DATABASE, "&Database name:", getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstants.P_DATABASEUSER, "&Database userid:", getFieldEditorParent()));
		addField(
				new StringFieldEditor(PreferenceConstants.P_DATABASEPASSWORD, "&Database password:", getFieldEditorParent()));
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}