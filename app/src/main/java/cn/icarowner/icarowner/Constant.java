package cn.icarowner.icarowner;

import cn.icarowner.icarowner.manager.PhoneManager;

/**
 * Constant
 * create by 崔婧
 * create at 2017/5/18 下午1:41
 */
public final class Constant {
    //请求地址
    private static final String REQUEST_HOST = "https://api.woshichezhu.com/";
    private static final String REQUEST_HOST_FOR_TEST = "https://dev.api.woshichezhu.com/";
    //推送地址
    private static final String PUSH_HOST = "api.woshichezhu.com";
    private static final String PUSH_HOST_FOR_TEST = "dev.api.woshichezhu.com";

    //是否是debug环境
    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    //获取请求地址
    public static String getHost() {
        if (isDebug()) {
            return REQUEST_HOST_FOR_TEST;
        } else {
            return REQUEST_HOST;
        }
    }

    //获取推送地址
    public static String getPushHost() {
        if (isDebug()) {
            return PUSH_HOST_FOR_TEST;
        } else {
            return PUSH_HOST;
        }
    }

    //微信ID
    public static final String WE_CHAT_APPID = "wx83c6b567ad37b372";
    private static final String APP_DIR = PhoneManager.getSdCardRootPath() + "/Icarowner/";//app文件目录
    public static final String IMAGE_CACHE_DIR_PATH = APP_DIR + "cache/";// 图片缓存地址
    public static final String UPLOAD_FILES_DIR_PATH = APP_DIR + "upload/";//上传文件、零时文件存放地址
    public static final String DOWNLOAD_DIR_PATH = APP_DIR + "downloads/";// 下载文件存放地址
}