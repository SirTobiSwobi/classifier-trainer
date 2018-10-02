package org.SirTobiSwobi.c3.classifiertrainer.db;

public class ReferenceHub {
	private CategoryManager categoryManager;
	private DocumentManager documentManager;
	private TargetFunctionManager targetFunctionManager;
	private ConfigurationManager configurationManager;
	private ModelManager modelManager;
	private CategorizationManager categorizationManager;
	private EvaluationManager evaluationManager;
	public ReferenceHub(CategoryManager categoryManager, DocumentManager documentManager,
			TargetFunctionManager targetFunctionManager, ConfigurationManager configurationManager, ModelManager modelManager, 
			CategorizationManager categorizationManager, EvaluationManager evaluationManager) {
		super();
		this.categoryManager = categoryManager;
		this.documentManager = documentManager;
		this.targetFunctionManager = targetFunctionManager;
		this.configurationManager = configurationManager;
		this.modelManager = modelManager;
		this.categorizationManager = categorizationManager;
		this.evaluationManager = evaluationManager;
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
	public ModelManager getModelManager() {
		return modelManager;
	}
	public void setModelManager(ModelManager modelManager) {
		this.modelManager = modelManager;
	}
	public CategorizationManager getCategorizationManager() {
		return categorizationManager;
	}
	public void setCategorizationManager(CategorizationManager categorizationManager) {
		this.categorizationManager = categorizationManager;
	}
	public EvaluationManager getEvaluationManager() {
		return evaluationManager;
	}
	public void setEvaluationManager(EvaluationManager evaluationManager) {
		this.evaluationManager = evaluationManager;
	}
	
	
	
	
	
}
