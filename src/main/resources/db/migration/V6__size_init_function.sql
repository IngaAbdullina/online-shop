CREATE OR REPLACE FUNCTION initialize_size_table_values()
    RETURNS VOID AS $$
DECLARE
    cust_article_id INTEGER;
    size_value_one VARCHAR;
    size_value_two VARCHAR;
    size_value_three VARCHAR;
    article_row article%rowtype;
BEGIN
    FOR article_row in SELECT * FROM article LOOP
        cust_article_id := article_row.id;
        size_value_one = (array['44', '46', '48', '50', '52', '54', '56'])[floor(random() * 7 + 1)];
        size_value_two = (array['44', '46', '48', '50', '52', '54', '56'])[floor(random() * 7 + 1)];
        size_value_three = (array['44', '46', '48', '50', '52', '54', '56'])[floor(random() * 7 + 1)];
        INSERT INTO size (value, article_id) VALUES (size_value_one, cust_article_id),
                                                    (size_value_two, cust_article_id),
                                                    (size_value_three, cust_article_id);
    END LOOP;
END
$$
LANGUAGE plpgsql;

SELECT initialize_size_table_values();
DROP FUNCTION initialize_size_table_values();