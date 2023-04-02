CREATE OR replace FUNCTION is_article_id_null(value bigint)
    RETURNS boolean
AS
$$
BEGIN
    RETURN (value::bigint IS NULL);
EXCEPTION
    WHEN OTHERS THEN
        RETURN FALSE;
END;
$$
LANGUAGE plpgsql
immutable;

CREATE OR REPLACE FUNCTION initialize_size_table_values()
    RETURNS VOID AS $$
DECLARE
    cust_article_id INTEGER;
    size_value VARCHAR;
    article_row article%rowtype;
BEGIN
    FOR article_row in SELECT * FROM article LOOP
        cust_article_id := article_row.id;
        size_value = (array['44', '46', '48', '50', '52', '54', '56'])[floor(random() * 7 + 1)];
        INSERT INTO size (value, article_id) VALUES (size_value, cust_article_id);
    END LOOP;
END
$$
LANGUAGE plpgsql;

SELECT initialize_size_table_values();

DROP FUNCTION is_article_id_null(value bigint);
DROP FUNCTION initialize_size_table_values();