# Integrantes do Grupo 
  * **Márcio Gastaldi - RM98811**
  * **Arthur Bessa Pian - RM99215**
  * **Davi Desenzi - RM550849**

# Gerenciamento de Alertas e Clima com Spring Boot (API RESTful)

Este projeto Spring Boot demonstra uma arquitetura orientada a serviços (SOA) para gerenciar alertas de eventos (simulando falta de energia) e integrar dados climáticos em tempo real de uma API externa (OpenWeatherMap).

## 🚀 Funcionalidades

* **Gerenciamento de Alertas:** Crie, liste e registre alertas de interrupções de energia.
* **Integração com API Externa:** Consome a API OpenWeatherMap para obter dados climáticos em tempo real para qualquer cidade.
* **Arquitetura Modular:** Separação clara de responsabilidades em camadas de controle, serviço e dados.

## 🛠️ Tecnologias Utilizadas

* **Java 17+**
* **Spring Boot 3.2.5+**
* **Spring Web:** Para construir APIs RESTful.
* **Spring Data JPA:** Para persistência de dados no banco de dados.
* **H2 Database:** Banco de dados em memória para desenvolvimento e testes.
* **`RestTemplate`:** Cliente HTTP para consumir APIs externas.
* **Maven:** Ferramenta de automação de build.
* **OpenWeatherMap API:** API externa de dados climáticos.

## ⚙️ Configuração e Execução

### Pré-requisitos

