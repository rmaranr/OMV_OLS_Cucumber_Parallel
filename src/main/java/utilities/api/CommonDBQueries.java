package utilities.api;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CommonDBQueries extends BaseUtils{

    private CommonDBUtils commonDBUtils;
    private StringBuffer sbQuery = new StringBuffer();

    public CommonDBQueries(){
        commonDBUtils = new CommonDBUtils();
    }
    /*
    Method to get customer details which has taxnumber
     */
    public String getCustomerNoWhichHasVatNumbers(){
        Properties inputProp = PropUtils.getProps(inputDataFile);
        sbQuery.append("select distinct mcust.customer_no from tax_numbers tn\n" +
                "inner join m_customers mcust on mcust.customer_mid = tn.member_oid\n" +
                "inner join m_clients mc on mc.client_mid = mcust.client_mid\n" +
                "where mc.name = "+PropUtils.getPropValue(inputProp,PropUtils.getPropValue(PropUtils.getProps(commonPropertyFile),"clientCountry")));
        Map<String, String>queryRes = commonDBUtils.getQueryResultsOnMap(sbQuery.toString());
        if(queryRes!=null || queryRes.size()==0){
            return "";
        }else{
            return queryRes.get("CUSTOMER_NO");
        }
    }
    /*
    Method to get tax numbers based on customer No
     */
//    public List<Map<String, String>> getTaxListFromCustNumber(String custNumber){
//        sbQuery.delete(0,sbQuery.length());
////        sbQuery.
//    }
}
