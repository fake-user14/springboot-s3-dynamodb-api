# Spring Boot + AWS S3 + DynamoDB API

## 📌 Overview
This project demonstrates a *Spring Boot (Java 11)* REST API that integrates with *AWS S3* and *AWS DynamoDB*.

The application allows users to:
- Upload files to *Amazon S3*
- Store file metadata in *Amazon DynamoDB*
- Return a public S3 file URL after upload

This project showcases *real-world AWS integration* using *AWS SDK v2* and Spring Boot best practices.

---

## 🏗️ Architecture

```mermaid
flowchart LR
    A[Client / Postman] --> B[Spring Boot REST API]
    B --> C[Amazon S3]
    B --> D[Amazon DynamoDB]
    C --> E[Public File URL]
    E --> A

## 🚀 Features
- Spring Boot REST API (Java 11)
- Multipart file upload support
- Amazon S3 integration
- Amazon DynamoDB integration
- AWS SDK v2 with BOM-based dependency management
- Clean layered architecture

## 🧩 Tech Stack
- Java 11
- Spring Boot 2.7.x
- AWS SDK v2
- Amazon S3
- Amazon DynamoDB
- Maven