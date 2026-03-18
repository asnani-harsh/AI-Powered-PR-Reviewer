# 🚀 AI-Powered PR Reviewer

<p align="center">
  <b>Automate code reviews with AI — faster feedback, better code, zero friction.</b>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange"/>
  <img src="https://img.shields.io/badge/SpringBoot-3.x-brightgreen"/>
  <img src="https://img.shields.io/badge/Architecture-Microservice-blue"/>
  <img src="https://img.shields.io/badge/AI-Integrated-purple"/>
</p>

---

## 📌 Overview

**AI-Powered PR Reviewer** is a Spring Boot microservice that brings **intelligent, automated code reviews** directly into your GitHub workflow.

It leverages AI to analyze pull requests and generate **context-aware review comments**, helping teams:

- 🚀 Ship faster  
- 🧠 Improve code quality  
- 🔁 Reduce manual review overhead  

---

## ⚡ Key Highlights

### 🤖 AI-Driven Code Reviews
Automatically generate meaningful, contextual feedback using AI (Claude-based, extensible).

### 🔄 Dual Execution Modes
- **Manual API Trigger** → On-demand PR reviews  
- **GitHub Webhook Automation** → Fully automated review pipeline  

### 🧩 Clean Architecture
- Layered Spring Boot design  
- DTO-driven contracts  
- Centralized exception handling  

### 🧪 Production-Ready Design
- Async processing  
- External API integrations (GitHub + AI)  
- Configurable and extensible  

---

## 🏗️ Architecture

```text
                ┌──────────────────────┐
                │   GitHub Webhook     │
                └─────────┬────────────┘
                          │
                          ▼
                ┌──────────────────────┐
                │   Controller Layer   │
                └─────────┬────────────┘
                          │
                          ▼
                ┌──────────────────────┐
                │    Service Layer     │
                │  (Business Logic)    │
                └─────────┬────────────┘
                          │
        ┌─────────────────┴─────────────────┐
        ▼                                   ▼
┌───────────────┐                 ┌──────────────────┐
│ GitHub Client │                 │   AI Provider    │
└───────────────┘                 └──────────────────┘
```

---

## 🚀 Features

### ✅ Manual Review API

Trigger AI review manually:

```
POST /api/v1/pull-requests/{owner}/{repo}/{prNumber}/ai-review
```

✔ Ideal for:
- CI pipelines  
- Bots  
- Developer-triggered reviews  

---

### 🤖 Automated Webhook Reviews

Endpoint:

```
POST /webhook/github
```

Triggers on:
- `pull_request.opened`
- `pull_request.synchronize`

✔ Fully hands-free workflow  
✔ Ensures every PR is reviewed  

---

## 🧠 Core Modules

| Module        | Responsibility |
|--------------|--------------|
| `controller/` | API endpoints & webhook handling |
| `service/`    | Business logic & integrations |
| `model/`      | DTOs and payload structures |
| `exception/`  | Centralized error handling |

---

## 🛠️ Tech Stack

- Java 17  
- Spring Boot 3.x  
- Lombok  
- GitHub API (kohsuke.github)  
- AI Integration (Claude - pluggable)  
- Maven  

---

## ▶️ Getting Started

### 1. Clone the Repository

```
git clone https://github.com/your-username/ai-pr-reviewer.git
cd ai-pr-reviewer
```

### 2. Build & Run

```
mvn clean package
java -jar target/my-app-0.0.1-SNAPSHOT.jar
```

---

## ⚙️ Configuration

Update `application.yml`:

```yaml
github:
  token: YOUR_GITHUB_TOKEN

ai:
  provider: claude
  apiKey: YOUR_AI_API_KEY
```

---

## 🧪 How to Test

1. Create a PR in your repository  
2. Call the API OR enable webhook  
3. Check PR comments for AI feedback  

---

## ✨ Why This Project Stands Out

✔ Real-world developer workflow automation  
✔ Event-driven design (webhooks + async)  
✔ Clean, maintainable architecture  
✔ External API integration (GitHub + AI)  
✔ Demonstrates scalable backend design  

---

## 🔮 Future Enhancements

- 🔄 Multi-AI provider support (OpenAI, Gemini, etc.)
- 💬 Inline code suggestions (line-level comments)
- 🧠 Review memory & deduplication
- 📊 PR quality scoring dashboard
- 🔐 Role-based review policies

---

## 🤝 Contributing

Contributions are welcome!

```
# Fork the repo
# Create a feature branch
# Submit a PR 🚀
```

---

## 📄 License

MIT License

---

## ⭐ Support

If you find this useful:

👉 Star the repo  
👉 Share with your team  
👉 Build on top of it  

---

## 💡 Pro Tip

Pair this service with CI/CD (GitHub Actions) to create a **fully automated AI code review pipeline** 🔥