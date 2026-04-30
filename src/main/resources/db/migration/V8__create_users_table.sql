CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL CHECK (role IN ('ROLE_GUEST', 'ROLE_ADMIN')),
    enabled BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_username ON users(username);

-- Insert guest user for recruiters
-- Default credentials: guest / visitor123
-- IMPORTANT: Change password after first login using /api/auth/change-password
-- Bcrypt hash of 'visitor123'
INSERT INTO users (username, password, role, enabled) VALUES
('guest', '$2a$10$SlAPrAeoaVnJdg9Ri2gVo.Vfv02PiMHuLNu1XwMzHPB.1nEJNPDXC', 'ROLE_GUEST', true);
