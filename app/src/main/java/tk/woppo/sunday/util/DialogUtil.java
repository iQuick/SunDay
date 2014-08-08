package tk.woppo.sunday.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import tk.woppo.sunday.R;
import tk.woppo.sunday.widget.titanic.Titanic;
import tk.woppo.sunday.widget.titanic.TitanicTextView;

/**
 * Created by Ho on 2014/7/2.
 */
public class DialogUtil {

    private static DialogUtil _Instance = null;
    private static Dialog dialog = null;

    public static DialogUtil Instance() {
        if (_Instance == null) {
            _Instance = new DialogUtil();
        }
        return _Instance;
    }


    /**
     * 显示加载对话框
     * @param context
     * @return
     */
    public Dialog createLoadingDialog(Context context) {
        return createLoadingDialog(context, null);
    }

    /**
     * 显示加载对话框
     * @param context
     * @param msg
     * @return
     */
    public Dialog createLoadingDialog(Context context, String msg) {

        Dialog loadingDialog = null;
        if (dialog == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.layout_loading, null);

            LinearLayout layout = (LinearLayout) view.findViewById(R.id.loading_view);
            TitanicTextView mTextView = (TitanicTextView) view.findViewById(R.id.tv_loading);
            Titanic mTitanic = new Titanic();

            if (msg != null) {
                mTextView.setText(msg);
            }

            loadingDialog = new Dialog(context, R.style.loading_dialog_tran);
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            return dialog;
        }
        return loadingDialog;
    }
}
