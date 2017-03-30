package com.chablis.lilosoft.utils;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.chablis.lilosoft.activity.LoadActivity;
import com.chablis.lilosoft.activity.MainActivity;
import com.chablis.lilosoft.base.Global;
import com.chablis.lilosoft.db.DatabaseAdapter;
import com.chablis.lilosoft.db.dbConfig;
import com.chablis.lilosoft.model.ClientInfo;
import com.chablis.lilosoft.model.TDDept;
import com.chablis.lilosoft.model.TDForm;
import com.chablis.lilosoft.model.TDMaterials;
import com.chablis.lilosoft.model.TDPublish;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.chablis.lilosoft.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UpdateUtil {
	/* 下载中 */
	private static final int ERROR = 0;
	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;
	/* 解压完成 */
	private static final int UNZIP_FINISH = 3;
	/* 更新数据库完成 */
	private static final int UPDATE_FINISH = 4;
	/* 更新完成 */
	private static final int BACKUP_FINFISH = 5;
	/* 提示信息*/
	private static final int SEND_TOAST = 6;
	/* 下载保存路径 */
	private String mSavePath;
	/* 记录进度条数量 */
	private int progress;
	/* 是否取消更新 */
	private boolean cancelUpdate = false;

	private Context mContext;
	/* 更新进度条 */
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				// 正在下载
				case DOWNLOAD:
					// 设置进度条位置
					mProgress.setProgress(progress);
					break;
				case DOWNLOAD_FINISH:
					// 下载完成
					mDownloadDialog.dismiss();
					PromptManager.showProgressDialog(mContext, "正在解压数据包...");
					new UnZipThread().start();
					break;
				case UNZIP_FINISH:
					// 解压完成，更新数据库
					PromptManager.closeProgressDialog();
					PromptManager.showProgressDialog(mContext, "正在更新数据库...");
					new UpdateDatabaseTherad().start();
					break;
				case UPDATE_FINISH:
					PromptManager.closeProgressDialog();
					if(mContext instanceof LoadActivity){
						((LoadActivity)mContext).LoadToMainActivity();
					}else if(mContext instanceof MainActivity){
						//重启程序
						restart();
					}
					break;
				case BACKUP_FINFISH:
					PromptManager.closeProgressDialog();
					showDownloadDialog();
					break;
				case ERROR:
					//退出程序
					ToastUtils.showToast(mContext, "程序出现错误");
					android.os.Process.killProcess(android.os.Process.myPid());
					break;
				case SEND_TOAST:
					PromptManager.closeProgressDialog();
					String note = msg.obj.toString();
					ToastUtils.showToast(mContext, note);
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							SystemClock.sleep(3000);
							mHandler.sendEmptyMessage(ERROR);
						}
					}).start();
					break;
				default:
					break;
			}
		};
	};
	private WebUtil webUtil;
	private Gson gson;

	public UpdateUtil(Context context) {
		this.mContext = context;
		webUtil = new WebUtil();
		gson = new Gson();
	}

	protected void restart() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(mContext.getApplicationContext(), MainActivity.class);
		PendingIntent restartIntent = PendingIntent.getActivity(
				mContext.getApplicationContext(), 0, intent,
				PendingIntent.FLAG_ONE_SHOT);
