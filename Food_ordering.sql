CREATE DATABASE food_ordering;

USE food_ordering;

-- Users Table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(50),
    role VARCHAR(10) -- "admin" or "user"
);

-- Food Menu
CREATE TABLE food_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    price DOUBLE
);

INSERT INTO food_items (name, price) VALUES 
('Margherita Pizza', 199.00),
('Veg Burger', 89.00),
('Paneer Tikka', 149.00),
('Chicken Biryani', 249.00),
('Masala Dosa', 99.00),
('Cold Coffee', 69.00),
('French Fries', 79.00),
('Chocolate Ice Cream', 59.00),
('Cheese Sandwich', 109.00),
('Momos', 99.00);

ALTER TABLE orders ADD COLUMN status VARCHAR(20) DEFAULT 'Pending';



-- Orders
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    food_id INT,
    quantity INT,
    total_price DOUBLE,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(food_id) REFERENCES food_items(id)
);

INSERT INTO users (username, password, role)
VALUES ('admin', 'admin123', 'admin');

