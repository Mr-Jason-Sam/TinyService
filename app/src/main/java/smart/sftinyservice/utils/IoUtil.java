/**   
 * <p><h1>Copyright:</h1><strong><a href="http://www.smart-f.cn">
 * BeiJing Smart Future Technology Co.Ltd. 2015 (c)</a></strong></p>
 */
package smart.sftinyservice.utils;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <p>
 * <h1>Copyright:</h1><strong><a href="http://www.smart-f.cn"> BeiJing Smart Future Technology Co.Ltd. 2015 (c)</a></strong>
 * </p>
 * 
 * @Title IoUtils.java
 * @Package cn.smart.droid.camera.utils
 * @Description input/output
 * @author jjj
 * @email <a href="jiangjunjie@smart-f.cn">jiangjunjie@smart-f.cn</a>
 * @date 2014��3��31�� ����7:31:14
 * @version V1.0
 */
public class IoUtil {

    public static final String TAG     = IoUtil.class.getSimpleName();
    /**
     * InputStream to String
     * 
     * @param in
     * @param encoding
     *            encode mode
     * @return
     * @author jjj
     * @see
     */
    public static String inputStreamToStringByByteArrayOutputStream(
            InputStream in, String encoding) {
        String result = "";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] buffer = new byte[2048];
        int length = 0;
        try {

            while ((length = in.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            in.close();
            result = new String(bos.toByteArray(), encoding);
        } catch (IOException e) {
            result = "";
            e.printStackTrace();
        }

        return result;
    }

    /**
     * InputStream to String
     * 
     * @param in
     * @param encoding
     *            encode mode
     * @return
     * @author jjj
     * @see
     */
    public static String inputStreamToStringByBufferedReader(InputStream in,
            String encoding) throws Exception {
        StringBuilder sBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(in,
                encoding);
        BufferedReader reader = new BufferedReader(inputStreamReader, 8192);
        String line = null;
        while ((line = reader.readLine()) != null) {
            sBuilder.append(line).append("\n");
        }
        reader.close();
        return sBuilder.toString();
    }

    public static void cleanDir(File f) {
        if (!f.exists() || !f.isDirectory()) {
            Log.e(TAG, "cleanDir, not exist or not dir.");
            return;
        }
        File[] files = f.listFiles();
        if (files == null)
            return;

        for (File file : files) {
            if (file.isDirectory()) {
                cleanDir(file);
                file.delete();
            } else if (file.isFile()) {
                file.delete();
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static long getDataPartitionFreeSize() {
        StatFs fileStats = new StatFs(Environment.getDataDirectory().getPath());  
        fileStats.restat(Environment.getDataDirectory().getPath());
        return (long) fileStats.getAvailableBlocks() * fileStats.getBlockSize(); // 注意与fileStats.getFreeBlocks()的区别
    }
}