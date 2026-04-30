# Guest Access - Recruiter Credentials

## Default Guest Account

**Username:** `guest`  
**Password:** `visitor123`

## Access Level

Guest users have **READ-ONLY** access to:
- Product Catalog (`GET /products`)
- Restaurant Information (`GET /restaurants`)
- API Documentation (Swagger UI)
- Monitoring Dashboards (Metrics endpoint)
- Health Check endpoint

## Restricted (Admin Only)

Guest users **CANNOT** access:
- Customer data (`/customers`) - Contains PII
- Order information (`/orders`) - Sensitive business data
- POST/PUT/DELETE operations on any endpoint
- System configuration

## Usage

### Via cURL
```bash
curl -u guest:visitor123 http://localhost:8080/products
```

### Via Browser
1. Go to http://localhost:8080/swagger-ui.html
2. Click "Authorize" button (top-right)
3. Enter credentials:
   - Username: `guest`
   - Password: `visitor123`

### In Postman
1. Create new request
2. Go to "Authorization" tab
3. Select "Basic Auth"
4. Username: `guest`
5. Password: `visitor123`

## Testing Access

### ✓ Allowed (Guest)
```bash
curl -u guest:visitor123 http://localhost:8080/products
curl -u guest:visitor123 http://localhost:8080/restaurants
curl -u guest:visitor123 http://localhost:8080/actuator/prometheus
```

### ✗ Forbidden (Guest)
```bash
curl -u guest:visitor123 http://localhost:8080/customers        # 403
curl -u guest:visitor123 http://localhost:8080/orders           # 403
curl -u guest:visitor123 -X POST http://localhost:8080/products # 403
```

## For Production

1. **Change default password immediately**
   - SQL: `UPDATE users SET password = <NEW_BCRYPT_HASH> WHERE username = 'guest'`
   - Use bcrypt generator to hash new password

2. **Consider rotating credentials regularly**

3. **Enable HTTPS** for all endpoints

4. **Monitor guest access logs**

## Creating Additional Accounts

Add more users by inserting into `users` table:

```sql
INSERT INTO users (username, password, role, enabled) VALUES
('recruiter2', '<BCRYPT_HASH>', 'ROLE_GUEST', true);
```

To generate bcrypt hash:
```bash
# Using Spring Boot CLI
spring encodepassword yourpassword

# Or use online bcrypt generator
```

## Endpoint Summary

| Endpoint | Method | Guest | Admin |
|----------|--------|-------|-------|
| /products | GET | ✓ | ✓ |
| /products | POST/PUT/DELETE | ✗ | ✓ |
| /restaurants | GET | ✓ | ✓ |
| /restaurants | POST/PUT/DELETE | ✗ | ✓ |
| /customers | ALL | ✗ | ✓ |
| /orders | ALL | ✗ | ✓ |
| /swagger-ui/** | GET | ✓ | ✓ |
| /actuator/health | GET | ✓ | ✓ |
| /actuator/prometheus | GET | ✓ | ✓ |

## Security Notes

- Credentials are stored with bcrypt hashing
- Basic Auth requires HTTPS in production
- Consider implementing JWT tokens for API clients
- Guest accounts should not have access to sensitive data
- Regularly audit guest account activity
