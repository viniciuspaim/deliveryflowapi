terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 3.0"
    }
    random = {
      source  = "hashicorp/random"
      version = "~> 3.0"
    }
  }
}

provider "azurerm" {
  features {}
}

locals {
  env           = var.environment
  rg_name       = "deliveryflow-${local.env}-rg"
  acr_name      = "deliveryflow${local.env}acr"
  app_name      = "deliveryflow-${local.env}"
  db_name       = "deliveryflow-${local.env}-db"
  location      = var.location
}

resource "azurerm_resource_group" "rg" {
  name     = local.rg_name
  location = local.location

  tags = {
    Environment = local.env
  }
}

resource "azurerm_container_registry" "acr" {
  name                = local.acr_name
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  sku                 = "Basic"
  admin_enabled       = true

  tags = {
    Environment = local.env
  }
}

resource "azurerm_app_service_plan" "plan" {
  name                = "${local.app_name}-plan"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  kind                = "Linux"
  reserved            = true

  sku {
    tier = "B"
    size = "B1"
  }

  tags = {
    Environment = local.env
  }
}

resource "azurerm_app_service" "app" {
  name                = local.app_name
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  app_service_plan_id = azurerm_app_service_plan.plan.id

  site_config {
    linux_fx_version = "DOCKER|${azurerm_container_registry.acr.login_server}/deliveryflowapi:latest"
    always_on         = true
  }

  app_settings = {
    DOCKER_REGISTRY_SERVER_URL      = "https://${azurerm_container_registry.acr.login_server}"
    DOCKER_REGISTRY_SERVER_USERNAME = azurerm_container_registry.acr.admin_username
    DOCKER_REGISTRY_SERVER_PASSWORD = azurerm_container_registry.acr.admin_password
    WEBSITES_ENABLE_APP_SERVICE_STORAGE = false

    SPRING_DATASOURCE_URL       = "jdbc:postgresql://${azurerm_postgresql_flexible_server.db.fqdn}:5432/deliveryflowapi"
    SPRING_DATASOURCE_USERNAME  = azurerm_postgresql_flexible_server.db.administrator_login
    SPRING_DATASOURCE_PASSWORD  = azurerm_postgresql_flexible_server.db.administrator_password
    SPRING_JPA_HIBERNATE_DDL_AUTO = "none"
    ENVIRONMENT                 = local.env
  }

  tags = {
    Environment = local.env
  }

  depends_on = [
    azurerm_postgresql_flexible_server.db
  ]
}

resource "azurerm_postgresql_flexible_server" "db" {
  name                = local.db_name
  location            = local.location
  resource_group_name = azurerm_resource_group.rg.name

  administrator_login    = "admin_pg"
  administrator_password = random_password.db_password.result

  sku_name = "B_Standard_B1ms"
  version  = "16"
  zone     = "2"

  storage_mb = 32768
  backup_retention_days = 7

  public_network_access_enabled = true

  tags = {
    Environment = local.env
  }
}

resource "azurerm_postgresql_flexible_server_firewall_rule" "allow_azure" {
  name             = "AllowAzureServices"
  server_id        = azurerm_postgresql_flexible_server.db.id
  start_ip_address = "0.0.0.0"
  end_ip_address   = "0.0.0.0"
}

resource "random_password" "db_password" {
  length  = 16
  special = true
}

output "acr_login_server" {
  description = "ACR login server"
  value       = azurerm_container_registry.acr.login_server
}

output "acr_admin_username" {
  description = "ACR admin username"
  value       = azurerm_container_registry.acr.admin_username
}

output "acr_admin_password" {
  description = "ACR admin password"
  value       = azurerm_container_registry.acr.admin_password
  sensitive   = true
}

output "app_url" {
  description = "App Service URL"
  value       = "https://${azurerm_app_service.app.default_site_hostname}"
}

output "db_host" {
  description = "PostgreSQL server FQDN"
  value       = azurerm_postgresql_flexible_server.db.fqdn
}

output "db_admin_username" {
  description = "PostgreSQL admin username"
  value       = azurerm_postgresql_flexible_server.db.administrator_login
}

output "db_admin_password" {
  description = "PostgreSQL admin password"
  value       = random_password.db_password.result
  sensitive   = true
}
