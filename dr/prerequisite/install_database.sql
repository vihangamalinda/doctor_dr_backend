IF DB_ID('doctor_dr') IS NULL
BEGIN
    CREATE DATABASE doctor_dr;
END
GO

IF NOT EXISTS (
    SELECT *
    FROM sys.server_principals
    WHERE name = 'doctor_user'
)
BEGIN
    CREATE LOGIN doctor_user
    WITH PASSWORD = 'SuserP@ssw0rDdoctor123';
END
GO

USE doctor_dr;
GO

IF NOT EXISTS (
    SELECT *
    FROM sys.database_principals
    WHERE name = 'doctor_user'
)
BEGIN
    CREATE USER doctor_user
    FOR LOGIN doctor_user;
END
GO

ALTER ROLE db_owner
ADD MEMBER doctor_user;
GO