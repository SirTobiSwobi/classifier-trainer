Common microservice sidecar for services implementing the classifier trainer API of the athlete/trainer pattern. 

See classifier-trainer.yml config file for metadata about the microservice. The same metadata can be accesses by calling /metadata of the running service. 

The Dockerfile to see which commands you need to run the service on a Linux machine with Java. 

Or you can just run the Docker container including everything necessary. 

Version change log:

0.0.1: implementation of configurable /metadata
0.1.1: implementation of GET /documents/$x
0.1.2: change internal data structure to AVL trees
0.1.3: implement delete operation in AVL trees