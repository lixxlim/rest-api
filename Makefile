MVNW=./mvnw

.PHONY: help build test run clean package

help:
	@echo "make build    - Compile sources"
	@echo "make test     - Run tests"
	@echo "make run      - Run Spring Boot app"
	@echo "make clean    - Clean build outputs"
	@echo "make package  - Build jar (skip tests)"

build:
	$(MVNW) clean compile

test:
	$(MVNW) test

run:
	$(MVNW) spring-boot:run

clean:
	$(MVNW) clean

package:
	$(MVNW) -DskipTests package
