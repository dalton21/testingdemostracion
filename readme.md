7444  docker build -t testingdemostracion .
7445  minikube image load testingdemostracion:latest
7464  k apply -f deployment.yml
7467  kubectl get endpoints testingdemostracion
7468  kubectl port-forward svc/testingdemostracion 8080:8080
