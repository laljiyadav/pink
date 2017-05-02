package com.pinkstar.main.data;

/**
 * Created by Win 7 on 11/5/2015.
 */
public class Apis {
    //public static final String Base = "http://ec2-54-169-93-225.ap-southeast-1.compute.amazonaws.com/api_old/restservices.php?";
    public static final String Base = "http://pinkstarapp.com/api_old/restservices.php?";
    public static final String Api_Token = "1f3870be274f6c49b3e31a0c6728957f";
    public static final String Token_id = "33452b6faa70d52accb77f77f371b525";
    public static final String Opt_pre = Base + "rquest=circle_list&operator=prepaid";
    public static final String Opt_post = Base + "rquest=circle_list&operator=postpaid";
    public static final String Opt_data = Base + "rquest=circle_list&operator=datacard";
    public static final String Opt_dth = Base + "rquest=circle_list&operator=dth";


    public static final String json_error = "[\n" +
            "  {\n" +
            "    \"Code\": \"Error0\",\n" +
            "    \"Message\": \"Recharge successfully completed.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error1\",\n" +
            "    \"Message\": \"Session with this number already exists.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error2\",\n" +
            "    \"Message\": \"Invalid Dealer code.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 3\",\n" +
            "    \"Message\": \"Invalid acceptance outlet code.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 4\",\n" +
            "    \"Message\": \"Invalid Operator code.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 5\",\n" +
            "    \"Message\": \"Invalid session code format.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 6\",\n" +
            "    \"Message\": \"Invalid EDS.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 7\",\n" +
            "    \"Message\": \"Invalid amount format or amount value is out of admissible range.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 8\",\n" +
            "    \"Message\": \"Invalid phone number format.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 9\",\n" +
            "    \"Message\": \"Invalid format of personal account number.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 10\",\n" +
            "    \"Message\": \"Invalid request message format.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 11\",\n" +
            "    \"Message\": \"Session with such a number does not exist.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 12\",\n" +
            "    \"Message\": \"The request is made from an unregistered IP.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 13\",\n" +
            "    \"Message\": \"The outlet is not registered with the Service Provider.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 15\",\n" +
            "    \"Message\": \"Payments to the benefit of this operator are not supported by the system.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 17\",\n" +
            "    \"Message\": \"The phone number does not match the previously entered number.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 18\",\n" +
            "    \"Message\": \"The payment amount does not match the previously entered amount.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 19\",\n" +
            "    \"Message\": \"The account (contract) number does not match the previously entered number.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 20\",\n" +
            "    \"Message\": \"The payment is being completed.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 21\",\n" +
            "    \"Message\": \"Not enough funds for effecting the payment.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 22\",\n" +
            "    \"Message\": \"The payment has not been accepted. Funds transfer error.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 23\",\n" +
            "    \"Message\": \"Invalid phone (account) number.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 24\",\n" +
            "    \"Message\": \"Error of connection with the provider’s server or a routine break in CyberPlat®.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 25\",\n" +
            "    \"Message\": \"Effecting of this type of payments is suspended.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 26\",\n" +
            "    \"Message\": \"Payments of this Dealer are temporarily blocked\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 27\",\n" +
            "    \"Message\": \"Operations with this account are suspended\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 30\",\n" +
            "    \"Message\": \"General system failure.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 31\",\n" +
            "    \"Message\": \"Exceeded number of simultaneously processed requests (CyberPlat®).\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 32\",\n" +
            "    \"Message\": \"Repeated payment within 60 minutes from the end of payment effecting process\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 33\",\n" +
            "    \"Message\": \"This denomination is applicable in <Flexi OR Special> category\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 34\",\n" +
            "    \"Message\": \"Transaction with such number could not be found.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 35\",\n" +
            "    \"Message\": \"Payment status alteration error.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 36\",\n" +
            "    \"Message\": \"Invalid payment status.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 37\",\n" +
            "    \"Message\": \"An attempt of referring to the gateway that is different from the gateway at the previous\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 38\",\n" +
            "    \"Message\": \"Invalid date. The effective period of the payment may have expired.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 39\",\n" +
            "    \"Message\": \"Invalid account number.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 40\",\n" +
            "    \"Message\": \"The card of the specified value is not registered in the system\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 41\",\n" +
            "    \"Message\": \"Error in saving the payment in the system.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 42\",\n" +
            "    \"Message\": \"Error in saving the receipt to the database.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 43\",\n" +
            "    \"Message\": \"Your working session in the system is invalid (the duration of the session may have expired), try to re-enter.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 44\",\n" +
            "    \"Message\": \"The Client cannot operate on this trading server.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 45\",\n" +
            "    \"Message\": \"No license is available for accepting payments to the benefit of this operator.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 46\",\n" +
            "    \"Message\": \"Could not complete the erroneous payment.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 47\",\n" +
            "    \"Message\": \"Time limitation of access rights has been activated.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 48\",\n" +
            "    \"Message\": \"Error in saving the session data to the database.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 50\",\n" +
            "    \"Message\": \"Effecting payments in the system is temporarily impossible.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 51\",\n" +
            "    \"Message\": \"Data are not found in the system.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 52\",\n" +
            "    \"Message\": \"The Dealer may be blocked. The Dealer’s current status does not allow effecting payments\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 53\",\n" +
            "    \"Message\": \"The Acceptance Outlet may be blocked. The Acceptance Outlet’s current status does not allow effecting payments.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 54\",\n" +
            "    \"Message\": \"The Operator may be blocked. The Operator’s current status does not allow effecting payments. (OP code not activated)\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 55\",\n" +
            "    \"Message\": \"The Dealer Type does not allow effecting payments.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 56\",\n" +
            "    \"Message\": \"An Acceptance Outlet of another type was expected. This Acceptance Outlet type does not allow effecting payments.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 57\",\n" +
            "    \"Message\": \"An Operator of another type was expected. This Operator type does not allow effecting payments\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 81\",\n" +
            "    \"Message\": \"Exceeded the maximum payment amount.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 82\",\n" +
            "    \"Message\": \"Daily debit amount has been exceeded.\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 88\",\n" +
            "    \"Message\": \"Duplicate Recharge\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 134\",\n" +
            "    \"Message\": \"Wrong keys or Be sure using Production Keys\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 137\",\n" +
            "    \"Message\": \"wrong key or passphrase\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"Code\": \" Error 224\",\n" +
            "    \"Message\": \"Operator Server Down\"\n" +
            "  }\n" +
            "]\n";

