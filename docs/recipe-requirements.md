# Recipe API Requirements

## Overview
The Recipe API provides CRUD operations for managing recipes. It supports creating, retrieving, updating, and deleting recipes, along with a paginated listing endpoint.

## Requirements
### Functional
- Create a recipe.
- Retrieve a recipe by ID.
- Retrieve recipes with pagination.
- Update a recipe by ID (full update).
- Delete a recipe by ID.

### Non-Functional
- Follow REST conventions for status codes and responses.
- Validate inputs and return `400 Bad Request` for invalid data.
- Return `404 Not Found` when a recipe does not exist.

## Data Model
### Entity: Recipe
- `title` (String, required)
- `description` (String, required, long text)
- `ingredients` (List<String>, required, ordered)
- `steps` (List<String>, required, ordered)
- `servings` (int, required, >= 1)
- `cookTimeMinutes` (int, required, >= 1)
- `tags` (List<String>, optional)

### Storage Notes
- `ingredients`, `steps`, and `tags` are stored via JPA `@ElementCollection`.
- Order is preserved for `ingredients` and `steps` using `@OrderColumn`.

## API Design
### Create Recipe
- `POST /recipes`
- Request: `RecipeCreateRequest`
- Response: `201 Created`, `Location: /recipes/{id}` and body `RecipeResponse`

### Get Recipe
- `GET /recipes/{id}`
- Response: `200 OK`, body `RecipeResponse`

### List Recipes
- `GET /recipes?page=&size=&sort=`
- Response: `200 OK`, body `Page<RecipeResponse>`

### Update Recipe
- `PUT /recipes/{id}`
- Request: `RecipeUpdateRequest`
- Response: `200 OK`, body `RecipeResponse`

### Delete Recipe
- `DELETE /recipes/{id}`
- Response: `204 No Content`
