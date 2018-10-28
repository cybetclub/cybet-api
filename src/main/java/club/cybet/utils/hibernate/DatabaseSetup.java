package club.cybet.utils.hibernate;

import club.cybet.utils.Constants;

import javax.xml.bind.ValidationException;

import static org.fusesource.jansi.Ansi.ansi;

public class DatabaseSetup {

    private String connectionUrl;
    private String databaseDriver;
    private String databaseDialect;

    public DatabaseSetup(){
        try {
            init();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void init() throws ValidationException {
        String databaseServer = Constants.getDbServer();
        System.out.println(ansi().a("Database Server: ").bold().a(databaseServer).reset());

        switch (databaseServer){
            case Constants.mySql:
                setupMySql();
                break;
            case Constants.postgreSql:
                setupPostgreSql();
                break;
            case Constants.microsoftSql:
                setupMicrosoftSql();
                break;
            case Constants.oracle:
                setupOracle();
                break;
            default:
                throw new ValidationException("Error: Unable to determine Database Server");

        }

    }

    private void setupMySql(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseDriver = "com.mysql.cj.jdbc.Driver";
            System.out.println(ansi().a("Database Drivers: ").bold().a(databaseDriver).reset());

            try{
                Class.forName("org.hibernate.dialect.MySQL57Dialect");
                databaseDialect = "org.hibernate.dialect.MySQL57Dialect";
                System.out.println(ansi().a("Database Dialect: ").bold().a(databaseDialect).reset());

                connectionUrl = "jdbc:mysql://"+Constants.getDbHost()+":"+Constants.getDbPort()+
                        "/"+Constants.getDbName()+"?useSSL=false&createDatabaseIfNotExist=true";
                System.out.println(ansi().a("Connection URL: ").bold().a(connectionUrl).reset());

            }catch (ClassNotFoundException e){
                System.err.print("Error. MySQL Database dialect dependencies found!");
            }

        } catch( ClassNotFoundException e ) {
            System.err.print("Error. MySQL Driver not found!");
        }
    }

    private void setupPostgreSql(){
        try {
            Class.forName("org.postgresql.Driver");
            databaseDriver = "org.postgresql.Driver";
            System.out.println(ansi().a("Database Drivers: ").bold().a(databaseDriver).reset());

            try{
                Class.forName("org.hibernate.dialect.PostgreSQL82Dialect");
                databaseDialect = "org.hibernate.dialect.PostgreSQL82Dialect";
                System.out.println(ansi().a("Database Dialect: ").bold().a(databaseDialect).reset());

                connectionUrl = "jdbc:postgresql://"+Constants.getDbHost()+":"
                        +Constants.getDbPort()+"/"+Constants.getDbName();
                System.out.println(ansi().a("Connection URL: ").bold().a(connectionUrl).reset());

            }catch (ClassNotFoundException e){
                System.err.print("Error. PostgreSQL Database dialect dependencies found!");
            }

        } catch( ClassNotFoundException e ) {
            System.err.print("Error. PostgreSQL Driver not found!");
        }
    }

    private void setupMicrosoftSql(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            databaseDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            System.out.println("MicrosoftSQL Driver located :)");
            System.out.println(ansi().a("Database Drivers: ").bold().a(databaseDriver).reset());

            try{
                Class.forName("org.hibernate.dialect.SQLServerDialect");
                databaseDialect = "org.hibernate.dialect.SQLServerDialect";
                System.out.println(ansi().a("Database Dialect: ").bold().a(databaseDialect).reset());

                connectionUrl = "jdbc:sqlserver://"+Constants.getDbHost()+":"+Constants.getDbPort()+
                        ";databaseName="+Constants.getDbName();
                System.out.println(ansi().a("Connection URL: ").bold().a(connectionUrl).reset());

            }catch (ClassNotFoundException e){
                System.err.print("Error. MicrosoftSQL Database dialect dependencies found!");
            }

        } catch( ClassNotFoundException e ) {
            System.err.print("Error. MicrosoftSQL Driver not found!");
        }
    }

    private void setupOracle(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            databaseDriver = "com.mysql.jdbc.Driver";
            System.out.println("Oracle Driver located :)");

            try{
                Class.forName("org.hibernate.dialect.MySQL57InnoDBDialect");
                databaseDialect = "org.hibernate.dialect.MySQL57InnoDBDialect";
                System.out.println("Oracle Database dialect dependencies located...");

                connectionUrl = "jdbc:mysql://"+Constants.getDbHost()+":"+Constants.getDbPort()+
                        "/"+Constants.getDbName()+"?useSSL=false&amp;createDatabaseIfNotExist=true";
                System.out.println("Connection URL -> "+connectionUrl);

            }catch (ClassNotFoundException e){
                System.err.print("Error. Oracle Database dialect dependencies found!");
            }

        } catch( ClassNotFoundException e ) {
            System.err.print("Error. Oracle Driver not found!");
        }
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public String getDatabaseDriver() {
        return databaseDriver;
    }

    public String getDatabaseDialect() {
        return databaseDialect;
    }
}
