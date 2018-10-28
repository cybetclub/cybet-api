package club.cybet.endpoints.http_handlers.kyc;

import club.cybet.repository.Repository;
import club.cybet.utils.http.ScedarHttpHandler;
import club.cybet.utils.http.exceptions.ExceptionRepresentation;
import club.cybet.utils.memory.JvmManager;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

import java.util.List;

/**
 * cybet-api (club.cybet.endpoints.http_handlers.kyc)
 * Created by: cybet
 * On: 24 Oct, 2018 10/24/18 11:20 PM
 **/
public class Kyc extends ScedarHttpHandler {

    private Class clazz;

    public Kyc (Class clazz){
        this.clazz = clazz;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

        if(requestIsNotAuthentic(exchange)) return;

        int[] ps = getPageAndPageSize(exchange);

        List<List<String>> filterData = getFilterData(exchange, clazz);

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

        Object accountType = Repository.filter(clazz, filterData, ps[0], ps[1]);

        send(exchange, accountType, StatusCodes.OK);
        JvmManager.gc(exchange, ps, accountType, filterData);
    }


}
