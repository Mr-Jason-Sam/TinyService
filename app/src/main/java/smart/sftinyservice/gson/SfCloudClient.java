package smart.sftinyservice.gson;

import android.util.Log;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import smart.sftinyservice.utils.ObjectCheck;
import smart.sftinyservice.utils.SfNetUtils;

/**
 * Created by jasonsam on 2018/3/6.
 */

public class SfCloudClient {
    private final static String TAG = "SfCloudClient";
    private final static String DEVICE_URL    = "http://192.168.21.209";//"https://192.168.21.209";
    private final static String URL_ACTION = "/substance/receiveRelation";
    private final static String URL_COLON = ":";
    private final static String URL_PORT = "8080";
    private static final Gson gson = new Gson();
    private final static String ACTION_RESULT_SUCCESS = "success";

    public boolean reportBoxStatus(SpatialRelation reportMessage) {

        String postReport = DEVICE_URL + URL_COLON + URL_PORT + URL_ACTION ;

        Map<String, String> params = new TreeMap<String, String>();
        params.put("relation", gson.toJson(reportMessage));

        String actionResult = doCloudAction(postReport, params, String.class);
        if (actionResult != null && actionResult.equalsIgnoreCase(ACTION_RESULT_SUCCESS))
            return true;
        return false;
    }

    public boolean reportBoxStatus(List<SpatialRelation> reportMessage) {

        String postReport = DEVICE_URL + URL_COLON + URL_PORT + URL_ACTION ;

        Map<String, String> params = new TreeMap<String, String>();
        params.put("relation", gson.toJson(reportMessage));

        String actionResult = doCloudAction(postReport, params, String.class);
        if (actionResult != null && actionResult.equalsIgnoreCase(ACTION_RESULT_SUCCESS))
            return true;
        return false;
    }

    private static String analyzeActionResult(String actionResult) {

        try {
            ActionResult result = gson.fromJson(actionResult, ActionResult.class);
            if (result != null && result.getCode() == 0)
                return result.getMsg();
            else {
                Log.e(TAG, "analyzeActionResult, err:" + result.getMsg());
            }
        } catch(JsonSyntaxException e) {
            Log.e(TAG, "analyzeActionResultJsonSyntaxException:" + actionResult);
        }
        return "";
    }

    private static <T> T doCloudAction(String requestURL, Map<String, String> pMap, Class<T> clz) {
        //Log.e(TAG, "requestURL: " + requestURL + " pMap: " + pMap);
        String result = SfNetUtils.getResultByPost(requestURL, pMap);

        if (ObjectCheck.isEmptyString(result)) {
            Log.e(TAG, "doCloudAction, getResultByPost failed, url:" + requestURL
                    + ", paramMap:" + pMap);
            return null;
        }

        String gsonResult = analyzeActionResult(result);
        if (ObjectCheck.isEmptyString(gsonResult)) {
            Log.e(TAG, "doCloudAction, analyzeActionResult failed, url:" + requestURL
                    + ", paramMap:" + pMap + ", result:" + result);
        }

        T t = null;

        try {
            t = gson.fromJson(gsonResult, clz);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.e(TAG, "doCloudAction, gsonResult:" + gsonResult);
        }

        return t;
    }


}
