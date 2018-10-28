package club.cybet.endpoints.http_handlers.sys.vars;

import club.cybet.domain.db.orm.SystemVariable;
import club.cybet.repository.Repository;
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
public class SysVar extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

        if(requestIsNotAuthentic(exchange)) return;

        String id = getPathVar(exchange, "id");

        Object variable = Repository.findById(SystemVariable.class, id);

        if(variable == null){
            send(exchange, new ExceptionRepresentation(
                    "System variable not found",
                    exchange.getRequestURI(),
                    "System variable not found",
                    StatusCodes.NOT_FOUND,
                    exchange.getRequestMethod()
            ), StatusCodes.NOT_FOUND);
            return;
        }

        send(exchange, variable, StatusCodes.OK);
        JvmManager.gc(id, exchange, variable);
    }

}
