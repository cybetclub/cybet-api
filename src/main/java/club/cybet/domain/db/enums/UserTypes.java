package club.cybet.domain.db.enums;

/**
 * cybet-api (club.cybet.domain.db.enums)
 * Created by: cybet
 * On: 16 Aug, 2018 8/16/18 8:00 PM
 **/
public enum UserTypes {

    ROOT("ROOT"),
    BOOKMAKER("BOOKMAKER"),
    STANDARD_USER("STANDARD USER");

    private String value;

    UserTypes(String value){
        this.value = value;
    }

    public String value() {
        return value;
    }

}
