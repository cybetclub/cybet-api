package club.cybet.endpoints.http_handlers.sys;

import club.cybet.domain.db.orm.*;
import club.cybet.repository.Repository;
import club.cybet.utils.Misc;
import club.cybet.utils.http.ScedarHttpHandler;
import club.cybet.utils.memory.JvmManager;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

import java.util.ArrayList;
import java.util.List;

/**
 * cybet-api (club.cybet.endpoints.http_handlers.sys)
 * Created by: cybet
 * On: 01 Sep, 2018 9/1/18 11:22 AM
 **/
public class FilterFields extends ScedarHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

        if(requestIsNotAuthentic(exchange)) return;

        List<_FilterFields> filterFields = new ArrayList<>();

        filterFields.add(getFilterFields(Attachment.class, Attachment.class.getSimpleName()));
        filterFields.add(getFilterFields(AttachmentType.class, AttachmentType.class.getSimpleName()));
        filterFields.add(getFilterFields(EmploymentIndustry.class, EmploymentIndustry.class.getSimpleName()));
        filterFields.add(getFilterFields(EmploymentStatus.class, EmploymentStatus.class.getSimpleName()));
        filterFields.add(getFilterFields(IdentificationType.class, IdentificationType.class.getSimpleName()));
        filterFields.add(getFilterFields(JobTitle.class, JobTitle.class.getSimpleName()));
        filterFields.add(getFilterFields(SelfAnnualIncomeBracket.class, SelfAnnualIncomeBracket.class.getSimpleName()));
        filterFields.add(getFilterFields(SourceOfFunds.class, SourceOfFunds.class.getSimpleName()));
        filterFields.add(getFilterFields(SystemVariable.class, SystemVariable.class.getSimpleName()));
        filterFields.add(getFilterFields(UserAccount.class, UserAccount.class.getSimpleName()));
        filterFields.add(getFilterFields(UserAccountType.class, UserAccountType.class.getSimpleName()));

        send(exchange, filterFields, StatusCodes.OK);

        JvmManager.gc(filterFields, exchange);

    }

    public static class _FilterFields{
        private String type;
        private String fields;
        private String labels;

        public _FilterFields(){}

        _FilterFields(String type, String fields,
                             String labels) {
            this.type = type;
            this.fields = fields;
            this.labels = labels;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFields() {
            return fields;
        }

        public void setFields(String fields) {
            this.fields = fields;
        }

        public String getLabels() {
            return labels;
        }

        public void setLabels(String labels) {
            this.labels = labels;
        }

        @Override
        public String toString() {
            return "_FilterFields{" +
                    "type='" + type + '\'' +
                    ", fields='" + fields + '\'' +
                    ", labels='" + labels + '\'' +
                    '}';
        }
    }

    static _FilterFields getFilterFields(Class entity, String type){
        List<String> _fields = Repository.getFilterByFields(entity);

        _FilterFields filterFields = new _FilterFields(
                type,
                String.join(",", _fields),
                String.join(",", Misc.convertFromCamelCaseToHeaderCase(_fields))
        );

        _fields.clear();
        _fields = null;

        return filterFields;
    }
}
