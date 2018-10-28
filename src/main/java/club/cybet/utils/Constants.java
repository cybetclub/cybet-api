package club.cybet.utils;

import club.cybet.domain.beans.CommandLine;
import club.cybet.utils.security.Encryption;
import club.cybet.utils.security.HashUtils;
import club.cybet.utils.xml.XmlUtils;
import org.fusesource.jansi.Ansi;

import java.nio.file.attribute.PosixFilePermission;
import java.util.*;

import static org.fusesource.jansi.Ansi.ansi;

public class Constants {

    /*=======================================================================================*/
    /*                              CONFIG FILE VARS & DEFAULTS                              */
    /*=======================================================================================*/
    public static final String mySql = "MySQL";
    public static final String microsoftSql = "MicrosoftSQL";
    public static final String oracle = "Oracle";
    public static final String postgreSql = "PostgreSQL";

    public static final String applicationJson = "application/json";
    public static final String applicationXml = "application/xml";
    public static final String textXml = "text/xml";

    public static final String REQ_ID = "Nx2YznsI18fpJvrRJ2yA";
    public static final String REQ_USER_TYPE = "y7bO48HmzhGfoTgLNKSv";
    public static final String REQ_PERMS = "BhUMtApG4A6ExIlCF7j8";
    public static final String MARSHALL_ERROR = "ustozu8wqhJ1wEJ";

    public static final String SHARE_CAPITAL = "Initial_Share_Capital$A7D";
    public static final String USER_ACCOUNT_COUNTER = "User_Account_Counter$GNA";
    public static final String ONE_OFF_ACCOUNT_COUNTER = "One_Off_Account_Counter$D3R";

    public static final String REG_ACCOUNT_PREFIX = "KREG";

    public static final String CONTRACT_ADDR_CYBET_TOKEN = "0xA77861800A3f16E9Ab73B1041F23776f709cc317";
    public static final String CONTRACT_ADDR_CROWDSALE = "0x0cb9E6721aF7DE4C33c0BA6CACF4F1823BE8D21f";

    /*=======================================================================================*/
    /*                              DATE TIME                                                */
    /*=======================================================================================*/

    //Date Time Formats
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    // Just Now Desc.
    public static final String JUST_NOW = "Just now";

    /*=======================================================================================*/
    /*                              REPORTS                                                  */
    /*=======================================================================================*/
    public static final String SUPPORTED_RELATIONS = "eq,neq,lt,lte,gt,gte,like,contains,sw,ew,in,!eq,!neq,!lt,!lte,!gt,!gte,!like,!contains,!sw,!ew,!in";
    public static final String RELATION_EQ = "eq";
    public static final String RELATION_LT = "lt";
    public static final String RELATION_LTE = "lte";
    public static final String RELATION_GT = "gt";
    public static final String RELATION_GTE = "gte";
    public static final String RELATION_LIKE = "like";
    public static final String RELATION_ILIKE = "ilike";
    public static final String RELATION_CONTAINS = "contains";
    public static final String RELATION_ICONTAINS = "icontains";
    public static final String RELATION_SW = "sw";
    public static final String RELATION_ISW = "isw";
    public static final String RELATION_EW = "ew";
    public static final String RELATION_IEW = "iew";
    public static final String RELATION_IN = "in";
    public static final String RELATION_NEQ = "!eq";
    public static final String RELATION_NLT = "!lt";
    public static final String RELATION_NLTE = "!lte";
    public static final String RELATION_NGT = "!gt";
    public static final String RELATION_NGTE = "!gte";
    public static final String RELATION_NLIKE = "!like";
    public static final String RELATION_NILIKE = "!ilike";
    public static final String RELATION_NCONTAINS = "!contains";
    public static final String RELATION_NICONTAINS = "!icontains";
    public static final String RELATION_NSW = "!sw";
    public static final String RELATION_NISW = "!isw";
    public static final String RELATION_NEW = "!ew";
    public static final String RELATION_NIEW = "!iew";
    public static final String RELATION_NIN = "!in";

    public static final String SUPPORTED_FIND_BY_STRATEGIES = "and,or";
    public static final String SFBS = " and , or ";

    /*=======================================================================================*/
    /*                              MISCELLANEOUS                                            */
    /*=======================================================================================*/
    public static final String ENCRYPTED = "ENCRYPTED";
    public static final String CLEAR_TEXT = "CLEAR_TEXT";
    public static final String SKY_DELIMITER = "&s*k@y%#+=&";
    public static final String SKY_SPLITTER = "&s\\*k@y%#\\+=&";

    public static final String LOCAL_CONF_FILENAME = CommandLine.getArg("-c") == null ? "cybet.conf" : CommandLine.getArg("-c");

    public static final String XML_PATH_TO_TOKEN_EXPIRY = "/API/CONTEXT/API/AUTH/ACCESS_TOKEN_EXPIRY";

    public static final String XML_PATH_TO_CONTEXT_PATH = "/API/CONTEXT/PATH";
    public static final String XML_PATH_TO_CONTEXT_CLIENT_ROOT_DIR = "/API/CONTEXT/CLIENT/ROOT_DIR";
    public static final String XML_PATH_TO_CONTEXT_HOST = "/API/CONTEXT/HOST";
    public static final String XML_PATH_TO_CONTEXT_SERVER = "/API/CONTEXT/SERVER";
    public static final String XML_PATH_TO_CONTEXT_PORT = "/API/CONTEXT/PORT";
    public static final String XML_PATH_TO_CONTEXT_PROTOCOL = "/API/CONTEXT/PROTOCOL";
    public static final String XML_PATH_TO_CONTEXT_ENABLE_BASIC_AUTH = "/API/CONTEXT/ENABLE_BASIC_AUTH";

    public static final String XML_PATH_TO_CONTEXT_TIME_ZONE = "/API/CONTEXT/TIME_ZONE";

    public static final String XML_PATH_TO_UNDERTOW_ENABLE_ACCESS_LOG = "/API/CONTEXT/UNDERTOW/ENABLE_ACCESS_LOG";
    public static final String XML_PATH_TO_UNDERTOW_ACCESS_LOG_DIR = "/API/CONTEXT/UNDERTOW/ACCESS_LOG_DIR";
    public static final String XML_PATH_TO_UNDERTOW_ACCESS_LOG_PATTERN = "/API/CONTEXT/UNDERTOW/ACCESS_LOG_PATTERN";
    public static final String XML_PATH_TO_UNDERTOW_ENABLE_COMPRESSION = "/API/CONTEXT/UNDERTOW/ENABLE_COMPRESSION";
    public static final String XML_PATH_TO_UNDERTOW_COMPRESSION_MIN_RESPONSE_SIZE = "/API/CONTEXT/UNDERTOW/COMPRESSION_MIN_RESPONSE_SIZE";

    private static final String XML_PATH_TO_SERVER= "/API/DB/SERVER";
    private static final String XML_PATH_TO_HOST = "/API/DB/HOST";
    private static final String XML_PATH_TO_PORT = "/API/DB/PORT";
    private static final String XML_PATH_TO_DATABASE_NAME = "/API/DB/NAME";
    private static final String XML_PATH_TO_DB_PASSWORD_TYPE = "/API/DB/PASSWORD/@TYPE";
    private static final String XML_PATH_TO_DB_USERNAME = "/API/DB/USERNAME";
    private static final String XML_PATH_TO_DB_PASSWORD = "/API/DB/PASSWORD";
    private static final String XML_PATH_TO_DB_INITIALIZE = "/API/DB/INITIALIZE";

    private static final String XML_PATH_TO_HIBERNATE_HBM2DDL_AUTO = "/API/DB/HIBERNATE/HBM2DDL_AUTO";
    private static final String XML_PATH_TO_HIBERNATE_DEFAULT_SCHEMA = "/API/DB/HIBERNATE/DEFAULT_SCHEMA";
    private static final String XML_PATH_TO_HIBERNATE_DEFAULT_CATALOG = "/API/DB/HIBERNATE/DEFAULT_CATALOG";
    private static final String XML_PATH_TO_HIBERNATE_USE_JDBC_METADATA_DEFAULTS = "/API/DB/HIBERNATE/USE_JDBC_METADATA_DEFAULTS";
    private static final String XML_PATH_TO_HIBERNATE_CONNECTION_POOL_SIZE = "/API/DB/HIBERNATE/CONNECTION_POOL_SIZE";
    private static final String XML_PATH_TO_HIBERNATE_SHOW_SQL = "/API/DB/HIBERNATE/SHOW_SQL";
    private static final String XML_PATH_TO_HIBERNATE_FORMAT_SQL = "/API/DB/HIBERNATE/FORMAT_SQL";
    private static final String XML_PATH_TO_HIBERNATE_USE_SECOND_LEVEL_CACHE = "/API/DB/HIBERNATE/USE_SECOND_LEVEL_CACHE";
    private static final String XML_PATH_TO_HIBERNATE_USE_QUERY_CACHE = "/API/DB/HIBERNATE/USE_QUERY_CACHE";
    private static final String XML_PATH_TO_HIBERNATE_GENERATE_STATISTICS = "/API/DB/HIBERNATE/GENERATE_STATISTICS";
    private static final String XML_PATH_TO_HIBERNATE_C3P0_MIN_SIZE = "/API/DB/HIBERNATE/C3P0/MIN_SIZE";
    private static final String XML_PATH_TO_HIBERNATE_C3P0_MAX_SIZE = "/API/DB/HIBERNATE/C3P0/MAX_SIZE";
    private static final String XML_PATH_TO_HIBERNATE_C3P0_TIMEOUT = "/API/DB/HIBERNATE/C3P0/TIMEOUT";
    private static final String XML_PATH_TO_HIBERNATE_C3P0_MAX_STATEMENTS = "/API/DB/HIBERNATE/C3P0/MAX_STATEMENTS";
    private static final String XML_PATH_TO_HIBERNATE_C3P0_IDLE_TEST_PERIOD = "/API/DB/HIBERNATE/C3P0/IDLE_TEST_PERIOD";

    private static final String XML_PATH_TO_TEMP_FILE_PATH = "/API/PATHS/TEMP_FOLDER";
    private static final String XML_PATH_TO_PUBLIC_ASSETS_FOLDER_PATH = "/API/PATHS/PUBLIC_ASSETS/FOLDER";
    private static final String XML_PATH_TO_PUBLIC_ASSETS_SERVER = "/API/PATHS/PUBLIC_ASSETS/SERVER";
    private static final String XML_PATH_TO_SECURE_FOLDER_PATH = "/API/PATHS/SECURE_FOLDER";

    private static final String XML_PATH_TO_MAILER_SMTP_HOST = "/API/MAILER/SMTP/HOST";
    private static final String XML_PATH_TO_MAILER_SMTP_ENABLE_TLS = "/API/MAILER/SMTP/ENABLE_TLS";
    private static final String XML_PATH_TO_MAILER_SMTP_PORT = "/API/MAILER/SMTP/PORT";
    private static final String XML_PATH_TO_MAILER_FROM_EMAIL_ADDRESS = "/API/MAILER/FROM_EMAIL_ADDRESS";
    private static final String XML_PATH_TO_MAILER_PASSWORD = "/API/MAILER/PASSWORD";
    private static final String XML_PATH_TO_MAILER_PASSWORD_TYPE = "/API/MAILER/PASSWORD/@TYPE";
    private static final String XML_PATH_TO_MAILER_PASS_RESET_HOST = "/API/MAILER/PORTAL_PASSWORD_RESET_HOST";

