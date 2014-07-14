package com.sumy.dooraccesscontrolsystem.utils;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;

/**
 * NFC标签的读写操作
 * @author caiwei
 *
 */
public final class NFCTools {
    private NFCTools() {
    };

    public static void writeNFC(String pMsg, Intent intent) {
        byte[] langBytes = Locale.CHINA.getLanguage().getBytes(
                Charset.forName("US-ASCII"));
        Charset utfEncode = Charset.forName("UTF-8");
        byte[] textBytes = pMsg.getBytes(utfEncode);
        int utfBit = 0;
        char status = (char) (utfBit + langBytes.length);

        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length,
                textBytes.length);
        NdefRecord ndefRecord = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
                NdefRecord.RTD_TEXT, new byte[0], data);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        NdefMessage ndefMessage = new NdefMessage(
                new NdefRecord[] { ndefRecord });
        try {
            Ndef ndef = Ndef.get(tag);
            ndef.connect();
            ndef.writeNdefMessage(ndefMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readNFC(Intent pIntent) {
        NdefRecord record = null;
        // Tag _Tag = pIntent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        // Ndef _Ndef = Ndef.get(_Tag);
        Parcelable[] rawMsgs = pIntent
                .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage[] msgs = null;
        @SuppressWarnings("unused")
        int contentSize = 0;
        if (rawMsgs != null) {
            msgs = new NdefMessage[rawMsgs.length];
            for (int i = 0; i < rawMsgs.length; i++) {
                msgs[i] = (NdefMessage) rawMsgs[i];
                contentSize += msgs[i].toByteArray().length;
            }
        }

        if (msgs != null) {
            record = msgs[0].getRecords()[0];
        }
        if (record.getTnf() != NdefRecord.TNF_WELL_KNOWN) {
            return null;
        }
        if (!Arrays.equals(record.getType(), NdefRecord.RTD_TEXT)) {
            return null;
        }
        try {
            byte[] payload = record.getPayload();
            String textEncode = ((payload[0] & 0x80) == 0) ? "UTF8" : "UTF16";
            int languageCodeLength = payload[0] & 0x3f;
            // String languageCode = new
            // String(payload,1,languageCodeLength,"US-ASCII");
            String text = new String(payload, languageCodeLength + 1,
                    payload.length - languageCodeLength - 1, textEncode);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
