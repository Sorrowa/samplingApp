package com.example.samplingapp.Activities.TaskDetailBaseActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.SearchView;

import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.R;

public abstract class TaskBaseActivity extends BaseActivity {

    private SearchView searchView;
    public String searchRes=null;
    /**
     * 弹出搜索框
     */
    public void showSearchDialog(DialogListener listener){
        //layout
        View layout=getLayoutInflater().inflate(R.layout.dialog_search,null);
        Dialog dialog=new Dialog(this);
        searchView=layout.findViewById(R.id.search_text);
        dialog.setContentView(layout);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });
        dialog.show();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                dialog.dismiss();
                listener.submit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchRes=s;
                return false;
            }
        });
    }

    public interface DialogListener{
        void submit();
    }
}
