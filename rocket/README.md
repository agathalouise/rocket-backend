# rocket

O rocket representa uma aplicação que gerencia o cadastro de usuarios para o cartão que concede ao titular
ao seu titular, o uso irrestrito e sem custos do transporte coletivo.


### Collection

A Collection do postman com todos os endpoints está disponível na pasta:
(OBS: a collection já está setada com todos os requests,  tá bem legal, vale a pena baixar)

```
DOCS/collection
```

[rocket-collection](DOCS/rocket.postman_collection.json)

### Stack
- Java 17
- MySql (sorry :( tô sem permissão de instalar sofwares no pc, dai usei o q já tinha >> DEIXEI O SCRIPT SQL NA PASTA DOCS)

### Funções:

- Crud de usuario
- Crud de Funcionario
- Aprovar / Reprovar solicitações do usuário

### Apis externas:
- Via CEP (Valida a região metropolitana)
- Imgur (Salva imagens e retorna a url de acesso)


### Descrição dos arquivos de configuração do projeto

| Recurso                               | Descrição                                                                                        |
|---------------------------------------|--------------------------------------------------------------------------------------------------|
| ./DOCS/rocket.postman_collection.json | Collection com todos os endpoints e requests preenchidos, inclusive com variavel de autenticação |
| ./DOCS/rocket-db.sql                  | Script Sql que cria e preenche as tabelas, caso queira usar outro banco                          |
| ./pom.xml                             | Arquivo de configuração da gestão de dependência deste projeto                                   |


### Endpoint de Monitoração

| Url                    | Descrição                                         |
|------------------------|---------------------------------------------------|
| /actuator/health       | Endpoint para verificar a saude da aplicação      |
| /swagger-ui/index.html | Endpoint para verificar a documentação do projeto |
