# Memo API Requirements

## Goals
- Provide CRUD APIs for memo management.
- Implement with TDD: controller tests, service tests, and integration tests.
- Use H2 database in tests.

## Base Path
- `/memo`

## API Specifications

### 1) List Memos
- Method: `GET`
- Path: `/memo`
- Query: none (pagination deferred)
- Response: `200 OK`
```json
[
  {"id": 1, "title": "title", "content": "content"}
]
```

### 2) Get Memo by ID
- Method: `GET`
- Path: `/memo/{id}`
- Response: `200 OK`
```json
{"id": 1, "title": "title", "content": "content"}
```

### 3) Create Memo
- Method: `POST`
- Path: `/memo`
- Request Body:
```json
{"title": "title", "content": "content"}
```
- Response: `201 Created`
- Headers: `Location: /memo/{id}`
```json
{"id": 1, "title": "title", "content": "content"}
```

### 4) Update Memo
- Method: `PUT`
- Path: `/memo/{id}`
- Request Body:
```json
{"title": "title", "content": "content"}
```
- Response: `200 OK`
```json
{"id": 1, "title": "title", "content": "content"}
```

### 5) Delete Memo
- Method: `DELETE`
- Path: `/memo/{id}`
- Response: `204 No Content`

## Validation
- `title`: required, non-empty
- `content`: required, non-empty

## Error Handling
- `404 Not Found`: memo not found
- `400 Bad Request`: validation failure

## Testing Requirements
- Controller tests with MockMvc
- Service tests (unit)
- Integration tests with H2
