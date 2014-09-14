package tk.woppo.sunday.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import tk.woppo.sunday.App;

/**
 * Created by Ho on 2014/6/26.
 */
public class ResourceUtil {

    /**
     * 读取assets中的文件
     * @param fileName 文件名
     * @return
     */
    public static String getFileFromAssets(String fileName) {

        if (StringUtil.isEmpty(fileName)) {
            return null;
        }

        try {
            StringBuilder sb = new StringBuilder("");
            InputStreamReader in = new InputStreamReader(App.getContext().getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取raw中的文件
     * @param resid
     * @return
     */
    public static String getFileFromRaw(int resid) {

        try {
            StringBuffer sb = new StringBuffer("");
            InputStreamReader in = new InputStreamReader(App.getContext().getResources().openRawResource(resid));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return  sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
