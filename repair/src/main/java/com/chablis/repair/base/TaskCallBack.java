package com.chablis.repair.base;

/**
 * Created by chablis on 2017/7/5.
 */

public interface TaskCallBack {
    String doInBackground();

//    void onPostExecute();

    void onSuccess(String result);

    void onFailure(String msg);
}