    private static final String XML_PATH_TO_INFURA_API_LINK = "/API/CONTEXT/ETHEREUM/INFURA_API_LINK";
    private static final String XML_PATH_TO_ETH_WALLET_PASSWORD = "/API/CONTEXT/ETHEREUM/WALLET_PASSWORD";
    private static final String XML_PATH_TO_ETH_WALLET_FILE = "/API/CONTEXT/ETHEREUM/WALLET_FILE_PATH";
    private static final String XML_PATH_TO_INFURA_API_LINK_TYPE = "/API/CONTEXT/ETHEREUM/INFURA_API_LINK/@TYPE";
    private static final String XML_PATH_TO_ETH_WALLET_PASSWORD_TYPE = "/API/CONTEXT/ETHEREUM/WALLET_PASSWORD/@TYPE";
    private static final String XML_PATH_TO_ETH_WALLET_FILE_TYPE = "/API/CONTEXT/ETHEREUM/WALLET_FILE_PATH/@TYPE";

    public static Set<PosixFilePermission> groupAccessPosixPerms = new HashSet<>();
    public static Set<PosixFilePermission> ownerAccessPosixPerms = new HashSet<>();
    public static Set<PosixFilePermission> groupAndOwnerAccessPosixPerms = new HashSet<>();
    public static Set<PosixFilePermission> allAccessPosixPerms = new HashSet<>();
    public static Deque<String> filter = new ArrayDeque<>();
    public static Deque<String> fields = new ArrayDeque<>();
    public static Deque<String> strategy = new ArrayDeque<>();
    public static List<String> filterRelations = new ArrayList<>();

    public static void populateApplicationCache(){

        groupAccessPosixPerms.add(PosixFilePermission.GROUP_WRITE);
        groupAccessPosixPerms.add(PosixFilePermission.GROUP_READ);
        groupAccessPosixPerms.add(PosixFilePermission.GROUP_EXECUTE);

        ownerAccessPosixPerms.add(PosixFilePermission.OWNER_WRITE);
        ownerAccessPosixPerms.add(PosixFilePermission.OWNER_READ);
        ownerAccessPosixPerms.add(PosixFilePermission.OWNER_EXECUTE);

        groupAndOwnerAccessPosixPerms.add(PosixFilePermission.OWNER_WRITE);
        groupAndOwnerAccessPosixPerms.add(PosixFilePermission.OWNER_READ);
        groupAndOwnerAccessPosixPerms.add(PosixFilePermission.OWNER_EXECUTE);
        groupAndOwnerAccessPosixPerms.add(PosixFilePermission.GROUP_WRITE);
        groupAndOwnerAccessPosixPerms.add(PosixFilePermission.GROUP_READ);
        groupAndOwnerAccessPosixPerms.add(PosixFilePermission.GROUP_EXECUTE);

        allAccessPosixPerms.add(PosixFilePermission.OWNER_WRITE);
        allAccessPosixPerms.add(PosixFilePermission.OWNER_READ);
        allAccessPosixPerms.add(PosixFilePermission.OWNER_EXECUTE);
        allAccessPosixPerms.add(PosixFilePermission.GROUP_WRITE);
        allAccessPosixPerms.add(PosixFilePermission.GROUP_READ);
        allAccessPosixPerms.add(PosixFilePermission.GROUP_EXECUTE);
        allAccessPosixPerms.add(PosixFilePermission.OTHERS_WRITE);
        allAccessPosixPerms.add(PosixFilePermission.OTHERS_READ);
        allAccessPosixPerms.add(PosixFilePermission.OTHERS_EXECUTE);

        fields.add("*");
        filter.add("*");
        strategy.add("and");

        filterRelations.add("eq");
        filterRelations.add("lt");
        filterRelations.add("lte");
        filterRelations.add("gt");
        filterRelations.add("gte");
        filterRelations.add("like");
        filterRelations.add("ilike");
        filterRelations.add("contains");
        filterRelations.add("icontains");
        filterRelations.add("sw");
        filterRelations.add("isw");
        filterRelations.add("ew");
        filterRelations.add("iew");
        filterRelations.add("in");
        filterRelations.add("!eq");
        filterRelations.add("!lt");
        filterRelations.add("!lte");
        filterRelations.add("!gt");
        filterRelations.add("!gte");
        filterRelations.add("!like");
        filterRelations.add("!ilike");
        filterRelations.add("!contains");
        filterRelations.add("!icontains");
        filterRelations.add("!sw");
        filterRelations.add("!isw");
        filterRelations.add("!ew");
        filterRelations.add("!iew");
        filterRelations.add("!in");
    }

    public static long getTokenExpiry(){
        return Long.parseLong(XmlUtils.readXMLTag(XML_PATH_TO_TOKEN_EXPIRY));
    }

    public static String getPublicAssetsServer(){
        return XmlUtils.readXMLTag(XML_PATH_TO_PUBLIC_ASSETS_SERVER);
    }
    public static String getSecureFolderPath(){
        return XmlUtils.readXMLTag(XML_PATH_TO_SECURE_FOLDER_PATH);
    }
    public static String getEnableBasicAuth(){
        return XmlUtils.readXMLTag(XML_PATH_TO_CONTEXT_ENABLE_BASIC_AUTH);
    }
    public static String getPublicAssetsPath(){
        return XmlUtils.readXMLTag(XML_PATH_TO_PUBLIC_ASSETS_FOLDER_PATH);
    }
    public static String getApiContextHost(){
        return XmlUtils.readXMLTag(XML_PATH_TO_CONTEXT_HOST);
    }
    public static String getApiServer(){
        return XmlUtils.readXMLTag(XML_PATH_TO_CONTEXT_SERVER);
    }
    public static String getApiContextClientRootDir(){
        return XmlUtils.readXMLTag(XML_PATH_TO_CONTEXT_CLIENT_ROOT_DIR);
    }

    public static String getDefaultTimeZone(){
        return XmlUtils.readXMLTag(XML_PATH_TO_CONTEXT_TIME_ZONE);
    }


    public static String getUndertowEnableAccessLog(){
        return XmlUtils.readXMLTag(XML_PATH_TO_UNDERTOW_ENABLE_ACCESS_LOG);
    }
    public static String getUndertowAccessLogDir(){
        return XmlUtils.readXMLTag(XML_PATH_TO_UNDERTOW_ACCESS_LOG_DIR);
    }
    public static String getUndertowAccessLogPattern(){
        return XmlUtils.readXMLTag(XML_PATH_TO_UNDERTOW_ACCESS_LOG_PATTERN);
    }
    public static String getUndertowEnableCompression(){
        return XmlUtils.readXMLTag(XML_PATH_TO_UNDERTOW_ENABLE_COMPRESSION);
    }
    public static String getUndertowCompressionMinResponseSize(){
        return XmlUtils.readXMLTag(XML_PATH_TO_UNDERTOW_COMPRESSION_MIN_RESPONSE_SIZE);
    }


    public static String getDbServer() {
        return XmlUtils.readXMLTag(XML_PATH_TO_SERVER);
    }

    public static String getApiContextPath() {
        return XmlUtils.readXMLTag(XML_PATH_TO_CONTEXT_PATH);
    }
    public static Integer getApiContextPort() {
        return Integer.parseInt(XmlUtils.readXMLTag(XML_PATH_TO_CONTEXT_PORT));
    }
    public static String getApiContextProtocol() {
        return XmlUtils.readXMLTag(XML_PATH_TO_CONTEXT_PROTOCOL);
    }

    public static String getDbHost() {
        return XmlUtils.readXMLTag(XML_PATH_TO_HOST);
    }

    public static String getDbPort() {
        return XmlUtils.readXMLTag(XML_PATH_TO_PORT);
    }

    public static String getDbName() {
        String dbName = XmlUtils.readXMLTag(XML_PATH_TO_DATABASE_NAME);
        System.out.println(ansi().a("Database Name: ").bold().a(dbName).reset());
        return dbName;
    }

    public static String getHibShowSql() {
        return XmlUtils.readXMLTag(XML_PATH_TO_HIBERNATE_SHOW_SQL);
    }

    public static String getHibFormatSql() {
        return XmlUtils.readXMLTag(XML_PATH_TO_HIBERNATE_FORMAT_SQL);
    }

    public static String getHibUseQueryCache() {
        return XmlUtils.readXMLTag(XML_PATH_TO_HIBERNATE_USE_QUERY_CACHE);
    }

    public static String getHibGenerateStatistics() {
        return XmlUtils.readXMLTag(XML_PATH_TO_HIBERNATE_GENERATE_STATISTICS);
    }

    public static String getHibUseSecondLevelCache() {
        return XmlUtils.readXMLTag(XML_PATH_TO_HIBERNATE_USE_SECOND_LEVEL_CACHE);
    }

    public static String getHibHbm2ddlAuto(boolean refactor) {

        String hbm2dllAuto = XmlUtils.readXMLTag(XML_PATH_TO_HIBERNATE_HBM2DDL_AUTO).toLowerCase();
        System.out.println(ansi().a("Hibernate DB Setup Strategy: ").bold().a(hbm2dllAuto).reset());

        if(hbm2dllAuto.equals("create")){
            System.out.println(ansi().fg(Ansi.Color.YELLOW).bold().a("WARN: ")
                    .fg(Ansi.Color.RED).a("Dropping all database objects and recreating them...").reset());
        }

        if(hbm2dllAuto.equals("update")){
            System.out.println(ansi().fg(Ansi.Color.BLUE).bold().a("INFO: ")
                    .fg(Ansi.Color.WHITE).a("Checking for schema changes and updating if any...").reset());
        }

        if(hbm2dllAuto.equals("validate")){
            System.out.println(ansi().fg(Ansi.Color.BLUE).bold().a("INFO: ")
                    .fg(Ansi.Color.WHITE).a("Validating and introspecting current schema...").reset());
        }

        if(refactor) if(hbm2dllAuto.equals("create") || hbm2dllAuto.equals("update"))
            XmlUtils.updateXMLTag(XML_PATH_TO_HIBERNATE_HBM2DDL_AUTO, "validate");

        return hbm2dllAuto;
    }

    public static String getHibHbm2ddlAuto() {
        return getHibHbm2ddlAuto(false);
    }

    public static String getHibDefaultSchema() {
        return XmlUtils.readXMLTag(XML_PATH_TO_HIBERNATE_DEFAULT_SCHEMA);
    }

    public static String getHibDefaultCatalog() {
        return XmlUtils.readXMLTag(XML_PATH_TO_HIBERNATE_DEFAULT_CATALOG);
    }

    public static String getHibUseJdbcMetadataDefaults() {
        return XmlUtils.readXMLTag(XML_PATH_TO_HIBERNATE_USE_JDBC_METADATA_DEFAULTS);
    }

    public static String getHibConnPoolSize() {
        return XmlUtils.readXMLTag(XML_PATH_TO_HIBERNATE_CONNECTION_POOL_SIZE);
    }

    public static String getHibC3p0MinSize() {
        return XmlUtils.readXMLTag(XML_PATH_TO_HIBERNATE_C3P0_MIN_SIZE);
    }

    public static String getHibC3p0MaxSize() {
        return XmlUtils.readXMLTag(XML_PATH_TO_HIBERNATE_C3P0_MAX_SIZE);
    }

    public static String getHibC3p0Timeout() {
        return XmlUtils.readXMLTag(XML_PATH_TO_HIBERNATE_C3P0_TIMEOUT);
    }

    public static String getHibC3p0MaxStatements() {
        return XmlUtils.readXMLTag(XML_PATH_TO_HIBERNATE_C3P0_MAX_STATEMENTS);
    }

    public static String getHibC3p0IdleTestPeriod() {
        return XmlUtils.readXMLTag(XML_PATH_TO_HIBERNATE_C3P0_IDLE_TEST_PERIOD);
    }

    public static String getPathToTmpFile() {
        return XmlUtils.readXMLTag(XML_PATH_TO_TEMP_FILE_PATH);
    }

    public static String getPasswordType(String tag) {

        String passwordType = XmlUtils.readXMLTag(tag);

        if(!passwordType.equals(SKY_DELIMITER)){
            return passwordType;
        }

        return SKY_DELIMITER;
    }

    public static String getDbUsername(){

        String dbUsername = XmlUtils.readXMLTag(XML_PATH_TO_DB_USERNAME);
        System.out.println(ansi().a("Database Username: ").bold().a(dbUsername).reset());

        if (!dbUsername.equals(SKY_DELIMITER)){
            return dbUsername;
        }
        return SKY_DELIMITER;
    }

