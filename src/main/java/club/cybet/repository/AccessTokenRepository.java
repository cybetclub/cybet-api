package club.cybet.repository;

import club.cybet.domain.db.orm.AccessToken;
import club.cybet.utils.DateTime;

import java.util.List;

/**
 * sls-api (club.cybet.repository)
 * Created by: cybet
 * On: 06 Jul, 2018 7/6/18 9:49 PM
 **/
public class AccessTokenRepository {

    @SuppressWarnings("unchecked")
    public static AccessToken validate(String accessToken){
        List<AccessToken> staleAt = (List<AccessToken>) Repository.findByFields(
                AccessToken.class,
                "accessToken".split(","),
                accessToken.split(","),
                " and "
        );

        if(staleAt.size() > 0){
            long accessTokenTime = staleAt.get(0).getDateCreated().getTime();
            long currentTime = DateTime.getCurrentUnixTimestamp();
            long delay = getDelay(staleAt.get(0).getTimeUnit(),
                    staleAt.get(0).getExpiry());

            if(accessTokenTime+delay <= currentTime){
                revoke(staleAt.get(0).getAccessToken());
                return null;
            }else return staleAt.get(0);
        }else {
            return null;
        }
    }

    public static AccessToken refresh(String accessToken){
        AccessToken staleAt = validate(accessToken);

        if(staleAt != null){
            revoke(accessToken);
            return (AccessToken) Repository
                    .save(AccessToken.class, new AccessToken(staleAt.getUserAccountId())).getData();
        }

        return null;
    }

    public static void revoke(String accessToken){
        Repository.deleteBy(AccessToken.class,
                "accessToken".split(","),
                accessToken.split(","),
                " or ");
    }

    private static long getDelay(String timeUnit, long expiry){
        switch (timeUnit) {
            case "seconds":
            case "s":
                return expiry * 1000;
            case "minutes":
            case "m":
                return expiry * 1000 * 60;
            case "hours":
            case "h":
                return expiry * 1000 * 60 * 60;
            case "days":
            case "d":
                return expiry * 1000 * 60 * 60 * 24;
            default:
                return expiry * 1000;
        }
    }

}
