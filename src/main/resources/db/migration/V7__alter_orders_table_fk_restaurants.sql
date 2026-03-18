ALTER TABLE tb_orders ADD COLUMN restaurant_id BIGINT;
ALTER TABLE tb_orders ADD CONSTRAINT fk_order_restaurant FOREIGN KEY (restaurant_id) REFERENCES tb_restaurants(restaurant_id);