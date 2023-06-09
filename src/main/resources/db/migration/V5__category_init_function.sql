CREATE OR REPLACE FUNCTION initialize_category_table_values()
    RETURNS VOID AS $$
DECLARE
    cust_article_id INTEGER;
    category_name VARCHAR;
    article_row article%rowtype;
BEGIN
    FOR article_row in SELECT * FROM article LOOP
        cust_article_id := article_row.id;
        category_name = (array['Fitness', 'Run', 'Sports', 'Other'])[floor(random() * 4 + 1)];
        INSERT INTO category (name, article_id) VALUES (category_name, cust_article_id);
    END LOOP;
END
$$
LANGUAGE plpgsql;

SELECT initialize_category_table_values();
DROP FUNCTION initialize_category_table_values();