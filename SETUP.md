# DeliveryFlow API - Setup Guide

## Local Development

### Prerequisites
- Docker & Docker Compose
- Java 17
- Gradle (via gradlew)

### Quick Start

1. **Setup environment**
```bash
cp .env.example .env
```

2. **Start stack**
```bash
docker-compose up -d
```

3. **Verify services**
```bash
docker-compose ps
```

### Access Points
- **App**: http://localhost:8080
- **API Docs**: http://localhost:8080/swagger
- **Prometheus**: http://localhost:9090
- **Grafana**: http://localhost:3000 (admin/admin)
- **PostgreSQL**: localhost:5432
- **RabbitMQ**: http://localhost:15672 (guest/guest)

### View Metrics
1. Prometheus targets: http://localhost:9090/targets
2. Grafana:
   - Login: admin/admin
   - Datasource: Prometheus (auto-configured)
   - Dashboard: DeliveryFlow - Spring Boot

---

## Azure Deployment

### Prerequisites
- Azure subscription
- Azure CLI
- Terraform v1.5+
- GitHub account

### Setup

1. **Create Azure Service Principal**
```bash
az ad sp create-for-rbac \
  --name "github-actions" \
  --role Contributor \
  --scopes /subscriptions/<SUBSCRIPTION_ID> \
  --json
```

2. **Add GitHub Secrets**
```
AZURE_CREDENTIALS=<output from step 1>
ACR_LOGIN_SERVER=deliveryflow<env>acr.azurecr.io
ACR_USERNAME=<acr-username>
ACR_PASSWORD=<acr-password>
GRAFANA_PASSWORD=<secure-password>
```

3. **Deploy infrastructure**
```bash
cd terraform
terraform init
terraform apply -var-file="dev.tfvars"
```

### Environments

| Branch | Environment | Resource Group |
|--------|-------------|------------------|
| develop | dev | deliveryflow-dev-rg |
| test | test | deliveryflow-test-rg |
| hml | hml | deliveryflow-hml-rg |
| master | prod | deliveryflow-prod-rg |

### CI/CD Pipeline

On push to any environment branch:
1. Run tests
2. Build & push Docker image to ACR
3. Deploy infrastructure via Terraform
4. Update App Service container image
5. Restart App Service

### Observability

- **Prometheus**: Scrapes metrics from `/actuator/prometheus`
- **Grafana**: Pre-configured dashboards for Spring Boot monitoring
- Metrics tracked:
  - HTTP request rate & response time
  - JVM memory usage
  - Database connections
  - RabbitMQ messages

### Monitoring

#### Local
```bash
docker-compose logs -f app
docker-compose logs -f prometheus
```

#### Azure
```bash
az webapp log tail --name deliveryflow-prod --resource-group deliveryflow-prod-rg
```

---

## Database Migrations

Flyway migrations located in `src/main/resources/db/migration/`

Applied automatically on app startup.

---

## API Documentation

Swagger UI available at: `/swagger`

Health check: `/actuator/health`

Metrics: `/actuator/prometheus`

---

## Troubleshooting

### Docker compose issues
```bash
# Clean up
docker-compose down -v

# Rebuild
docker-compose build --no-cache
docker-compose up -d
```

### Terraform issues
```bash
# Validate
terraform validate

# Re-init
rm -rf .terraform
terraform init
```

### App not starting
Check logs:
```bash
docker-compose logs app
```

---

## Multi-Environment Strategy

- **dev**: Fast iteration, minimal resources
- **test**: Testing before staging
- **hml**: Staging/homolog - pre-production
- **prod**: Production

Each environment isolated in separate resource groups with dedicated databases & registries.
