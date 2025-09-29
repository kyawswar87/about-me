# About Me - AI-Powered Personal Information Assistant

A Spring Boot application that uses AI to answer questions about a person (Kyaw Swa Aung) by leveraging PDF document analysis, vector search capabilities, and Model Context Protocol (MCP) integration.

## 🚀 Project Overview

This application demonstrates the power of **Retrieval-Augmented Generation (RAG)** using Spring AI framework with advanced MCP client integration. It:

- Ingests a PDF resume/CV document into a PostgreSQL vector database
- Uses AI models (Ollama) to answer questions about the person with enhanced context
- Provides a REST API for querying personal information
- Leverages semantic search to find relevant information from the PDF
- Integrates with MCP servers for extended functionality and tool access

## 🏗️ Architecture

The application uses the following technology stack:

- **Spring Boot 3.5.6** - Main framework
- **Spring AI 1.0.2** - AI integration framework with RAG capabilities
- **Ollama** - Local AI model hosting (qwen2.5, nomic-embed-text)
- **PostgreSQL with pgvector** - Vector database for embeddings
- **Model Context Protocol (MCP)** - Client integration for extended tool access
- **Java 25** - Programming language
- **Maven** - Build and dependency management

📊 **For detailed system architecture diagrams and component interactions, see [System Architecture Documentation](system-architecture.md)**

## ✨ Key Features

### 🤖 AI-Powered Question Answering
- **RAG Implementation**: Uses Retrieval-Augmented Generation for context-aware responses
- **Semantic Search**: Leverages vector embeddings for intelligent document retrieval
- **Multi-Model Support**: Integrates both chat and embedding models via Ollama

### 📄 Document Processing
- **PDF Ingestion**: Automatic processing of PDF documents on application startup
- **Vector Storage**: Documents stored as embeddings in PostgreSQL with pgvector
- **Real-time Verification**: API endpoint to verify document ingestion status

### 🔧 MCP Integration
- **Tool Access**: Connects to MCP servers for extended functionality
- **Synchronous Communication**: Uses SSE (Server-Sent Events) for real-time updates
- **Extensible Architecture**: Easy integration with additional MCP tools and services

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
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/pdf_agent_db
spring.datasource.username=your_username
spring.datasource.password=your_password

# Ollama Configuration
spring.ai.ollama.base-url=http://localhost:11434/
spring.ai.ollama.chat.options.model=qwen2.5
spring.ai.ollama.embedding.options.model=nomic-embed-text
spring.ai.ollama.init.pull-model-strategy=when_missing

# Vector Store Configuration
spring.ai.vectorstore.pgvector.table-name=vector_store
spring.ai.vectorstore.pgvector.dimensions=768
spring.ai.vectorstore.pgvector.schema-name=public
spring.ai.vectorstore.pgvector.remove-existing-vector-store-table=true
spring.ai.vectorstore.pgvector.initialize-schema=true

# MCP Client Configuration
spring.ai.mcp.client.sse.type=SYNC
spring.ai.mcp.client.sse.connections.server1.url=http://localhost:8081

# Logging Configuration
logging.level.org.springframework.ai.chat.client.advisor=DEBUG
```

### 4. MCP Server Setup (Optional)

If you want to use MCP functionality, ensure you have an MCP server running on `http://localhost:8081`. The application will automatically connect to it on startup.

### 5. PDF Document

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

Query the AI assistant about information from the PDF document.

**Parameters:**
- `question` (required)
    - The question you want to ask about Kyaw Swa Aung
    - send email with compose message

**Example Requests:**
```bash
# Basic question
curl "http://localhost:8080/me?question=What is Kyaw Swa Aung's experience?"

# More specific questions
curl "http://localhost:8080/me?question=What programming languages does he know?"
curl "http://localhost:8080/me?question=What are his educational qualifications?"
curl "http://localhost:8080/me?question=Tell me about his work experience"
curl "http://localhost:8080/me?question=compose a email message about that "I want to interview him" and send to him"
```

