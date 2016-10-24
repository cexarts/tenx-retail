CREATE TABLE store (
    store_id BIGINT      NOT NULL AUTO_INCREMENT
    COMMENT 'The id of the store',
    name     VARCHAR(50) NOT NULL
    COMMENT 'The name of the store',
    PRIMARY KEY (store_id)
);

CREATE TABLE product (
    product_id  BIGINT         NOT NULL AUTO_INCREMENT
    COMMENT 'The id of the product',
    store_id    BIGINT         NOT NULL,
    name        VARCHAR(50)    NOT NULL
    COMMENT 'The name of the product',
    description TEXT           NOT NULL
    COMMENT 'Description of the product',
    sku         VARCHAR(10)    NOT NULL
    COMMENT 'Product SKU code',
    price       DECIMAL(20, 2) NOT NULL
    COMMENT 'Price of the product',
    FOREIGN KEY (store_id) REFERENCES store (store_id),
    PRIMARY KEY (product_id)
);

CREATE INDEX product_fk_store_id
    ON product (product_id);

CREATE TABLE stock (
    product_id  BIGINT NOT NULL
    COMMENT 'The product Id',
    store_id  BIGINT NOT NULL
    COMMENT 'The Store Id',
    count BIGINT NOT NULL
    COMMENT 'Total count in stock',
    FOREIGN KEY (product_id) REFERENCES product (product_id),
    FOREIGN KEY (store_id) REFERENCES store (store_id),
    PRIMARY KEY (product_id)
);

CREATE INDEX stock_fk_product_id
    ON stock (product_id);

CREATE TABLE purchase_order (

    order_id   BIGINT      NOT NULL AUTO_INCREMENT
    COMMENT 'The order Id',
    store_id   BIGINT      NOT NULL
    COMMENT 'The store assosiated with this order',
    first_name VARCHAR(50) NOT NULL
    COMMENT 'Purchaser first name',
    last_name  VARCHAR(50) NOT NULL
    COMMENT 'Purchaser last name',
    email      VARCHAR(50) NOT NULL
    COMMENT 'Purchaser email',
    phone      VARCHAR(20) NOT NULL
    COMMENT 'Purchaser phone number',
    order_date DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP
    COMMENT 'order date/time',
    status     INT(3)  NOT NULL
    COMMENT 'Order status',
    FOREIGN KEY (store_id) REFERENCES store (store_id),
    PRIMARY KEY (order_id)
);

CREATE INDEX purchase_order_fk_store_id
    ON purchase_order (store_id);

CREATE TABLE purchase_order_item (

    order_item_id BIGINT NOT NULL AUTO_INCREMENT,
    order_id      BIGINT NOT NULL
    COMMENT 'The order id',
    product_id    BIGINT NOT NULL
    COMMENT 'The product id',
    count BIGINT NOT NULL
    COMMENT 'Product count',
    FOREIGN KEY (order_id) REFERENCES purchase_order (order_id),
    FOREIGN KEY (product_id) REFERENCES product (product_id),
    PRIMARY KEY (order_item_id),
    UNIQUE (order_id, product_id)
);

CREATE INDEX purchase_order_item_fk_order_id
    ON purchase_order_item (order_id);
CREATE INDEX purchase_order_item_fk_product_id
    ON purchase_order_item (product_id);