//        //退出程序
		AlarmManager mgr = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
				restartIntent); // 1秒钟后重启应用
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	protected void updateDataBase() {
		// TODO Auto-generated method stub
		Type type_dept = new TypeToken<ArrayList<TDDept>>() {
		}.getType();
		Type type_form = new TypeToken<ArrayList<TDForm>>() {
		}.getType();
		Type type_materials = new TypeToken<ArrayList<TDMaterials>>() {
		}.getType();

		try {
			//获取基本信息
			String tdinfo = webUtil.GetTDClientInfo(mContext);
			if(!TextUtils.isEmpty(tdinfo)){
				ClientInfo clientInfo = gson.fromJson(tdinfo, ClientInfo.class);
				TDPublish publish = gson.fromJson(webUtil.GetTDPublish(), TDPublish.class);
				clientInfo.setPublish_type(publish.getPublish_type());
				DatabaseAdapter client_adapter = new DatabaseAdapter(mContext,
						dbConfig.DB_NAME, dbConfig.DB_VERSION,
						dbConfig.CreateSql_EGS_TD_CLIENTINFO, dbConfig.EGS_TD_CLIENTINFO,
						"PUBLISH_TYPE");
				client_adapter.open();
				client_adapter.insert(clientInfo);
				client_adapter.close();
			}else{
				if(mContext instanceof Activity){
					Message msg = Message.obtain();
					msg.what = SEND_TOAST;
					msg.obj = "获取终端信息失败";
					mHandler.sendMessage(msg);
				}else{
					Log.e("newegov", "context非activity");
				}
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			Message msg = Message.obtain();
			msg.what = SEND_TOAST;
			msg.obj = "获取终端信息失败";
			mHandler.sendMessage(msg);
		}

		try {
			//更新部门数据
			String deptinfo = webUtil.GetTableOfDept(Global.areacode).toUpperCase();
			if(!TextUtils.isEmpty(deptinfo)){
				ArrayList<TDDept> list_dept = gson.fromJson(
						deptinfo, type_dept);
				DatabaseAdapter dept_adapter = new DatabaseAdapter(mContext,
						dbConfig.DB_NAME, dbConfig.DB_VERSION,
						dbConfig.CreateSql_EGS_TD_DEPT, dbConfig.EGS_TD_DEPT,
						dbConfig.DB_TABLE_TabType_PK);
				dept_adapter.open();
				dept_adapter.insertAll(list_dept);
				dept_adapter.close();
			}else{
				if(mContext instanceof Activity){
					Message msg = Message.obtain();
					msg.what = SEND_TOAST;
					msg.obj = "获取部门表失败";
					mHandler.sendMessage(msg);
				}else{
					Log.e("newegov", "context非activity");
				}
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			Message msg = Message.obtain();
			msg.what = SEND_TOAST;
			msg.obj = "获取部门表失败";
			mHandler.sendMessage(msg);
		}

		try {
			//更新表单数据
			String forminfo = webUtil.GetTableOfForm(Global.areacode).toUpperCase();
//			CommonUtil.saveLog("2016/6/23", forminfo);
			if(!TextUtils.isEmpty(forminfo)){
				ArrayList<TDForm> list_form = gson.fromJson(
						forminfo, type_form);
				DatabaseAdapter form_adapter = new DatabaseAdapter(mContext,
						dbConfig.DB_NAME, dbConfig.DB_VERSION,
						dbConfig.CreateSql_EGS_TD_FORM, dbConfig.EGS_TD_FORM,
						dbConfig.DB_TABLE_TabType_PK);
				form_adapter.open();
				form_adapter.insertAll(list_form);
				form_adapter.close();
			}else{
				if(mContext instanceof Activity){
					Message msg = Message.obtain();
					msg.what = SEND_TOAST;
					msg.obj = "获取表单失败";
					mHandler.sendMessage(msg);
				}else{
					Log.e("newegov", "context非activity");
				}
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			Message msg = Message.obtain();
			msg.what = SEND_TOAST;
			msg.obj = "获取表单失败";
			mHandler.sendMessage(msg);
		}

		try {
			//更新资料
			String materialsinfo = webUtil.GetTableOfMaterials(Global.areacode).toUpperCase();
			if(!TextUtils.isEmpty(materialsinfo)){
				ArrayList<TDMaterials> list_materials = gson.fromJson(
						materialsinfo, type_materials);
				DatabaseAdapter materials_adapter = new DatabaseAdapter(mContext,
						dbConfig.DB_NAME, dbConfig.DB_VERSION,
						dbConfig.CreateSql_EGS_TD_MATERIALS, dbConfig.EGS_TD_MATERIALS,
						dbConfig.DB_TABLE_TabType_PK);
				materials_adapter.open();
				materials_adapter.insertAll(list_materials);
				materials_adapter.close();
			}else{
				if(mContext instanceof Activity){
					Message msg = Message.obtain();
					msg.what = SEND_TOAST;
					msg.obj = "获取资料失败";
					mHandler.sendMessage(msg);
				}else{
					Log.e("newegov", "context非activity");
				}
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			Message msg = Message.obtain();
			msg.what = SEND_TOAST;
			msg.obj = "获取资料失败";
			mHandler.sendMessage(msg);
		}


		mHandler.sendEmptyMessage(UPDATE_FINISH);
	}

	public class UpdateDatabaseTherad extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			updateDataBase();
		}
	}

	public void exit(){
		android.os.Process.killProcess(android.os.Process.myPid());
	};

	public void starUpdate(){
		ToastUtils.showToast(mContext, "准备更新");
		if(mContext instanceof LoadActivity){
			PromptManager.showProgressDialog(mContext, "正在备份文件");
			new BackupThread().start();
		}else if(mContext instanceof MainActivity){
			showDownloadDialog();
		}
//		mHandler.sendEmptyMessage(UNZIP_FINISH);
	}

	public void starUpdateByTopGridActivity(){
		//备份并删除文件
		PromptManager.showProgressDialog(mContext, "正在备份文件");
		new BackupThread().start();
	}

	public class BackupThread extends Thread{
		@Override
		public void run() {
			super.run();
			FileUtil fu = new FileUtil();
			try {
				//1.删除原始备份
				fu.deleteFile(Global.getSDCardPath(mContext, Global.backup));
				//2.备份数据
				fu.copyFile(Global.getSDCardPath(mContext, Global.AppFileRootPath), Global.getSDCardPath(mContext, Global.backup));
				//3.删除现有数据
//				fu.deleteFile(Global.getSDCardPath(mContext, Global.AppFileRootPath));
				//4.重新下载数据
			} catch (Exception e) {
				// TODO: handle exception
			}
			mHandler.sendEmptyMessage(BACKUP_FINFISH);
		}
	}

	/**
	 * 显示软件下载对话框
	 */
	private void showDownloadDialog() {
		// 构造软件下载对话框
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_updating);
		// 给下载对话框增加进度条
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.softupdate_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		builder.setView(v);
		// 取消更新
		builder.setNegativeButton(R.string.soft_update_cancel,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 设置取消状态
						cancelUpdate = true;
					}
				});
		mDownloadDialog = builder.create();
		mDownloadDialog.show();
		// 现在文件
		downloadApk();
	}

	/**
	 * 下载apk文件
	 */
	private void downloadApk() {
		// 启动新线程下载软件
		new downloadZipThread().start();
	}

	/**
	 * 下载文件线程
	 *
	 * @author coolszy
	 * @date 2012-4-26
	 * @blog http://blog.92coding.com
	 */
	private class downloadZipThread extends Thread {
		@Override
		public void run() {
			try {
				// 判断SD卡是否存在，并且是否具有读写权限
				String state = Environment.getExternalStorageState();
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// 获得存储卡的路径
					String sdpath = Global.AppFileRootPath + File.separator;
					mSavePath = Global.getSDCardPath(mContext, Global.AppFileRootPath)+File.separator;
					String downloadUrl = webUtil
							.GetZipDownloadUrl(Global.areacode);
					if (TextUtils.isEmpty(downloadUrl)) {
						return;
					}
					URL url = new URL(downloadUrl);

					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// 判断文件目录是否存在
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(mSavePath, Global.areacode + ".zip");
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do {
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// 下载完成
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
					fos.close();
					is.close();
				}
			} catch (Exception e) {
				ToastUtils.showToast(mContext, "无法获取数据，请检测网络情况，区域码，ip是否填写正确");
				Message msg = Message.obtain();
				msg.what = ERROR;
				mHandler.sendMessageDelayed(msg, 3000);
				e.printStackTrace();
			}
		}
	};

	public class UnZipThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				ZipUtil.unZipFiles(
						new File(Global.getSDCardPath(mContext,
								Global.AppFileRootPath)
								+ File.separator
								+ Global.areacode+".zip"), Global.getSDCardPath(
								mContext, Global.AppFileRootPath));
//				ZipUtil.unzipFiles(
//						new File(Global.getSDCardPath(mContext,
//								Global.AppFileRootPath)
//								+ File.separator
//								+ Global.areacode+".zip"), Global.getSDCardPath(
//										mContext, Global.AppFileRootPath));
			} catch (Exception e) {
				e.printStackTrace();
			}
			mHandler.sendEmptyMessage(UNZIP_FINISH);
		}
	}

}
