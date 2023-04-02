CREATE OR replace FUNCTION is_price_null(value FLOAT8)
    RETURNS boolean
AS
$$
BEGIN
    RETURN (value::float8 IS NULL);
EXCEPTION
    WHEN OTHERS THEN
        RETURN FALSE;
END;
$$
LANGUAGE plpgsql
immutable;

CREATE OR replace FUNCTION is_stock_null(value int)
    RETURNS boolean
AS
$$
BEGIN
    RETURN (value::int IS NULL);
EXCEPTION
    WHEN OTHERS THEN
        RETURN FALSE;
END;
$$
LANGUAGE plpgsql
immutable;

CREATE OR REPLACE FUNCTION initialize_article_table_values()
    RETURNS VOID AS $$
DECLARE
    article_price INTEGER;
    price_low INTEGER = 2000;
    price_high INTEGER = 10000;
    is_price_null BOOL;

    article_stock INTEGER;
    stock_low INTEGER = 10;
    stock_high INTEGER = 50;
    is_stock_null BOOL;

    row article%rowtype;
BEGIN
    FOR row in SELECT * FROM article LOOP
    is_price_null = is_price_null(row.price);
    is_stock_null = is_stock_null(row.stock);
    article_price := random() * (price_high - price_low + 1) + price_low;
    article_stock := floor(random() * (stock_high - stock_low + 1) + stock_low)::int;

    CASE
        WHEN is_price_null AND is_stock_null THEN UPDATE article
            SET price = article_price, stock = article_stock WHERE id=row.id;
        ELSE
        END CASE;
    END LOOP;
END
$$
LANGUAGE plpgsql;

SELECT initialize_article_table_values();

DROP FUNCTION is_price_null(value FLOAT8);
DROP FUNCTION is_stock_null(value int);
DROP FUNCTION initialize_article_table_values();