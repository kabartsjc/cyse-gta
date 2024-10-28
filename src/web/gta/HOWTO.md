#install postgresql
docker pull postgres

#create volumn --> persist database
docker volume create pgdata


#start the container
 docker run -d   --name my_postgres_container   -e POSTGRES_PASSWORD=CYSE1972   -v pgdata:/var/lib/postgresql/data   -p 5432:5432   postgres
 
 
#stop
docker ps -a
docker stop my_postgres_container
docker rm my_postgres_container
