package club.cybet.endpoints.http_handlers.users;

import club.cybet.domain.db.orm.UserAccount;
import club.cybet.repository.Repository;
import club.cybet.rstatic.portal.PortalPkgLocator;
import club.cybet.utils.Constants;
import club.cybet.utils.http.ScedarHttpHandler;
import club.cybet.utils.security.Encryption;
import io.undertow.Handlers;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.util.StatusCodes;

import java.util.Deque;

/**
 * cybet-api (club.cybet.endpoints.http_handlers.users)
 * Created by: cybet
 * On: 24 Oct, 2018 10/24/18 7:04 PM
 **/
public class ActivateAccount extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

        String [] params = getParams(exchange);

        if(params[0].equals("0") || params[1].equals("0")){
            send(exchange, Constants.accountActivationFailedHtml,
                    StatusCodes.OK, "text/html");
            return;
        }

        UserAccount user = (UserAccount) Repository.findById(UserAccount.class, Encryption.decrypto(params[0]));

        if(user == null){
            send(exchange, Constants.accountActivationFailedHtml,
                    StatusCodes.OK, "text/html");
            return;
        }

        String email = user.getEmailAddress();
        String password = user.getUserPassword();
        long userId = user.getId();

        try{
            String[] userHash = Encryption.decrypto(params[1]).split(Constants.SKY_SPLITTER);

            if(
                    userHash[0].equals(password)
                    && userHash[1].equals(email)
                    && Long.parseLong(userHash[2]) == userId
                    ){

                UserAccount updatedUser = new UserAccount();
                updatedUser.setActivated("YES");

                Repository.update(user, updatedUser);

                send(exchange, Constants.accountActivatedHtml,
                        StatusCodes.OK, "text/html");
            }

        }catch (Exception e){
            send(exchange, Constants.accountActivationFailedHtml,
                    StatusCodes.OK, "text/html");
        }

    }

    private String [] getParams(HttpServerExchange exchange){
        String [] params = new String[2];
        params[0] = "0"; params[1] = "0";

        Deque<String> id = exchange.getQueryParameters().get("id");
        Deque<String> hash = exchange.getQueryParameters().get("hash");

        if(id != null && !id.getFirst().equals(""))
            params[0] = id.getFirst();

        if(hash != null && !hash.getFirst().equals(""))
            params[1] = hash.getFirst();

        return params;
    }
}
