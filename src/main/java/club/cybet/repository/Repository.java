package club.cybet.repository;

import club.cybet.domain.beans.DbTransactionWrapper;
import club.cybet.domain.beans.NullAwareBeanUtils;
import club.cybet.domain.beans.PageableWrapper;
import club.cybet.utils.Constants;
import club.cybet.utils.DateTime;
import club.cybet.utils.hibernate.HibernateSetup;
import club.cybet.utils.memory.JvmManager;
import club.cybet.utils.security.ScedarUID;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static club.cybet.utils.Constants.*;

/**
 * sls-api (club.cybet.repository)
 * Created by: cybet
 * On: 01 Jul, 2018 7/1/18 10:50 PM
 **/
public class Repository {

    public static Long count(Class entity){

        Session session = HibernateSetup.getMainSession();
        Long result;

        try {
            result = (Long) session.createQuery("select count(*) from "+entity.getName()+" " +
                    "where deleted = 'NO' ")
                    .setCacheable(true)
                    .getSingleResult();
            session.close();
        } catch (Exception e){
            session.close();
            e.printStackTrace();
            return 0L;
        }

        return result;
    }

    public static Long countByFields(Class entity, String[] fields,
                                     String[] values, String strategy){
        if(fields.length != values.length) return null;

        if(!Constants.SFBS.contains(strategy))
            return null;

        StringBuilder fBfS = new StringBuilder();
        fBfS.append(" (");
        for (int i = 0; i < fields.length ; i++) {
            fBfS.append(fields[i])
                    .append(" = ")
                    .append("'").append(values[i]).append("'");

            if(!(i+1 == fields.length)) fBfS.append(strategy);
        }
        fBfS.append(") ");

        Session session = HibernateSetup.getMainSession();
        Long result;

        try{
            result = (Long) session.createQuery("select count(*) from "+entity.getName()+" " +
                    "where " + fBfS.toString()+
                    " and deleted = 'NO' ")
                    .setCacheable(true)
                    .getSingleResult();
            session.close();
        }catch (NoResultException ignore){
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return result;

    }

    public static Object findById(Class entity, String id){

        Session session = HibernateSetup.getMainSession();
        Object result = null;

        try{
            result = session.createQuery("from "+entity.getName()+" " +
                    "where id = '"+id+"' " +
                    "and deleted = 'NO' " +
                    "order by "+getOrderByField(entity))
                    .setCacheable(true)
                    .getSingleResult();
            session.close();
        }catch (NoResultException ignore){
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    public static Object findByUniqueField(Class entity, String field, String value){
        Session session = HibernateSetup.getMainSession();
        Object result;

        try{
            result = session.createQuery("from "+entity.getName()+" " +
                    "where "+field+" = '"+value+"' " +
                    "and deleted = 'NO' ")
                    .setCacheable(true)
                    .getSingleResult();
            session.close();
        }catch (NoResultException ignore){
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    public static Object findByFields(Class entity, String[] fields,
                                    String[] values, String strategy){
        return findByFields(entity, fields, values, strategy, 1, 10);
    }

    public static Object findByFields(Class entity, String[] fields,
                                    String[] values, String strategy,
                                    Integer page, Integer pageSize){

        if(fields.length != values.length) return null;

        if(!Constants.SFBS.contains(strategy))
            return null;

        StringBuilder fBfS = new StringBuilder();
        fBfS.append(" (");
        for (int i = 0; i < fields.length ; i++) {
            fBfS.append(fields[i])
                    .append(" = ")
                    .append("'").append(values[i]).append("'");

            if(!(i+1 == fields.length)) fBfS.append(strategy);
        }
        fBfS.append(") ");

        Session session = HibernateSetup.getMainSession();

        List result = session.createQuery("from "+entity.getName()+" " +
                "where " + fBfS.toString()+
                " and deleted = 'NO' " +
                " order by "+getOrderByField(entity))
                .setCacheable(true)
                .setFirstResult((page < 1 ? page : page - 1 )*pageSize)
                .setMaxResults(pageSize)
                .list();

        session.close();

        if(entity.getSimpleName().equals("AccessToken")){
            return result;
        }else{
            return bundleUp(entity, result,
                    countByFields(entity, fields, values, strategy),
                    page, pageSize);
        }
    }

    public static Object findAll(Class entity){
        // TODO: Put 10 in kkz.conf
        return findAll(entity, 1, 10);
    }

    public static Object findAll(Class entity, Integer page, Integer pageSize){

        Session session = HibernateSetup.getMainSession();

        List results = session.createQuery("from "+entity.getName()+" " +
                "where deleted = 'NO' " +
                "order by "+getOrderByField(entity))
                .setCacheable(true)
                .setFirstResult((page < 1 ? page : page - 1 )*pageSize)
                .setMaxResults(pageSize)
                .list();

        session.close();

        return bundleUp(entity, results, count(entity), page, pageSize);
    }

    @SuppressWarnings("unchecked")
    public static Object filter(Class entity, List<List<String>> filterData, int page, int pageSize){

        Session session = HibernateSetup.getMainSession();
        String hqlCriteria = filterHqlGenerator(filterData);
        String hqlCriteriaCount = hqlCriteria;

        if(filterData.get(2).get(0).equals("*"))
            hqlCriteria = "from "+entity.getName()+hqlCriteria;
        else
            hqlCriteria = "select "+filterData.get(2).get(0)+" from "+entity.getName()+hqlCriteria;

        List results = session.createQuery(
                hqlCriteria +
                " order by "+getOrderByField(entity))
                .setCacheable(true)
                .setFirstResult((page < 1 ? page : page - 1 )*pageSize)
                .setMaxResults(pageSize)
                .list();

        session.close();

        List<Long> count = (List<Long>)
                executeQuery("select count(*) from "+entity.getName()+" "+hqlCriteriaCount);
        if(count == null) count = new ArrayList<>(Collections.singleton(0L));
        return bundleUp(entity, results, count.get(0), page, pageSize);
    }

    public static boolean isInOr(Class entity, String field, String value){
        return isIn(entity, field, value, " or ");
    }

    public static boolean isInAnd(Class entity, String field, String value){
        return isIn(entity, field, value, " and ");
    }

    public static boolean isIn(Class entity, String field, String value, String strategy){
        return isIn(entity, field.split(","), value.split(","), strategy);
    }

    public static boolean isIn(Class entity, String[] fields, String[] values, String strategy){

        if(fields.length != values.length) return false;

        if(!Constants.SFBS.contains(strategy))
            return false;

        StringBuilder fBfS = new StringBuilder();
        fBfS.append(" (");
        for (int i = 0; i < fields.length ; i++) {
            fBfS.append(fields[i])
                    .append(" = ")
                    .append("'").append(values[i]).append("'");

            if(!(i+1 == fields.length)) fBfS.append(strategy);
        }
        fBfS.append(") ");

        Session session = HibernateSetup.getMainSession();

        Object obj =  session
                .createQuery("select 1 from " + entity.getName() +
                        " where " + fBfS.toString()+
                        " and deleted = 'NO' ")
                .setCacheable(true)
                .uniqueResult();

        session.close();
        return (obj != null);
    }

    public static DbTransactionWrapper save(Class c, Object data){
        return save(data);
    }


    public static DbTransactionWrapper save(Object data){
        Session session = HibernateSetup.getMainSession();
        Transaction transaction = session.beginTransaction();
        DbTransactionWrapper wrapper = new DbTransactionWrapper();

        Class entity = data.getClass();

        if(!(entity.getSimpleName().equals("AccessToken") ||
            entity.getSimpleName().equals("IntegSkyMpesaApi"))){
            setDefaults(data);
        }

        try{
            Object res = session.get(Class.forName(entity.getName()), session.save(data));

            transaction.commit();
            session.close();

            wrapper.setData(res);

            return wrapper;

        }catch (Exception e){
            transaction.rollback();
            session.close();

            wrapper.setHasErrors(true);
            wrapper.setErrors(new ArrayList<>(Arrays.asList(
                    e.getMessage(), e.toString())));

            //TODO: Put in Application to return errors
            e.printStackTrace();
            return wrapper;
        }

    }

    private static void setDefaults(Object data) {
        try{
            // Check to see if defaults are there
            Method _getCreated = data.getClass().getMethod("getCreated");
            Method _setCreated = data.getClass().getMethod("setCreated", Timestamp.class);
            Method _getUpdated = data.getClass().getMethod("getUpdated");
            Method _setUpdated = data.getClass().getMethod("setUpdated", Timestamp.class);
            Method _getDeleted = data.getClass().getMethod("getDeleted");
            Method _setDeleted = data.getClass().getMethod("setDeleted", String.class);

            if(_getCreated.invoke(data) == null) _setCreated.invoke(data, DateTime.getCurrentSqlTimestamp());
            if(_getUpdated.invoke(data) == null) _setUpdated.invoke(data, DateTime.getCurrentSqlTimestamp());
            if(_getDeleted.invoke(data) == null) _setDeleted.invoke(data, "NO");

        }catch (NoSuchMethodException |
                InvocationTargetException |
                IllegalAccessException nsme){
            nsme.printStackTrace();
        }
    }

    public static DbTransactionWrapper update(Object stale, Object update){

        NullAwareBeanUtils beanUtils = new NullAwareBeanUtils();
        Session session = HibernateSetup.getMainSession();
        Transaction transaction = session.beginTransaction();
        DbTransactionWrapper wrapper = new DbTransactionWrapper();

        try{
            beanUtils.copyProperties(stale, update);
            session.update(stale);
            transaction.commit();
            session.close();

            wrapper.setData(true);

            return wrapper;

        }catch (Exception e){
            transaction.rollback();
            session.close();
            e.printStackTrace();

            wrapper.setHasErrors(true);
            wrapper.setErrors(new ArrayList<>(Arrays.asList(
                    e.getMessage(), e.toString())));

            return wrapper;
        }
    }

    public static boolean deactivate(Class entity, String id){
        Session session = HibernateSetup.getMainSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.createQuery("update "+entity.getName()+" " +
                    "set deleted = 'YES' " +
                    "where id = '"+id+"'")
                    .executeUpdate();

            transaction.commit();
            session.close();

            return true;

        }catch (Exception e){
            transaction.rollback();
            session.close();
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteById(Class entity, String id){
        Session session = HibernateSetup.getMainSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.createQuery("delete from "+entity.getName()+" " +
                    "where id = '"+id+"'").executeUpdate();

            transaction.commit();
            session.close();

            return true;

        }catch (Exception e){
            transaction.rollback();
            session.close();
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteBy(Class entity, String[] fields,
                                   String[] values, String strategy){

        if(fields.length != values.length) return false;

        if(!Constants.SFBS.contains(strategy))
            return false;

        StringBuilder fBfS = new StringBuilder();

        for (int i = 0; i < fields.length ; i++) {
            fBfS.append(fields[i])
                    .append(" = ")
                    .append("'").append(values[i]).append("'");

            if(!(i+1 == fields.length)) fBfS.append(strategy);
        }

        Session session = HibernateSetup.getMainSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.createQuery("delete from "+entity.getName()+" " +
                    "where "+fBfS.toString()).executeUpdate();

            transaction.commit();
            session.close();

            return true;

        }catch (Exception e){
            transaction.rollback();
            session.close();
            e.printStackTrace();
            return false;
        }
    }

    public static Object executeQuery(String query){
        Session session = HibernateSetup.getMainSession();
        Object results;

        try{
            results = session.createQuery(query)
                    .setCacheable(true).list();
            session.close();

            return results;
        } catch (Exception e){
            session.close();
            e.printStackTrace();
            return null;
        }
    }

    public static Object executeNativeQuery(String query){
        Session session = HibernateSetup.getMainSession();
        Object results;

        try{
            results = session.createNativeQuery(query).list();
            session.close();

            return results;
        } catch (Exception e){
            session.close();
            e.printStackTrace();
            return null;
        }
    }

    public static int executeUpdateQuery(String query){
        Session session = HibernateSetup.getMainSession();
        Transaction transaction = session.beginTransaction();
        int results;

        try{
            results = session.createQuery(query)
                    .executeUpdate();
            transaction.commit();
            session.close();

            return results;
        } catch (Exception e){
            transaction.rollback();
            session.close();
            e.printStackTrace();
            return -1;
        }
    }

    @SuppressWarnings("unchecked")
    public static String getNextInt(String key, int increment){

        List<String> variable = (List<String>) Repository.executeQuery("" +
                "select value from SystemVariable where key = '"+key+"'");

        if(variable != null && variable.size() > 0){
            String value = variable.get(0);
            String newValue = String.valueOf(Long.parseLong(value)+increment);

            Repository.executeUpdateQuery("update SystemVariable set value = '"+newValue+"'" +
                    " where key = '"+key+"'");

            JvmManager.gc(variable, newValue);

            return value;
        }
        return ScedarUID.generateUid(5);
    }

    private static Object bundleUp(Class entity, List records,
                                   Long totalCount, Integer page, Integer pageSize){

        PageableWrapper pageableWrapper = new PageableWrapper();
        pageableWrapper.setCurrentPage(page);
        pageableWrapper.setPageSize(pageSize);
        pageableWrapper.setDomain(entity.getSimpleName());
        pageableWrapper.setRecords(records);
        pageableWrapper.setNextPage("page="+(records.size() == pageSize ? page+1 : page)+"&pageSize="+pageSize);
        pageableWrapper.setTotalCount(totalCount);

        return pageableWrapper;
//        return records;
    }

    private static String filterHqlGenerator(List<List<String>> filterData) {

        StringBuilder hql = new StringBuilder();
        hql.append(" where deleted = 'NO' ");
        String strategy = filterData.get(1).get(0);
        String[] filterProps;

        if(filterData.get(0).get(0).equals("*")) return hql.toString();

        for (String filter : filterData.get(0)) {
            filterProps = filter.split(":");

            switch (filterProps[1]) {
                case RELATION_EQ:
                    hql.append(" ").append(strategy).append(" ")
                            .append(filterProps[0])
                            .append(" = '")
                            .append(filterProps[2]).append("'");
                    break;

                case RELATION_LT:
                    hql.append(" ").append(strategy).append(" ")
                            .append(filterProps[0])
                            .append(" < '")
                            .append(filterProps[2]).append("'");
                    break;

                case RELATION_LTE:
                    hql.append(" ").append(strategy).append(" ")
                            .append(filterProps[0])
                            .append(" <= '")
                            .append(filterProps[2]).append("'");
                    break;

                case RELATION_GT:
                    hql.append(" ").append(strategy).append(" ")
                            .append(filterProps[0])
                            .append(" > '")
                            .append(filterProps[2]).append("'");
                    break;

                case RELATION_GTE:
                    hql.append(" ").append(strategy).append(" ")
                            .append(filterProps[0])
                            .append(" >= '")
                            .append(filterProps[2]).append("'");
                    break;

                case RELATION_LIKE:
                    hql.append(" ").append(strategy).append(" ")
                            .append(filterProps[0])
                            .append(" like '%")
                            .append(filterProps[2]).append("%'");
                    break;

                case RELATION_ILIKE:
                    hql.append(" ").append(strategy)
                            .append(" lower(")
                            .append(filterProps[0])
                            .append(") like '%")
                            .append(filterProps[2].toLowerCase()).append("%'");
                    break;

                case RELATION_CONTAINS:
                    hql.append(" ").append(strategy).append(" ")
                            .append(filterProps[0])
                            .append(" like '%")
                            .append(filterProps[2]).append("%'");
                    break;

                case RELATION_ICONTAINS:
                    hql.append(" ").append(strategy)
                            .append(" lower(")
                            .append(filterProps[0])
                            .append(") like '%")
                            .append(filterProps[2].toLowerCase()).append("%'");
                    break;

                case RELATION_SW:
                    hql.append(" ").append(strategy).append(" ")
                            .append(filterProps[0])
                            .append(" like '")
                            .append(filterProps[2]).append("%'");
                    break;

                case RELATION_ISW:
                    hql.append(" ").append(strategy)
                            .append(" lower(")
                            .append(filterProps[0])
                            .append(") like '")
                            .append(filterProps[2].toLowerCase()).append("%'");
                    break;

                case RELATION_EW:
                    hql.append(" ").append(strategy).append(" ")
                            .append(filterProps[0])
                            .append(" like '%")
                            .append(filterProps[2]).append("'");
                    break;

                case RELATION_IEW:
                    hql.append(" ").append(strategy)
                            .append(" lower(")
                            .append(filterProps[0])
                            .append(") like '%")
                            .append(filterProps[2].toLowerCase()).append("'");
                    break;

                case RELATION_IN:
                    hql.append(" ").append(strategy).append(" ")
                            .append(filterProps[0])
                            .append(" in(")
                            .append(filterProps[2]).append(")");
                    break;

                case RELATION_NEQ:
                    hql.append(" ").append(strategy).append(" ")
                            .append(filterProps[0])
                            .append(" <> '")
                            .append(filterProps[2]).append("'");
                    break;

                case RELATION_NLT:
                    hql.append(" ").append(strategy)
                            .append(" not ")
                            .append(filterProps[0])
                            .append(" < '")
                            .append(filterProps[2]).append("'");
                    break;

                case RELATION_NLTE:
                    hql.append(" ").append(strategy)
                            .append(" not ")
                            .append(filterProps[0])
                            .append(" <= '")
                            .append(filterProps[2]).append("'");
                    break;

                case RELATION_NGT:
                    hql.append(" ").append(strategy)
                            .append(" not ")
                            .append(filterProps[0])
                            .append(" > '")
                            .append(filterProps[2]).append("'");
                    break;

                case RELATION_NGTE:
                    hql.append(" ").append(strategy)
                            .append(" not ")
                            .append(filterProps[0])
                            .append(" >= '")
                            .append(filterProps[2]).append("'");
                    break;

                case RELATION_NLIKE:
                    hql.append(" ").append(strategy)
                            .append(" not ")
                            .append(filterProps[0])
                            .append(" like '%")
                            .append(filterProps[2]).append("%'");
                    break;

                case RELATION_NILIKE:
                    hql.append(" ").append(strategy)
                            .append(" not ")
                            .append(" lower(")
                            .append(filterProps[0])
                            .append(") like '%")
                            .append(filterProps[2].toLowerCase()).append("%'");
                    break;

                case RELATION_NCONTAINS:
                    hql.append(" ").append(strategy)
                            .append(" not ")
                            .append(filterProps[0])
                            .append(" like '%")
                            .append(filterProps[2]).append("%'");
                    break;

                case RELATION_NICONTAINS:
                    hql.append(" ").append(strategy)
                            .append(" not ")
                            .append(" lower(")
                            .append(filterProps[0])
                            .append(") like '%")
                            .append(filterProps[2].toLowerCase()).append("%'");
                    break;

                case RELATION_NSW:
                    hql.append(" ").append(strategy)
                            .append(" not ")
                            .append(filterProps[0])
                            .append(" like '")
                            .append(filterProps[2]).append("%'");
                    break;

                case RELATION_NISW:
                    hql.append(" ").append(strategy)
                            .append(" not ")
                            .append(" lower(")
                            .append(filterProps[0])
                            .append(") like '")
                            .append(filterProps[2].toLowerCase()).append("%'");
                    break;

                case RELATION_NEW:
                    hql.append(" ").append(strategy)
                            .append(" not ")
                            .append(filterProps[0])
                            .append(" like '%")
                            .append(filterProps[2]).append("'");
                    break;

                case RELATION_NIEW:
                    hql.append(" ").append(strategy)
                            .append(" not ")
                            .append(" lower(")
                            .append(filterProps[0])
                            .append(") like '%")
                            .append(filterProps[2].toLowerCase()).append("'");
                    break;

                case RELATION_NIN:
                    hql.append(" ").append(strategy).append(" ")
                            .append(" not ")
                            .append(filterProps[0])
                            .append(" in(")
                            .append(filterProps[2]).append(")");
                    break;

            }
        }

        return hql.toString();
    }

    private static String getOrderByField(Class entity){

        if(entity.getSimpleName().equals("AccessToken")){
            return "dateCreated asc ";
        }

        return "updated desc ";
    }

    public static List<String> getFilterByFields(Class clazz){
        List<String> viableFields = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields){

            Class<?> fType = field.getType();
            if(fType.isAssignableFrom(Integer.TYPE) ||
                    fType.isAssignableFrom(Integer.class) ||
                    fType.isAssignableFrom(Long.TYPE) ||
                    fType.isAssignableFrom(Long.class) ||
                    fType.isAssignableFrom(Double.TYPE) ||
                    fType.isAssignableFrom(Double.class) ||
                    fType.isAssignableFrom(Float.TYPE) ||
                    fType.isAssignableFrom(Float.class) ||
                    fType.isAssignableFrom(Short.TYPE) ||
                    fType.isAssignableFrom(Short.class) ||
                    fType.isAssignableFrom(String.class) ||
                    fType.isAssignableFrom(Timestamp.class) ||
                    fType.isAssignableFrom(Byte.class) ||
                    fType.isAssignableFrom(Byte.TYPE)){

                String fname = field.getName();
                if(!(fname.equals("userPassword") ||
                     fname.equals("resetPassword") ||
                     fname.equals("active") ||
                     fname.equals("exposedPath") ||
                     fname.equals("systemProtected") ||
                     fname.equals("displayPicture"))){

                    switch (clazz.getSimpleName()) {
                        case "Guarantor":
                            if (!(fname.equals("guaranteed") ||
                                    fname.equals("loanId"))) {
                                viableFields.add(field.getName());
                            }
                            break;
                        case "UserLoan":
                            if (!(fname.equals("userAccountId"))) {
                                viableFields.add(field.getName());
                            }
                            break;
                        case "UserDocument":
                            if (!(fname.equals("userAccountId"))) {
                                viableFields.add(field.getName());
                            }
                            break;
                        case "UserRole":
                            if (!(fname.equals("userAccountId"))) {
                                viableFields.add(field.getName());
                            }
                            break;
                        case "NextOfKin":
                            if (!(fname.equals("userAccountId"))) {
                                viableFields.add(field.getName());
                            }
                            break;
                        case "NextOfKinDocument":
                            if (!(fname.equals("nextOfKinId"))) {
                                viableFields.add(field.getName());
                            }
                            break;
                        default:
                            viableFields.add(field.getName());
                            break;
                    }
                }
            }
        }
        return viableFields;
    }

    public static List<String> getAllFields(Class clazz){
        List<String> allFields = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields){
            allFields.add(field.getName());
        }
        return allFields;
    }
}