    public static String getDbPassword(){
        return getPassword(XML_PATH_TO_DB_PASSWORD_TYPE, XML_PATH_TO_DB_PASSWORD);
    }

    public static String getInfuraLink(){
        return getPassword(XML_PATH_TO_INFURA_API_LINK_TYPE, XML_PATH_TO_INFURA_API_LINK);
    }

    public static String getEthWalletPassword(){
        return getPassword(XML_PATH_TO_ETH_WALLET_PASSWORD_TYPE, XML_PATH_TO_ETH_WALLET_PASSWORD);
    }

    public static String getEthWalletFilePath(){
        return getPassword(XML_PATH_TO_ETH_WALLET_FILE_TYPE, XML_PATH_TO_ETH_WALLET_FILE);
    }

    public static String getPassword(String passwordTypeTag, String passwordTag){
        if (getPasswordType(passwordTypeTag).equals(CLEAR_TEXT)){
            String password = XmlUtils.readXMLTag(passwordTag);

            String encryptedText = Encryption.encrypt(password);

            if (!(encryptedText.equals(SKY_DELIMITER) || password.equals(SKY_DELIMITER))){
                setPassword(encryptedText, passwordTag);
                setPasswordType(ENCRYPTED, passwordTypeTag);
                return password;
            }

            return SKY_DELIMITER;
        }else{
            String decryptedText = Encryption.decrypt(
                    XmlUtils.readXMLTag(passwordTag)
            );

            if (!decryptedText.equals(SKY_DELIMITER)){
                return decryptedText;
            }
        }
        return SKY_DELIMITER;
    }

    public static boolean doWeInitializeDb(boolean refactor){
        String dbInitStatus = XmlUtils.readXMLTag(XML_PATH_TO_DB_INITIALIZE).toLowerCase();

        if(dbInitStatus.equals("true")){
            if(refactor) XmlUtils.updateXMLTag(XML_PATH_TO_DB_INITIALIZE, "false");
            return true;
        }

        return false;
    }

    public static boolean doWeInitializeDb(){
        return doWeInitializeDb(false);
    }

    private static void setPasswordType(String credentialsType, String passwordTypeTag)  {
        XmlUtils.updateXMLTag(passwordTypeTag, credentialsType);
    }

    public static boolean setDbUsername(String dbUsedname)  {
        return XmlUtils.updateXMLTag(XML_PATH_TO_DB_USERNAME, dbUsedname);
    }

    private static void setPassword(String password, String tag)  {
        XmlUtils.updateXMLTag(tag, password);
    }


    public static String getMailerSmtpHost() {
        return XmlUtils.readXMLTag(XML_PATH_TO_MAILER_SMTP_HOST);
    }

    public static String getMailerSmtpEnableTls() {
        return XmlUtils.readXMLTag(XML_PATH_TO_MAILER_SMTP_ENABLE_TLS);
    }

    public static String getMailerSmtpPort() {
        return XmlUtils.readXMLTag(XML_PATH_TO_MAILER_SMTP_PORT);
    }

    public static String getMailerFromEmailAddress() {
        return XmlUtils.readXMLTag(XML_PATH_TO_MAILER_FROM_EMAIL_ADDRESS);
    }

    public static String getMailerPassword() {
        if (getMailerPasswordType().equals(CLEAR_TEXT)){
            String mailerPassword = XmlUtils.readXMLTag(XML_PATH_TO_MAILER_PASSWORD);

            String encryptedText = Encryption.encrypt(mailerPassword);

            if (!(encryptedText.equals(SKY_DELIMITER) || mailerPassword.equals(SKY_DELIMITER))){
                setMailerPassword(encryptedText);
                setMailerPassowrdType(ENCRYPTED);
                return mailerPassword;
            }

            return SKY_DELIMITER;
        }else{
            String decryptedText = Encryption.decrypt(
                    XmlUtils.readXMLTag(XML_PATH_TO_MAILER_PASSWORD)
            );

            if (!decryptedText.equals(SKY_DELIMITER)){
                return decryptedText;
            }
        }
        return SKY_DELIMITER;
    }

    public static String getMailerPasswordType() {
        return XmlUtils.readXMLTag(XML_PATH_TO_MAILER_PASSWORD_TYPE);
    }

    public static String getPortalPassResetHost() {
        return XmlUtils.readXMLTag(XML_PATH_TO_MAILER_PASS_RESET_HOST);
    }

    public static boolean setMailerPassword(String mailerPassword)  {
        return XmlUtils.updateXMLTag(XML_PATH_TO_MAILER_PASSWORD, mailerPassword);
    }

    public static boolean setMailerPassowrdType(String credentialsType)  {
        return XmlUtils.updateXMLTag(XML_PATH_TO_MAILER_PASSWORD_TYPE, credentialsType);
    }

    public static String getAccTypeQuery(String accessToken){
        return "select ua.id, uat.acTypeName from UserAccount as ua" +
                " inner join UserAccountType uat on ua.userAccountTypeId = uat.id" +
                " inner join AccessToken at on ua.id = at.userAccountId" +
                " where at.accessToken = '"+accessToken+"'";
    }

    public static String getAccRolesQuery(String userId){
        return "select sr.roleName from UserRole ur" +
                " inner join SystemRole sr on ur.systemRoleId = sr.id" +
                " where ur.userAccountId = "+userId;
    }

    public static String getDpNameQuery(long userId, int attachmentTypeId){
        return "select docName from Attachment where " +
                "userAccountId = "+userId+" and " +
                "attachmentTypeId = "+attachmentTypeId+" " +
                "order by updated desc";
    }

    public static String getNextOfKinDocOwnerQuery(String kdid, long userId){
        return "select 1 from NextOfKinDocument nokd " +
                "inner join NextOfKin nok on nok.id = nokd.nextOfKinId " +
                "inner join UserAccount ua on ua.id = nok.userAccountId " +
                "where nokd.id = "+kdid+" and " +
                "ua.id = "+userId;
    }

    public static String getLoanGuarantorOwnerQuery(String gid, long userId){
        return "select 1 from Guarantor ga " +
                "inner join UserLoan ul on ul.id = ga.loanId " +
                "inner join UserAccount ua on ua.id = ul.userAccountId " +
                "where ga.id = "+gid+" and " +
                "ua.id = "+userId;
    }

    public static String getConfirmFirstTimeRegPaymentQuery(String account){
        return "select amount from integ_sky_mpesa_api " +
                "where lower(account) = '"+account+"' order by date_created desc limit 1";
    }
    