    public static final String Oprator = "{\n" +
            "\"Operator\":[\n" +
            "{\"Code\":\"28\",\"Operator\":\"AIRTEL\"},\n" +
            "{\"Code\":\"1\",\"Operator\":\"AIRCEL\"},\n" +
            "{\"Code\":\"3\",\"Operator\":\"BSNL\"},\n" +
            "{\"Code\":\"22\",\"Operator\":\"VODAFONE\"},\n" +
            "{\"Code\":\"17\",\"Operator\":\"TATA DOCOMO GSM\"},\n" +
            "{\"Code\":\"18\",\"Operator\":\"TATA DOCOMO CDMA\"},\n" +
            "{\"Code\":\"13\",\"Operator\":\"RELIANCE GSM\"},\n" +
            "{\"Code\":\"12\",\"Operator\":\"RELAINCE CDMA\"},\n" +
            "{\"Code\":\"10\",\"Operator\":\"MTS\"},\n" +
            "{\"Code\":\"19\",\"Operator\":\"UNINOR\"},\n" +
           // "{\"Code\":\"9\",\"Operator\":\"LOOP\"},\n" +
            "{\"Code\":\"5\",\"Operator\":\"VIDEOCON\"},\n" +
            "{\"Code\":\"6\",\"Operator\":\"MTNL MUMBAI\"},\n" +
            "{\"Code\":\"20\",\"Operator\":\"MTNL DELHI\"},\n" +
            //"{\"Code\":\"0\",\"Operator\":\"NONE\"},\n" +
            "{\"Code\":\"8\",\"Operator\":\"IDEA\"},\n" +
            "{\"Code\":\"29\",\"Operator\":\"JIO\"}\n" +
            "],\n" +
            "\"Circle\":[\n" +
            "{\"Code\":\"5\",\"Operator\":\"Andhra Pradesh\"},\n" +
            "{\"Code\":\"19\",\"Operator\":\"Assam\"},\n" +
            "{\"Code\":\"17\",\"Operator\":\"Bihar & Jharkhand\"},\n" +
            "{\"Code\":\"23\",\"Operator\":\"Chennai\"},\n" +
            "{\"Code\":\"1\",\"Operator\":\"Delhi/NCR\"},\n" +
            "{\"Code\":\"8\",\"Operator\":\"Gujarat\"},\n" +
            "{\"Code\":\"16\",\"Operator\":\"Haryana\"},\n" +
            "{\"Code\":\"21\",\"Operator\":\"Himachal Pradesh\"},\n" +
            "{\"Code\":\"22\",\"Operator\":\"Jammu & Kashmir\"},\n" +
            "{\"Code\":\"7\",\"Operator\":\"Karnataka\"},\n" +
            "{\"Code\":\"14\",\"Operator\":\"Kerala\"},\n" +
            "{\"Code\":\"3\",\"Operator\":\"Kolkata\"},\n" +
            "{\"Code\":\"2\",\"Operator\":\"Mumbai\"},\n" +
            "{\"Code\":\"20\",\"Operator\":\"North East\"},\n" +
            "{\"Code\":\"18\",\"Operator\":\"Orissa\"},\n" +
            "{\"Code\":\"15\",\"Operator\":\"Punjab\"},\n" +
            "{\"Code\":\"13\",\"Operator\":\"Rajasthan\"},\n" +
            "{\"Code\":\"6\",\"Operator\":\"Tamil Nadu\"},\n" +
            "{\"Code\":\"9\",\"Operator\":\"Uttar Pradesh (E)\"},\n" +
            "{\"Code\":\"11\",\"Operator\":\"Uttar Pradesh (W)\"},\n" +
            "{\"Code\":\"12\",\"Operator\":\"West Bengal\"},\n" +
            "{\"Code\":\"4\",\"Operator\":\"Maharashtra\"},\n" +
            "{\"Code\":\"10\",\"Operator\":\"Madhya Pradesh\"},\n" +
            "{\"Code\":\"0\",\"Operator\":\"None\"}\n" +
            "],\n" +
            "\"Errors\":[\n" +
            "{\"Error\":\"User ID / app key missing\",\"Code\":\"1\"},\n" +
            "{\"Error\":\"Operator code missing \",\"Code\":\"2\"},\n" +
            "{\"Error\":\"Service code missing \",\"Code\":\"3\"},\n" +
            "{\"Error\":\"Amount code missing \",\"Code\":\"4\"},\n" +
            "{\"Error\":\"Client order id missing \",\"Code\":\"5\"},\n" +
            "{\"Error\":\"Operator code is invalid \",\"Code\":\"6\"},\n" +
            "{\"Error\":\"Amount is less than 4 \",\"Code\":\"7\"},\n" +
            "{\"Error\":\"Amount is greater than 25000 \",\"Code\":\"8\"},\n" +
            "{\"Error\":\"Amount is not numeric \",\"Code\":\"9\"},\n" +
            "{\"Error\":\"Mobile number is not numeric \",\"Code\":\"10\"},\n" +
            "{\"Error\":\"Mobile number is not valid \",\"Code\":\"11\"},\n" +
            "{\"Error\":\"Subscriber ID or DTH ID is not numeric \",\"Code\":\"12\"},\n" +
            "{\"Error\":\"Amount is less than 100 \",\"Code\":\"13\"},\n" +
            "{\"Error\":\"Amount is greater than 25000 \",\"Code\":\"14\"},\n" +
            "{\"Error\":\"Amount is less than 100 \",\"Code\":\"15\"},\n" +
            "{\"Error\":\"Amount is greater than 25000 \",\"Code\":\"16\"},\n" +
            "{\"Error\":\"Amount is not numeric \",\"Code\":\"17\"},\n" +
            "{\"Error\":\"Mobile number is not numeric \",\"Code\":\"18\"},\n" +
            "{\"Error\":\"Mobile number is not valid \",\"Code\":\"19\"},\n" +
            "{\"Error\":\"No IP address linked \",\"Code\":\"20\"},\n" +
            "{\"Error\":\"IP address not matched \",\"Code\":\"21\"},\n" +
            "{\"Error\":\"Balance is less than 10 \",\"Code\":\"22\"},\n" +
            "{\"Error\":\"Balance is less than requested recharge amount \",\"Code\":\"23\"},\n" +
            "{\"Error\":\"Internal server error, try again after 2 seconds \",\"Code\":\"24\"},\n" +
            "{\"Error\":\"Same number with same amount not allowed within 60 seconds \",\"Code\":\"25\"},\n" +
            "{\"Error\":\"Service number barred temporary \",\"Code\":\"102\"},\n" +
            "{\"Error\":\"Operator-jolo internal error \",\"Code\":\"103\"},\n" +
            "{\"Error\":\"Unknown operator \",\"Code\":\"104\"},\n" +
            "{\"Error\":\"Recharge disabled on this operator \",\"Code\":\"105\"},\n" +
            "{\"Error\":\"Operator not allowed \",\"Code\":\"106\"},\n" +
            "{\"Error\":\"Min. amount must be 5 for this prepaid \",\"Code\":\"107\"},\n" +
            "{\"Error\":\"Max. amount set limit exceeded for this prepaid \",\"Code\":\"108\"},\n" +
            "{\"Error\":\"This recharge already in progress at operator end \",\"Code\":\"109\"},\n" +
            "{\"Error\":\"Unknown operation \",\"Code\":\"110\"},\n" +
            "{\"Error\":\"No valid operator set for DTH \",\"Code\":\"111\"},\n" +
            "{\"Error\":\"Repeated recharge not allowed within set limit \",\"Code\":\"112\"},\n" +
            "{\"Error\":\"Operator-jolo internal error \",\"Code\":\"113-151\"},\n" +
            "{\"Error\":\"Amount / denomination not accepted by this operator \",\"Code\":\"152-153\"},\n" +
            "{\"Error\":\"Subscriber ID or DTH ID is not valid \",\"Code\":\"154\"},\n" +
            "{\"Error\":\"Retry after 5 minutes \",\"Code\":\"155\"},\n" +
            "{\"Error\":\"Customer exceeded per day attempts on this number \",\"Code\":\"162\"},\n" +
            "{\"Error\":\"Amount / denomination barred \",\"Code\":\"163\"},\n" +
            "{\"Error\":\"Mobile number or Subscriber ID or DTH ID barred \",\"Code\":\"164\"},\n" +
            "{\"Error\":\"Invalid denomination \",\"Code\":\"165\"},\n" +
            "{\"Error\":\"Invalid mobile number or Subscriber ID or DTH ID \",\"Code\":\"166\"},\n" +
            "{\"Error\":\"Multiple transaction error from operator on this number \",\"Code\":\"167\"},\n" +
            "{\"Error\":\"Temporary operator end error \",\"Code\":\"168-171\"},\n" +
            "{\"Error\":\"Operator is down at this moment \",\"Code\":\"172-173\"},\n" +
            "{\"Error\":\"Parameter missing on url \",\"Code\":\"186-190\"},\n" +
            "{\"Error\":\"Amount is not accepted by operator \",\"Code\":\"191\"},\n" +
            "{\"Error\":\"Invalid date of birth Format \",\"Code\":\"192-193\"},\n" +
            "{\"Error\":\"Invalid customer account number \",\"Code\":\"194\"},\n" +
            "{\"Error\":\"Invalid service number \",\"Code\":\"195\"},\n" +
            "{\"Error\":\"Invalid policy number \",\"Code\":\"196\"},\n" +
            "{\"Error\":\"Multiple transaction error from operator on this number \",\"Code\":\"197\"},\n" +
            "{\"Error\":\"Amount is invalid \",\"Code\":\"198\"},\n" +
            "{\"Error\":\"Biller service is down at this moment \",\"Code\":\"199-203\"},\n" +
            "{\"Error\":\"Account not found \",\"Code\":\"204\"},\n" +
            "{\"Error\":\"Agent blocked temporary \",\"Code\":\"205\"},\n" +
            "{\"Error\":\"Agent not found \",\"Code\":\"206\"},\n" +
            "{\"Error\":\"Invalid account number \",\"Code\":\"207\"},\n" +
            "{\"Error\":\"Invalid amount \",\"Code\":\"208\"},\n" +
            "{\"Error\":\"Operator-jolo internal error \",\"Code\":\"209-214\"},\n" +
            "{\"Error\":\"Denomination not found \",\"Code\":\"215\"},\n" +
            "{\"Error\":\"Service agent not found \",\"Code\":\"216\"},\n" +
            "{\"Error\":\"Threshold reached \",\"Code\":\"217\"},\n" +
            "{\"Error\":\"Template not found \",\"Code\":\"218\"},\n" +
            "{\"Error\":\"Reason not found \",\"Code\":\"219\"},\n" +
            "{\"Error\":\"Operator-jolo internal error \",\"Code\":\"220-224\"},\n" +
            "{\"Error\":\"Operator technical failure \",\"Code\":\"225\"},\n" +
            "{\"Error\":\"Operator timed out \",\"Code\":\"226\"},\n" +
            "{\"Error\":\"Unknown error \",\"Code\":\"227\"},\n" +
            "{\"Error\":\"Unexpected Error \",\"Code\":\"229\"},\n" +
            "{\"Error\":\"Invalid operator \",\"Code\":\"230\"},\n" +
            "{\"Error\":\"Subscriber Id validation failure \",\"Code\":\"231\"},\n" +
            "{\"Error\":\"Operator blocked \",\"Code\":\"232\"},\n" +
            "{\"Error\":\"Operator internal server error \",\"Code\":\"233\"},\n" +
            "{\"Error\":\"Validation failure \",\"Code\":\"235\"},\n" +
            "{\"Error\":\"Invalid source IP \",\"Code\":\"236\"},\n" +
            "{\"Error\":\"Operator temporary blocked \",\"Code\":\"237\"},\n" +
            "{\"Error\":\"Operator is not active \",\"Code\":\"238\"},\n" +
            "{\"Error\":\"Amount validation failure \",\"Code\":\"239\"},\n" +
            "{\"Error\":\"Transaction under process \",\"Code\":\"240\"},\n" +
            "{\"Error\":\"Request parameters are incomplete \",\"Code\":\"241\"},\n" +
            "{\"Error\":\"User access denied \",\"Code\":\"242\"},\n" +
            "{\"Error\":\"Invalid credentials \",\"Code\":\"243\"},\n" +
            "{\"Error\":\"Invalid access token \",\"Code\":\"244\"},\n" +
            "{\"Error\":\"Operator-jolo internal error \",\"Code\":\"245\"},\n" +
            "{\"Error\":\"Operator-jolo internal error \",\"Code\":\"246\"},\n" +
            "{\"Error\":\"Invalid service provider \",\"Code\":\"247\"},\n" +
            "{\"Error\":\"Duplicate transaction ID \",\"Code\":\"248\"},\n" +
            "{\"Error\":\"Try after 15 minutes \",\"Code\":\"249\"},\n" +
            "{\"Error\":\"Invalid account number \",\"Code\":\"250\"},\n" +
            "{\"Error\":\"Invalid refill amount \",\"Code\":\"251\"},\n" +
            "{\"Error\":\"Denomination temporarily barred \",\"Code\":\"252\"},\n" +
            "{\"Error\":\"Refill barred temporarily \",\"Code\":\"253\"},\n" +
            "{\"Error\":\"Service provider error \",\"Code\":\"254\"},\n" +
            "{\"Error\":\"Service provider downtime \",\"Code\":\"255\"},\n" +
            "{\"Error\":\"Unknown error description \",\"Code\":\"256\"},\n" +
            "{\"Error\":\"Invalid error code \",\"Code\":\"257\"},\n" +
            "{\"Error\":\"Invalid response type \",\"Code\":\"258\"},\n" +
            "{\"Error\":\"Invalid transaction ID \",\"Code\":\"259\"},\n" +
            "{\"Error\":\"Transaction status unavailable \",\"Code\":\"260\"},\n" +
            "{\"Error\":\"Internal processing error \",\"Code\":\"261\"},\n" +
            "{\"Error\":\"System Error \",\"Code\":\"262\"},\n" +
            "{\"Error\":\"Transaction refund processed \",\"Code\":\"263\"},\n" +
            "{\"Error\":\"Transaction dispute error \",\"Code\":\"264\"},\n" +
            "{\"Error\":\"Operator code not valid for your account \",\"Code\":\"300\"},\n" +
            "{\"Error\":\"Unknown error \",\"Code\":\"360\"}\n" +
            "],\n" +
            "\"Plans\":[\n" +
            "{\"Code\":\"TUP\",\"Name\":\"Top-up Recharge\"},\n" +
            "{\"Code\":\"FTT\",\"Name\":\"Full Talk-time Recharge\"},\n" +
            "{\"Code\":\"2G\",\"Name\":\"2G Data Recharge\"},\n" +
            "{\"Code\":\"3G\",\"Name\":\"3G/4G Data Recharge\"},\n" +
            "{\"Code\":\"SMS\",\"Name\":\"SMS Pack Recharge\"},\n" +
            "{\"Code\":\"LSC\",\"Name\":\"Local/STD/ISD Call Recharge\"},\n" +
            "{\"Code\":\"OTR\",\"Name\":\"Other Recharge\"},\n" +
            "{\"Code\":\"RMG\",\"Name\":\"National/International Roaming Recharge\"}\n" +
            "]\n" +
            "}\n";


