package com.sumy.dooraccesscontrolsystem.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;

import com.sumy.dooraccesscontrolsystem.R;
import com.sumy.dooraccesscontrolsystem.entity.Employee;
import com.sumy.dooraccesscontrolsystem.utils.NFCTools;

/**
 * NFC写员工标签
 * 
 * @author sumy
 * 
 */
public class NFCInputActivity extends BaseActivity {

    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private Employee mEmployee;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_nfc_input;
    }

    @Override
    protected void initView() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            showToast("您的设备不支持NFC，无法制卡");
            finish();
            return;
        }
        if (!mNfcAdapter.isEnabled()) {
            showToast("请启用设备NFC功能");
            finish();
            return;
        }

        // 创建一个PendingIntent对象，以便Android系统能够在扫描到NFC标签时，用它来封装NFC标签的详细信息
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()), 0);
        if (getIntent() != null) {
            int position = getIntent().getExtras().getInt("position");
            if (position != 0) {
                mEmployee = (Employee) (doorSystem.getUserlist().get(position));
            }
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 处理NFC的相关信息
        super.onNewIntent(intent);
        String _EmployeeID = mEmployee.getCardid();
        NFCTools.writeNFC(_EmployeeID, intent);
        mEmployee.makeCard();
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();

        // 声明NFC前台调度
        if (mNfcAdapter != null) {
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null,
                    null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 取消NFC前台调度
        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(this);
        }
    }

}
