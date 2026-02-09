# Repository Guidelines

## Project Structure & Module Organization
- `src/main/java/com/lixlim/rest_api`: application source code.
- `src/main/java/com/lixlim/rest_api/memo` and `.../recipe`: feature modules with controller/service/repository/entity/dto layers.
- `src/main/resources`: runtime configuration (`application.yaml`).
- `src/test/java/com/lixlim/rest_api`: unit/integration tests.
- `src/test/resources`: test configuration (`application-test.yaml`).
- `docs`: planning and requirements notes.

## Build, Test, and Development Commands
- `./mvnw clean compile`: compile sources.
- `./mvnw test`: run all tests.
- `./mvnw spring-boot:run`: run the app locally with the default profile.
- `./mvnw -DskipTests package`: build a runnable jar without tests.

## Coding Style & Naming Conventions
- Language: Java (Spring Boot). Indentation uses 4 spaces.
- Packages follow `com.lixlim.rest_api.<feature>` (e.g., `memo`, `recipe`).
- Classes: `PascalCase` (e.g., `MemoServiceImpl`). Methods/fields: `camelCase`.
- DTOs and requests/responses end with `Request`/`Response` (e.g., `MemoCreateRequest`).
- Keep controllers thin; push logic into services and repositories.

## Testing Guidelines
- Frameworks: JUnit 5, Spring Boot Test, MockMvc, Mockito.
- Test class naming: `*Test` and `*IntegrationTest` (see `MemoControllerTest`, `RecipeIntegrationTest`).
- Use `application-test.yaml` for in-memory H2 database configuration.
- Run all tests with `./mvnw test`. Prefer adding unit tests for services and controller tests for API behavior.

## Commit & Pull Request Guidelines
- Commit messages are short and descriptive; some follow `type: subject` (e.g., `chore: ...`), others are plain. Keep them consistent within a PR.
- PRs should include:
  - A concise summary of changes and affected modules.
  - Test evidence (`./mvnw test` output or a note if not run).
  - Any API changes with sample requests/responses.

## Configuration & Data
- Default config is minimal; tests use H2 with PostgreSQL compatibility (`MODE=PostgreSQL`).
- If adding new datasources or profiles, update both `src/main/resources/application.yaml` and `src/test/resources/application-test.yaml`.
