CREATE TABLE IF NOT EXISTS tasks (id identity primary key, content varchar(50), isDone int NOT NULL CHECK(isdone IN (0,1)));