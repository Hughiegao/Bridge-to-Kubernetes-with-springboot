# Bridge-to-Kubernetes-with-springboot
A working sample of bridge to kube with springboot

This sample is to demo how to debug code on your development computer, while still connected to your kubernetes cluster with the rest of your application or services. I am reuse the code from https://github.com/microsoft/todo-app-java-on-azure: 
* clone it and rename to todo-ms
* clone it and rename to user-ms and modify all entity from todo to user
* I also add a new endpoint on todo-ms and user-ms to have them calling each others.

<h1>Create Azure Resource Group</h1>
login your Azure CLI, and set your subscription id

```
az login
az account set -s <your-subscription-id>
```
create an Azure Resource Group, and note your group name
```
az group create --name myResourceGroup --location eastus
```

<h1>Create Azure Cosmos DB documentDB</h1>
You can follow our steps using Azure CLI 2.0 to deploy an Azure Cosmos DB documentDB, or follow this article to create it from Azure portal.

create Azure Cosmos DB with DocumentDB kind. Note the documentEndpoint field in the response.
```
az cosmosdb create --kind GlobalDocumentDB -g myResourceGroup -n todo-app-db
Note name of cosmos db must be in lowercase.
```
get your Azure Cosmos DB key, get the primaryMasterKey of the DocumentDB you just created.
```
az cosmosdb list-keys -g myResourceGroup -n todo-app-db
```
  Configuration
Note your DocumentDB uri and key from last step, specify a database name but no need to create it. Then modify src/main/resources/application.properties file and save it.
```
  azure.documentdb.uri=https://todo-app-db.documents.azure.com:443
  azure.documentdb.key=RbGTt2Gxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx7fowuIURox5xQ==
  azure.documentdb.database=todo-app-db4
  if azure.documentdb.* don't work, you can try azure.cosmosdb.*
```

<h1>Create Azure Container registry using the Azure CLI</h1>

read more about ACR with https://docs.microsoft.com/en-us/azure/container-registry/container-registry-get-started-azure-cli
create a container registry
```
az acr create --resource-group myResourceGroup --name todocr --sku Basic
```
login to registry
```
az acr login --name todocr
```

<h1>Build and post docker image to Azure Container registry using the Azure CLI</h1>

build jar with maven
```
mvn clean install
```
build docker image with docker
```
docker build -t todocr.azurecr.io/demo/todo:1.0 .
```
push image to Azure container registry
```
az acr login --name todocr
docker push todocr.azurecr.io/demo/todo:1.0
```

<h1>Create Azure Kubernetes Service cluster using the Azure CLI</h1>
you can also create it with portal https://docs.microsoft.com/en-us/azure/aks/kubernetes-walkthrough-portal

```
az group create --name myResourceGroup --location eastus

{
  "id": "/subscriptions/<guid>/resourceGroups/myResourceGroup",
  "location": "eastus",
  "managedBy": null,
  "name": "myResourceGroup",
  "properties": {
    "provisioningState": "Succeeded"
  },
  "tags": null
}
```

create aks cluster
```
az aks create --resource-group myResourceGroup --name myAKSCluster --node-count 1 --generate-ssh-keys
```

connect to the cluster
```
az aks get-credentials --resource-group myResourceGroup --name myAKSCluster
```

create and select namespace
```
kubectl create namespace todo-app
kubectl config set-context --current --namespace=todo-app
```

create secret that contain your Azure container registry login token
```
az acr login --name todocr --expose-token
 
kubectl create secret docker-registry todocr --docker-server=todocr.azurecr.io --docker-username=00000000-0000-0000-0000-000000000000 --docker-password <your todocr login token>

```
deploy the application using the kubectl apply command
```
kubectl apply -f azure-vote.yaml
```