    public static String activateAccountEmail = "" +
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
            "<head>" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" +
            "    <meta name=\"viewport\" content=\"width=device-width\" />" +
            "    <title>Project SilverSpoon - Activate Account</title>" +
            "    <style type=\"text/css\">" +
            "" +
            "        * {" +
            "            margin:0;" +
            "            padding:0;" +
            "            font-family: Helvetica, Arial, sans-serif;" +
            "        }" +
            "" +
            "        img {" +
            "            max-width: 100%;" +
            "            outline: none;" +
            "            text-decoration: none;" +
            "            -ms-interpolation-mode: bicubic;" +
            "        }" +
            "" +
            "        .image-fix {" +
            "            display:block;" +
            "        }" +
            "" +
            "        .collapse {" +
            "            margin:0;" +
            "            padding:0;" +
            "        }" +
            "" +
            "        body {" +
            "            -webkit-font-smoothing:antialiased;" +
            "            -webkit-text-size-adjust:none;" +
            "            width: 100%!important;" +
            "            height: 100%;" +
            "            text-align: center;" +
            "            color: #747474;" +
            "            background-color: #ffffff;" +
            "        }" +
            "" +
            "        h1,h2,h3,h4,h5,h6 {" +
            "            font-family: Helvetica, Arial, sans-serif;" +
            "            line-height: 1.1;" +
            "        }" +
            "" +
            "        h1 small, h2 small, h3 small, h4 small, h5 small, h6 small {" +
            "            font-size: 60%;" +
            "            line-height: 0;" +
            "            text-transform: none;" +
            "        }" +
            "" +
            "        h1 {" +
            "            font-weight:200;" +
            "            font-size: 44px;" +
            "        }" +
            "" +
            "        h2 {" +
            "            font-weight:200;" +
            "            font-size: 32px;" +
            "            margin-bottom: 14px;" +
            "        }" +
            "" +
            "        h3 {" +
            "            font-weight:500;" +
            "            font-size: 27px;" +
            "        }" +
            "" +
            "        h4 {" +
            "            font-weight:500;" +
            "            font-size: 23px;" +
            "        }" +
            "" +
            "        h5 {" +
            "            font-weight:900;" +
            "            font-size: 17px;" +
            "        }" +
            "" +
            "        h6 {" +
            "            font-weight:900;" +
            "            font-size: 14px;" +
            "            text-transform: uppercase;" +
            "        }" +
            "" +
            "        .collapse {" +
            "            margin:0!important;" +
            "        }" +
            "" +
            "        td, div {" +
            "            font-family: Helvetica, Arial, sans-serif;" +
            "            text-align: center;" +
            "        }" +
            "" +
            "        p, ul {" +
            "            margin-bottom: 10px;" +
            "            font-weight: normal;" +
            "            font-size:14px;" +
            "            line-height:1.6;" +
            "        }" +
            "" +
            "        p.lead {" +
            "            font-size:17px;" +
            "        }" +
            "" +
            "        p.last {" +
            "            margin-bottom:0px;" +
            "        }" +
            "" +
            "        ul li {" +
            "            margin-left:5px;" +
            "            list-style-position: inside;" +
            "        }" +
            "" +
            "        a {" +
            "            color: #747474;" +
            "            text-decoration: none;" +
            "        }" +
            "" +
            "        a img {" +
            "            border: none;" +
            "        }" +
            "" +
            "        .head-wrap {" +
            "            width: 100%;" +
            "            margin: 0 auto;" +
            "            background-color: #f9f8f8;" +
            "            border-bottom: 1px solid #d8d8d8;" +
            "        }" +
            "" +
            "        .head-wrap * {" +
            "            margin: 0;" +
            "            padding: 0;" +
            "        }" +
            "" +
            "        .header-background {" +
            "            background: repeat-x url(https://www.filepicker.io/api/file/wUGKTIOZTDqV2oJx5NCh) left bottom;" +
            "        }" +
            "" +
            "        .header {" +
            "            height: 42px;" +
            "        }" +
            "" +
            "        .header .content {" +
            "            padding: 0;" +
            "        }" +
            "" +
            "        .header .brand {" +
            "            font-size: 16px;" +
            "            line-height: 42px;" +
            "            font-weight: bold;" +
            "        }" +
            "" +
            "        .header .brand a {" +
            "            color: #464646;" +
            "        }" +
            "" +
            "        .body-wrap {" +
            "            width: 505px;" +
            "            margin: 0 auto;" +
            "            background-color: #ffffff;" +
            "        }" +
            "" +
            "        .soapbox .soapbox-title {" +
            "            font-size: 30px;" +
            "            color: #464646;" +
            "            padding-top: 25px;" +
            "            padding-bottom: 28px;" +
            "        }" +
            "" +
            "        .content .status-container.single .status-padding {" +
            "            width: 80px;" +
            "        }" +
            "" +
            "        .content .status {" +
            "            width: 100%;" +
            "        }" +
            "" +
            "        .content .status-container.single .status {" +
            "            width: 300px;" +
            "        }" +
            "" +
            "        .status {" +
            "            border-collapse: collapse;" +
            "            margin-left: 15px;" +
            "            color: #656565;" +
            "        }" +
            "" +
            "        .status .status-cell {" +
            "            border: 1px solid #b3b3b3;" +
            "            height: 50px;" +
            "            padding-left: 15px;" +
            "            padding-right: 15px;" +
            "        }" +
            "" +
            "        .status .status-cell.success," +
            "        .status .status-cell.active {" +
            "            height: 65px;" +
            "        }" +
            "" +
            "        .status .status-cell.success {" +
            "            background: #f2ffeb;" +
            "            color: #51da42;" +
            "        }" +
            "" +
            "        .status .status-cell.success .status-title {" +
            "            font-size: 15px;" +
            "        }" +
            "" +
            "        .status .status-cell.active {" +
            "            background: #fffde0;" +
            "            width: 135px;" +
            "        }" +
            "" +
            "        .status .status-title {" +
            "            font-size: 16px;" +
            "            font-weight: bold;" +
            "            line-height: 23px;" +
            "        }" +
            "" +
            "        .status .status-image {" +
            "            vertical-align: text-bottom;" +
            "        }" +
            "" +
            "        .body .body-padded," +
            "        .body .body-padding {" +
            "            padding-top: 34px;" +
            "        }" +
            "" +
            "        .body .body-padding {" +
            "            width: 41px;" +
            "        }" +
            "" +
            "        .body-padded," +
            "        .body-title {" +
            "            text-align: left;" +
            "        }" +
            "" +
            "        .body .body-title {" +
            "            font-weight: bold;" +
            "            font-size: 17px;" +
            "            color: #464646;" +
            "        }" +
            "" +
            "        .body .body-text .body-text-cell {" +
            "            text-align: left;" +
            "            font-size: 14px;" +
            "            line-height: 1.6;" +
            "            padding: 9px 0 17px;" +
            "        }" +
            "" +
            "        .body .body-signature-block .body-signature-cell {" +
            "            padding: 25px 0 30px;" +
            "            text-align: left;" +
            "        }" +
            "" +
            "        .body .body-signature {" +
            "            font-family: \"Comic Sans MS\", Textile, cursive;" +
            "            font-weight: bold;" +
            "        }" +
            "" +
            "        .footer-wrap {" +
            "            width: 100%;" +
            "            margin: 0 auto;" +
            "            clear: both !important;" +
            "            background-color: #e5e5e5;" +
            "            border-top: 1px solid #b3b3b3;" +
            "            font-size: 12px;" +
            "            color: #656565;" +
            "            line-height: 30px;" +
            "        }" +
            "" +
            "        .footer-wrap .container {" +
            "            padding: 14px 0;" +
            "        }" +
            "" +
            "        .footer-wrap .container .content {" +
            "            padding: 0;" +
            "        }" +
            "" +
            "        .footer-wrap .container .footer-lead {" +
            "            font-size: 14px;" +
            "        }" +
            "" +
            "        .footer-wrap .container .footer-lead a {" +
            "            font-size: 14px;" +
            "            font-weight: bold;" +
            "            color: #535353;" +
            "        }" +
            "" +
            "        .footer-wrap .container a {" +
            "            font-size: 12px;" +
            "            color: #656565;" +
            "        }" +
            "" +
            "        .footer-wrap .container a.last {" +
            "            margin-right: 0;" +
            "        }" +
            "" +
            "        .footer-wrap .footer-group {" +
            "            display: inline-block;" +
            "        }" +
            "" +
            "        .container {" +
            "            display: block !important;" +
            "            max-width: 505px !important;" +
            "            clear: both !important;" +
            "        }" +
            "" +
            "        .content {" +
            "            padding: 0;" +
            "            max-width: 505px;" +
            "            margin: 0 auto;" +
            "            display: block;" +
            "        }" +
            "" +
            "        .content table {" +
            "            width: 100%;" +
            "        }" +
            "" +
            "" +
            "        .clear {" +
            "            display: block;" +
            "            clear: both;" +
            "        }" +
            "" +
            "        table.full-width-gmail-android {" +
            "            width: 100% !important;" +
            "        }" +
            "" +
            "    </style>" +
            "" +
            "    <style type=\"text/css\" media=\"only screen\">" +
            "" +
            "        @media only screen {" +
            "" +
            "            table[class*=\"head-wrap\"]," +
            "            table[class*=\"body-wrap\"]," +
            "            table[class*=\"footer-wrap\"] {" +
            "                width: 100% !important;" +
            "            }" +
            "" +
            "            td[class*=\"container\"] {" +
            "                margin: 0 auto !important;" +
            "            }" +
            "" +
            "        }" +
            "" +
            "        @media only screen and (max-width: 505px) {" +
            "" +
            "            *[class*=\"w320\"] {" +
            "                width: 320px !important;" +
            "            }" +
            "" +
            "            table[class=\"status\"] td[class*=\"status-cell\"]," +
            "            table[class=\"status\"] td[class*=\"status-cell\"].active {" +
            "                display: block !important;" +
            "                width: auto !important;" +
            "            }" +
            "" +
            "            table[class=\"status-container single\"] table[class=\"status\"] {" +
            "                width: 270px !important;" +
            "                margin-left: 0;" +
            "            }" +
            "" +
            "            table[class=\"status\"] td[class*=\"status-cell\"]," +
            "            table[class=\"status\"] td[class*=\"status-cell\"].active," +
            "            table[class=\"status\"] td[class*=\"status-cell\"] [class*=\"status-title\"] {" +
            "                line-height: 65px !important;" +
            "                font-size: 18px !important;" +
            "            }" +
            "" +
            "            table[class=\"status-container single\"] table[class=\"status\"] td[class*=\"status-cell\"]," +
            "            table[class=\"status-container single\"] table[class=\"status\"] td[class*=\"status-cell\"].active," +
            "            table[class=\"status-container single\"] table[class=\"status\"] td[class*=\"status-cell\"] [class*=\"status-title\"] {" +
            "                line-height: 51px !important;" +
            "            }" +
            "" +
            "            table[class=\"status\"] td[class*=\"status-cell\"].active [class*=\"status-title\"] {" +
            "                display: inline !important;" +
            "            }" +
            "" +
            "        }" +
            "    </style>" +
            "</head>" +
            "" +
            "<body bgcolor=\"#ffffff\">" +
            "" +
            "<div align=\"center\">" +
            "    <table class=\"head-wrap w320 full-width-gmail-android\" bgcolor=\"#f9f8f8\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">" +
            "        <tr>" +
            "            <td background=\"https://www.filepicker.io/api/file/UOesoVZTFObSHCgUDygC\" bgcolor=\"#ffffff\" width=\"100%\" height=\"8\" valign=\"top\">" +
            "                <!--[if gte mso 9]>" +
            "                <v:rect xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"true\" stroke=\"false\" style=\"mso-width-percent:1000;height:8px;\">" +
            "                    <v:fill type=\"tile\" src=\"https://www.filepicker.io/api/file/UOesoVZTFObSHCgUDygC\" color=\"#ffffff\" />" +
            "                    <v:textbox inset=\"0,0,0,0\">" +
            "                <![endif]-->" +
            "                <div height=\"8\">" +
            "                </div>" +
            "                <!--[if gte mso 9]>" +
            "                </v:textbox>" +
            "                </v:rect>" +
            "                <![endif]-->" +
            "            </td>" +
            "        </tr>" +
            "        <tr class=\"header-background\">" +
            "            <td class=\"header container\" align=\"center\">" +
            "                <div class=\"content\">" +
            "            <span class=\"brand\">" +
            "              <a href=\"#\">" +
            "                Project SilverSpoon" +
            "              </a>" +
            "            </span>" +
            "                </div>" +
            "            </td>" +
            "        </tr>" +
            "    </table>" +
            "" +
            "    <table class=\"body-wrap w320\">" +
            "        <tr>" +
            "            <td></td>" +
            "            <td class=\"container\">" +
            "                <div class=\"content\">" +
            "                    <table cellspacing=\"0\">" +
            "                        <tr>" +
            "                            <td>" +
            "                                <table class=\"soapbox\">" +
            "                                    <tr>" +
            "                                        <td class=\"soapbox-title\">Welcome to SilverSpoon!</td>" +
            "                                    </tr>" +
            "                                </table>" +
            "                                <table class=\"body\">" +
            "                                    <tr>" +
            "                                        <td class=\"body-padding\"></td>" +
            "                                        <td class=\"body-padded\">" +
            "                                            <div class=\"body-title\">Hey, thanks for signing up.</div>" +
            "                                            <table class=\"body-text\">" +
            "                                                <tr>" +
            "                                                    <td class=\"body-text-cell\">" +
            "                                                        We're really excited for you to join our community! You're just one click away from activate your account." +
            "                                                    </td>" +
            "                                                </tr>" +
            "                                            </table>" +
            "                                            <div style=\"text-align:left;\"><!--[if mso]>" +
            "                                                <v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"#\" style=\"height:38px;v-text-anchor:middle;width:190px;\" arcsize=\"11%\" strokecolor=\"#407429\" fill=\"t\">" +
            "                                                    <v:fill type=\"tile\" src=\"https://www.filepicker.io/api/file/N8GiNGsmT6mK6ORk00S7\" color=\"#41CC00\" />" +
            "                                                    <w:anchorlock/>" +
            "                                                    <center style=\"color:#ffffff;font-family:sans-serif;font-size:17px;font-weight:bold;\">Come on back</center>" +
            "                                                </v:roundrect>" +
            "                                                <![endif]--><a href=\"%(redirect)%\"" +
            "                                                               target=\"_blank\"" +
            "                                                               style=\"background-color:#41CC00;background-image:url(https://www.filepicker.io/api/file/N8GiNGsmT6mK6ORk00S7);border:1px solid #407429;border-radius:4px;color:#ffffff;display:inline-block;font-family:sans-serif;font-size:17px;font-weight:bold;text-shadow: -1px -1px #47A54B;line-height:38px;text-align:center;text-decoration:none;width:190px;-webkit-text-size-adjust:none;mso-hide:all;\">Activate Account!</a></div>" +
            "                                            <table class=\"body-signature-block\">" +
            "                                                <tr>" +
            "                                                    <td class=\"body-signature-cell\">" +
            "                                                        <p>Thanks so much,</p>" +
            "                                                        <p class=\"body-signature\">Project SilverSpoon</p>" +
            "                                                    </td>" +
            "                                                </tr>" +
            "                                            </table>" +
            "                                        </td>" +
            "                                        <td class=\"body-padding\"></td>" +
            "                                    </tr>" +
            "                                </table>" +
            "                            </td>" +
            "                        </tr>" +
            "                    </table>" +
            "                </div>" +
            "            </td>" +
            "            <td></td>" +
            "        </tr>" +
            "    </table>" +
            "" +
            "    <table class=\"footer-wrap w320 full-width-gmail-android\" bgcolor=\"#e5e5e5\">" +
            "        <tr>" +
            "            <td class=\"container\">" +
            "                <div class=\"content footer-lead\">" +
            "                    <a href=\"#\"><b>Get in touch</b></a> if you have any questions or feedback" +
            "                </div>" +
            "            </td>" +
            "        </tr>" +
            "    </table>" +
            "    <table class=\"footer-wrap w320 full-width-gmail-android\" bgcolor=\"#e5e5e5\">" +
            "        <tr>" +
            "            <td class=\"container\">" +
            "                <div class=\"content\">" +
            "                    <a href=\"#\">Contact Us</a>&nbsp;&nbsp;|&nbsp;&nbsp;" +
            "                    <span class=\"footer-group\">" +
            "              <a href=\"#\">Facebook</a>&nbsp;&nbsp;|&nbsp;&nbsp;" +
            "              <a href=\"#\">Twitter</a>&nbsp;&nbsp;|&nbsp;&nbsp;" +
            "              <a href=\"#\">Support</a>" +
            "            </span>" +
            "                </div>" +
            "            </td>" +
            "        </tr>" +
            "    </table>" +
            "</div>" +
            "" +
            "</body>" +
            "</html>";
    
