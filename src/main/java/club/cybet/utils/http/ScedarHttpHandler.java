package club.cybet.utils.http;

import club.cybet.domain.db.enums.UserTypes;
import club.cybet.domain.db.orm.AccessToken;
import club.cybet.domain.db.orm.UserAccount;
import club.cybet.repository.AccessTokenRepository;
import club.cybet.repository.Repository;
import club.cybet.utils.Constants;
import club.cybet.utils.http.exceptions.ExceptionRepresentation;
import club.cybet.utils.memory.JvmManager;
import club.cybet.utils.security.Encryption;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.util.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * sls-api (club.cybet.http_handlers)
 * Created by: cybet
 * On: 30 Jun, 2018 6/30/18 12:55 PM
 **/
public class ScedarHttpHandler implements HttpHandler {

    protected boolean requestIsNotAuthentic(HttpServerExchange exchange, UserTypes... requiredUserType){
        HeaderValues authorizationHeader = exchange.getRequestHeaders()
                .get("Authorization");

        if(authorizationHeader == null){
            send(exchange, new ExceptionRepresentation(
                    "Missing HEADER 'Authorization'",
                    exchange.getRequestURI(),
                    "HEADER 'Authorization' is required",
                    StatusCodes.FORBIDDEN,
                    exchange.getRequestMethod()
            ), StatusCodes.FORBIDDEN);
            return true;
        }else{
            String accessToken = authorizationHeader.getFirst()
                    .replace("Bearer ", "");
            AccessToken aT = AccessTokenRepository.validate(accessToken);

            if(aT != null) {
                setRequestContext(exchange, accessToken);

                UserTypes userType = UserTypes.valueOf(
                        exchange.getQueryParameters()
                        .get(Constants.REQ_USER_TYPE).getFirst()
                        .replaceAll(" ", "_"));

                if(hasSufficientRights(userType, requiredUserType)){
                    return false;
                }else{
                    forbidProcession(exchange, requiredUserType);
                    return true;
                }

            }
            else {
                send(exchange, new ExceptionRepresentation(
                        "Invalid credentials",
                        exchange.getRequestURI(),
                        "Expired/corrupt access token",
                        StatusCodes.FORBIDDEN,
                        exchange.getRequestMethod()
                ), StatusCodes.FORBIDDEN);
                return true;
            }
        }
    }

    protected Long getAuthenticatedUserId(HttpServerExchange exchange){
        //Get authenticated user id
        return Long.parseLong(exchange.getQueryParameters()
                .get(Constants.REQ_ID).getFirst());
    }

