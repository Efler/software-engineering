CREATE OR REPLACE FUNCTION log_change() RETURNS trigger AS
$$
BEGIN
    IF TG_OP = 'DELETE' THEN
        INSERT INTO change_log(table_name, record_id, old_data, operation)
        VALUES (TG_TABLE_NAME, OLD.id, row_to_json(OLD), 'DELETE');
        RETURN OLD;
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO change_log(table_name, record_id, old_data, new_data, operation)
        VALUES (TG_TABLE_NAME, NEW.id, row_to_json(OLD), row_to_json(NEW), 'UPDATE');
        RETURN NEW;
    ELSE
        INSERT INTO change_log(table_name, record_id, new_data, operation)
        VALUES (TG_TABLE_NAME, NEW.id, row_to_json(NEW), TG_OP);
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_log_change
    AFTER INSERT OR UPDATE
    ON my_table
    FOR EACH ROW
EXECUTE FUNCTION log_change();
CREATE TRIGGER trg_log_change_delete
    AFTER DELETE
    ON my_table
    FOR EACH ROW
EXECUTE FUNCTION log_change();
