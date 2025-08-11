# OpenAPI Specification — Viron MVP

This directory contains the **OpenAPI 3.0.3 specification** for the Viron **Minimum Viable Product (MVP)**.  
The specification defines the REST API for **2D spatial simulation**, including environments, grids, locations, entities, and movement.

---

## 📂 Files

- **viron-mvp.openapi.json** — Source of truth for Viron’s MVP API contract.

---

## 🎯 Purpose

The OpenAPI specification exists to:

- Serve as the **single source of truth** for the Viron API.
- Enable **client and server code generation**.
- Provide clear and consistent documentation for developers integrating with Viron.
- Support **automated testing** and API validation.
- Model Viron’s spatial hierarchy explicitly: **Environment → Grid → Locations → Entities**.

The scope is defined in [`MVP.md`](../MVP.md) and must remain aligned with it.

---

## 🛠 Usage

### View Documentation

Preview with Swagger UI:

docker run --rm -p 8081:8080 \
  -e SWAGGER_JSON=/tmp/viron-mvp.openapi.json \
  -v $(pwd)/viron-mvp.openapi.json:/tmp/viron-mvp.openapi.json \
  swaggerapi/swagger-ui

Then open: http://localhost:8081

---

### Generate Clients or Servers

Using OpenAPI Generator:

docker run --rm -v ${PWD}:/local openapitools/openapi-generator-cli generate \
  -i /local/viron-mvp.openapi.json \
  -g java \
  -o /local/gen/java-client

Replace `-g java` with your target language or framework.

---

### Validate the Specification

Using Redocly CLI:

npm install -g @redocly/cli
redocly lint openapi/viron-mvp.openapi.json

---

## 🔄 Maintenance

- **Update** `viron-mvp.openapi.json` whenever an endpoint changes.
- Keep the specification in sync with:
  - The implementation in code
  - The MVP scope in [`MVP.md`](../MVP.md)
- Maintain **DRY** definitions using `$ref` for schemas, parameters, and responses.
- Include **examples** for all request and response bodies.
- Ensure the spatial hierarchy remains consistent:
  - **Environments**: Manage collections of grids.
  - **Grids**: Define dimensions and structure.
  - **Locations**: Addressable coordinates within grids.
  - **Entities**: Objects placed at locations.
  - **Movement & Placement**: Rules for changing locations.

---

## 📄 License

The OpenAPI specification is licensed under the same terms as Viron (MIT License).  
See the [LICENSE](../LICENSE) file for full terms.
