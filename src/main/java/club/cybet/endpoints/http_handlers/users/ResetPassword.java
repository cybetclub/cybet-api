package club.cybet.endpoints.http_handlers.users;

import club.cybet.domain.db.orm.UserAccount;
import club.cybet.repository.Repository;
import club.cybet.utils.Constants;
import club.cybet.utils.http.ScedarHttpHandler;
import club.cybet.utils.http.exceptions.ExceptionRepresentation;
import club.cybet.utils.security.Encryption;
import club.cybet.utils.security.Passwords;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

import java.util.Deque;

/**
 * cybet-api (club.cybet.endpoints.http_handlers.users)
 * Created by: cybet
 * On: 24 Oct, 2018 10/24/18 11:55 PM
 **/
public class ResetPassword extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

        String[] params = getParams(exchange);

        if (params[0].equals("0") || params[1].equals("0") || params[2].equals("0")) {
            send(exchange, new ExceptionRepresentation(
                    "All GET parameters 'id', 'hash' and 'password' are required",
                    exchange.getRequestURI(),
                    "Bad request.",
                    StatusCodes.BAD_REQUEST,
                    exchange.getRequestMethod()
            ), StatusCodes.BAD_REQUEST);
            return;
        }

        UserAccount user = (UserAccount) Repository.findById(UserAccount.class, Encryption.decrypto(params[0]));

        if (user == null) {
            send(exchange, new ExceptionRepresentation(
                    "User account not found",
                    exchange.getRequestURI(),
                    "User not found.",
                    StatusCodes.NOT_FOUND,
                    exchange.getRequestMethod()
            ), StatusCodes.NOT_FOUND);
            return;
        }

        String email = user.getEmailAddress();
        String password = user.getUserPassword();
        String userId = String.valueOf(user.getId());

        try {
            String[] userHash = Encryption.decrypto(params[1]).split(Constants.SKY_SPLITTER);

            if (
                    userHash[0].equals(password)
                            && userHash[1].equals(email)
                            && userHash[2].equals(userId)
                    ) {

                UserAccount updatedUser = new UserAccount();
                updatedUser.setActivated(user.getActivated());
                updatedUser.setCompletedKyc(user.getCompletedKyc());
                updatedUser.setUserPassword(Passwords.getHash(params[2]));

                Repository.update(user, updatedUser);

                send(exchange, new ExceptionRepresentation(
                        "Password reset successfully",
                        exchange.getRequestURI(),
                        "Password reset successfully.",
                        StatusCodes.OK,
                        exchange.getRequestMethod()
                ), StatusCodes.OK);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        send(exchange, new ExceptionRepresentation(
                "Error resetting password. Invalid user hash",
                exchange.getRequestURI(),
                "Error resetting password.",
                StatusCodes.INTERNAL_SERVER_ERROR,
                exchange.getRequestMethod()
        ), StatusCodes.INTERNAL_SERVER_ERROR);
    }

    private String [] getParams(HttpServerExchange exchange){
        String [] params = new String[3];
        params[0] = "0"; params[1] = "0"; params[2] = "0";

        Deque<String> id = exchange.getQueryParameters().get("id");
        Deque<String> hash = exchange.getQueryParameters().get("hash");
        Deque<String> password = exchange.getQueryParameters().get("password");

        if(id != null && !id.getFirst().equals(""))
            params[0] = id.getFirst();

        if(hash != null && !hash.getFirst().equals(""))
            params[1] = hash.getFirst();

        if(password != null && !password.getFirst().equals(""))
            params[2] = password.getFirst();

        return params;
    }
}
