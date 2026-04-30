variable "environment" {
  description = "Environment name"
  type        = string
}

variable "location" {
  description = "Azure location"
  type        = string
  default     = "East US"
}

variable "repo_url" {
  description = "Repository URL for git provisioning"
  type        = string
  default     = "https://github.com/seu-usuario/deliveryflowapi.git"
}

variable "app_service_sku" {
  description = "App Service SKU per environment"
  type        = map(string)
  default = {
    dev  = "B1"
    test = "B1"
    hml  = "B2"
    prod = "P1V2"
  }
}