    public static String accountActivatedHtml = "" +
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
            "<head>" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" +
            "    <meta name=\"viewport\" content=\"width=device-width\" />" +
            "    <title>Account Activated</title>" +
            "    <style type=\"text/css\">" +
            "        /* -------------------------------------" +
            "          GLOBAL" +
            "        ------------------------------------- */" +
            "        * {" +
            "            margin:0;" +
            "            padding:0;" +
            "            font-family: Helvetica, Arial, sans-serif;" +
            "        }" +
            "" +
            "        img {" +
            "            max-width: 100%;" +
            "            outline: none;" +
            "            text-decoration: none;" +
            "            -ms-interpolation-mode: bicubic;" +
            "        }" +
            "        .image-fix {" +
            "            display:block;" +
            "        }" +
            "        .collapse {" +
            "            margin:0;" +
            "            padding:0;" +
            "        }" +
            "        body {" +
            "            -webkit-font-smoothing:antialiased;" +
            "            -webkit-text-size-adjust:none;" +
            "            width: 100%!important;" +
            "            height: 100%;" +
            "            text-align: center;" +
            "            color: #747474;" +
            "            background-color: #ffffff;" +
            "        }" +
            "        h1,h2,h3,h4,h5,h6 {" +
            "            font-family: Helvetica, Arial, sans-serif;" +
            "            line-height: 1.1;" +
            "        }" +
            "        h1 small, h2 small, h3 small, h4 small, h5 small, h6 small {" +
            "            font-size: 60%;" +
            "            line-height: 0;" +
            "            text-transform: none;" +
            "        }" +
            "" +
            "        h1 {" +
            "            font-weight:200;" +
            "            font-size: 44px;" +
            "        }" +
            "        h2 {" +
            "            font-weight:200;" +
            "            font-size: 32px;" +
            "            margin-bottom: 14px;" +
            "        }" +
            "        h3 {" +
            "            font-weight:500;" +
            "            font-size: 27px;" +
            "        }" +
            "        h4 {" +
            "            font-weight:500;" +
            "            font-size: 23px;" +
            "        }" +
            "        h5 {" +
            "            font-weight:900;" +
            "            font-size: 17px;" +
            "        }" +
            "        h6 {" +
            "            font-weight:900;" +
            "            font-size: 14px;" +
            "            text-transform: uppercase;" +
            "        }" +
            "" +
            "        .collapse {" +
            "            margin: 0 !important;" +
            "        }" +
            "" +
            "        td, div {" +
            "            font-family: Helvetica, Arial, sans-serif;" +
            "            text-align: center;" +
            "        }" +
            "" +
            "        p, ul {" +
            "            margin-bottom: 10px;" +
            "            font-weight: normal;" +
            "            font-size:14px;" +
            "            line-height:1.6;" +
            "        }" +
            "        p.lead {" +
            "            font-size:17px;" +
            "        }" +
            "        p.last {" +
            "            margin-bottom:0px;" +
            "        }" +
            "" +
            "        ul li {" +
            "            margin-left:5px;" +
            "            list-style-position: inside;" +
            "        }" +
            "" +
            "        a {" +
            "            color: #747474;" +
            "            text-decoration: none;" +
            "        }" +
            "" +
            "        a img {" +
            "            border: none;" +
            "        }" +
            "" +
            "        /* -------------------------------------" +
            "            ELEMENTS" +
            "        ------------------------------------- */" +
            "" +
            "" +
            "        table.head-wrap {" +
            "            width: 100%;" +
            "            margin: 0 auto;" +
            "            background-color: #f9f8f8;" +
            "            border-bottom: 1px solid #d8d8d8;" +
            "        }" +
            "" +
            "        .head-wrap * {" +
            "            margin: 0;" +
            "            padding: 0;" +
            "        }" +
            "" +
            "        .header-background {" +
            "            background: repeat-x url(https://www.filepicker.io/api/file/wUGKTIOZTDqV2oJx5NCh) left bottom;" +
            "        }" +
            "" +
            "" +
            "        .header {" +
            "            height: 42px;" +
            "        }" +
            "        .header .content {" +
            "            padding: 0;" +
            "        }" +
            "        .header .brand {" +
            "            font-size: 16px;" +
            "            line-height: 42px;" +
            "            font-weight: bold;" +
            "        }" +
            "        .header .brand a {" +
            "            color: #464646;" +
            "        }" +
            "" +
            "" +
            "" +
            "" +
            "" +
            "        table.body-wrap {" +
            "            width: 505px;" +
            "            margin: 0 auto;" +
            "            background-color: #ffffff;" +
            "        }" +
            "" +
            "" +
            "        .soapbox .soapbox-title {" +
            "            font-size: 30px;" +
            "            color: #464646;" +
            "            padding-top: 25px;" +
            "            padding-bottom: 28px;" +
            "        }" +
            "" +
            "        .content .status {" +
            "            width: 90%;" +
            "        }" +
            "" +
            "        .status {" +
            "            border-collapse: collapse;" +
            "            margin-left: 15px;" +
            "            color: #656565;" +
            "        }" +
            "        .status .status-cell {" +
            "            border: 1px solid #b3b3b3;" +
            "            height: 50px;" +
            "            font-size: 16px;" +
            "            line-height: 23px;" +
            "        }" +
            "        .status .status-cell.success," +
            "        .status .status-cell.active {" +
            "            height: 65px;" +
            "        }" +
            "        .status .status-cell.success {" +
            "            background: #f2ffeb;" +
            "            color: #51da42;" +
            "            font-size: 15px;" +
            "        }" +
            "        .status .status-cell.active {" +
            "            background: #fffde0;" +
            "            width: 135px;" +
            "        }" +
            "        .status .status-image {" +
            "            vertical-align: text-bottom;" +
            "        }" +
            "" +
            "" +
            "        .body .body-padded," +
            "        .body .body-padding {" +
            "            padding-top: 34px;" +
            "        }" +
            "        .body .body-padding {" +
            "            width: 41px;" +
            "        }" +
            "        .body-padded," +
            "        .body-title {" +
            "            text-align: left;" +
            "        }" +
            "        .body .body-title {" +
            "            font-weight: bold;" +
            "            font-size: 17px;" +
            "            color: #464646;" +
            "        }" +
            "        .body .body-text .body-text-cell {" +
            "            text-align: left;" +
            "            font-size: 14px;" +
            "            line-height: 1.6;" +
            "            padding: 9px 0 17px;" +
            "        }" +
            "        .body .body-signature-block .body-signature-cell {" +
            "            padding: 25px 0 30px;" +
            "            text-align: left;" +
            "        }" +
            "        .body .body-signature {" +
            "            font-family: \"Comic Sans MS\", Textile, cursive;" +
            "            font-weight: bold;" +
            "        }" +
            "" +
            "" +
            "" +
            "        .footer-wrap {" +
            "            width: 100%;" +
            "            margin: 0 auto;" +
            "            clear: both !important;" +
            "            background-color: #e5e5e5;" +
            "            border-top: 1px solid #b3b3b3;" +
            "            font-size: 12px;" +
            "            color: #656565;" +
            "            line-height: 30px;" +
            "        }" +
            "        .footer-wrap td {" +
            "            padding: 14px 0;" +
            "        }" +
            "        .footer-wrap td .content {" +
            "            padding: 0;" +
            "        }" +
            "        .footer-wrap td .footer-lead {" +
            "            font-size: 14px;" +
            "        }" +
            "        .footer-wrap td .footer-lead a {" +
            "            font-size: 14px;" +
            "            font-weight: bold;" +
            "            color: #535353;" +
            "        }" +
            "        .footer-wrap td a {" +
            "            font-size: 12px;" +
            "            color: #656565;" +
            "        }" +
            "        .footer-wrap td a.last {" +
            "            margin-right: 0;" +
            "        }" +
            "        .footer-wrap .footer-group {" +
            "            display: inline-block;" +
            "        }" +
            "" +
            "" +
            "" +
            "        /* ---------------------------------------------------" +
            "            RESPONSIVENESS" +
            "        ------------------------------------------------------ */" +
            "" +
            "        /* Set a max-width, and make it display as block so it will automatically stretch to that width, but will also shrink down on a phone or something */" +
            "        .container {" +
            "            display: block !important;" +
            "            max-width: 505px !important;" +
            "            clear: both !important;" +
            "        }" +
            "" +
            "        /* This should also be a block element, so that it will fill 100% of the .container */" +
            "        .content {" +
            "            padding: 0;" +
            "            max-width: 505px;" +
            "            margin: 0 auto;" +
            "            display: block;" +
            "        }" +
            "" +
            "        /* Let's make sure tables in the content area are 100% wide */" +
            "        .content table {" +
            "            width: 100%;" +
            "        }" +
            "" +
            "" +
            "        /* Be sure to place a .clear element after each set of columns, just to be safe */" +
            "        .clear {" +
            "            display: block;" +
            "            clear: both;" +
            "        }" +
            "" +
            "        table.full-width-gmail-android {" +
            "            width: 100% !important;" +
            "        }" +
            "" +
            "    </style>" +
            "    <style type=\"text/css\" media=\"only screen\">" +
            "" +
            "" +
            "        /* -------------------------------------------" +
            "            PHONE" +
            "            For clients that support media queries." +
            "            Nothing fancy." +
            "        -------------------------------------------- */" +
            "        @media only screen {" +
            "" +
            "            table[class=\"head-wrap\"]," +
            "            table[class=\"body-wrap\"]," +
            "            table[class*=\"footer-wrap\"] {" +
            "                width: 100% !important;" +
            "            }" +
            "" +
            "            td[class*=\"container\"] {" +
            "                margin: 0 auto !important;" +
            "            }" +
            "        }" +
            "" +
            "        @media only screen and (max-width: 505px) {" +
            "" +
            "            *[class*=\"w320\"] {" +
            "                width: 320px !important;" +
            "            }" +
            "" +
            "            table[class=\"status\"] td[class*=\"status-cell\"]," +
            "            table[class=\"status\"] td[class*=\"status-cell\"].active {" +
            "                display: block !important;" +
            "                width: auto !important;" +
            "                height: auto !important;" +
            "                padding: 15px !important;" +
            "                font-size: 18px !important;" +
            "            }" +
            "" +
            "        }" +
            "    </style>" +
            "</head>" +
            "" +
            "<body bgcolor=\"#ffffff\">" +
            "" +
            "<div align=\"center\">" +
            "    <table class=\"head-wrap w320 full-width-gmail-android\" bgcolor=\"#f9f8f8\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">" +
            "        <tr>" +
            "            <td background=\"https://www.filepicker.io/api/file/UOesoVZTFObSHCgUDygC\" bgcolor=\"#ffffff\" width=\"100%\" height=\"8\" valign=\"top\">" +
            "                <!--[if gte mso 9]>" +
            "                <v:rect xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"true\" stroke=\"false\" style=\"mso-width-percent:1000;height:8px;\">" +
            "                    <v:fill type=\"tile\" src=\"https://www.filepicker.io/api/file/UOesoVZTFObSHCgUDygC\" color=\"#ffffff\" />" +
            "                    <v:textbox inset=\"0,0,0,0\">" +
            "                <![endif]-->" +
            "                <div height=\"8\">" +
            "                </div>" +
            "                <!--[if gte mso 9]>" +
            "                </v:textbox>" +
            "                </v:rect>" +
            "                <![endif]-->" +
            "            </td>" +
            "        </tr>" +
            "        <tr class=\"header-background\">" +
            "            <td class=\"header container\" align=\"center\">" +
            "                <div class=\"content\">" +
            "            <span class=\"brand\">" +
            "              <a href=\"#\">" +
            "                Project SilverSpoon" +
            "              </a>" +
            "            </span>" +
            "                </div>" +
            "            </td>" +
            "        </tr>" +
            "    </table>" +
            "" +
            "    <table class=\"body-wrap w320\">" +
            "        <tr>" +
            "            <td></td>" +
            "            <td class=\"container\">" +
            "                <div class=\"content\">" +
            "                    <table cellspacing=\"0\">" +
            "                        <tr>" +
            "                            <td>" +
            "                                <table class=\"soapbox\">" +
            "                                    <tr>" +
            "                                        <td class=\"soapbox-title\">Your account has been successfully activated</td>" +
            "                                    </tr>" +
            "                                </table>" +
            "                                <table class=\"status\" bgcolor=\"#fffeea\" cellspacing=\"0\">" +
            "                                    <tr>" +
            "                                        <td class=\"status-cell success\">" +
            "                                            <img class=\"status-image\" src=\"https://www.filepicker.io/api/file/gd9yTMfATWV8fPJlmyRC\" alt=\"✓\">&nbsp;" +
            "                                            <b>Account is now active</b>" +
            "                                        </td>" +
            "                                    </tr>" +
            "                                </table>" +
            "                                <table class=\"body\">" +
            "                                    <tr>" +
            "                                        <td class=\"body-padding\"></td>" +
            "                                        <td class=\"body-padded\">" +
            "                                            <table class=\"body-signature-block\">" +
            "                                                <tr>" +
            "                                                    <td class=\"body-signature-cell\">" +
            "                                                        <p>Thanks so much,</p>" +
            "                                                        <p class=\"body-signature\">Project SilverSpoon</p>" +
            "                                                    </td>" +
            "                                                </tr>" +
            "                                            </table>" +
            "                                        </td>" +
            "                                        <td class=\"body-padding\"></td>" +
            "                                    </tr>" +
            "                                </table>" +
            "                            </td>" +
            "                        </tr>" +
            "                    </table>" +
            "                </div>" +
            "            </td>" +
            "            <td></td>" +
            "        </tr>" +
            "    </table>" +
            "" +
            "    <table class=\"footer-wrap w320 full-width-gmail-android\" bgcolor=\"#e5e5e5\">" +
            "        <tr>" +
            "            <td>" +
            "                <div class=\"content footer-lead\">" +
            "                    <a href=\"#\"><b>Get in touch</b></a> if you have any questions or feedback" +
            "                </div>" +
            "            </td>" +
            "        </tr>" +
            "    </table>" +
            "    <table class=\"footer-wrap w320 full-width-gmail-android\" bgcolor=\"#e5e5e5\">" +
            "        <tr>" +
            "            <td>" +
            "                <div class=\"content\">" +
            "                    <a href=\"#\">Contact Us</a>&nbsp;&nbsp;|&nbsp;&nbsp;" +
            "                    <span class=\"footer-group\">" +
            "              <a href=\"#\">Facebook</a>&nbsp;&nbsp;|&nbsp;&nbsp;" +
            "              <a href=\"#\">Twitter</a>&nbsp;&nbsp;|&nbsp;&nbsp;" +
            "              <a href=\"#\">Support</a>" +
            "            </span>" +
            "                </div>" +
            "            </td>" +
            "        </tr>" +
            "    </table>" +
            "</div>" +
            "" +
            "</body>" +
            "</html>";
    