    protected boolean userIsNotPrivilaged(HttpServerExchange exchange, UserTypes... userTypes){

        boolean isPrivilegedUserType = false;

        if(exchange.getQueryParameters()
                .get(Constants.REQ_USER_TYPE).getFirst().equals(UserTypes.ROOT.value())) return false;

        for (UserTypes userType : userTypes){
            isPrivilegedUserType = (isPrivilegedUserType || userType.value().equals(exchange.getQueryParameters()
                    .get(Constants.REQ_USER_TYPE).getFirst()));
        }

        if(!isPrivilegedUserType){
            send(exchange, new ExceptionRepresentation(
                            "Access denied.",
                            exchange.getRequestURI(),
                            "Your account requires elevation" +
                                    "Any of the following account types is required. ["+ getEnumString(userTypes) +"]",
                            StatusCodes.FORBIDDEN,
                            exchange.getRequestMethod()),
                    StatusCodes.FORBIDDEN);
            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private void setRequestContext(HttpServerExchange exchange, String accessToken){
        List<Object[]> accountType = (List<Object[]>)
                Repository.executeQuery(
                        Constants.getAccTypeQuery(accessToken));

        assert accountType != null;
        exchange.addQueryParam(Constants.REQ_ID,
                String.valueOf(accountType.get(0)[0]));
        exchange.addQueryParam(Constants.REQ_USER_TYPE,
                String.valueOf(accountType.get(0)[1]));
    }

    private boolean hasSufficientRights(UserTypes userType, UserTypes... requiredUserTypes){

        if(userType == UserTypes.ROOT) return true;
        if(requiredUserTypes.length == 0) return true;

        return getEnumString(requiredUserTypes).contains(userType.value());

    }

    protected HashMap<String, String> getPathVars(HttpServerExchange exchange, String... pathVarIds){
        PathTemplateMatch pathMatch =
                exchange.getAttachment(PathTemplateMatch.ATTACHMENT_KEY);

        HashMap<String, String> pathVars = new HashMap<>(pathVarIds.length);

        for (String pathVarId : pathVarIds){
            pathVars.put(pathVarId, pathMatch.getParameters().get(pathVarId));
        }
        JvmManager.gc(pathMatch);
        return pathVars;
    }

    protected String getPathVar(HttpServerExchange exchange, String pathVarId){
        PathTemplateMatch pathMatch =
                exchange.getAttachment(PathTemplateMatch.ATTACHMENT_KEY);
        String pv = pathMatch.getParameters().get(pathVarId);
        JvmManager.gc(pathMatch);
        return pv;
    }

    protected String getQueryParam(HttpServerExchange exchange, String key){
        Deque<String> param = exchange.getQueryParameters().get(key);
        String paramStr = null;

        if(param != null && !param.getFirst().equals(""))
            paramStr = param.getFirst();

        JvmManager.gc(param);

        return paramStr;
    }

    protected HashMap<String, String> getQueryParams(HttpServerExchange exchange, String... keys){

        HashMap<String, String> params = new HashMap<>();
        Deque<String> param = null;

        for (String key: keys){
            param = exchange.getQueryParameters().get(key);

            if(param != null && !param.getFirst().equals(""))
                params.put(key, param.getFirst());
        }

        JvmManager.gc(param);

        return params;
    }

    protected List<List<String>> getFilterData(HttpServerExchange exchange, Class clazz){

        List<List<String>> filterData = new ArrayList<>(3);
        List<String> errors = new ArrayList<>();
        String[] filterProps = new String[3];
        boolean hasError = false;

        filterData.add(0, new ArrayList<>(exchange.getQueryParameters()
                .getOrDefault("filter", Constants.filter)));
        filterData.add(1, new ArrayList<>(exchange.getQueryParameters()
                .getOrDefault("strategy", Constants.strategy)));
        filterData.add(2, new ArrayList<>(exchange.getQueryParameters()
                .getOrDefault("fields", Constants.fields)));

        String[] fields = filterData.get(2).get(0).split(",");

        if(!(filterData.get(1).get(0).toLowerCase().trim().equals("or") ||
                filterData.get(1).get(0).toLowerCase().trim().equals("and"))){
            hasError = true;
            errors.add("Strategy '"+filterData.get(1).get(0)+"' is invalid (available strategies -> or,and)");
        }

        for (String field : fields){

            if(field.equals("*")) break;

            try{
                Field f;

                switch (clazz.getSimpleName()) {
                    case "MeWallets":
                        f = UserAccount.class.getDeclaredField(field.trim());
                        break;
                    default:
                        f = clazz.getDeclaredField(field.trim());
                        break;
                }

                if(f.getName().equals("userPassword")) throw new NoSuchFieldException();
                if(f.getName().equals("active")) throw new NoSuchFieldException();

            }catch (NoSuchFieldException nsfe){
                hasError = true;
                errors.add("Field '"+field+"' does not exist (maybe retry using camelcase)");
            }
        }

        for (String filter : filterData.get(0)){

            if(filter.equals("*")) break;

            filterProps = filter.split(":");

            if(filterProps.length < 3){
                hasError = true;
                errors.add("Filter set '"+filter+"' is invalid (use format 'field:relation:queryValue')");
                continue;
            }

            try{
                Field f;

                switch (clazz.getSimpleName()) {
                    case "MeWallets":
                        f = UserAccount.class.getDeclaredField(filterProps[0].trim());
                        break;
                    default:
                        f = clazz.getDeclaredField(filterProps[0].trim());
                        break;
                }

                if(f.getName().equals("userPassword")) throw new NoSuchFieldException();
                if(f.getName().equals("resetPassword")) throw new NoSuchFieldException();
                if(f.getName().equals("active")) throw new NoSuchFieldException();
                if(f.getName().equals("systemProtected")) throw new NoSuchFieldException();

                if(clazz.getSimpleName().equals("Attachment")){
                    if(f.getName().equals("userAccountId")) throw new NoSuchFieldException();
                }

            }catch (NoSuchFieldException nsfe){
                hasError = true;
                errors.add("Field '"+filterProps[0]+"' does not exist or is used in URI path (NB:- Field names are case sensitive)");
            }

            if(!Constants.filterRelations.contains(filterProps[1].toLowerCase().trim())){
                hasError = true;
                errors.add("Relation '"+filterProps[1]+"' does not exist (see supported relations at URI:- /api/field-relations)");
            }

            if(filterProps[2].trim().equals("")){
                hasError = true;
                errors.add("No query value for filter '"+filter+"'");
            }
        }

        if(hasError){
            filterData.get(0).add(0, "ERROR");
            filterData.add(1, errors);

            JvmManager.gc(filterProps, errors, hasError, fields);

            return filterData;
        }
        JvmManager.gc(filterProps, errors, hasError, fields);
        return filterData;
    }

    protected int[] getPageAndPageSize(HttpServerExchange exchange){

        int[] pageAndPageSize = new int[] {1, 10};

        Deque<String> page = exchange.getQueryParameters().get("page");
        Deque<String> pageSize = exchange.getQueryParameters().get("pageSize");

        if(page != null){
            try{
                pageAndPageSize[0] = Integer.parseInt(page.getFirst());
            } catch (Exception ignore){

            }
        }

        if(pageSize != null){
            try{
                pageAndPageSize[1] = Integer.parseInt(pageSize.getFirst());
            } catch (Exception ignore){

            }
        }

        JvmManager.gc(page, pageSize);

        return pageAndPageSize;

    }

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange)
            throws Exception {

    }

    private void forbidProcession(HttpServerExchange exchange, UserTypes... userTypes){
        send(exchange, new ExceptionRepresentation(
                "Access denied.",
                exchange.getRequestURI(),
                "You need one of these user account types ["+getEnumString(userTypes)+"] to perform this request. ",
                StatusCodes.FORBIDDEN,
                exchange.getRequestMethod()
        ), StatusCodes.FORBIDDEN);
    }



    protected void send(HttpServerExchange exchange, String data, Integer status, String contentType){
        exchange.setStatusCode(status);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, contentType);
        exchange.getResponseSender().send(data);
    }

