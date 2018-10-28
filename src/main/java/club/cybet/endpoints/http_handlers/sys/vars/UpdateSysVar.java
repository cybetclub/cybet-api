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
public class UpdateSysVar extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

        if (requestIsNotAuthentic(exchange)) return;

        SystemVariable variable = (SystemVariable) getBodyObject(exchange, SystemVariable.class);

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

        String id = getPathVar(exchange, "id");

        SystemVariable staleVariable = (SystemVariable) Repository
                .findById(SystemVariable.class, id);

        if(staleVariable == null){
            send(exchange, new ExceptionRepresentation(
                    "System variable not found",
                    exchange.getRequestURI(),
                    "System variable not found",
                    StatusCodes.NOT_FOUND,
                    exchange.getRequestMethod()
            ), StatusCodes.NOT_FOUND);
            return;
        }

        if(staleVariable.getSystemProtected().equals("YES")){
            send(exchange, new ExceptionRepresentation(
                    "Error: Unable to update system variable.",
                    exchange.getRequestURI(),
                    "Update operation on sys var <id:"+staleVariable.getId()+">" +
                            "<key:"+staleVariable.getKey()+"> is prohibited",
                    StatusCodes.FORBIDDEN,
                    exchange.getRequestMethod()
            ), StatusCodes.FORBIDDEN);
            return;
        }

        if(!staleVariable.getCreatedById().equals(getAuthenticatedUserId(exchange))){
            send(exchange, new ExceptionRepresentation(
                    "Error: Unable to update system variable.",
                    exchange.getRequestURI(),
                    "Update operation on sys var <id:"+staleVariable.getId()+">" +
                            "<key:"+staleVariable.getKey()+"> is prohibited. You are not the owner of this object",
                    StatusCodes.FORBIDDEN,
                    exchange.getRequestMethod()
            ), StatusCodes.FORBIDDEN);
            return;
        }

        long authenticatedUserId = getAuthenticatedUserId(exchange);

        if(!staleVariable.getCreatedById().equals(authenticatedUserId)){
            if(authenticatedUserId != 1L){
                send(exchange, new ExceptionRepresentation(
                        "Error: Unable to update system variable.",
                        exchange.getRequestURI(),
                        "Update operation on sys var <id:"+staleVariable.getId()+">" +
                                "<key:"+staleVariable.getKey()+"> is prohibited. You are not the owner of this object",
                        StatusCodes.FORBIDDEN,
                        exchange.getRequestMethod()
                ), StatusCodes.FORBIDDEN);
                return;
            }
        }

        variable.setUpdatedById(authenticatedUserId);

        DbTransactionWrapper wrapper = Repository.update(staleVariable, variable);

        if(wrapper.hasErrors()){
            send(exchange, new ExceptionRepresentation(
                    "Error: Unable to update system variable.",
                    exchange.getRequestURI(),
                    wrapper.getErrors(),
                    StatusCodes.INTERNAL_SERVER_ERROR,
                    exchange.getRequestMethod()
            ), StatusCodes.INTERNAL_SERVER_ERROR);
            return;
        }

        send(exchange, wrapper.getData(), StatusCodes.OK);

        JvmManager.gc(variable, staleVariable, wrapper, id, exchange, authenticatedUserId);
    }

}
