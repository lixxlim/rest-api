# Recipe API Execution Plan

## Execution Plan
1. Create package `com.lixlim.rest_api.recipe` with entity, DTOs, repository, service, controller, and advice.
2. Implement `Recipe` entity extending `BaseEntity`.
3. Add DTOs: `RecipeCreateRequest`, `RecipeUpdateRequest`, `RecipeResponse`.
4. Implement `RecipeService` and `RecipeServiceImpl` with CRUD logic.
5. Implement `RecipeController` with REST endpoints and proper status codes.
6. Add `RecipeNotFoundException` and `RecipeControllerAdvice` for 404 responses.
7. Add tests: unit, controller, and integration.

## Test Plan
- **Service tests**
  - Create, findById, findAll (paged), update, delete
  - Not found exception cases
- **Controller tests**
  - Validate status codes and response bodies for CRUD
  - Validation failure returns 400
- **Integration tests**
  - Full CRUD flow
  - Pagination behavior
  - Validation and 404 handling

## Scope / Assumptions
- Authentication/authorization and ownership checks are out of scope.
- Updates are full replacements via `PUT`.
- Ingredient and step order is meaningful and must be preserved.
