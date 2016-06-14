package android.particles.com.retrofit.component.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.particles.com.retrofit.common.MyApplication;

/**
 * Created by YLM on 2016/6/14.
 */
public class GetCopyResult
{
    private String result;
    private ClipboardManager clipboardManager;
    private ClipData clipData;
    private Context context;

    private String getCopy()
    {
        String temp;
        ClipData abc = clipboardManager.getPrimaryClip();
        ClipData.Item item = abc.getItemAt(0);
        temp = item.getText().toString();
        return temp;
    }
    public GetCopyResult()
    {
        context = MyApplication.getContext();
        clipboardManager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    public String getResult()
    {
        result = getCopy();
        return result;
    }


}
