CREATE OR REPLACE FUNCTION initialize_brand_table_values()
    RETURNS VOID AS $$
DECLARE
    cust_brand_name VARCHAR;
    cust_article_id INTEGER;
    article_row article%rowtype;
BEGIN
    FOR article_row in SELECT * FROM article LOOP
        cust_article_id := article_row.id;
        CASE
                WHEN cust_article_id BETWEEN 1 AND 75 THEN cust_brand_name := 'Adidas';
                WHEN cust_article_id BETWEEN 76 AND 135 THEN cust_brand_name := 'Nike';
                WHEN cust_article_id BETWEEN 136 AND 193 THEN cust_brand_name := 'Puma';
                WHEN cust_article_id BETWEEN 194 AND 258 THEN cust_brand_name := 'Reebok';
            ELSE
        END CASE;
        INSERT INTO brand (name, article_id) VALUES (cust_brand_name, cust_article_id);
    END LOOP;
END
$$
LANGUAGE plpgsql;

SELECT initialize_brand_table_values();
DROP FUNCTION initialize_brand_table_values();