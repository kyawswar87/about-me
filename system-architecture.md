# System Architecture Diagram

## About Me - AI-Powered Personal Information Assistant

```mermaid
graph TB
    %% External Systems
    subgraph "External Systems"
        PDF[📄 KyawSwaAung.pdf]
        Ollama[🤖 Ollama AI Models<br/>- qwen2.5 (Chat)<br/>- nomic-embed-text (Embeddings)]
    end

    %% Client Layer
    subgraph "Client Layer"
        User[👤 User]
        API[🌐 REST API Client<br/>curl/browser]
    end

    %% Application Layer
    subgraph "Spring Boot Application"
        subgraph "Controllers"
            Controller[🎮 AboutMeController<br/>/me?question={q}<br/>/verify-pdf]
        end
        
        subgraph "Services"
            Service[⚙️ AboutMeService<br/>PDF Verification]
            PdfReader[📖 PdfReader<br/>PDF Processing]
        end
        
        subgraph "AI Components"
            ChatClient[💬 ChatClient<br/>RAG-enabled]
            RAG[🔍 Retrieval-Augmented<br/>Generation Advisor]
            VectorRetriever[🎯 Vector Store<br/>Document Retriever]
        end
        
        subgraph "Runners"
            IngestionRunner[🚀 PdfIngestionRunner<br/>CommandLineRunner]
        end
    end

    %% Data Layer
    subgraph "Data Layer"
        subgraph "PostgreSQL Database"
            PgVector[🗄️ pgvector Extension<br/>vector_store table<br/>768 dimensions]
        end
    end

    %% Flow Connections
    User --> API
    API --> Controller
    
    Controller --> ChatClient
    Controller --> Service
    
    ChatClient --> RAG
    RAG --> VectorRetriever
    VectorRetriever --> PgVector
    
    Service --> PgVector
    
    %% Startup Flow
    IngestionRunner --> PdfReader
    PdfReader --> PDF
    PdfReader --> PgVector
    
    %% AI Model Connections
    ChatClient --> Ollama
    VectorRetriever --> Ollama
    
    %% Styling
    classDef external fill:#e1f5fe,stroke:#01579b,stroke-width:2px
    classDef client fill:#f3e5f5,stroke:#4a148c,stroke-width:2px
    classDef controller fill:#e8f5e8,stroke:#1b5e20,stroke-width:2px
    classDef service fill:#fff3e0,stroke:#e65100,stroke-width:2px
    classDef ai fill:#fce4ec,stroke:#880e4f,stroke-width:2px
    classDef data fill:#f1f8e9,stroke:#33691e,stroke-width:2px
    
    class PDF,Ollama external
    class User,API client
    class Controller controller
    class Service,PdfReader,IngestionRunner service
    class ChatClient,RAG,VectorRetriever ai
    class PgVector data
```

## Data Flow Diagram

```mermaid
sequenceDiagram
    participant U as User
    participant API as REST API
    participant C as Controller
    participant CC as ChatClient
    participant RAG as RAG Advisor
    participant VR as Vector Retriever
    participant DB as PostgreSQL + pgvector
    participant O as Ollama Models

    Note over U,O: Application Startup
    rect rgb(240, 248, 255)
        Note over C,DB: PDF Ingestion Process
        C->>C: PdfIngestionRunner starts
        C->>C: PdfReader.ingestPdf()
        C->>DB: Store PDF embeddings
        Note over DB: Documents stored with vectors
    end

    Note over U,O: Query Processing
    rect rgb(255, 248, 240)
        U->>API: GET /me?question="What is his experience?"
        API->>C: Forward request
        C->>CC: chatClient.prompt().user(question)
        CC->>RAG: Process with RAG advisor
        RAG->>VR: Retrieve similar documents
        VR->>O: Generate embeddings for query
        VR->>DB: Search similar vectors
        DB-->>VR: Return relevant documents
        VR-->>RAG: Pass context documents
        RAG->>O: Generate response with context
        O-->>RAG: AI-generated response
        RAG-->>CC: Return final response
        CC-->>C: Return content
        C-->>API: Return response
        API-->>U: Display answer
    end
```

## Component Interaction Diagram

```mermaid
graph LR
    subgraph "Request Flow"
        A[User Question] --> B[AboutMeController]
        B --> C[ChatClient]
        C --> D[RAG Advisor]
        D --> E[Vector Retriever]
        E --> F[PostgreSQL + pgvector]
        F --> G[Similar Documents]
        G --> H[Ollama AI Models]
        H --> I[Generated Response]
        I --> J[User Answer]
    end

    subgraph "Startup Flow"
        K[Application Start] --> L[PdfIngestionRunner]
        L --> M[PdfReader]
        M --> N[PDF Document]
        M --> O[Generate Embeddings]
        O --> P[Store in Vector DB]
    end

    classDef request fill:#e3f2fd,stroke:#1976d2,stroke-width:2px
    classDef startup fill:#f1f8e9,stroke:#388e3c,stroke-width:2px
    
    class A,B,C,D,E,F,G,H,I,J request
    class K,L,M,N,O,P startup
```

## Technology Stack Overview

```mermaid
graph TB
    subgraph "Frontend/Client"
        Client[🌐 HTTP Client<br/>curl, Postman, Browser]
    end

    subgraph "Application Layer"
        SpringBoot[☕ Spring Boot 3.5.6<br/>Java 25]
        SpringAI[🤖 Spring AI 1.0.2<br/>RAG Framework]
    end

    subgraph "AI/ML Layer"
        Ollama[🦙 Ollama<br/>Local AI Models]
        Qwen[🧠 qwen2.5<br/>Chat Model]
        Embed[📊 nomic-embed-text<br/>Embedding Model]
    end

    subgraph "Data Layer"
        PostgreSQL[🐘 PostgreSQL<br/>Database]
        PgVector[📐 pgvector<br/>Vector Extension]
    end

    subgraph "Document Processing"
        PDF[📄 PDF Document<br/>KyawSwaAung.pdf]
        PDFReader[📖 PDF Reader<br/>Paragraph Extraction]
    end

    Client --> SpringBoot
    SpringBoot --> SpringAI
    SpringAI --> Ollama
    Ollama --> Qwen
    Ollama --> Embed
    SpringAI --> PostgreSQL
    PostgreSQL --> PgVector
    SpringBoot --> PDFReader
    PDFReader --> PDF

    classDef frontend fill:#e8eaf6,stroke:#3f51b5,stroke-width:2px
    classDef app fill:#e0f2f1,stroke:#00695c,stroke-width:2px
    classDef ai fill:#fff3e0,stroke:#f57c00,stroke-width:2px
    classDef data fill:#fce4ec,stroke:#c2185b,stroke-width:2px
    classDef doc fill:#f3e5f5,stroke:#7b1fa2,stroke-width:2px

    class Client frontend
    class SpringBoot,SpringAI app
    class Ollama,Qwen,Embed ai
    class PostgreSQL,PgVector data
    class PDF,PDFReader doc
```
