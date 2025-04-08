#!/bin/bash

echo "Starting mock-server..."
cd mock-server/ || exit 1
mvn exec:java -Dexec.mainClass="com.example.mock.MockServer" -Dexec.args="start" &
MOCK_PID=$!
cd ..

sleep 3

echo "Running API Tests..."
cd api-tests/ || exit 1
mvn clean test -D allure.results.directory=../test-results/Allure
cd ..

echo "Running UI Tests..."
cd ui-tests/ || exit 1
npx playwright test
cd ..

echo "Creating Allure Report..."
cd test-results/ || exit 1
npx allure generate Allure -o Allure-report
npx allure open Allure-report &
cd ..

echo "Stop mock-server..."
kill "$MOCK_PID"

echo ""
read -p "Press Enter to close..."
