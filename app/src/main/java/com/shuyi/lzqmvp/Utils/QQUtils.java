package com.shuyi.lzqmvp.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * created by Lzq
 * on 2020/12/14 0014
 * Describe ：跳转qq
 */
public class QQUtils {

    /**
     * 跳转QQ聊天界面
     */
    public static void joinQQ(Context context, String qqNum) {
        try {
            //第二种方式：可以跳转到添加好友，如果qq号是好友了，直接聊天
            String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qqNum;//uin是发送过去的qq号码
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.show("请检查是否安装QQ");
        }
    }

    /****************
     *
     * 发起添加群流程。群号：xxxxxxxxxxx 的 key 为：xxxxxxxxxxxxx
     * 调用 xxxxxxxxxxxxxxxxxxxx 即可发起手Q客户端申请加群 xxxxxxxxx
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回false表示呼起失败
     ******************/
    public static void joinQQGroup(Context context, String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26jump_from%3Dwebapi%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context. startActivity(intent);
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
          ToastUtils.show(" 未安装手Q或安装的版本不支持");
        }
    }
}
