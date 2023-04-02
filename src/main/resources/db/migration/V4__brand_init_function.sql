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

CREATE OR REPLACE FUNCTION initialize_brand_table_values()
    RETURNS VOID AS $$
DECLARE
    cust_article_id INTEGER;
    is_article_id_null BOOL;

    row brand%rowtype;
BEGIN
    FOR row in SELECT * FROM brand LOOP
        is_article_id_null = is_article_id_null(row.article_id);
        cust_article_id := row.id;

    CASE
        WHEN is_article_id_null THEN UPDATE brand SET article_id = cust_article_id WHERE id=row.id;
        ELSE
        END CASE;
    END LOOP;
END
$$
LANGUAGE plpgsql;

SELECT initialize_brand_table_values();

DROP FUNCTION is_article_id_null(value bigint);
DROP FUNCTION initialize_brand_table_values();