    public static String accountActivationFailedHtml = "" +
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
            "<head>" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" +
            "    <meta name=\"viewport\" content=\"width=device-width\" />" +
            "    <title>Account Activation Failed</title>" +
            "    <style type=\"text/css\">" +
            "        /* -------------------------------------" +
            "          GLOBAL" +
            "        ------------------------------------- */" +
            "        * {" +
            "            margin:0;" +
            "            padding:0;" +
            "            font-family: Helvetica, Arial, sans-serif;" +
            "        }" +
            "" +
            "        img {" +
            "            max-width: 100%;" +
            "            outline: none;" +
            "            text-decoration: none;" +
            "            -ms-interpolation-mode: bicubic;" +
            "        }" +
            "        .image-fix {" +
            "            display:block;" +
            "        }" +
            "        .collapse {" +
            "            margin:0;" +
            "            padding:0;" +
            "        }" +
            "        body {" +
            "            -webkit-font-smoothing:antialiased;" +
            "            -webkit-text-size-adjust:none;" +
            "            width: 100%!important;" +
            "            height: 100%;" +
            "            text-align: center;" +
            "            color: #747474;" +
            "            background-color: #ffffff;" +
            "        }" +
            "        h1,h2,h3,h4,h5,h6 {" +
            "            font-family: Helvetica, Arial, sans-serif;" +
            "            line-height: 1.1;" +
            "        }" +
            "        h1 small, h2 small, h3 small, h4 small, h5 small, h6 small {" +
            "            font-size: 60%;" +
            "            line-height: 0;" +
            "            text-transform: none;" +
            "        }" +
            "" +
            "        h1 {" +
            "            font-weight:200;" +
            "            font-size: 44px;" +
            "        }" +
            "        h2 {" +
            "            font-weight:200;" +
            "            font-size: 32px;" +
            "            margin-bottom: 14px;" +
            "        }" +
            "        h3 {" +
            "            font-weight:500;" +
            "            font-size: 27px;" +
            "        }" +
            "        h4 {" +
            "            font-weight:500;" +
            "            font-size: 23px;" +
            "        }" +
            "        h5 {" +
            "            font-weight:900;" +
            "            font-size: 17px;" +
            "        }" +
            "        h6 {" +
            "            font-weight:900;" +
            "            font-size: 14px;" +
            "            text-transform: uppercase;" +
            "        }" +
            "" +
            "        .collapse {" +
            "            margin: 0 !important;" +
            "        }" +
            "" +
            "        td, div {" +
            "            font-family: Helvetica, Arial, sans-serif;" +
            "            text-align: center;" +
            "        }" +
            "" +
            "        p, ul {" +
            "            margin-bottom: 10px;" +
            "            font-weight: normal;" +
            "            font-size:14px;" +
            "            line-height:1.6;" +
            "        }" +
            "        p.lead {" +
            "            font-size:17px;" +
            "        }" +
            "        p.last {" +
            "            margin-bottom:0px;" +
            "        }" +
            "" +
            "        ul li {" +
            "            margin-left:5px;" +
            "            list-style-position: inside;" +
            "        }" +
            "" +
            "        a {" +
            "            color: #747474;" +
            "            text-decoration: none;" +
            "        }" +
            "" +
            "        a img {" +
            "            border: none;" +
            "        }" +
            "" +
            "        /* -------------------------------------" +
            "            ELEMENTS" +
            "        ------------------------------------- */" +
            "" +
            "" +
            "        table.head-wrap {" +
            "            width: 100%;" +
            "            margin: 0 auto;" +
            "            background-color: #f9f8f8;" +
            "            border-bottom: 1px solid #d8d8d8;" +
            "        }" +
            "" +
            "        .head-wrap * {" +
            "            margin: 0;" +
            "            padding: 0;" +
            "        }" +
            "" +
            "        .header-background {" +
            "            background: repeat-x url(https://www.filepicker.io/api/file/wUGKTIOZTDqV2oJx5NCh) left bottom;" +
            "        }" +
            "" +
            "" +
            "        .header {" +
            "            height: 42px;" +
            "        }" +
            "        .header .content {" +
            "            padding: 0;" +
            "        }" +
            "        .header .brand {" +
            "            font-size: 16px;" +
            "            line-height: 42px;" +
            "            font-weight: bold;" +
            "        }" +
            "        .header .brand a {" +
            "            color: #464646;" +
            "        }" +
            "" +
            "" +
            "" +
            "" +
            "" +
            "        table.body-wrap {" +
            "            width: 505px;" +
            "            margin: 0 auto;" +
            "            background-color: #ffffff;" +
            "        }" +
            "" +
            "" +
            "        .soapbox .soapbox-title {" +
            "            font-size: 30px;" +
            "            color: #464646;" +
            "            padding-top: 25px;" +
            "            padding-bottom: 28px;" +
            "        }" +
            "" +
            "        .content .status {" +
            "            width: 90%;" +
            "        }" +
            "" +
            "        .status {" +
            "            border-collapse: collapse;" +
            "            margin-left: 15px;" +
            "            color: #656565;" +
            "        }" +
            "        .status .status-cell {" +
            "            border: 1px solid #b3b3b3;" +
            "            height: 50px;" +
            "            font-size: 16px;" +
            "            line-height: 23px;" +
            "        }" +
            "        .status .status-cell.success," +
            "        .status .status-cell.active {" +
            "            height: 65px;" +
            "        }" +
            "        .status .status-cell.success {" +
            "            background: #f2ffeb;" +
            "            color: #51da42;" +
            "            font-size: 15px;" +
            "        }" +
            "" +
            "        .status .status-cell.error {" +
            "            background: #ffbeb0;" +
            "            color: #da261c;" +
            "            font-size: 15px;" +
            "        }" +
            "        .status .status-cell.active {" +
            "            background: #fffde0;" +
            "            width: 135px;" +
            "        }" +
            "        .status .status-image {" +
            "            vertical-align: text-bottom;" +
            "        }" +
            "" +
            "" +
            "        .body .body-padded," +
            "        .body .body-padding {" +
            "            padding-top: 34px;" +
            "        }" +
            "        .body .body-padding {" +
            "            width: 41px;" +
            "        }" +
            "        .body-padded," +
            "        .body-title {" +
            "            text-align: left;" +
            "        }" +
            "        .body .body-title {" +
            "            font-weight: bold;" +
            "            font-size: 17px;" +
            "            color: #464646;" +
            "        }" +
            "        .body .body-text .body-text-cell {" +
            "            text-align: left;" +
            "            font-size: 14px;" +
            "            line-height: 1.6;" +
            "            padding: 9px 0 17px;" +
            "        }" +
            "        .body .body-signature-block .body-signature-cell {" +
            "            padding: 25px 0 30px;" +
            "            text-align: left;" +
            "        }" +
            "        .body .body-signature {" +
            "            font-family: \"Comic Sans MS\", Textile, cursive;" +
            "            font-weight: bold;" +
            "        }" +
            "" +
            "" +
            "" +
            "        .footer-wrap {" +
            "            width: 100%;" +
            "            margin: 0 auto;" +
            "            clear: both !important;" +
            "            background-color: #e5e5e5;" +
            "            border-top: 1px solid #b3b3b3;" +
            "            font-size: 12px;" +
            "            color: #656565;" +
            "            line-height: 30px;" +
            "        }" +
            "        .footer-wrap td {" +
            "            padding: 14px 0;" +
            "        }" +
            "        .footer-wrap td .content {" +
            "            padding: 0;" +
            "        }" +
            "        .footer-wrap td .footer-lead {" +
            "            font-size: 14px;" +
            "        }" +
            "        .footer-wrap td .footer-lead a {" +
            "            font-size: 14px;" +
            "            font-weight: bold;" +
            "            color: #535353;" +
            "        }" +
            "        .footer-wrap td a {" +
            "            font-size: 12px;" +
            "            color: #656565;" +
            "        }" +
            "        .footer-wrap td a.last {" +
            "            margin-right: 0;" +
            "        }" +
            "        .footer-wrap .footer-group {" +
            "            display: inline-block;" +
            "        }" +
            "" +
            "" +
            "" +
            "        /* ---------------------------------------------------" +
            "            RESPONSIVENESS" +
            "        ------------------------------------------------------ */" +
            "" +
            "        /* Set a max-width, and make it display as block so it will automatically stretch to that width, but will also shrink down on a phone or something */" +
            "        .container {" +
            "            display: block !important;" +
            "            max-width: 505px !important;" +
            "            clear: both !important;" +
            "        }" +
            "" +
            "        /* This should also be a block element, so that it will fill 100% of the .container */" +
            "        .content {" +
            "            padding: 0;" +
            "            max-width: 505px;" +
            "            margin: 0 auto;" +
            "            display: block;" +
            "        }" +
            "" +
            "        /* Let's make sure tables in the content area are 100% wide */" +
            "        .content table {" +
            "            width: 100%;" +
            "        }" +
            "" +
            "" +
            "        /* Be sure to place a .clear element after each set of columns, just to be safe */" +
            "        .clear {" +
            "            display: block;" +
            "            clear: both;" +
            "        }" +
            "" +
            "        table.full-width-gmail-android {" +
            "            width: 100% !important;" +
            "        }" +
            "" +
            "    </style>" +
            "    <style type=\"text/css\" media=\"only screen\">" +
            "" +
            "" +
            "        /* -------------------------------------------" +
            "            PHONE" +
            "            For clients that support media queries." +
            "            Nothing fancy." +
            "        -------------------------------------------- */" +
            "        @media only screen {" +
            "" +
            "            table[class=\"head-wrap\"]," +
            "            table[class=\"body-wrap\"]," +
            "            table[class*=\"footer-wrap\"] {" +
            "                width: 100% !important;" +
            "            }" +
            "" +
            "            td[class*=\"container\"] {" +
            "                margin: 0 auto !important;" +
            "            }" +
            "        }" +
            "" +
            "        @media only screen and (max-width: 505px) {" +
            "" +
            "            *[class*=\"w320\"] {" +
            "                width: 320px !important;" +
            "            }" +
            "" +
            "            table[class=\"status\"] td[class*=\"status-cell\"]," +
            "            table[class=\"status\"] td[class*=\"status-cell\"].active {" +
            "                display: block !important;" +
            "                width: auto !important;" +
            "                height: auto !important;" +
            "                padding: 15px !important;" +
            "                font-size: 18px !important;" +
            "            }" +
            "" +
            "        }" +
            "    </style>" +
            "</head>" +
            "" +
            "<body bgcolor=\"#ffffff\">" +
            "" +
            "<div align=\"center\">" +
            "    <table class=\"head-wrap w320 full-width-gmail-android\" bgcolor=\"#f9f8f8\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">" +
            "        <tr>" +
            "            <td background=\"https://www.filepicker.io/api/file/UOesoVZTFObSHCgUDygC\" bgcolor=\"#ffffff\" width=\"100%\" height=\"8\" valign=\"top\">" +
            "                <!--[if gte mso 9]>" +
            "                <v:rect xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"true\" stroke=\"false\" style=\"mso-width-percent:1000;height:8px;\">" +
            "                    <v:fill type=\"tile\" src=\"https://www.filepicker.io/api/file/UOesoVZTFObSHCgUDygC\" color=\"#ffffff\" />" +
            "                    <v:textbox inset=\"0,0,0,0\">" +
            "                <![endif]-->" +
            "                <div height=\"8\">" +
            "                </div>" +
            "                <!--[if gte mso 9]>" +
            "                </v:textbox>" +
            "                </v:rect>" +
            "                <![endif]-->" +
            "            </td>" +
            "        </tr>" +
            "        <tr class=\"header-background\">" +
            "            <td class=\"header container\" align=\"center\">" +
            "                <div class=\"content\">" +
            "            <span class=\"brand\">" +
            "              <a href=\"#\">" +
            "                Project SilverSpoon" +
            "              </a>" +
            "            </span>" +
            "                </div>" +
            "            </td>" +
            "        </tr>" +
            "    </table>" +
            "" +
            "    <table class=\"body-wrap w320\">" +
            "        <tr>" +
            "            <td></td>" +
            "            <td class=\"container\">" +
            "                <div class=\"content\">" +
            "                    <table cellspacing=\"0\">" +
            "                        <tr>" +
            "                            <td>" +
            "                                <table class=\"soapbox\">" +
            "                                    <tr>" +
            "                                        <td class=\"soapbox-title\">Account activation failed</td>" +
            "                                    </tr>" +
            "                                </table>" +
            "                                <table class=\"status\" bgcolor=\"#fffeea\" cellspacing=\"0\">" +
            "                                    <tr>" +
            "                                        <td class=\"status-cell error\">" +
            "                                            ✗&nbsp;" +
            "                                            <b>Your account hash is invalid.</b>" +
            "                                        </td>" +
            "                                    </tr>" +
            "                                </table>" +
            "" +
            "                                <table class=\"body\">" +
            "                                    <tr>" +
            "                                        <td class=\"body-padding\"></td>" +
            "                                        <td class=\"body-padded\">" +
            "                                            <div class=\"body-title\">What next?</div>" +
            "                                            <table class=\"body-text\">" +
            "                                                <tr>" +
            "                                                    <td class=\"body-text-cell\">" +
            "                                                        <p>Head back to the email we sent you and retry. Kindly use the button provided so as not to tamper with the account hash.</p>" +
            "                                                        <p>If this fails, just <a style=\"color: blue; text-decoration: underline\" href=\"mailto:support@silverspoon.com?subject=Invalid Account Hash\">Email us</a>" +
            "                                                         at <span style=\"color: #0f9d58; text-decoration: underline\">support@silverspoon.com</span></p>" +
            "                                                    </td>" +
            "                                                </tr>" +
            "                                            </table>" +
            "                                        </td>" +
            "                                    </tr>" +
            "                                </table>" +
            "" +
            "                                <table class=\"body\">" +
            "                                    <tr>" +
            "                                        <td class=\"body-padding\"></td>" +
            "                                        <td>" +
            "                                            <table class=\"body-signature-block\">" +
            "                                                <tr>" +
            "                                                    <td class=\"body-signature-cell\" style=\"padding-top: 0\">" +
            "                                                        <p>Thanks so much,</p>" +
            "                                                        <p class=\"body-signature\">Project SilverSpoon</p>" +
            "                                                    </td>" +
            "                                                </tr>" +
            "                                            </table>" +
            "                                        </td>" +
            "                                        <td class=\"body-padding\"></td>" +
            "                                    </tr>" +
            "                                </table>" +
            "                            </td>" +
            "                        </tr>" +
            "                    </table>" +
            "                </div>" +
            "            </td>" +
            "            <td></td>" +
            "        </tr>" +
            "    </table>" +
            "" +
            "    <table class=\"footer-wrap w320 full-width-gmail-android\" bgcolor=\"#e5e5e5\">" +
            "        <tr>" +
            "            <td>" +
            "                <div class=\"content footer-lead\">" +
            "                    <a href=\"#\"><b>Get in touch</b></a> if you have any questions or feedback" +
            "                </div>" +
            "            </td>" +
            "        </tr>" +
            "    </table>" +
            "    <table class=\"footer-wrap w320 full-width-gmail-android\" bgcolor=\"#e5e5e5\">" +
            "        <tr>" +
            "            <td>" +
            "                <div class=\"content\">" +
            "                    <a href=\"#\">Contact Us</a>&nbsp;&nbsp;|&nbsp;&nbsp;" +
            "                    <span class=\"footer-group\">" +
            "              <a href=\"#\">Facebook</a>&nbsp;&nbsp;|&nbsp;&nbsp;" +
            "              <a href=\"#\">Twitter</a>&nbsp;&nbsp;|&nbsp;&nbsp;" +
            "              <a href=\"#\">Support</a>" +
            "            </span>" +
            "                </div>" +
            "            </td>" +
            "        </tr>" +
            "    </table>" +
            "</div>" +
            "" +
            "</body>" +
            "</html>";
    
