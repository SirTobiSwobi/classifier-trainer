package org.SirTobiSwobi.c3.classifiertrainer.db;

public class ReferenceHub {
	private CategoryManager categoryManager;
	private DocumentManager documentManager;
	private TargetFunctionManager targetFunctionManager;
	private ConfigurationManager configurationManager;
	public ReferenceHub(CategoryManager categoryManager, DocumentManager documentManager,
			TargetFunctionManager targetFunctionManager, ConfigurationManager configurationManager) {
		super();
		this.categoryManager = categoryManager;
		this.documentManager = documentManager;
		this.targetFunctionManager = targetFunctionManager;
		this.configurationManager = configurationManager;
	}
	public CategoryManager getCategoryManager() {
		return categoryManager;
	}
	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}
	public DocumentManager getDocumentManager() {
		return documentManager;
	}
	public void setDocumentManager(DocumentManager documentManager) {
		this.documentManager = documentManager;
	}
	public TargetFunctionManager getTargetFunctionManager() {
		return targetFunctionManager;
	}
	public void setTargetFunctionManager(TargetFunctionManager targetFunctionManager) {
		this.targetFunctionManager = targetFunctionManager;
	}
	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}
	public void setConfigurationManager(ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}
	
	
	
}
