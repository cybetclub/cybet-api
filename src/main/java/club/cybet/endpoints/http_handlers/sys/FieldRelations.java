package club.cybet.endpoints.http_handlers.sys;

import club.cybet.utils.Constants;
import club.cybet.utils.http.ScedarHttpHandler;
import club.cybet.utils.memory.JvmManager;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

import java.util.ArrayList;
import java.util.List;

/**
 * cybet-api (club.cybet.endpoints.http_handlers.sys)
 * Created by: cybet
 * On: 01 Sep, 2018 9/1/18 11:16 AM
 **/
public class FieldRelations extends ScedarHttpHandler {


    @Override
    public void handleRequest(HttpServerExchange exchange) {
        printRequestInfo(exchange);

        List<Object> fieldRelations = new ArrayList<>();

        fieldRelations.add(Constants.filterRelations);

        fieldRelations.add(new _FieldRelations(
                "Equal to",
                "Test whether provided value is exactly equal to value in the database. " +
                        "Works best with numerical figures or very precise alphanumeric queries",
                "eq"
        ));

        fieldRelations.add(new _FieldRelations(
                "Not Equal to",
                "Negation for 'Equal To' (eq). NB. The rest of the relations work the same." +
                        " i.e. Not In (!in), Not Starts With (!sw)",
                "!eq"
        ));

        fieldRelations.add(new _FieldRelations(
                "Less than",
                "Test whether provided value is less than another value in the database. " +
                        "Works best with numerical figures. In case of alphanumerics, it performs character count",
                "lt"
        ));

        fieldRelations.add(new _FieldRelations(
                "Less than or equal to",
                "Test whether provided value is either less than or equal to value in the database. " +
                        "Works best with numerical figures. In case of alphanumerics, it performs character count",
                "lte"
        ));

        fieldRelations.add(new _FieldRelations(
                "Greater than",
                "Test whether provided value is greater than a value in the database. " +
                        "Works best with numerical figures. In case of alphanumerics, it performs character count",
                "gt"
        ));

        fieldRelations.add(new _FieldRelations(
                "Greater than or equal to",
                "Test whether provided value is greater than or equal to value in the database. " +
                        "Works best with numerical figures. In case of alphanumerics, it performs character count",
                "gte"
        ));

        fieldRelations.add(new _FieldRelations(
                "Like",
                "Test whether provided value is like another value in the database. " +
                        "Works best with numerical alphanumerics. It will perform a case sensitive search " +
                        "and locate the specified characters anywhere in the target field. " +
                        "For numerical cases, it will treat them as characters",
                "like"
        ));

        fieldRelations.add(new _FieldRelations(
                "Not Like",
                "Test whether provided value is like another value in the database. " +
                        "Works best with numerical alphanumerics. It will perform a case sensitive search " +
                        "and locate the specified characters anywhere in the target field. Then negate it" +
                        "For numerical cases, it will treat them as characters",
                "!like"
        ));

        fieldRelations.add(new _FieldRelations(
                "ILike",
                "Test whether provided value is like another value in the database. " +
                        "Works best with numerical alphanumerics. It will perform a case insensitive search " +
                        "and locate the specified characters anywhere in the target field. " +
                        "For numerical cases, it will treat them as characters",
                "ilike"
        ));

        fieldRelations.add(new _FieldRelations(
                "Not ILike",
                "Test whether provided value is like another value in the database. " +
                        "Works best with numerical alphanumerics. It will perform a case insensitive search " +
                        "and locate the specified characters anywhere in the target field. Then negate it" +
                        "For numerical cases, it will treat them as characters",
                "!ilike"
        ));

        fieldRelations.add(new _FieldRelations(
                "Contains",
                "Test whether provided value is contained in another value in the database. " +
                        "Works best with numerical alphanumerics. It will perform a case sensitive search " +
                        "and locate the specified characters anywhere in the target field. " +
                        "For numerical cases, it will treat them as characters",
                "contains"
        ));

        fieldRelations.add(new _FieldRelations(
                "Starts with",
                "Test whether provided value is at the beginning of a value in the database. " +
                        "Works best with characters. It will perform a case sensitive search " +
                        "and will only retrieve a record if the provided value occurs " +
                        "exactly at the beginning of the target field",
                "sw"
        ));

        fieldRelations.add(new _FieldRelations(
                "Ends with",
                "Test whether provided value is at the end of a value in the database. " +
                        "Works best with characters. It will perform a case sensitive search " +
                        "and will only retrieve a record if the provided value occurs " +
                        "exactly at the end of the target field",
                "ew"
        ));

        fieldRelations.add(new _FieldRelations(
                "In",
                "Test whether provided set of values is in the database. " +
                        "Works best with characters. It will perform a case sensitive search " +
                        "of the whole word/value and will only retrieve a record if the provided value occurs. " +
                        "Sample value for this is 'Admin*User'. NB. The values are separated by '*'",
                "in"
        ));

        send(exchange, fieldRelations, StatusCodes.OK);
        JvmManager.gc(fieldRelations, exchange);
    }

    public class _FieldRelations{
        private String label;
        private String description;
        private String relation;

        public _FieldRelations() {}

        _FieldRelations(String label,
                        String description,
                        String relation) {
            this.label = label;
            this.description = description;
            this.relation = relation;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }

        @Override
        public String toString() {
            return "_FieldRelations{" +
                    "label='" + label + '\'' +
                    ", description='" + description + '\'' +
                    ", relation='" + relation + '\'' +
                    '}';
        }
    }
}