    public static String passwordResetEmail = "" +
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
            "<head>" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" +
            "    <meta name=\"viewport\" content=\"width=device-width\" />" +
            "    <title>Password Reset</title>" +
            "    <style type=\"text/css\">" +
            "" +
            "        * {" +
            "            margin:0;" +
            "            padding:0;" +
            "            font-family: Helvetica, Arial, sans-serif;" +
            "        }" +
            "" +
            "        img {" +
            "            max-width: 100%;" +
            "            outline: none;" +
            "            text-decoration: none;" +
            "            -ms-interpolation-mode: bicubic;" +
            "        }" +
            "" +
            "        .image-fix {" +
            "            display:block;" +
            "        }" +
            "" +
            "        .collapse {" +
            "            margin:0;" +
            "            padding:0;" +
            "        }" +
            "" +
            "        body {" +
            "            -webkit-font-smoothing:antialiased;" +
            "            -webkit-text-size-adjust:none;" +
            "            width: 100%!important;" +
            "            height: 100%;" +
            "            text-align: center;" +
            "            color: #747474;" +
            "            background-color: #ffffff;" +
            "        }" +
            "" +
            "        h1,h2,h3,h4,h5,h6 {" +
            "            font-family: Helvetica, Arial, sans-serif;" +
            "            line-height: 1.1;" +
            "        }" +
            "" +
            "        h1 small, h2 small, h3 small, h4 small, h5 small, h6 small {" +
            "            font-size: 60%;" +
            "            line-height: 0;" +
            "            text-transform: none;" +
            "        }" +
            "" +
            "        h1 {" +
            "            font-weight:200;" +
            "            font-size: 44px;" +
            "        }" +
            "" +
            "        h2 {" +
            "            font-weight:200;" +
            "            font-size: 32px;" +
            "            margin-bottom: 14px;" +
            "        }" +
            "" +
            "        h3 {" +
            "            font-weight:500;" +
            "            font-size: 27px;" +
            "        }" +
            "" +
            "        h4 {" +
            "            font-weight:500;" +
            "            font-size: 23px;" +
            "        }" +
            "" +
            "        h5 {" +
            "            font-weight:900;" +
            "            font-size: 17px;" +
            "        }" +
            "" +
            "        h6 {" +
            "            font-weight:900;" +
            "            font-size: 14px;" +
            "            text-transform: uppercase;" +
            "        }" +
            "" +
            "        .collapse {" +
            "            margin:0!important;" +
            "        }" +
            "" +
            "        td, div {" +
            "            font-family: Helvetica, Arial, sans-serif;" +
            "            text-align: center;" +
            "        }" +
            "" +
            "        p, ul {" +
            "            margin-bottom: 10px;" +
            "            font-weight: normal;" +
            "            font-size:14px;" +
            "            line-height:1.6;" +
            "        }" +
            "" +
            "        p.lead {" +
            "            font-size:17px;" +
            "        }" +
            "" +
            "        p.last {" +
            "            margin-bottom:0px;" +
            "        }" +
            "" +
            "        ul li {" +
            "            margin-left:5px;" +
            "            list-style-position: inside;" +
            "        }" +
            "" +
            "        a {" +
            "            color: #747474;" +
            "            text-decoration: none;" +
            "        }" +
            "" +
            "        a img {" +
            "            border: none;" +
            "        }" +
            "" +
            "        .head-wrap {" +
            "            width: 100%;" +
            "            margin: 0 auto;" +
            "            background-color: #f9f8f8;" +
            "            border-bottom: 1px solid #d8d8d8;" +
            "        }" +
            "" +
            "        .head-wrap * {" +
            "            margin: 0;" +
            "            padding: 0;" +
            "        }" +
            "" +
            "        .header-background {" +
            "            background: repeat-x url(https://www.filepicker.io/api/file/wUGKTIOZTDqV2oJx5NCh) left bottom;" +
            "        }" +
            "" +
            "        .header {" +
            "            height: 42px;" +
            "        }" +
            "" +
            "        .header .content {" +
            "            padding: 0;" +
            "        }" +
            "" +
            "        .header .brand {" +
            "            font-size: 16px;" +
            "            line-height: 42px;" +
            "            font-weight: bold;" +
            "        }" +
            "" +
            "        .header .brand a {" +
            "            color: #464646;" +
            "        }" +
            "" +
            "        .body-wrap {" +
            "            width: 505px;" +
            "            margin: 0 auto;" +
            "            background-color: #ffffff;" +
            "        }" +
            "" +
            "        .soapbox .soapbox-title {" +
            "            font-size: 30px;" +
            "            color: #464646;" +
            "            padding-top: 25px;" +
            "            padding-bottom: 28px;" +
            "        }" +
            "" +
            "        .content .status-container.single .status-padding {" +
            "            width: 80px;" +
            "        }" +
            "" +
            "        .content .status {" +
            "            width: 100%;" +
            "        }" +
            "" +
            "        .content .status-container.single .status {" +
            "            width: 300px;" +
            "        }" +
            "" +
            "        .status {" +
            "            border-collapse: collapse;" +
            "            margin-left: 15px;" +
            "            color: #656565;" +
            "        }" +
            "" +
            "        .status .status-cell {" +
            "            border: 1px solid #b3b3b3;" +
            "            height: 50px;" +
            "            padding-left: 15px;" +
            "            padding-right: 15px;" +
            "        }" +
            "" +
            "        .status .status-cell.success," +
            "        .status .status-cell.active {" +
            "            height: 65px;" +
            "        }" +
            "" +
            "        .status .status-cell.success {" +
            "            background: #f2ffeb;" +
            "            color: #51da42;" +
            "        }" +
            "" +
            "        .status .status-cell.success .status-title {" +
            "            font-size: 15px;" +
            "        }" +
            "" +
            "        .status .status-cell.active {" +
            "            background: #fffde0;" +
            "            width: 135px;" +
            "        }" +
            "" +
            "        .status .status-title {" +
            "            font-size: 16px;" +
            "            font-weight: bold;" +
            "            line-height: 23px;" +
            "        }" +
            "" +
            "        .status .status-image {" +
            "            vertical-align: text-bottom;" +
            "        }" +
            "" +
            "        .body .body-padded," +
            "        .body .body-padding {" +
            "            padding-top: 34px;" +
            "        }" +
            "" +
            "        .body .body-padding {" +
            "            width: 41px;" +
            "        }" +
            "" +
            "        .body-padded," +
            "        .body-title {" +
            "            text-align: left;" +
            "        }" +
            "" +
            "        .body .body-title {" +
            "            font-weight: bold;" +
            "            font-size: 17px;" +
            "            color: #464646;" +
            "        }" +
            "" +
            "        .body .body-text .body-text-cell {" +
            "            text-align: left;" +
            "            font-size: 14px;" +
            "            line-height: 1.6;" +
            "            padding: 9px 0 17px;" +
            "        }" +
            "" +
            "        .body .body-signature-block .body-signature-cell {" +
            "            padding: 25px 0 30px;" +
            "            text-align: left;" +
            "        }" +
            "" +
            "        .body .body-signature {" +
            "            font-family: \"Comic Sans MS\", Textile, cursive;" +
            "            font-weight: bold;" +
            "        }" +
            "" +
            "        .footer-wrap {" +
            "            width: 100%;" +
            "            margin: 0 auto;" +
            "            clear: both !important;" +
            "            background-color: #e5e5e5;" +
            "            border-top: 1px solid #b3b3b3;" +
            "            font-size: 12px;" +
            "            color: #656565;" +
            "            line-height: 30px;" +
            "        }" +
            "" +
            "        .footer-wrap .container {" +
            "            padding: 14px 0;" +
            "        }" +
            "" +
            "        .footer-wrap .container .content {" +
            "            padding: 0;" +
            "        }" +
            "" +
            "        .footer-wrap .container .footer-lead {" +
            "            font-size: 14px;" +
            "        }" +
            "" +
            "        .footer-wrap .container .footer-lead a {" +
            "            font-size: 14px;" +
            "            font-weight: bold;" +
            "            color: #535353;" +
            "        }" +
            "" +
            "        .footer-wrap .container a {" +
            "            font-size: 12px;" +
            "            color: #656565;" +
            "        }" +
            "" +
            "        .footer-wrap .container a.last {" +
            "            margin-right: 0;" +
            "        }" +
            "" +
            "        .footer-wrap .footer-group {" +
            "            display: inline-block;" +
            "        }" +
            "" +
            "        .container {" +
            "            display: block !important;" +
            "            max-width: 505px !important;" +
            "            clear: both !important;" +
            "        }" +
            "" +
            "        .content {" +
            "            padding: 0;" +
            "            max-width: 505px;" +
            "            margin: 0 auto;" +
            "            display: block;" +
            "        }" +
            "" +
            "        .content table {" +
            "            width: 100%;" +
            "        }" +
            "" +
            "" +
            "        .clear {" +
            "            display: block;" +
            "            clear: both;" +
            "        }" +
            "" +
            "        table.full-width-gmail-android {" +
            "            width: 100% !important;" +
            "        }" +
            "" +
            "    </style>" +
            "" +
            "    <style type=\"text/css\" media=\"only screen\">" +
            "" +
            "        @media only screen {" +
            "" +
            "            table[class*=\"head-wrap\"]," +
            "            table[class*=\"body-wrap\"]," +
            "            table[class*=\"footer-wrap\"] {" +
            "                width: 100% !important;" +
            "            }" +
            "" +
            "            td[class*=\"container\"] {" +
            "                margin: 0 auto !important;" +
            "            }" +
            "" +
            "        }" +
            "" +
            "        @media only screen and (max-width: 505px) {" +
            "" +
            "            *[class*=\"w320\"] {" +
            "                width: 320px !important;" +
            "            }" +
            "" +
            "            table[class=\"status\"] td[class*=\"status-cell\"]," +
            "            table[class=\"status\"] td[class*=\"status-cell\"].active {" +
            "                display: block !important;" +
            "                width: auto !important;" +
            "            }" +
            "" +
            "            table[class=\"status-container single\"] table[class=\"status\"] {" +
            "                width: 270px !important;" +
            "                margin-left: 0;" +
            "            }" +
            "" +
            "            table[class=\"status\"] td[class*=\"status-cell\"]," +
            "            table[class=\"status\"] td[class*=\"status-cell\"].active," +
            "            table[class=\"status\"] td[class*=\"status-cell\"] [class*=\"status-title\"] {" +
            "                line-height: 65px !important;" +
            "                font-size: 18px !important;" +
            "            }" +
            "" +
            "            table[class=\"status-container single\"] table[class=\"status\"] td[class*=\"status-cell\"]," +
            "            table[class=\"status-container single\"] table[class=\"status\"] td[class*=\"status-cell\"].active," +
            "            table[class=\"status-container single\"] table[class=\"status\"] td[class*=\"status-cell\"] [class*=\"status-title\"] {" +
            "                line-height: 51px !important;" +
            "            }" +
            "" +
            "            table[class=\"status\"] td[class*=\"status-cell\"].active [class*=\"status-title\"] {" +
            "                display: inline !important;" +
            "            }" +
            "" +
            "        }" +
            "    </style>" +
            "</head>" +
            "" +
            "<body bgcolor=\"#ffffff\">" +
            "" +
            "<div align=\"center\">" +
            "    <table class=\"head-wrap w320 full-width-gmail-android\" bgcolor=\"#f9f8f8\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">" +
            "        <tr>" +
            "            <td background=\"https://www.filepicker.io/api/file/UOesoVZTFObSHCgUDygC\" bgcolor=\"#ffffff\" width=\"100%\" height=\"8\" valign=\"top\">" +
            "                <!--[if gte mso 9]>" +
            "                <v:rect xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"true\" stroke=\"false\" style=\"mso-width-percent:1000;height:8px;\">" +
            "                    <v:fill type=\"tile\" src=\"https://www.filepicker.io/api/file/UOesoVZTFObSHCgUDygC\" color=\"#ffffff\" />" +
            "                    <v:textbox inset=\"0,0,0,0\">" +
            "                <![endif]-->" +
            "                <div height=\"8\">" +
            "                </div>" +
            "                <!--[if gte mso 9]>" +
            "                </v:textbox>" +
            "                </v:rect>" +
            "                <![endif]-->" +
            "            </td>" +
            "        </tr>" +
            "        <tr class=\"header-background\">" +
            "            <td class=\"header container\" align=\"center\">" +
            "                <div class=\"content\">" +
            "            <span class=\"brand\">" +
            "              <a href=\"#\">" +
            "                Project SilverSpoon" +
            "              </a>" +
            "            </span>" +
            "                </div>" +
            "            </td>" +
            "        </tr>" +
            "    </table>" +
            "" +
            "    <table class=\"body-wrap w320\">" +
            "        <tr>" +
            "            <td></td>" +
            "            <td class=\"container\">" +
            "                <div class=\"content\">" +
            "                    <table cellspacing=\"0\">" +
            "                        <tr>" +
            "                            <td>" +
            "                                <table class=\"soapbox\">" +
            "                                    <tr>" +
            "                                        <td class=\"soapbox-title\">Password Reset</td>" +
            "                                    </tr>" +
            "                                </table>" +
            "                                <table class=\"body\">" +
            "                                    <tr>" +
            "                                        <td class=\"body-padding\"></td>" +
            "                                        <td class=\"body-padded\">" +
            "                                            <div class=\"body-title\">Forgot your password?</div>" +
            "                                            <table class=\"body-text\">" +
            "                                                <tr>" +
            "                                                    <td class=\"body-text-cell\">" +
            "                                                        Don't worry, we got you covered. Click on the link below to reset your password" +
            "                                                    </td>" +
            "                                                </tr>" +
            "                                            </table>" +
            "                                            <div style=\"text-align:left;\"><!--[if mso]>" +
            "                                                <v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"#\" style=\"height:38px;v-text-anchor:middle;width:190px;\" arcsize=\"11%\" strokecolor=\"#407429\" fill=\"t\">" +
            "                                                    <v:fill type=\"tile\" src=\"https://www.filepicker.io/api/file/N8GiNGsmT6mK6ORk00S7\" color=\"#41CC00\" />" +
            "                                                    <w:anchorlock/>" +
            "                                                    <center style=\"color:#ffffff;font-family:sans-serif;font-size:17px;font-weight:bold;\">Come on back</center>" +
            "                                                </v:roundrect>" +
            "                                                <![endif]--><a href=\"%(redirect)%\"" +
            "                                                               target=\"_blank\"" +
            "                                                               style=\"background-color:#41CC00;background-image:url(https://www.filepicker.io/api/file/N8GiNGsmT6mK6ORk00S7);border:1px solid #407429;border-radius:4px;color:#ffffff;display:inline-block;font-family:sans-serif;font-size:17px;font-weight:bold;text-shadow: -1px -1px #47A54B;line-height:38px;text-align:center;text-decoration:none;width:190px;-webkit-text-size-adjust:none;mso-hide:all;\">Reset Password</a></div>" +
            "                                            <table class=\"body-signature-block\">" +
            "                                                <tr>" +
            "                                                    <td class=\"body-signature-cell\">" +
            "                                                        <p>Sincerely,</p>" +
            "                                                        <p class=\"body-signature\">Project SilverSpoon</p>" +
            "                                                    </td>" +
            "                                                </tr>" +
            "                                            </table>" +
            "                                        </td>" +
            "                                        <td class=\"body-padding\"></td>" +
            "                                    </tr>" +
            "                                </table>" +
            "                            </td>" +
            "                        </tr>" +
            "                    </table>" +
            "                </div>" +
            "            </td>" +
            "            <td></td>" +
            "        </tr>" +
            "    </table>" +
            "" +
            "    <table class=\"footer-wrap w320 full-width-gmail-android\" bgcolor=\"#e5e5e5\">" +
            "        <tr>" +
            "            <td class=\"container\">" +
            "                <div class=\"content footer-lead\">" +
            "                    <a href=\"#\"><b>Get in touch</b></a> if you have any questions or feedback" +
            "                </div>" +
            "            </td>" +
            "        </tr>" +
            "    </table>" +
            "    <table class=\"footer-wrap w320 full-width-gmail-android\" bgcolor=\"#e5e5e5\">" +
            "        <tr>" +
            "            <td class=\"container\">" +
            "                <div class=\"content\">" +
            "                    <a href=\"#\">Contact Us</a>&nbsp;&nbsp;|&nbsp;&nbsp;" +
            "                    <span class=\"footer-group\">" +
            "              <a href=\"#\">Facebook</a>&nbsp;&nbsp;|&nbsp;&nbsp;" +
            "              <a href=\"#\">Twitter</a>&nbsp;&nbsp;|&nbsp;&nbsp;" +
            "              <a href=\"#\">Support</a>" +
            "            </span>" +
            "                </div>" +
            "            </td>" +
            "        </tr>" +
            "    </table>" +
            "</div>" +
            "" +
            "</body>" +
            "</html>";

}
