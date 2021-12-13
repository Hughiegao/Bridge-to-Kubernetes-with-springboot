# Bridge-to-Kubernetes-with-springboot
A working sample of bridge to kube with springboot

This sample is to demo how to debug code on your development computer, while still connected to your kubernetes cluster with the rest of your application or services. I am reuse the code from https://github.com/microsoft/todo-app-java-on-azure: 
* clone it and rename to todo-ms
* clone it and rename to user-ms and modify all entity from todo to user
* I also add a new endpoint on todo-ms and user-ms to have them calling each others.

Create Azure Cosmos DB documentDB
You can follow our steps using Azure CLI 2.0 to deploy an Azure Cosmos DB documentDB, or follow this article to create it from Azure portal.

1. login your Azure CLI, and set your subscription id

az login
az account set -s <your-subscription-id>
  
2. create an Azure Resource Group, and note your group name

az group create -n <your-azure-group-name> -l <your-resource-group-region>
create Azure Cosmos DB with DocumentDB kind. Note the documentEndpoint field in the response.

az cosmosdb create --kind GlobalDocumentDB -g <your-azure-group-name> -n <your-azure-documentDB-name>
Note name of cosmos db must be in lowercase.

get your Azure Cosmos DB key, get the primaryMasterKey of the DocumentDB you just created.

az cosmosdb list-keys -g <your-azure-group-name> -n <your-azure-documentDB-name>

  Configuration
Note your DocumentDB uri and key from last step, specify a database name but no need to create it. Then modify src/main/resources/application.properties file and save it.

azure.documentdb.uri=put-your-documentdb-uri-here
azure.documentdb.key=put-your-documentdb-key-here
azure.documentdb.database=put-your-documentdb-databasename-here

