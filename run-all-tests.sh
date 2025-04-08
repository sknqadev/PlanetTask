#!/bin/bash

echo "🚀 Iniciando mock-server..."
cd mock-server/ || exit 1
mvn exec:java -Dexec.mainClass="com.example.mock.MockServer" -Dexec.args="start" &
MOCK_PID=$!
cd ..

sleep 3

echo "🧪 Executando testes de API..."
cd api-tests/ || exit 1
mvn clean test -D allure.results.directory=../allure-results
cd ..

echo "🌐 Executando testes de UI (Playwright)..."
cd ui-tests/ || exit 1

# Garante que não está sujo
rm -rf allure-results

# Executa os testes
npx playwright test

# Garante que a pasta principal allure-results exista na raiz
mkdir -p ../allure-results

# Copia os resultados do Playwright para a raiz do projeto
cp -r allure-results/* ../allure-results/ 2>/dev/null || echo "⚠️ Nenhum resultado do Playwright encontrado."
cd ..

echo "📊 Gerando relatório Allure..."
npx allure generate allure-results -o allure-report
npx allure open allure-report &

echo "🛑 Finalizando mock-server..."
kill "$MOCK_PID"

echo ""
read -p "✅ Pressione [Enter] para encerrar..."
