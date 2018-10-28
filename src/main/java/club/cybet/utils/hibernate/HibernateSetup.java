package club.cybet.utils.hibernate;

import club.cybet.domain.db.orm.*;
import club.cybet.utils.Constants;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;

public class HibernateSetup {

    //This block maintains hibernate for main orm
    public static DatabaseSetup databaseSetup = null;
    public static SessionFactory sessionFactory = null;


    public HibernateSetup(){

        databaseSetup = new DatabaseSetup();
        sessionFactory = buildSessionFactory();
        Logger.getLogger("org.hibernate").setLevel(Level.ERROR);
    }

    public static Session getMainSession() {
        return getSessionFactory().openSession();
    }

    public static Session getSession() {
        return getSessionFactory().getCurrentSession();
    }

    private static SessionFactory getSessionFactory() {
        return sessionFactory;
    }


    private static SessionFactory buildSessionFactory(){
        try{
            Configuration configuration = new Configuration()
                    .addAnnotatedClass(AccessToken.class)
                    .addAnnotatedClass(Attachment.class)
                    .addAnnotatedClass(AttachmentType.class)
                    .addAnnotatedClass(EmploymentIndustry.class)
                    .addAnnotatedClass(EmploymentStatus.class)
                    .addAnnotatedClass(IdentificationType.class)
                    .addAnnotatedClass(JobTitle.class)
                    .addAnnotatedClass(SelfAnnualIncomeBracket.class)
                    .addAnnotatedClass(SourceOfFunds.class)
                    .addAnnotatedClass(SystemVariable.class)
                    .addAnnotatedClass(UserAccount.class)
                    .addAnnotatedClass(UserAccountType.class)
                    .setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory")
                    .setProperty("hibernate.current_session_context_class", "thread")
                    .setProperty("hibernate.connection.url",databaseSetup.getConnectionUrl())
                    .setProperty("hibernate.connection.driver_class",databaseSetup.getDatabaseDriver())
                    .setProperty("hibernate.dialect", databaseSetup.getDatabaseDialect())
                    .setProperty("hibernate.connection.username", Constants.getDbUsername())
                    .setProperty("hibernate.connection.password", Constants.getDbPassword())
                    .setProperty("hibernate.hbm2ddl.auto", Constants.getHibHbm2ddlAuto(true))
                    .setProperty("hibernate.temp.use_jdbc_metadata_defaults",
                            Constants.getHibUseJdbcMetadataDefaults())
                    .setProperty("hibernate.default_schema", Constants.getHibDefaultSchema())
                    .setProperty("hibernate.default_catalog", Constants.getHibDefaultCatalog())
                    .setProperty("hibernate.show_sql", Constants.getHibShowSql())
                    .setProperty("hibernate.format_sql", Constants.getHibFormatSql())
                    .setProperty("hibernate.cache.use_second_level_cache", Constants.getHibUseSecondLevelCache())
                    .setProperty("hibernate.cache.use_query_cache", Constants.getHibUseQueryCache())
                    .setProperty("hibernate.generate_statistics", Constants.getHibGenerateStatistics())
                    .setProperty("hibernate.c3p0.acquire_increment", "3")
                    .setProperty("hibernate.c3p0.contextClassLoaderSource", "library")
                    .setProperty("hibernate.c3p0.min_size", Constants.getHibC3p0MinSize())
                    .setProperty("hibernate.c3p0.max_size", Constants.getHibC3p0MaxSize())
                    .setProperty("hibernate.c3p0.timeout", Constants.getHibC3p0Timeout())
                    .setProperty("hibernate.c3p0.max_statements", Constants.getHibC3p0MaxStatements())
                    .setProperty("hibernate.c3p0.idle_test_period", Constants.getHibC3p0IdleTestPeriod())
                    .setProperty("connection.pool_size", Constants.getHibConnPoolSize());

            return configuration.buildSessionFactory();
        }catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

}
