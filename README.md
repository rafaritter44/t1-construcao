# t1-construcao-BookTracker

# Executando o back-end
1. Instalar o [Docker] (https://docs.docker.com/install/) em sua máquina, caso ainda não o tenha
1. Clonar o repositório
1. No diretório backend, executar o seguinte comando  "docker build -t booktracker ."
1. Após o término do bake do container, executar o seguinte comando "docker run -it -p 8080:8080 booktracker"
1. Pronto, seu server deve estar rodando corretamente, para verificar, acesse o [swagger](https://swagger.io/) da aplicação: http://localhost:8080/swagger-ui.html#/
