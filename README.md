# About Me - AI-Powered Personal Information Assistant

A Spring Boot application that uses AI to answer questions about a person (Kyaw Swa Aung) by leveraging PDF document analysis and vector search capabilities.

## 🚀 Project Overview

This application demonstrates the power of **Retrieval-Augmented Generation (RAG)** using Spring AI framework. It:

- Ingests a PDF resume/CV document into a PostgreSQL vector database
- Uses AI models (Ollama) to answer questions about the person
- Provides a REST API for querying personal information
- Leverages semantic search to find relevant information from the PDF

## 🏗️ Architecture

The application uses the following technology stack:

- **Spring Boot 3.5.6** - Main framework
- **Spring AI 1.0.2** - AI integration framework
- **Ollama** - Local AI model hosting
- **PostgreSQL with pgvector** - Vector database for embeddings
- **Java 25** - Programming language

📊 **For detailed system architecture diagrams and component interactions, see [System Architecture Documentation](system-architecture.md)**

## 📋 Prerequisites

Before running this application, ensure you have:

1. **Java 25** installed
2. **PostgreSQL** with pgvector extension installed
3. **Ollama** installed and running locally
4. **Maven** for dependency management

## 🛠️ Setup Instructions

### 1. Database Setup

Create a PostgreSQL database and enable the pgvector extension:

```sql
-- Create database
CREATE DATABASE pdf_agent_db;

-- Connect to the database and enable pgvector
\c pdf_agent_db;
CREATE EXTENSION IF NOT EXISTS vector;
```

### 2. Ollama Setup

Install and start Ollama, then pull the required models:

```bash
# Install Ollama (if not already installed)
# Visit: https://ollama.ai/

# Start Ollama service
ollama serve

# Pull required models (in separate terminal)
ollama pull qwen2.5
ollama pull nomic-embed-text
```

### 3. Application Configuration

Update the database credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/pdf_agent_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 4. PDF Document

Place your PDF file (`KyawSwaAung.pdf`) in the `src/main/resources/` directory.

## 🚀 Running the Application

### Option 1: Using Maven Wrapper

```bash
# Navigate to project directory
cd /aboutme

# Run the application
./mvnw spring-boot:run
```

### Option 2: Using Maven

```bash
# Compile and run
mvn clean compile
mvn spring-boot:run
```

### Option 3: Using IDE

Run the `AboutmeApplication.java` main class directly from your IDE.

## 📡 API Endpoints

Once the application is running, you can access the following endpoints:

### 1. Ask Questions About the Person

**GET** `/me?question={your_question}`

Example:
```bash
curl "http://localhost:8080/me?question=What is Kyaw Swa Aung's experience?"
```

### 2. Verify PDF Ingestion

**GET** `/verify-pdf`

Example:
```bash
curl "http://localhost:8080/verify-pdf"
```

This endpoint returns information about the documents stored in the vector database.

## 🔧 Configuration Details

### AI Models Used

- **Chat Model**: `qwen2.5` - For generating responses
- **Embedding Model**: `nomic-embed-text` - For creating vector embeddings

### Vector Store Configuration

- **Database**: PostgreSQL with pgvector extension
- **Table**: `vector_store`
- **Dimensions**: 768 (for nomic-embed-text model)
- **Similarity Threshold**: 0.30

### Application Properties

Key configuration options in `application.properties`:

```properties
# Ollama configuration
spring.ai.ollama.base-url=http://localhost:11434/
spring.ai.ollama.chat.options.model=qwen2.5
spring.ai.ollama.embedding.options.model=nomic-embed-text

# Vector store configuration
spring.ai.vectorstore.pgvector.table-name=vector_store
spring.ai.vectorstore.pgvector.dimensions=768
spring.ai.vectorstore.pgvector.remove-existing-vector-store-table=true
spring.ai.vectorstore.pgvector.initialize-schema=true
```

## 🏛️ Project Structure

```
src/
├── main/
│   ├── java/com/example/aboutme/
│   │   ├── AboutmeApplication.java      # Main Spring Boot application
│   │   ├── AboutMeController.java       # REST API endpoints
│   │   ├── AboutMeService.java          # Business logic for PDF verification
│   │   ├── PdfReader.java               # PDF ingestion component
│   │   └── PdfIngestionRunner.java              # Command line runner for PDF ingestion
│   └── resources/
│       ├── application.properties       # Application configuration
│       └── KyawSwaAung.pdf             # PDF document to be analyzed
└── test/
    └── java/com/example/aboutme/
        └── AboutmeApplicationTests.java # Unit tests
```

## 🔄 How It Works

1. **Application Startup**: The `PdfIngestionRunner` automatically ingests the PDF document into the vector database
2. **PDF Processing**: The `PdfReader` component reads the PDF and creates embeddings
3. **Vector Storage**: Documents are stored in PostgreSQL with pgvector extension
4. **Query Processing**: When a question is asked:
   - The question is converted to embeddings
   - Similar documents are retrieved from the vector store
   - The AI model generates a response using the retrieved context

## 📝 Development Notes

- The application uses **Retrieval-Augmented Generation (RAG)** pattern
- PDF documents are automatically processed on application startup
- The system is configured to remove and recreate the vector store table on each startup
- Debug logging is enabled for the chat client advisor