    protected void send(HttpServerExchange exchange, Object data, Integer status){
        exchange.setStatusCode(status);

        String contentType = determineAccept(exchange);
        ObjectMapper objectMapper = null;

        if(contentType.equals(Constants.applicationJson)){
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, Constants.applicationJson);
            try {
                objectMapper = getResponseObjectMapper(exchange);
                exchange.getResponseSender().send(objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(data));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else {
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, Constants.applicationXml);
            try {
                objectMapper = getResponseObjectMapper(exchange);
                exchange.getResponseSender().send(objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(data));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        JvmManager.gc(objectMapper);
    }

    private String getEnumString(UserTypes... userTypes){
        String[] strPerms = new String[userTypes.length];

        for (int i = 0; i < userTypes.length ; i++) {
            strPerms[i] = userTypes[i].value();
        }

        return String.join(", ", strPerms);
    }

    public static String toJson(Object obj){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String objStr = objectMapper.writeValueAsString(obj);
            JvmManager.gc(obj, objectMapper);
            return objStr;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Object toHashMap(String objStr, TypeReference T){
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = new HashMap<>();
        try {
            map = objectMapper.readValue(objStr, T);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JvmManager.gc(objectMapper, objStr);
        return map;
    }

    @SuppressWarnings("Duplicates")
    protected void send(HttpServerExchange exchange, String exposedPath){
        String rawPath = Encryption.decrypto(exposedPath);

        File file = new File(rawPath);
        OutputStream outStream = null;
        FileInputStream inputStream = null;
        int BUFFER_SIZE = 1024 * 100;
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        if(file.exists()){
            String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/octet-stream");
            exchange.getResponseHeaders().put(Headers.CONTENT_DISPOSITION, headerValue);

            try {
                outStream = exchange.getOutputStream();
                inputStream = new FileInputStream(file);

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();
                outStream.flush();
                outStream.close();
            } catch(IOException ioExObj) {
                ioExObj.printStackTrace();
                send(exchange, new ExceptionRepresentation(
                        "This may be due to file permissions. If this is a public attachment, " +
                                "try accessing it via '"+Constants.getPublicAssetsServer()+file.getName()+"'",
                        exchange.getRequestURI(),
                        "Exception While Performing The I/O Operation",
                        StatusCodes.INTERNAL_SERVER_ERROR,
                        exchange.getRequestMethod()
                ).setErrors(Collections.singletonList(Constants.getPublicAssetsServer() + file.getName()))
                        , StatusCodes.INTERNAL_SERVER_ERROR);
            } finally {
                try{
                    if (inputStream != null) {
                        inputStream.close();
                    }

                    if(outStream != null){
                        outStream.flush();
                        outStream.close();
                    }
                } catch (IOException ignore){}
            }

        } else {
            send(exchange, new ExceptionRepresentation(
                    "File not found in server.",
                    exchange.getRequestURI(),
                    "File not found",
                    StatusCodes.NOT_FOUND,
                    exchange.getRequestMethod()
            ), StatusCodes.NOT_FOUND);
        }

        JvmManager.gc(rawPath, file, outStream, inputStream, BUFFER_SIZE, buffer, bytesRead);
    }

    @SuppressWarnings("unchecked")
    protected Object getBodyObject(HttpServerExchange exchange, Class clazz){

        try {
            String contentType = determineContentType(exchange);
            String body = getBody(exchange);

            ObjectMapper objectMapper = getObjectMapper(contentType);

            Object obj = objectMapper.readValue(body, clazz);
            JvmManager.gc(objectMapper);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            exchange.addQueryParam(Constants.MARSHALL_ERROR,e.getMessage());
            return null;
        }
    }

    private static String determineContentType(HttpServerExchange exchange){
        try{
            return determineAorCt(exchange.getRequestHeaders()
                    .get("Content-Type").getFirst());
        }catch (NullPointerException e){
            return determineAorCt(Constants.applicationJson);
        }
    }

    private static String determineAccept(HttpServerExchange exchange){
        try{
            return determineAorCt(exchange.getRequestHeaders()
                    .get("Accept").getFirst());
        }catch (NullPointerException e){
            return determineAorCt(Constants.applicationJson);
        }
    }

    private static String determineAorCt(String headerValue){
        switch (headerValue) {
            case Constants.applicationJson:
                return Constants.applicationJson;
            case Constants.applicationXml:
                return Constants.applicationXml;
            default:
                return Constants.applicationJson;
        }
    }

    public static ObjectMapper getRequestObjectMapper(HttpServerExchange exchange){
        return getObjectMapper(determineContentType(exchange));
    }

    private static ObjectMapper getResponseObjectMapper(HttpServerExchange exchange){
        return getObjectMapper(determineAccept(exchange));
    }

    private static ObjectMapper getObjectMapper(String contentType){
        if(contentType.equals(Constants.applicationXml)){
            JacksonXmlModule xmlModule = new JacksonXmlModule();
            xmlModule.setDefaultUseWrapper(false);
            return new XmlMapper(xmlModule);
        }else{
            return new ObjectMapper();
        }
    }

    protected void printRequestInfo(HttpServerExchange exchange){

        exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Origin"), "*");
        exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Methods"),
                "GET, POST, PUT, DELETE, PATCH, OPTIONS");

        String userAgentHeader = "UNKNOWN";
        try{
            userAgentHeader = exchange.getRequestHeaders().get("User-Agent").getFirst();
        }catch (NullPointerException ignore){}
        String user = userAgentHeader.toLowerCase();

        String os = "UNKNOWN";
        String browser = "UNKNOWN";

        // Determine OS
        if (user.contains("windows")) os = "Windows";
        else if (user.contains("linux")) os = "Linux";
        else if(user.contains("mac")) os = "Mac";
        else if(user.contains("x11")) os = "Unix";
        else if(user.contains("android")) os = "Android";
        else if(user.contains("iphone")) os = "IPhone";

        // Determine Browser
        if (user.contains("msie"))
        {
            String substring= userAgentHeader.substring(userAgentHeader.indexOf("MSIE")).split(";")[0];
            browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version"))
        {
            browser=(userAgentHeader.substring(userAgentHeader.indexOf("Safari")).split(" ")[0]).split("/")[0]+"-"+(userAgentHeader.substring(userAgentHeader.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if ( user.contains("opr") || user.contains("opera"))
        {
            if(user.contains("opera"))
                browser=(userAgentHeader.substring(userAgentHeader.indexOf("Opera")).split(" ")[0]).split("/")[0]+"-"+(userAgentHeader.substring(userAgentHeader.indexOf("Version")).split(" ")[0]).split("/")[1];
            else if(user.contains("opr"))
                browser=((userAgentHeader.substring(userAgentHeader.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
        } else if (user.contains("chrome"))
        {
            browser=(userAgentHeader.substring(userAgentHeader.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((user.contains("mozilla/7.0")) || (user.contains("netscape6"))  || (user.contains("mozilla/4.7")) || (user.contains("mozilla/4.78")) || (user.contains("mozilla/4.08")) || (user.contains("mozilla/3")) )
        {
            browser = "Netscape-?";

        } else if (user.contains("firefox"))
        {
            browser=(userAgentHeader.substring(userAgentHeader.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if(user.contains("rv"))
        {
            browser="IE-" + user.substring(user.indexOf("rv") + 3, user.indexOf(")"));
        }

        System.out.println("\n*********** REQUEST INFO ***********");
        System.out.println("Request URI: " + exchange.getRequestURI());
        System.out.println("Protocol: " + exchange.getProtocol());
        System.out.println("Request Method: " + exchange.getRequestMethod());
        try{
            System.out.println("Remote Address: " + exchange.getRequestHeaders().get("X-Real-IP").getFirst());
        }catch (NullPointerException e){
            System.out.println("Remote Address: "+exchange.getSourceAddress().getAddress().toString());
        }
        System.out.println("User-Agent: " + userAgentHeader);
        System.out.println("Remote OS: " + os);
        System.out.println("Remote Browser: " + browser);
        System.out.println("**************************************\n");
    }

    public static String getFormData(HttpServerExchange exchange, String key){
        FormData formData = exchange.getAttachment(FormDataParser.FORM_DATA);
        Deque<FormData.FormValue> formValueDeque = formData.get(key);
        String value = null;

        if(formValueDeque != null){
            value = formValueDeque.getFirst().getValue();
        }
        JvmManager.gc(formData, formValueDeque);
        return value;
    }

    public static HashMap<String, String> getFormData(HttpServerExchange exchange, String... keys){
        FormData formData = exchange.getAttachment(FormDataParser.FORM_DATA);
        HashMap<String, String> values = new HashMap<>();
        Deque<FormData.FormValue> formValueDeque = null;

        for(String key: keys){
            formValueDeque = formData.get(key);
            if(key != null){
                values.put(key, formValueDeque.getFirst().getValue());
            }
        }
        JvmManager.gc(formData, formValueDeque);
        return values;
    }

    private String getBody(HttpServerExchange exchange){
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();

        try {
            exchange.startBlocking();
            reader = new BufferedReader(new InputStreamReader(exchange.getInputStream()));

            String line;
            while((line = reader.readLine()) != null ) {
                builder.append( line );
            }
        } catch(IOException e) {
            e.printStackTrace( );
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch( IOException e ) {
                    e.printStackTrace();
                }
            }
            JvmManager.gc(reader, builder);
        }

        String body = builder.toString();
        JvmManager.gc(reader, builder);

        return body;
    }
}
