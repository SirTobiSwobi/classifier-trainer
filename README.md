Common microservice sidecar for services implementing the classifier trainer API of the athlete/trainer pattern. 

See classifier-trainer.yml config file for metadata about the microservice. The same metadata can be accesses by calling /metadata of the running service. 

The Dockerfile to see which commands you need to run the service on a Linux machine with Java. 

Or you can just run the Docker container including everything necessary. 

Version change log:

- 0.0.1: implementation of configurable /metadata
- 0.1.1: implementation of GET /documents/$x
- 0.1.2: change internal data structure to AVL trees
- 0.1.3: implement delete operation in AVL trees
- 0.1.4: implementing GET /documents to get all documents. 
- 0.2.0: finished implementing all /documents/$x and /documents operations. /categories/$x under development.
- 0.3.0: Found missing feature to download document from online source. Breaking API compability to add URL. Therefore new version for categories/$x
- 0.3.1: /categories and /categories/$x working including unit tests. WEB GUI under development.
- 0.3.2: JSON doesn't work when one value field has multiple lines. WEB GUI preventing this finished.
- 0.3.3: finished WEB GUI for /categories and /categoires/$x
- 0.3.4: implementing /relationships and /relationships/$x including WEB GUI
- 0.3.5: implementing endpoint hash function
- 0.3.6: implementing /targetfunction and /targetfunction/$x endpoints
- 0.3.7: implementing targetfunction GUI
- 0.3.8: implementing abstract /configuration endpoint
- 0.3.9: creating JavaScript Library encapsulating all calls to the API and adding rudimentary GUI design 
- 0.4.0: creating training logic using the /model endpoint. This implements n-fold cross-validation. This breaks the established configuration logic adding the folds as parameter
- 0.4.1: creating /evaluations endpoint
- 0.5.0: finished trainer API. Adding included athlete endpoints starting with active model logic at /model
- 0.5.1: implementing /categorizations and /retraining. Now functionally complete. 
- 1.0.0: reworking /metadata mechanism and configuration options implementing trainer/athlete switch
- 1.0.1: implemented checks for impossible targetfunction assignments from development of implementing microservice
- 1.0.2: bugfixing and changing trainer to only use documents for which assignments exist within the target function
- 1.0.3: adding entire configuration to model. Learning from NTFC implementation.
- 1.0.4: merged common text sanitization function into the utilities class
- 1.0.5: merged added AVL tree functionality to return all used indices
- 1.0.6: added internal FeatureExtractor Interface
- 1.0.7: switched evaluation set generation to modulo-divison. Much increased effectiveness. Merged from tfidf-svm v0.0.5
- 1.0.8: split up c3.ct.trainer-api.js into general and specific library to fix Chrome cacheing bug