**Response Format:**
```
Plain text response with AI-generated answer based on the PDF content
```

### 2. Verify PDF Ingestion

**GET** `/verify-pdf`

Verify that the PDF document has been successfully processed and stored in the vector database.

**Example Request:**
```bash
curl "http://localhost:8080/verify-pdf"
```

**Response Format:**
```
PDF Verification Results:
Total documents found: 5

Document 1:
ID: doc-123
Content preview: Kyaw Swa Aung is a software engineer with 5 years of experience...
Metadata: {source=KyawSwaAung.pdf, page=1}

Document 2:
ID: doc-124
Content preview: He has expertise in Java, Spring Boot, and microservices...
Metadata: {source=KyawSwaAung.pdf, page=2}
...
```

This endpoint returns detailed information about the documents stored in the vector database, including document IDs, content previews, and metadata.

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

### Key Components

- **AboutmeApplication.java**: Main application class with ChatClient configuration, RAG advisor setup, and MCP tool integration
- **AboutMeController.java**: REST controller handling `/me` and `/verify-pdf` endpoints
- **AboutMeService.java**: Service layer for vector store operations and document verification
- **PdfReader.java**: Handles PDF document parsing and text extraction
- **PdfIngestionRunner.java**: Command line runner that processes PDF on application startup

## 🔄 How It Works

1. **Application Startup**: The `PdfIngestionRunner` automatically ingests the PDF document into the vector database
2. **PDF Processing**: The `PdfReader` component reads the PDF and creates embeddings
3. **Vector Storage**: Documents are stored in PostgreSQL with pgvector extension
4. **Query Processing**: When a question is asked:
   - The question is converted to embeddings
   - Similar documents are retrieved from the vector store
   - The AI model generates a response using the retrieved context

## 🔧 Troubleshooting

### Common Issues and Solutions

#### 1. Database Connection Issues
**Problem**: Application fails to start with database connection errors
**Solution**: 
- Verify PostgreSQL is running: `pg_ctl status`
- Check database credentials in `application.properties`
- Ensure pgvector extension is installed: `CREATE EXTENSION IF NOT EXISTS vector;`

#### 2. Ollama Model Issues
**Problem**: Models not found or connection refused
**Solution**:
- Start Ollama service: `ollama serve`
- Pull required models: `ollama pull qwen2.5 && ollama pull nomic-embed-text`
- Check Ollama is accessible: `curl http://localhost:11434/api/tags`

#### 3. PDF Processing Issues
**Problem**: PDF not being processed or empty responses
**Solution**:
- Verify PDF file exists in `src/main/resources/`
- Check PDF is not corrupted or password-protected
- Review application logs for processing errors
- Use `/verify-pdf` endpoint to check document ingestion

#### 4. MCP Connection Issues
**Problem**: MCP client connection failures
**Solution**:
- Ensure MCP server is running on `http://localhost:8081`
- Check MCP server configuration and logs
- Verify network connectivity between client and server
- MCP functionality is optional - application works without it

#### 5. Vector Store Issues
**Problem**: Vector store table creation or query failures
**Solution**:
- Check PostgreSQL permissions for table creation
- Verify pgvector extension is properly installed
- Review vector store configuration in `application.properties`
- Check available disk space for vector storage

### Debug Mode

Enable debug logging by adding to `application.properties`:
```properties
logging.level.org.springframework.ai=DEBUG
logging.level.com.example.aboutme=DEBUG
```

## 📝 Development Notes

- The application uses **Retrieval-Augmented Generation (RAG)** pattern
- PDF documents are automatically processed on application startup
- The system is configured to remove and recreate the vector store table on each startup
- Debug logging is enabled for the chat client advisor
- MCP integration provides extensible tool access capabilities
- Vector similarity threshold is set to 0.30 for optimal retrieval accuracy