* **Java Development Kit (JDK) 17 ou superior** instalado.
* **Maven** instalado.
* Uma **API Key gratuita da OpenWeatherMap**.
    1.  Vá para [https://openweathermap.org/](https://openweathermap.openweathermap.org/).
    2.  Registre-se ou faça login.
    3.  Acesse a seção "My API keys" ([https://home.openweathermap.org/api_keys](https://home.openweathermap.org/api_keys)) e copie sua chave.

### Estrutura do Projeto

Certifique-se de que seu projeto tem a seguinte estrutura de pastas e arquivos:

```
gestao-energia/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── energia/
│   │   │           └── gestao/
│   │   │               ├── GestaoEnergiaApplication.java
│   │   │               ├── controller/
│   │   │               │   └── AlertaController.java
│   │   │               ├── model/
│   │   │               │   ├── AlertaInterrupcao.java
│   │   │               │   └── WeatherData.java
│   │   │               ├── repository/
│   │   │               │   └── AlertaRepository.java
│   │   │               └── service/
│   │   │                   ├── AlertaService.java
│   │   │                   └── ExternalApiService.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── energia/
│                   └── gestao/
│                       └── GestaoEnergiaApplicationTests.java
└── README.md
```

### Passos

1.  **Clone ou Baixe o Projeto:**
    Se você estiver usando um repositório Git, clone-o:
    ```bash
    git clone <url-do-seu-repositorio>
    cd gestao-energia
    ```
    Caso contrário, descompacte o arquivo ZIP do projeto na sua máquina.

2.  **Configurar a API Key da OpenWeatherMap:**
    Abra o arquivo `src/main/java/com/energia/gestao/service/ExternalApiService.java`.
    Localize a linha:
    ```java
    private final String OPENWEATHER_API_KEY = "SUA_API_KEY_AQUI"; // <-- *** SUBSTITUA PELA SUA CHAVE REAL ***
    ```
    Substitua `"SUA_API_KEY_AQUI"` pela sua chave de API real que você obteve da OpenWeatherMap.

3.  **Compilar e Executar a Aplicação:**

    * **Via IDE (Recomendado para Desenvolvimento):**
        * Abra o projeto no seu Visual Studio Code (com as extensões Java instaladas), IntelliJ IDEA, Eclipse (com Spring Tools 4) ou outra IDE.
        * Navegue até o arquivo `src/main/java/com/energia/gestao/GestaoEnergiaApplication.java`.
        * Clique no botão "Run" (geralmente um triângulo verde) ao lado do método `main()` ou na sua barra de ferramentas.

    * **Via Linha de Comando (Maven):**
        Abra o terminal na pasta raiz do projeto (onde está o `pom.xml`) e execute:
        ```bash
        mvn spring-boot:run
        ```

    A aplicação será iniciada na porta padrão `8080`. Você verá logs no console indicando o início do servidor Tomcat e o mapeamento dos endpoints.

## 🌐 Endpoints da API

A API estará disponível em `http://localhost:8080/api`.

### 1. Gerenciamento de Alertas

#### a. Listar Todos os Alertas

* **Método:** `GET`
* **URL:** `http://localhost:8080/api/alertas`
* **Descrição:** Retorna uma lista de todos os alertas de interrupção de energia registrados.
* **Exemplo de Resposta (JSON):**
    ```json
    [
      {
        "id": 1,
        "idMedidor": "MED002",
        "tipoAlerta": "queda_energia",
        "descricao": "Queda de energia na região sul.",
        "dataHora": "2025-06-05T23:30:00",
        "resolvido": false
      }
    ]
    ```

#### b. Reportar Queda de Energia

* **Método:** `POST`
* **URL:** `http://localhost:8080/api/alertas/reportar-queda`
* **Descrição:** Simula o reporte de uma queda de energia para um medidor específico.
    * **Observação:** No código, `MED002` é hardcoded como "offline" para testes. Outros IDs serão considerados online.
* **Parâmetros de Consulta (Query Parameters):**
    * `idMedidor` (String): ID do medidor afetado (ex: "MED002").
    * `descricao` (String): Descrição da ocorrência (ex: "Sem luz desde as 22h").
* **Exemplo de Consulta (com `curl`):**
    ```bash
    curl -X POST "http://localhost:8080/api/alertas/reportar-queda?idMedidor=MED002&descricao=Queda de energia na rua principal"
    ```
* **Exemplo de Resposta (JSON - Sucesso):**
    ```json
    {
      "id": 2,
      "idMedidor": "MED002",
      "tipoAlerta": "queda_energia",
      "descricao": "Queda de energia na rua principal",
      "dataHora": "2025-06-05T23:35:00",
      "resolvido": false
    }
    ```
* **Exemplo de Resposta (JSON - Erro 400 Bad Request):**
    ```json
    "Medidor MED001 está online, não há queda de energia reportada."
    ```

#### c. Criar Alerta Genérico

* **Método:** `POST`
* **URL:** `http://localhost:8080/api/alertas`
* **Descrição:** Cria um alerta de qualquer tipo.
* **Corpo da Requisição (JSON - `Content-Type: application/json`):**
    ```json
    {
      "idMedidor": "MED001",
      "tipoAlerta": "alto_consumo",
      "descricao": "Consumo acima do normal detectado.",
      "resolvido": false
    }
    ```
* **Exemplo de Consulta (com `curl`):**
    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{
      "idMedidor": "MED001",
      "tipoAlerta": "alto_consumo",
      "descricao": "Consumo acima do normal detectado.",
      "resolvido": false
    }' http://localhost:8080/api/alertas
    ```
* **Exemplo de Resposta (JSON - Sucesso):**
    ```json
    {
      "id": 3,
      "idMedidor": "MED001",
      "tipoAlerta": "alto_consumo",
      "descricao": "Consumo acima do normal detectado.",
      "dataHora": "2025-06-05T23:40:00",
      "resolvido": false
    }
    ```

### 2. Consulta de Dados Climáticos (OpenWeatherMap)

#### a. Obter Dados Climáticos por Cidade

* **Método:** `GET`
* **URL:** `http://localhost:8080/api/clima/{city}`
* **Descrição:** Busca dados climáticos atuais (temperatura, umidade, vento, etc.) de uma cidade específica usando a API OpenWeatherMap.
* **Variável de Caminho (Path Variable):**
    * `city` (String): Nome da cidade (ex: "Sao Paulo", "London", "Tokyo").
* **Exemplo de Consulta (com navegador ou `curl`):**
    ```bash
    http://localhost:8080/api/clima/Sao Paulo
    ```bash
    curl http://localhost:8080/api/clima/London
    ```
* **Exemplo de Resposta (JSON):**
    ```json
    {
      "coord": { "lon": -46.6333, "lat": -23.5505 },
      "weather": [
        {
          "id": 801,
          "main": "Clouds",
          "description": "few clouds",
          "icon": "02d"
        }
      ],
      "main": {
        "temp": 25.5,
        "feels_like": 25.8,
        "temp_min": 24.0,
        "temp_max": 27.0,
        "pressure": 1012,
        "humidity": 60
      },
      "wind": { "speed": 4.12, "deg": 130 },
      "name": "Sao Paulo",
      "dt": 1717540800
    }
    ```
    * Os valores de temperatura (`temp`), umidade (`humidity`), etc., serão os dados reais no momento da sua requisição.
* **Possíveis Respostas de Erro:**
    * **Status 500 Internal Server Error:** Se sua API Key não estiver configurada corretamente no `ExternalApiService.java` ou se houver um erro na comunicação com a API OpenWeatherMap (ex: cidade inválida, problema na rede). Verifique os logs da sua aplicação para detalhes.

## 📚 Estrutura do Código

O projeto segue a arquitetura de camadas padrão do Spring Boot:

* **`com.energia.gestao` (Pacote Raiz):**
    * `GestaoEnergiaApplication.java`: Classe principal para iniciar a aplicação.
* **`com.energia.gestao.controller`:**
    * `AlertaController.java`: Camada de controle. Expõe os endpoints REST e lida com as requisições HTTP.
* **`com.energia.gestao.service`:**
    * `AlertaService.java`: Camada de serviço. Contém a lógica de negócio para o gerenciamento de alertas.
    * `ExternalApiService.java`: Camada de serviço. É responsável por consumir a API externa do OpenWeatherMap.
* **`com.energia.gestao.model`:**
    * `AlertaInterrupcao.java`: Modelo de dados (entidade JPA) para representar alertas de interrupção.
    * `WeatherData.java`: Modelo de dados para mapear a resposta da API do OpenWeatherMap, incluindo classes aninhadas para a estrutura JSON.
* **`com.energia.gestao.repository`:**
    * `AlertaRepository.java`: Camada de dados. Interfaces Spring Data JPA para interagir com o banco de dados H2.

## 🧪 Como Testar

1.  Inicie a aplicação conforme descrito na seção "Configuração e Execução".
2.  Use um navegador, `curl`, Postman, Insomnia ou outra ferramenta de cliente HTTP para enviar requisições aos endpoints descritos na seção "Endpoints da API".
3.  Monitore o console da sua aplicação para ver os logs e confirmar que as chamadas às APIs e a lógica de negócio estão sendo executadas.
