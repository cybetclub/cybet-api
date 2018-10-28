package club.cybet.endpoints.http_handlers.users.me;

import club.cybet.domain.db.orm.UserAccount;
import club.cybet.repository.Repository;
import club.cybet.utils.http.ScedarHttpHandler;
import club.cybet.utils.http.exceptions.ExceptionRepresentation;
import club.cybet.utils.memory.JvmManager;
import club.cybet.utils.security.ScedarUID;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

/**
 * cybet-api
 * Created by: cybet
 * On: 04 Sep, 2018 9/4/18 9:59 PM
 **/
public class DeleteMe extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

        if (requestIsNotAuthentic(exchange)) return;

        String id = String.valueOf(getAuthenticatedUserId(exchange));

        UserAccount staleUserAccount = (UserAccount) Repository
                .findById(UserAccount.class, id);

        assert staleUserAccount != null;

        UserAccount user = new UserAccount();
        user.setEmailAddress(staleUserAccount.getEmailAddress()+"/|\\DELETED/|\\"
                +ScedarUID.generateUid(20));
        try{
            user.setEthereumWalletAddress(staleUserAccount.getEthereumWalletAddress()+"/|\\DELETED/|\\"
                    +ScedarUID.generateUid(20));
        } catch (NullPointerException e){
            user.setEthereumWalletAddress(""+"/|\\DELETED/|\\"
                    +ScedarUID.generateUid(20));
        }
        user.setDeleted("YES");

        Repository.update(staleUserAccount, user);

        send(exchange,
                true,
                StatusCodes.OK);

        JvmManager.gc(staleUserAccount, exchange, id);
    }

}
