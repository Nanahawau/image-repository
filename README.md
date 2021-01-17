#Image Repository

##Introduction
This project is an image repository that takes advantage of
the minio's object storage capabilities to store images one at a time and 
in bulk. 

##Tools 
* Spring Boot
* Spring Data JPA
* SQL Native Queries
* Minio
* Spring Security
* JWT 
* H2 Database


##Setup

###What is Minio?

MinIO is an Amazon S3 compatible server-side
software storage stack, it can handle unstructured data 
such as photos, videos, log files, backups, and container images with
currently the maximum supported object size of 5TB

Some of it's benefits include: 
* Scalabity
* Flexibility
* Availabilty
* Durability

##Setting up minio locally in Spring Boot

For this project to run on your local machine, you have to set 
up a minio server locally. Click on the link below and follow the
instructions:
https://medium.com/the-innovation/running-s3-object-storage-locally-with-minio-f50540ffc239

After successful set up, set the following properties in your 
application.properties file

* minio.url
* access.key
* secret.key
* bucket.name

##Encryption of Minio Bucket
MinIO uses a key-management-system (KMS) to support SSE-S3. If a client requests
SSE-S3, or auto-encryption is enabled, the MinIO server encrypts each object with an 
unique object key which is protected by a master key managed by the KMS.

For safe storage, you will set encryption for your minio buckets
and objects. Minio ships out of box with a KMS which you can use locally to test encryption. It is
advisable to host your own KMS when you move to production. 

Follow the steps: 

1. Fetch the private key and certificate of the root identity:
````
curl -sSL --tlsv1.2 \
     -O 'https://raw.githubusercontent.com/minio/kes/master/root.key' \
     -O 'https://raw.githubusercontent.com/minio/kes/master/root.cert'

````

2.Set the MinIO-KES configuration
````
export MINIO_KMS_KES_ENDPOINT=https://play.min.io:7373
export MINIO_KMS_KES_KEY_FILE=root.key
export MINIO_KMS_KES_CERT_FILE=root.cert
export MINIO_KMS_KES_KEY_NAME=my-minio-key
````

3.`To Enable Encryption: mc encrypt set sse-s3 myminio/bucketname/`

4.`To check Encryption status: mc encrypt info myminio/bucketname/ `


You can read the KMS guide for more information
https://docs.min.io/docs/minio-kms-quickstart-guide.html


##Application Security
All rest endpoints except the /login and /register are secured using JWT and must be called 
with a JWT token in the header.


##Database
An in-memory database set up for storing image and user related information.

####Postman Collection
At the root of the project is a postman collection file, import into Postman and use as a guide.
Alternatively, you can use the link below to access the postman docs online.
https://documenter.getpostman.com/view/9516731/TVzViGL2



##Swagger Documentation URL
When you run the application, you can access the swagger docs on this url
http://localhost:8900/swagger-ui.html#/


##Author 
Nana Hawau Adeku
https://www.linkedin.com/in/nana-hawau-adeku-6a391160














 
 
