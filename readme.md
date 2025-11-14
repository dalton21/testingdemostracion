# testingdemostracion

Descripción
-----------
Aplicación de ejemplo construida con Spring Boot (Java 17) y Maven que expone una API REST para gestionar objetos "Nodo". Usa una base de datos H2 en memoria por defecto y proporciona operaciones básicas:

- POST /nodos : crear un Nodo (campos: titulo, cuerpo)
- GET /nodos : listar todos los Nodos
- GET /nodos/{id} : obtener un Nodo por su id

Requisitos
----------
- Java 17 o superior instalado y disponible en PATH.
- Maven (mvn) para compilar y ejecutar localmente.
- Docker (para construir la imagen).
- Minikube y kubectl (opcional, para desplegar en Kubernetes).

Compilar y ejecutar localmente
------------------------------
1. Compilar el proyecto con Maven:

```bash
mvn clean package
```

2. Ejecutar el JAR generado (el archivo se encuentra en `target/`):

```bash
java -jar target\*.jar
```

La aplicación arranca por defecto en el puerto 8080. Puedes cambiar la configuración en `src/main/resources/application.properties` o usando variables de entorno de Spring Boot.

Probar los endpoints
--------------------
Ejemplos usando curl (desde Windows PowerShell o WSL) o desde cmd con curl instalado.

- Crear un Nodo (POST):

```bash
curl -X POST http://localhost:8080/nodos -H "Content-Type: application/json" -d "{\"titulo\": \"Mi titulo\", \"cuerpo\": \"Contenido del nodo\"}"
```

Respuesta: 201 Created y el JSON del nodo creado (incluye el id).

- Listar todos los Nodos (GET):

```bash
curl http://localhost:8080/nodos
```

- Obtener un Nodo por id (GET):

```bash
curl http://localhost:8080/nodos/1
```

Notas:
- Si intentas crear un Nodo con un `titulo` ya existente, la aplicación responde con un error (se evita duplicados por título).
- La base de datos por defecto es H2 en memoria; al reiniciar la aplicación los datos se pierden.

Docker
------
Construir la imagen Docker (desde la raíz del proyecto donde está el `Dockerfile`):

```bash
docker build -t testingdemostracion .
```

Si vas a usar Minikube, carga la imagen en el registro de Minikube (evita que Kubernetes tenga que tirar la imagen desde Docker Hub):

```bash
minikube image load testingdemostracion:latest
```

Kubernetes / Minikube (despliegue de ejemplo)
--------------------------------------------
Se incluye un manifiesto `deployment.yml` para desplegar la aplicación (Deployment + Service). Para aplicar el manifiesto:

```bash
kubectl apply -f deployment.yml
```

Comprobar el estado de los recursos:

```bash
kubectl get pods
kubectl get svc
kubectl get deployments
```

Para exponer el servicio localmente y poder probar los endpoints con curl desde tu máquina:

```bash
kubectl port-forward svc/testingdemostracion 8080:8080
```

Entonces prueba:

```bash
curl http://localhost:8080/nodos
```

Consejos y solución de problemas rápida
-------------------------------------
- Ver logs de un pod:

```bash
kubectl logs <pod-name>
```

- Si el servicio no responde, comprobar los endpoints y el port-forward:

```bash
kubectl get endpoints testingdemostracion
kubectl port-forward svc/testingdemostracion 8080:8080
```

- Si tienes problemas con la imagen en Minikube, asegúrate de haber ejecutado `minikube image load` o publica la imagen en un registry accesible.

Archivos relevantes
-------------------
- `pom.xml` : configuración y dependencias de Maven.
- `Dockerfile` : instrucciones para crear la imagen Docker.
- `deployment.yml` : manifiesto de Kubernetes (Deployment + Service).
- `src/main/resources/application.properties` : configuración de Spring Boot.
- Código fuente:
  - `src/main/java/com/example/controller/NodoController.java` (endpoints REST)
  - `src/main/java/com/example/model/Nodo.java` (modelo JPA)
  - `src/main/java/com/example/service` (lógica de negocio)
  - `src/main/java/com/example/repository` (acceso a datos)

Pruebas
-------
Hay pruebas unitarias e integradas en el directorio `src/test/java`. Para ejecutar las pruebas:

```bash
mvn test
```
