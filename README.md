# Top Product API 🛍️

## 📖 Sobre o Projeto
API REST para gerenciamento de produtos, desenvolvida com Spring Boot. Oferece operações CRUD completas com suporte a paginação e ordenação.

## 🔗 URL Base
```
https://toproduct-api-production.up.railway.app
```

## 🚀 Endpoints

### Produtos
- `GET /products` - Lista todos os produtos (paginado)
- `GET /products/{id}` - Obtém um produto específico
- `POST /products` - Cria um novo produto
- `PUT /products/{id}` - Atualiza um produto existente
- `DELETE /products/{id}` - Remove um produto



### Listar Produtos
```http
GET /products
```
- Suporta paginação e ordenação
- Parâmetros:
    - `page` (default: 0)
    - `size` (default: 20)
    - `sort` (ex: price,asc)

### Buscar Produto por ID
```http
GET /products/{id}
```
- ID no formato UUID

### Criar Produto
```http
POST /products
```
Exemplo de corpo da requisição:
```json
{
  "name": "Produto Exemplo",
  "description": "Descrição do produto",
  "price": 99.90,
  "available": true
}
```

### Atualizar Produto
```http
PUT /products/{id}
```
Exemplo de corpo da requisição:
```json
{
  "name": "Produto Atualizado",
  "description": "Nova descrição",
  "price": 149.90,
  "available": true
}
```

### Deletar Produto
```http
DELETE /products/{id}
```

## 📊 Modelo de Dados

### Product
| Campo       | Tipo    | Obrigatório |
|-------------|---------|-------------|
| id          | UUID    | Sim*        |
| name        | String  | Sim         |
| description | String  | Não         |
| price       | Double  | Sim         |
| available   | Boolean | Não         |

*Gerado automaticamente

## 📝 Códigos de Resposta
- `200`: Operação realizada com sucesso
- `204`: Operação realizada com sucesso (sem conteúdo)
- `400`: Produto não encontrado ou dados inválidos

## 🔄 Paginação
A API suporta paginação em suas consultas:
```http
GET /products?page=0&size=20&sort=name,asc
```

## 🛠️ Tecnologias Utilizadas
- Spring Boot
- Spring Data JPA
- MySQL
- Swagger/OpenAPI
- Junit5
- Docker

## 📚 Documentação
Documentação completa da API disponível via Swagger UI em:
```
https://toproduct-api-production.up.railway.app/swagger-ui.html
```