    public static final String std_code = "[{\"name\":\"Afghanistan\",\"dial_code\":\"+93\",\"code\":\"AF\"},{\"name\":\"Albania\",\"dial_code\":\"+355\",\"code\":\"AL\"},{\"name\":\"Algeria\",\"dial_code\":\"+213\",\"code\":\"DZ\"},{\"name\":\"AmericanSamoa\",\"dial_code\":\"+1684\",\"code\":\"AS\"},{\"name\":\"Andorra\",\"dial_code\":\"+376\",\"code\":\"AD\"},{\"name\":\"Angola\",\"dial_code\":\"+244\",\"code\":\"AO\"},{\"name\":\"Anguilla\",\"dial_code\":\"+1264\",\"code\":\"AI\"},{\"name\":\"Antigua and Barbuda\",\"dial_code\":\"+1268\",\"code\":\"AG\"},{\"name\":\"Argentina\",\"dial_code\":\"+54\",\"code\":\"AR\"},{\"name\":\"Armenia\",\"dial_code\":\"+374\",\"code\":\"AM\"},{\"name\":\"Aruba\",\"dial_code\":\"+297\",\"code\":\"AW\"},{\"name\":\"Australia\",\"dial_code\":\"+61\",\"code\":\"AU\"},{\"name\":\"Austria\",\"dial_code\":\"+43\",\"code\":\"AT\"},{\"name\":\"Azerbaijan\",\"dial_code\":\"+994\",\"code\":\"AZ\"},{\"name\":\"Bahamas\",\"dial_code\":\"+1242\",\"code\":\"BS\"},{\"name\":\"Bahrain\",\"dial_code\":\"+973\",\"code\":\"BH\"},{\"name\":\"Bangladesh\",\"dial_code\":\"+880\",\"code\":\"BD\"},{\"name\":\"Barbados\",\"dial_code\":\"+1246\",\"code\":\"BB\"},{\"name\":\"Belarus\",\"dial_code\":\"+375\",\"code\":\"BY\"},{\"name\":\"Belgium\",\"dial_code\":\"+32\",\"code\":\"BE\"},{\"name\":\"Belize\",\"dial_code\":\"+501\",\"code\":\"BZ\"},{\"name\":\"Benin\",\"dial_code\":\"+229\",\"code\":\"BJ\"},{\"name\":\"Bermuda\",\"dial_code\":\"+1441\",\"code\":\"BM\"},{\"name\":\"Bhutan\",\"dial_code\":\"+975\",\"code\":\"BT\"},{\"name\":\"Bosnia and Herzegovina\",\"dial_code\":\"+387\",\"code\":\"BA\"},{\"name\":\"Botswana\",\"dial_code\":\"+267\",\"code\":\"BW\"},{\"name\":\"Brazil\",\"dial_code\":\"+55\",\"code\":\"BR\"},{\"name\":\"British Indian Ocean Territory\",\"dial_code\":\"+246\",\"code\":\"IO\"},{\"name\":\"Bulgaria\",\"dial_code\":\"+359\",\"code\":\"BG\"},{\"name\":\"Burkina Faso\",\"dial_code\":\"+226\",\"code\":\"BF\"},{\"name\":\"Burundi\",\"dial_code\":\"+257\",\"code\":\"BI\"},{\"name\":\"Cambodia\",\"dial_code\":\"+855\",\"code\":\"KH\"},{\"name\":\"Cameroon\",\"dial_code\":\"+237\",\"code\":\"CM\"},{\"name\":\"Canada\",\"dial_code\":\"+1\",\"code\":\"CA\"},{\"name\":\"Cape Verde\",\"dial_code\":\"+238\",\"code\":\"CV\"},{\"name\":\"Cayman Islands\",\"dial_code\":\"+345\",\"code\":\"KY\"},{\"name\":\"Central African Republic\",\"dial_code\":\"+236\",\"code\":\"CF\"},{\"name\":\"Chad\",\"dial_code\":\"+235\",\"code\":\"TD\"},{\"name\":\"Chile\",\"dial_code\":\"+56\",\"code\":\"CL\"},{\"name\":\"China\",\"dial_code\":\"+86\",\"code\":\"CN\"},{\"name\":\"Christmas Island\",\"dial_code\":\"+61\",\"code\":\"CX\"},{\"name\":\"Colombia\",\"dial_code\":\"+57\",\"code\":\"CO\"},{\"name\":\"Comoros\",\"dial_code\":\"+269\",\"code\":\"KM\"},{\"name\":\"Congo\",\"dial_code\":\"+242\",\"code\":\"CG\"},{\"name\":\"Cook Islands\",\"dial_code\":\"+682\",\"code\":\"CK\"},{\"name\":\"Costa Rica\",\"dial_code\":\"+506\",\"code\":\"CR\"},{\"name\":\"Croatia\",\"dial_code\":\"+385\",\"code\":\"HR\"},{\"name\":\"Cuba\",\"dial_code\":\"+53\",\"code\":\"CU\"},{\"name\":\"Cyprus\",\"dial_code\":\"+537\",\"code\":\"CY\"},{\"name\":\"Czech Republic\",\"dial_code\":\"+420\",\"code\":\"CZ\"},{\"name\":\"Denmark\",\"dial_code\":\"+45\",\"code\":\"DK\"},{\"name\":\"Djibouti\",\"dial_code\":\"+253\",\"code\":\"DJ\"},{\"name\":\"Dominica\",\"dial_code\":\"+1767\",\"code\":\"DM\"},{\"name\":\"Dominican Republic\",\"dial_code\":\"+1849\",\"code\":\"DO\"},{\"name\":\"Ecuador\",\"dial_code\":\"+593\",\"code\":\"EC\"},{\"name\":\"Egypt\",\"dial_code\":\"+20\",\"code\":\"EG\"},{\"name\":\"El Salvador\",\"dial_code\":\"+503\",\"code\":\"SV\"},{\"name\":\"Equatorial Guinea\",\"dial_code\":\"+240\",\"code\":\"GQ\"},{\"name\":\"Eritrea\",\"dial_code\":\"+291\",\"code\":\"ER\"},{\"name\":\"Estonia\",\"dial_code\":\"+372\",\"code\":\"EE\"},{\"name\":\"Ethiopia\",\"dial_code\":\"+251\",\"code\":\"ET\"},{\"name\":\"Faroe Islands\",\"dial_code\":\"+298\",\"code\":\"FO\"},{\"name\":\"Fiji\",\"dial_code\":\"+679\",\"code\":\"FJ\"},{\"name\":\"Finland\",\"dial_code\":\"+358\",\"code\":\"FI\"},{\"name\":\"France\",\"dial_code\":\"+33\",\"code\":\"FR\"},{\"name\":\"French Guiana\",\"dial_code\":\"+594\",\"code\":\"GF\"},{\"name\":\"French Polynesia\",\"dial_code\":\"+689\",\"code\":\"PF\"},{\"name\":\"Gabon\",\"dial_code\":\"+241\",\"code\":\"GA\"},{\"name\":\"Gambia\",\"dial_code\":\"+220\",\"code\":\"GM\"},{\"name\":\"Georgia\",\"dial_code\":\"+995\",\"code\":\"GE\"},{\"name\":\"Germany\",\"dial_code\":\"+49\",\"code\":\"DE\"},{\"name\":\"Ghana\",\"dial_code\":\"+233\",\"code\":\"GH\"},{\"name\":\"Gibraltar\",\"dial_code\":\"+350\",\"code\":\"GI\"},{\"name\":\"Greece\",\"dial_code\":\"+30\",\"code\":\"GR\"},{\"name\":\"Greenland\",\"dial_code\":\"+299\",\"code\":\"GL\"},{\"name\":\"Grenada\",\"dial_code\":\"+1473\",\"code\":\"GD\"},{\"name\":\"Guadeloupe\",\"dial_code\":\"+590\",\"code\":\"GP\"},{\"name\":\"Guam\",\"dial_code\":\"+1671\",\"code\":\"GU\"},{\"name\":\"Guatemala\",\"dial_code\":\"+502\",\"code\":\"GT\"},{\"name\":\"Guinea\",\"dial_code\":\"+224\",\"code\":\"GN\"},{\"name\":\"Guinea-Bissau\",\"dial_code\":\"+245\",\"code\":\"GW\"},{\"name\":\"Guyana\",\"dial_code\":\"+595\",\"code\":\"GY\"},{\"name\":\"Haiti\",\"dial_code\":\"+509\",\"code\":\"HT\"},{\"name\":\"Honduras\",\"dial_code\":\"+504\",\"code\":\"HN\"},{\"name\":\"Hungary\",\"dial_code\":\"+36\",\"code\":\"HU\"},{\"name\":\"Iceland\",\"dial_code\":\"+354\",\"code\":\"IS\"},{\"name\":\"India\",\"dial_code\":\"+91\",\"code\":\"IN\"},{\"name\":\"Indonesia\",\"dial_code\":\"+62\",\"code\":\"ID\"},{\"name\":\"Iraq\",\"dial_code\":\"+964\",\"code\":\"IQ\"},{\"name\":\"Ireland\",\"dial_code\":\"+353\",\"code\":\"IE\"},{\"name\":\"Israel\",\"dial_code\":\"+972\",\"code\":\"IL\"},{\"name\":\"Italy\",\"dial_code\":\"+39\",\"code\":\"IT\"},{\"name\":\"Jamaica\",\"dial_code\":\"+1876\",\"code\":\"JM\"},{\"name\":\"Japan\",\"dial_code\":\"+81\",\"code\":\"JP\"},{\"name\":\"Jordan\",\"dial_code\":\"+962\",\"code\":\"JO\"},{\"name\":\"Kazakhstan\",\"dial_code\":\"+7 7\",\"code\":\"KZ\"},{\"name\":\"Kenya\",\"dial_code\":\"+254\",\"code\":\"KE\"},{\"name\":\"Kiribati\",\"dial_code\":\"+686\",\"code\":\"KI\"},{\"name\":\"Kuwait\",\"dial_code\":\"+965\",\"code\":\"KW\"},{\"name\":\"Kyrgyzstan\",\"dial_code\":\"+996\",\"code\":\"KG\"},{\"name\":\"Latvia\",\"dial_code\":\"+371\",\"code\":\"LV\"},{\"name\":\"Lebanon\",\"dial_code\":\"+961\",\"code\":\"LB\"},{\"name\":\"Lesotho\",\"dial_code\":\"+266\",\"code\":\"LS\"},{\"name\":\"Liberia\",\"dial_code\":\"+231\",\"code\":\"LR\"},{\"name\":\"Liechtenstein\",\"dial_code\":\"+423\",\"code\":\"LI\"},{\"name\":\"Lithuania\",\"dial_code\":\"+370\",\"code\":\"LT\"},{\"name\":\"Luxembourg\",\"dial_code\":\"+352\",\"code\":\"LU\"},{\"name\":\"Madagascar\",\"dial_code\":\"+261\",\"code\":\"MG\"},{\"name\":\"Malawi\",\"dial_code\":\"+265\",\"code\":\"MW\"},{\"name\":\"Malaysia\",\"dial_code\":\"+60\",\"code\":\"MY\"},{\"name\":\"Maldives\",\"dial_code\":\"+960\",\"code\":\"MV\"},{\"name\":\"Mali\",\"dial_code\":\"+223\",\"code\":\"ML\"},{\"name\":\"Malta\",\"dial_code\":\"+356\",\"code\":\"MT\"},{\"name\":\"Marshall Islands\",\"dial_code\":\"+692\",\"code\":\"MH\"},{\"name\":\"Martinique\",\"dial_code\":\"+596\",\"code\":\"MQ\"},{\"name\":\"Mauritania\",\"dial_code\":\"+222\",\"code\":\"MR\"},{\"name\":\"Mauritius\",\"dial_code\":\"+230\",\"code\":\"MU\"},{\"name\":\"Mayotte\",\"dial_code\":\"+262\",\"code\":\"YT\"},{\"name\":\"Mexico\",\"dial_code\":\"+52\",\"code\":\"MX\"},{\"name\":\"Monaco\",\"dial_code\":\"+377\",\"code\":\"MC\"},{\"name\":\"Mongolia\",\"dial_code\":\"+976\",\"code\":\"MN\"},{\"name\":\"Montenegro\",\"dial_code\":\"+382\",\"code\":\"ME\"},{\"name\":\"Montserrat\",\"dial_code\":\"+1664\",\"code\":\"MS\"},{\"name\":\"Morocco\",\"dial_code\":\"+212\",\"code\":\"MA\"},{\"name\":\"Myanmar\",\"dial_code\":\"+95\",\"code\":\"MM\"},{\"name\":\"Namibia\",\"dial_code\":\"+264\",\"code\":\"NA\"},{\"name\":\"Nauru\",\"dial_code\":\"+674\",\"code\":\"NR\"},{\"name\":\"Nepal\",\"dial_code\":\"+977\",\"code\":\"NP\"},{\"name\":\"Netherlands\",\"dial_code\":\"+31\",\"code\":\"NL\"},{\"name\":\"Netherlands Antilles\",\"dial_code\":\"+599\",\"code\":\"AN\"},{\"name\":\"New Caledonia\",\"dial_code\":\"+687\",\"code\":\"NC\"},{\"name\":\"New Zealand\",\"dial_code\":\"+64\",\"code\":\"NZ\"},{\"name\":\"Nicaragua\",\"dial_code\":\"+505\",\"code\":\"NI\"},{\"name\":\"Niger\",\"dial_code\":\"+227\",\"code\":\"NE\"},{\"name\":\"Nigeria\",\"dial_code\":\"+234\",\"code\":\"NG\"},{\"name\":\"Niue\",\"dial_code\":\"+683\",\"code\":\"NU\"},{\"name\":\"Norfolk Island\",\"dial_code\":\"+672\",\"code\":\"NF\"},{\"name\":\"Northern Mariana Islands\",\"dial_code\":\"+1670\",\"code\":\"MP\"},{\"name\":\"Norway\",\"dial_code\":\"+47\",\"code\":\"NO\"},{\"name\":\"Oman\",\"dial_code\":\"+968\",\"code\":\"OM\"},{\"name\":\"Pakistan\",\"dial_code\":\"+92\",\"code\":\"PK\"},{\"name\":\"Palau\",\"dial_code\":\"+680\",\"code\":\"PW\"},{\"name\":\"Panama\",\"dial_code\":\"+507\",\"code\":\"PA\"},{\"name\":\"Papua New Guinea\",\"dial_code\":\"+675\",\"code\":\"PG\"},{\"name\":\"Paraguay\",\"dial_code\":\"+595\",\"code\":\"PY\"},{\"name\":\"Peru\",\"dial_code\":\"+51\",\"code\":\"PE\"},{\"name\":\"Philippines\",\"dial_code\":\"+63\",\"code\":\"PH\"},{\"name\":\"Poland\",\"dial_code\":\"+48\",\"code\":\"PL\"},{\"name\":\"Portugal\",\"dial_code\":\"+351\",\"code\":\"PT\"},{\"name\":\"Puerto Rico\",\"dial_code\":\"+1939\",\"code\":\"PR\"},{\"name\":\"Qatar\",\"dial_code\":\"+974\",\"code\":\"QA\"},{\"name\":\"Romania\",\"dial_code\":\"+40\",\"code\":\"RO\"},{\"name\":\"Rwanda\",\"dial_code\":\"+250\",\"code\":\"RW\"},{\"name\":\"Samoa\",\"dial_code\":\"+685\",\"code\":\"WS\"},{\"name\":\"San Marino\",\"dial_code\":\"+378\",\"code\":\"SM\"},{\"name\":\"Saudi Arabia\",\"dial_code\":\"+966\",\"code\":\"SA\"},{\"name\":\"Senegal\",\"dial_code\":\"+221\",\"code\":\"SN\"},{\"name\":\"Serbia\",\"dial_code\":\"+381\",\"code\":\"RS\"},{\"name\":\"Seychelles\",\"dial_code\":\"+248\",\"code\":\"SC\"},{\"name\":\"Sierra Leone\",\"dial_code\":\"+232\",\"code\":\"SL\"},{\"name\":\"Singapore\",\"dial_code\":\"+65\",\"code\":\"SG\"},{\"name\":\"Slovakia\",\"dial_code\":\"+421\",\"code\":\"SK\"},{\"name\":\"Slovenia\",\"dial_code\":\"+386\",\"code\":\"SI\"},{\"name\":\"Solomon Islands\",\"dial_code\":\"+677\",\"code\":\"SB\"},{\"name\":\"South Africa\",\"dial_code\":\"+27\",\"code\":\"ZA\"},{\"name\":\"South Georgia and the South Sandwich Islands\",\"dial_code\":\"+500\",\"code\":\"GS\"},{\"name\":\"Spain\",\"dial_code\":\"+34\",\"code\":\"ES\"},{\"name\":\"Sri Lanka\",\"dial_code\":\"+94\",\"code\":\"LK\"},{\"name\":\"Sudan\",\"dial_code\":\"+249\",\"code\":\"SD\"},{\"name\":\"Suriname\",\"dial_code\":\"+597\",\"code\":\"SR\"},{\"name\":\"Swaziland\",\"dial_code\":\"+268\",\"code\":\"SZ\"},{\"name\":\"Sweden\",\"dial_code\":\"+46\",\"code\":\"SE\"},{\"name\":\"Switzerland\",\"dial_code\":\"+41\",\"code\":\"CH\"},{\"name\":\"Tajikistan\",\"dial_code\":\"+992\",\"code\":\"TJ\"},{\"name\":\"Thailand\",\"dial_code\":\"+66\",\"code\":\"TH\"},{\"name\":\"Togo\",\"dial_code\":\"+228\",\"code\":\"TG\"},{\"name\":\"Tokelau\",\"dial_code\":\"+690\",\"code\":\"TK\"},{\"name\":\"Tonga\",\"dial_code\":\"+676\",\"code\":\"TO\"},{\"name\":\"Trinidad and Tobago\",\"dial_code\":\"+1868\",\"code\":\"TT\"},{\"name\":\"Tunisia\",\"dial_code\":\"+216\",\"code\":\"TN\"},{\"name\":\"Turkey\",\"dial_code\":\"+90\",\"code\":\"TR\"},{\"name\":\"Turkmenistan\",\"dial_code\":\"+993\",\"code\":\"TM\"},{\"name\":\"Turks and Caicos Islands\",\"dial_code\":\"+1649\",\"code\":\"TC\"},{\"name\":\"Tuvalu\",\"dial_code\":\"+688\",\"code\":\"TV\"},{\"name\":\"Uganda\",\"dial_code\":\"+256\",\"code\":\"UG\"},{\"name\":\"Ukraine\",\"dial_code\":\"+380\",\"code\":\"UA\"},{\"name\":\"United Arab Emirates\",\"dial_code\":\"+971\",\"code\":\"AE\"},{\"name\":\"United Kingdom\",\"dial_code\":\"+44\",\"code\":\"GB\"},{\"name\":\"United States\",\"dial_code\":\"+1\",\"code\":\"US\"},{\"name\":\"Uruguay\",\"dial_code\":\"+598\",\"code\":\"UY\"},{\"name\":\"Uzbekistan\",\"dial_code\":\"+998\",\"code\":\"UZ\"},{\"name\":\"Vanuatu\",\"dial_code\":\"+678\",\"code\":\"VU\"},{\"name\":\"Wallis and Futuna\",\"dial_code\":\"+681\",\"code\":\"WF\"},{\"name\":\"Yemen\",\"dial_code\":\"+967\",\"code\":\"YE\"},{\"name\":\"Zambia\",\"dial_code\":\"+260\",\"code\":\"ZM\"},{\"name\":\"Zimbabwe\",\"dial_code\":\"+263\",\"code\":\"ZW\"},{\"name\":\"Ã…land Islands\",\"dial_code\":\"+358\",\"code\":\"AX\"},{\"name\":\"Antarctica\",\"dial_code\":\"+672\",\"code\":\"AQ\"},{\"name\":\"Bolivia, Plurinational State of\",\"dial_code\":\"+591\",\"code\":\"BO\"},{\"name\":\"Brunei Darussalam\",\"dial_code\":\"+673\",\"code\":\"BN\"},{\"name\":\"Cocos (Keeling) Islands\",\"dial_code\":\"+61\",\"code\":\"CC\"},{\"name\":\"Congo, The Democratic Republic of the\",\"dial_code\":\"+243\",\"code\":\"CD\"},{\"name\":\"Cote d'Ivoire\",\"dial_code\":\"+225\",\"code\":\"CI\"},{\"name\":\"Falkland Islands (Malvinas)\",\"dial_code\":\"+500\",\"code\":\"FK\"},{\"name\":\"Guernsey\",\"dial_code\":\"+44\",\"code\":\"GG\"},{\"name\":\"Holy See (Vatican City State)\",\"dial_code\":\"+379\",\"code\":\"VA\"},{\"name\":\"Hong Kong\",\"dial_code\":\"+852\",\"code\":\"HK\"},{\"name\":\"Iran, Islamic Republic of\",\"dial_code\":\"+98\",\"code\":\"IR\"},{\"name\":\"Isle of Man\",\"dial_code\":\"+44\",\"code\":\"IM\"},{\"name\":\"Jersey\",\"dial_code\":\"+44\",\"code\":\"JE\"},{\"name\":\"Korea, Democratic People's Republic of\",\"dial_code\":\"+850\",\"code\":\"KP\"},{\"name\":\"Korea, Republic of\",\"dial_code\":\"+82\",\"code\":\"KR\"},{\"name\":\"Lao People's Democratic Republic\",\"dial_code\":\"+856\",\"code\":\"LA\"},{\"name\":\"Libyan Arab Jamahiriya\",\"dial_code\":\"+218\",\"code\":\"LY\"},{\"name\":\"Macao\",\"dial_code\":\"+853\",\"code\":\"MO\"},{\"name\":\"Macedonia, The Former Yugoslav Republic of\",\"dial_code\":\"+389\",\"code\":\"MK\"},{\"name\":\"Micronesia, Federated States of\",\"dial_code\":\"+691\",\"code\":\"FM\"},{\"name\":\"Moldova, Republic of\",\"dial_code\":\"+373\",\"code\":\"MD\"},{\"name\":\"Mozambique\",\"dial_code\":\"+258\",\"code\":\"MZ\"},{\"name\":\"Palestinian Territory, Occupied\",\"dial_code\":\"+970\",\"code\":\"PS\"},{\"name\":\"Pitcairn\",\"dial_code\":\"+872\",\"code\":\"PN\"},{\"name\":\"RÃ©union\",\"dial_code\":\"+262\",\"code\":\"RE\"},{\"name\":\"Russia\",\"dial_code\":\"+7\",\"code\":\"RU\"},{\"name\":\"Saint BarthÃ©lemy\",\"dial_code\":\"+590\",\"code\":\"BL\"},{\"name\":\"Saint Helena, Ascension and Tristan Da Cunha\",\"dial_code\":\"+290\",\"code\":\"SH\"},{\"name\":\"Saint Kitts and Nevis\",\"dial_code\":\"+1869\",\"code\":\"KN\"},{\"name\":\"Saint Lucia\",\"dial_code\":\"+1758\",\"code\":\"LC\"},{\"name\":\"Saint Martin\",\"dial_code\":\"+590\",\"code\":\"MF\"},{\"name\":\"Saint Pierre and Miquelon\",\"dial_code\":\"+508\",\"code\":\"PM\"},{\"name\":\"Saint Vincent and the Grenadines\",\"dial_code\":\"+1784\",\"code\":\"VC\"},{\"name\":\"Sao Tome and Principe\",\"dial_code\":\"+239\",\"code\":\"ST\"},{\"name\":\"Somalia\",\"dial_code\":\"+252\",\"code\":\"SO\"},{\"name\":\"Svalbard and Jan Mayen\",\"dial_code\":\"+47\",\"code\":\"SJ\"},{\"name\":\"Syrian Arab Republic\",\"dial_code\":\"+963\",\"code\":\"SY\"},{\"name\":\"Taiwan, Province of China\",\"dial_code\":\"+886\",\"code\":\"TW\"},{\"name\":\"Tanzania, United Republic of\",\"dial_code\":\"+255\",\"code\":\"TZ\"},{\"name\":\"Timor-Leste\",\"dial_code\":\"+670\",\"code\":\"TL\"},{\"name\":\"Venezuela, Bolivarian Republic of\",\"dial_code\":\"+58\",\"code\":\"VE\"},{\"name\":\"Viet Nam\",\"dial_code\":\"+84\",\"code\":\"VN\"},{\"name\":\"Virgin Islands, British\",\"dial_code\":\"+1284\",\"code\":\"VG\"},{\"name\":\"Virgin Islands, U.S.\",\"dial_code\":\"+1340\",\"code\":\"VI\"}]";


}
