resource "azurerm_container_group" "prometheus" {
  name                = "${local.env}-prometheus"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  os_type             = "Linux"
  ip_address_type     = "Public"
  dns_name_label      = "deliveryflow-${local.env}-prometheus"

  container {
    name   = "prometheus"
    image  = "prom/prometheus:latest"
    cpu    = "0.5"
    memory = "1.0"

    ports {
      port     = 9090
      protocol = "TCP"
    }

    volume {
      name       = "prometheus-config"
      mount_path = "/etc/prometheus"
      git_repo {
        url       = var.repo_url
        directory = "prometheus"
      }
    }

    volume {
      name       = "prometheus-storage"
      mount_path = "/prometheus"
      empty_dir  = true
    }

    commands = [
      "prometheus",
      "--config.file=/etc/prometheus/prometheus.yml",
      "--storage.tsdb.path=/prometheus"
    ]
  }

  tags = {
    Environment = local.env
    Component   = "observability"
  }
}

resource "azurerm_container_group" "grafana" {
  name                = "${local.env}-grafana"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  os_type             = "Linux"
  ip_address_type     = "Public"
  dns_name_label      = "deliveryflow-${local.env}-grafana"

  container {
    name   = "grafana"
    image  = "grafana/grafana:latest"
    cpu    = "0.5"
    memory = "1.0"

    ports {
      port     = 3000
      protocol = "TCP"
    }

    environment_variables = {
      GF_SECURITY_ADMIN_USER     = "admin"
      GF_SECURITY_ADMIN_PASSWORD = random_password.grafana.result
      GF_PATHS_PROVISIONING      = "/etc/grafana/provisioning"
    }

    volume {
      name       = "grafana-datasources"
      mount_path = "/etc/grafana/provisioning/datasources"
      git_repo {
        url       = var.repo_url
        directory = "grafana/provisioning/datasources"
      }
    }

    volume {
      name       = "grafana-dashboards"
      mount_path = "/etc/grafana/provisioning/dashboards"
      git_repo {
        url       = var.repo_url
        directory = "grafana/provisioning/dashboards"
      }
    }

    volume {
      name       = "grafana-storage"
      mount_path = "/var/lib/grafana"
      empty_dir  = true
    }
  }

  tags = {
    Environment = local.env
    Component   = "observability"
  }
}

resource "random_password" "grafana" {
  length  = 16
  special = true
}

resource "random_password" "rabbitmq" {
  length  = 16
  special = true
}

resource "azurerm_container_group" "rabbitmq" {
  name                = "${local.env}-rabbitmq"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  os_type             = "Linux"
  ip_address_type     = "Public"
  dns_name_label      = "deliveryflow-${local.env}-rabbitmq"

  container {
    name   = "rabbitmq"
    image  = "rabbitmq:3-management"
    cpu    = "1"
    memory = "1.5"

    ports {
      port     = 5672
      protocol = "TCP"
    }

    ports {
      port     = 15672
      protocol = "TCP"
    }

    environment_variables = {
      RABBITMQ_DEFAULT_USER = "admin"
      RABBITMQ_DEFAULT_PASS = random_password.rabbitmq.result
    }
  }

  tags = {
    Environment = local.env
    Component   = "messaging"
  }
}

output "prometheus_url" {
  description = "Prometheus URL"
  value       = "http://${azurerm_container_group.prometheus.fqdn}:9090"
}

output "grafana_url" {
  description = "Grafana URL"
  value       = "http://${azurerm_container_group.grafana.fqdn}:3000"
}

output "grafana_admin_user" {
  description = "Grafana admin username"
  value       = "admin"
}

output "grafana_admin_password" {
  description = "Grafana admin password"
  value       = random_password.grafana.result
  sensitive   = true
}

output "rabbitmq_url" {
  description = "RabbitMQ AMQP URL"
  value       = "amqp://admin:${random_password.rabbitmq.result}@${azurerm_container_group.rabbitmq.fqdn}:5672"
  sensitive   = true
}

output "rabbitmq_admin_user" {
  description = "RabbitMQ admin username"
  value       = "admin"
}

output "rabbitmq_admin_password" {
  description = "RabbitMQ admin password"
  value       = random_password.rabbitmq.result
  sensitive   = true
}

output "rabbitmq_management_url" {
  description = "RabbitMQ Management UI URL"
  value       = "http://${azurerm_container_group.rabbitmq.fqdn}:15672"
}
