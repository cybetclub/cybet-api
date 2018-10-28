package club.cybet.endpoints.http_handlers.sys.vars;

import club.cybet.domain.beans.DbTransactionWrapper;
import club.cybet.domain.db.orm.SystemVariable;
import club.cybet.repository.Repository;
import club.cybet.utils.Constants;
import club.cybet.utils.http.ScedarHttpHandler;
import club.cybet.utils.http.exceptions.ExceptionRepresentation;
import club.cybet.utils.memory.JvmManager;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

/**
 * cybet-api (club.cybet.endpoints.http_handlers.sys.vars)
 * Created by: cybet
 * On: 03 Sep, 2018 9/3/18 6:54 PM
 **/
public class CreateSysVar extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

        if(requestIsNotAuthentic(exchange)) return;

        SystemVariable variable = (SystemVariable)
                getBodyObject(exchange, SystemVariable.class);

        if(variable == null){
            send(exchange, new ExceptionRepresentation(
                    exchange.getQueryParameters().get(Constants.MARSHALL_ERROR).getFirst(),
                    exchange.getRequestURI(),
                    "Error: Unable to understand payload.",
                    StatusCodes.INTERNAL_SERVER_ERROR,
                    exchange.getRequestMethod()
            ), StatusCodes.INTERNAL_SERVER_ERROR);
            return;
        }

        long authenticatedUserId = getAuthenticatedUserId(exchange);
        variable.setCreatedById(authenticatedUserId);
        variable.setUpdatedById(authenticatedUserId);

        DbTransactionWrapper wrapper = Repository.save(SystemVariable.class, variable);

        if(wrapper.hasErrors()){
            send(exchange, new ExceptionRepresentation(
                    "Error: Unable to create system variable.",
                    exchange.getRequestURI(),
                    wrapper.getErrors(),
                    StatusCodes.INTERNAL_SERVER_ERROR,
                    exchange.getRequestMethod()
            ), StatusCodes.INTERNAL_SERVER_ERROR);
            return;
        }

        send(exchange, wrapper.getData(), StatusCodes.OK);
        JvmManager.gc(variable, authenticatedUserId, exchange, wrapper);
    }

}
