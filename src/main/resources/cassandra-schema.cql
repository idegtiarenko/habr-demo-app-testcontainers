CREATE KEYSPACE IF NOT EXISTS test WITH replication = {
    'class': 'SimpleStrategy',
    'replication_factor': 1
};

CREATE TABLE IF NOT EXISTS test.events(
    user_id uuid,
    id uuid,
    time bigint,
    type varchar,
    PRIMARY KEY ((user_id), id)
);