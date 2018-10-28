package club.cybet.endpoints.http_handlers.auth;

import club.cybet.domain.db.orm.AccessToken;
import club.cybet.domain.db.orm.UserAccount;
import club.cybet.repository.Repository;
import club.cybet.repository.UserAccountRepository;
import club.cybet.utils.Constants;
import club.cybet.utils.http.ScedarHttpHandler;
import club.cybet.utils.http.exceptions.ExceptionRepresentation;
import club.cybet.utils.security.Passwords;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

import java.util.Deque;
import java.util.List;

/**
 * sls-api (club.cybet.http_handlers.auth)
 * Created by: cybet
 * On: 30 Jun, 2018 6/30/18 12:11 PM
 **/
public class Authenticate extends ScedarHttpHandler {

    @Override
    @SuppressWarnings("unchecked")
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

        if(!isRequestValid(exchange)) return;

        String username = exchange.getQueryParameters().get("username").getFirst();
        String password = exchange.getQueryParameters().get("password").getFirst();
        String grantType = exchange.getQueryParameters().get("grant_type").getFirst().toLowerCase();

        try{
            username = getCredentials(username);

            UserAccount user = UserAccountRepository.getByUsernameFields(username);

            if(user == null) {
                send(exchange, new ExceptionRepresentation(
                        "Access denied",
                        exchange.getRequestURI(),
                        "Invalid credentials",
                        StatusCodes.FORBIDDEN,
                        exchange.getRequestMethod()
                ), StatusCodes.FORBIDDEN);
                return;
            }

            if(grantType.equals("password")){
                if(passwordAuth(getCredentials(password), user)){

                    String dp = Constants.getPublicAssetsServer();

                    List<String> dpNames = (List<String>)Repository.executeQuery(
                            Constants.getDpNameQuery(user.getId(), 1)
                    );

                    if(dpNames == null || dpNames.size() < 1) dp = "No display picture set";
                    else dp+=dpNames.get(0);

                    user.setDisplayPicture(dp);

                    AccessToken accessToken = (AccessToken) Repository
                            .save(new AccessToken(user.getId()).setUser(user)).getData();
                    if(accessToken != null){
                        send(exchange, accessToken, StatusCodes.OK);
                    }else throw new NullPointerException("Error generating access token");

                }else {
                    send(exchange, new ExceptionRepresentation(
                            "Access denied",
                            exchange.getRequestURI(),
                            "Invalid credentials",
                            StatusCodes.FORBIDDEN,
                            exchange.getRequestMethod()
                    ), StatusCodes.FORBIDDEN);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            send(exchange, new ExceptionRepresentation(
                    "Internal Server Error ("+e.getMessage()+")",
                    exchange.getRequestURI(),
                    "Error: "+e.getMessage()+". Caused by: "+e.getCause(),
                    StatusCodes.INTERNAL_SERVER_ERROR,
                    exchange.getRequestMethod()
            ), StatusCodes.INTERNAL_SERVER_ERROR);
        }
    }

    private String getCredentials(String credentials){
        //TODO: Look for universal encryption mode
        return credentials;
    }

    private boolean passwordAuth(String password, UserAccount user){
        return (Passwords.check(password, user.getUserPassword()));
    }

    private boolean isRequestValid(HttpServerExchange exchange){
        Deque<String> dUsername = exchange.getQueryParameters().get("username");
        Deque<String> dPassword = exchange.getQueryParameters().get("password");
        Deque<String> dGrantType = exchange.getQueryParameters().get("grant_type");

        if(dUsername == null || dUsername.getFirst().equals("")){
            send(exchange, new ExceptionRepresentation(
                    "Missing GET parameter",
                    exchange.getRequestURI(),
                    "GET parameter 'username' is required",
                    StatusCodes.BAD_REQUEST,
                    exchange.getRequestMethod()
            ), StatusCodes.BAD_REQUEST);
            return false;
        }

        if(dPassword == null || dPassword.getFirst().equals("")){
            send(exchange, new ExceptionRepresentation(
                    "Missing GET parameter",
                    exchange.getRequestURI(),
                    "GET parameter 'password' is required",
                    StatusCodes.BAD_REQUEST,
                    exchange.getRequestMethod()
            ), StatusCodes.BAD_REQUEST);
            return false;
        }

        if(dGrantType == null || dGrantType.getFirst().equals("")){
            send(exchange, new ExceptionRepresentation(
                    "Missing GET parameter",
                    exchange.getRequestURI(),
                    "GET parameter 'grant_type' is required",
                    StatusCodes.BAD_REQUEST,
                    exchange.getRequestMethod()
            ), StatusCodes.BAD_REQUEST);
            return false;
        }

        String grantType = dGrantType.getFirst().trim().toLowerCase();

        if(!(grantType.equals("password"))){
            send(exchange, new ExceptionRepresentation(
                    "Invalid grant_type",
                    exchange.getRequestURI(),
                    "grant_type '"+grantType+"' is invalid. Use 'password'",
                    StatusCodes.BAD_REQUEST,
                    exchange.getRequestMethod()
            ), StatusCodes.BAD_REQUEST);
            return false;
        }

        return true;
    }
}
