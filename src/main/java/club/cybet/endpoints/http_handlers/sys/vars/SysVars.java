package club.cybet.endpoints.http_handlers.sys.vars;

import club.cybet.domain.db.orm.SystemVariable;
import club.cybet.repository.Repository;
import club.cybet.utils.http.ScedarHttpHandler;
import club.cybet.utils.http.exceptions.ExceptionRepresentation;
import club.cybet.utils.memory.JvmManager;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

import java.util.List;

/**
 * cybet-api (club.cybet.endpoints.http_handlers.sys.vars)
 * Created by: cybet
 * On: 03 Sep, 2018 9/3/18 6:54 PM
 **/
public class SysVars extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

        if(requestIsNotAuthentic(exchange)) return;

        int[] ps = getPageAndPageSize(exchange);

        List<List<String>> filterData = getFilterData(exchange, SystemVariable.class);

        if(filterData.get(0).get(0).equals("ERROR")){
            send(exchange, new ExceptionRepresentation(
                    filterData.get(1).toString(),
                    exchange.getRequestURI(),
                    "Bad request.",
                    StatusCodes.BAD_REQUEST,
                    exchange.getRequestMethod()
            ).setErrors(filterData.get(1)), StatusCodes.BAD_REQUEST);
            return;
        }

        Object variable = Repository.filter(SystemVariable.class, filterData, ps[0], ps[1]);

        send(exchange, variable, StatusCodes.OK);
        JvmManager.gc(exchange, ps, variable, filterData);
    }

}
