# Java IA - Assistente Inteligente com Spring Boot & LangChain4j

Projeto prático demonstrando a integração do ecossistema **Java (Spring Boot)** com Inteligência Artificial utilizando a biblioteca **LangChain4j** e o modelo **Google Gemini** (via Google AI Studio).

A aplicação simula um assistente corporativo para locadora de veículos, demonstrando o uso de **AI Services** declarativos e **Tool Calling / Function Calling** (onde o modelo de IA decide autonomamente quando acionar regras de negócio e métodos Java nativos para cálculos e regras específicas).

---

## Tecnologias Utilizadas

- **Java 21+**
- **Spring Boot 3** (Spring Web)
- **LangChain4j** (`langchain4j-spring-boot-starter`, `langchain4j-google-ai-gemini-spring-boot-starter`)
- **Google Gemini API** (`gemini-2.5-flash` / `gemini-1.5-flash`)
- **Maven**

---

## Arquitetura e Funcionamento

```
Cliente / Postman
       │
       ▼ (POST /api/assistant)
[ AssistantController ]
       │
       ▼
[ AssistantAIService ]  ◄───► [ Google Gemini LLM ]
       │                               ▲
       │ (Decide se precisa executar)  │
       ▼                               │
[ AssistantTools (@Tool) ] ────────────┘
(Cálculo de diárias + seguro corporativo)
```

1. **Perguntas Informativas** (ex: *"Quais categorias existem?"*, *"Como funciona a política de combustível?"*):
   - O `AssistantAIService` consulta diretamente o LLM com base nas instruções de sistema (*System Message*) e responde ao usuário sem disparar ferramentas internas.
2. **Cotações e Cálculos** (ex: *"Simule o aluguel de um SUV por 15 dias"*):
   - O modelo identifica a intenção e os parâmetros (categoria e quantidade de dias), aciona a ferramenta `@Tool` em `AssistantTools` (`calculateCotacao`), processa o cálculo com as taxas internas e retorna a resposta final estruturada.

---

## Pré-requisitos

- **JDK 21** ou superior instalado
- **Maven 3.8+**
- Uma chave de API do **Google AI Studio** ([Obter chave aqui](https://aistudio.google.com/))

---

## Configuração e Execução

### 1. Clonar o Repositório
```bash
git clone https://github.com/Demanxier/Java_IA.git
cd Java_IA
```

### 2. Configurar a Chave de API
Abra o arquivo `src/main/resources/application.properties` e configure a sua API Key e o modelo:

```properties
spring.application.name=Java_IA

gemini.api.key=SUA_CHAVE_AQUI
gemini.model=gemini-2.5-flash
spring.ai.google.genai.api-key=${GEMINI_API_KEY:XXXXXXXXXXXXXXXXXXXXX}
```

> **Dica de Segurança:** Evite comitar sua chave diretamente no repositório. Você também pode passar como variável de ambiente ou parâmetro de execução:
> ```properties
> gemini.api.key=${GEMINI_API_KEY}
> ```

### 3. Compilar e Executar
```bash
mvn clean spring-boot:run
```
A aplicação iniciará na porta padrão `8080`.

---

## Testando os Endpoints

### **Endpoint:**
`POST http://localhost:8080/api/assistant`

### **Headers:**
`Content-Type: text/plain` (ou JSON conforme payload configurado)

---

### **Exemplos de Requisições:**

#### 1. Pergunta Geral (Sem acionamento de Tool)
- **Mensagem:**
  ```text
  Quais categorias de veículos vocês oferecem?
  ```
- **Resposta Esperada:**
  ```text
  Oferecemos as seguintes categorias de veículo para locação corporativa: econômico, SUV e premium.
  ```

#### 2. Cotação Completa (Com acionamento de Tool)
- **Mensagem:**
  ```text
  Simule o aluguel de um SUV por 15 dias.
  ```
- **Resposta Esperada:**
  ```text
  A cotação para o aluguel de um SUV por 15 dias é de R$ 4.536,00 (já incluído o seguro de 8%).
  ```

#### 3. Dados Incompletos
- **Mensagem:**
  ```text
  Quanto custa o SUV?
  ```
- **Resposta Esperada:**
  ```text
  Para calcular o custo do aluguel do SUV, preciso saber por quantos dias você pretende alugar.
  ```

---

## 📚 Referências

- Baseado na aula da **Michelli Brito**: [Aprenda IA com Java LangChain4j | Arquitetura Spring Boot + Gemini na prática](https://www.youtube.com/watch?v=A5i7D7RAPA4)
- Documentação oficial do [LangChain4j](https://docs.langchain4j.dev/)
