# 🌌 PlanetTask

This is a complete test automation project designed to validate a simulated **Online Banking System**. It includes API, UI, and Performance testing using modern frameworks and technologies. The entire flow is integrated into a CI/CD pipeline using GitHub Actions.

> ✅ Validates fund transfers, balances, and transaction details via mock APIs  
> ✅ Simulates UI interactions with a fund transfer form  
> ✅ Load tests using JMeter for performance  
> ✅ Full pipeline automation with Allure reporting and QA/Production simulation

---

## 🛠️ Tech Stack

- Java 23+ with Maven
- REST-assured + JUnit 5 for API Testing
- TypeScript + Playwright for UI Testing
- WireMock + Jetty for Mock Server
- JMeter for Performance Testing
- Allure for Reporting
- GitHub Actions for CI/CD

---

## 🚧 Project Structure

```
PlanetTask/
├── api-tests/             # REST-assured test suite
├── mock-server/           # WireMock + Jetty mock API and UI
├── ui-tests/              # Playwright E2E tests
├── load-tests/            # JMeter performance scenarios
├── test-results/          # Allure results output
├── run-all-tests.sh       # Local test execution script
└── .github/workflows/     # GitHub Actions pipeline
```

---

## 🔧 Setup & Execution

### 1. Clone the Repository
```bash
git clone https://github.com/<your-username>/PlanetTask.git
cd PlanetTask
```

### 2. Install Requirements
- Java 23+
- Maven
- Node.js 20+
- Allure CLI
- Playwright browsers:
  ```bash
  cd ui-tests
  npm ci
  npx playwright install
  ```

### 3. Local Test Run
```bash
chmod +x run-all-tests.sh
./run-all-tests.sh
```
This will:
- Start the mock server
- Run API + UI tests
- Generate an Allure report
- Stop the server afterwards

---

## 🚀 GitHub Actions CI/CD

The project features a **multi-job pipeline** simulating a real-world flow:

### Jobs
- **Build**: Compiles the mock server and uploads the JAR as artifact
- **Stage**: Simulated QA environment placeholder
- **Tests**: Runs Unit (mocked), Integration (REST-assured), UI (Playwright) and Performance (JMeter) tests
- **Deploy to Production**: Placeholder for real-world prod deploy

All test results are published with Allure to GitHub Pages for easy access.

---

## 📊 API Tests

Written using **REST-assured** and **JUnit 5**, located under `api-tests/`:

- `AccountBalanceTests`: Validate `/accounts/{id}/balance`
- `FundTransferTests`: Validate `/transactions` POST endpoint
- `TransactionDetailsTests`: Validate `/transactions/{id}` GET
- `TransferScenariosParameterizedTests`: Covers edge cases like 0-amount or same-account transfer

Includes JSON Schema validation and rich Allure annotations.

---

## 🗃️ UI Tests

Located in `ui-tests/`, built with **Playwright + TypeScript**:

- Targets the static HTML served by Jetty
- Follows the Fund Transfer flow
- Parameterized and assertion-rich
- Uses modern Playwright practices and fixtures
- Uses Page Object Model for the sake of demonstration

---

## 📈 Performance Tests

Using JMeter under `load-tests/`:

- `fund-transfer-load.jmx`: Simulates 50 concurrent fund transfers
- Executed as part of CI `tests` job
- Results are converted and sent to Allure

---

## 📘 Mock Server

Built in `mock-server/` with:

- **WireMock** for stubbing API behavior
- **Jetty** to serve a fake HTML frontend
- Endpoints:
  - `POST /transactions`
  - `GET /transactions/{id}`
  - `GET /accounts/{id}/balance`
- Runnable with `java -jar` and `start|stop` CLI options

---

## 🏡 Reporting with Allure

All test layers generate results into `test-results/Allure`. GitHub Actions publishes the report automatically to the `gh-pages` branch.

View the latest test report at:
```
https://<your-username>.github.io/PlanetTask/
```

---

## 📜 Example of Allure Report with history
![image](https://github.com/user-attachments/assets/685bc644-461d-482b-a67e-2009d6f73a37)


---

## 🧑‍💻 Author

- Created by Davi Travaglia | [@sknqadev](https://github.com/sknqadev)
- QA Automation Engineer | E2E | API | Performance Testing

