package com.travel.library.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by wisn on 2017/3/13.
 * system image  need rooted
 */

public class SuUtils {

    public static String TAG = "SuUtils";
    private static Process process;

    /**
     * 结束进程,执行操作调用即可
     */
    public static void kill(String packageName) {
        initProcess();
        work("am force-stop  " + packageName + " \n");
        close();
    }

    /**
     * @param packageName
     */
    public static void enable(String packageName) {
        initProcess();
        work("pm enable  " + packageName + " \n");
        close();
    }

    /**
     * @param packageName
     */
    public static void disable(String packageName) {
        initProcess();
        work("pm disable  " + packageName + " \n");
        close();
    }

    /**
     * @param packageName
     */
    public static void unInstall(String packageName) {
        initProcess();
        work(" uninstall " + packageName + " \n");
        close();
    }


    /**
     * 静默安装
     *
     * @param apkPath
     *
     * @return
     */
    public boolean clientInstall(String apkPath) {
        PrintWriter PrintWriter = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            PrintWriter = new PrintWriter(process.getOutputStream());
            PrintWriter.println("chmod 777 " + apkPath);
            PrintWriter.println("export LD_LIBRARY_PATH=/vendor/lib:/system/lib");
            PrintWriter.println("pm install -r " + apkPath);
//          PrintWriter.println("exit");
            PrintWriter.flush();
            PrintWriter.close();
            int value = process.waitFor();
            return returnResult(value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }

    /**
     * 启动app
     *
     * @param packageName  com.exmaple.client/.MainActivity
     * @param activityName com.exmaple.client/com.exmaple.client.MainActivity
     *
     * @return
     */
    public boolean startApp(String packageName, String activityName) {
        boolean isSuccess = false;
        String cmd = "am start -n " + packageName + "/" + activityName + " \n";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            int value = process.waitFor();
            return returnResult(value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return isSuccess;
    }


    private boolean returnResult(int value) {
        // 代表成功
        if (value == 0) {
            return true;
        } else if (value == 1) { // 失败
            return false;
        } else { // 未知情况
            return false;
        }
    }

    /**
     * @param packageName
     *
     * @return
     */
    public boolean clientUninstall(String packageName) {
        PrintWriter PrintWriter = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            PrintWriter = new PrintWriter(process.getOutputStream());
            PrintWriter.println("LD_LIBRARY_PATH=/vendor/lib:/system/lib ");
            PrintWriter.println("pm uninstall " + packageName);
            PrintWriter.flush();
            PrintWriter.close();
            int value = process.waitFor();
            return returnResult(value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }

    /**
     * @param pid
     */
    public static void killProcessByPID(int pid) {
        //android.os.Process.killProcess(pid);
        initProcess();
        work(" kill  " + pid + " \n");
        close();
    }


    /**
     * 初始化进程
     */
    private static void initProcess() {
        if (process == null)
            try {
                process = Runtime.getRuntime().exec("su");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


    /**
     * 结束进程
     */
    private static void work(String cmd) {
        OutputStream out = process.getOutputStream();
        String cmds = cmd + " \n";
        try {
            out.write(cmds.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭输出流
     */
    private static void close() {
        if (process != null)
            try {
                process.getOutputStream().close();
                process = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
