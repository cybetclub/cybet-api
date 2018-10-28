package club.cybet.endpoints.http_handlers.users.me.attachments;

import club.cybet.domain.db.orm.Attachment;
import club.cybet.repository.Repository;
import club.cybet.utils.http.ScedarHttpHandler;
import club.cybet.utils.http.exceptions.ExceptionRepresentation;
import club.cybet.utils.memory.JvmManager;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

import java.util.List;

/**
 * cybet-api (club.cybet.endpoints.http_handlers.users.attachments)
 * Created by: cybet
 * On: 29 Aug, 2018 8/29/18 5:48 PM
 **/
public class MeAttachments extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

        if(requestIsNotAuthentic(exchange)) return;

        String id = String.valueOf(getAuthenticatedUserId(exchange));

        int[] ps = getPageAndPageSize(exchange);

        List<List<String>> filterData = getFilterData(exchange, Attachment.class);

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

        if(filterData.get(0).get(0).equals("*")) {
            filterData.get(0).remove(0);
            filterData.get(0).add(0, "userAccountId:eq:" + id);
        }else filterData.get(0).add("userAccountId:eq:"+id);
        filterData.get(1).add(0, "and");

        Object attachments = Repository.filter(Attachment.class, filterData, ps[0], ps[1]);

        send(exchange, attachments, StatusCodes.OK);
        JvmManager.gc(exchange, id, ps, attachments, filterData);
    }
}
