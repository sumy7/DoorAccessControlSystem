package com.sumy.dooraccesscontrolsystem.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;

import com.sumy.dooraccesscontrolsystem.R;
import com.sumy.dooraccesscontrolsystem.dao.CheckInDao;
import com.sumy.dooraccesscontrolsystem.entity.CheckIn;
import com.sumy.dooraccesscontrolsystem.entity.Employee;
import com.sumy.dooraccesscontrolsystem.entity.User;
import com.sumy.dooraccesscontrolsystem.utils.NFCTools;

/**
 * 雇员刷卡进入
 * 
 * @author sumy
 * 
 */
public class EmployeeNFCEnterActivity extends BaseActivity {

    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private CheckInDao mDao;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_nfc_input;
    }

    @Override
    protected void initView() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()), 0);
        mDao = new CheckInDao(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null,
                    null);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String _ID = NFCTools.readNFC(intent);
        for (User user : doorSystem.getUserlist()) {
            if (user instanceof Employee) {
                Employee _Employee = (Employee) user;
                if (_ID.equals(_Employee.getCardid())) {
                    setResult(RESULT_OK);
                    SimpleDateFormat dateformat = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss E ");
                    String time = dateformat.format(new Date());
                    mDao.insert(new CheckIn(user.getName(), time));
                    showToast("验证成功");
                    finish();
                }
            }
        }
    }

}
